/*
 * Proyecto: Diplomatic Fleet
 * Módulo: Manejo de errores
 *
 * Descripción:
 * Define la excepción utilizada cuando un recurso
 * solicitado no existe en el sistema.
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

package com.diplomaticfleet.backend.exception

/**
 * Indica que el recurso solicitado no fue encontrado.
 *
 * Ejemplo: consultar un usuario mediante un identificador inexistente.
 *
 * @param mensaje explicación del recurso que no fue encontrado.
 * @author Equipo Diplomatic Fleet
 */
class RecursoNoEncontradoException(
    mensaje: String
) : RuntimeException(mensaje)