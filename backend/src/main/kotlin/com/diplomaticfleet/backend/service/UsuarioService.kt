/*
 * Proyecto: Diplomatic Fleet
 * Módulo: Usuarios y roles
 *
 * Descripción:
 * Implementa las reglas de negocio para administrar
 * las cuentas de usuario y sus roles.
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

package com.diplomaticfleet.backend.service

import com.diplomaticfleet.backend.dto.CrearUsuarioRequest
import com.diplomaticfleet.backend.dto.UsuarioResponse
import com.diplomaticfleet.backend.entity.Usuario
import com.diplomaticfleet.backend.exception.ConflictoException
import com.diplomaticfleet.backend.exception.RecursoNoEncontradoException
import com.diplomaticfleet.backend.repository.RolRepository
import com.diplomaticfleet.backend.repository.UsuarioRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

/**
 * Gestiona las operaciones relacionadas con usuarios.
 *
 * Centraliza las validaciones, la protección de contraseñas
 * y la asignación de roles.
 *
 * @author Equipo Diplomatic Fleet
 */
@Service
class UsuarioService(
    private val usuarioRepository: UsuarioRepository,
    private val rolRepository: RolRepository,
    private val passwordEncoder: PasswordEncoder
) {

    /**
     * Consulta todas las cuentas registradas.
     *
     * @return lista de usuarios sin información sensible.
     */
    @Transactional(readOnly = true)
    fun listar(): List<UsuarioResponse> =
        usuarioRepository.findAll().map(::convertirARespuesta)

    /**
     * Consulta una cuenta mediante su identificador.
     *
     * @param id identificador del usuario.
     * @return información pública de la cuenta.
     * @throws RecursoNoEncontradoException si no existe.
     */
    @Transactional(readOnly = true)
    fun buscarPorId(id: Long): UsuarioResponse {
        val usuario = obtenerEntidad(id)
        return convertirARespuesta(usuario)
    }

    /**
     * Registra una cuenta y protege su contraseña.
     *
     * También comprueba que el correo sea único y que todos
     * los roles solicitados existan y estén activos.
     *
     * @param request datos de la nueva cuenta.
     * @return usuario creado sin exponer la contraseña.
     * @throws ConflictoException si el correo ya está registrado.
     * @throws RecursoNoEncontradoException si algún rol no existe.
     */
    @Transactional
    fun crear(request: CrearUsuarioRequest): UsuarioResponse {
        val correoNormalizado = request.correo.trim().lowercase()

        if (usuarioRepository.existsByCorreo(correoNormalizado)) {
            throw ConflictoException(
                "Ya existe un usuario registrado con el correo indicado"
            )
        }

        if (request.roles.isEmpty()) {
            throw ConflictoException(
                "El usuario debe tener al menos un rol"
            )
        }

        val rolesEncontrados = request.roles.map { nombreRol ->
            val nombreNormalizado = nombreRol.trim().uppercase()

            val rol = rolRepository.findByNombre(nombreNormalizado)
                ?: throw RecursoNoEncontradoException(
                    "No existe el rol $nombreNormalizado"
                )

            if (!rol.activo) {
                throw ConflictoException(
                    "El rol $nombreNormalizado no está activo"
                )
            }

            rol
        }.toMutableSet()

        // La contraseña se transforma antes de guardarse.
val usuario = Usuario(
    nombre = request.nombre.trim(),
    apellido = request.apellido.trim(),
    correo = correoNormalizado,
    passwordHash = requireNotNull(
        passwordEncoder.encode(request.password)
    ) {
        "No fue posible proteger la contraseña"
    },
    telefono = request.telefono
        ?.trim()
        ?.takeIf { it.isNotEmpty() },
    activo = true,
    roles = rolesEncontrados
)

        return convertirARespuesta(usuarioRepository.save(usuario))
    }

    /**
     * Activa o desactiva una cuenta existente.
     *
     * @param id identificador del usuario.
     * @param activo nuevo estado de la cuenta.
     * @return usuario actualizado.
     */
    @Transactional
    fun actualizarEstado(id: Long, activo: Boolean): UsuarioResponse {
        val usuario = obtenerEntidad(id)

        usuario.activo = activo
        usuario.actualizadoEn = LocalDateTime.now()

        return convertirARespuesta(usuarioRepository.save(usuario))
    }

    /**
     * Obtiene la entidad completa mediante su identificador.
     *
     * @param id identificador buscado.
     * @return entidad encontrada.
     * @throws RecursoNoEncontradoException si no existe.
     */
    private fun obtenerEntidad(id: Long): Usuario =
        usuarioRepository.findById(id).orElseThrow {
            RecursoNoEncontradoException(
                "No existe un usuario con el identificador $id"
            )
        }

    /**
     * Convierte una entidad en una respuesta segura para la API.
     *
     * La contraseña protegida no se incluye en el resultado.
     *
     * @param usuario entidad que se desea convertir.
     * @return información pública del usuario.
     */
    private fun convertirARespuesta(usuario: Usuario): UsuarioResponse =
        UsuarioResponse(
            id = requireNotNull(usuario.id),
            nombre = usuario.nombre,
            apellido = usuario.apellido,
            correo = usuario.correo,
            telefono = usuario.telefono,
            activo = usuario.activo,
            roles = usuario.roles.map { it.nombre }.toSet()
        )
}
