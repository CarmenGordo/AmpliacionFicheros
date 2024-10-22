import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GestorContactos gestor = new GestorContactos();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("Gestor de Contactos");
            System.out.println("1. Añadir contacto");
            System.out.println("2. Listar contactos");
            System.out.println("3. Buscar contacto");
            System.out.println("4. Eliminar contacto");
            System.out.println("5. Salir");
            System.out.print("Elige una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (opcion) {
                case 1:
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Teléfono: ");
                    String telefono = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    Contacto nuevoContacto = new Contacto(nombre, telefono, email);
                    gestor.anadirContacto(nuevoContacto);
                    System.out.println("Contacto añadido.");
                    break;
                case 2:
                    List<Contacto> contactos = gestor.listarContactos();
                    for (Contacto contacto : contactos) {
                        System.out.println(contacto);
                    }
                    break;
                case 3:
                    System.out.print("Introduce el nombre a buscar: ");
                    nombre = scanner.nextLine();
                    Contacto contactoBuscado = gestor.buscarContacto(nombre);
                    if (contactoBuscado != null) {
                        System.out.println(contactoBuscado);
                    } else {
                        System.out.println("Contacto no encontrado.");
                    }
                    break;
                case 4:
                    System.out.print("Introduce el nombre a eliminar: ");
                    nombre = scanner.nextLine();
                    gestor.eliminarContacto(nombre);
                    System.out.println("Contacto eliminado si existía.");
                    break;
                case 5:
                    System.out.println("Saliendo del gestor...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 5);
    }
}
