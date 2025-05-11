package util;

import java.util.Scanner;

/**
 * Clase utilitaria para facilitar la entrada de datos por teclado con
 * validación.
 *
 * @author IES Aguadulce
 * @version abril/2025
 */
public class EntradaTeclado {

    /**
     * Solicita al usuario un número entero positivo o cero.
     *
     * @param prompt Mensaje que se muestra al usuario para indicar que se pide.
     * @return Número entero introducido por el usuario.
     */
    public static int nEnteroPositivo(String prompt) {
        boolean correcto = false;
        int numero = 0;
        Scanner teclado = null;
        do {
            try {
                System.out.print(prompt);
                teclado = new Scanner(System.in);
                numero = teclado.nextInt();
                if (numero >= 0) {
                    correcto = true;
                } else {
                    System.out.println(Color.rojo("ERROR: Debe ser positivo o cero."));
                }
            } catch (Exception e) {
                System.out.println(Color.rojo("ERROR: No es un número entero."));
            }
        } while (!correcto);
        return numero;
    }

    /**
     * Solicita al usuario un número real positivo o cero.
     *
     * @param prompt Mensaje que se muestra al usuario para indicar que se pide.
     * @return Número real introducido por el usuario.
     */
    public static double nDouble(String prompt) {
        boolean correcto = false;
        double numero = 0;
        Scanner teclado = null;
        do {
            try {
                System.out.print(prompt);
                teclado = new Scanner(System.in);
                numero = teclado.nextInt();
                if (numero >= 0) {
                    correcto = true;
                } else {
                    System.out.println(Color.rojo("ERROR: Debe ser positivo o cero."));
                }
            } catch (Exception e) {
                System.out.println(Color.rojo("ERROR: No es un número real."));
            }

        } while (!correcto);
        return numero;
    }

    /**
     * Solicita un número real positivo o cero con una cantidad específica de
     * decimales.
     *
     * @param prompt Mensaje que se muestra al usuario para indicar que se pide.
     * @param decimales Número máximo de decimales permitidos.
     * @return Número real introducido por el usuario.
     */
    public static double nDoublePositivo(String prompt, int decimales) {
        boolean correcto = false;
        double numero = 0;
        Scanner teclado = new Scanner(System.in);
        String regex = "^\\d+(\\.\\d{1," + decimales + "})?$"; // Expresión regular para validar decimales

        do {
            try {
                System.out.print(prompt);
                String entrada = teclado.next();
                if (!entrada.matches(regex)) {
                    throw new IllegalArgumentException();
                }
                numero = Double.parseDouble(entrada);
                if (numero >= 0) {
                    correcto = true;
                } else {
                    System.out.println(Color.rojo("ERROR: Debe ser positivo o cero."));
                }
            } catch (IllegalArgumentException e) {
                System.out.println(Color.rojo("ERROR: No es un número real de " + decimales + " decimales."));
            }
        } while (!correcto);

        return numero;
    }

    /**
     * Solicita al usuario una cadena de texto.
     *
     * @param prompt Mensaje que se muestra al usuario para indicar que se pide.
     * @return Cadena introducida por el usuario.
     */
    public static String cadena(String prompt) {
        Scanner teclado = new Scanner(System.in);
        String cadena = "";

        System.out.print(prompt);
        cadena = teclado.nextLine();

        return cadena;
    }

    /**
     * Solicita al usuario una cadena cuya longitud esté entre los límites
     * especificados.
     *
     * @param prompt Mensaje que se muestra al usuario para indicar que se pide.
     * @param longitudMinima Longitud mínima permitida.
     * @param longitudMaxima Longitud máxima permitida.
     * @return Cadena válida introducida por el usuario.
     */
    public static String cadenaLimitada(String prompt, int longitudMinima, int longitudMaxima) {
        Scanner teclado = new Scanner(System.in);
        String cadena;

        do {
            System.out.print(prompt);
            cadena = teclado.nextLine().trim();

            if (cadena.length() < longitudMinima || cadena.length() > longitudMaxima) {
                if (longitudMinima == longitudMaxima) {
                    System.out.println(Color.rojo("ERROR: Debe tener " + longitudMinima + " caracteres."));
                } else {
                    System.out.println(Color.rojo("ERROR: Debe tener entre "
                            + longitudMinima + " y " + longitudMaxima + " caracteres."));
                }
            }
        } while (cadena.length() < longitudMinima || cadena.length() > longitudMaxima);

        return cadena;
    }
}
