/*
 * Proyecto: Diplomatic Fleet
 * Módulo: Usuarios y roles
 *
 * Descripción:
 * Define los roles empleados para controlar los permisos
 * de acceso dentro de la aplicación.
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
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

/**
 * Representa un rol disponible dentro de Diplomatic Fleet.
 *
 * @property id identificador único generado por PostgreSQL.
 * @property nombre nombre único del rol.
 * @property descripcion responsabilidad asociada al rol.
 * @property activo indica si el rol puede seguir asignándose.
 *
 * @author Equipo Diplomatic Fleet
 */
@Entity
@Table(name = "roles")
class Rol(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, unique = true, length = 50)
    var nombre: String = "",

    @Column(length = 255)
    var descripcion: String? = null,

    @Column(nullable = false)
    var activo: Boolean = true
)