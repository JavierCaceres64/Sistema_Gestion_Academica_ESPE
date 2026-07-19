package Sistema.Gestion_Academica.Entidades;
/**
 * @author Javier
 */
public class Estudiante extends Usuario  {

    //Atributos propios
    
    private String carrera;
    private int semestre;
    
    // Constructores

    public Estudiante() {
        super();
    }


    public Estudiante(String carrera, int semestre, String nombres,
            String apellidos, String direccion, String correo,
            String cedula, String id) {
        super(nombres, apellidos, direccion, correo, cedula, id);
        this.carrera = carrera;
        this.semestre = semestre;
    }
    
    //Getters & Setters 

    public String getCarrera() {
        return carrera;
    }
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
    public int getSemestre() {
        return semestre;
    }
    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }
    
    //Metodos

    @Override
    public String obtenerRol() {
        return "Estudiante";
    }
    
}