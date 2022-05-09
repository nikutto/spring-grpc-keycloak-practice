package com.example.demo

import org.keycloak.adapters.KeycloakConfigResolver
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver
import org.keycloak.adapters.springsecurity.KeycloakConfiguration
import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy
import org.lognet.springboot.grpc.security.GrpcSecurityConfigurerAdapter
import org.lognet.springboot.grpc.security.GrpcSecurity
import io.grpc.examples.helloworld.GreeterGrpc
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.lognet.springboot.grpc.security.jwt.JwtAuthProviderFactory
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider as SBJwtAuthenticationProvider

@KeycloakConfiguration
class MySecurityConfig : KeycloakWebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        val keycloakAuthenticationProvider = keycloakAuthenticationProvider()
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(SimpleAuthorityMapper())
        auth.authenticationProvider(keycloakAuthenticationProvider)
    }
    
    override fun sessionAuthenticationStrategy() = RegisterSessionAuthenticationStrategy(SessionRegistryImpl())

    override fun configure(http: HttpSecurity) {
        super.configure(http)

        http.authorizeRequests()
            .antMatchers("/hello").permitAll()
            .anyRequest().authenticated()
    }
}

@KeycloakConfiguration
class Config {
    @Bean
    fun keycloakConfigResolver(): KeycloakConfigResolver = KeycloakSpringBootConfigResolver()
}


class GrpcSecurityConfig(
    @Autowired val jwtDecoder: JwtDecoder
): GrpcSecurityConfigurerAdapter() {
    
    
    override fun configure(builder: GrpcSecurity) {
        super.configure(builder)
        builder.authorizeRequests()
                .methods(GreeterGrpc.getSayHelloMethod()).authenticated()
        .and()
                .authenticationProvider(JwtAuthProviderFactory.forAuthorities(jwtDecoder))
    }
    
}
