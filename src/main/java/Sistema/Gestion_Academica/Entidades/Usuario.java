package Sistema.Gestion_Academica.Entidades;
/**
 * @author Javier
 */
public abstract class Usuario {
    
    //Atributos
    
    private String nombres, apellidos, direccion, correo, cedula, id;
    
    // Constructores

    public Usuario() {
    }

    public Usuario(String nombres, String apellidos, 
           String direccion, String correo, String cedula,String id) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.correo = correo;
        this.cedula = cedula;
        this.id = id;
    }
    
    //Getters & Setters 

    public String getNombres() {
        return nombres;
    }
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getCedula() {
        return cedula;
    }
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    //Metodos
    
    public abstract String obtenerRol();
    
    public String obtenerNombreCompleto() {
        return nombres + " " + apellidos;
    }
    
    @Override
    public String toString() {
        return obtenerRol() + ": " + obtenerNombreCompleto();
    }
}