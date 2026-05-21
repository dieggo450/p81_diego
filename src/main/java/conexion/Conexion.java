package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/"; //Ruta jdbc del servidor de bases de datos donde se conectará la aplicación. 
    private static final String NOMBRE_BASE_DATOS = "p81diego"; //Nombre de nuestra base de datos. Dento de nuestro espacio de trabajo (MySQL WorkBench).
    private static final String USER = "diego"; // El nombre de usuario de mysql con el que nos vamos a conectar al servidor.
    private static final String PASS = "123456"; // La clave de usuario anterior. 
    private static Connection instancia; // Objeto Singleton.

    private Conexion() {
    }

// Método de clase para acceder a la instancia del objeto Connection
    public static Connection getInstance() {
        // Si el objeto Connection no está creado, se crea
        if (instancia == null) {
            try {
                // Se crea el objeto Connection	
                instancia = DriverManager.getConnection(URL + NOMBRE_BASE_DATOS, USER, PASS);

                System.out.println("Conexión realizada con éxito.");

            } catch (SQLException e) {

                // Error en la conexión
                System.out.println("Conexión fallida: " + e.getMessage());
            }
        }
        return instancia;
    }
}
