package Sistema.Gestion_Academica.DAO;

import Sistema.Gestion_Academica.Entidades.Docente;
import java.util.List;

/**
 * @author Javier
 */
public interface Docente_DAO {
    
    void guardar(Docente docente);
    List<Docente> listar();
    Docente buscarPorCedula(String cedula);
    void actualizar(Docente docente);
    void eliminar(String cedula);
    
}
