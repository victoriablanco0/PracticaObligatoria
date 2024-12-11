package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


//CLASE PARA EXPORTAR/IMPORTAR EN CSV

public class CSVExporter implements IExporter{

    Path ruta = Paths.get(System.getProperty("user.home"), "Downloads", "output.csv");
    String delimitador = ";"; //Comma Separated Value
    
    
    @Override
    public List<Task> importarTareas() throws ExporterException  {
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
        } catch (IOException | ParseException e) {
            return null;
        }
    }


    @Override
    public boolean exportarTareas(List<Task> tareas) {
        
        //El archivo se guardará en la carpeta Descargas

        Path rutaCSV = Paths.get(System.getProperty("user.home"),"Downloads", "output.csv" );
        
        try(PrintWriter writer = new PrintWriter(rutaCSV.toFile())) {
            
            for(Task tarea: tareas){
                String tareaString = tarea.getInstanceAsDelimitedString(";");
                writer.println(tareaString);
            }
        return true;
    } catch (Exception e) {
        return false;
    }
    }

    

    

}
