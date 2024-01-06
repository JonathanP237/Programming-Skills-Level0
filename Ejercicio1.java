import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Clase principal que contiene el programa principal del ejercicio 1.
 * El programa simula un sistema de banca en línea donde los usuarios pueden realizar depósitos, retiros, consultas y transferencias.
 * Los usuarios deben ingresar un usuario válido y una contraseña para acceder al sistema.
 * Si se ingresan datos inválidos o se exceden los intentos de contraseña, la cuenta se bloquea.
 * Los usuarios pueden realizar múltiples transacciones hasta que decidan cerrar sesión.
 */
public class Ejercicio1 {
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	public static void main(String[] args) throws IOException {
        ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
        String id;
        boolean aux;

        listaClientes.add(new Cliente("Pablo",  "Pablo1010"));
        listaClientes.add(new Cliente("Oscar", "Oscar1011"));
        listaClientes.add(new Cliente("Laura", "Laura1012"));
        listaClientes.add(new Cliente("Carla", "Carla1013"));
        listaClientes.add(new Cliente("Sonia", "Sonia1014"));

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
    }

    /**
        * función que valida a un cliente.
        *
        * @param id El identificador del cliente.
        * @param listaClientes La lista de clientes.
        * @return El cliente válido.
        * @throws IOException Si ocurre un error de entrada/salida.
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
				System.out.println("Pass: " + cliente.getPass());
                System.out.println("Ingrese Contraseña:");                
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
     * Bloquea la cuenta de un cliente si se han realizado 3 intentos fallidos de acceso.
     * @param cliente el cliente cuya cuenta se va a bloquear
     * @param intentos el número de intentos fallidos
     */
    public static void bloqueoCuenta(Cliente cliente, int intentos) {
        if (intentos == 3 || cliente.getActivo() == true) {
            cliente.setActivo(false);
            System.out.println("Su cuenta ha sido bloqueda.");
        }
    }

    /**
     * Muestra el menú principal del sistema.
     * 
     * @param cliente el objeto Cliente actual
     * @param listaClientes la lista de clientes disponibles
     */
    public static void menu(Cliente cliente,ArrayList<Cliente> listaClientes) throws NumberFormatException, IOException {
        int opcion = 0;
        int monto = 0;
        String id = "";
        do {
            System.out.println("\nMenú Principal\n1- Deposito\n2- Retiro");
            System.out.println("3- Consulta\n4- Transferencia\n0-Cerrar Sesión");
            opcion = validarEntero();
            switch (opcion) {
            case 1:
            	System.out.println("\nIngrese el monto del deposito");
            	monto = validarEntero();
            	if(validarMonto(monto)) {
            		deposito(cliente,monto);
					System.out.println("\nDeposito exitoso");
            	}else {
            		System.out.println("\nIngrese un monto válido");
            	}
                break;
            case 2:
            	System.out.println("\nIngrese el monto del retiro");
            	monto = validarEntero();
            	if(validarMonto(monto,cliente)) {
            		deposito(cliente,-monto);
					System.out.println("\nRetiro exitoso");
            	}else {
            		System.out.println("\nIngrese un monto válido");
            	}
                break;
            case 3:
            	System.out.println("\nSu saldo es: " + cliente.getFondos());
                break;
            case 4:
            	System.out.println("Ingrese el monto del deposito:");
            	monto = validarEntero(); 
            	System.out.println("Ingrese el id al que desea transferir:");
            	id = in.readLine();  
            	transferencia(cliente, monto, id, listaClientes);
                break;
            case 0:   
				System.out.println("Gracias por utilizar nuestro servicios");	             			
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
     * Realiza una transferencia de dinero entre dos clientes.
     * 
     * @param cActual el cliente que realiza la transferencia
     * @param monto el monto de dinero a transferir
     * @param id el identificador del cliente receptor
     * @param listaClientes la lista de clientes disponibles
     */
    public static void transferencia(Cliente cActual, int monto, String id, ArrayList<Cliente> listaClientes) throws IOException {
    	Cliente cTransfer = new Cliente();
    	cTransfer = validaUsuario(id, listaClientes);    	
    	if(cTransfer.getId() != null && validarMonto(monto,cActual)) {
    		deposito(cTransfer,monto);
        	deposito(cActual,-monto);
    		System.out.println("\nTransferencia exitosa");
    	}else {
    		System.out.println("\nDatos inválidos");
    	}
    }
	
    /**
     * Valida si un monto es mayor que cero.
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
     * Valida si el monto ingresado es válido para realizar una transacción por parte del cliente.
     * 
     * @param monto el monto a validar
     * @param cliente el cliente que realiza la transacción
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
     * @return el entero validado ingresado por el usuario.
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
 * La clase Cliente representa a un cliente en un sistema.
 * Cada cliente tiene un identificador, una contraseña, un estado de activo y una cantidad de fondos.
 */
class Cliente {
    private String id, pass;
    private boolean activo;
    private int fondos;
    
    /**
     * Crea un nuevo objeto Cliente sin inicializar sus atributos.
     */
    public Cliente() {
    }
    
    /**
     * Crea un nuevo objeto Cliente con el identificador y contraseña especificados.
     * El cliente se inicializa como activo y con una cantidad de fondos de 2000.
     * 
     * @param id el identificador del cliente
     * @param pass la contraseña del cliente
     */
    public Cliente(String id, String pass) {
        this.id = id;
        this.pass = pass;
        this.activo = true;
        this.fondos = 2000;
    }
    
    /**
     * Devuelve el identificador del cliente.
     * 
     * @return el identificador del cliente
     */
    public String getId() {
        return this.id;
    }
    
    /**
     * Devuelve la contraseña del cliente.
     * 
     * @return la contraseña del cliente
     */
    public String getPass() {
        return this.pass;
    }
    
    /**
     * Devuelve el estado de activo del cliente.
     * 
     * @return true si el cliente está activo, false de lo contrario
     */
    public boolean getActivo() {
        return this.activo;
    }
    
    /**
     * Devuelve la cantidad de fondos del cliente.
     * 
     * @return la cantidad de fondos del cliente
     */
    public int getFondos() {
        return this.fondos;
    }
    
    /**
     * Establece el estado de activo del cliente.
     * 
     * @param activo el nuevo estado de activo del cliente
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    /**
     * Establece la cantidad de fondos del cliente.
     * 
     * @param fondos la nueva cantidad de fondos del cliente
     */
    public void setFondos(int fondos) {
        this.fondos = fondos;
    }   
}