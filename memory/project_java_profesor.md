---
name: Agente Profesor de Java
description: Sistema de wiki pedagógica para clases de Java, basado en el patrón LLM Wiki
type: project
---

El usuario es profesor de Java y tiene alumnos. Se construyó un sistema de agente profesor basado en el patrón LLM Wiki.

**Why:** El usuario quiere explicar mejor los conceptos de programación Java a sus alumnos usando los materiales del repositorio como fuente primaria.

**How to apply:** Cuando el usuario pida explicar conceptos, generar ejercicios o revisar código de alumnos, usar CLAUDE.md como guía de comportamiento y actualizar/crear páginas en la wiki de Obsidian.

## Archivos clave

- `c:\Users\whutc\Desktop\GitHub\JavaClases\CLAUDE.md` — esquema completo del agente (cargado automáticamente)
- `c:\Users\whutc\Desktop\BovedaObsidian\JavaClass\index.md` — índice maestro de la wiki
- `c:\Users\whutc\Desktop\BovedaObsidian\JavaClass\log.md` — registro de actividad
- `c:\Users\whutc\Desktop\BovedaObsidian\JavaClass\conceptos\` — páginas de conceptos
- `c:\Users\whutc\Desktop\BovedaObsidian\JavaClass\proyectos\` — walkthrough de proyectos

## Repositorio de fuentes

- 67 archivos Java en `Java/src/` organizados en 11 paquetes-proyecto
- 15 documentos de teoría en `Java/src/Teoria/` (01 al 13 + extras)
- Proyectos por complejidad: Animales (baja) → GestorDietas (alta)

## Wiki inicializada con

- Conceptos: tipos de datos, clases y objetos, herencia, excepciones (completos); interfaces, colecciones (stubs)
- Proyectos: Animales y GestorDietas (con preguntas pedagógicas para alumnos)
