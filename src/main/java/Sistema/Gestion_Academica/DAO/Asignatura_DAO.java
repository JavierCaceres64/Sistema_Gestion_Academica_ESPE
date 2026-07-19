package Sistema.Gestion_Academica.DAO;

import Sistema.Gestion_Academica.Entidades.Asignatura;
import java.util.List;

/**
 * @author Javier
 */
public interface Asignatura_DAO {
    
    void guardar(Asignatura asignatura);
    List<Asignatura> listar();
    Asignatura buscarPorCodigo(String codigo);
    List<Asignatura> listarPorDocente(String cedulaDocente);
    void actualizar(Asignatura asignatura);
    void eliminar(String codigo);
    
}
