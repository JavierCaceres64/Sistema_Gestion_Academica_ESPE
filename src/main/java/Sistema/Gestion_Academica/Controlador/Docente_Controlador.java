package Sistema.Gestion_Academica.Controlador;

import Sistema.Gestion.Academica.Vista.Pnl_Docente;
import Sistema.Gestion_Academica.DAO.Docente_DAO;
import Sistema.Gestion_Academica.DAO_Implementacion.Docente_DAO_Mongo;
import Sistema.Gestion_Academica.Entidades.Docente;
import Sistema.Gestion_Academica.Excepciones.Dato_Invalido;

/**
 * @author Javier
 */
public class Docente_Controlador {

    private Pnl_Docente vista;
    private Docente_DAO dao;

    public Docente_Controlador(Pnl_Docente vista) {
        this.vista = vista;
        this.dao = new Docente_DAO_Mongo();
    }

    public void listar() {
        vista.mostrarDocentes(dao.listar());
    }

    public void guardar() {
        try {
            validar();

            if (dao.buscarPorCedula(vista.getCedula()) != null) {
                throw new Dato_Invalido("Ya existe un docente con esa cédula.");
            }

            Docente d = new Docente(vista.getDepartamento(), vista.getEspecialidad(),
                    vista.getNombres(), vista.getApellidos(), vista.getDireccion(),
                    vista.getCorreo(), vista.getCedula(), "");

            dao.guardar(d);
            vista.mostrarMensaje("Docente guardado.");
            vista.limpiarCampos();
            listar();

        } catch (Dato_Invalido ex) {
            vista.mostrarError(ex.getMessage());
        }
    }

    public void actualizar() {
        try {
            validar();

            Docente d = new Docente(vista.getDepartamento(), vista.getEspecialidad(),
                    vista.getNombres(), vista.getApellidos(), vista.getDireccion(),
                    vista.getCorreo(), vista.getCedula(), "");

            dao.actualizar(d);
            vista.mostrarMensaje("Docente actualizado.");
            vista.limpiarCampos();
            listar();

        } catch (Dato_Invalido ex) {
            vista.mostrarError(ex.getMessage());
        }
    }

    public void eliminar() {
        int fila = vista.getFilaSeleccionada();

        if (fila == -1) {
            vista.mostrarError("Seleccione un docente de la tabla.");
            return;
        }

        dao.eliminar(vista.getCedulaDeTabla(fila));
        vista.mostrarMensaje("Docente eliminado.");
        vista.limpiarCampos();
        listar();
    }

    public void seleccionar() {
        int fila = vista.getFilaSeleccionada();

        if (fila != -1) {
            Docente d = dao.buscarPorCedula(vista.getCedulaDeTabla(fila));
            if (d != null) {
                vista.cargarDatos(d);
            }
        }
    }

    private void validar() throws Dato_Invalido {

        if (vista.getCedula().length() != 10) {
            throw new Dato_Invalido("La cédula debe tener 10 dígitos.");
        }
        if (vista.getNombres().isEmpty()) {
            throw new Dato_Invalido("Ingrese los nombres.");
        }
        if (vista.getApellidos().isEmpty()) {
            throw new Dato_Invalido("Ingrese los apellidos.");
        }
        if (!vista.getCorreo().contains("@")) {
            throw new Dato_Invalido("El correo no es válido.");
        }
    }
}
