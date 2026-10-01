/*
 * Proyecto: Diplomatic Fleet
 * Módulo: Seguridad
 *
 * Descripción:
 * Configura el componente encargado de proteger
 * las contraseñas antes de almacenarlas.
 *
 * Equipo de desarrollo:
 * - Bryan Steven Pardo Núñez
 * - Maria Camila Pacheco Morales
 *
 * Versión: 1.0
 * Fecha: 2026-09-29
 */

package com.diplomaticfleet.backend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

/**
 * Proporciona componentes relacionados con la seguridad.
 *
 * @author Equipo Diplomatic Fleet
 */
@Configuration
class PasswordConfig {

    /**
     * Crea el codificador utilizado para proteger contraseñas.
     *
     * @return componente de codificación de contraseñas.
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}