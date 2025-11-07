import java.util.ArrayList;
import java.util.Calendar;
/**
 * Representa una biblioteca que administra socios y libros.
 * Permite registrar socios, agregar libros y gestionar préstamos.
 */
public class Biblioteca {
    //Atributos privados
    private String nombre;
    private ArrayList <Socio> socios;
    private ArrayList <Libro> libros;
    
    /**
     * Crea una biblioteca con un nombre, una lista inicial de socios y una lista inicial de libros.
     * @param p_nombre nombre de la biblioteca
     * @param p_socios lista de socios registrados inicialmente
     * @param p_libros lista de libros disponibles inicialmente
     */
    public Biblioteca(String p_nombre, ArrayList <Socio> p_socios, ArrayList <Libro> p_libros) {
        this.setNombre(p_nombre);
        this.setSocios(p_socios);
        this.setLibros(p_libros);
    }
    
    /**
     * Crea una biblioteca con un nombre y con las listas vacías
     * @param p_nombre nombre de la biblioteca
     * @param p_socios lista de socios vacía
     * @param p_libros lista de libros vacía
     */
    public Biblioteca(String p_nombre, Socio p_socios, Libro p_libros) {
        this.setNombre(p_nombre);
        this.setSocios(new ArrayList<>());
        this.setLibros(new ArrayList<>());
    }
    
    //Getters
    public String getNombre() {
        return this.nombre;
    }
    
    public ArrayList <Socio> getSocios() {
        return this.socios;
    }
    
    public ArrayList <Libro> getLibros() {
        return this.libros;
    }
    
    //Setters
    private void setNombre(String p_nombre) {
        this.nombre = p_nombre;
    }
    
    private void setSocios(ArrayList <Socio> p_socios) {
        this.socios = p_socios;
    }
    
    private void setLibros(ArrayList <Libro> p_libros) {
        this.libros = p_libros;
    }
    
    /**
     * Método para agregar un Libro
     * @param p_titulo Título
     * @param p_edicion Edición
     * @param p_editorial Editorial
     * @param p_anio Año
     */
    public void nuevoLibro(String p_titulo, int p_edicion, String p_editorial, int p_anio) {
        Libro libro = new Libro(p_titulo, p_editorial, p_anio, p_edicion);
        this.getLibros().add(libro);
    }
    
    /**
     * Método para agregar un nuevo Socio de tipo Estudiante
     * @param p_dniSocio DNI
     * @param p_nombre Nombre
     * @param p_carrera Carrera
     */
    public void nuevoSocioEstudiante(int p_dniSocio, String p_nombre, String p_carrera) {
        Estudiante estudiante = new Estudiante(p_dniSocio, p_nombre, 20, p_carrera);
        this.getSocios().add(estudiante);
    }
    
    /**
     * Método para agregar un nuevo Socio de tipo Docente
     * @param p_dniSocio DNI
     * @param p_nombre Nombre
     * @param p_area Área
     */
    public void nuevoSocioDocente(int p_dniSocio, String p_nombre, String p_area) {
        Docente docente = new Docente(p_dniSocio, p_nombre, 5, p_area);
        this.getSocios().add(docente);
    }
    
    /**
     * Método que crea un préstamo y lo agrega en el libro y el socio
     * @param p_fechaRetiro Fecha de retiro
     * @param p_socio Parámetro de tipo Socio
     * @param p_libro Parámetro de tipo Libro
     * @return devuelve un boolean
     */
    public boolean prestarLibro(Calendar p_fechaRetiro, Socio p_socio, Libro p_libro) {
        if (!p_libro.prestado() && p_socio.puedePedir()) {
            Prestamo nuevoPrestamo = new Prestamo(p_fechaRetiro, p_socio, p_libro);
            p_libro.agregarPrestamo(nuevoPrestamo);
            p_socio.addPrestamo(nuevoPrestamo);
            return true;
        }
        return false;
    }
    
    /**
     * Asigna la fecha de devolución del préstamo con la fecha actual.
     * Lanza una excepción si el libro no está prestado.
     */
    public void devolverLibro(Libro p_libro) throws LibroNoPrestadoException {
        if (!p_libro.prestado()) {
            throw new LibroNoPrestadoException(
                "El libro " + p_libro.getTitulo() + " no se puede devolver ya que se encuentra en la biblioteca.");
        }
        Prestamo prestamo = p_libro.ultimoPrestamo();
        prestamo.registrarFechaDevolucion(Calendar.getInstance());
    }

    /**
     * Método que asigna la fecha de devolución del préstamo con la fecha actual
     * @param p_libro Parámetro del tipo Libro
     * @return cantidad de socios por tipo
     */
    public int cantidadDeSociosPorTipo(String p_tipo) {
        int contador = 0;
        for (Socio s : this.getSocios()) {
            if (s.soyDeLaClase().equalsIgnoreCase(p_tipo)) {
                contador++;
            }
        }
        return contador;
    }
    
    /**
     * Método que devuelve una colección con los préstamos vencidos
     * @return un ArrayList de los préstamos vencidos
     */
    public ArrayList<Prestamo> prestamosVencidos() {
        ArrayList<Prestamo> vencidos = new ArrayList<>();
        Calendar hoy = Calendar.getInstance();

        for (Socio socio : this.getSocios()) {
            for (Prestamo prestamo : socio.getPrestamos()) {
                if (prestamo.vencido(hoy) && prestamo.getFechaDevolucion() == null) {
                    vencidos.add(prestamo);
                }
            }
        }
        return vencidos;
    }
    
    /**
     * Método que realiza la búsqueda de un socio según el documento DNI
     * @param p_dni DNI
     * @return devuelve el socio encontrado por dni
     */
    public Socio buscarSocio(int p_dni) {
        for (Socio s : this.getSocios()) {
            if (s.getDniSocio() == p_dni) {
                return s;
            }
        }
        return null;
    }
    
    /**
     * Método que devuelve una cadena de lista de socios
     * @return string con formato
     */
    public String listaDeSocios() {
        String lista = "Lista de Socios:\n";
        for (int i = 0; i < this.getSocios().size(); i++) {
            Socio s = this.getSocios().get(i);
            lista += (i + 1) + ") " + s.toString() + " || Libros Prestados: " + s.cantLibrosPrestados() + "\n";
        }
        return lista;
    }
    
    /**
     * Método que devuelve una cadena una lista de libros
     * @return string con formato
     */
    public String listaDeLibros() {
        String lista = "Lista de Libros:\n";
        for (int i = 0; i < this.getLibros().size(); i++) {
            Libro l = this.getLibros().get(i);
            lista += (i + 1) + ") " + l.toString() + " || Prestado: (" + (l.prestado() ? "Si" : "No") + ")\n";
        }
        return lista;
    }

    /**
     * Método que devuelve una cadena una lista de los docentes responsables
     * @return string con formato
     */
    public String listaDeDocentesResponsables() {
        String lista = "Lista de Docentes Responsables:\n";
        for (int i = 0; i < this.getSocios().size(); i++) {
            Socio s = this.getSocios().get(i);
            if (s.soyDeLaClase().equals("Docente")) {
                Docente d = (Docente) s;
                if (d.esResponsable()) {
                    lista += "* " + d.toString() + " || Libros Prestados: " + d.cantLibrosPrestados() + "\n";
                }
            }
        }
        return lista;
    }
}
