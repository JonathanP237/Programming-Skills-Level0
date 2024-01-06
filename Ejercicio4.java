import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Ejercicio4 {
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        ArrayList<usuario> registrados = new ArrayList<usuario>();
        usuario usActual = new usuario();
        registrados.add(new usuario("6", "Luisa1015"));
        registrados.add(new usuario("7", "Pedro1016"));
        String id;
        do {
            System.out.println("\nIngrese su id o SALIR para salir:");
            id = in.readLine();
            usActual = login(id, registrados);
            if (usActual != null && usActual.getActivo() == true) {
                menu();
            } else if(!id.toUpperCase().equals("SALIR")) {
                System.out.println("Usuario no registrado o bloqueado.");
            }
        } while(!id.toUpperCase().equals("SALIR"));
    }

    /**
     * Función que válida el login de los usuarios, validando id y contraseña.
     *
     * @param id the user's ID
     * @param registrados the list of registered users
     * @return the logged-in user
     */
    public static usuario login(String id, ArrayList<usuario> registrados) throws IOException {
        int intentos = 0;
        usuario usActual = new usuario();
        for (int i = 0; i < registrados.size(); i++) {
            if (registrados.get(i).getId().equals(id)) {
                usActual = registrados.get(i);
                break;
            }
        }
        while (intentos < 3 && usActual.getActivo() == true) {
            if (usActual.getId() != null) {
                System.out.println("\nIngrese Contraseña:");
                String pass = in.readLine();
                if (usActual.getPass().equals(pass)) {
                    break;
                } else {
                    intentos++;
                    System.out.println("Contraseña incorrecta, intentos restantes: " + (3 - intentos));
                    if (intentos == 3) {
                        usActual.setActivo(false);
                        System.out.println("\nSu cuenta ha sido bloqueada.");
                    }
                }
            } else {
                usActual = null;
                break;
            }
        }
        return usActual;
    }

    /**
     * Muestra un menú de opciones y realiza la acción correspondiente según la opción seleccionada.
     * 
     */
    public static void menu() throws IOException {
        int opcion;
        do{
            System.out.println("\n1. Realizar envío");
            System.out.println("0. Salir");
            System.out.println("\nIngrese una opción:");
            opcion = Integer.parseInt(in.readLine());
        switch (opcion) {
            case 1:
                enviar();
                break;
            case 0:
                System.out.println("Saliendo...");
                break;
            default:
                System.out.println("Opción inválida.");
                break;
            }
        }while(opcion!=0);
    }

    /**
     * Realiza el proceso de envío de paquetes.
     * Permite ingresar los datos del destinatario, calcular el costo del envío,
     * confirmar el envío y repetir el proceso para realizar otro envío si se desea.
     */
    private static void enviar() throws IOException {
        String otro;
        do{
            String numero, nombre, direccion;
        double peso, costo;
        System.out.println("Ingrese el nombre del destinatario:");
        nombre = in.readLine();
        System.out.println("Ingrese la dirección del destinatario:");
        direccion = in.readLine();
        numero = String.valueOf((int)(Math.random()*1000000));
        System.out.println("Ingrese el peso del paquete:");
        peso = Double.parseDouble(in.readLine());
        costo = peso * 2;
        System.out.println("El costo del envío es: " + costo);
        System.out.println("El paquete se enviará a: " + nombre + " con dirección: " + direccion + " y número paquete: " + numero);
        System.out.println("¿Desea confirmar el envío? (S/N)");
        String confirmacion = in.readLine();
        if (confirmacion.toUpperCase().equals("S")) {
            System.out.println("El paquete ha sido enviado.");
        } else {
            System.out.println("El paquete no ha sido enviado.");
        }
        System.out.println("¿Desea realizar otro envío? (S/N)");
        otro = in.readLine();
        }while(otro.toUpperCase().equals("S"));
    }
}


/**
 * Clase que representa a un usuario.
 */
class usuario {
    private String id, pass;
    boolean activo = true;

    /**
     * Constructor vacío de la clase usuario.
     */
    public usuario() {
    }

    /**
     * Constructor de la clase usuario que recibe el id y la contraseña del usuario.
     * @param id el id del usuario
     * @param pass la contraseña del usuario
     */
    public usuario(String id, String pass) {
        this.id = id;
        this.pass = pass;
        this.activo = true;
    }

    /**
     * Método para obtener el id del usuario.
     * @return el id del usuario
     */
    public String getId() {
        return id;
    }

    /**
     * Método para obtener la contraseña del usuario.
     * @return la contraseña del usuario
     */
    public String getPass() {
        return pass;
    }

    /**
     * Método para obtener el estado de activo del usuario.
     * @return true si el usuario está activo, false si no lo está
     */
    public boolean getActivo() {
        return activo;
    }

    /**
     * Método para establecer el estado de activo del usuario.
     * @param activo true si el usuario está activo, false si no lo está
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
