/**
 * Excepción personalizada que se lanza cuando se intenta devolver
 * un libro que no está prestado.
 */
public class LibroNoPrestadoException extends Exception {
    /**
     * Constructor que recibe un mensaje descriptivo del error.
     * @param mensaje texto que explica la causa del error.
     */
    public LibroNoPrestadoException(String mensaje) {
        super(mensaje);
    }
}