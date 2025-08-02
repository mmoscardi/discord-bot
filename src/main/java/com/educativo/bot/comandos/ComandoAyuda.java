package com.educativo.bot.comandos;

/**
 * CONCEPTO: HERENCIA (Clase Hija)
 * 
 * Esta clase HEREDA de ComandoBase, lo que significa que:
 * 1. Obtiene automáticamente todos los atributos y métodos de ComandoBase
 * 2. Puede usar métodos protegidos de la clase padre
 * 3. Debe implementar métodos abstractos de la clase padre
 * 4. Puede sobrescribir métodos de la clase padre (polimorfismo)
 * 
 * ComandoAyuda proporciona información sobre todos los comandos disponibles.
 */
public class ComandoAyuda extends ComandoBase {
    
    // Atributo específico de esta clase hija
    private final String[] comandosDisponibles;
    
    /**
     * Constructor de la clase hija
     * Llama al constructor de la clase padre usando 'super'
     */
    public ComandoAyuda() {
        // HERENCIA: Llamada al constructor de la clase padre (ComandoBase)
        super(
            "ayuda",                                    // nombre
            "Muestra información sobre los comandos disponibles", // descripcion
            "!ayuda [comando específico]",              // uso
            false                                       // no requiere permisos
        );
        
        // Inicialización específica de esta clase
        this.comandosDisponibles = new String[]{
            "ayuda", "tarea", "puntos", "materia", "bienvenida"
        };
    }
    
    /**
     * IMPLEMENTACIÓN del método abstracto heredado
     * 
     * Este método DEBE ser implementado porque es abstracto en la clase padre.
     * Aquí se define el comportamiento específico del comando ayuda.
     */
    @Override
    public String ejecutar(String[] args, String canalId, String usuarioId) {
        // Usar método heredado para validar argumentos
        if (!validarArgumentos(args, 0)) {
            return formatearError("Error interno en argumentos");
        }
        
        // Si no se especifica comando, mostrar ayuda general
        if (args.length == 0) {
            return generarAyudaGeneral();
        }
        
        // Si se especifica un comando, mostrar ayuda específica
        String comandoBuscado = args[0].toLowerCase();
        return generarAyudaEspecifica(comandoBuscado);
    }
    
    /**
     * Método específico de esta clase para generar ayuda general
     * Utiliza métodos heredados para formatear la respuesta
     * 
     * @return Mensaje con ayuda general
     */
    private String generarAyudaGeneral() {
        StringBuilder ayuda = new StringBuilder();
        
        ayuda.append("🤖 **Bot Educativo - Comandos Disponibles**\n\n");
        
        // Información sobre cada comando
        ayuda.append("📚 **!ayuda** - Muestra esta ayuda\n");
        ayuda.append("   Uso: `!ayuda` o `!ayuda [comando]`\n\n");
        
        ayuda.append("📝 **!tarea** - Gestiona tareas de estudio\n");
        ayuda.append("   Uso: `!tarea crear [título] [descripción] [materia]`\n");
        ayuda.append("   Uso: `!tarea listar [materia]`\n");
        ayuda.append("   Uso: `!tarea completar [id]`\n\n");
        
        ayuda.append("🏆 **!puntos** - Muestra tu progreso y puntos\n");
        ayuda.append("   Uso: `!puntos` o `!puntos [@usuario]`\n\n");
        
        ayuda.append("📊 **!ranking** - Muestra el ranking de usuarios\n");
        ayuda.append("   Uso: `!ranking [materia]`\n\n");
        
        ayuda.append("📖 **!materia** - Información sobre materias\n");
        ayuda.append("   Uso: `!materia info [nombre]`\n");
        ayuda.append("   Uso: `!materia listar`\n\n");
        
        ayuda.append("⏰ **!recordatorio** - Crea recordatorios de estudio\n");
        ayuda.append("   Uso: `!recordatorio [tiempo] [mensaje]`\n\n");
        
        ayuda.append("💡 **Tip**: Usa `!ayuda [comando]` para obtener ayuda específica de un comando.");
        
        // Usar método heredado para formatear como información
        return formatearInfo(ayuda.toString());
    }
    
    /**
     * Método específico para generar ayuda de un comando específico
     * 
     * @param comando Nombre del comando
     * @return Mensaje con ayuda específica
     */
    private String generarAyudaEspecifica(String comando) {
        StringBuilder ayuda = new StringBuilder();
        
        switch (comando) {
            case "tarea":
                ayuda.append("📝 **Comando !tarea** - Gestión de tareas de estudio\n\n");
                ayuda.append("**Crear tarea:**\n");
                ayuda.append("`!tarea crear [título] | [descripción] | [materia]`\n");
                ayuda.append("Ejemplo: `!tarea crear Estudiar POO | Repasar herencia y polimorfismo | Programación`\n\n");
                ayuda.append("**Listar tareas:**\n");
                ayuda.append("`!tarea listar` - Todas las tareas\n");
                ayuda.append("`!tarea listar [materia]` - Tareas de una materia\n\n");
                ayuda.append("**Completar tarea:**\n");
                ayuda.append("`!tarea completar [id]` - Marca una tarea como completada\n");
                break;
                
            case "puntos":
                ayuda.append("🏆 **Comando !puntos** - Sistema de puntuación\n\n");
                ayuda.append("**Ver tus puntos:**\n");
                ayuda.append("`!puntos` - Muestra tu progreso personal\n\n");
                ayuda.append("**Ver puntos de otro usuario:**\n");
                ayuda.append("`!puntos [@usuario]` - Muestra el progreso de otro usuario\n\n");
                ayuda.append("**¿Cómo ganar puntos?**\n");
                ayuda.append("• Completar tareas: +10 puntos\n");
                ayuda.append("• Ayudar a otros: +5 puntos\n");
                ayuda.append("• Participar en discusiones: +2 puntos\n");
                break;
                
            case "ranking":
                ayuda.append("📊 **Comando !ranking** - Clasificaciones\n\n");
                ayuda.append("**Ranking general:**\n");
                ayuda.append("`!ranking` - Top 10 usuarios por puntos totales\n\n");
                ayuda.append("**Ranking por materia:**\n");
                ayuda.append("`!ranking [materia]` - Top 10 en una materia específica\n");
                ayuda.append("Ejemplo: `!ranking Matemáticas`\n");
                break;
                
            default:
                return formatearError("Comando '" + comando + "' no encontrado. Usa `!ayuda` para ver todos los comandos.");
        }
        
        return formatearInfo(ayuda.toString());
    }
    
    /**
     * SOBRESCRITURA (Polimorfismo por sobrescritura)
     * 
     * Sobrescribimos el método toString() de la clase padre para proporcionar
     * información más específica sobre este comando.
     */
    @Override
    public String toString() {
        return String.format("ComandoAyuda{nombre='%s', comandosDisponibles=%d}", 
                           getNombre(), comandosDisponibles.length);
    }
}
