package com.gearshare.gearshare.security;

import com.gearshare.gearshare.domain.entities.ClientEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Data
public class ClientPrincipal implements OAuth2User, UserDetails {

    private UUID clientUUID;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String provider;
    private String providerId;
    private String role;
    private Map<String, Object> attributes;

    public ClientPrincipal(
                           UUID clientUUID,
                           String username,
                           String email,
                           String firstName,
                           String lastName,
                           String provider,
                           String providerId,
                           String role) {
        this.username = username;
        this.clientUUID = clientUUID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
    }


    public static ClientPrincipal createClientPrincipal(ClientEntity client){
        return new ClientPrincipal(
                client.getClientUUID(),
                client.getUsername(),
                client.getEmail(),
                client.getFirstName(),
                client.getLastName(),
                client.getProvider(),
                client.getProviderId(),
                client.getRole()
        );
    }

    public static ClientPrincipal createClientPrincipal(ClientEntity client, Map<String, Object> attributes){
        ClientPrincipal principal = ClientPrincipal.createClientPrincipal(client);
        principal.setAttributes(attributes);
        return principal;
    }


    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String r = (role == null ? "user" : role).toUpperCase();  // USER/SELLER/ADMIN
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + r));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getName() {
        return clientUUID.toString();
    }

   @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
