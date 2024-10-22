import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestorContactos {
    private static final String FILE_NAME = "contactos.txt";

    // Método para añadir contacto
    public void anadirContacto(Contacto contacto) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            writer.write(contacto.getNombre() + "," + contacto.getTelefono() + "," + contacto.getEmail() + "\n");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo.");
        }
    }

    // Método para listar contactos
    public List<Contacto> listarContactos() {
        List<Contacto> contactos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 3) {
                    contactos.add(new Contacto(datos[0], datos[1], datos[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo.");
        }
        return contactos;
    }

    // Método para buscar un contacto por nombre
    public Contacto buscarContacto(String nombre) {
        List<Contacto> contactos = listarContactos();
        for (Contacto contacto : contactos) {
            if (contacto.getNombre().equalsIgnoreCase(nombre)) {
                return contacto;
            }
        }
        return null;
    }

    // Método para eliminar un contacto por nombre
    public void eliminarContacto(String nombre) {
        List<Contacto> contactos = listarContactos();
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            for (Contacto contacto : contactos) {
                if (!contacto.getNombre().equalsIgnoreCase(nombre)) {
                    writer.write(contacto.getNombre() + "," + contacto.getTelefono() + "," + contacto.getEmail() + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo.");
        }
    }
}
