import java.util.*;

/**
 * Ejecutable del sistema de Gestión de Biblioteca
 * Adaptado para el código actual de Ivo (sin modificar las clases del modelo)
 */
public class GestionBiblioteca {

    private static Scanner sc = new Scanner(System.in);
    private static Biblioteca biblioteca = new Biblioteca("Biblioteca Central", null, null);

    public static void main(String[] args) {
        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");
            System.out.println();

            try {
                switch (opcion) {
                    case 1 -> registrarLibro();
                    case 2 -> registrarSocio();
                    case 3 -> prestarLibro();
                    case 4 -> devolverLibro();
                    case 5 -> listarSocios();
                    case 6 -> listarLibros();
                    case 7 -> listarTitulos();
                    case 8 -> listarDocentesResponsables();
                    case 9 -> listarPrestamosVencidos();
                    case 0 -> System.out.println("Saliendo del sistema...");
                    default -> System.out.println("Opción inválida. Intente nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println();
        } while (opcion != 0);

        sc.close();
    }

    // ===================== MÉTODOS DEL MENÚ =====================

    private static void mostrarMenu() {
        System.out.println("========== GESTIÓN DE BIBLIOTECA ==========");
        System.out.println("1. Registrar nuevo libro");
        System.out.println("2. Registrar nuevo socio");
        System.out.println("3. Prestar libro");
        System.out.println("4. Devolver libro");
        System.out.println("5. Listar socios");
        System.out.println("6. Listar libros");
        System.out.println("7. Listar títulos únicos");
        System.out.println("8. Listar docentes responsables");
        System.out.println("9. Mostrar préstamos vencidos");
        System.out.println("0. Salir");
        System.out.println("==========================================");
    }

    private static void registrarLibro() {
        System.out.println("--- Registrar Libro ---");
        System.out.print("Título: ");
        String titulo = sc.nextLine();
        int edicion = leerEntero("Edición: ");
        System.out.print("Editorial: ");
        String editorial = sc.nextLine();
        int anio = leerEntero("Año: ");

        biblioteca.nuevoLibro(titulo, edicion, editorial, anio);
        System.out.println("Libro registrado con éxito.");
    }

    private static void registrarSocio() {
        System.out.println("--- Registrar Socio ---");
        System.out.println("1. Docente");
        System.out.println("2. Estudiante");
        int tipo = leerEntero("Seleccione el tipo de socio: ");

        int dni = leerEntero("DNI: ");
        System.out.print("Nombre completo: ");
        String nombre = sc.nextLine();

        if (tipo == 1) {
            System.out.print("Área: ");
            String area = sc.nextLine();
            biblioteca.nuevoSocioDocente(dni, nombre, area);
        } else if (tipo == 2) {
            System.out.print("Carrera: ");
            String carrera = sc.nextLine();
            biblioteca.nuevoSocioEstudiante(dni, nombre, carrera);
        } else {
            System.out.println("Tipo inválido. Socio no registrado.");
            return;
        }

        System.out.println("Socio registrado con éxito.");
    }

    private static void prestarLibro() throws Exception {
        System.out.println("--- Prestar Libro ---");
        int dni = leerEntero("DNI del socio: ");
        Socio socio = biblioteca.buscarSocio(dni);

        if (socio == null) {
            System.out.println("Socio no encontrado.");
            return;
        }

        System.out.print("Título del libro: ");
        String titulo = sc.nextLine();
        Libro libro = buscarLibroInterno(titulo);

        if (libro == null) {
            System.out.println("Libro no encontrado.");
            return;
        }

        Calendar hoy = Calendar.getInstance();
        biblioteca.prestarLibro(hoy, socio, libro);
        System.out.println("Préstamo registrado exitosamente.");
    }

    private static void devolverLibro() {
        System.out.println("--- Devolver Libro ---");
        System.out.print("Título del libro: ");
        String titulo = sc.nextLine();
        Libro libro = buscarLibroInterno(titulo);

        if (libro == null) {
            System.out.println("Libro no encontrado.");
            return;
        }

        try {
            biblioteca.devolverLibro(libro);
            System.out.println("Libro devuelto correctamente.");
        } catch (LibroNoPrestadoException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listarSocios() {
        System.out.println("--- Lista de Socios ---");
        System.out.println(biblioteca.listaDeSocios());
    }

    private static void listarLibros() {
        System.out.println("--- Lista de Libros ---");
        System.out.println(biblioteca.listaDeLibros());
    }

    private static void listarTitulos() {
        System.out.println("--- Lista de Títulos (únicos) ---");
        ArrayList<String> titulos = new ArrayList<>();
        try {
            // Intentamos obtener la lista de libros internamente
            var field = Biblioteca.class.getDeclaredField("libros");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            ArrayList<Libro> libros = (ArrayList<Libro>) field.get(biblioteca);

            for (Libro libro : libros) {
                if (!titulos.contains(libro.getTitulo())) {
                    titulos.add(libro.getTitulo());
                }
            }
        } catch (Exception e) {
            System.out.println("No se pudo acceder a la lista de libros.");
        }

        if (titulos.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            for (String t : titulos) {
                System.out.println("- " + t);
            }
        }
    }

    private static void listarDocentesResponsables() {
        System.out.println("--- Docentes Responsables ---");
        System.out.println(biblioteca.listaDeDocentesResponsables());
    }

    private static void listarPrestamosVencidos() {
        System.out.println("--- Préstamos Vencidos ---");
        ArrayList<Prestamo> vencidos = biblioteca.prestamosVencidos();

        if (vencidos.isEmpty()) {
            System.out.println("No hay préstamos vencidos al día de la fecha.");
        } else {
            for (Prestamo p : vencidos) {
                System.out.println(p);
                System.out.println("----------------------");
            }
        }
    }

    // ===================== MÉTODOS AUXILIARES =====================

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!sc.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            sc.next();
        }
        int valor = sc.nextInt();
        sc.nextLine(); 
        return valor;
    }

    // Búsqueda interna del libro por título 
    private static Libro buscarLibroInterno(String titulo) {
        try {
            var field = Biblioteca.class.getDeclaredField("libros");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            ArrayList<Libro> libros = (ArrayList<Libro>) field.get(biblioteca);

            for (Libro libro : libros) {
                if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                    return libro;
                }
            }
        } catch (Exception e) {
            System.out.println("No se pudo acceder a los libros internos.");
        }
        return null;
    }
}
