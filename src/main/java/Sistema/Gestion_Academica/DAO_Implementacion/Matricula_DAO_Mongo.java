package Sistema.Gestion_Academica.DAO_Implementacion;

import Sistema.Gestion_Academica.DAO.Asignatura_DAO;
import Sistema.Gestion_Academica.DAO.Estudiante_DAO;
import Sistema.Gestion_Academica.DAO.Matricula_DAO;
import Sistema.Gestion_Academica.Entidades.Matricula;
import Sistema.Gestion_Academica.Util.Conexion_Mongo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * @author Javier
 */
public class Matricula_DAO_Mongo implements Matricula_DAO {
    
    private MongoCollection<Document> coleccion;
    private Estudiante_DAO estudianteDAO;
    private Asignatura_DAO asignaturaDAO;
    
    public Matricula_DAO_Mongo() {
        this.coleccion = Conexion_Mongo.getInstancia()
                .getBaseDatos()
                .getCollection("matriculas");
        this.estudianteDAO = new Estudiante_DAO_Mongo();
        this.asignaturaDAO = new Asignatura_DAO_Mongo();
    }
    
    @Override
    public void guardar(Matricula matricula) {
        Document doc = new Document("cedulaEstudiante", matricula.getEstudiante().getCedula())
                .append("codigoAsignatura", matricula.getAsignatura().getCodigo())
                .append("periodo", matricula.getPeriodo())
                .append("fecha", matricula.getFecha())
                .append("nota", matricula.getNota())
                .append("estado", matricula.getEstado());
        
        coleccion.insertOne(doc);
    }
    
    @Override
    public List<Matricula> listar() {
        List<Matricula> lista = new ArrayList<>();
        
        for (Document doc : coleccion.find()) {
            lista.add(convertirAMatricula(doc));
        }
        return lista;
    }
    
    @Override
    public List<Matricula> listarPorEstudiante(String cedulaEstudiante) {
        List<Matricula> lista = new ArrayList<>();
        
        for (Document doc : coleccion.find(Filters.eq("cedulaEstudiante", cedulaEstudiante))) {
            lista.add(convertirAMatricula(doc));
        }
        return lista;
    }
    
    @Override
    public List<Matricula> listarPorAsignatura(String codigoAsignatura) {
        List<Matricula> lista = new ArrayList<>();
        
        for (Document doc : coleccion.find(Filters.eq("codigoAsignatura", codigoAsignatura))) {
            lista.add(convertirAMatricula(doc));
        }
        return lista;
    }
    
    @Override
    public void actualizar(Matricula matricula) {
        Document nuevosDatos = new Document("nota", matricula.getNota())
                .append("estado", matricula.getEstado());
        
        coleccion.updateOne(Filters.eq("_id", new ObjectId(matricula.getId())),
                            new Document("$set", nuevosDatos));
    }
    
    @Override
    public void eliminar(String id) {
        coleccion.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }
    
    private Matricula convertirAMatricula(Document doc) {
        Matricula matricula = new Matricula();
        
        matricula.setId(doc.getObjectId("_id").toString());
        matricula.setPeriodo(doc.getString("periodo"));
        matricula.setFecha(doc.getString("fecha"));
        matricula.setNota(doc.getDouble("nota"));
        matricula.setEstado(doc.getString("estado"));
        
        matricula.setEstudiante(estudianteDAO.buscarPorCedula(doc.getString("cedulaEstudiante")));
        matricula.setAsignatura(asignaturaDAO.buscarPorCodigo(doc.getString("codigoAsignatura")));
        
        return matricula;
    }
}