package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVExporter implements IExporter{

    Path ruta = Paths.get(System.getProperty("user.home"), "Downloads", "tasks.csv");
    String delimitador = ";";
    
    
    @Override
    public ArrayList<Task> importarTareas() {
        ArrayList<Task> tareas = new ArrayList<>();
        try {
            List<String> lineas = Files.readAllLines(ruta);
            for(String linea:lineas){
                Task tarea = Task.getTaskFromString(linea,delimitador);
                if(tarea!=null){
                    tareas.add(tarea);
                }

            }
            return tareas;
        } catch (IOException e) {
            return null;
        }
    }


    @Override
    public boolean exportarTareas(ArrayList<Task> tareas) {
        Path rutaCSV = Paths.get(System.getProperty("user.home"),"Downloads","tareas.csv" );

        try(PrintWriter writer = new PrintWriter(rutaCSV.toFile())) {
            for(Task tarea: tareas){
                String tareaString = tarea.getInstanceAsDelimitedString(";");
                writer.println(tareaString);
                System.out.println(tareaString);

            }
        return true;
    } catch (Exception e) {
        return false;
    }
    }

    

    

}
