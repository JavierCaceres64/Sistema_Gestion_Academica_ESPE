package Sistema.Gestion_Academica.Excepciones;
/**
 * @author Javier
 */
public class Conexion_BD extends Exception {
    public Conexion_BD(String mensaje) {
        super(mensaje);
    }
}
