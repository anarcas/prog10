package JPA;

import javax.persistence.*;
import java.util.List;

/**
 * Clase genérica que facilita las operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * sobre entidades persistentes usando JPA.
 *
 * @param <T> Tipo de entidad gestionada por esta clase.
 * 
 * @author IES Aguadulce
 * @version abril/2025
 */
public class GestorDePersistenciaGenerico<T> {

    private final EntityManager em;
    private final Class<T> entityClass;

    /**
     * Crea un nuevo gestor de persistencia para una clase de entidad específica.
     *
     * @param entityClass Clase de la entidad a gestionar.
     */
    public GestorDePersistenciaGenerico(Class<T> entityClass) {
        this.em = AdministradorDePersistencia.getEntityManager();
        this.entityClass = entityClass;
    }

    /**
     * Inserta una nueva entidad en la base de datos.
     *
     * @param entidad Entidad que se desea insertar.
     * @return {@code true} si la operación fue exitosa, {@code false} en caso contrario.
     */
    public boolean insertar(T entidad) {
        boolean resultado = false;
        try {
            em.getTransaction().begin();
            em.persist(entidad);
            em.getTransaction().commit();
            resultado = true;
        } catch (PersistenceException e) {
            // Hacemos rollback en caso de error
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        return resultado;
    }

    /**
     * Actualiza una entidad existente en la base de datos.
     *
     * @param entidad Entidad que se desea actualizar.
     * @return {@code true} si la operación fue exitosa, {@code false} en caso contrario.
     */
    public boolean actualizar(T entidad) {
        boolean resultado = false;
        try {
            em.getTransaction().begin();
            em.merge(entidad);
            em.getTransaction().commit();
            resultado = true;
        } catch (PersistenceException e) {
            // Manejo de excepciones en la actualización
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        return resultado;
    }

    /**
     * Elimina una entidad de la base de datos a partir de su clave primaria.
     *
     * @param id Clave primaria de la entidad que se desea eliminar.
     * @return {@code true} si la operación fue exitosa, {@code false} si la entidad no existe o hay error.
     */
    public boolean eliminar(Object id) {
        boolean resultado = false;
        try {
            em.getTransaction().begin();
            T entidad = em.find(entityClass, id);
            if (entidad != null) {
                em.remove(entidad);
                resultado = true;
            }
            em.getTransaction().commit();
        } catch (PersistenceException e) {
            // Manejo de excepciones en la eliminación
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        return resultado;
    }

    /**
     * Busca una entidad en la base de datos por su clave primaria.
     *
     * @param id Clave primaria de la entidad.
     * @return La entidad encontrada, o {@code null} si no existe.
     */
    public T buscar(Object id) {
        return em.find(entityClass, id);
    }

    /**
     * Recupera todas las entidades del tipo gestionado desde la base de datos.
     *
     * @return Lista con todas las entidades persistidas.
     */
    public List<T> listarTodos() {
        return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass).getResultList();
    }
}
