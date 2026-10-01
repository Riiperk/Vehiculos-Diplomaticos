/*
 * Proyecto: Diplomatic Fleet
 * Módulo: Seguridad
 *
 * Descripción:
 * Configura provisionalmente el acceso a los endpoints.
 * Durante el desarrollo inicial permite probar la API
 * antes de implementar la autenticación de US01.
 *
 * Equipo de desarrollo:
 * - Daniela Largo Corredor
 * - Xiomara Lobatón Galindo
 * - Bryan Steven Pardo Núñez
 * - Maria Camila Pacheco Morales
 *
 * Versión: 1.0
 * Fecha: 2026-09-29
 */

package com.diplomaticfleet.backend.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

/**
 * Define las reglas generales de seguridad de la aplicación.
 *
 * Esta versión permite temporalmente todas las peticiones
 * para facilitar las pruebas iniciales del backend.
 *
 * @author Equipo Diplomatic Fleet
 */
@Configuration
class SecurityConfig {

    /**
     * Configura la cadena de filtros de seguridad.
     *
     * CSRF se desactiva porque la aplicación utilizará una API REST.
     * Las sesiones no se almacenan en el servidor.
     *
     * @param http configuración de seguridad proporcionada por Spring.
     * @return cadena de filtros configurada.
     */
    @Bean
    fun securityFilterChain(
        http: HttpSecurity
    ): SecurityFilterChain {
        http
            .csrf { csrf ->
                csrf.disable()
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            }
            .authorizeHttpRequests { requests ->
                requests.anyRequest().permitAll()
            }

        return http.build()
    }
}
