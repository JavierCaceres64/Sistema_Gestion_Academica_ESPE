package Sistema.Gestion_Academica.DAO_Implementacion;

import Sistema.Gestion_Academica.DAO.Asignatura_DAO;
import Sistema.Gestion_Academica.DAO.Docente_DAO;
import Sistema.Gestion_Academica.Entidades.Asignatura;
import Sistema.Gestion_Academica.Util.Conexion_Mongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 * @author Javier
 */
public class Asignatura_DAO_Mongo implements Asignatura_DAO {
    
    private MongoCollection<Document> coleccion;
    private Docente_DAO docenteDAO;
    
    public Asignatura_DAO_Mongo() {
        this.coleccion = Conexion_Mongo.getInstancia()
                .getBaseDatos()
                .getCollection("asignaturas");
        this.docenteDAO = new Docente_DAO_Mongo();
    }
    
    @Override
    public void guardar(Asignatura asignatura) {
        String cedulaDocente = "";
        if (asignatura.getDocente() != null) {
            cedulaDocente = asignatura.getDocente().getCedula();
        }
        
        Document doc = new Document("codigo", asignatura.getCodigo())
                .append("nombre", asignatura.getNombre())
                .append("creditos", asignatura.getCreditos())
                .append("cedulaDocente", cedulaDocente);
        
        coleccion.insertOne(doc);
    }
    
    @Override
    public List<Asignatura> listar() {
        List<Asignatura> lista = new ArrayList<>();
        
        for (Document doc : coleccion.find()) {
            lista.add(convertirAAsignatura(doc));
        }
        return lista;
    }
    
    @Override
    public Asignatura buscarPorCodigo(String codigo) {
        Document doc = coleccion.find(Filters.eq("codigo", codigo)).first();
        
        if (doc == null) {
            return null;
        }
        return convertirAAsignatura(doc);
    }
    
    @Override
    public List<Asignatura> listarPorDocente(String cedulaDocente) {
        List<Asignatura> lista = new ArrayList<>();
        
        for (Document doc : coleccion.find(Filters.eq("cedulaDocente", cedulaDocente))) {
            lista.add(convertirAAsignatura(doc));
        }
        return lista;
    }
    
    @Override
    public void actualizar(Asignatura asignatura) {
        String cedulaDocente = "";
        if (asignatura.getDocente() != null) {
            cedulaDocente = asignatura.getDocente().getCedula();
        }
        
        Document nuevosDatos = new Document("nombre", asignatura.getNombre())
                .append("creditos", asignatura.getCreditos())
                .append("cedulaDocente", cedulaDocente);
        
        coleccion.updateOne(Filters.eq("codigo", asignatura.getCodigo()),
                            new Document("$set", nuevosDatos));
    }
    
    @Override
    public void eliminar(String codigo) {
        coleccion.deleteOne(Filters.eq("codigo", codigo));
    }
    
    private Asignatura convertirAAsignatura(Document doc) {
        Asignatura asignatura = new Asignatura();
        
        asignatura.setId(doc.getObjectId("_id").toString());
        asignatura.setCodigo(doc.getString("codigo"));
        asignatura.setNombre(doc.getString("nombre"));
        asignatura.setCreditos(doc.getInteger("creditos"));
        
        // Reconstruye el docente a partir de la cédula guardada
        String cedulaDocente = doc.getString("cedulaDocente");
        if (cedulaDocente != null && !cedulaDocente.isEmpty()) {
            asignatura.setDocente(docenteDAO.buscarPorCedula(cedulaDocente));
        }
        
        return asignatura;
    }
}