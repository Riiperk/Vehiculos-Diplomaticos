package com.diplomaticfleet.backend.dto

/**
 * Información pública de una cuenta.
 *
 * No contiene la contraseña ni su representación protegida.
 *
 * @property id identificador del usuario.
 * @property nombre nombres del usuario.
 * @property apellido apellidos del usuario.
 * @property correo correo de acceso.
 * @property telefono número de contacto.
 * @property activo estado de la cuenta.
 * @property roles roles asignados.
 *
 * @author Equipo Diplomatic Fleet
 */
data class UsuarioResponse(
    val id: Long,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val telefono: String?,
    val activo: Boolean,
    val roles: Set<String>
)