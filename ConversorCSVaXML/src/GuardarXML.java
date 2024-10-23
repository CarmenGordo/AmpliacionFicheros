import org.w3c.dom.Document;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class GuardarXML {
    public static void guardar(Document doc, String archivoXML) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(archivoXML));

            transformer.transform(source, result);
            System.out.println("Archivo XML guardado en " + archivoXML);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}