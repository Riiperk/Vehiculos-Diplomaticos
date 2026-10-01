/*
 * Proyecto: Diplomatic Fleet
 * Módulo: Clientes institucionales
 *
 * Descripción:
 * Define los datos que recibe la API al registrar
 * un cliente institucional.
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

package com.diplomaticfleet.backend.dto

import com.diplomaticfleet.backend.entity.TipoEntidadInstitucional
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

/**
 * Datos requeridos para registrar una institución.
 *
 * @property nombreEntidad nombre oficial de la institución.
 * @property tipoEntidad clasificación institucional.
 * @property nombreContacto contacto autorizado.
 * @property correoContacto correo del contacto.
 * @property telefonoContacto teléfono opcional.
 * @property direccion dirección opcional.
 * @property ciudad ciudad de ubicación.
 *
 * @author Equipo Diplomatic Fleet
 */
data class CrearClienteInstitucionalRequest(

    @field:NotBlank(
        message = "El nombre de la entidad es obligatorio"
    )
    @field:Size(max = 150)
    val nombreEntidad: String,

    val tipoEntidad: TipoEntidadInstitucional,

    @field:NotBlank(
        message = "El nombre del contacto es obligatorio"
    )
    @field:Size(max = 120)
    val nombreContacto: String,

    @field:NotBlank(
        message = "El correo del contacto es obligatorio"
    )
    @field:Email(
        message = "El correo del contacto no es válido"
    )
    @field:Size(max = 150)
    val correoContacto: String,

    @field:Size(max = 30)
    val telefonoContacto: String? = null,

    @field:Size(max = 200)
    val direccion: String? = null,

    @field:Size(max = 100)
    val ciudad: String? = null
)
