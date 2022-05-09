package com.example.demo
import org.keycloak.KeycloakPrincipal
import org.springframework.security.core.context.SecurityContextHolder

class UserInfoService {
    fun getUserInfo(): UserInfo {
        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as KeycloakPrincipal<*>
        val session = principal.keycloakSecurityContext
        val accessToken = session.token
        val idToken = session.idToken

        val idTokenInfo = IdTokenInfo(
            issuer = accessToken.issuer,
            audience = accessToken.audience.joinToString(",")
        )
        val realmAccess = accessToken.realmAccess
        val accessTokenInfo = AccessTokenInfo(
            issuer = "?",
            audience = "?",
            roles = realmAccess.roles.toString(),
            scopes = accessToken.scope
        )

        val info = UserInfo(
            username = idToken.preferredUsername,
            email = idToken.email ?: "",
            lastname = idToken.familyName ?: "",
            firstname = idToken.givenName ?: "",
            idTokenInfo,
            accessTokenInfo
        )

        return info
    }

    data class UserInfo(
        val username: String,
        val email: String,
        val lastname: String,
        val firstname: String,
        val idTokenInfo: IdTokenInfo,
        val accessTokenInfo: AccessTokenInfo
    )

    data class IdTokenInfo(
        val issuer: String,
        val audience: String
    )

    data class AccessTokenInfo(
        val issuer: String,
        val audience: String,
        val roles: String,
        val scopes: String
    )
}
