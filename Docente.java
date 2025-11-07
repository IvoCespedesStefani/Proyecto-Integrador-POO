import java.util.*;

public class Docente extends Socio {

    // Atributos
    private String area;

    // Constructor
    public Docente (int p_dniSocio, String p_nombre, int p_diasPrestamo, String p_area){
        super(p_dniSocio, p_nombre, p_diasPrestamo);
        this.setArea(p_area);
    }

    //Setters y Getters
    public String getArea(){
        return this.area;
    }

    private void setArea(String p_area){
        this.area = p_area;
    }

    // Métodos
    public boolean esResponsable(){
        Calendar hoy = Calendar.getInstance();
        for (Prestamo prestamo : this.getPrestamos()) {
            if (prestamo.vencido(hoy)) {
                return false;
            }
        }
        return true;
    }

    public void cambiarDiasDePrestamo(int p_dias){
        this.setDiasPrestamo(this.getDiasPrestamo() + p_dias);
    }

    public boolean puedePedir(){
        return this.esResponsable();
    }

    public String soyDeLaClase(){
        return "Docente";
    }

}
