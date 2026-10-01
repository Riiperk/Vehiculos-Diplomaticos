package com.diplomaticfleet.backend.dto

/**
 * Datos utilizados para activar o desactivar una cuenta.
 *
 * @property activo nuevo estado de la cuenta.
 *
 * @author Equipo Diplomatic Fleet
 */
data class ActualizarEstadoUsuarioRequest(
    val activo: Boolean
)
