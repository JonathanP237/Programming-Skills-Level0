import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Ejercicio3 {
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    /**
     * El método principal del programa.
     * 
     * @param args los argumentos de la línea de comandos
     */
    public static void main(String[] args) throws IOException {
        ArrayList<usuario> registrados = new ArrayList<usuario>();
        usuario usActual = new usuario();
        String id;
        registrados.add(new usuario("1", "Pablo1010", "Pablo", "Perez", "londres", "informatica"));
        registrados.add(new usuario("2", "Oscar1011", "Oscar", "Perez", "manchester", "informatica"));
        registrados.add(new usuario("3", "Laura1012", "Laura", "Perez", "liverpool", "informatica"));
        registrados.add(new usuario("4", "Carla1013", "Carla", "Perez", "manchester", "informatica"));
        registrados.add(new usuario("5", "Sonia1014"));
        registrados.add(new usuario("6", "Luisa1015"));
        registrados.add(new usuario("7", "Pedro1016"));
        ArrayList<usuario> informatica = new ArrayList<usuario>();
        informatica.add(registrados.get(0));        
        informatica.add(registrados.get(1));
        informatica.add(registrados.get(2));
        informatica.add(registrados.get(3));
        ArrayList<usuario> medicina = new ArrayList<usuario>();
        ArrayList<usuario> marketing = new ArrayList<usuario>();
        ArrayList<usuario> artes = new ArrayList<usuario>();

        do {
            System.out.println("\nIngrese su id o \"salir\":");
            id = in.readLine();
            usActual = login(id, registrados);
            if (usActual.getPrograma().isEmpty() && usActual.getActivo() == true) {
                System.out.println("\nIngrese su nombre:");
                usActual.setNombre(in.readLine());
                System.out.println("\nIngrese su apellido:");
                usActual.setApellido(in.readLine());                
                validarPrograma(usActual, informatica, medicina, marketing, artes);                
                validarCampus(usActual, informatica, medicina, marketing, artes);
                System.out.println("Sesión cerrada");
            } else if (usActual.getActivo() == true) {
                System.out.println("\nBienvenido " + usActual.getNombre() + " " + usActual.getApellido());
                System.out.println("Programa: " + usActual.getPrograma());
                System.out.println("Campus: " + usActual.getCampus());
                System.out.println("Sesión cerrada");
            }
        } while (!id.toUpperCase().equals("SALIR"));
    }

    /**
     * Valida el programa seleccionado por el usuario y lo asigna al objeto usuario actual.
     * Además, agrega el usuario actual a la lista correspondiente al programa seleccionado.
     * Si no hay cupos disponibles para el programa seleccionado, muestra un mensaje de error.
     * 
     * @param usActual el usuario actual
     * @param informatica la lista de usuarios del programa de informática
     * @param medicina la lista de usuarios del programa de medicina
     * @param marketing la lista de usuarios del programa de marketing
     * @param artes la lista de usuarios del programa de artes
     */
    private static void validarPrograma(usuario usActual, ArrayList<usuario> informatica, ArrayList<usuario> medicina,
            ArrayList<usuario> marketing, ArrayList<usuario> artes) throws IOException {
        String programa;
        boolean aux = true;
        do{
            System.out.println("\nIngrese su programa:");
        System.out.println("informatica, medicina, marketing, artes");
        programa = in.readLine();
        programa.toLowerCase();
        switch (programa) {
            case "informatica":
                if (informatica.size() == 5) {
                    System.out.println("No hay cupos disponibles para el programa de informatica.");
                } else {
                    usActual.setPrograma(programa);
                    informatica.add(usActual);
                    System.out.println("Bienvenido al programa de informatica.");
                    aux = false;
                }
                break;
            case "medicina":
                if (medicina.size() == 5) {
                    System.out.println("No hay cupos disponibles para el programa de medicina.");
                } else {
                    usActual.setPrograma(programa);
                    medicina.add(usActual);
                    System.out.println("Bienvenido al programa de medicina.");
                    aux = false;
                }
                break;
            case "marketing":
                if (marketing.size() == 5) {
                    System.out.println("No hay cupos disponibles para el programa de marketing.");
                } else {
                    usActual.setPrograma(programa);
                    marketing.add(usActual);
                    System.out.println("Bienvenido al programa de marketing.");
                    aux = false;
                }
                break;
            case "artes":
                if (artes.size() == 5) {
                    System.out.println("No hay cupos disponibles para el programa de artes.");
                } else {
                    usActual.setPrograma(programa);
                    artes.add(usActual);
                    System.out.println("Bienvenido al programa de artes.");
                    aux = false;
                }
                break;
            default:
                System.out.println("Programa no encontrado.");
                break;
        }
        }while(aux==true);
    }

    /**
     * Valida el campus al que pertenece un usuario y lo asigna si hay cupos disponibles.
     * 
     * @param usActual el usuario actual
     * @param informatica la lista de usuarios del programa de informática
     * @param medicina la lista de usuarios del programa de medicina
     * @param marketing la lista de usuarios del programa de marketing
     * @param artes la lista de usuarios del programa de artes
     */
    private static void validarCampus(usuario usActual, ArrayList<usuario> informatica, ArrayList<usuario> medicina, ArrayList<usuario> marketing,
            ArrayList<usuario> artes) throws IOException {
        ArrayList<ArrayList<usuario>> programas = new ArrayList<ArrayList<usuario>>();
        programas.add(informatica);
        programas.add(medicina);
        programas.add(marketing);
        programas.add(artes);           
        boolean aux = true;
        String campus;      
        int contador, aux2 = 0;   
        if(usActual.getPrograma().equals("informatica")){
            aux2 = 0;
        }else if(usActual.getPrograma().equals("medicina")){
            aux2 = 1;
        }else if(usActual.getPrograma().equals("marketing")){
            aux2 = 2;
        }else if(usActual.getPrograma().equals("artes")){
            aux2 = 3;
        }
        do{
            System.out.println("\nIngrese su campus:");
            System.out.println("Londres, Manchester, Liverpool");
            campus = (in.readLine());
            contador = 0;       
            switch (campus.toLowerCase()) {
            case "manchester":
                for (int i = 0; i < programas.get(aux2).size(); i++) {
                    if (programas.get(aux2).get(i).getCampus().equals(campus))
                        contador++;
                }
                if (contador == 3) {
                    System.out.println("No hay cupos disponibles para el campus de Manchester.");
                } else {
                    usActual.setCampus(campus);
                    System.out.println("Bienvenido al campus de Manchester.");
                    aux = false;
                }
                break;
            case "londres":
                for (int i = 0; i < programas.get(aux2).size(); i++) {
                    if (programas.get(aux2).get(i).getCampus().equals(campus))
                        contador++;
                }
                if (contador == 1) {
                    System.out.println("No hay cupos disponibles para el campus de londres.");
                } else {
                    usActual.setCampus(campus);
                    System.out.println("Bienvenido al campus de londres.");
                    aux = false;
                }
                break;
            case "liverpool":
                for (int i = 0; i < programas.get(aux2).size(); i++) {
                    if (programas.get(aux2).get(i).getCampus().equals(campus))
                        contador++;
                }
                if (contador == 1) {
                    System.out.println("No hay cupos disponibles para el campus de liverpool.");
                } else {
                    usActual.setCampus(campus);
                    System.out.println("Bienvenido al campus de liverpool.");
                    aux = false;
                }
                break;
            default:
                System.out.println("Campus no encontrado.");
                break;
            }
        }while(aux==true);
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
                System.out.println("Usuario no encontrado.");
                usActual.setActivo(false);
                break;
            }
        }
        return usActual;
    }
}

/**
 * Clase que representa a un usuario.
 */
class usuario {
    private String id, pass, nombre, apellido, campus = "", programa = "";
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
     * Constructor de la clase usuario que recibe todos los atributos del usuario.
     * @param id el id del usuario
     * @param pass la contraseña del usuario
     * @param nombre el nombre del usuario
     * @param apellido el apellido del usuario
     * @param campus el campus del usuario
     * @param programa el programa del usuario
     */
    public usuario(String id, String pass, String nombre, String apellido, String campus, String programa) {
        this.id = id;
        this.pass = pass;
        this.nombre = nombre;
        this.apellido = apellido;
        this.campus = campus;
        this.programa = programa;
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
     * Método para obtener el nombre del usuario.
     * @return el nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Método para establecer el nombre del usuario.
     * @param nombre el nombre del usuario
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Método para obtener el apellido del usuario.
     * @return el apellido del usuario
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Método para establecer el apellido del usuario.
     * @param apellido el apellido del usuario
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Método para obtener el campus del usuario.
     * @return el campus del usuario
     */
    public String getCampus() {
        return campus;
    }

    /**
     * Método para establecer el campus del usuario.
     * @param campus el campus del usuario
     */
    public void setCampus(String campus) {
        this.campus = campus;
    }

    /**
     * Método para obtener el programa del usuario.
     * @return el programa del usuario
     */
    public String getPrograma() {
        return programa;
    }

    /**
     * Método para establecer el programa del usuario.
     * @param programa el programa del usuario
     */
    public void setPrograma(String programa) {
        this.programa = programa;
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
