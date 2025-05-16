package entidadesJPA;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Objects;

/**
 * Entidad JPA que representa un empleado del supermercado.
 * <p>Contiene información sobre su identificador, nombre, salario anual y
 * sección a la que pertenece. Cada empleado está vinculado a una {@link Seccion}.</p>
 * <p>La clave primaria es el campo {@code idEmpleado}.</p>
 *
 * @author Antonio Naranjo Castillo
 * @version mayo/2025
 */
@Entity // Se marca esta clase como una entidad JPA
@Table(name = "empleado") // Se especifica el nombre de la tabla si es diferente al nombre de la clase
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L; // Identificador para la serialización

    /**
     * Identificador único del empleado.
     * Longitud máxima: 4 caracteres.
     */
    @Id // Se marca el campo como clave primaria
    @Column(name = "id_empleado", length = 4) // Mapea a la columna id_empleado de tipo CHAR(4)
    private String idEmpleado;

    /**
     * Nombre del empleado.
     * Longitud máxima: 30 caracteres. No puede ser nulo.
     */
    @Column(name = "nombre", length = 30, nullable = false) // Mapea a la columna nombre de tipo VARCHAR(30)
    private String nombre;

    /**
     * Salario anual del empleado. No puede ser nulo.
     */
    @Column(name = "salario_anual", nullable = false) // Mapea a la columna salario_anual de tipo INT
    private Integer salarioAnual; // Se usa Integer para consistencia con otros Integer de JPA

    /**
     * Sección a la que pertenece el empleado.
     * Asociación muchos-a-uno con la entidad {@link Seccion}.
     * Esto indica que muchos empleados pueden pertenecer a una única sección.
     */
    @ManyToOne // Define una relación muchos-a-uno
    @JoinColumn(name = "id_seccion", nullable = false) // Se especifica la columna de clave foránea en la tabla 'empleado'
    private Seccion seccion;

    /**
     * Constructor vacío requerido por JPA.
     */
    public Empleado() {
    }

    /**
     * Constructor completo para crear un nuevo empleado con todos los atributos.
     *
     * @param idEmpleado   Identificador del empleado
     * @param nombre       Nombre del empleado
     * @param salarioAnual Salario anual del empleado
     * @param seccion      Sección a la que pertenece el empleado
     */
    public Empleado(String idEmpleado, String nombre, Integer salarioAnual, Seccion seccion) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.salarioAnual = salarioAnual;
        this.seccion = seccion;
    }

    // --- Getters y Setters ---

    /**
     * Devuelve el identificador del empleado.
     *
     * @return ID del empleado
     */
    public String getIdEmpleado() {
        return idEmpleado;
    }

    /**
     * Establece el identificador del empleado.
     *
     * @param idEmpleado Nuevo ID del empleado
     */
    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    /**
     * Devuelve el nombre del empleado.
     *
     * @return Nombre del empleado
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del empleado.
     *
     * @param nombre Nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el salario anual del empleado.
     *
     * @return Salario anual del empleado
     */
    public Integer getSalarioAnual() {
        return salarioAnual;
    }

    /**
     * Establece el salario anual del empleado.
     *
     * @param salarioAnual Nuevo salario anual
     */
    public void setSalarioAnual(Integer salarioAnual) {
        this.salarioAnual = salarioAnual;
    }

    /**
     * Devuelve la sección asociada al empleado.
     *
     * @return Sección del empleado
     */
    public Seccion getSeccion() {
        return seccion;
    }

    /**
     * Establece la sección del empleado.
     *
     * @param seccion Nueva sección asociada
     */
    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }

    // --- Métodos para gestión de identidad (equals y hashCode) ---

    /**
     * Calcula el código hash del objeto Empleado basado en su clave primaria.
     *
     * @return Código hash del objeto
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpleado != null ? idEmpleado.hashCode() : 0);
        return hash;
    }

    /**
     * Compara este objeto Empleado con otro objeto para determinar si son iguales.
     * La igualdad se basa en la clave primaria (idEmpleado).
     *
     * @param object El objeto con el que se compara.
     * @return {@code true} si los objetos son iguales, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object object) {
        // Comprobación de referencia, si son el mismo objeto en memoria
        if (this == object) {
            return true;
        }

        // Comprobación de tipo y nulidad, si el objeto es nulo o no es del mismo tipo
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        // Casteo al tipo correcto
        Empleado other = (Empleado) object;

        // Compara por la clave primaria
        return Objects.equals(this.idEmpleado, other.idEmpleado);
    }

    // --- Método toString ---

    /**
     * Devuelve una representación en texto del empleado.
     *
     * @return Cadena con los datos del empleado
     */
    @Override
    public String toString() {
        return "Empleado{" +
               "idEmpleado='" + idEmpleado + '\'' +
               ", nombre='" + nombre + '\'' +
               ", salarioAnual=" + salarioAnual +
               ", seccion=" + (seccion != null ? seccion.getDescripcion() : "N/A") +
               '}';
    }
}