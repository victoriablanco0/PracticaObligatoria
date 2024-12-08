package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.coti.tools.Esdia;

import model.RepositoryException;
import model.Task;

public class ConsolaListadoView extends BaseView {
    
    @Override
    public void init() throws Exception{
        mostrarMenu();
    }
    
    @Override
    public void showMessage(String mensaje){
        System.out.println(mensaje);
    }


    @Override
    public void showErrorMessage(String mensajeError){
        System.out.println(mensajeError);

    }


    @Override
    public void end(){
        System.out.println("SALIR DEL PROGRAMA");

    }



    @Override
    public void mostrarMenu() throws Exception {
        int opcion;
        do{
            System.out.println("\n--- MENU ---");
            System.out.println("1. Agregar Tarea: ");
            System.out.println("2. Listar Tarea por orden de prioridad y el historial de tareas: ");
            System.out.println("3. Modificar Tarea: ");
            System.out.println("4. Exportar Tarea: ");
            System.out.println("5. Importar Tareas: ");
            System.out.println("6. Salir");
            opcion = Esdia.readInt("Ingrese una opción: ");

            switch (opcion) {
                case 1:
                    agregarTarea();
                    break;
            
                case 2:
                    listarTareas();
                    break;
                
                case 3: 
                    detallesTarea();
                    break;
                case 4:
                    exportarImportar();
                    break;
                case 5:
                    end();
                    break;
                default:
                    System.out.println("Opción introducida no válida. ");
                    break;
            }
        } while (opcion !=5);
    }



    public void agregarTarea() throws RepositoryException{
        System.out.println("Introduzca los datos de la tarea: ");
        UUID identifier = UUID.randomUUID();
        String title = Esdia.readString("Introduzca el título de la tarea: ");
        LocalDate date = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Formato de fecha esperado (por ejemplo, 2023-12-07)
        while (date == null) { // Continuar pidiendo hasta obtener una fecha válida
            String entrada = Esdia.readString("Introduce una fecha en formato yyyy-MM-dd: ");

            try {
                date = LocalDate.parse(entrada, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Fecha inválida. Por favor, inténtalo de nuevo.");
            }
        String content = Esdia.readString("Introduzca el contenido de la tarea: ");
        int priority = Esdia.readInt("Introduzca la prioridad de la tarea: ");
        int estimatedDuration = Esdia.readInt("Introduzca la duración estimada: ");
        boolean completed = false;

        if(controller.agregarTarea(new Task(identifier, title, date, content, priority, estimatedDuration, completed))){
            System.out.println("Tarea agregada con éxito");
        } else{
            System.out.println("No se pudo agregar la tarea.");
        }
    }
}

    public void listarTareas() throws RepositoryException{
        System.out.println("TAREAS SIN COMPLETAR POR ORDEN DE PRIORIDAD: ");
        tareasSinCompletar();

        System.out.println("HISTORIAL DE TAREAS: ");
        historialTareas();
    }

    private void tareasSinCompletar() throws RepositoryException{
        List<Task> tareas = controller.obtenerTareasDelModelo();
        List<Task> pendingTasks = new ArrayList<>();
            for(Task tarea : tareas){
            if(!tarea.isCompleted()){
                pendingTasks.add(tarea);
            }

        pendingTasks.sort((t1, t2) -> Integer.compare(t2.getPriority(), t1.getPriority()));
        if (pendingTasks.isEmpty()) {
            System.out.println("No hay tareas pendientes.");
        } else {
            for (Task task : pendingTasks) {
                System.out.println(task);
            }
        }

        }
        
        
    }

    private void historialTareas() throws RepositoryException{
        List<Task> tareas = controller.obtenerTareasDelModelo();
        
        if (tareas.isEmpty()) {
            System.out.println("No hay tareas registradas.");
        } else {
            for(Task tarea : tareas){
            System.out.println("|----------------------------------------------------------------------------|");
            System.out.println(tarea.listarTarea());
                }   
            }
        
    }


    public void detallesTarea() throws RepositoryException{
        String identifierString = Esdia.readString("Introduzca el identificador de la tarea que vamos a obtener los detalles y modificar: ");
        UUID identifierModificar = UUID.fromString(identifierString);
        Task tareaModificar = controller.modificarTarea(identifierModificar);
        System.out.println(tareaModificar.listarTarea()); //damos los detalles de la tarea con el id introducido

        if(tareaModificar !=null){
            boolean salir = false;
            System.out.println("MENÚ");
            System.out.println("1.- Marcar la tarea como completada.");
            System.out.println("2.- Modificar la tarea seleccionada.");
            System.out.println("3.- Eliminar la tarea seleccionada.");
            System.out.println("4.- Salir");
            do {
                int opcion = Esdia.readInt("Introduzca la opcion deseada: ");
                switch (opcion) {
                    case 1:
                        tareaCompletada();
                        break;
                    case 2:
                        modificarTarea();
                        break;
                    case 3:
                        eliminarTarea();
                        break;
                    case 4:
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción no válida");
                        break;
                }
            } while (!salir);
        }
    }

    private void tareaCompletada(){
        String identifierString = Esdia.readString("Introduzca el identificador de la tarea que vamos a marcar como completada: ");
        UUID identifierCompletar = UUID.fromString(identifierString);
        if(controller.completarTarea(identifierCompletar)){
            System.out.println("La tarea con el identificador " + identifierCompletar + "se ha marcado como completada.");
        } else{
            System.out.println("Tarea no encontrada");

        }
    }

    private void modificarTarea() throws RepositoryException{
        String identifierString = Esdia.readString("Introduzca el identificador de la tarea que vamos a marcar como completada: ");
        UUID identifierModificar = UUID.fromString(identifierString);
        Task tareaModificar = controller.modificarTarea(identifierModificar);
        if(tareaModificar != null){
            String newTitle=tareaModificar.getTitle();
            LocalDate newDate=tareaModificar.getDate();
            String newContent=tareaModificar.getContent();
            int newPriority=tareaModificar.getPriority();
            int newEstimatedDuration=tareaModificar.getEstimatedDuration();
            boolean newCompleted=tareaModificar.isCompleted();

            newTitle = Esdia.readString("Introduzca el nuevo título de la tarea");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String nuevaFecha = Esdia.readString("Introduce la nueva fecha en formato yyyy-MM-dd: ");
            newDate = LocalDate.parse(nuevaFecha, formatter);
            newContent = Esdia.readString("Introduzca el nuevo contenido de la tarea: ");
            newPriority = Esdia.readInt("Introduzca la nueva prioridad: ");
            newEstimatedDuration = Esdia.readInt("Introduzca la nueva duración estimada: ");
            newCompleted = Esdia.siOno("Introduzca si la tarea está completada o no: ");
            
            Task tareaModificada = new Task(identifierModificar, newTitle, newDate, newContent,newPriority, newEstimatedDuration, newCompleted);
            controller.agregarTarea(tareaModificada);

            System.out.println("La tarea con el identificador " + identifierModificar + "se ha marcado como completada.");
        } else{
            System.out.println("Tarea no encontrada");
        }
    }
        
    public void eliminarTarea(){
        String identifierString = Esdia.readString("Introduzca el identificador de la tarea que vamos a eliminar: ");
        UUID identifierEliminar = UUID.fromString(identifierString);
        if(controller.eliminarTarea(identifierEliminar)){
            System.out.println("La tarea con el identificador " + identifierEliminar + "ha sido eliminada.");
        } else{
            System.out.println("La tarea con el identificador " + identifierEliminar + "no pudo ser eliminada.");

        }
    }

    public void exportarImportar(){
        boolean salir = false;
            do {
                System.out.println("MENÚ");
                System.out.println("1.- Importar JSON");
                System.out.println("2.- Importar CSV");
                System.out.println("3.- Exportar JSON");
                System.out.println("4.- Exportar CSV");
                System.out.println("5.- Salir");
                int opcion = Esdia.readInt("Introduzca la opcion deseada: ");
                switch (opcion) {
                    case 1:
                        importarJSON();
                        break;
                    case 2:
                        importarCSV();
                        break;
                    case 3:
                        exportarJSON();
                        break;
                    case 4:
                        exportarCSV();
                        break;
                    case 5:
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción no válida");
                        break;
                }
            } while (!salir);

    }

    public void importarJSON(){
        if(controller.importarTareasJSON()){
            System.out.println("Se ha importado en formato JSON");
        }else{
            System.out.println("No se pudo importar en formato JSON");
        }
    }

    public void importarCSV(){
        if(controller.importarTareas()){
            System.out.println("Se ha importado en formato CSV");
        }else{
            System.out.println("No se pudo importar en formato CSV");
        }
    }
    public void exportarJSON(){
        if(controller.exportarTareasJSON()){
            System.out.println("Se ha exportado en formato JSON");
        }else{
            System.out.println("No se pudo exportar en formato JSON");
        }
    }
    public void exportarCSV(){
        if(controller.exportarTareas()){
            System.out.println("Se ha exportado en formato CSV");
        }else{
            System.out.println("No se pudo exportar en formato CSV");
        }
    }
        


    




    
}
