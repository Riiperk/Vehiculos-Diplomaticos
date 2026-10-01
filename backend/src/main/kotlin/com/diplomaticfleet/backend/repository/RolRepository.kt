package com.diplomaticfleet.backend.repository

import com.diplomaticfleet.backend.entity.Rol
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Gestiona las operaciones de persistencia de los roles.
 *
 * @author Equipo Diplomatic Fleet
 */
interface RolRepository : JpaRepository<Rol, Long> {

    /**
     * Busca un rol por su nombre único.
     *
     * @param nombre nombre del rol.
     * @return rol encontrado o `null`.
     */
    fun findByNombre(nombre: String): Rol?
}