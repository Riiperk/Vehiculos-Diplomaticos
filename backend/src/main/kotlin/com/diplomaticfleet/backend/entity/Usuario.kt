/*
 * Proyecto: Diplomatic Fleet
 * Módulo: Usuarios y roles
 *
 * Descripción:
 * Representa las cuentas autorizadas para acceder
 * a la plataforma.
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

package com.diplomaticfleet.backend.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import java.time.LocalDateTime

/**
 * Representa una cuenta registrada en Diplomatic Fleet.
 *
 * La contraseña no se almacena directamente. [passwordHash]
 * contiene únicamente su representación protegida.
 *
 * @property id identificador único del usuario.
 * @property nombre nombres del usuario.
 * @property apellido apellidos del usuario.
 * @property correo dirección utilizada para iniciar sesión.
 * @property passwordHash contraseña almacenada de forma protegida.
 * @property telefono número de contacto opcional.
 * @property activo indica si la cuenta puede acceder.
 * @property creadoEn fecha de creación.
 * @property actualizadoEn fecha de la última modificación.
 * @property roles permisos asignados al usuario.
 *
 * @author Equipo Diplomatic Fleet
 */
@Entity
@Table(name = "usuarios")
class Usuario(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, length = 80)
    var nombre: String = "",

    @Column(nullable = false, length = 80)
    var apellido: String = "",

    @Column(nullable = false, unique = true, length = 150)
    var correo: String = "",

    @Column(name = "password_hash", nullable = false, length = 255)
    var passwordHash: String = "",

    @Column(length = 30)
    var telefono: String? = null,

    @Column(nullable = false)
    var activo: Boolean = true,

    @Column(name = "creado_en", nullable = false)
    var creadoEn: LocalDateTime = LocalDateTime.now(),

    @Column(name = "actualizado_en", nullable = false)
    var actualizadoEn: LocalDateTime = LocalDateTime.now(),

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuarios_roles",
        joinColumns = [JoinColumn(name = "usuario_id")],
        inverseJoinColumns = [JoinColumn(name = "rol_id")]
    )
    var roles: MutableSet<Rol> = mutableSetOf()
)