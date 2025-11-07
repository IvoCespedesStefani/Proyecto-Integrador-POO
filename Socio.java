import java.util.*;

/**
 * Clase abstracta que representa un socio de la biblioteca.
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Socio
{
    private int dniSocio;
    private String nombre;
    private int diasPrestamo;
    private ArrayList <Prestamo> prestamos;

    /**
     * Constructor de Socio.
     * Crea un socio con DNI, nombre y cantidad de días de prestamo asignados.
     * Inicializa la lista de prestamos como una lista vacía.
     * @param p_dniSocio dni del socio
     * @param p_nombre nombre completo del socio
     * @param p_diasPrestamo Cantidad de días de prestamo asignados al socio
     */
    public Socio(int p_dniSocio, String p_nombre, int p_diasPrestamo)
    {
        this.setDniSocio(p_dniSocio);
        this.setNombre(p_nombre);
        this.setDiasPrestamo(p_diasPrestamo);
        this.setPrestamos(new ArrayList<Prestamo> ());
    }
    
     /**
     * Constructor de Socio.
     * Crea un socio con DNI, nombre y cantidad de días de prestamo asignados.
     * una lista de prestamos preexistentes. 
     * @param p_dniSocio dni del socio
     * @param p_nombre nombre completo del socio
     * @param p_diasPrestamo Cantidad de días de prestamo asignados al socio
     */
    public Socio(int p_dniSocio, String p_nombre, int p_diasPrestamo, ArrayList <Prestamo> p_prestamos)
    {
        this.setDniSocio(p_dniSocio);
        this.setNombre(p_nombre);
        this.setDiasPrestamo(p_diasPrestamo);
        this.setPrestamos(p_prestamos);
    }
    
    //SETTERS
    private void setDniSocio(int p_dniSocio){
        this.dniSocio = p_dniSocio;
    }
    
    private void setNombre(String p_nombre){
        this.nombre = p_nombre;
    }
    
    public void setDiasPrestamo(int p_dias){
        this.diasPrestamo = p_dias;
    }
    
    private void setPrestamos(ArrayList <Prestamo> p_prestamos){
        this.prestamos = p_prestamos;
    }
    
    //GETTERS
    public int getDniSocio(){
        return this.dniSocio;
    }

    public String getNombre(){
        return this.nombre;
    }
    
    public int getDiasPrestamo(){
        return this.diasPrestamo;
    }
    
    public ArrayList <Prestamo> getPrestamos(){
        return this.prestamos;
    }
  
    //METODOS
    public boolean addPrestamo(Prestamo p_prestamo){
        return this.getPrestamos().add(p_prestamo);
    }
    
    public boolean removePrestamo(Prestamo p_prestamo){
         return this.getPrestamos().remove(p_prestamo);
    }
    
    /*public int cantLibrosPrestados(){
        return this.getPrestamos().size();
    }*/
    
    /** Devuelve la cantidad de libros actualmente prestados al socio
     * @return numero de prestamos activos
     */
    public int cantLibrosPrestados(){
        int total = 0;
        for(Prestamo p: this.getPrestamos()){
            //solo se cuenta los prestmaos que aun no fueron devueltos
            if(p.getFechaDevolucion() == null){
                total++;
            }
        }
        return total;
    }
    
     /**
     * Devuelve true si el socio puede pedir un libro (no tiene prestamos vencidos)
     * @return true si puede pedir
     */
    public boolean puedePedir(){
        Calendar fechaHoy = Calendar.getInstance();
        
        //recorre todos los prestmamos de socio
        for(Prestamo p: this.getPrestamos()){
            
            //si el prestamo esta acivo y vencido, no puede pedir otro libro
            if(p.getFechaDevolucion() == null && p.vencido(fechaHoy)){
                return false;
            }
        }
        return true; //si ningun prestamo esta vencido, puede pedir
    }
    
    /**
     * Representacion en String del socio (Formato del enunciado)
     */
    public String toString(){
        return ("D.N.I.: " + this.getDniSocio() + " || " + this.getNombre() + 
        " (" + this.soyDeLaClase() + ") || Libros Prestados: " + this.cantLibrosPrestados());
    }

    /**
     * Metodo abstracto que debe implementar cada subclase para indicar su tipo
     * @return "Docente" o "Estudiante"
     */
    public abstract String soyDeLaClase();
}