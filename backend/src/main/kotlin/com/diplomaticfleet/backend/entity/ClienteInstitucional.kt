/*
 * Proyecto: Diplomatic Fleet
 * Módulo: Clientes institucionales
 *
 * Descripción:
 * Representa una embajada, consulado u organismo
 * que solicita servicios vehiculares.
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
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

/**
 * Representa un cliente institucional registrado.
 *
 * @property id identificador único.
 * @property nombreEntidad nombre oficial de la institución.
 * @property tipoEntidad clasificación de la institución.
 * @property nombreContacto persona autorizada para solicitar servicios.
 * @property correoContacto correo del contacto institucional.
 * @property telefonoContacto teléfono del contacto.
 * @property direccion dirección física de la institución.
 * @property ciudad ciudad donde está ubicada.
 * @property activo indica si puede presentar nuevas solicitudes.
 * @property creadoEn fecha de creación del registro.
 * @property actualizadoEn fecha de la última modificación.
 *
 * @author Equipo Diplomatic Fleet
 */
@Entity
@Table(name = "clientes_institucionales")
class ClienteInstitucional(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "nombre_entidad", nullable = false, length = 150)
    var nombreEntidad: String = "",

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_entidad", nullable = false, length = 40)
    var tipoEntidad: TipoEntidadInstitucional =
        TipoEntidadInstitucional.OTRO,

    @Column(name = "nombre_contacto", nullable = false, length = 120)
    var nombreContacto: String = "",

    @Column(name = "correo_contacto", nullable = false, length = 150)
    var correoContacto: String = "",

    @Column(name = "telefono_contacto", length = 30)
    var telefonoContacto: String? = null,

    @Column(length = 200)
    var direccion: String? = null,

    @Column(length = 100)
    var ciudad: String? = null,

    @Column(nullable = false)
    var activo: Boolean = true,

    @Column(name = "creado_en", nullable = false)
    var creadoEn: LocalDateTime = LocalDateTime.now(),

    @Column(name = "actualizado_en", nullable = false)
    var actualizadoEn: LocalDateTime = LocalDateTime.now()
)