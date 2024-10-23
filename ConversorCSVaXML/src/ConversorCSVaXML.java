import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ConversorCSVaXML {
    private static final Logger logger = LoggerFactory.getLogger(ConversorCSVaXML.class);

    // Método para leer el archivo CSV y generar un archivo XML
    public void convertirCSVaXML(String archivoCSV, String archivoXML) {
        logger.info("Iniciando la conversión de CSV a XML...");

        try (CSVReader reader = new CSVReader(new FileReader(archivoCSV))) {
            List<String[]> filas = reader.readAll();
            if (filas.isEmpty() || filas.get(0).length != 5) {
                logger.error("El archivo CSV no tiene el formato correcto.");
                throw new IllegalArgumentException("El archivo CSV no tiene el formato correcto.");
            }

            // Crear el documento XML
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // Elemento raíz <Estudiantes>
            Element rootElement = doc.createElement("Estudiantes");
            doc.appendChild(rootElement);

            // Procesar las filas del CSV
            for (int i = 1; i < filas.size(); i++) { // Ignorar la primera fila (encabezado)
                String[] datos = filas.get(i);

                Element estudiante = doc.createElement("Estudiante");

                // Crear y agregar los elementos al XML
                Element id = doc.createElement("ID");
                id.appendChild(doc.createTextNode(datos[0]));
                estudiante.appendChild(id);

                Element nombre = doc.createElement("Nombre");
                nombre.appendChild(doc.createTextNode(datos[1]));
                estudiante.appendChild(nombre);

                Element apellido = doc.createElement("Apellido");
                apellido.appendChild(doc.createTextNode(datos[2]));
                estudiante.appendChild(apellido);

                Element edad = doc.createElement("Edad");
                edad.appendChild(doc.createTextNode(datos[3]));
                estudiante.appendChild(edad);

                Element curso = doc.createElement("Curso");
                curso.appendChild(doc.createTextNode(datos[4]));
                estudiante.appendChild(curso);

                // Agregar <Estudiante> a <Estudiantes>
                rootElement.appendChild(estudiante);
            }

            // Guardar el archivo XML
            GuardarXML.guardar(doc, archivoXML);
            logger.info("Conversión finalizada. Archivo XML generado: {}", archivoXML);

        } catch (IOException | ParserConfigurationException e) {
            logger.error("Error al procesar el archivo CSV", e);
        }
    }
}
