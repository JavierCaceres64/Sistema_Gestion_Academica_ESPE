package Sistema.Gestion_Academica.DAO_Implementacion;

import Sistema.Gestion_Academica.DAO.Estudiante_DAO;
import Sistema.Gestion_Academica.Entidades.Estudiante;
import Sistema.Gestion_Academica.Util.Conexion_Mongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 * @author Javier
 */
public class Estudiante_DAO_Mongo implements Estudiante_DAO {
    
    //Atributos
    
    private MongoCollection<Document> coleccion;
    
    // Constructor
    public Estudiante_DAO_Mongo() {
        this.coleccion = Conexion_Mongo.getInstancia()
                .getBaseDatos()
                .getCollection("estudiantes");
    }
    
    @Override
    public void guardar(Estudiante estudiante) {
        Document doc = new Document("cedula", estudiante.getCedula())
                .append("nombres", estudiante.getNombres())
                .append("apellidos", estudiante.getApellidos())
                .append("direccion", estudiante.getDireccion())
                .append("correo", estudiante.getCorreo())
                .append("carrera", estudiante.getCarrera())
                .append("semestre", estudiante.getSemestre());
        
        coleccion.insertOne(doc);
    }
    
    @Override
    public List<Estudiante> listar() {
        List<Estudiante> lista = new ArrayList<>();
        
        for (Document doc : coleccion.find()) {
            lista.add(convertirAEstudiante(doc));
        }
        return lista;
    }
    
    @Override
    public Estudiante buscarPorCedula(String cedula) {
        Document doc = coleccion.find(Filters.eq("cedula", cedula)).first();
        
        if (doc == null) {
            return null;
        }
        return convertirAEstudiante(doc);
    }
    
    @Override
    public void actualizar(Estudiante estudiante) {
        Document nuevosDatos = new Document("nombres", estudiante.getNombres())
                .append("apellidos", estudiante.getApellidos())
                .append("direccion", estudiante.getDireccion())
                .append("correo", estudiante.getCorreo())
                .append("carrera", estudiante.getCarrera())
                .append("semestre", estudiante.getSemestre());
        
        coleccion.updateOne(Filters.eq("cedula", estudiante.getCedula()),
                            new Document("$set", nuevosDatos));
    }
    
    @Override
    public void eliminar(String cedula) {
        coleccion.deleteOne(Filters.eq("cedula", cedula));
    }
    
    // Método auxiliar: convierte un documento de Mongo en un objeto Estudiante
    private Estudiante convertirAEstudiante(Document doc) {
        Estudiante estudiante = new Estudiante();
        
        estudiante.setId(doc.getObjectId("_id").toString());
        estudiante.setCedula(doc.getString("cedula"));
        estudiante.setNombres(doc.getString("nombres"));
        estudiante.setApellidos(doc.getString("apellidos"));
        estudiante.setDireccion(doc.getString("direccion"));
        estudiante.setCorreo(doc.getString("correo"));
        estudiante.setCarrera(doc.getString("carrera"));
        estudiante.setSemestre(doc.getInteger("semestre"));
        
        return estudiante;
    }
}