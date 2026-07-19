package Sistema.Gestion_Academica.Util;

import Sistema.Gestion_Academica.Entidades.Asignatura;
import Sistema.Gestion_Academica.Entidades.Docente;
import Sistema.Gestion_Academica.Entidades.Estudiante;
import Sistema.Gestion_Academica.Entidades.Matricula;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Genera los reportes en PDF del sistema.
 * @author Javier
 */
public class Generador_PDF {

    private static final Font TITULO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
    private static final Font SUBTITULO = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
    private static final Font NORMAL = FontFactory.getFont(FontFactory.HELVETICA, 10);

    // ---------- Reporte 1: historial académico de un estudiante ----------

    public void historialAcademico(Estudiante estudiante, List<Matricula> matriculas)
            throws Exception {

        Document documento = new Document();
        String ruta = crearRuta("Historial_" + estudiante.getCedula());
        PdfWriter.getInstance(documento, new FileOutputStream(ruta));

        documento.open();
        agregarEncabezado(documento, "Historial Académico");

        documento.add(new Paragraph("Estudiante: " + estudiante.getNombres()
                + " " + estudiante.getApellidos(), SUBTITULO));
        documento.add(new Paragraph("Cédula: " + estudiante.getCedula(), NORMAL));
        documento.add(new Paragraph("Carrera: " + estudiante.getCarrera(), NORMAL));
        documento.add(new Paragraph(" "));

        PdfPTable tabla = new PdfPTable(6);
        tabla.setWidthPercentage(100);
        agregarTitulos(tabla, "Código", "Asignatura", "Créditos", "Período", "Nota", "Estado");

        for (Matricula m : matriculas) {
            tabla.addCell(new Phrase(m.getAsignatura().getCodigo(), NORMAL));
            tabla.addCell(new Phrase(m.getAsignatura().getNombre(), NORMAL));
            tabla.addCell(new Phrase(String.valueOf(m.getAsignatura().getCreditos()), NORMAL));
            tabla.addCell(new Phrase(m.getPeriodo(), NORMAL));
            tabla.addCell(new Phrase(String.valueOf(m.getNota()), NORMAL));
            tabla.addCell(new Phrase(m.getEstado(), NORMAL));
        }

        documento.add(tabla);
        documento.add(new Paragraph(" "));
        documento.add(new Paragraph("Total de asignaturas: " + matriculas.size(), SUBTITULO));

        documento.close();
        abrir(ruta);
    }

    // ---------- Reporte 2: estudiantes de una asignatura ----------

    public void estudiantesPorAsignatura(Asignatura asignatura, List<Matricula> matriculas)
            throws Exception {

        Document documento = new Document();
        String ruta = crearRuta("Estudiantes_" + asignatura.getCodigo());
        PdfWriter.getInstance(documento, new FileOutputStream(ruta));

        documento.open();
        agregarEncabezado(documento, "Estudiantes por Asignatura");

        documento.add(new Paragraph("Asignatura: " + asignatura.getNombre(), SUBTITULO));
        documento.add(new Paragraph("Código: " + asignatura.getCodigo(), NORMAL));

        String docente = "Sin asignar";
        if (asignatura.getDocente() != null) {
            docente = asignatura.getDocente().getNombres() + " "
                    + asignatura.getDocente().getApellidos();
        }
        documento.add(new Paragraph("Docente: " + docente, NORMAL));
        documento.add(new Paragraph(" "));

        PdfPTable tabla = new PdfPTable(5);
        tabla.setWidthPercentage(100);
        agregarTitulos(tabla, "Cédula", "Nombres", "Apellidos", "Carrera", "Estado");

        for (Matricula m : matriculas) {
            Estudiante e = m.getEstudiante();
            tabla.addCell(new Phrase(e.getCedula(), NORMAL));
            tabla.addCell(new Phrase(e.getNombres(), NORMAL));
            tabla.addCell(new Phrase(e.getApellidos(), NORMAL));
            tabla.addCell(new Phrase(e.getCarrera(), NORMAL));
            tabla.addCell(new Phrase(m.getEstado(), NORMAL));
        }

        documento.add(tabla);
        documento.add(new Paragraph(" "));
        documento.add(new Paragraph("Total de estudiantes: " + matriculas.size(), SUBTITULO));

        documento.close();
        abrir(ruta);
    }

    // ---------- Reporte 3: asignaturas de un docente ----------

    public void asignaturasPorDocente(Docente docente, List<Asignatura> asignaturas)
            throws Exception {

        Document documento = new Document();
        String ruta = crearRuta("Asignaturas_" + docente.getCedula());
        PdfWriter.getInstance(documento, new FileOutputStream(ruta));

        documento.open();
        agregarEncabezado(documento, "Asignaturas por Docente");

        documento.add(new Paragraph("Docente: " + docente.getNombres()
                + " " + docente.getApellidos(), SUBTITULO));
        documento.add(new Paragraph("Cédula: " + docente.getCedula(), NORMAL));
        documento.add(new Paragraph("Departamento: " + docente.getDepartamento(), NORMAL));
        documento.add(new Paragraph(" "));

        PdfPTable tabla = new PdfPTable(3);
        tabla.setWidthPercentage(100);
        agregarTitulos(tabla, "Código", "Nombre", "Créditos");

        int totalCreditos = 0;
        for (Asignatura a : asignaturas) {
            tabla.addCell(new Phrase(a.getCodigo(), NORMAL));
            tabla.addCell(new Phrase(a.getNombre(), NORMAL));
            tabla.addCell(new Phrase(String.valueOf(a.getCreditos()), NORMAL));
            totalCreditos = totalCreditos + a.getCreditos();
        }

        documento.add(tabla);
        documento.add(new Paragraph(" "));
        documento.add(new Paragraph("Total de asignaturas: " + asignaturas.size(), SUBTITULO));
        documento.add(new Paragraph("Total de créditos: " + totalCreditos, SUBTITULO));

        documento.close();
        abrir(ruta);
    }

    // ---------- Métodos auxiliares ----------

    private void agregarEncabezado(Document documento, String titulo) throws Exception {
        Paragraph universidad = new Paragraph("UNIVERSIDAD DE LAS FUERZAS ARMADAS - ESPE", SUBTITULO);
        universidad.setAlignment(Element.ALIGN_CENTER);
        documento.add(universidad);

        Paragraph nombre = new Paragraph(titulo, TITULO);
        nombre.setAlignment(Element.ALIGN_CENTER);
        documento.add(nombre);

        String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
        Paragraph emision = new Paragraph("Emitido el " + fecha, NORMAL);
        emision.setAlignment(Element.ALIGN_CENTER);
        documento.add(emision);

        documento.add(new Paragraph(" "));
    }

    private void agregarTitulos(PdfPTable tabla, String... titulos) {
        for (String titulo : titulos) {
            PdfPCell celda = new PdfPCell(new Phrase(titulo, SUBTITULO));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(celda);
        }
    }

    private String crearRuta(String nombreArchivo) {
        File carpeta = new File("reportes");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }
        return "reportes/" + nombreArchivo + ".pdf";
    }

    private void abrir(String ruta) throws Exception {
        Desktop.getDesktop().open(new File(ruta));
    }
}