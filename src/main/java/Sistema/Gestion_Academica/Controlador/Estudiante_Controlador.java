package Sistema.Gestion_Academica.Controlador;

import Sistema.Gestion_Academica.DAO.Estudiante_DAO;
import Sistema.Gestion_Academica.DAO_Implementacion.Estudiante_DAO_Mongo;
import Sistema.Gestion_Academica.Entidades.Estudiante;
import Sistema.Gestion_Academica.Excepciones.Dato_Invalido;
import Sistema.Gestion.Academica.Vista.Pnl_Estudiante;

/**
 * @author Javier
 */
public class Estudiante_Controlador {
    
    private Pnl_Estudiante vista;
    private Estudiante_DAO dao;
    
    public Estudiante_Controlador(Pnl_Estudiante vista) {
        this.vista = vista;
        this.dao = new Estudiante_DAO_Mongo();
    }
    
    public void listar() {
        vista.mostrarEstudiantes(dao.listar());
    }
    
    public void guardar() {
        try {
            validar();
            
            if (dao.buscarPorCedula(vista.getCedula()) != null) {
                throw new Dato_Invalido("Ya existe un estudiante con esa cédula.");
            }
            
            Estudiante e = new Estudiante(vista.getCarrera(), vista.getSemestre(),
                    vista.getNombres(), vista.getApellidos(), vista.getDireccion(),
                    vista.getCorreo(), vista.getCedula(), "");
            
            dao.guardar(e);
            vista.mostrarMensaje("Estudiante guardado.");
            vista.limpiarCampos();
            listar();
            
        } catch (Dato_Invalido ex) {
            vista.mostrarError(ex.getMessage());
        }
    }
    
    public void actualizar() {
        try {
            validar();
            
            Estudiante e = new Estudiante(vista.getCarrera(), vista.getSemestre(),
                    vista.getNombres(), vista.getApellidos(), vista.getDireccion(),
                    vista.getCorreo(), vista.getCedula(), "");
            
            dao.actualizar(e);
            vista.mostrarMensaje("Estudiante actualizado.");
            vista.limpiarCampos();
            listar();
            
        } catch (Dato_Invalido ex) {
            vista.mostrarError(ex.getMessage());
        }
    }
    
    public void eliminar() {
        int fila = vista.getFilaSeleccionada();
        
        if (fila == -1) {
            vista.mostrarError("Seleccione un estudiante de la tabla.");
            return;
        }
        
        dao.eliminar(vista.getCedulaDeTabla(fila));
        vista.mostrarMensaje("Estudiante eliminado.");
        vista.limpiarCampos();
        listar();
    }
    
    public void seleccionar() {
        int fila = vista.getFilaSeleccionada();
        
        if (fila != -1) {
            Estudiante e = dao.buscarPorCedula(vista.getCedulaDeTabla(fila));
            if (e != null) {
                vista.cargarDatos(e);
            }
        }
    }
    
    // Todas las validaciones en un solo lugar
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