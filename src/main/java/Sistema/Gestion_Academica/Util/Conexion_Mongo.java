package Sistema.Gestion_Academica.Util;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 * @author Javier
 */
public class Conexion_Mongo {
    //Atributos
    
    private static Conexion_Mongo instancia;
    private MongoDatabase baseDatos;
    
    private static final String URL = "mongodb://localhost:27017";
    private static final String NOMBRE_BD = "gestion_academica";
    
    // Constructor privado: nadie puede hacer new ConexionMongo()
    private Conexion_Mongo() {
        MongoClient cliente = MongoClients.create(URL);
        this.baseDatos = cliente.getDatabase(NOMBRE_BD);
    }
    
    // Único punto de acceso a la instancia
    public static Conexion_Mongo getInstancia() {
        if (instancia == null) {
            instancia = new Conexion_Mongo();
        }
        return instancia;
    }
    
    public MongoDatabase getBaseDatos() {
        return baseDatos;
    }
}

