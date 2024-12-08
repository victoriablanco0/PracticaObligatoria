package model;

public class ExporterFactory{
    /*
     * private static final Map<String, Supplier<IExporter>> exporters = new HashMap<>();

    static {
        exporters.put("csv", CsvExporter::new);
        exporters.put("json", JsonExporter::new);
    }

    public static IExporter getExporter(String type) throws ExporterException {
        Supplier<IExporter> exporterSupplier = exporters.get(type.toLowerCase());
        if (exporterSupplier != null) {
            return exporterSupplier.get();
        }
        throw new ExporterException("Tipo de exportador desconocido: " + type);
    }
     */
}
