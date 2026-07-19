package Sistema.Gestion_Academica.DAO;

import Sistema.Gestion_Academica.Entidades.Matricula;
import java.util.List;

/**
 * @author Javier
 */
public interface Matricula_DAO {
    
    void guardar(Matricula matricula);
    List<Matricula> listar();
    List<Matricula> listarPorEstudiante(String cedulaEstudiante);
    List<Matricula> listarPorAsignatura(String codigoAsignatura);
    void actualizar(Matricula matricula);
    void eliminar(String id);
    
}