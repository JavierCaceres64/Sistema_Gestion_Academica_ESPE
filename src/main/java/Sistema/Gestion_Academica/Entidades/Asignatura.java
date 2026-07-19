package Sistema.Gestion_Academica.Entidades;
/**
 * @author Javier
 */
public class Asignatura {
    
    //Atributos
    
    private String id, codigo, nombre;
    private int creditos;
    private Docente docente;
    
    // Constructores
    
    public Asignatura() {
    }
    public Asignatura(String codigo, String nombre, int creditos) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.creditos = creditos;
    }
    
    //Getters & Setters
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getCreditos() {
        return creditos;
    }
    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }
    public Docente getDocente() {
        return docente;
    }
    public void setDocente(Docente docente) {
        this.docente = docente;
    }
    
    @Override
    public String toString() {
        return codigo + " - " + nombre;
    }
}

