package com.educativo.bot.comandos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.educativo.bot.modelos.Materia;
import com.educativo.bot.modelos.Tarea;
import com.educativo.bot.utils.FormateadorMensajes;

/**
 * CONCEPTO: POLIMORFISMO Y HERENCIA
 * 
 * Esta clase demuestra:
 * 1. Herencia - extiende de ComandoBase
 * 2. Polimorfismo - implementa el método ejecutar() con switch para subcomandos
 * 3. Encapsulamiento - gestiona datos de materias de forma controlada
 * 4. Método único que maneja múltiples funcionalidades
 * 
 * Permite gestionar materias académicas: crear, listar, eliminar y ver tareas.
 */
public class ComandoMateria extends ComandoBase {
    
    // Lista estática compartida de materias (simula base de datos)
    private static final List<Materia> materias = new ArrayList<>();
    
    /**
     * Constructor que demuestra herencia
     */
    public ComandoMateria() {
        super(
            "materia",
            "Permite crear, listar y gestionar materias académicas",
            "!materia [crear|listar|eliminar|tareas] [parámetros]\n\n" +
            "**CREAR MATERIA:**\n" +
            "• `!materia crear <código> \"<nombre>\" [\"descripción\"] [\"profesor\"]`\n" +
            "• Ejemplo: `!materia crear MAT101 \"Matemáticas\" \"Álgebra básica\" \"Dr. Juan Pérez\"`\n" +
            "• Código: Identificador único (obligatorio)\n" +
            "• Nombre: Nombre de la materia (obligatorio, entre comillas)\n" +
            "• Descripción: Descripción opcional (entre comillas)\n" +
            "• Profesor: Nombre del docente (opcional, entre comillas)\n\n" +
            "**OTROS COMANDOS:**\n" +
            "• `!materia listar [activas|archivadas|detalle]` - Ver materias\n" +
            "• `!materia eliminar <código>` - Eliminar materia\n" +
            "• `!materia tareas <código> [pendientes|completadas|vencidas]` - Ver tareas",
            false
        );
    }
    
    /**
     * POLIMORFISMO: Implementación específica del método ejecutar
     * Usa switch para manejar diferentes subcomandos
     * 
     * @param args Argumentos del comando
     * @param canalId ID del canal donde se ejecutó
     * @param usuarioId ID del usuario que ejecutó el comando
     * @return Respuesta del comando
     */
    @Override
    public String ejecutar(String[] args, String canalId, String usuarioId) {
        // Validar argumentos mínimos
        if (!validarArgumentos(args, 1)) {
            return formatearError("⚠️ **Debes especificar una acción**\n\n" + 
                                "**Acciones disponibles:**\n" +
                                "• `!materia crear <código> \"<nombre>\"` - Crear materia\n" +
                                "• `!materia listar` - Ver todas las materias\n" +
                                "• `!materia eliminar <código>` - Eliminar materia\n" +
                                "• `!materia tareas <código>` - Ver tareas de materia\n\n" +
                                "💡 Usa `!ayuda materia` para ver ejemplos detallados");
        }
        
        String accion = args[0].toLowerCase();
        
        // POLIMORFISMO: Usar switch para procesar diferentes acciones
        switch (accion) {
            case "crear":
                return procesarCrearMateria(args, usuarioId);
            case "listar":
                return procesarListarMaterias(args, usuarioId);
            case "eliminar":
                return procesarEliminarMateria(args, usuarioId);
            case "tareas":
                return procesarTareasMateria(args, usuarioId);
            default:
                return formatearError("❌ **Acción no reconocida:** " + accion + "\n\n" +
                                    "**Acciones disponibles:**\n" +
                                    "• `crear` - Crear nueva materia\n" +
                                    "• `listar` - Ver materias existentes\n" +
                                    "• `eliminar` - Eliminar una materia\n" +
                                    "• `tareas` - Ver tareas de una materia\n\n" +
                                    "💡 Usa `!ayuda materia` para ver ejemplos detallados");
        }
    }
    
    /**
     * Procesa la creación de una nueva materia - VERSIÓN SIMPLIFICADA
     */
    private String procesarCrearMateria(String[] args, String usuarioId) {
        // Validar argumentos básicos
        if (args.length < 3) {
            return FormateadorMensajes.usoIncorrecto("materia", 
                "!materia crear CODIGO \"Nombre\" [\"Descripción\"] [\"Profesor\"]");
        }
        
        String codigo = args[1].trim().toUpperCase();
        
        // Unir argumentos y parsear comillas
        StringBuilder argumentosTexto = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            if (i > 2) argumentosTexto.append(" ");
            argumentosTexto.append(args[i]);
        }
        
        String[] parametros = parsearArgumentosEntreComillas(argumentosTexto.toString());
        
        if (parametros.length < 1 || codigo.isEmpty() || parametros[0].trim().isEmpty()) {
            return FormateadorMensajes.error("Código y nombre son obligatorios");
        }
        
        // Verificar duplicados
        if (existeMateriaConCodigo(codigo)) {
            return FormateadorMensajes.error("Ya existe una materia con código: " + codigo);
        }
        
        // Crear materia
        String nombreMateria = parametros[0].trim();
        String descripcionMateria = parametros.length > 1 ? parametros[1].trim() : "";
        String profesorMateria = parametros.length > 2 ? parametros[2].trim() : "";
        
        try {
            Materia nuevaMateria = new Materia(
                UUID.randomUUID().toString(),
                nombreMateria, 
                codigo, 
                descripcionMateria, 
                profesorMateria, 
                usuarioId
            );
            
            materias.add(nuevaMateria);
            return FormateadorMensajes.materiaCreada(nuevaMateria);
            
        } catch (Exception e) {
            return FormateadorMensajes.error("Error al crear materia: " + e.getMessage());
        }
    }
    
    /**
     * Procesa la lista de materias - VERSIÓN SIMPLIFICADA
     */
    private String procesarListarMaterias(String[] args, String usuarioId) {
        // Aplicar filtro si se especifica
        String filtro = args.length > 1 ? args[1].toLowerCase() : "todas";
        List<Materia> materiasFiltradas = aplicarFiltroMaterias(materias, filtro);
        
        if (materiasFiltradas.isEmpty() && !materias.isEmpty()) {
            return FormateadorMensajes.error("No hay materias con el filtro: " + filtro);
        }
        
        return FormateadorMensajes.listaMaterias(materiasFiltradas);
    }
    
    /**
     * Procesa la eliminación de una materia - VERSIÓN SIMPLIFICADA
     */
    private String procesarEliminarMateria(String[] args, String usuarioId) {
        if (args.length < 2) {
            return FormateadorMensajes.usoIncorrecto("materia", "!materia eliminar CODIGO");
        }
        
        String codigo = args[1].trim().toUpperCase();
        Materia materia = buscarMateriaPorCodigo(codigo);
        
        if (materia == null) {
            return FormateadorMensajes.noEncontrado("Materia", codigo);
        }
        
        if (!materia.getCreadorId().equals(usuarioId)) {
            return FormateadorMensajes.error("Solo el creador puede eliminar la materia");
        }
        
        if (materia.getCantidadTareas() > 0) {
            return FormateadorMensajes.error("La materia tiene " + materia.getCantidadTareas() + 
                                           " tareas. Elimínalas primero");
        }
        
        materias.removeIf(m -> m.getCodigo().equalsIgnoreCase(codigo));
        return FormateadorMensajes.exito("Materia **" + codigo + "** eliminada");
    }
    
    /**
     * Procesa consulta de tareas de una materia - VERSIÓN SIMPLIFICADA
     */
    private String procesarTareasMateria(String[] args, String usuarioId) {
        if (args.length < 2) {
            return FormateadorMensajes.usoIncorrecto("materia", "!materia tareas CODIGO");
        }
        
        String codigo = args[1].trim().toUpperCase();
        Materia materia = buscarMateriaPorCodigo(codigo);
        
        if (materia == null) {
            return FormateadorMensajes.noEncontrado("Materia", codigo);
        }
        
        // Obtener tareas de la materia
        List<Tarea> todasLasTareas = ComandoTarea.getTareas();
        List<Tarea> tareasMateria = todasLasTareas.stream()
                .filter(tarea -> materia.getCodigo().equalsIgnoreCase(tarea.getMateria()))
                .collect(Collectors.toList());
        
        return FormateadorMensajes.listaTareas(tareasMateria, materia.getNombreCompleto());
    }
    
    // ========================== MÉTODOS AUXILIARES ==========================
    
    /**
     * Verifica si existe una materia con el código dado
     */
    private boolean existeMateriaConCodigo(String codigo) {
        return materias.stream()
                .anyMatch(materia -> materia.getCodigo().equalsIgnoreCase(codigo));
    }
    
    /**
     * Aplica filtro a la lista de materias
     */
    private List<Materia> aplicarFiltroMaterias(List<Materia> materias, String filtro) {
        switch (filtro) {
            case "activas":
                return materias.stream()
                        .filter(Materia::isActiva)
                        .collect(Collectors.toList());
                        
            case "archivadas":
                return materias.stream()
                        .filter(materia -> !materia.isActiva())
                        .collect(Collectors.toList());
                        
            case "detalle":
            case "todas":
            default:
                return materias.stream()
                        .collect(Collectors.toList());
        }
    }
    
    // ========================== MÉTODOS ESTÁTICOS PÚBLICOS ==========================
    
    /**
     * Método estático para obtener la lista de materias
     * Permite a otros comandos acceder a las materias
     * 
     * @return Lista de materias
     */
    public static List<Materia> getMaterias() {
        return materias;
    }
    
    /**
     * Método estático para buscar materia por código
     * 
     * @param codigo Código de la materia
     * @return Materia encontrada o null
     */
    public static Materia buscarMateriaPorCodigo(String codigo) {
        return materias.stream()
                .filter(materia -> materia.getCodigo().equalsIgnoreCase(codigo))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Método estático para buscar materia por ID
     * 
     * @param id ID de la materia
     * @return Materia encontrada o null
     */
    public static Materia buscarMateriaPorId(String id) {
        return materias.stream()
                .filter(materia -> materia.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * POLIMORFISMO: Personaliza la representación string del comando
     */
    @Override
    public String toString() {
        return String.format("ComandoMateria{nombre='%s', materiasCreadas=%d}", 
                           getNombre(), materias.size());
    }
}
