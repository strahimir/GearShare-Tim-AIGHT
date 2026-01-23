package com.gearshare.gearshare.controllers;

import com.gearshare.gearshare.domain.dto.ClientDto;
import com.gearshare.gearshare.domain.entities.ClientEntity;
import com.gearshare.gearshare.mappers.Mapper;
import com.gearshare.gearshare.security.ClientPrincipal;
import com.gearshare.gearshare.services.ClientService;
import com.gearshare.gearshare.services.SellerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;
    private final SellerService sellerService;
    private final Mapper<ClientEntity, ClientDto> clientMapper;

    public ClientController(
            Mapper<ClientEntity, ClientDto> clientMapper,
            ClientService clientService,
            SellerService sellerService
    ) {
        this.clientMapper = clientMapper;
        this.clientService = clientService;
        this.sellerService = sellerService;
    }

    @PostMapping
    public ResponseEntity<ClientDto> createClient(@RequestBody ClientDto client) {
        ClientEntity clientEntity = clientMapper.mapFrom(client);
        ClientEntity savedClientEntity = clientService.createOrUpdateClient(clientEntity);
        return new ResponseEntity<>(clientMapper.mapTo(savedClientEntity), HttpStatus.CREATED);
    }

    @GetMapping
    public List<ClientDto> getAllClients() {
        List<ClientEntity> clients = clientService.findAllClients();
        return clients.stream()
                .map(clientMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{clientUUID}")
    public ResponseEntity<ClientDto> getClientByUUID(@PathVariable("clientUUID") UUID clientUUID) {
        Optional<ClientEntity> client = clientService.findClientWithUUID(clientUUID);
        return client.map(clientEntity -> new ResponseEntity<>(clientMapper.mapTo(clientEntity), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/profiles/{username}")
    public ResponseEntity<ClientDto> getClientByUsername(@PathVariable("username") String username) {
        Optional<ClientEntity> client = clientService.findClientWithUsername(username);
        return client.map(clientEntity -> new ResponseEntity<>(clientMapper.mapTo(clientEntity), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/{clientUUID}")
    public ResponseEntity<ClientDto> fullUpdateClient(@RequestBody ClientDto client,
                                                      @PathVariable("clientUUID") UUID clientUUID) {
        if (!clientService.exists(clientUUID)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        client.setClientUUID(clientUUID);
        ClientEntity clientEntity = clientMapper.mapFrom(client);
        ClientEntity updatedClientEntity = clientService.createOrUpdateClient(clientEntity);
        return new ResponseEntity<>(clientMapper.mapTo(updatedClientEntity), HttpStatus.OK);
    }

    @PatchMapping(path = "/{clientUUID}")
    public ResponseEntity<ClientDto> partialUpdateClient(@RequestBody ClientDto clientDto,
                                                         @PathVariable("clientUUID") UUID clientUUID) {
        if (!clientService.exists(clientUUID)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        clientDto.setClientUUID(clientUUID);
        ClientEntity clientEntity = clientMapper.mapFrom(clientDto);
        ClientEntity updatedClientEntity = clientService.partiallyUpdateClientWithUUID(clientUUID, clientEntity);

        return new ResponseEntity<>(clientMapper.mapTo(updatedClientEntity), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{clientUUID}")
    public ResponseEntity<Void> deleteClient(@PathVariable("clientUUID") UUID clientUUID) {
        if (!clientService.exists(clientUUID)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        clientService.deleteClientWithUUID(clientUUID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    

    @PatchMapping("/me/role/client")
    @Transactional
    public ResponseEntity<ClientDto> becomeClient(Authentication authentication) {
        return setMyRole(authentication, "CLIENT");
    }

    /**
     * When becoming SELLER, also insert into seller table:
     * - subscription start = now
     * - subscription end = now + 1 month
     */
    @PatchMapping("/me/role/seller")
    @Transactional
    public ResponseEntity<ClientDto> becomeSeller(Authentication authentication) {
        // auth checks
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (!(authentication.getPrincipal() instanceof ClientPrincipal principal)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // load + update client role
        ClientEntity client = clientService.findClientWithUUID(principal.getClientUUID()).orElse(null);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        client.setRole("SELLER");
        ClientEntity saved = clientService.createOrUpdateClient(client);

        // create seller subscription row (start now, end +1 month)
        sellerService.startSubscriptionNow(saved.getClientUUID());

        // refresh session principal so ROLE_SELLER applies immediately
        refreshAuthentication(authentication, principal, saved);

        return ResponseEntity.ok(clientMapper.mapTo(saved));
    }

    @PatchMapping("/me/role/admin")
    @Transactional
    public ResponseEntity<ClientDto> becomeAdmin(Authentication authentication) {
        return setMyRole(authentication, "ADMIN");
    }

    private ResponseEntity<ClientDto> setMyRole(Authentication authentication, String newRole) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Object principalObj = authentication.getPrincipal();
        if (!(principalObj instanceof ClientPrincipal principal)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        ClientEntity client = clientService.findClientWithUUID(principal.getClientUUID())
                .orElse(null);

        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        client.setRole(newRole);
        ClientEntity saved = clientService.createOrUpdateClient(client);

        // Update the session principal so the new role applies immediately
        refreshAuthentication(authentication, principal, saved);

        return ResponseEntity.ok(clientMapper.mapTo(saved));
    }

    private void refreshAuthentication(Authentication oldAuth, ClientPrincipal oldPrincipal, ClientEntity updatedClient) {
        ClientPrincipal newPrincipal =
                ClientPrincipal.createClientPrincipal(updatedClient, oldPrincipal.getAttributes());

        Authentication newAuth;

        if (oldAuth instanceof OAuth2AuthenticationToken oauth2) {
            newAuth = new OAuth2AuthenticationToken(
                    newPrincipal,
                    newPrincipal.getAuthorities(),
                    oauth2.getAuthorizedClientRegistrationId()
            );
        } else {
            newAuth = new UsernamePasswordAuthenticationToken(
                    newPrincipal,
                    oldAuth.getCredentials(),
                    newPrincipal.getAuthorities()
            );
        }

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }
}
