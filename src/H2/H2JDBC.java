package H2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import util.*;

/**
 * Clase para gestionar la conexión a base de datos desde Java JDBC por medio
 * del patrón singleton, también se incluyen métodos útiles para trabajar con la
 * base de datos.
 *
 * @author IES Aguadulce
 * @version abril/2021
 *
 * @see
 * <a href=https://itquetzali.com/2019/09/01/patron-de-diseno-singleton-implementacion-en-java/>Patrón
 * de Diseño Singleton implementación en Java</a>
 * @see
 * <a href=https://keepcoding.io/blog/que-es-el-patron-singleton-en-java/>¿Qué
 * es el patrón Singleton en Java?</a>
 */
public class H2JDBC {

    /**
     * Usuario de la base de datos.
     */
    public static final String USUARIO = "admin";

    /**
     * Password del usuario de base de datos.
     */
    public static final String PASSWORD = "1234";

    /**
     * Cadena de conexión JDBC.
     */
    public static final String URL = "jdbc:h2:tcp://localhost/~/databaseH2/supermercado";

    /**
     * Ruta donde se guardan los ficheros de script SQL.
     */
    public static String PATH_SCRIPT = "./src/SQLscripts/";

    /**
     * Objeto Connection.
     */
    private static Connection conexionBD = null;

    /**
     * Método privado para que no se puedan instanciar objetos de la clase desde
     * fuera.
     */
    private H2JDBC() {
        System.out.print("Abriendo conexión JDBC con la base de datos...");
        try {
            H2JDBC.conexionBD = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println(Color.verde("OK"));
        } catch (SQLException e) {
            System.out.println(Color.rojo("ERROR: no es posible conectar con la base de datos."));
        }
    }

    /**
     * Devuelve la conexión a la base de datos según el patrón Singleton.
     *
     * @return el objeto Singleton Connection
     */
    public static Connection getConexion() {

        if (H2JDBC.conexionBD == null) {
            new H2JDBC();
        }
        return H2JDBC.conexionBD;
    }

    /**
     * Cierra la conexión con la base de datos.
     */
    public static void cerrarConexion() {

        if (H2JDBC.conexionBD != null) {
            try {
                System.out.print("Cerrando conexión con la base de datos...");
                H2JDBC.conexionBD.close(); //Cierra la conexión con la base de datos
                System.out.println(Color.verde("OK"));
            } catch (SQLException e) {
                System.out.println(Color.rojo("ERROR"));
            }
        }
    }

    /**
     * Ejecuta un script SQL contenido en el fichero especificado. Las
     * sentencias SQL deben finalizar por punto y coma.
     *
     * @param ficheroScriptSQL Nombre del fichero script SQL (sin ruta)
     * @return {@code true} si todas las sentencias del script se ejecutaron
     * correctamente, {@code false} si ocurrió algún error durante la ejecución.
     */
    public static boolean execScriptSQL(String ficheroScriptSQL) {
        boolean resultado = false;
        String scriptSQL = cargaFicheroScriptSQL(PATH_SCRIPT + ficheroScriptSQL);

        String[] sentenciasSQL = scriptSQL.split(";");  // Divide las sentencias por punto y coma

        if (scriptSQL.length() > 0) {
            try (Statement st = H2JDBC.getConexion().createStatement()) {
                System.out.print("Ejecutando el fichero de script SQL " + ficheroScriptSQL + "... ");
                for (String sentenciaSQL : sentenciasSQL) {
                    if (!sentenciaSQL.trim().isEmpty()) {
                        st.execute(sentenciaSQL.trim());
                    }
                }
                resultado = true;
                System.out.println(Color.verde("OK"));
            } catch (SQLException e) {
                resultado = false;
                System.out.println(Color.rojo("ERROR de SQL:"+e.getSQLState()+" "+e.getMessage()));
            }
        }
        return resultado;
    }

    /**
     * Carga el contenido de un fichero de script SQL desde la ruta especificada
     * por la constante {@code PATH_SCRIPT}.
     *
     * @param ficheroScriptSQL Nombre del fichero script SQL (sin ruta)
     * @return El contenido del fichero como String, incluyendo saltos de línea.
     * Retorna una cadena vacía si ocurre algún error durante la lectura.
     */
    private static String cargaFicheroScriptSQL(String ficheroScriptSQL) {
        String linea;
        StringBuilder scriptSQL = new StringBuilder();

        System.out.print("Leyendo fichero de script SQL " + ficheroScriptSQL + "... ");
        File f = new File(ficheroScriptSQL);
        try (FileReader fr = new FileReader(f); BufferedReader br = new BufferedReader(fr)) {
            while ((linea = br.readLine()) != null) {
                scriptSQL.append(linea).append("\n");
            }
            System.out.println(Color.verde("OK"));
        } catch (FileNotFoundException e) {
            System.out.println(Color.rojo("ERROR al abrir el fichero script SQL."));
        } catch (IOException ex) {
            System.out.println(Color.rojo("ERROR de lectura del fichero de script sQL."));
        }
        return scriptSQL.toString();
    }

}
