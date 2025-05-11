package util;

/**
 * Clase utilitaria para aplicar colores ANSI a textos mostrados por consola.
 * <p>
 * Solo funciona en consolas que soporten secuencias ANSI.
 *
 * @author IES Aguadulce
 * @version abril/2025
 */
public class Color {

    /**
     * Código ANSI para letra azul.
     */
    private static final String LETRA_AZUL = "\u001B[34m";

    /**
     * Código ANSI para letra roja.
     */
    private static final String LETRA_ROJA = "\u001B[31m";

    /**
     * Código ANSI para letra verde.
     */
    private static final String LETRA_VERDE = "\u001B[32m";

    /**
     * Código ANSI para reiniciar el color.
     */
    private static final String RESET = "\u001B[0m";

    /**
     * Devuelve el texto formateado con color azul.
     *
     * @param texto Texto a colorear.
     * @return Texto con formato azul para consola.
     */
    public static String azul(String texto) {
        return (LETRA_AZUL + texto + RESET);
    }

    /**
     * Devuelve el texto formateado con color verde.
     *
     * @param texto Texto a colorear.
     * @return Texto con formato verde para consola.
     */
    public static String verde(String texto) {
        return (LETRA_VERDE + texto + RESET);
    }

    /**
     * Devuelve el texto formateado con color rojo.
     *
     * @param texto Texto a colorear.
     * @return Texto con formato rojo para consola.
     */
    public static String rojo(String texto) {
        return (LETRA_ROJA + texto + RESET);
    }

}
