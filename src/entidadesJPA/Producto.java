/**
 * Entidad JPA que representa un producto del supermercado.
 * 
 * <p>Contiene información sobre su identificador, descripción, precio, stock y
 * sección asociada. Cada producto está vinculado a una {@link Seccion}.</p>
 * 
 * <p>La clave primaria es el campo {@code idProducto}.</p>
 * 
 * @author IES Aguadulce
 * @version abril/2025
 */
package entidadesJPA;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Producto implements Serializable {

    /**
     * Identificador único del producto.
     * Longitud máxima: 4 caracteres.
     */
    @Id
    @Column(name = "id_producto", length = 4)
    private String idProducto;

    /**
     * Descripción del producto.
     * Longitud máxima: 40 caracteres. No puede ser nula.
     */
    @Column(name = "descripcion", length = 40, nullable = false)
    private String descripcion;

    /**
     * Precio del producto. No puede ser nulo.
     * Precisión de hasta 6 dígitos con 2 decimales.
     */
    @Column(name = "precio", precision = 6, scale = 2, nullable = false)
    private double precio;

    /**
     * Stock actual disponible del producto. No puede ser nulo.
     */
    @Column(name = "stock_actual", nullable = false)
    private Integer stockActual;

    /**
     * Sección a la que pertenece el producto.
     * Asociación muchos-a-uno con la entidad {@link Seccion}.
     */
    @ManyToOne
    @JoinColumn(name = "id_seccion", nullable = false)
    private Seccion seccion;

    /**
     * Constructor vacío requerido por JPA.
     */
    public Producto() {
    }

    /**
     * Constructor completo para crear un nuevo producto con todos los atributos.
     * 
     * @param idProducto Identificador del producto
     * @param descripcion Descripción del producto
     * @param precio Precio del producto
     * @param seccion Sección a la que pertenece
     * @param stockActual Stock actual disponible
     */
    public Producto(String idProducto, String descripcion, double precio, Seccion seccion, Integer stockActual) {
        this.idProducto = idProducto;
        this.descripcion = descripcion;
        this.precio = precio;
        this.seccion = seccion;
        this.stockActual = stockActual;
    }

    /**
     * Devuelve el identificador del producto.
     * @return ID del producto
     */
    public String getIdProducto() {
        return idProducto;
    }

    /**
     * Establece el identificador del producto.
     * @param idProducto Nuevo ID del producto
     */
    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Devuelve la descripción del producto.
     * @return Descripción del producto
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del producto.
     * @param descripcion Nueva descripción
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Devuelve el precio del producto.
     * @return Precio del producto
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del producto.
     * @param precio Nuevo precio
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Devuelve el stock actual del producto.
     * @return Stock actual
     */
    public Integer getStockActual() {
        return stockActual;
    }

    /**
     * Establece el stock actual del producto.
     * @param stockActual Nuevo stock actual
     */
    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }

    /**
     * Devuelve la sección asociada al producto.
     * @return Sección del producto
     */
    public Seccion getSeccion() {
        return seccion;
    }

    /**
     * Establece la sección del producto.
     * @param seccion Nueva sección asociada
     */
    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }

    /**
     * Devuelve una representación en texto del producto.
     * @return Cadena con los datos del producto
     */
    @Override
    public String toString() {
        return this.getIdProducto() + " "
                + this.getDescripcion() + " "
                + this.getPrecio() + " "
                + this.getStockActual() + " "
                + this.getSeccion().toString();
    }
}
