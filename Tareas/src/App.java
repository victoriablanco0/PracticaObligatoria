import controller.Controller;
import model.BinaryRepository;
import model.CSVExporter;
import model.IExporter;
import model.IRepository;
import model.Model;
import view.BaseView;
import view.ConsolaListadoView;

public class App {
    public static void main(String[] args) throws Exception {
        IRepository repository = new BinaryRepository();
        BaseView view = new ConsolaListadoView();
        IExporter exporter = new CSVExporter();

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
