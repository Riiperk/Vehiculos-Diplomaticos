/*
 * Proyecto: Diplomatic Fleet
 * Módulo: Clientes institucionales
 *
 * Descripción:
 * Proporciona operaciones de acceso a datos para
 * los clientes institucionales.
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

package com.diplomaticfleet.backend.repository

import com.diplomaticfleet.backend.entity.ClienteInstitucional
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Gestiona la persistencia de los clientes institucionales.
 *
 * @author Equipo Diplomatic Fleet
 */
interface ClienteInstitucionalRepository :
    JpaRepository<ClienteInstitucional, Long> {

    /**
     * Comprueba si existe una institución activa con el mismo nombre.
     *
     * La búsqueda ignora diferencias entre mayúsculas y minúsculas.
     *
     * @param nombreEntidad nombre que se desea comprobar.
     * @return `true` cuando ya existe un cliente con ese nombre.
     */
    fun existsByNombreEntidadIgnoreCase(
        nombreEntidad: String
    ): Boolean
}
