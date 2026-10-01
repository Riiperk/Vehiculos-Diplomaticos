/*
 * Proyecto: Diplomatic Fleet
 * Archivo: V1__crear_esquema_inicial.sql
 *
 * Descripción:
 * Crea la estructura inicial de PostgreSQL necesaria para implementar
 * las historias de usuario US01 a US10.
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

-- =========================================================
-- US01 Y US02: AUTENTICACIÓN, USUARIOS Y ROLES
-- =========================================================

-- Catálogo de roles disponibles en la aplicación.
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

-- Almacena las cuentas que pueden acceder al sistema.
CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    apellido VARCHAR(80) NOT NULL,
    correo VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    telefono VARCHAR(30),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Relación entre usuarios y roles.
-- Permite que una cuenta pueda tener uno o varios roles.
CREATE TABLE usuarios_roles (
    usuario_id BIGINT NOT NULL,
    rol_id BIGINT NOT NULL,

    PRIMARY KEY (usuario_id, rol_id),

    CONSTRAINT fk_usuario_rol_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id),

    CONSTRAINT fk_usuario_rol_rol
        FOREIGN KEY (rol_id)
        REFERENCES roles(id)
);

-- =========================================================
-- US03: CLIENTES INSTITUCIONALES
-- =========================================================

-- Registra embajadas, consulados y organismos internacionales.
CREATE TABLE clientes_institucionales (
    id BIGSERIAL PRIMARY KEY,
    nombre_entidad VARCHAR(150) NOT NULL,
    tipo_entidad VARCHAR(40) NOT NULL,
    nombre_contacto VARCHAR(120) NOT NULL,
    correo_contacto VARCHAR(150) NOT NULL,
    telefono_contacto VARCHAR(30),
    direccion VARCHAR(200),
    ciudad VARCHAR(100),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_tipo_entidad
        CHECK (
            tipo_entidad IN (
                'EMBAJADA',
                'CONSULADO',
                'ORGANISMO_INTERNACIONAL',
                'OTRO'
            )
        )
);

-- =========================================================
-- US04: GESTIÓN DE VEHÍCULOS
-- =========================================================

-- Almacena los vehículos administrados por la empresa.
CREATE TABLE vehiculos (
    id BIGSERIAL PRIMARY KEY,
    placa VARCHAR(20) NOT NULL UNIQUE,
    marca VARCHAR(60) NOT NULL,
    modelo VARCHAR(60) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    anio INTEGER NOT NULL,
    color VARCHAR(40),
    capacidad_pasajeros INTEGER NOT NULL,
    capacidad_equipaje INTEGER NOT NULL DEFAULT 0,
    estado VARCHAR(30) NOT NULL DEFAULT 'DISPONIBLE',
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_anio_vehiculo
        CHECK (anio BETWEEN 1950 AND 2100),

    CONSTRAINT chk_capacidad_pasajeros
        CHECK (capacidad_pasajeros > 0),

    CONSTRAINT chk_capacidad_equipaje
        CHECK (capacidad_equipaje >= 0),

    CONSTRAINT chk_estado_vehiculo
    CHECK (
    estado IN (
        'DISPONIBLE',
        'RESERVADO',
        'EN_SERVICIO',
        'EN_MANTENIMIENTO',
        'INACTIVO'
    )
)
);

-- =========================================================
-- US05: DOCUMENTACIÓN DE VEHÍCULOS
-- =========================================================

-- Conserva los documentos legales asociados a cada vehículo.
CREATE TABLE documentos_vehiculo (
    id BIGSERIAL PRIMARY KEY,
    vehiculo_id BIGINT NOT NULL,
    tipo_documento VARCHAR(50) NOT NULL,
    numero_documento VARCHAR(100),
    fecha_emision DATE,
    fecha_vencimiento DATE NOT NULL,
    observaciones VARCHAR(500),
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_documento_vehiculo
        FOREIGN KEY (vehiculo_id)
        REFERENCES vehiculos(id),

    CONSTRAINT chk_fechas_documento
        CHECK (
            fecha_emision IS NULL
            OR fecha_vencimiento >= fecha_emision
        )
);

-- =========================================================
-- US06: MANTENIMIENTOS
-- =========================================================

-- Registra mantenimientos preventivos y correctivos.
CREATE TABLE mantenimientos (
    id BIGSERIAL PRIMARY KEY,
    vehiculo_id BIGINT NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    estado VARCHAR(30) NOT NULL DEFAULT 'PROGRAMADO',
    fecha_programada DATE NOT NULL,
    fecha_inicio DATE,
    fecha_fin DATE,
    proveedor VARCHAR(150),
    costo NUMERIC(14, 2) NOT NULL DEFAULT 0,
    observaciones VARCHAR(1000),
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_mantenimiento_vehiculo
        FOREIGN KEY (vehiculo_id)
        REFERENCES vehiculos(id),

    CONSTRAINT chk_tipo_mantenimiento
        CHECK (
            tipo IN (
                'PREVENTIVO',
                'CORRECTIVO'
            )
        ),

    CONSTRAINT chk_estado_mantenimiento
        CHECK (
            estado IN (
                'PROGRAMADO',
                'EN_PROCESO',
                'FINALIZADO',
                'CANCELADO'
            )
        ),

    CONSTRAINT chk_costo_mantenimiento
        CHECK (costo >= 0),

    CONSTRAINT chk_fechas_mantenimiento
        CHECK (
            fecha_fin IS NULL
            OR fecha_inicio IS NULL
            OR fecha_fin >= fecha_inicio
        )
);

-- =========================================================
-- US07 Y US08: DISPONIBILIDAD Y SOLICITUDES
-- =========================================================

-- Registra solicitudes presentadas por clientes institucionales.
CREATE TABLE solicitudes (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(30) NOT NULL UNIQUE,
    cliente_id BIGINT NOT NULL,
    solicitante_id BIGINT NOT NULL,
    vehiculo_id BIGINT,
    modalidad VARCHAR(30) NOT NULL,
    categoria_solicitada VARCHAR(50),
    cantidad_pasajeros INTEGER NOT NULL,
    cantidad_equipaje INTEGER NOT NULL DEFAULT 0,
    fecha_hora_inicio TIMESTAMP NOT NULL,
    fecha_hora_fin TIMESTAMP NOT NULL,
       lugar_recogida VARCHAR(250) NOT NULL,
    lugar_devolucion VARCHAR(250) NOT NULL,
    necesidades_especiales VARCHAR(1000),
    estado VARCHAR(30) NOT NULL DEFAULT 'RECIBIDA',
    motivo_rechazo VARCHAR(500),
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_solicitud_cliente
        FOREIGN KEY (cliente_id)
        REFERENCES clientes_institucionales(id),

    CONSTRAINT fk_solicitud_usuario
        FOREIGN KEY (solicitante_id)
        REFERENCES usuarios(id),

    CONSTRAINT fk_solicitud_vehiculo
        FOREIGN KEY (vehiculo_id)
        REFERENCES vehiculos(id),

    CONSTRAINT chk_modalidad_solicitud
        CHECK (
            modalidad IN (
                'CON_CONDUCTOR',
                'SIN_CONDUCTOR'
            )
        ),

    CONSTRAINT chk_estado_solicitud
        CHECK (
            estado IN (
                'RECIBIDA',
                'EN_REVISION',
                'COTIZADA',
                'APROBADA',
                'RECHAZADA',
                'CANCELADA'
            )
        ),

    CONSTRAINT chk_fechas_solicitud
        CHECK (fecha_hora_fin > fecha_hora_inicio),

    CONSTRAINT chk_pasajeros_solicitud
        CHECK (cantidad_pasajeros > 0),

    CONSTRAINT chk_equipaje_solicitud
        CHECK (cantidad_equipaje >= 0)
);

-- =========================================================
-- US09: VALIDACIÓN DEL CONDUCTOR AUTORIZADO
-- =========================================================

-- Solo se utiliza cuando la modalidad es SIN_CONDUCTOR.
CREATE TABLE conductores_autorizados (
    id BIGSERIAL PRIMARY KEY,
    solicitud_id BIGINT NOT NULL UNIQUE,
    nombre_completo VARCHAR(160) NOT NULL,
    tipo_documento VARCHAR(30) NOT NULL,
    numero_documento VARCHAR(60) NOT NULL,
    numero_licencia VARCHAR(80) NOT NULL,
    categoria_licencia VARCHAR(20),
    fecha_vencimiento_licencia DATE NOT NULL,
    estado_validacion VARCHAR(30) NOT NULL DEFAULT 'PENDIENTE',
    observaciones VARCHAR(500),
    validado_por BIGINT,
    validado_en TIMESTAMP,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_conductor_solicitud
        FOREIGN KEY (solicitud_id)
        REFERENCES solicitudes(id),

    CONSTRAINT fk_conductor_validador
        FOREIGN KEY (validado_por)
        REFERENCES usuarios(id),

    CONSTRAINT chk_estado_validacion_conductor
        CHECK (
            estado_validacion IN (
                'PENDIENTE',
                'APROBADO',
                'RECHAZADO'
            )
        )
);

-- =========================================================
-- US10: COTIZACIONES
-- =========================================================

-- Guarda la propuesta económica asociada a una solicitud.
CREATE TABLE cotizaciones (
    id BIGSERIAL PRIMARY KEY,
    codigo VARCHAR(30) NOT NULL UNIQUE,
    solicitud_id BIGINT NOT NULL,
    vehiculo_id BIGINT,
    elaborada_por BIGINT NOT NULL,
    fecha_emision TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_vencimiento DATE NOT NULL,
    tarifa_base NUMERIC(14, 2) NOT NULL DEFAULT 0,
    valor_extras NUMERIC(14, 2) NOT NULL DEFAULT 0,
    valor_impuestos NUMERIC(14, 2) NOT NULL DEFAULT 0,
    valor_garantia NUMERIC(14, 2) NOT NULL DEFAULT 0,
    valor_total NUMERIC(14, 2) NOT NULL DEFAULT 0,
    estado VARCHAR(30) NOT NULL DEFAULT 'BORRADOR',
    observaciones VARCHAR(1000),
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    actualizado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_cotizacion_solicitud
        FOREIGN KEY (solicitud_id)
        REFERENCES solicitudes(id),

    CONSTRAINT fk_cotizacion_vehiculo
        FOREIGN KEY (vehiculo_id)
        REFERENCES vehiculos(id),

    CONSTRAINT fk_cotizacion_usuario
        FOREIGN KEY (elaborada_por)
        REFERENCES usuarios(id),

    CONSTRAINT chk_estado_cotizacion
               CHECK (
            estado IN (
                'BORRADOR',
                'ENVIADA',
                'ACEPTADA',
                'RECHAZADA',
                'VENCIDA'
            )
        ),

    CONSTRAINT chk_valores_cotizacion
        CHECK (
            tarifa_base >= 0
            AND valor_extras >= 0
            AND valor_impuestos >= 0
            AND valor_garantia >= 0
            AND valor_total >= 0
        )
);

-- Registra los conceptos individuales incluidos en una cotización.
CREATE TABLE detalles_cotizacion (
    id BIGSERIAL PRIMARY KEY,
    cotizacion_id BIGINT NOT NULL,
    concepto VARCHAR(150) NOT NULL,
    descripcion VARCHAR(500),
    cantidad NUMERIC(12, 2) NOT NULL DEFAULT 1,
    valor_unitario NUMERIC(14, 2) NOT NULL DEFAULT 0,
    subtotal NUMERIC(14, 2) NOT NULL DEFAULT 0,

    CONSTRAINT fk_detalle_cotizacion
        FOREIGN KEY (cotizacion_id)
        REFERENCES cotizaciones(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_cantidad_detalle
        CHECK (cantidad > 0),

    CONSTRAINT chk_valores_detalle
        CHECK (
            valor_unitario >= 0
            AND subtotal >= 0
        )
);

-- =========================================================
-- ÍNDICES
-- Mejoran el rendimiento de las consultas frecuentes.
-- =========================================================

CREATE INDEX idx_vehiculo_estado
    ON vehiculos(estado);

CREATE INDEX idx_documento_vencimiento
    ON documentos_vehiculo(fecha_vencimiento);

CREATE INDEX idx_mantenimiento_fechas
    ON mantenimientos(fecha_inicio, fecha_fin);

CREATE INDEX idx_solicitud_fechas
    ON solicitudes(fecha_hora_inicio, fecha_hora_fin);

CREATE INDEX idx_solicitud_estado
    ON solicitudes(estado);

CREATE INDEX idx_solicitud_vehiculo
    ON solicitudes(vehiculo_id);

CREATE INDEX idx_cotizacion_estado
    ON cotizaciones(estado);

-- =========================================================
-- DATOS INICIALES
-- Roles necesarios para utilizar la aplicación.
-- =========================================================

INSERT INTO roles (nombre, descripcion) VALUES
(
    'ADMINISTRADOR_SISTEMA',
    'Gestiona usuarios, roles y configuración de acceso.'
),
(
    'CLIENTE_INSTITUCIONAL',
    'Consulta vehículos y presenta solicitudes de servicio.'
),
(
    'AGENTE_OPERACIONES',
    'Gestiona solicitudes, validaciones y cotizaciones.'
),
(
    'ADMINISTRADOR_VEHICULOS',
    'Gestiona vehículos, documentos y mantenimientos.'
),
(
    'CONDUCTOR_PROFESIONAL',
    'Ejecuta servicios de movilidad con conductor.'
),
(
    'GESTOR_COBROS',
    'Gestiona liquidaciones y procesos de cobro.'
);