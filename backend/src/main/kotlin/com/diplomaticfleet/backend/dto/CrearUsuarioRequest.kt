package com.diplomaticfleet.backend.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * Datos necesarios para registrar una cuenta.
 *
 * @property nombre nombres del usuario.
 * @property apellido apellidos del usuario.
 * @property correo correo único para iniciar sesión.
 * @property password contraseña sin transformar recibida temporalmente.
 * @property telefono número de contacto opcional.
 * @property roles nombres de los roles asignados.
 *
 * @author Equipo Diplomatic Fleet
 */
data class CrearUsuarioRequest(

    @field:NotBlank(message = "El nombre es obligatorio")
    @field:Size(max = 80)
    val nombre: String,

    @field:NotBlank(message = "El apellido es obligatorio")
    @field:Size(max = 80)
    val apellido: String,

    @field:NotBlank(message = "El correo es obligatorio")
    @field:Email(message = "El correo no tiene un formato válido")
    @field:Size(max = 150)
    val correo: String,

    @field:NotBlank(message = "La contraseña es obligatoria")
    @field:Size(
        min = 8,
        max = 72,
        message = "La contraseña debe tener entre 8 y 72 caracteres"
    )
    val password: String,

    @field:Size(max = 30)
    val telefono: String? = null,

    val roles: Set<String>
)