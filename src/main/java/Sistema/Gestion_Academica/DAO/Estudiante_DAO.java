package Sistema.Gestion_Academica.DAO;

import Sistema.Gestion_Academica.Entidades.Estudiante;
import java.util.List;

/**
 * @author Javier
 */
public interface Estudiante_DAO {
    
    void guardar(Estudiante estudiante);
    List<Estudiante> listar();
    Estudiante buscarPorCedula(String cedula);
    void actualizar(Estudiante estudiante);
    void eliminar(String cedula);
}