import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Contacto implements Serializable {
    private String nombre;
    private String telefono;

    public Contacto(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Teléfono: " + telefono;
    }
}

public class AgendaTelefonica {
    private static final String ARCHIVO = "agenda.dat";
    private static ArrayList<Contacto> agenda = new ArrayList<>();

    public static void main(String[] args) {
        cargarAgenda();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- Menú de Agenda Telefónica ---");
            System.out.println("1. Añadir contacto");
            System.out.println("2. Eliminar contacto");
            System.out.println("3. Mostrar agenda");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    añadirContacto(scanner);
                    break;
                case 2:
                    eliminarContacto(scanner);
                    break;
                case 3:
                    mostrarAgenda();
                    break;
                case 4:
                    guardarAgenda();
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 4);

        scanner.close();
    }

    private static void añadirContacto(Scanner scanner) {
        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el teléfono: ");
        String telefono = scanner.nextLine();
        agenda.add(new Contacto(nombre, telefono));
        System.out.println("Contacto añadido con éxito.");
    }

    private static void eliminarContacto(Scanner scanner) {
        System.out.print("Ingrese el nombre del contacto a eliminar: ");
        String nombre = scanner.nextLine();
        boolean eliminado = agenda.removeIf(contacto -> contacto.getNombre().equalsIgnoreCase(nombre));
        if (eliminado) {
            System.out.println("Contacto eliminado con éxito.");
        } else {
            System.out.println("No se encontró un contacto con ese nombre.");
        }
    }

    private static void mostrarAgenda() {
        if (agenda.isEmpty()) {
            System.out.println("La agenda está vacía.");
        } else {
            System.out.println("\n--- Lista de Contactos ---");
            for (Contacto contacto : agenda) {
                System.out.println(contacto);
            }
        }
    }

    private static void cargarAgenda() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList) {
                agenda = (ArrayList<Contacto>) obj;
            } else {
                System.out.println("El archivo no es compatible. Se inicializará una nueva agenda.");
                agenda = new ArrayList<>();
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró un archivo de agenda. Se creará uno nuevo.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar la agenda: " + e.getMessage());
            agenda = new ArrayList<>();
        }
    }

    private static void guardarAgenda() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            oos.writeObject(agenda);
            System.out.println("Agenda guardada con éxito.");
        } catch (IOException e) {
            System.out.println("Error al guardar la agenda: " + e.getMessage());
        }
    }
}