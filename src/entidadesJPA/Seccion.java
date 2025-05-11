package entidadesJPA;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * Entidad JPA que representa una sección dentro de la tienda o supermercado.
 * 
 * <p>Una sección puede contener productos y empleados. Cada sección tiene un
 * identificador único {@code idSeccion} y una descripción asociada.</p>
 * 
 * @author IES Aguadulce
 * @version abril/2025
 */
@Entity
public class Seccion implements Serializable {

    /**
     * Identificador único de la sección.
     * Longitud máxima: 2 caracteres.
     * 
     * @see #getIdSeccion()
     * @see #setIdSeccion(String)
     */
    @Id
    @Column(name = "id_seccion", length = 2)
    private String idSeccion;

    /**
     * Descripción de la sección.
     * Longitud máxima: 15 caracteres. No puede ser nula.
     * 
     * @see #getDescripcion()
     * @see #setDescripcion(String)
     */
    @Column(name = "descripcion", length = 15, nullable = false)
    private String descripcion;

    /**
     * Lista de productos asociados a esta sección.
     * Relación uno-a-muchos con la entidad {@link Producto}.
     * 
     * @see #getProductos()
     * @see #setProductos(List)
     */
    @OneToMany(mappedBy = "seccion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Producto> productos;

    /**
     * Lista de empleados asociados a esta sección.
     * Relación uno-a-muchos con la entidad {@link Empleado}.
     * 
     * @see #getEmpleados()
     * @see #setEmpleados(List)
     */
     //  ¡¡¡ATENCIÓN!!! Hay que definir la relación.

    /**
     * Constructor vacío requerido por JPA.
     */
    public Seccion() {
    }

    /**
     * Constructor con parámetros para crear una sección con identificador y descripción.
     * 
     * @param idSeccion Identificador de la sección
     * @param descripcion Descripción de la sección
     */
    public Seccion(String idSeccion, String descripcion) {
        this.idSeccion = idSeccion;
        this.descripcion = descripcion;
    }

    /**
     * Devuelve el identificador de la sección.
     * 
     * @return Identificador de la sección
     */
    public String getIdSeccion() {
        return idSeccion;
    }

    /**
     * Establece el identificador de la sección.
     * 
     * @param idSeccion Nuevo identificador de la sección
     */
    public void setIdSeccion(String idSeccion) {
        this.idSeccion = idSeccion;
    }

    /**
     * Devuelve la descripción de la sección.
     * 
     * @return Descripción de la sección
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la sección.
     * 
     * @param descripcion Nueva descripción de la sección
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Devuelve la lista de productos asociados a esta sección.
     * 
     * @return Lista de productos
     */
    public List<Producto> getProductos() {
        return productos;
    }

    /**
     * Establece la lista de productos asociados a esta sección.
     * 
     * @param productos Nueva lista de productos
     */
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    /**
     * Devuelve la lista de empleados asociados a esta sección.
     * 
     * @return Lista de empleados
     */
        //  ¡¡¡ATENCIÓN!!! Método no implementado.

    /**
     * Establece la lista de empleados asociados a esta sección.
     * 
     * @param empleados Nueva lista de empleados
     */
        //  ¡¡¡ATENCIÓN!!! Método no implementado.

    /**
     * Devuelve una representación en texto de la sección.
     * 
     * @return Cadena con los datos de la sección
     */
    @Override
    public String toString() {
        return this.getIdSeccion() + " " + this.getDescripcion();
    }
}
