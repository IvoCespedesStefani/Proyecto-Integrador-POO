import java.util.ArrayList;
import java.util.Calendar;

public class Libro {
    //Atributos 
    private String titulo;
    private String editorial;
    private int anio;
    private int edicion;
    private ArrayList<Prestamo> prestamos;

    // Constructores
    public Libro(String p_titulo, String p_editorial, int p_anio, int p_edicion) {
        this.setTitulo(p_titulo);
        this.setEditorial(p_editorial);
        this.setAnio(p_anio);
        this.setEdicion(p_edicion);
        this.prestamos = new ArrayList<Prestamo>();
    }

    //Setters y getters
    public String getTitulo(){ 
        return this.titulo; 
    }
    
    private void setTitulo(String p_titulo){
        this.titulo = p_titulo;
    }

    public String getEditorial(){
        return this.editorial; 
    }
    
    private void setEditorial(String p_editorial){
        this.editorial = p_editorial;
    }

    public int getAnio(){
        return this.anio;
    }
    
    private void setAnio(int p_anio){
        this.anio = p_anio; 
    }

    public int getEdicion(){ 
        return this.edicion; 
    }
    
    private void setEdicion(int p_edicion){
        this.edicion = p_edicion; 
    }

    public ArrayList<Prestamo> getPrestamos(){
        return this.prestamos; 
    }

    //Metodos
    public void agregarPrestamo(Prestamo p_prestamo) {
        this.prestamos.add(p_prestamo);
    }

    public Prestamo ultimoPrestamo() {
        if (this.prestamos.isEmpty()) {
            return null;
        } else {
            return this.prestamos.get(this.prestamos.size() - 1);
        }
    }

    public boolean prestado() {
        Prestamo ultimoPrestamo = this.ultimoPrestamo();
        return (ultimoPrestamo != null && ultimoPrestamo.getFechaDevolucion() == null);
    }

    @Override
    public String toString() {
        return "Titulo: " + this.getTitulo();
    }
}