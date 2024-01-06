import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Esta clase representa el programa Ejercicio2, el cual permite convertir valores de una moneda a otra.
 */
public class Ejercicio2 {    
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    /**
     * Método principal que inicia la ejecución del programa.
     * Permite al usuario realizar conversiones de moneda.     *
     */
    public static void main(String[] args) throws IOException{
        do{
            double vEntrada = 0;
            String moneda1, moneda2;
            System.out.println("Ingrese la moneda a convertir:");
            System.out.println("CLP, ARS, USD, EUR, TRY, GBP");
            moneda1 = in.readLine();
            System.out.println("Ingrese la moneda a la que desea convertir:");
            System.out.println("CLP, ARS, USD, EUR, TRY, GBP");
            moneda2 = in.readLine();
            System.out.println("Ingrese el valor a convertir:");
            vEntrada = Double.parseDouble(in.readLine());
            System.out.println("El valor convertido es: " + String.format("%.6f", convertir(moneda1, moneda2, vEntrada)));  
            System.out.println("Confirma el cambio para el retiro?(SI/NO):");  
            resultOperacion(in.readLine());            
            System.out.println("Desea realizar otra operación?(SI/NO):.");
        }while(validaCambio(in.readLine()));
    }

    /**
     * Convierte una cantidad de una moneda a otra.
     * 
     * @param moneda1 la moneda de entrada
     * @param moneda2 la moneda de salida
     * @param vEntrada el valor de entrada a convertir
     * @return el valor convertido a la moneda de salida
     */
    public static double convertir(String moneda1, String moneda2, double vEntrada){
        double vSalida = 0, vAux = 0;
        String[] monedas = {"CLP", "ARS", "USD", "EUR", "TRY", "GBP"};
        double[] valores = {0.0011, 0.0012, 1, 1.09, 0.034, 1.27};
        int pos1 = 0, pos2 = 0;
        for (int i = 0; i < monedas.length; i++) {
            if (moneda1.toUpperCase().equals(monedas[i])) {
                pos1 = i;
            }
            if (moneda2.toUpperCase().equals(monedas[i])) {
                pos2 = i;
            }
        }
        vAux = vEntrada * valores[pos1] / valores[2];
        vSalida = vAux * valores[2] / valores[pos2];
        return vSalida;
        }

    /**
     * Valida si el cambio es requerido por el cliente.
     * 
     * @param confirmar la cadena a validar
     * @return true si el cambio es válido, false de lo contrario
     */
    public static boolean validaCambio(String confirmar){
        boolean aux = false;
        if (confirmar.toUpperCase().equals("SI")) {
            aux = true;
        }
        return aux;
    }

    /**
     * Valida la elección del cliente y muestra un mensaje de éxito o cancelación.
     * 
     * @param confirmar el valor de confirmación para realizar la operación
     */
    public static void resultOperacion(String confirmar){
        if(validaCambio(confirmar)){
                System.out.println("Operación realizada con éxito.");
            }else{
                System.out.println("Operación cancelada.");
            }
    }
}
