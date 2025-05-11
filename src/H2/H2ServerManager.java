package H2;

import util.Color;
import java.sql.SQLException;
import java.awt.Desktop;
import java.net.URI;
import org.h2.tools.Server;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Clase para gestionar el servidor H2 embebido.
 *
 * @author IES Aguadulce
 * @version abril/2025
 */
public class H2ServerManager {

    private static final String URL = "jdbc:h2:tcp://localhost/~/databaseH2/supermercado";
    private static final String USUARIO = "admin";
    private static final String PASSWORD = "1234";
    private static Server tcpServer;
    private static Server webServer;

    /**
     * Constructor privado para que no se puedan instanciar objetos de la clase desde
     * fuera.
     */
    private H2ServerManager() {
    }

    /**
     * Inicia el servidor H2 si no está en ejecución. Muestra la URL de la
     * consola de administración si el servidor se inicia correctamente.
     *
     * @return true si el servidor se inicia correctamente, false en caso
     * contrario.
     */
    public static boolean iniciarServidorH2() {
        boolean resultado = true;
        System.out.print("Iniciando servidor H2...");
        if (servidorH2EnEjecucion()) {
            System.out.println(Color.verde("El servidor H2 ya estaba iniciado."));
        } else {
            try {
                tcpServer = Server.createTcpServer(
                        "-tcp",
                        "-tcpAllowOthers",
                        "-tcpPort", "9092",
                        "-ifNotExists"
                ).start();

                webServer = Server.createWebServer(
                        "-web",
                        "-webAllowOthers",
                        "-webPort", "8082",
                        "-ifNotExists"
                ).start();

                System.out.println(Color.verde("OK"));
                System.out.println("------------------------------------------------------------------");
                System.out.println(Color.azul("Consola de H2 en: http://localhost:8082"));
                System.out.println("Datos de conexión JDBC a la base de datos H2 de [supermercado]");
                System.out.println("   Controlador: org.h2.Driver");
                System.out.println("   URL JDBC: jdbc:h2:tcp://localhost/~/databaseH2/supermercado");
                System.out.println("   Usuario:  admin");
                System.out.println("   Password: 1234");
                System.out.println("------------------------------------------------------------------");
            } catch (SQLException e) {
                System.out.println(Color.rojo("ERROR"));
                resultado = false;
            }
        }
        return resultado;
    }

    /**
     * Detiene el servidor H2 si está en ejecución.
     */
    public static void detenerServidorH2() {
        if (tcpServer != null) {
            tcpServer.stop();
            System.out.println(Color.verde("Servidor H2 TCP detenido."));
        }
        if (webServer != null) {
            webServer.stop();
            System.out.println(Color.verde("Servidor H2 Web detenido."));
        }
    }

    /**
     * Abre la consola web de H2 en el navegador predeterminado.
     */
    public static void abrirConsolaH2EnNavegador() {
        String url = "http://localhost:8082";
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifica si el servidor H2 ya está en ejecución intentando establecer una
     * conexión y comprobando el resultado.
     *
     * @return true si el servidor está en ejecución, false en caso contrario.
     */
    private static boolean servidorH2EnEjecucion() {
        boolean enEjecucion;

        try (Connection conn = DriverManager.getConnection(URL, USUARIO, PASSWORD)) {
            enEjecucion = true;
        } catch (SQLException e) {
            enEjecucion = false;
        }
        return enEjecucion;
    }
}
