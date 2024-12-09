package model;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONExporter implements IExporter{
Path ruta = Paths.get(System.getProperty("user.home"), "Downloads", "tareas.json");
    String delimitador = ";";
    
    
    @Override
    public List<Task> importarTareas() {
        ArrayList<Task> tareas = new ArrayList<>();
        Gson gson = new Gson();

        try (FileReader reader = new FileReader("tareas.json")){
            List<String> lineas = Files.readAllLines(ruta);
            for(String linea:lineas){
                Task tarea = gson.fromJson(linea, Task.class);
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
    public boolean exportarTareas(List<Task> tareas){
        // Crear un objeto Gson
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try(FileWriter writer = new FileWriter("tareas.json")) {
            for(Task tarea: tareas){

                gson.toJson(tarea,writer);
                System.out.println("Archivo JSON exportado correctamente.");

            }
        return true;
    } catch (IOException e) {
        return false;
    }
    }
    
}

