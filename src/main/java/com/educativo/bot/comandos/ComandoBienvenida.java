package com.educativo.bot.comandos;

public class ComandoBienvenida extends ComandoBase {
    
    /**
     * Constructor que define el comando de bienvenida
     */
    public ComandoBienvenida() {
        super(
            "bienvenida",
            "Envía un mensaje de bienvenida al usuario",
            "!bienvenida [@usuario]",
            false
        );
    }
    
    /**
     * Ejecuta el comando de bienvenida
     * 
     * @param args Argumentos del comando
     * @param canalId ID del canal donde se ejecuta el comando
     * @param usuarioId ID del usuario que ejecuta el comando
     * @return Mensaje de bienvenida
     */
    @Override
    public String ejecutar(String[] args, String canalId, String usuarioId) {
        String usuarioObjetivo = usuarioId; // Por defecto, el usuario que ejecuta el comando
        
        // Si se menciona otro usuario
        if (validarArgumentos(args, 1)) {
            String mencion = args[0];
            if (mencion.startsWith("<@") && mencion.endsWith(">")) {
                // Extraer ID del usuario mencionado
                usuarioObjetivo = mencion.substring(2, mencion.length() - 1);
                if (usuarioObjetivo.startsWith("!")) {
                    usuarioObjetivo = usuarioObjetivo.substring(1);
                }
            } else {
                return formatearError("Formato de mención incorrecto. Usa @usuario");
            }
        }
        
        return "¡Bienvenido/a <@" + usuarioObjetivo + ">! Estamos felices de tenerte aquí.";
    }
    
    /**
     * Crea un mensaje de bienvenida completo con lista de comandos disponibles
     * Para uso automático cuando un nuevo usuario se une al servidor
     * 
     * @param usuarioId ID del usuario que se acaba de unir
     * @return Mensaje completo de bienvenida
     */
    public String crearMensajeBienvenidaCompleto(String usuarioId) {
        StringBuilder mensaje = new StringBuilder();
        
        // Mensaje de bienvenida principal
        mensaje.append("🎉 **¡Bienvenido/a al servidor educativo!** 🎉\n\n");
        mensaje.append("Hola <@").append(usuarioId).append(">, estamos muy felices de tenerte aquí. ");
        mensaje.append("Este servidor está diseñado para ayudarte a organizar tus estudios y aprender conceptos de programación.\n\n");
        
        // Lista de comandos disponibles
        mensaje.append("📚 **COMANDOS DISPONIBLES:**\n\n");
        
        // Comandos de materias
        mensaje.append("**📖 Gestión de Materias:**\n");
        mensaje.append("`!materia crear CODIGO \"Nombre\" [\"Descripción\"] [\"Profesor\"]` - Crear nueva materia\n");
        mensaje.append("`!materia listar [activas|archivadas]` - Listar materias\n");
        mensaje.append("`!materia tareas CODIGO` - Ver tareas de una materia\n");
        mensaje.append("`!materia eliminar CODIGO` - Eliminar materia\n\n");
        
        // Comandos de tareas
        mensaje.append("**📝 Gestión de Tareas:**\n");
        mensaje.append("`!tarea crear \"Título\" [\"Descripción\"] [MATERIA]` - Crear nueva tarea\n");
        mensaje.append("`!tarea listar [pendientes|completadas]` - Listar tareas\n");
        mensaje.append("`!tarea completar NUMERO` - Completar tarea\n\n");
        
        // Sistema de puntos
        mensaje.append("**🏆 Sistema de Progreso:**\n");
        mensaje.append("`!puntos` - Ver tu progreso personal\n");
        mensaje.append("`!puntos @usuario` - Ver progreso de otro usuario\n\n");
        
        // Ayuda
        mensaje.append("**❓ Ayuda:**\n");
        mensaje.append("`!ayuda` - Ver ayuda general\n");
        mensaje.append("`!ayuda [comando]` - Ayuda específica de un comando\n\n");
        
        // Información adicional
        mensaje.append("💡 **¡Consejos para empezar!**\n");
        mensaje.append("1. Prueba crear tu primera materia: `!materia crear MAT101 \"Matemáticas\"`\n");
        mensaje.append("2. Agrega una tarea: `!tarea crear \"Estudiar capítulo 1\" \"\" MAT101`\n");
        mensaje.append("3. Ve tu progreso: `!puntos`\n\n");
        
        mensaje.append("🎓 **¡Que tengas un excelente aprendizaje!**");
        
        return mensaje.toString();
    }
}