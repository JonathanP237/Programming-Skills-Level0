import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Clase principal que contiene el método main y las funcionalidades del programa.
 */
public class Ejercicio1 {
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

/**
 * The main method is the entry point of the Java program.
 * It initializes a list of clients, prompts the user to enter a valid user ID,
 * validates the user ID and password, and displays a menu for the authenticated user.
 * The program continues to prompt the user until they enter "SALIR" to exit.
 * After the user exits, a farewell message is displayed.
 *
 * @param args the command line arguments
 * @throws IOException if an I/O error occurs
 */
    public static void main(String[] args) throws IOException {
        Cliente c1 = new Cliente("1010", "Pablo", "Pablo1010");
        Cliente c2 = new Cliente("1011", "Oscar", "Oscar1011");
        Cliente c3 = new Cliente("1012", "Laura", "Laura1012");
        Cliente c4 = new Cliente("1013", "Carla", "Carla1013");
        Cliente c5 = new Cliente("1014", "Sonia", "Sonia1014");
        ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
        String id;
        boolean aux;

        listaClientes.add(c1);
        listaClientes.add(c2);
        listaClientes.add(c3);
        listaClientes.add(c4);
        listaClientes.add(c5);

        System.out.println("Ingrese un usuario válido o \"SALIR\" para salir:");

        do {
        	aux = true;
            System.out.println("Ingrese Id:");
            id = in.readLine();
            Cliente cActual = validaUsuario(id, listaClientes);
            aux = validaPass(cActual);
            if(aux) {
            	menu(cActual,listaClientes);
            }            
        } while (!id.toUpperCase().equals("SALIR"));
        System.out.println("Gracias por utilizar nuestro servicios");	
    }

/**
 * Función que valida a los usuarios.
 *
 * @param id El identificador del cliente.
 * @param listaClientes La lista de clientes.
 * @return El cliente válido.
 * @throws IOException Si ocurre un error de entrada/salida.
 *
 */
    public static Cliente validaUsuario(String id, ArrayList<Cliente> listaClientes) throws IOException {
        Cliente cliente = new Cliente();
        for (int i = 0; i < listaClientes.size(); i++) {
            if (listaClientes.get(i).getId().equals(id)) {
                cliente = listaClientes.get(i);
                break;
            }
        }
        if (cliente.getActivo() == false && cliente.getId() != null) {
            System.out.println("Su cuenta se encuentra bloqueada.");
        }
        return cliente;
    }

/**
 * Valida la contraseña de un cliente.
 * 
 * @param cliente el cliente cuya contraseña se va a validar
 * @return true si la contraseña es válida, false de lo contrario
 * @throws IOException si ocurre un error de entrada/salida
 */
    public static boolean validaPass(Cliente cliente) throws IOException {
        int intentos = 0;
        boolean aux = false;
        while (cliente.getActivo() == true) {
            if (cliente.getId() != null) {
                System.out.println("Ingrese Contraseña:");
                System.out.println(cliente.getPass());
                String pass = in.readLine();
                if (cliente.getPass().equals(pass)) {
                	aux = true;
                	break;
                } else {
                    intentos++;
                    bloqueoCuenta(cliente, intentos);
                }
            }
        }
        return aux;
    }

/**
 * Bloquea la cuenta del cliente si se han realizado 3 intentos fallidos de acceso o si la cuenta está inactiva.
 * @param cliente el cliente cuya cuenta se va a bloquear
 * @param intentos el número de intentos fallidos de acceso
 */
    public static void bloqueoCuenta(Cliente cliente, int intentos) {
        if (intentos == 3 || cliente.getActivo() != true) {
            cliente.setActivo(false);
            System.out.println("Su cuenta ha sido bloqueda.");
        }
    }

/**
 * Muestra el menú principal y permite al usuario realizar diferentes operaciones bancarias.
 * 
 * @param cliente el objeto Cliente que representa al usuario actual
 * @param listaClientes la lista de todos los clientes registrados en el sistema
 * @throws NumberFormatException si se produce un error al convertir una cadena en un número entero
 * @throws IOException si se produce un error de entrada o salida durante la lectura de datos
 */
    public static void menu(Cliente cliente,ArrayList<Cliente> listaClientes) throws NumberFormatException, IOException {
        int opcion = 0;
        int monto = 0;
        String id = "";
        do {
            System.out.println("Menú Principal\n1- Deposito\n2- Retiro");
            System.out.println("3- Consulta\n4- Transferencia\n0-Cerrar Sesión");
            opcion = validarEntero();
            switch (opcion) {
            case 1:
            	System.out.println("Ingrese el monto del deposito");
            	monto = validarEntero();
            	if(validarMonto(monto)) {
            		deposito(cliente,monto);
            	}else {
            		System.out.println("Ingrese un monto válido");
            	}
                break;
            case 2:
            	System.out.println("Ingrese el monto del retiro");
            	monto = validarEntero();
            	if(validarMonto(monto)) {
            		deposito(cliente,-monto);
            	}else {
            		System.out.println("Ingrese un monto válido");
            	}
                break;
            case 3:
            	System.out.println("Su saldo es: " + cliente.getFondos());
                break;
            case 4:
            	System.out.println("Ingrese el monto del deposito:");
            	monto = validarEntero(); 
            	System.out.println("Ingrese el id al que desea transferir:");
            	id = in.readLine();  
            	transferencia(cliente, monto, id, listaClientes);
                break;
            case 0:                			
                break;
            default:
                System.out.println("X Opción inválida X");
                break;
            }
        } while (opcion != 0);
    }
    
/**
 * Realiza un depósito en la cuenta del cliente.
 * 
 * @param cliente el cliente al que se le realizará el depósito
 * @param monto el monto a depositar en la cuenta del cliente
 */
    public static void deposito(Cliente cliente, int monto) {
    	monto += cliente.getFondos();
		cliente.setFondos(monto);
    }
    
/**
 * Realiza una transferencia de dinero desde el cliente actual hacia otro cliente.
 * 
 * @param cActual el cliente actual desde donde se realiza la transferencia
 * @param monto el monto de dinero a transferir
 * @param id el identificador del cliente al que se realiza la transferencia
 * @param listaClientes la lista de clientes disponibles
 * @throws IOException si ocurre un error de entrada/salida durante la transferencia
 */
    public static void transferencia(Cliente cActual, int monto, String id, ArrayList<Cliente> listaClientes) throws IOException {
    	Cliente cTransfer = new Cliente();
    	cTransfer = validaUsuario(id, listaClientes);    	
    	if(cTransfer.getId() != null && validarMonto(monto,cActual)) {
    		deposito(cTransfer,monto);
        	deposito(cActual,-monto);
    		System.out.println("Transferencia exitosa");
    	}else {
    		System.out.println("Datos inválidos");
    	}
    }
        
/**
 * Valida si un monto es mayor que válido.
 * 
 * @param monto el monto a validar
 * @return true si el monto es mayor que cero, false de lo contrario
 */
    public static boolean validarMonto(int monto) {
    	boolean aux = false;
    	if(monto>0) aux= true;
    	return aux;
    }
    
/**
 * Valida si el monto especificado es válido para el cliente dado.
 * 
 * @param monto el monto a validar
 * @param cliente el cliente para el cual se valida el monto
 * @return true si el monto es válido, false de lo contrario
 */
    public static boolean validarMonto(int monto, Cliente cliente) {
    	boolean aux = false;
    	if(monto>0&&monto<=cliente.getFondos()) aux= true;
    	return aux;
    }
    
/**
 * Valida y devuelve un entero ingresado por el usuario.
 * Si el usuario ingresa un valor no válido, se devuelve -1.
 * @return el entero validado ingresado por el usuario, o -1 si no es válido.
 */
    public static int validarEntero() {
    	int entero = 0;
    	try {
    		entero = Integer.parseInt(in.readLine());
        }catch(Exception e ){
        	entero = -1;
        }		
    	return entero;
    }
}

/**
 * Esta clase representa a un cliente en el sistema.
 */
class Cliente {
	private String id, nombre, pass;
	private boolean activo;
	private int fondos;

	/**
	 * Constructor por defecto de la clase Cliente.
	 */
	public Cliente() {
	}

	/**
	 * Constructor de la clase Cliente que recibe los datos del cliente.
	 * @param id el ID del cliente
	 * @param nombre el nombre del cliente
	 * @param pass la contraseña del cliente
	 */
	public Cliente(String id, String nombre, String pass) {
		this.id = id;
		this.nombre = nombre;
		this.pass = pass;
		this.activo = true;
		this.fondos = 2000;
	}

	/**
	 * Obtiene el ID del cliente.
	 * @return el ID del cliente
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Obtiene el nombre del cliente.
	 * @return el nombre del cliente
	 */
	public String getNombre() {
		return this.nombre;
	}

	/**
	 * Obtiene la contraseña del cliente.
	 * @return la contraseña del cliente
	 */
	public String getPass() {
		return this.pass;
	}

	/**
	 * Verifica si el cliente está activo.
	 * @return true si el cliente está activo, false de lo contrario
	 */
	public boolean getActivo() {
		return this.activo;
	}

	/**
	 * Obtiene los fondos del cliente.
	 * @return los fondos del cliente
	 */
	public int getFondos() {
		return this.fondos;
	}

	/**
	 * Establece el estado de activo del cliente.
	 * @param activo el estado de activo del cliente
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	/**
	 * Establece los fondos del cliente.
	 * @param fondos los fondos del cliente
	 */
	public void setFondos(int fondos) {
		this.fondos = fondos;
	}    
}