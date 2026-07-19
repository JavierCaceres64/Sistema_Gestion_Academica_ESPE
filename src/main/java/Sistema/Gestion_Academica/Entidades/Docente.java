package Sistema.Gestion_Academica.Entidades;
/**
 * @author Javier
 */
public class Docente extends Usuario {
    
    //Atributos 
    
    private String departamento, especialidad;
    
    //Constructores

    public Docente() {
        super();
    }

    public Docente(String departamento, String especialidad, String nombres,
            String apellidos, String direccion, String correo,
            String cedula, String id) {
        super(nombres, apellidos, direccion, correo, cedula, id);
        this.departamento = departamento;
        this.especialidad = especialidad;
    }
    
    //Getters & Setters 

    public String getDepartamento() {
        return departamento;
    }
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    public String getEspecialidad() {
        return especialidad;
    }
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }  
    
    //Metodos

    @Override
    public String obtenerRol() {
        return "Docente";
    }
}