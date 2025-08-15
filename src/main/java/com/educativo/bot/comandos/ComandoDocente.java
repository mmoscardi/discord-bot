package com.educativo.bot.comandos;

import java.util.ArrayList;
import java.util.List;

import com.educativo.bot.modelos.Usuario;
import com.educativo.bot.modelos.Docente;

/**
 * COMANDOS DE TAREAS UNIFICADOS
 * 
 * Esta clase agrupa todas las funcionalidades relacionadas con tareas:
 * - Crear tareas (generales o asociadas a materias)
 * - Listar tareas (todas, pendientes, completadas, vencidas)
 * - Completar tareas
 * - Eliminar tareas
 * - Establecer fechas de vencimiento
 * - Cambiar prioridades
 * 
 * Demuestra POLIMORFISMO con múltiples subcomandos especializados.
 */
public class ComandoDocente extends ComandoBase {
    
    // ENCAPSULAMIENTO: Datos estáticos compartidos
    private static final List<Docentes> tareas = new ArrayList<>();
    private static final List<Usuario> usuarios = new ArrayList<>();
    
    public ComandoDocente() {
        super(
            "docente",
            "Comandos para gestionar a los docentes de la institución",
            "Ayuda no disponible",
            false
        );
    }
    
    @Override
    public String ejecutar(String[] args, String canalId, String usuarioId) {
        if (args.length == 0) {
            return "❌ Debes especificar una acción: `crear`, `listar`, `completar`, `eliminar`, `vencimiento` o `prioridad`\n" +
                   "Usa `!ayuda tarea` para ver todos los comandos disponibles.";
        }
        
        String accion = args[0].toLowerCase();
        
        switch (accion) {
            case "crear":
                return crearDocente(args, usuarioId);
            case "listar":
                return listarDocentes(args, usuarioId);
            case "eliminar":
                return eliminarDocente(args, usuarioId);
            default:
                return "❌ Acción no válida: `" + accion + "`\n" +
                       "Acciones disponibles: `crear`, `listar`, `completar`, `eliminar`, `vencimiento`, `prioridad`";
        }
    }
    
    // ========================
    // MÉTODOS DE DOCENTES
    // ========================

    private String crearDocente(String[] args, String usuarioId) {
        if (args.length < 2) {
            return "❌ **Uso incorrecto**\n" +
                   "Formato: `!docente crear \"<nombre>\" [\"descripción\"]`\n\n" +
                   "**Ejemplo:**\n" +
                   "`!docente crear \"Juan Pérez\" \"Profesor de Matemáticas\"`";
        }
        
        List<String> argumentosParsed = parsearArgumentosConComillas(args, 1);
        
        if (argumentosParsed.isEmpty()) {
            return "❌ Debes proporcionar un nombre para el docente entre comillas.";
        }
        
        String nombre = argumentosParsed.get(0);
        String descripcion = argumentosParsed.size() > 1 ? argumentosParsed.get(1) : "";
        
        // Crear docente (aquí podrías implementar la lógica de creación)
        // Por simplicidad, solo retornamos un mensaje de éxito
        return "✅ **Docente creado exitosamente**\n\n" +
               "👨‍🏫 **Nombre:** " + nombre + "\n" +
               "📄 **Descripción:** " + descripcion;
    }
    
    private String listarDocentes(String[] args, String usuarioId) {
        // Aquí podrías implementar la lógica para listar docentes
        // Por simplicidad, retornamos un mensaje de ejemplo
        return "👨‍🏫 **Lista de Docentes**\n\n" +
               "1. Juan Pérez - Profesor de Matemáticas\n" +
               "2. Ana Gómez - Profesora de Historia\n" +
               "3. Carlos López - Profesor de Ciencias";
    }

    private String eliminarDocente(String[] args, String usuarioId) {
        if (args.length < 2) {
            return "❌ **Uso incorrecto**\n" +
                   "Formato: `!docente eliminar <nombre>`\n\n" +
                   "**Ejemplo:**\n" +
                   "`!docente eliminar \"Juan Pérez\"`";
        }
        
        String nombre = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
        
        // Aquí podrías implementar la lógica para eliminar un docente
        // Por simplicidad, solo retornamos un mensaje de éxito
        return "✅ **Docente eliminado exitosamente**\n\n" +
               "👨‍🏫 **Nombre:** " + nombre;
    }
   
 
    
  
    
    private List<String> parsearArgumentosConComillas(String[] args, int startIndex) {
        List<String> resultado = new ArrayList<>();
        StringBuilder argumentoActual = new StringBuilder();
        boolean dentroDeComillas = false;
        
        for (int i = startIndex; i < args.length; i++) {
            String arg = args[i];
            
            if (!dentroDeComillas) {
                if (arg.startsWith("\"")) {
                    dentroDeComillas = true;
                    argumentoActual.append(arg.substring(1));
                    if (arg.endsWith("\"") && arg.length() > 1) {
                        dentroDeComillas = false;
                        argumentoActual.setLength(argumentoActual.length() - 1);
                        resultado.add(argumentoActual.toString());
                        argumentoActual.setLength(0);
                    }
                } else {
                    resultado.add(arg);
                }
            } else {
                if (arg.endsWith("\"")) {
                    argumentoActual.append(" ").append(arg.substring(0, arg.length() - 1));
                    resultado.add(argumentoActual.toString());
                    argumentoActual.setLength(0);
                    dentroDeComillas = false;
                } else {
                    argumentoActual.append(" ").append(arg);
                }
            }
        }
        
        // Si quedó algo pendiente sin cerrar comillas
        if (argumentoActual.length() > 0) {
            resultado.add(argumentoActual.toString());
        }
        
        return resultado;
    }
    
  
}
