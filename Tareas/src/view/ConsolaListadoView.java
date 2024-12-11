package view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import com.coti.tools.Esdia;
import model.ExporterException;
import model.RepositoryException;
import model.Task;

//Vista en la que se desarrollan todos los métodos necesarios para la ejecución de la aplicación

public class ConsolaListadoView extends BaseView {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void init() throws Exception{
        showMenu();
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
        System.out.println("Saliendo de la aplicacion");

    }



    @Override
    public void showMenu() throws Exception {
        int opcion;
        do{
            System.out.println("\n--- MENU ---");
            System.out.println("1. Agregar Tarea: ");
            System.out.println("2. Listar Tarea por orden de prioridad y el historial de tareas: ");
            System.out.println("3. holaModificar Tarea: ");
            System.out.println("4. Exportar o Importar Tareas: ");
            System.out.println("5. Salir ");
            opcion = Esdia.readInt("Ingrese una opción: ");

            switch (opcion) {
                case 1:
                    addTask();
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



    public void addTask() throws RepositoryException, ParseException{
        System.out.println("Introduzca los datos de la tarea: ");
        UUID identifier = UUID.randomUUID();
        String title = Esdia.readString("Introduzca el título de la tarea: ");
        Date fecha = pedirFecha();
        String content = Esdia.readString("Introduzca el contenido de la tarea: ");
        int priority = Esdia.readInt("Introduzca la prioridad de la tarea: ");
        int estimatedDuration = Esdia.readInt("Introduzca la duración estimada: ");
        boolean completed = false;

        if(controller.addTask(new Task(identifier, title, fecha, content, priority, estimatedDuration, completed))){
            System.out.println("Tarea agregada con éxito");
        } else{
            System.out.println("No se pudo agregar la tarea.");
        }
    }


    public Date pedirFecha() throws ParseException{
        Date date = null;
        boolean fechaCorrecta = false;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        formatter.setLenient(false);
        while (!fechaCorrecta) { // Continuar pidiendo hasta obtener una fecha válida
            
        String entrada = Esdia.readString("Introduce una fecha en formato yyyy-MM-dd: ");

            try {
                date = formatter.parse(entrada);
                fechaCorrecta = true;
            } catch (ParseException e) {
                System.out.println("Fecha inválida. Por favor, inténtalo de nuevo.");
            }
        }
        return date;

    }

    public void listarTareas() throws RepositoryException{
        System.out.println("TAREAS SIN COMPLETAR POR ORDEN DE PRIORIDAD: ");
        tareasSinCompletar();

        System.out.println("HISTORIAL DE TAREAS: ");
        historialTareas();
    }

    private void tareasSinCompletar() throws RepositoryException{
        List<Task> tareas = controller.getAllTasks();
        List<Task> pendingTasks = new ArrayList<>();
            for(Task tarea : tareas){
            if(tarea.isCompleted()==false){
                pendingTasks.add(tarea);
            }
            pendingTasks.sort(Comparator.comparing(Task::getPriority));
            Collections.reverse(pendingTasks);

            if(pendingTasks.isEmpty()){
                System.out.println("No hay tareas pendientes.");
            }else{
            for(Task t : pendingTasks){
                System.out.println(t.listarTarea());
            }
            }

        }
        
        
    }

    private void historialTareas() throws RepositoryException{
        List<Task> tareas = controller.getAllTasks();
        
        if (tareas.isEmpty()) {
            System.out.println("No hay tareas registradas.");
        } else {
            for(Task tarea : tareas){
                System.out.println(tarea.listarTarea());
            }
            }
         
        
    }


    public void detallesTarea() throws RepositoryException, ParseException{
        
        boolean salir = false;
                 

            System.out.println("MENÚ");
            System.out.println("1.- Marcar la tarea como completada.");
            System.out.println("2.- Modificar la tarea seleccionada.");
            System.out.println("3.- Eliminar la tarea seleccionada.");
            System.out.println("4.- Salir");
            
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
            
            
    
    }

    private void tareaCompletada(){
        String identifierString = Esdia.readString("Introduzca el identificador de la tarea que vamos a completar: ");
        UUID identifierModificar = UUID.fromString(identifierString);
        if(controller.completarTarea(identifierModificar)){
            System.out.println("La tarea con el identificador " + identifierModificar + "se ha marcado como completada.");
        } else{
            System.out.println("Tarea no encontrada");
            
        }
        
    }

    private void modificarTarea() throws RepositoryException, ParseException{
        String identifierString = Esdia.readString("Introduzca el identificador de la tarea que vamos a obtener los detalles y modificar: ");
        UUID identifierModificar = UUID.fromString(identifierString);
        Task tareaModificar = controller.modifyTask(identifierModificar);
        if(tareaModificar != null){
            /*String newTitle=tareaModificar.getTitle();
            Date newDate=tareaModificar.getDate();
            String newContent=tareaModificar.getContent();
            int newPriority=tareaModificar.getPriority();
            int newEstimatedDuration=tareaModificar.getEstimatedDuration();
            boolean newCompleted=tareaModificar.isCompleted();*/

            String newTitle = Esdia.readString("Introduzca el nuevo título de la tarea: ");
            Date newDate = pedirFecha(); 
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                formatter.setLenient(false);
            String newContent = Esdia.readString("Introduzca el nuevo contenido de la tarea: ");
            int newPriority = Esdia.readInt("Introduzca la nueva prioridad: ");
            int newEstimatedDuration = Esdia.readInt("Introduzca la nueva duración estimada: ");
            boolean newCompleted = Esdia.siOno("Introduzca si la tarea está completada o no: ");
            
            Task tareaModificada = new Task(identifierModificar, newTitle, newDate, newContent,newPriority, newEstimatedDuration, newCompleted);
            controller.removeTask(identifierModificar);
            controller.addTask(tareaModificada);

            System.out.println("La tarea con el identificador " + identifierModificar + "se ha modificado.");
        } else{
            System.out.println("Tarea no encontrada");
        }
    }
        
    public void eliminarTarea() throws RepositoryException{
        String identifierString = Esdia.readString("Introduzca el identificador de la tarea que vamos a eliminar: ");
        UUID identifierModificar = UUID.fromString(identifierString);
        if(controller.removeTask(identifierModificar)){
            System.out.println("La tarea con el identificador " + identifierModificar + "ha sido eliminada.");
        } else{
            System.out.println("La tarea con el identificador " + identifierModificar + "no pudo ser eliminada.");

        }
    }

    public void exportarImportar() throws ExporterException, RepositoryException{
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

 
    public void exportarCSV() throws ExporterException, RepositoryException {
        if(controller.exportarTareas()){
            System.out.println("Exportacion csv realizada con exito:");
        } else{
            System.out.println("No se pudo exoportar csv.");
        }
    }

    public void importarCSV() throws ExporterException, RepositoryException{
        if(controller.importarTareas()){
            System.out.println("Importación CSV realizada con éxito");
        }else{
            System.out.println("No se pudo importar csv ");
        }
    }

    public void exportarJSON() throws ExporterException, RepositoryException {
        if(controller.exportarTareas()){
            System.out.println("Exportacion json realizada con exito:");
        } else{
            System.out.println("No se pudo exoportar json.");
        }
    }

    public void importarJSON() throws ExporterException, RepositoryException{
        if(controller.importarTareas()){
            System.out.println("Importación json realizada con exito");
        }else{
            System.out.println("No se pudo importar json ");
        }
    }




    





}
