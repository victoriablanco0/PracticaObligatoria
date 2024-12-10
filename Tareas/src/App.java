

import controller.Controller;
import model.BinaryRepository;
import model.CSVExporter;
import model.ExporterFactory;
import model.IExporter;
import model.IRepository;
import model.Model;
import model.NotionRepository;
import view.BaseView;
import view.ConsolaListadoView;
import java.time.format.DateTimeFormatter;

public class App {


    
    public static void main(String[] args) throws Exception {
        IRepository repository;
        BaseView view = new ConsolaListadoView();
        IExporter exporter;
        
        
        //String apiToken = "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";
        //String databaseId = "157be7b75ff88056a150c994ff38d843";
        //NotionRepository repository = new NotionRepository(apiToken, databaseId);

        if(args.length == 2){
            repository = getRepositoryForOption(args[0]);
            exporter = getExporterForOption(args[1]);
            
        }else{
            // Opciones por defecto:
            System.out.println("No se proporcionaron argumentos válidos. Usando valores por defecto.");

            repository = new BinaryRepository();
            exporter = new CSVExporter();

       }

        // Inicialización del modelo y controlador

       Model model = new Model(repository, exporter);
       Controller controller = new Controller(view, model);
       
       //Ejecutar el controlador
       controller.run();  
   }

   private static IExporter getExporterForOption(String argumento) {
        return ExporterFactory.getExporter(argumento);
   }

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
