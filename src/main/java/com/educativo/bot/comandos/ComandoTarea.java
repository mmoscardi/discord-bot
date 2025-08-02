package com.educativo.bot.comandos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.educativo.bot.modelos.Tarea;
import com.educativo.bot.modelos.Usuario;
import com.educativo.bot.utils.FormateadorMensajes;

/**
 * CONCEPTO: HERENCIA y POLIMORFISMO
 * 
 * Esta clase HEREDA de ComandoBase y demuestra:
 * 1. Herencia: Obtiene funcionalidad de la clase padre
 * 2. Polimorfismo por sobrescritura: Redefine métodos de la clase padre
 * 3. Polimorfismo por implementación: Implementa métodos abstractos
 * 
 * ComandoTarea permite gestionar tareas de estudio de los usuarios.
 */
public class ComandoTarea extends ComandoBase {
    
    // Simulación de almacenamiento en memoria (en una app real sería una base de datos)
    private static final List<Tarea> tareas = new ArrayList<>();
    private static final List<Usuario> usuarios = new ArrayList<>();
    
    /**
     * Constructor de la clase hija
     * Demuestra herencia al llamar al constructor padre con 'super'
     */
    public ComandoTarea() {
        // HERENCIA: Llamada al constructor de ComandoBase
        super(
            "tarea",
            "Permite crear, listar y gestionar tareas de estudio",
            "!tarea [crear|listar|completar] [parámetros]\n\n" +
            "**CREAR TAREA:**\n" +
            "• `!tarea crear \"<título>\" [\"descripción\"] [materia]`\n" +
            "• Ejemplo: `!tarea crear \"Estudiar capítulo 5\" \"Revisar ejemplos\" MAT101`\n" +
            "• Título: Obligatorio, entre comillas si tiene espacios\n" +
            "• Descripción: Opcional, entre comillas si tiene espacios\n" +
            "• Materia: Opcional, código de materia (sin comillas)\n\n" +
            "**OTROS COMANDOS:**\n" +
            "• `!tarea listar` - Ver todas las tareas\n" +
            "• `!tarea completar <número>` - Marcar tarea como completada",
            false  // No requiere permisos especiales
        );
    }
    
    /**
     * POLIMORFISMO por implementación
     * 
     * Implementamos el método abstracto de la clase padre.
     * Este método define el comportamiento específico de este comando.
     */
    @Override
    public String ejecutar(String[] args, String canalId, String usuarioId) {
        // Usar método heredado para validar argumentos básicos
        if (!validarArgumentos(args, 1)) {
            return formatearError("Debes especificar una acción: crear, listar o completar");
        }
        
        String accion = args[0].toLowerCase();
        
        // Usar polimorfismo en el procesamiento según la acción
        switch (accion) {
            case "crear":
                return procesarCrearTarea(args, usuarioId);
            case "listar":
                return procesarListarTareas(args, usuarioId);
            case "completar":
                return procesarCompletarTarea(args, usuarioId);
            default:
                return formatearError("Acción no reconocida: " + accion + 
                                    ". Usa: crear, listar o completar");
        }
    }
    
    /**
     * Procesa la creación de una nueva tarea - VERSIÓN SIMPLIFICADA
     */
    private String procesarCrearTarea(String[] args, String usuarioId) {
        if (args.length < 2) {
            return FormateadorMensajes.usoIncorrecto("tarea", 
                "!tarea crear \"Título\" [\"Descripción\"] [MATERIA]");
        }
        
        // Unir argumentos y parsear comillas
        StringBuilder argumentosTexto = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            if (i > 1) argumentosTexto.append(" ");
            argumentosTexto.append(args[i]);
        }
        
        String[] parametros = parsearArgumentosEntreComillas(argumentosTexto.toString());
        
        if (parametros.length < 1 || parametros[0].trim().isEmpty()) {
            return FormateadorMensajes.error("El título es obligatorio");
        }
        
        String titulo = parametros[0].trim();
        String descripcionTarea = parametros.length > 1 ? parametros[1].trim() : "";
        String materia = parametros.length > 2 ? parametros[2].trim() : "General";
        
        try {
            Tarea nuevaTarea = new Tarea(
                UUID.randomUUID().toString(),
                titulo, 
                descripcionTarea, 
                materia, 
                usuarioId, 
                2
            );
            
            tareas.add(nuevaTarea);
            buscarOCrearUsuario(usuarioId); // Crear usuario si no existe
            
            return FormateadorMensajes.tareaCreada(nuevaTarea);
            
        } catch (Exception e) {
            return FormateadorMensajes.error("Error al crear tarea: " + e.getMessage());
        }
    }
    
    /**
     * Procesa el listado de tareas - VERSIÓN SIMPLIFICADA
     */
    private String procesarListarTareas(String[] args, String usuarioId) {
        return FormateadorMensajes.listaTareas(tareas, null);
    }
    
    /**
     * Procesa la completación de una tarea - VERSIÓN SIMPLIFICADA
     */
    private String procesarCompletarTarea(String[] args, String usuarioId) {
        if (args.length < 2) {
            return FormateadorMensajes.usoIncorrecto("tarea", "!tarea completar NUMERO");
        }
        
        try {
            int numero = Integer.parseInt(args[1]) - 1;
            
            if (numero < 0 || numero >= tareas.size()) {
                return FormateadorMensajes.error("Número de tarea inválido (1-" + tareas.size() + ")");
            }
            
            Tarea tarea = tareas.get(numero);
            
            if (tarea.isCompletada()) {
                return FormateadorMensajes.info("Esta tarea ya está completada");
            }
            
            tarea.setCompletada(true);
            
            // Otorgar puntos al usuario
            Usuario usuario = buscarOCrearUsuario(usuarioId);
            usuario.agregarPuntos(10 * tarea.getPrioridad(), tarea.getMateria());
            
            return FormateadorMensajes.exito("🎉 ¡Tarea completada!\n" +
                                "+" + (10 * tarea.getPrioridad()) + " puntos\n\n" +
                                tarea.toString());
            
        } catch (NumberFormatException e) {
            return FormateadorMensajes.error("El número debe ser válido");
        }
    }
    
    /**
     * Busca un usuario existente o crea uno nuevo
     * Método auxiliar que demuestra encapsulamiento
     */
    private Usuario buscarOCrearUsuario(String usuarioId) {
        // Buscar usuario existente
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(usuarioId)) {
                return usuario;
            }
        }
        
        // Crear nuevo usuario si no existe
        Usuario nuevoUsuario = new Usuario(usuarioId, "Usuario_" + usuarioId.substring(0, 6));
        usuarios.add(nuevoUsuario);
        return nuevoUsuario;
    }
    
    /**
     * Método estático para acceder a las tareas desde otros comandos
     * Demuestra encapsulamiento controlado de datos compartidos
     * 
     * @return Lista de usuarios registrados
     */
    public static List<Usuario> getUsuarios() {
        return usuarios;
    }
    
    /**
     * Método estático para acceder a las tareas desde otros comandos
     * Demuestra encapsulamiento controlado de datos compartidos
     * 
     * @return Lista de tareas registradas
     */
    public static List<Tarea> getTareas() {
        return tareas;
    }
    
    /**
     * SOBRESCRITURA del método toString
     * Proporciona información específica sobre este comando
     */
    @Override
    public String toString() {
        return String.format("ComandoTarea{nombre='%s', tareasRegistradas=%d}", 
                           getNombre(), tareas.size());
    }
}
