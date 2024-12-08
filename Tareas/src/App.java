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

                
//        if(args.length == 2){
//            view = getViewForoption(args[0]);
//            repository = getRepositoryForOption(args[1]);
//            
//        }else{
//            // Opciones por defecto:
//            view = new ConsolaListadoView();
//            repository = new MemoryRepository(20);
//        }
//        
//        Model model = new Model(repository);
//        Controller controller = new Controller(view, model);
//        
//        controller.inicio();  
//    }
//
//    private static IExporter getExporterForOption(String argumento) {
//        switch (argumento) {
//            case "csv":
//                return new CSVExporter();
//            default:
//                return new MemoryRepository(20);
//        }
//    }
//
//    private static IExporter getRepositoryForOption(String argumento) {
//        switch (argumento) {
//            case "csv":
//                return new CSVExporter();
//            default:
//                return new MemoryRepository(20);
//        }
//    }
//
//    private static BaseView getViewForoption(String argumento) {
//        switch (argumento) {
//            case "CLIView":
//                return new CLIView();
//            case "submenus":
//                return new ConsolaSubMenusView();
//            default:
//                return new ConsolaListadoView();
//        }
//    }

    }
}
