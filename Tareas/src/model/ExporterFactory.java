package model;


//Clase para selccionar el tipo de exportador 

public class ExporterFactory{
    public static IExporter getExporter(String type){
        switch (type) {
            case "csv":
                return new CSVExporter();                
               
            case "json":
                return new JSONExporter();
            default:
                System.out.println("Opción de exportador no reconocida. Usando CSVExporter por defecto.");
                return new CSVExporter();
        }
     }
    
}
