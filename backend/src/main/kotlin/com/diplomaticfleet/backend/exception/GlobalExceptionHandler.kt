/*
 * Proyecto: Diplomatic Fleet
 * Módulo: Manejo de errores
 *
 * Descripción:
 * Centraliza los errores producidos por los controladores
 * y los convierte en respuestas HTTP comprensibles.
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

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * Traduce excepciones de negocio y validación
 * en respuestas HTTP uniformes.
 *
 * @author Equipo Diplomatic Fleet
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    /**
     * Devuelve HTTP 404 cuando el recurso solicitado no existe.
     *
     * @param exception excepción generada.
     * @param request petición HTTP que produjo el error.
     * @return respuesta con información comprensible.
     */
    @ExceptionHandler(RecursoNoEncontradoException::class)
    fun manejarNoEncontrado(
        exception: RecursoNoEncontradoException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> =
        crearRespuesta(
            estado = HttpStatus.NOT_FOUND,
            mensaje = exception.message ?: "Recurso no encontrado",
            ruta = request.requestURI
        )

    /**
     * Devuelve HTTP 409 cuando una operación genera
     * un conflicto con los datos existentes.
     *
     * @param exception excepción generada.
     * @param request petición HTTP que produjo el error.
     * @return respuesta con información del conflicto.
     */
    @ExceptionHandler(ConflictoException::class)
    fun manejarConflicto(
        exception: ConflictoException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> =
        crearRespuesta(
            estado = HttpStatus.CONFLICT,
            mensaje = exception.message ?: "Se produjo un conflicto",
            ruta = request.requestURI
        )

    /**
     * Devuelve HTTP 400 cuando los datos recibidos
     * incumplen las reglas de validación.
     *
     * @param exception contiene los campos inválidos.
     * @param request petición HTTP que produjo el error.
     * @return respuesta con todos los errores encontrados.
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun manejarValidacion(
        exception: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {
        val mensaje = exception.bindingResult.fieldErrors
            .joinToString("; ") { error ->
                "${error.field}: ${error.defaultMessage}"
            }

        return crearRespuesta(
            estado = HttpStatus.BAD_REQUEST,
            mensaje = mensaje,
            ruta = request.requestURI
        )
    }

    /**
     * Construye el formato estándar de error utilizado por la API.
     *
     * @param estado código HTTP correspondiente.
     * @param mensaje explicación del problema.
     * @param ruta dirección solicitada.
     * @return respuesta HTTP con el error.
     */
    private fun crearRespuesta(
        estado: HttpStatus,
        mensaje: String,
        ruta: String
    ): ResponseEntity<ErrorResponse> =
        ResponseEntity.status(estado).body(
            ErrorResponse(
                estado = estado.value(),
                error = estado.reasonPhrase,
                mensaje = mensaje,
                ruta = ruta
            )
        )
}
