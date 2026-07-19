package Sistema.Gestion_Academica.Controlador;

import Sistema.Gestion.Academica.Vista.Pnl_Matricula;
import Sistema.Gestion_Academica.DAO.Asignatura_DAO;
import Sistema.Gestion_Academica.DAO.Estudiante_DAO;
import Sistema.Gestion_Academica.DAO.Matricula_DAO;
import Sistema.Gestion_Academica.DAO_Implementacion.Asignatura_DAO_Mongo;
import Sistema.Gestion_Academica.DAO_Implementacion.Estudiante_DAO_Mongo;
import Sistema.Gestion_Academica.DAO_Implementacion.Matricula_DAO_Mongo;
import Sistema.Gestion_Academica.Entidades.Matricula;
import Sistema.Gestion_Academica.Excepciones.Dato_Invalido;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Javier
 */
public class Matricula_Controlador {

    private Pnl_Matricula vista;
    private Matricula_DAO dao;
    private Estudiante_DAO estudianteDAO;
    private Asignatura_DAO asignaturaDAO;
    private List<Matricula> listaActual;

    public Matricula_Controlador(Pnl_Matricula vista) {
        this.vista = vista;
        this.dao = new Matricula_DAO_Mongo();
        this.estudianteDAO = new Estudiante_DAO_Mongo();
        this.asignaturaDAO = new Asignatura_DAO_Mongo();
    }

    public void cargarCombos() {
        vista.llenarComboEstudiantes(estudianteDAO.listar());
        vista.llenarComboAsignaturas(asignaturaDAO.listar());
    }

    public void listar() {
        listaActual = dao.listar();
        vista.mostrarMatriculas(listaActual);
    }

    public void guardar() {
        try {
            validar();

            if (yaEstaMatriculado()) {
                throw new Dato_Invalido("El estudiante ya está matriculado "
                                      + "en esa asignatura en este período.");
            }

            String fecha = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

            Matricula m = new Matricula(vista.getEstudiante(), vista.getAsignatura(),
                                        vista.getPeriodo(), fecha);

            dao.guardar(m);
            vista.mostrarMensaje("Matrícula registrada.");
            vista.limpiarCampos();
            listar();

        } catch (Dato_Invalido ex) {
            vista.mostrarError(ex.getMessage());
        }
    }

    public void actualizar() {
        int fila = vista.getFilaSeleccionada();

        if (fila == -1) {
            vista.mostrarError("Seleccione una matrícula de la tabla.");
            return;
        }

        try {
            double nota = leerNota();

            Matricula m = listaActual.get(fila);
            m.setNota(nota);
            m.setEstado(vista.getEstado());

            dao.actualizar(m);
            vista.mostrarMensaje("Matrícula actualizada.");
            listar();

        } catch (Dato_Invalido ex) {
            vista.mostrarError(ex.getMessage());
        }
    }

    public void eliminar() {
        int fila = vista.getFilaSeleccionada();

        if (fila == -1) {
            vista.mostrarError("Seleccione una matrícula de la tabla.");
            return;
        }

        dao.eliminar(listaActual.get(fila).getId());
        vista.mostrarMensaje("Matrícula eliminada.");
        vista.limpiarCampos();
        listar();
    }

    public void seleccionar() {
        int fila = vista.getFilaSeleccionada();

        if (fila != -1) {
            vista.cargarDatos(listaActual.get(fila));
        }
    }

    private double leerNota() throws Dato_Invalido {
        try {
            double nota = Double.parseDouble(vista.getNota());
            if (nota < 0 || nota > 20) {
                throw new Dato_Invalido("La nota debe estar entre 0 y 20.");
            }
            return nota;
        } catch (NumberFormatException ex) {
            throw new Dato_Invalido("La nota debe ser un número.");
        }
    }

    private boolean yaEstaMatriculado() {
        for (Matricula m : dao.listarPorEstudiante(vista.getEstudiante().getCedula())) {
            if (m.getAsignatura().getCodigo().equals(vista.getAsignatura().getCodigo())
                    && m.getPeriodo().equals(vista.getPeriodo())) {
                return true;
            }
        }
        return false;
    }

    private void validar() throws Dato_Invalido {

        if (vista.getEstudiante() == null) {
            throw new Dato_Invalido("Debe registrar estudiantes primero.");
        }
        if (vista.getAsignatura() == null) {
            throw new Dato_Invalido("Debe registrar asignaturas primero.");
        }
        if (vista.getPeriodo().isEmpty()) {
            throw new Dato_Invalido("Ingrese el período. Ejemplo: 2026-S1");
        }
    }
}
