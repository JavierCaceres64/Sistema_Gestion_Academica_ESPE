package Sistema.Gestion_Academica.Controlador;

import Sistema.Gestion.Academica.Vista.Pnl_Reporte;
import Sistema.Gestion_Academica.DAO.Asignatura_DAO;
import Sistema.Gestion_Academica.DAO.Docente_DAO;
import Sistema.Gestion_Academica.DAO.Estudiante_DAO;
import Sistema.Gestion_Academica.DAO.Matricula_DAO;
import Sistema.Gestion_Academica.DAO_Implementacion.Asignatura_DAO_Mongo;
import Sistema.Gestion_Academica.DAO_Implementacion.Docente_DAO_Mongo;
import Sistema.Gestion_Academica.DAO_Implementacion.Estudiante_DAO_Mongo;
import Sistema.Gestion_Academica.DAO_Implementacion.Matricula_DAO_Mongo;
import Sistema.Gestion_Academica.Entidades.Asignatura;
import Sistema.Gestion_Academica.Entidades.Docente;
import Sistema.Gestion_Academica.Entidades.Estudiante;
import Sistema.Gestion_Academica.Entidades.Matricula;
import Sistema.Gestion_Academica.Util.Generador_PDF;
import java.util.List;

/**
 * @author Javier
 */
public class Reporte_Controlador {

    private Pnl_Reporte vista;
    private Estudiante_DAO estudianteDAO;
    private Docente_DAO docenteDAO;
    private Asignatura_DAO asignaturaDAO;
    private Matricula_DAO matriculaDAO;
    private Generador_PDF generador;

    public Reporte_Controlador(Pnl_Reporte vista) {
        this.vista = vista;
        this.estudianteDAO = new Estudiante_DAO_Mongo();
        this.docenteDAO = new Docente_DAO_Mongo();
        this.asignaturaDAO = new Asignatura_DAO_Mongo();
        this.matriculaDAO = new Matricula_DAO_Mongo();
        this.generador = new Generador_PDF();
    }

    public void cargarCombos() {
        vista.llenarComboEstudiantes(estudianteDAO.listar());
        vista.llenarComboAsignaturas(asignaturaDAO.listar());
        vista.llenarComboDocentes(docenteDAO.listar());
    }

    public void generarHistorial() {
        try {
            Estudiante e = vista.getEstudiante();

            if (e == null) {
                vista.mostrarError("Seleccione un estudiante.");
                return;
            }

            List<Matricula> matriculas = matriculaDAO.listarPorEstudiante(e.getCedula());

            if (matriculas.isEmpty()) {
                vista.mostrarError("El estudiante no tiene matrículas registradas.");
                return;
            }

            generador.historialAcademico(e, matriculas);
            vista.mostrarMensaje("Reporte generado en la carpeta reportes.");

        } catch (Exception ex) {
            vista.mostrarError("No se pudo generar el reporte: " + ex.getMessage());
        }
    }

    public void generarEstudiantesPorAsignatura() {
        try {
            Asignatura a = vista.getAsignatura();

            if (a == null) {
                vista.mostrarError("Seleccione una asignatura.");
                return;
            }

            List<Matricula> matriculas = matriculaDAO.listarPorAsignatura(a.getCodigo());

            if (matriculas.isEmpty()) {
                vista.mostrarError("La asignatura no tiene estudiantes matriculados.");
                return;
            }

            generador.estudiantesPorAsignatura(a, matriculas);
            vista.mostrarMensaje("Reporte generado en la carpeta reportes.");

        } catch (Exception ex) {
            vista.mostrarError("No se pudo generar el reporte: " + ex.getMessage());
        }
    }

    public void generarAsignaturasPorDocente() {
        try {
            Docente d = vista.getDocente();

            if (d == null) {
                vista.mostrarError("Seleccione un docente.");
                return;
            }

            List<Asignatura> asignaturas = asignaturaDAO.listarPorDocente(d.getCedula());

            if (asignaturas.isEmpty()) {
                vista.mostrarError("El docente no tiene asignaturas asignadas.");
                return;
            }

            generador.asignaturasPorDocente(d, asignaturas);
            vista.mostrarMensaje("Reporte generado en la carpeta reportes.");

        } catch (Exception ex) {
            vista.mostrarError("No se pudo generar el reporte: " + ex.getMessage());
        }
    }
}
