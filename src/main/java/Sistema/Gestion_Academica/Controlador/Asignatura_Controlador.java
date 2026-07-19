package Sistema.Gestion_Academica.Controlador;

import Sistema.Gestion.Academica.Vista.Pnl_Asignatura;
import Sistema.Gestion_Academica.DAO.Asignatura_DAO;
import Sistema.Gestion_Academica.DAO.Docente_DAO;
import Sistema.Gestion_Academica.DAO_Implementacion.Asignatura_DAO_Mongo;
import Sistema.Gestion_Academica.DAO_Implementacion.Docente_DAO_Mongo;
import Sistema.Gestion_Academica.Entidades.Asignatura;
import Sistema.Gestion_Academica.Excepciones.Dato_Invalido;

/**
 * @author Javier
 */
public class Asignatura_Controlador {

    private Pnl_Asignatura vista;
    private Asignatura_DAO dao;
    private Docente_DAO docenteDAO;

    public Asignatura_Controlador(Pnl_Asignatura vista) {
        this.vista = vista;
        this.dao = new Asignatura_DAO_Mongo();
        this.docenteDAO = new Docente_DAO_Mongo();
    }

    public void cargarDocentes() {
        vista.llenarComboDocentes(docenteDAO.listar());
    }

    public void listar() {
        vista.mostrarAsignaturas(dao.listar());
    }

    public void guardar() {
        try {
            validar();

            if (dao.buscarPorCodigo(vista.getCodigo()) != null) {
                throw new Dato_Invalido("Ya existe una asignatura con ese código.");
            }

            Asignatura a = new Asignatura(vista.getCodigo(), vista.getNombre(),
                                          vista.getCreditos());
            a.setDocente(vista.getDocente());

            dao.guardar(a);
            vista.mostrarMensaje("Asignatura guardada.");
            vista.limpiarCampos();
            listar();

        } catch (Dato_Invalido ex) {
            vista.mostrarError(ex.getMessage());
        }
    }

    public void actualizar() {
        try {
            validar();

            Asignatura a = new Asignatura(vista.getCodigo(), vista.getNombre(),
                                          vista.getCreditos());
            a.setDocente(vista.getDocente());

            dao.actualizar(a);
            vista.mostrarMensaje("Asignatura actualizada.");
            vista.limpiarCampos();
            listar();

        } catch (Dato_Invalido ex) {
            vista.mostrarError(ex.getMessage());
        }
    }

    public void eliminar() {
        int fila = vista.getFilaSeleccionada();

        if (fila == -1) {
            vista.mostrarError("Seleccione una asignatura de la tabla.");
            return;
        }

        dao.eliminar(vista.getCodigoDeTabla(fila));
        vista.mostrarMensaje("Asignatura eliminada.");
        vista.limpiarCampos();
        listar();
    }

    public void seleccionar() {
        int fila = vista.getFilaSeleccionada();

        if (fila != -1) {
            Asignatura a = dao.buscarPorCodigo(vista.getCodigoDeTabla(fila));
            if (a != null) {
                vista.cargarDatos(a);
            }
        }
    }

    private void validar() throws Dato_Invalido {

        if (vista.getCodigo().isEmpty()) {
            throw new Dato_Invalido("Ingrese el código de la asignatura.");
        }
        if (vista.getNombre().isEmpty()) {
            throw new Dato_Invalido("Ingrese el nombre de la asignatura.");
        }
        if (vista.getDocente() == null) {
            throw new Dato_Invalido("Debe registrar docentes antes de crear asignaturas.");
        }
    }
}
