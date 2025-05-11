package aplicacionJDBC;

import util.Color;
import H2.H2JDBC;
import java.sql.*;
import java.util.Arrays; // Necesario para la validación del parámetro orderBy

/**
 * Clase que contiene métodos estáticos para realizar diversas consultas y operaciones
 * sobre la base de datos del supermercado, tales como listar productos, empleados,
 * y calcular valores totales relacionados con los productos y empleados de diferentes secciones.
 *
 * @author IES Aguadulce
 * @version abril/2025
 */
public class Supermercado {

    /**
     * Constructor privado para evitar la creación de instancias de esta clase.
     */
    private Supermercado() {
    }

    /**
     * Realiza una consulta de todos los productos de la base de datos y los ordena
     * según el parámetro proporcionado.
     *
     * @param orderBy El criterio por el cual se ordenarán los productos
     * @return Un String con la lista de productos ordenada, o un mensaje de error si ocurre un fallo
     */
    public static String selectAllProductosOrderBy(String orderBy) {
        StringBuilder resultadoSelect = new StringBuilder();

        // Validar el parámetro orderBy para prevenir inyección SQL y errores
        String[] camposPermitidos = {"id_producto", "descripcion", "precio", "stock_actual", "id_seccion"};
        if (!Arrays.asList(camposPermitidos).contains(orderBy)) {
            return Color.rojo("ERROR: Criterio de ordenación no válido para productos: " + orderBy);
        }

        resultadoSelect.append(String.format("Productos ordenados por %s%n", orderBy));

        // Definir el formato de las columnas
        String formatoCabecera = "%-6s %-40s %9s %6s %-7s%n";
        String formatoFila = "%-6s %-40s %9.2f %6d %7s%n";

        // Agregar cabecera
        resultadoSelect.append(Color.azul(String.format(formatoCabecera,
                "CÓDIGO",
                "DESCRIPCIÓN",
                "PRECIO",
                "STOCK",
                "SECCIÓN")));

        String sentenciaSQL = "SELECT * FROM producto ORDER BY " + orderBy + " ASC";

        try (PreparedStatement st = H2JDBC.getConexion().prepareStatement(sentenciaSQL)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                resultadoSelect.append(String.format(formatoFila,
                        rs.getString("id_producto"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getInt("stock_actual"),
                        rs.getString("id_seccion")));
            }
        } catch (SQLException ex) {
            resultadoSelect.append(Color.rojo(String.format("ERROR al obtener productos ordenados por %s: %s%n", orderBy, ex.getMessage())));
        }
        return resultadoSelect.toString();
    }

    /**
     * Calcula el valor total de los productos en el supermercado (precio * stock_actual).
     *
     * @return Un String con el valor total de los productos o un mensaje de error si ocurre un fallo
     */
    public static String valorStockTotal() {
        StringBuilder resultadoSelect = new StringBuilder();

        resultadoSelect.append(String.format("Valor total de todos productos del supermercado: "));
        try (PreparedStatement st = H2JDBC.getConexion().prepareStatement(String.format("SELECT SUM(precio*stock_actual) AS valorStock FROM producto"))) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                resultadoSelect.append(String.format("%,.2f€", rs.getDouble("valorStock")));
                resultadoSelect.append("\n");
            }
        } catch (SQLException ex) {
            resultadoSelect.append(Color.rojo(String.format("ERROR: no se puede obtener el valor del supermercado: %s%n", ex.getMessage()))).append("\n");
        }
        return resultadoSelect.toString();
    }

    /**
     * Calcula el valor total de los productos de una sección específica (precio * stock_actual).
     *
     * @param id_seccion El identificador de la sección cuyo valor total se calculará
     * @return Un String con el valor total de los productos de la sección o un mensaje de error si ocurre un fallo
     */
    public static String valorStockSeccion(String id_seccion) {
        StringBuilder resultadoSelect = new StringBuilder();
        String descripcionSec = Supermercado.descripcionSeccion(id_seccion);

        if (descripcionSec.isEmpty()) { // Verifica que la sección existe
            return Color.rojo("ERROR: La sección con ID '" + id_seccion + "' no existe.");
        }

        resultadoSelect.append(String.format("Valor de los productos de la sección [%s]:", descripcionSec));
        try (PreparedStatement st = H2JDBC.getConexion().prepareStatement("SELECT SUM(precio*stock_actual) AS valorStockSeccion FROM producto WHERE id_seccion=?")) {
            st.setString(1, id_seccion);
            ResultSet rs = st.executeQuery();
            if (rs.next()) { // Siempre habrá una fila, incluso si el SUM es 0 o null
                double valor = rs.getDouble("valorStockSeccion");
                // Comprobar si el valor es SQL NULL (puede ocurrir si no hay productos en la sección)
                if (rs.wasNull()) {
                    resultadoSelect.append(String.format(" No hay productos en esta sección.\n"));
                } else {
                    resultadoSelect.append(String.format("%,.2f€", valor));
                    resultadoSelect.append("\n");
                }
            }
        } catch (SQLException ex) {
            resultadoSelect.append(Color.rojo(String.format("ERROR: No se puede obtener el valor de la sección %s: %s%n", id_seccion, ex.getMessage()))).append("\n");
        }
        return resultadoSelect.toString();
    }

    /**
     * Devuelve una lista de los productos de una sección específica.
     *
     * @param id_seccion El identificador de la sección cuyos productos se mostrarán
     * @return Un String con la lista de productos de la sección o un mensaje de error si ocurre un fallo
     */
    public static String productosDeSección(String id_seccion) {
        StringBuilder resultadoSelect = new StringBuilder();
        String descripcionSec = Supermercado.descripcionSeccion(id_seccion);

        if (descripcionSec.isEmpty()) { // Verifica que la sección existe
            return Color.rojo("ERROR: La sección con ID '" + id_seccion + "' no existe.");
        }

        resultadoSelect.append(String.format("Lista de productos de la sección [%s]\n", descripcionSec));
        // Definir el formato de las columnas
        String formatoCabecera = "%-40s %6s %5s%n";
        String formatoFila = "%-40s %6.2f %5d%n";

        try (PreparedStatement st = H2JDBC.getConexion().prepareStatement("SELECT descripcion, precio, stock_actual FROM producto WHERE id_seccion = ? ORDER BY descripcion")) {
            st.setString(1, id_seccion); 
            ResultSet rs = st.executeQuery();

            if (!rs.isBeforeFirst()) { // Verifica si hay al menos una fila en el resultset
                resultadoSelect.append(Color.rojo("No hay productos en esta sección."));
            } else {
                resultadoSelect.append(Color.azul(String.format(formatoCabecera, "DESCRIPCIÓN", "PRECIO", "STOCK")));

                // Filas de productos con formato de ancho fijo
                while (rs.next()) {
                    resultadoSelect.append(String.format(formatoFila,
                            rs.getString("descripcion"),
                            rs.getDouble("precio"), 
                            rs.getInt("stock_actual")));
                }
            }
        } catch (SQLException ex) {
            resultadoSelect.append(Color.rojo(String.format("ERROR: no se pueden listar los productos de la sección %s: %s%n", id_seccion, ex.getMessage()))).append("\n");
        }
        return resultadoSelect.toString();
    }

    /**
     * Devuelve la descripción de una sección según su identificador.
     *
     * @param id_seccion El identificador de la sección
     * @return La descripción de la sección o una cadena vacía si no existe o hay un error.
     */
    public static String descripcionSeccion(String id_seccion) {
        String descripcion = ""; // Inicializa a cadena vacía
        try (PreparedStatement st = H2JDBC.getConexion().prepareStatement("SELECT descripcion FROM seccion WHERE id_seccion = ?")) {
            st.setString(1, id_seccion);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                descripcion = rs.getString("descripcion");
            }
        } catch (SQLException ex) {
            // Se podría loggear el error, pero el método ya devuelve una cadena vacía.
        }
        return descripcion;
    }

    /**
     * Actualiza el precio de todos los productos de una sección, aplicando un porcentaje de aumento.
     *
     * @param id_seccion El identificador de la sección cuyos productos se actualizarán
     * @param porcentaje El porcentaje de aumento a aplicar
     * @return Un String con el resultado de la operación
     */
    public static String actualizarPrecioSeccion(String id_seccion, double porcentaje) {
        StringBuilder resultado = new StringBuilder();
        String descripcionSec = Supermercado.descripcionSeccion(id_seccion);

        if (descripcionSec.isEmpty()) {
            return Color.rojo("ERROR: La sección con ID '" + id_seccion + "' no existe.");
        }
        
        String sentenciaSQL = "UPDATE producto SET precio = precio + (precio * ? / 100) WHERE id_seccion = ?";

        try (PreparedStatement st = H2JDBC.getConexion().prepareStatement(sentenciaSQL)) {
            st.setDouble(1, porcentaje);
            st.setString(2, id_seccion);
            int filasAfectadas = st.executeUpdate();

            resultado.append(String.format("Actualizando los precios de %d productos en la sección [%s]...", filasAfectadas, descripcionSec));
            if (filasAfectadas > 0) {
                resultado.append(Color.verde("OK"));
            } else {
                resultado.append(Color.rojo(String.format("La sección [%s] no tiene productos o no se pudieron actualizar.", descripcionSec)));
            }
        } catch (SQLException e) {
            resultado.append(Color.rojo(String.format("ERROR: Falló la actualización de los precios en la sección %s: %s%n", id_seccion, e.getMessage())));
        }
        return resultado.toString();
    }

    /**
     * Realiza una consulta de todos los empleados de la base de datos y los
     * ordena según el parámetro proporcionado.
     *
     * @param orderBy El criterio por el cual se ordenarán los empleados
     * (acepta "nombre", "id_empleado", "id_seccion", "salario_anual")
     * @return Un String con la lista de empleados ordenada, o un mensaje de error si ocurre un fallo
     */
    public static String selectAllEmpleadosOrderBy(String orderBy) {
        StringBuilder resultadoSelect = new StringBuilder();

        // Validar el parámetro orderBy para prevenir inyección SQL y errores
        String[] camposPermitidos = {"nombre", "id_empleado", "id_seccion", "salario_anual"};
        if (!Arrays.asList(camposPermitidos).contains(orderBy)) {
            return Color.rojo("ERROR: Criterio de ordenación no válido para empleados: " + orderBy);
        }

        resultadoSelect.append(String.format("Empleados ordenados por %s%n", orderBy));

        // Definir el formato de las columnas
        String formatoCabecera = "%-6s %-30s %15s %-7s%n";
        String formatoFila = "%-6s %-30s %15d %7s%n";

        // Agregar cabecera
        resultadoSelect.append(Color.azul(String.format(formatoCabecera,
                "CÓDIGO",
                "NOMBRE",
                "SALARIO ANUAL",
                "SECCIÓN")));

        String sentenciaSQL = "SELECT id_empleado, nombre, salario_anual, id_seccion FROM empleado ORDER BY " + orderBy + " ASC";

        try (PreparedStatement st = H2JDBC.getConexion().prepareStatement(sentenciaSQL)) {
            ResultSet rs = st.executeQuery();
            if (!rs.isBeforeFirst()) { // Verifica si hay al menos una fila en el resultset
                resultadoSelect.append(Color.rojo("No hay empleados en la base de datos."));
            } else {
                while (rs.next()) {
                    resultadoSelect.append(String.format(formatoFila,
                            rs.getString("id_empleado"),
                            rs.getString("nombre"),
                            rs.getInt("salario_anual"),
                            rs.getString("id_seccion")));
                }
            }
        } catch (SQLException ex) {
            resultadoSelect.append(Color.rojo(String.format("ERROR al obtener empleados ordenados por %s: %s%n", orderBy, ex.getMessage())));
        }
        return resultadoSelect.toString();
    }

    /**
     * Devuelve una lista de los empleados de una sección específica.
     *
     * @param id_seccion El identificador de la sección cuyos empleados se mostrarán
     * @return Un String con la lista de empleados de la sección o un mensaje de error si ocurre un fallo
     */
    public static String empleadosDeSeccion(String id_seccion) {
        StringBuilder resultadoSelect = new StringBuilder();
        String descripcionSec = Supermercado.descripcionSeccion(id_seccion);

        if (descripcionSec.isEmpty()) { // Verifica que la sección existe
            return Color.rojo("ERROR: La sección con ID '" + id_seccion + "' no existe.");
        }

        resultadoSelect.append(String.format("Lista de empleados de la sección [%s]\n", descripcionSec));
        // Definir el formato de las columnas
        String formatoCabecera = "%-6s %-30s %15s%n";
        String formatoFila = "%-6s %-30s %15d%n";

        try (PreparedStatement st = H2JDBC.getConexion().prepareStatement("SELECT id_empleado, nombre, salario_anual FROM empleado WHERE id_seccion = ? ORDER BY nombre ASC")) {
            st.setString(1, id_seccion);
            ResultSet rs = st.executeQuery();

            if (!rs.isBeforeFirst()) { // Verifica si hay al menos una fila en el resultset
                resultadoSelect.append(Color.rojo("No hay empleados en esta sección."));
            } else {
                resultadoSelect.append(Color.azul(String.format(formatoCabecera, "CÓDIGO", "NOMBRE", "SALARIO ANUAL")));

                while (rs.next()) {
                    resultadoSelect.append(String.format(formatoFila,
                            rs.getString("id_empleado"),
                            rs.getString("nombre"),
                            rs.getInt("salario_anual")));
                }
            }
        } catch (SQLException ex) {
            resultadoSelect.append(Color.rojo(String.format("ERROR: no se pueden listar los empleados de la sección %s: %s%n", id_seccion, ex.getMessage()))).append("\n");
        }
        return resultadoSelect.toString();
    }

    /**
     * Aumenta el salario de todos los empleados de una sección aplicando un porcentaje.
     *
     * @param id_seccion El identificador de la sección cuyos empleados se actualizarán
     * @param porcentaje El porcentaje de aumento a aplicar
     * @return Un String con el resultado de la operación
     */
    public static String aumentarSalarioSeccion(String id_seccion, double porcentaje) {
        StringBuilder resultado = new StringBuilder();
        String descripcionSec = Supermercado.descripcionSeccion(id_seccion);

        if (descripcionSec.isEmpty()) {
            return Color.rojo("ERROR: La sección con ID '" + id_seccion + "' no existe.");
        }
        
        if (porcentaje <= 0) {
            return Color.rojo("ERROR: El porcentaje de aumento debe ser un valor positivo.");
        }

        String sentenciaSQL = "UPDATE empleado SET salario_anual = salario_anual + (salario_anual * ? / 100) WHERE id_seccion = ?";

        try (PreparedStatement st = H2JDBC.getConexion().prepareStatement(sentenciaSQL)) {
            st.setDouble(1, porcentaje);
            st.setString(2, id_seccion);
            int filasAfectadas = st.executeUpdate();

            resultado.append(String.format("Actualizando el salario de %d empleados en la sección [%s]...", filasAfectadas, descripcionSec));
            if (filasAfectadas > 0) {
                resultado.append(Color.verde("OK"));
            } else {
                resultado.append(Color.rojo(String.format("La sección [%s] no tiene empleados o no se pudieron actualizar sus salarios.", descripcionSec)));
            }
        } catch (SQLException e) {
            resultado.append(Color.rojo(String.format("ERROR: Falló la actualización de salarios en la sección %s: %s%n", id_seccion, e.getMessage())));
        }
        return resultado.toString();
    }
}