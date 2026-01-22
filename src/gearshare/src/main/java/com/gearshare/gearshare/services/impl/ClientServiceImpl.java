package com.gearshare.gearshare.services.impl;

import com.gearshare.gearshare.domain.entities.ClientEntity;
import com.gearshare.gearshare.repositories.ClientRepository;
import com.gearshare.gearshare.security.OAuth2ClientService;
import com.gearshare.gearshare.services.ClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private static final Logger logger = LoggerFactory.getLogger(OAuth2ClientService.class);

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public ClientEntity createOrUpdateClient(ClientEntity client) {
        logger.info("Attempting to save client with email : {}", client.getEmail());

        ClientEntity savedClient = clientRepository.save(client);

        logger.info("Saved client with generated UUID : {}", client.getClientUUID());

        return savedClient;
    }

    @Override
    public List<ClientEntity> findAllClients() {
        return StreamSupport
                .stream(clientRepository
                                .findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ClientEntity> findClientWithUUID(UUID clientUUID) {
        return clientRepository.findById(clientUUID);
    }

    @Override
    public boolean exists(UUID clientUUID) {
        return clientRepository.existsById(clientUUID);
    }

    @Override
    public void deleteClientWithUUID(UUID clientUUID) {
        clientRepository.deleteById(clientUUID);
    }

    @Override
    public ClientEntity partiallyUpdateClientWithUUID(UUID clientUUID, ClientEntity client) {

        return clientRepository.findById(clientUUID)
                .map(existingClient -> {
                    Optional.ofNullable(client.getUsername())
                            .ifPresent(existingClient::setUsername);
                    Optional.ofNullable(client.getPhoneNumber())
                            .ifPresent(existingClient::setPhoneNumber);
                    Optional.ofNullable(client.getFirstName())
                            .ifPresent(existingClient::setFirstName);
                    Optional.ofNullable(client.getLastName())
                            .ifPresent(existingClient::setLastName);

                    return clientRepository.save(existingClient);
                }).orElseThrow(() -> new RuntimeException(String.format("Client with UUID [%s] doesn't exist!", clientUUID)));

    }

    @Override
    public Optional<ClientEntity> findClientWithUsername(String username) {
        return clientRepository.findByUsername(username);
    }

    @Override
    public Optional<ClientEntity> findClientWithEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public Optional<ClientEntity> findClientWithProviderAndProviderId(String provider, String providerId) {
        return clientRepository.findByProviderAndProviderId(provider, providerId);
    }

    @Override
    @Transactional
    public void flush() {
        clientRepository.flush();
    }
}
