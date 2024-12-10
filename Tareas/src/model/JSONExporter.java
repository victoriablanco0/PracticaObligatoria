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
import com.google.gson.reflect.TypeToken;


public class JSONExporter implements IExporter{

Path ruta = Paths.get(System.getProperty("user.home"), "Downloads", "output.json");
    

@Override
    public List<Task> importarTareas() {
           List<Task> tareas = new ArrayList<>();  // Inicializamos la lista de tareas
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(Date.class, new DateAdapter())  // Registrar el TypeAdapter para Date
            .create();

    String filePath = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + "output.json";

    try (FileReader reader = new FileReader(filePath)) {
        // Leemos todo el archivo JSON y lo convertimos a una lista de objetos Task
        tareas = gson.fromJson(reader, new TypeToken<List<Task>>(){}.getType());
        return tareas;  // Devolvemos la lista de tareas deserializadas
    } catch (IOException e) {
        System.err.println("Error al importar tareas: " + e.getMessage());
        return null;  // Si ocurre un error, devolvemos null
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
        

        
    
       
    

