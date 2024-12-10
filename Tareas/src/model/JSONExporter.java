package model;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class JSONExporter implements IExporter{

Path ruta = Paths.get(System.getProperty("user.home"), "Downloads", "output.json");
    

@Override
    public List<Task> importarTareas() {
        ArrayList<Task> tareas = new ArrayList<>();
        Gson gson = new Gson();


        try (FileReader reader = new FileReader("output.json")){
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
    public boolean exportarTareas(List<Task> tareas) throws ExporterException{
        Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(Date.class, new DateAdapter()) // Registrar el TypeAdapter para Date
        .create();
String filePath = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "output.json";
try (FileWriter writer = new FileWriter(filePath)){
     
    gson.toJson(tareas, writer); // Serializar y escribir en el archivo
    return true; // Operación exitosa
} catch (IOException e) {
    System.err.println("Error al exportar JSON: " + e.getMessage());
    return false; // Operación fallida
}
}
}
        

        
    
       
    

