public class Main {
    public static void main(String[] args) {
        ConversorCSVaXML conversor = new ConversorCSVaXML();
        conversor.convertirCSVaXML("estudiantes.csv", "estudiantes.xml");
    }
}