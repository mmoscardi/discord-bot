package com.educativo.bot.comandos;

import java.util.List;

import com.educativo.bot.modelos.Usuario;

/**
 * CONCEPTO: POLIMORFISMO
 * 
 * Esta clase demuestra polimorfismo de varias formas:
 * 1. Herencia y sobrescritura de métodos
 * 2. Implementación de interfaz
 * 3. Uso polimórfico de objetos (tratando objetos de diferentes clases de manera uniforme)
 * 
 * ComandoPuntos permite ver el progreso y puntuación de los usuarios.
 */
public class ComandoPuntos extends ComandoBase {
    
    // Lista estática compartida con otros comandos
    private static final List<Usuario> usuarios = ComandoTarea.getUsuarios(); // Referencia a la misma lista
    
    /**
     * Constructor que demuestra herencia
     */
    public ComandoPuntos() {
        super(
            "puntos",
            "Muestra el progreso y puntuación de un usuario",
            "!puntos [@usuario] o !puntos",
            false
        );
    }
    
    /**
     * POLIMORFISMO por implementación
     * 
     * Implementa el método abstracto de ComandoBase.
     * Cada clase hija puede tener una implementación completamente diferente.
     */
    @Override
    public String ejecutar(String[] args, String canalId, String usuarioId) {
        // Determinar de qué usuario mostrar los puntos
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
        
        // Buscar el usuario
        Usuario usuario = buscarUsuario(usuarioObjetivo);
        
        if (usuario == null) {
            // Si es el mismo usuario, crearlo
            if (usuarioObjetivo.equals(usuarioId)) {
                usuario = new Usuario(usuarioId, "Usuario_" + usuarioId.substring(0, 6));
                usuarios.add(usuario);
            } else {
                return formatearError("Usuario no encontrado o no ha usado el bot");
            }
        }
        
        // Generar respuesta usando métodos del modelo Usuario
        return generarResumenUsuario(usuario, usuarioObjetivo.equals(usuarioId));
    }
    
    /**
     * POLIMORFISMO en acción: Método que puede trabajar con diferentes tipos de usuarios
     * pero los trata de manera uniforme a través de la interfaz común (clase Usuario)
     * 
     * @param usuario Usuario del que generar el resumen
     * @param esUsuarioActual Si es el usuario que ejecutó el comando
     * @return Resumen formateado
     */
    private String generarResumenUsuario(Usuario usuario, boolean esUsuarioActual) {
        StringBuilder resumen = new StringBuilder();
        
        // Título personalizado según el tipo de consulta
        if (esUsuarioActual) {
            resumen.append("📊 **Tu Progreso Actual**\n\n");
        } else {
            resumen.append("📊 **Progreso de ").append(usuario.getNombre()).append("**\n\n");
        }
        
        // Usar el método del modelo Usuario (encapsulamiento)
        resumen.append(usuario.getResumenProgreso());
        
        // Añadir información adicional usando polimorfismo
        resumen.append("\n\n").append(generarEstadisticasDetalladas(usuario));
        
        // Añadir consejos personalizados
        resumen.append("\n\n").append(generarConsejosPersonalizados(usuario));
        
        return formatearInfo(resumen.toString());
    }
    
    /**
     * POLIMORFISMO: Este método trata a todos los usuarios de la misma manera,
     * pero cada usuario puede tener datos diferentes que influyen en el resultado
     * 
     * @param usuario Usuario para generar estadísticas
     * @return Estadísticas detalladas
     */
    private String generarEstadisticasDetalladas(Usuario usuario) {
        StringBuilder stats = new StringBuilder();
        stats.append("📈 **Estadísticas Detalladas:**\n");
        
        // Usar métodos polimórficos del usuario
        var puntosPorMateria = usuario.getPuntosPorMateriaMap();
        
        if (puntosPorMateria.isEmpty()) {
            stats.append("• Sin actividad por materias registrada");
        } else {
            stats.append("• **Distribución por materias:**\n");
            for (var entrada : puntosPorMateria.entrySet()) {
                String materia = entrada.getKey();
                int puntos = entrada.getValue();
                double porcentaje = (double) puntos / usuario.getPuntos() * 100;
                
                stats.append("  - ").append(materia).append(": ");
                stats.append(puntos).append(" pts (").append(String.format("%.1f", porcentaje)).append("%)\n");
            }
        }
        
        // Calcular estadísticas adicionales
        stats.append("• **Tiempo en el sistema:** ");
        stats.append(calcularTiempoEnSistema(usuario));
        
        return stats.toString();
    }
    
    /**
     * POLIMORFISMO: Genera consejos personalizados basados en el estado del usuario
     * Cada usuario puede recibir consejos diferentes según su progreso
     * 
     * @param usuario Usuario para generar consejos
     * @return Consejos personalizados
     */
    private String generarConsejosPersonalizados(Usuario usuario) {
        StringBuilder consejos = new StringBuilder();
        consejos.append("💡 **Consejos Personalizados:**\n");
        
        // Consejos basados en el nivel
        if (usuario.getNivel() == 1) {
            consejos.append("• ¡Bienvenido! Crea tu primera tarea para ganar puntos\n");
            consejos.append("• Participa en los canales de materias para ganar experiencia");
        } else if (usuario.getNivel() < 5) {
            consejos.append("• ¡Vas bien! Completa más tareas para subir de nivel\n");
            consejos.append("• Prueba ayudar a otros estudiantes para ganar puntos extra");
        } else if (usuario.getNivel() < 10) {
            consejos.append("• ¡Excelente progreso! Considera especializarte en tu materia favorita\n");
            consejos.append("• Comparte recursos útiles con la comunidad");
        } else {
            consejos.append("• ¡Eres un veterano! Considera convertirte en mentor\n");
            consejos.append("• Tu experiencia es valiosa para nuevos estudiantes");
        }
        
        // Consejos basados en actividad
        if (!usuario.estaActivo()) {
            consejos.append("\n• Te hemos extrañado, ¡vuelve pronto!");
        }
        
        return consejos.toString();
    }
    
    /**
     * Método auxiliar para buscar un usuario
     * Demuestra encapsulamiento al abstraer la lógica de búsqueda
     * 
     * @param usuarioId ID del usuario a buscar
     * @return Usuario encontrado o null
     */
    private Usuario buscarUsuario(String usuarioId) {
        // POLIMORFISMO: Tratamos todos los usuarios de manera uniforme
        // sin importar sus características específicas
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(usuarioId)) {
                return usuario;
            }
        }
        return null;
    }
    
    /**
     * Calcula el tiempo que el usuario lleva en el sistema
     * 
     * @param usuario Usuario del que calcular el tiempo
     * @return Texto descriptivo del tiempo en el sistema
     */
    private String calcularTiempoEnSistema(Usuario usuario) {
        var fechaRegistro = usuario.getFechaRegistro();
        var ahora = java.time.LocalDateTime.now();
        var duracion = java.time.Duration.between(fechaRegistro, ahora);
        
        long dias = duracion.toDays();
        if (dias == 0) {
            return "Menos de un día";
        } else if (dias == 1) {
            return "1 día";
        } else {
            return dias + " días";
        }
    }
    
    /**
     * POLIMORFISMO por sobrescritura
     * Personaliza la representación string de este comando específico
     */
    @Override
    public String toString() {
        return String.format("ComandoPuntos{nombre='%s', usuariosRegistrados=%d}", 
                           getNombre(), usuarios.size());
    }
}
