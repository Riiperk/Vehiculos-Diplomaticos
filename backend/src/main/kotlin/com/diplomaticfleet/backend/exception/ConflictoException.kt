package com.diplomaticfleet.backend.exception

/**
 * Indica que una operación produciría un conflicto de datos.
 *
 * Ejemplo: intentar registrar un correo que ya existe.
 *
 * @param mensaje explicación del conflicto detectado.
 * @author Equipo Diplomatic Fleet
 */
class ConflictoException(
    mensaje: String
) : RuntimeException(mensaje)
