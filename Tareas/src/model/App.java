package model;


import controller.Controller;
import view.BaseView;
import view.ConsolaListadoView;
import java.time.format.DateTimeFormatter;

public class App {


    // para ejecutar por consola:
    //jjava -jar .\PracticaObligatoria-2.jar "notion" "csv"
    //donde repository es binary o notion y exporter es csv o json


    public static void main(String[] args) throws Exception {
        IRepository repository;
        BaseView view = new ConsolaListadoView();
        IExporter exporter;
        
    
        if(args.length == 2){
            repository = getRepositoryForOption(args[0]);
            exporter = getExporterForOption(args[1]);
            
        }else{
            // Opciones por defecto:
    
            repository = new BinaryRepository();
            exporter = new CSVExporter();

       }

        // Inicialización del modelo y controlador

       Model model = new Model(repository, exporter);
       Controller controller = new Controller(view, model);
       
       //Ejecutar el controlador
       controller.run();  
   }

   //Obtenemos el exporter deseado gracias a la clase ExporterFactory
   //añadimos .toLoweCase() por si el usuario introduce el argumento con mayúsculas
   private static IExporter getExporterForOption(String argumento) {
        return ExporterFactory.getExporter(argumento.toLowerCase());
   }

   //Obtenemos el tipo de repositorio qeu se va a utilizar durante la ejecución
   private static IRepository getRepositoryForOption(String argumento) {
        switch (argumento.toLowerCase()) {
            case "binary":
                return new BinaryRepository();
        
            case "notion":
                String apiToken = "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";
                String databaseId = "157be7b75ff88056a150c994ff38d843";
                return new NotionRepository(apiToken, databaseId);

            default:
            System.out.println("Opción de repositorio no reconocida. Usando BinaryRepository por defecto.");
            return new BinaryRepository();
        }
    
    }
}
