package Sistema.Gestion.Academica;

import Sistema.Gestion.Academica.Vista.Frm_Principal;

/**
 * @author Javier
 */
public class Sistema_Gestion_Academica_POO {
    
    public static void main(String[] args) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frm_Principal().setVisible(true);
            }
        });
    }
}