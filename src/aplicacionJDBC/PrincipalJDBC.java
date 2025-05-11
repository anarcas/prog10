package aplicacionJDBC;

import H2.H2JDBC;
import H2.H2ServerManager;
import util.*;
import java.util.Scanner;

/**
 * Programa principal que usa el acceso a base de datos configurado en
 * H2JDBC.java
 *
 * @author IES Aguadulce
 * @version abril/2025
 */
public class PrincipalJDBC {

    /**
     * Método principal de la clase PrincipalJDBC
     * @param args Array de objetos String
     */
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        String opcion;
        String idSeccion;
        double porcentaje; // Renombrado de 'interes' a 'porcentaje' para mayor claridad en el contexto de salario

        if (H2ServerManager.iniciarServidorH2()) { // Inicia el servidor antes de usar JDBC
            if (H2JDBC.getConexion() != null) {
                do {
                    System.out.println();
                    System.out.println(Color.azul("=================================================================="));
                    System.out.println(Color.azul("======= Gestión de Supermercado 1.0 (usando tecnología JDBC)======"));
                    System.out.println(Color.azul("=================================================================="));
                    System.out.println(Color.azul("[CONSULTAS DE SECCIONES]"));
                    System.out.println("    [VS]Valor de los productos de una sección.");
                    System.out.println(Color.azul("[CONSULTAS DE PRODUCTOS]"));
                    System.out.println("    [PD]Productos ordenados por descripción.");
                    System.out.println("    [PCP]Productos ordenados por código de producto.");
                    System.out.println("    [PCS]Productos ordenados por código de sección.");
                    System.out.println("    [PS]Productos de una sección.");
                    System.out.println(Color.azul("[CONSULTAS DE EMPLEADOS]"));
                    System.out.println("    [EN]Empleados ordenados por nombre.");
                    System.out.println("    [ECE]Empleados ordenados por código de empleado.");
                    System.out.println("    [ECS]Empleados ordenados por código de sección.");
                    System.out.println("    [ES]Empleados de una sección.");
                    System.out.println(Color.azul("[ACTUALIZACIONES]"));
                    System.out.println("    [IP]Incrementar el precio de los productos de una sección.");
                    System.out.println("    [IS]Incrementar el salario de los empleados de una sección.");
                    System.out.println(Color.azul("[GENERAL]"));
                    System.out.println("    [AC]Abrir consola H2 en navegador web");
                    System.out.println("    [S]Salir");
                    System.out.println(Color.azul("=================================================================="));
                    System.out.println("Opción:");

                    opcion = teclado.nextLine().toUpperCase();

                    switch (opcion) {
                        case "VS":
                            idSeccion = EntradaTeclado.cadenaLimitada("Código de sección:", 2, 2);
                            System.out.println(Supermercado.valorStockSeccion(idSeccion));
                            break;
                        case "PD":
                            System.out.println(Supermercado.selectAllProductosOrderBy("descripcion"));
                            break;
                        case "PCP":
                            System.out.println(Supermercado.selectAllProductosOrderBy("id_producto"));
                            break;
                        case "PCS":
                            System.out.println(Supermercado.selectAllProductosOrderBy("id_seccion"));
                            break;
                        case "PS":
                            idSeccion = EntradaTeclado.cadenaLimitada("Código de sección:", 2, 2);
                            System.out.println(Supermercado.productosDeSección(idSeccion));
                            break;
                        case "EN": // Empleados ordenados por nombre
                            System.out.println(Supermercado.selectAllEmpleadosOrderBy("nombre"));
                            break;
                        case "ECE": // Empleados ordenados por código de empleado
                            System.out.println(Supermercado.selectAllEmpleadosOrderBy("id_empleado"));
                            break;
                        case "ECS": // Empleados ordenados por código de sección
                            System.out.println(Supermercado.selectAllEmpleadosOrderBy("id_seccion"));
                            break;
                        case "ES": // Empleados de una sección
                            idSeccion = EntradaTeclado.cadenaLimitada("Código de sección:", 2, 2);
                            System.out.println(Supermercado.empleadosDeSeccion(idSeccion));
                            break;
                        case "IP":
                            idSeccion = EntradaTeclado.cadenaLimitada("Código de sección:", 2, 2);
                            porcentaje = EntradaTeclado.nDoublePositivo("Porcentaje de subida de precio:", 2);
                            System.out.println(Supermercado.actualizarPrecioSeccion(idSeccion, porcentaje));
                            break;
                        case "IS": // Incrementar el salario de los empleados de una sección
                            idSeccion = EntradaTeclado.cadenaLimitada("Código de sección:", 2, 2);
                            porcentaje = EntradaTeclado.nDoublePositivo("Porcentaje de subida de salario:", 2);
                            System.out.println(Supermercado.aumentarSalarioSeccion(idSeccion, porcentaje));
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
            } else {
                System.out.println(Color.rojo("ERROR: No se pudo establecer conexión con la base de datos."));
            }
        } else {
            System.out.println(Color.rojo("ERROR: No se pudo iniciar el servidor H2."));
        }
        H2JDBC.cerrarConexion();
        H2ServerManager.detenerServidorH2();
    }
}