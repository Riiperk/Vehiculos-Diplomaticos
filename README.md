# Diplomatic Fleet

Sistema web para gestionar servicios de alquiler y movilidad vehicular dirigidos a embajadas, consulados, organismos internacionales y personal autorizado.

## Demo pública

Accede a la demostración desde el siguiente enlace:

PEGAR_AQUÍ_EL_ENLACE_DE_GITHUB_PAGES

### Credenciales de demostración

- **Correo:** admin@diplomaticfleet.co
- **Contraseña:** Demo1234

> La demo pública utiliza almacenamiento local del navegador para facilitar su evaluación sin instalar Java, PostgreSQL ni otras herramientas.

## Descripción

Diplomatic Fleet permite gestionar el proceso previo al alquiler o servicio vehicular institucional:

1. Registro del cliente institucional.
2. Consulta y registro de vehículos.
3. Control documental.
4. Programación de mantenimientos.
5. Consulta de disponibilidad.
6. Creación de solicitudes.
7. Selección de modalidad con o sin conductor.
8. Validación del conductor autorizado.
9. Elaboración de cotizaciones.
10. Gestión demostrativa de usuarios y roles.

El término “diplomático” hace referencia al público institucional al que se dirige el servicio. No significa que todos los vehículos tengan placas diplomáticas.

## Alcance de la demo

La versión pública representa las historias de usuario US01 a US10:

- **US01:** autenticación demostrativa.
- **US02:** gestión de usuarios y roles.
- **US03:** clientes institucionales.
- **US04:** gestión de vehículos.
- **US05:** documentación vehicular.
- **US06:** mantenimientos.
- **US07:** consulta de disponibilidad.
- **US08:** solicitudes de servicio.
- **US09:** validación del conductor autorizado.
- **US10:** cotizaciones.

### Limitaciones actuales

- La demo pública almacena los datos en `localStorage`.
- Los datos no se comparten entre dispositivos.
- La autenticación pública es demostrativa.
- El backend y la base de datos requieren ejecución local.
- Algunas operaciones corresponden a un prototipo funcional y continuarán desarrollándose.

## Tecnologías

### Backend

- Kotlin
- Spring Boot
- Spring Security
- Spring Data JPA
- Flyway
- Gradle

### Base de datos

- PostgreSQL

### Interfaz de demostración

- HTML5
- CSS3
- JavaScript
- LocalStorage
- GitHub Pages

## Arquitectura

El backend utiliza una arquitectura organizada por capas:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Entity
    ↓
PostgreSQL
