package com.diplomaticfleet.backend.exception

import java.time.LocalDateTime

/**
 * Formato estándar utilizado para devolver errores de la API.
 *
 * @property fechaHora momento en que ocurrió el error.
 * @property estado código HTTP.
 * @property error nombre del tipo de error.
 * @property mensaje explicación comprensible.
 * @property ruta dirección solicitada.
 *
 * @author Equipo Diplomatic Fleet
 */
data class ErrorResponse(
    val fechaHora: LocalDateTime = LocalDateTime.now(),
    val estado: Int,
    val error: String,
    val mensaje: String,
    val ruta: String
)