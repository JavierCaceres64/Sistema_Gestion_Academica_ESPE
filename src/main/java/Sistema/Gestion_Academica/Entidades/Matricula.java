package Sistema.Gestion_Academica.Entidades;
/**
 * @author Javier
 */
public class Matricula {
    
    //Atributos
    
    private String id, periodo, fecha, estado;
    private double nota;
    private Estudiante estudiante;
    private Asignatura asignatura;
    
    // Constructores
    
    public Matricula() {
    }
    public Matricula(Estudiante estudiante, Asignatura asignatura, 
            String periodo, String fecha) {
        this.estudiante = estudiante;
        this.asignatura = asignatura;
        this.periodo = periodo;
        this.fecha = fecha;
        this.estado = "ACTIVA";
        this.nota = 0;
    }
    
    //Getters & Setters
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPeriodo() {
        return periodo;
    }
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public double getNota() {
        return nota;
    }
    public void setNota(double nota) {
        this.nota = nota;
    }
    public Estudiante getEstudiante() {
        return estudiante;
    }
    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
    public Asignatura getAsignatura() {
        return asignatura;
    }
    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }
}
