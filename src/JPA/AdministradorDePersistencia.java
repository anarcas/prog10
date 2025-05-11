package JPA;

import util.Color;
import javax.persistence.*;

/**
 * Clase auxiliar que gestiona el ciclo de vida del {@link EntityManagerFactory}
 * para la unidad de persistencia definida en el proyecto.
 *
 * Esta clase proporciona métodos estáticos para:
 * <ul>
 * <li>Crear el {@code EntityManagerFactory}</li>
 * <li>Obtener instancias de {@code EntityManager}</li>
 * <li>Cerrar el {@code EntityManagerFactory}</li>
 * </ul>
 *
 * Se utiliza en la aplicación JPA para centralizar la gestión de la
 * persistencia.
 *
 * @author IES Aguadulce
 * @version abril/2025
 */
public class AdministradorDePersistencia {

    /**
     * Nombre de la unidad de persistencia definida en persistence.xml
     */
    private static final String PERSISTENCE_UNIT = "SupermercadoPersistenceUnit";

    /**
     * Fábrica de EntityManager (conexiones con la base de datos)
     */
    private static EntityManagerFactory emf;

    /**
     * Crea el {@code EntityManagerFactory} necesario para trabajar con JPA.
     * Este método también crea un {@code EntityManager} inicial para que se
     * creen automáticamente las tablas si es necesario.
     *
     * @return {@code true} si se ha creado correctamente, {@code false} si ha
     * habido algún error.
     */
    public static boolean creaEntityManagerFactory() {
        boolean resultado = true;
        System.out.print("Creando EntityManagerFactory...");
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            EntityManager em = AdministradorDePersistencia.getEntityManager(); // Necesario para que se creen las tablas
            System.out.println(Color.verde("OK"));
        } catch (PersistenceException e) {
            System.err.println(Color.rojo("ERROR"));
            resultado = false;
        }
        return resultado;
    }

    /**
     * Devuelve una nueva instancia de {@code EntityManager} para realizar
     * operaciones con la base de datos.
     *
     * @return Un {@code EntityManager} si el factory está disponible, o
     * {@code null} si no se ha creado.
     */
    public static EntityManager getEntityManager() {
        if (emf == null) {
            System.out.println(Color.rojo("ERROR: No existe el EntityManagerFactory."));
            return null;
        }
        return emf.createEntityManager();
    }

    /**
     * Cierra el {@code EntityManagerFactory} si está abierto. Este método debe
     * llamarse al final de la ejecución de la aplicación para liberar recursos.
     */
    public static void cerrarEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            System.out.println(Color.verde("EntityManagerFactory cerrado."));
            emf.close();
        }
    }
}
