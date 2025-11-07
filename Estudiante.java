import java.util.*;

public class Estudiante extends Socio {

    // Atributos
    private String carrera;

    // Constructor
    public Estudiante (int p_dniSocio, String p_nombre, int p_diasPrestamo, String p_carrera){
        super(p_dniSocio, p_nombre, p_diasPrestamo);
        this.setCarrera(p_carrera);
    }

    // Setters y Getters
    public String getCarrera(){
        return this.carrera;
    }

    private void setCarrera(String p_carrera){
        this.carrera = p_carrera;
    }

    // Métodos
    public boolean puedePedir(){
        Calendar hoy = Calendar.getInstance();
        for (Prestamo prestamo : this.getPrestamos()) {
            if (prestamo.vencido(hoy)) {
                return false;
            }
        }
        return this.cantLibrosPrestados() < 3;
    }

    public String soyDeLaClase(){
        return "Estudiante";
    }

}