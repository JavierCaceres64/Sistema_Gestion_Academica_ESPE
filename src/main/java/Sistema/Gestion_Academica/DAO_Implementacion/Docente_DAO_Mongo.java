package Sistema.Gestion_Academica.DAO_Implementacion;

import Sistema.Gestion_Academica.DAO.Docente_DAO;
import Sistema.Gestion_Academica.Entidades.Docente;
import Sistema.Gestion_Academica.Util.Conexion_Mongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 * @author Javier
 */
public class Docente_DAO_Mongo implements Docente_DAO {
    
    private MongoCollection<Document> coleccion;
    
    public Docente_DAO_Mongo() {
        this.coleccion = Conexion_Mongo.getInstancia()
                .getBaseDatos()
                .getCollection("docentes");
    }
    
    @Override
    public void guardar(Docente docente) {
        Document doc = new Document("cedula", docente.getCedula())
                .append("nombres", docente.getNombres())
                .append("apellidos", docente.getApellidos())
                .append("direccion", docente.getDireccion())
                .append("correo", docente.getCorreo())
                .append("departamento", docente.getDepartamento())
                .append("especialidad", docente.getEspecialidad());
        
        coleccion.insertOne(doc);
    }
    
    @Override
    public List<Docente> listar() {
        List<Docente> lista = new ArrayList<>();
        
        for (Document doc : coleccion.find()) {
            lista.add(convertirADocente(doc));
        }
        return lista;
    }
    
    @Override
    public Docente buscarPorCedula(String cedula) {
        Document doc = coleccion.find(Filters.eq("cedula", cedula)).first();
        
        if (doc == null) {
            return null;
        }
        return convertirADocente(doc);
    }
    
    @Override
    public void actualizar(Docente docente) {
        Document nuevosDatos = new Document("nombres", docente.getNombres())
                .append("apellidos", docente.getApellidos())
                .append("direccion", docente.getDireccion())
                .append("correo", docente.getCorreo())
                .append("departamento", docente.getDepartamento())
                .append("especialidad", docente.getEspecialidad());
        
        coleccion.updateOne(Filters.eq("cedula", docente.getCedula()),
                            new Document("$set", nuevosDatos));
    }
    
    @Override
    public void eliminar(String cedula) {
        coleccion.deleteOne(Filters.eq("cedula", cedula));
    }
    
    private Docente convertirADocente(Document doc) {
        Docente docente = new Docente();
        
        docente.setId(doc.getObjectId("_id").toString());
        docente.setCedula(doc.getString("cedula"));
        docente.setNombres(doc.getString("nombres"));
        docente.setApellidos(doc.getString("apellidos"));
        docente.setDireccion(doc.getString("direccion"));
        docente.setCorreo(doc.getString("correo"));
        docente.setDepartamento(doc.getString("departamento"));
        docente.setEspecialidad(doc.getString("especialidad"));
        
        return docente;
    }
}