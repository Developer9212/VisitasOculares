package com.fenoreste.voculares.security;


import com.fenoreste.voculares.entity.Tabla;
import com.fenoreste.voculares.entity.TablaPK;
import com.fenoreste.voculares.service.ITablaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.util.UUID;

@Configuration
public class AuthorizationServerConfig {

    private final ITablaService tablasService;
    private final PasswordEncoder passwordEncoder;

    public AuthorizationServerConfig(
            ITablaService tablasService,
            PasswordEncoder passwordEncoder) {

        this.tablasService = tablasService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 🔐 Security para /oauth2/token
     */
    @Bean
    public SecurityFilterChain authorizationServerSecurityFilterChain(
            HttpSecurity http) throws Exception {

        OAuth2AuthorizationServerConfiguration
                .applyDefaultSecurity(http);

        http
                .oauth2ResourceServer(resourceServer ->
                        resourceServer.jwt(Customizer.withDefaults()));

        return http.build();
    }

    /**
     * 🔑 CLIENT dinámico desde BD
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository() {

        TablaPK pk = new TablaPK("voculares", "user-ws");

        Tabla tbSecurity = tablasService.buscarPorId(pk);
        System.out.println(":::::::::::::::::::::::::::::::::::::::::::::");
        System.out.println(
                "client_id:" + tbSecurity.getDato1() +
                        ", client_secret(hash):" + tbSecurity.getDato2()
        );

        RegisteredClient registeredClient =
                RegisteredClient
                        .withId(UUID.randomUUID().toString())
                        .clientId(tbSecurity.getDato1())

                        // 🔥 YA ENCRIPTADO EN BD
                        .clientSecret(tbSecurity.getDato2())

                        .authorizationGrantType(
                                AuthorizationGrantType.CLIENT_CREDENTIALS
                        )
                        .authorizationGrantType(
                                AuthorizationGrantType.REFRESH_TOKEN
                        )
                        .scope("read")
                        .scope("write")
                        .build();

        return new InMemoryRegisteredClientRepository(
                registeredClient
        );
    }

    /**
     * ⚙️ Configuración general del Authorization Server
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {

        return AuthorizationServerSettings
                .builder()
                .build();
    }

}