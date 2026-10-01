package com.diplomaticfleet.backend.repository

import com.diplomaticfleet.backend.entity.Usuario
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Gestiona las operaciones de persistencia de los usuarios.
 *
 * @author Equipo Diplomatic Fleet
 */
interface UsuarioRepository : JpaRepository<Usuario, Long> {

    /**
     * Busca una cuenta mediante el correo utilizado para iniciar sesión.
     *
     * @param correo correo electrónico del usuario.
     * @return usuario encontrado o `null`.
     */
    fun findByCorreo(correo: String): Usuario?

    /**
     * Comprueba si ya existe una cuenta con el correo indicado.
     *
     * @param correo correo que se desea validar.
     * @return `true` cuando el correo ya está registrado.
     */
    fun existsByCorreo(correo: String): Boolean
}
