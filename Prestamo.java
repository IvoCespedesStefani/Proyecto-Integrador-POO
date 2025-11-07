import java.util.*;

public class Prestamo {
    private Calendar fechaRetiro;
    private Calendar fechaDevolucion;
    private Socio socio;
    private Libro libro;

    public Prestamo(Calendar p_fechaRetiro, Socio p_socio, Libro p_libro) {
        this.setFechaRetiro(p_fechaRetiro);
        this.setSocio(p_socio);
        this.setLibro(p_libro);
    }

    private void setFechaRetiro(Calendar p_fechaRetiro) {
        this.fechaRetiro = p_fechaRetiro;
    }

    public Calendar getFechaRetiro() {
        return this.fechaRetiro;
    }

    private void setFechaDevolucion(Calendar p_fechaDevolucion) {
        this.fechaDevolucion = p_fechaDevolucion;
    }

    public void registrarFechaDevolucion(Calendar p_fechaDevolucion) {
        this.setFechaDevolucion(p_fechaDevolucion);
    }

    public Calendar getFechaDevolucion() {
        return this.fechaDevolucion;
    }

    private void setSocio(Socio p_socio) {
        this.socio = p_socio;
    }

    public Socio getSocio() {
        return this.socio;
    }

    private void setLibro(Libro p_libro) {
        this.libro = p_libro;
    }

    public Libro getLibro() {
        return this.libro;
    }

    public boolean vencido(Calendar p_fecha) {
        
        //Se crea un nuevo Calendar para no modificar la fecha original del prestamo
        Calendar fechaVencimiento = Calendar.getInstance();
        
        //se extrae el dia, mes y año, de la fecha en que se retiro el libro.
        int day = this.getFechaRetiro().get(Calendar.DAY_OF_MONTH);
        int month = this.getFechaRetiro().get(Calendar.MONTH);
        int year = this.getFechaRetiro().get(Calendar.YEAR);

        //se asigna esa fecha al nuevo Calendar(fechaVencimiento).
        fechaVencimiento.set(year, month, day);
        
        //se agregar los dias de prestamo parmitidos segun el socio
        fechaVencimiento.add(Calendar.DAY_OF_MONTH, this.getSocio().getDiasPrestamo());
        
        //se comapra la ffecha recibida (p_fehca) con la fecha de vencimiento
        //si p_fecha es despies de fechaVencimiento, significa que YA SE PASO: esta vencido
        return p_fecha.after(fechaVencimiento);
    }

    public String toString(){
        String fechaR = this.getFechaRetiro().get(Calendar.DATE) + "/" + (this.getFechaRetiro().get(Calendar.MONTH) + 1) + "/" + 
                        this.getFechaRetiro().get(Calendar.YEAR);
        
        if(this.getFechaDevolucion() != null){
            String fechaD = this.getFechaDevolucion().get(Calendar.DATE) + "/" + (this.getFechaDevolucion().get(Calendar.MONTH) + 1) + "/" + 
                        this.getFechaDevolucion().get(Calendar.YEAR);
                        
            return "Retiro: " + fechaR + " - Devolucion: " + fechaD + "\nLibro: " + this.getLibro().getTitulo() + "\nSocio: " + 
                    this.getSocio().getNombre();
        } else {
            return "Retiro: " + fechaR + " - Devolucion: No ha sido devuelto" + "\nLibro: " + this.getLibro().getTitulo() + "\nSocio: " + 
                    this.getSocio().getNombre();
        }
    }
}   