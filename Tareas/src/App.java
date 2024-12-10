import controller.Controller;
import model.BinaryRepository;
import model.CSVExporter;
import model.IRepository;
import model.IExporter;
import model.JSONExporter;
import model.Model;
import model.NotionRepository;
import view.BaseView;
import view.ConsolaListadoView;
import java.time.format.DateTimeFormatter;

public class App {


    
    public static void main(String[] args) throws Exception {
        IRepository repository = new BinaryRepository();
        BaseView view = new ConsolaListadoView();
        IExporter exporter = new JSONExporter();
        
        //IExporter exporter = new CSVExporter();
        
        //String apiToken = "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";
        //String databaseId = "157be7b75ff88056a150c994ff38d843";
        //NotionRepository repository = new NotionRepository(apiToken, databaseId);

        Model model = new Model(repository, exporter);
        Controller controller = new Controller(view, model);
        controller.run();



        
        
        /* 

            IRepository repository;
            BaseView view = new ConsolaListadoView();
            IExporter exporter;

           if(args.length == 2){
            repository = getRepositoryForOption(args[0]);
            exporter = getExporterForOption(args[1]);
            
        }else{
            // Opciones por defecto:
            exporter = new CSVExporter();
           repository = new BinaryRepository();
       }
       
       Model model = new Model(repository, exporter);
       Controller controller = new Controller(view, model);
       
       controller.run();  
   }

   private static IExporter getExporterForOption(String argumento) {
       .getExporter(argumento);
   }

   private static IExporter getRepositoryForOption(String argumento) {
       switch (argumento) {
           case "csv":
               return new CSVExporter();
           default:
               return new MemoryRepository(20);*/
       }
   
    
}
