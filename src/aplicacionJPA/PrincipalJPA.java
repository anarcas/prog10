package aplicacionJPA;

import H2.H2JDBC;
import util.Color;
import H2.H2ServerManager;
import JPA.AdministradorDePersistencia;
import java.util.Scanner;

/**
 * Clase principal de la aplicación de gestión de supermercado usando JPA.
 *
 * <p>
 * Presenta un menú en consola que permite realizar operaciones CRUD sobre las
 * entidades Sección, Producto y Empleado. Además, permite abrir la consola web
 * de H2 y también carga un script SQL inicial.</p>
 *
 * <p>
 * Requiere que el servidor H2 esté iniciado correctamente y que se cree la
 * EntityManagerFactory antes de mostrar el menú.</p>
 *
 * @author IES Aguadulce
 * @version abril/2025
 */
public class PrincipalJPA {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        String opcion;

        if (H2ServerManager.iniciarServidorH2()) { // Inicia el servidor antes de usar JPA

            if (AdministradorDePersistencia.creaEntityManagerFactory()) {

                // Crea un conjunto de datos de prueba 
                H2JDBC.execScriptSQL("datos.sql");

                do {
                    System.out.println();
                    System.out.println(Color.azul("===================================================================="));
                    System.out.println(Color.azul("======== Gestión de Supermercado 1.0 (usando tecnología JPA)========"));
                    System.out.println(Color.azul("===================================================================="));
                    System.out.println(Color.azul("[SECCIONES]"));
                    System.out.println("    [IS]Insertar [LS]Listar [MS]Modificar [ES]Eliminar");
                    System.out.println(Color.azul("[PRODUCTOS]"));
                    System.out.println("    [IP]Insertar [CP]Consultar [LP] Listar [MP]Modificar [EP]Eliminar");
                    System.out.println(Color.azul("[EMPLEADOS]"));
                    System.out.println("    [IE]Insertar [CE]Consultar [LE] Listar [ME]Modificar [EE]Eliminar");
                    System.out.println(Color.azul("[GENERAL]"));
                    System.out.println("    [AC]Abrir consola H2 en navegador web");
                    System.out.println("    [S]Salir");
                    System.out.println(Color.azul("===================================================================="));
                    System.out.print("Opción: "); // Modificado para que el cursor quede en la misma línea

                    opcion = teclado.nextLine().toUpperCase();

                    switch (opcion) {
                        case "IS":
                            GestorEntidadesJPA.insertarSeccion();
                            break;
                        case "LS":
                            GestorEntidadesJPA.consultarSecciones();
                            break;
                        case "MS":
                            GestorEntidadesJPA.modificarSeccion();
                            break;
                        case "ES":
                            GestorEntidadesJPA.eliminarSeccion();
                            break;
                        case "IP":
                            GestorEntidadesJPA.insertarProducto();
                            break;
                        case "CP":
                            GestorEntidadesJPA.consultarProducto();
                            break;
                        case "LP":
                            GestorEntidadesJPA.listarProductos();
                            break;
                        case "MP":
                            GestorEntidadesJPA.modificarProducto();
                            break;
                        case "EP":
                            GestorEntidadesJPA.eliminarProducto();
                            break;
                        case "IE":
                            // LLAMADA AL MÉTODO INSERTAR EMPLEADO
                            GestorEntidadesJPA.insertarEmpleado();
                            break;
                        case "CE":
                            // LLAMADA AL MÉTODO CONSULTAR EMPLEADO
                            GestorEntidadesJPA.consultarEmpleado();
                            break;
                        case "LE":
                            // LLAMADA AL MÉTODO LISTAR EMPLEADOS
                            GestorEntidadesJPA.listarEmpleados();
                            break;
                        case "ME":
                            // LLAMADA AL MÉTODO MODIFICAR EMPLEADO
                            GestorEntidadesJPA.modificarEmpleado();
                            break;
                        case "EE":
                            // LLAMADA AL MÉTODO ELIMINAR EMPLEADO
                            GestorEntidadesJPA.eliminarEmpleado();
                            break;
                        case "AC":
                            H2ServerManager.abrirConsolaH2EnNavegador();
                            break;
                        case "S":
                            System.out.println("\nSaliendo de la aplicación... ");
                            break;
                        default:
                            System.out.println("Opción no válida.");
                    }
                } while (!opcion.equals("S"));
            }
        }
        AdministradorDePersistencia.cerrarEntityManagerFactory();
        H2ServerManager.detenerServidorH2();
        teclado.close(); // Cerrar el scanner al finalizar la aplicación
    }
}
