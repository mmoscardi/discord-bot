# 📖 G## 🤖 Funcionalidades Automáticas

### **👋 Bienvenida Automática**

El bot detecta automáticamente cuando un nuevo usuario se une al servidor y:

1. **🎯 Busca el canal adecuado**:
   - Prioriza el canal `#bienvenida` si existe
   - Si no existe, usa el canal `#general`

2. **📝 Envía mensaje completo**:
   - Saludo personalizado mencionando al usuario
   - Lista completa de todos los comandos disponibles
   - Consejos para comenzar a usar el bot
   - Información sobre el propósito educativo

3. **📊 Registro en consola**:
   - Confirma cuando un usuario se une
   - Registra si el mensaje fue enviado exitosamente

**Ejemplo de mensaje automático:**
```
🎉 ¡Bienvenido/a al servidor educativo! 🎉

Hola @NuevoUsuario, estamos muy felices de tenerte aquí.
Este servidor está diseñado para ayudarte a organizar tus estudios...

📚 COMANDOS DISPONIBLES:
[Lista completa de comandos con ejemplos]

💡 ¡Consejos para empezar!
1. Prueba crear tu primera materia...
```

## 📝 Comandos Manualesía de Uso - Bot Educativo Discord

## 🎯 Introducción

El Bot Educativo está diseñado para ayudar a estudiantes y profesores a gestionar tareas, materias y seguimiento de progreso académico en Discord.

## 🚀 Comandos Disponibles

### **📚 Gestión de Materias**

#### **Crear Materia**
```
!materia crear CODIGO "Nombre" ["Descripción"] ["Profesor"]
```

**Ejemplos:**
```
!materia crear MAT101 "Matemáticas Básicas"
!materia crear FIS201 "Física II" "Mecánica y termodinámica" "Dr. García"
!materia crear PROG "Programación" "" "Prof. López"
```

**Parámetros:**
- `CODIGO`: Identificador único (sin espacios)
- `"Nombre"`: Nombre completo (entre comillas si tiene espacios)
- `"Descripción"`: Opcional, descripción de la materia
- `"Profesor"`: Opcional, nombre del docente

#### **Listar Materias**
```
!materia listar [filtro]
```

**Ejemplos:**
```
!materia listar           # Todas las materias
!materia listar activas   # Solo materias activas
!materia listar archivadas # Solo materias archivadas
```

#### **Ver Tareas de Materia**
```
!materia tareas CODIGO [filtro]
```

**Ejemplos:**
```
!materia tareas MAT101              # Todas las tareas
!materia tareas PROG pendientes     # Solo pendientes
!materia tareas FIS201 completadas  # Solo completadas
```

#### **Eliminar Materia**
```
!materia eliminar CODIGO
```

**Ejemplo:**
```
!materia eliminar TEST
```

⚠️ **Nota**: Solo el creador puede eliminar una materia, y no debe tener tareas asociadas.

---

### **📝 Gestión de Tareas**

#### **Crear Tarea**
```
!tarea crear "Título" ["Descripción"] [MATERIA]
```

**Ejemplos:**
```
!tarea crear "Estudiar capítulo 5"
!tarea crear "Resolver ejercicios" "Problemas 1-20 del libro" MAT101
!tarea crear "Preparar examen" "" FIS201
```

**Parámetros:**
- `"Título"`: Título de la tarea (obligatorio, entre comillas)
- `"Descripción"`: Opcional, descripción detallada
- `MATERIA`: Opcional, código de materia (sin comillas)

#### **Listar Tareas**
```
!tarea listar [filtro]
```

**Ejemplos:**
```
!tarea listar             # Todas las tareas
!tarea listar pendientes  # Solo pendientes
!tarea listar completadas # Solo completadas
```

#### **Completar Tarea**
```
!tarea completar NUMERO
```

**Ejemplo:**
```
!tarea completar 3        # Completa la tarea número 3
```

**Recompensas:**
- 🏆 **Puntos base**: 10 puntos por tarea
- 🔥 **Bonus prioridad**: +5 puntos por nivel de prioridad
- 📈 **Progreso**: Contribuye al nivel del usuario

---

### **🏆 Sistema de Puntos**

#### **Ver Puntos Propios**
```
!puntos
```

**Respuesta típica:**
```
🏆 Tu Progreso Académico

👤 Usuario: @TuNombre
🎯 Nivel: 3 (245/300 puntos)
📊 Progreso: ████████░░ 82%

📚 Estadísticas:
• Tareas completadas: 24
• Materias activas: 3
• Racha actual: 7 días

🥇 Materia favorita: Programación (120 puntos)

💡 Consejos Personalizados:
• ¡Vas bien! Completa más tareas para subir de nivel
• Prueba ayudar a otros estudiantes para ganar puntos extra
```

#### **Ver Puntos de Otro Usuario**
```
!puntos @usuario
```

**Ejemplo:**
```
!puntos @Juan
```

---

### **❓ Ayuda y Soporte**

#### **Ayuda General**
```
!ayuda
```

#### **Ayuda Específica**
```
!ayuda [comando]
```

**Ejemplos:**
```
!ayuda materia
!ayuda tarea
!ayuda puntos
```

---

## 📋 Casos de Uso Prácticos

### **Escenario 1: Profesor Configurando Materias**

```
# 1. Crear materias del semestre
!materia crear MAT101 "Cálculo I" "Límites y derivadas" "Prof. Martínez"
!materia crear FIS201 "Física II" "Electromagnetismo" "Dr. García"
!materia crear PROG "Programación" "Java y POO" "Prof. López"

# 2. Verificar materias creadas
!materia listar

# 3. Informar a estudiantes
Los estudiantes pueden crear tareas con:
!tarea crear "Mi tarea" "Descripción" MAT101
```

### **Escenario 2: Estudiante Organizándose**

```
# 1. Ver materias disponibles
!materia listar

# 2. Crear tareas para la semana
!tarea crear "Estudiar límites" "Capítulo 3, ejemplos 1-15" MAT101
!tarea crear "Laboratorio física" "Experimento de ondas" FIS201
!tarea crear "Proyecto POO" "Implementar patrón Observer" PROG

# 3. Ver todas las tareas
!tarea listar

# 4. Completar tarea terminada
!tarea completar 1

# 5. Ver progreso
!puntos
```

### **Escenario 3: Seguimiento de Progreso**

```
# 1. Profesor verifica actividad de estudiantes
!materia tareas MAT101

# 2. Estudiante compara su progreso
!puntos @companero

# 3. Ver tareas pendientes del grupo
!tarea listar pendientes
```

---

## 🎨 Formato de Respuestas

### **Mensajes de Éxito**
```
✅ Materia creada exitosamente

• Código: MAT101
• Nombre: Matemáticas Básicas
• Descripción: Álgebra y geometría
• Profesor: Prof. Martínez
```

### **Listas de Elementos**
```
📚 Materias (3):

1. MAT101 - Matemáticas Básicas (5 tareas)
2. FIS201 - Física II (3 tareas)
3. PROG - Programación (8 tareas)
```

### **Mensajes de Error**
```
❌ Error: Ya existe una materia con código: MAT101

💡 Usa !materia listar para ver materias disponibles
```

---

## ⚡ Consejos de Uso

### **Mejores Prácticas**

#### **Para Profesores:**
- 📝 **Códigos claros**: Usa códigos descriptivos (MAT101, FIS201)
- 📚 **Descripciones útiles**: Incluye información relevante del curso
- 🎯 **Organización**: Crea materias al inicio del semestre
- 📊 **Seguimiento**: Revisa regularmente las tareas de los estudiantes

#### **Para Estudiantes:**
- 🎯 **Títulos descriptivos**: "Estudiar capítulo 5" mejor que "Estudiar"
- 📝 **Usa descripciones**: Incluye detalles importantes
- 🏷️ **Asocia a materias**: Siempre especifica la materia
- ✅ **Marca completadas**: No olvides completar tareas terminadas
- 📈 **Revisa progreso**: Usa `!puntos` regularmente

### **Comandos Útiles del Día a Día**

```bash
# Rutina matutina del estudiante
!tarea listar pendientes    # Ver qué hay que hacer hoy

# Al terminar una tarea
!tarea completar 2          # Marcar como completada

# Rutina nocturna
!puntos                     # Ver progreso del día
!tarea crear "..." ...      # Planificar tareas del siguiente día

# Cada semana
!materia listar             # Revisar todas las materias
!tarea listar completadas   # Ver logros de la semana
```

---

## 🔧 Personalización

### **Modificar Prefijo de Comandos**
Por defecto, los comandos usan `!`. Si quieres cambiarlo:

1. Modifica el archivo `.env`:
```env
BOT_PREFIX=?
```

2. Reinicia el bot

3. Ahora usa: `?materia crear ...`

### **Para Profesores: Configuraciones Recomendadas**

#### **Canales Sugeridos**
- `#tareas-matematicas` - Para MAT101
- `#tareas-fisica` - Para FIS201
- `#tareas-programacion` - Para PROG
- `#progreso-general` - Para comando `!puntos`

#### **Roles y Permisos**
- **Profesores**: Pueden eliminar cualquier materia/tarea
- **Estudiantes**: Solo pueden gestionar sus propias tareas
- **Ayudantes**: Pueden ver progreso de todos los estudiantes

---

## 🎯 Objetivos Pedagógicos

### **Para Estudiantes**
- 📝 **Organización**: Gestionar tareas de múltiples materias
- 🎯 **Responsabilidad**: Seguimiento propio del progreso
- 🏆 **Motivación**: Sistema de puntos y niveles
- 👥 **Colaboración**: Visibilidad del progreso grupal

### **Para Profesores**
- 📊 **Seguimiento**: Monitorear actividad de estudiantes
- 🎯 **Engagement**: Gamificación del aprendizaje
- 📚 **Organización**: Estructura clara de materias
- 💬 **Comunicación**: Canal directo con estudiantes

---

## ❓ Preguntas Frecuentes

### **¿Puedo editar una tarea ya creada?**
Actualmente no. Debes completar la tarea actual y crear una nueva si necesitas cambios.

### **¿Los puntos se pierden?**
No, los puntos son acumulativos y permanentes.

### **¿Puedo ver tareas de otros estudiantes?**
Solo puedes ver el progreso general, no tareas específicas de otros usuarios.

### **¿Qué pasa si elimino una materia?**
Se eliminan todas las tareas asociadas. Esta acción no se puede deshacer.

### **¿Hay límite de materias o tareas?**
No hay límites técnicos, pero se recomienda mantener una organización clara.

---

**🎓 ¡Disfruta gestionando tu progreso académico con el Bot Educativo!**

Para más información técnica, consulta la [Estructura del Proyecto](ESTRUCTURA.md).
