# MediTurn – Citas Médicas (Kotlin + Jetpack Compose)

Enlace Figma: https://www.figma.com/design/t7GNh0kH7693i8cWhxNjp1/Clinica?node-id=0-1&t=UV1w8Kc1qvN9r9IG-1

## Descripción
MediTurn es una app móvil que permite a pacientes buscar médicos por especialidad y ciudad, visualizar disponibilidad y reservar citas médicas (presencial o teleconsulta). Incluye flujo de inicio de sesión simulado, listado y detalle de médicos, agenda de citas y perfil básico del paciente.

## Público objetivo y alcance
- Pacientes que necesitan agendar consultas médicas de forma rápida y ordenada.
- Alcance v1.0: datos locales simulados (sin backend), búsqueda/filtrado, reserva/reprogramación/cancelación de citas, navegación completa.

## Roles del equipo
- Líder técnico: [Nombre Apellido]
- Diseñador(a) UI: [Nombre Apellido]
- Tester/Documentación: [Nombre Apellido]

## Arquitectura y navegación
Arquitectura por capas simple:
- ui: pantallas Compose y componentes.
- navigation: rutas y NavHost.
- data: datasource simulado y repositorios.
- model: data classes (Doctor, Slot, Appointment, Patient).
- util: constantes.

Diagrama (simplificado):

```
MainActivity
  └── AppNavigation (NavHost)
      ├── Splash → Login → Home
      ├── Home → DoctorsList → DoctorDetail → NewAppointment
      ├── Home → Appointments
      └── Home → Profile
```

## Tecnologías
- Kotlin, Jetpack Compose, Material 3, Navigation Compose.
- Datos simulados en memoria (sin Room/Retrofit en v1.0).

## Cómo ejecutar
1. Abrir el proyecto en Android Studio Iguana+.
2. Sincronizar Gradle y ejecutar en un emulador (API 24+). Target SDK: 36.
3. Flujo recomendado: Splash → Login → Home → Doctors → Doctor Detail → New Appointment → Appointments.

## Capturas (a subir en docs/screenshots/)
- Home
- Listado de Médicos
- Detalle de Médico (con horarios)
- Agendar Cita (validaciones y pickers)
- Mis Citas (estado lleno y/o vacío)
- Perfil

Consulta la guía: docs/screenshots/README.md

## Historias de usuario
🩺 HU01 – Buscar médicos por ciudad o tipo de atención  
Como paciente, quiero buscar médicos por ciudad o tipo de atención (presencial o virtual) para encontrar un profesional que se adapte a mi necesidad.  
Prioridad: Alta. Criterio de aceptación: El buscador muestra médicos que coinciden con los filtros seleccionados.

💊 HU02 – Explorar categorías médicas populares  
Como paciente, quiero explorar categorías o ramas médicas más consultadas para acceder fácilmente sin necesidad de escribir.  
Prioridad: Media. Criterio de aceptación: Se muestran tarjetas con íconos y nombres de categorías destacadas.

👨‍⚕ HU03 – Consultar el perfil completo de un médico  
Como paciente, quiero ver la información completa de un médico (nombre, experiencia, atención, costo, valoración) para evaluar si es el profesional adecuado para mí.  
Prioridad: Alta. Criterio de aceptación: Al seleccionar un médico, se muestra su perfil con todos los datos disponibles.

📅 HU04 – Reservar una cita médica  
Como paciente, quiero elegir fecha, hora y modalidad de consulta para agendar una cita en el horario que me convenga.  
Prioridad: Alta. Criterio de aceptación: El sistema valida los datos y confirma la reserva correctamente.

🕒 HU05 – Gestionar mis citas próximas  
Como paciente, quiero ver mis citas agendadas y poder cancelarlas o reprogramarlas para tener control sobre mis próximas atenciones.  
Prioridad: Alta. Criterio de aceptación: Las citas próximas muestran acciones disponibles.

📋 HU06 – Revisar mi historial de citas anteriores  
Como paciente, quiero consultar mis citas pasadas con fecha, médico y diagnóstico para llevar un seguimiento de mis consultas anteriores.  
Prioridad: Media. Criterio de aceptación: Se listan las citas finalizadas.

👤 HU07 – Actualizar mis datos personales y de contacto  
Como paciente, quiero modificar mi información personal (nombre, correo, dirección, grupo sanguíneo) para mantener mis datos actualizados.  
Prioridad: Media. Criterio de aceptación: La edición se guarda y se refleja en el perfil.

💬 HU08 – Ver consejos médicos y alertas de salud  
Como paciente, quiero ver consejos de bienestar y alertas de prevención para cuidar mi salud y mantener buenos hábitos.  
Prioridad: Baja. Criterio de aceptación: En Home aparece un bloque con un consejo o alerta diaria.

## Notas de la versión v1.0.0
- Navegación completa y estable (Splash, Login, Home, Doctors, Detail, New Appointment, Appointments, Profile).
- Búsqueda y filtros (especialidad, ciudad, teleconsulta).
- Reserva de citas, reprogramación y cancelación (datos simulados). 
- Material 3 aplicado.

Limitaciones: sin backend real, perfil estático, sin autenticación real, calendario mensual no implementado.

## Roadmap (futuro)
- Persistencia local (Room/SQLite) y/o backend (Ktor/Retrofit).
- Autenticación real y perfil editable.
- Calendario mensual y recordatorios (WorkManager/AlarmManager).
- Modo offline y sincronización.

## Enlaces
- Figma: https://www.figma.com/design/t7GNh0kH7693i8cWhxNjp1/Clinica?node-id=0-1&t=UV1w8Kc1qvN9r9IG-1
- GitHub: [agregar URL del repositorio]

## Créditos
Docente: Juan León. Curso: Aplicaciones Móviles con Android. Equipo: [Nombres].