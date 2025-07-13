package com.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import com.example.archivos.ManejoArchivos;
import com.example.enums.CategoriaProducto;
import com.example.enums.EstadoPedido;
import com.example.usuarios.Cliente;
import com.example.usuarios.Repartidor;
import com.example.usuarios.Usuario;

/**
 * Clase SistemaDeVentas
 * 
 * @author Menendez Anai
 */
public class SistemaDeVentas {
    
    ArrayList<Usuario> usuarios = cargarUsuarios();
    ArrayList<Producto> productos = cargarProductos();
    ArrayList<Pedido> pedidos = cargarPedidos();

    // ================= MÉTODOS PARA CARGAR DATOS DESDE ARCHIVOS ================= //
    // USUARIOS (CLIENTES & REPARTIDORES)
    /**
    * Carga y construye la lista de usuarios desde archivos de texto.
    * Combina datos de 'Usuarios.txt', 'Clientes.txt' y 'Repartidores.txt'.
    * @return ArrayList<Usuario> con todos los usuarios registrados (Clientes y Repartidores)
    */
    public static ArrayList<Usuario> cargarUsuarios() { 
        ArrayList<Usuario> usuarios = new ArrayList<>();
        ArrayList<String> lineasUsuarios = ManejoArchivos.LeeFichero("Usuarios.txt");
        ArrayList<String> lineasClientes = ManejoArchivos.LeeFichero("Clientes.txt");
        ArrayList<String> lineasRepartidores = ManejoArchivos.LeeFichero("Repartidores.txt");

        boolean primeraLinea=true;
        for (String linea : lineasUsuarios) {
            
            if (primeraLinea) { 
                primeraLinea = false;
                continue; // Saltar encabezado
            }
            String[] p = linea.split("\\|");

            if (p.length >= 8) {
                String codigo = p[0];
                String cedula = p[1];
                String nombres = p[2];
                String apellidos = p[3];
                String usuario = p[4];
                String contrasena = p[5];
                String correo = p[6].trim();
                char rol = p[7].charAt(0);

                if (rol == 'C') {
                    // Busca info extra en clientes.txt
                    for (String lineaCliente : lineasClientes) {
                        String[] c = lineaCliente.split("\\|");
                        if (c[0].equals(codigo)) {
                            String celular = c[4];
                            String direccion = c[5];
                            usuarios.add(new Cliente(codigo, cedula, nombres, apellidos, usuario, contrasena, correo, rol, celular, direccion));
                            break;
                        }
                    }
                } else if (rol == 'R') {
                    // Busca info extra en repartidores.txt
                    for (String lineaRepartidor : lineasRepartidores) {
                        String[] r = lineaRepartidor.split("\\|");
                        if (r[0].equals(codigo)) {
                            String empresa = r[4];
                            usuarios.add(new Repartidor(codigo, cedula, nombres, apellidos, usuario, contrasena, correo, rol, empresa));
                            break;
                        }
                    }
                }
            }
        }
        return usuarios;
    }
    // PRODUCTOS
    /**
    * Carga el catálogo de productos desde 'Productos.txt'.
    * @return ArrayList<Producto> con todos los productos disponibles
    */
    public static ArrayList<Producto> cargarProductos() {
        ArrayList<Producto> productos = new ArrayList<>();
        ArrayList<String> lineas = ManejoArchivos.LeeFichero("Productos.txt");

        boolean primeraLinea = true;
        for (String linea : lineas) {
            if (primeraLinea) {
                primeraLinea = false; // Saltamos la línea de encabezado
                continue;
            }
           String[] p = linea.split("\\|");
            if (p.length >= 5) {
                String codigo = p[0];
                // .toUppercase()-> para convertir a mayuscula
                String categoriaStr = p[1].toUpperCase().replace("Í", "I"); // para evitar errores con acentos
                categoriaStr = categoriaStr.replace(" ", ""); // eliminar espacios si es que hay

                CategoriaProducto categoria = CategoriaProducto.valueOf(categoriaStr); // convertir a enum

                String nombre = p[2];
                double precio = Double.parseDouble(p[3]);
                int stock = Integer.parseInt(p[4]);

                productos.add(new Producto(codigo, nombre, categoria, precio, stock));
            }
        }

        return productos;
    }
    // PEDIDOS
    /**
    * Carga el historial de pedidos desde 'Pedidos.txt'.
    * @return ArrayList<Pedido> con todos los pedidos registrados
    */
    public static ArrayList<Pedido> cargarPedidos() {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        ArrayList<String> lineas = ManejoArchivos.LeeFichero("Pedidos.txt");

        boolean primeraLinea=true;
        for (String linea : lineas) {
            if (primeraLinea) { // No se esta haciendo uso
                primeraLinea = false;
                continue; // Saltar encabezado
            }
            String[] p = linea.split("\\|");

            if (p.length >= 7) {
                String codigoPedido = p[0].trim();
                LocalDate fecha = LocalDate.parse(p[1]);
                String codigoProducto = p[2];
                int cantidad = Integer.parseInt(p[3]);
                double valorPagado = Double.parseDouble(p[4]);
                
                // Normaliza estado: "EN PREPARACIÓN" → "EN_PREPARACION"
                String estadoTexto = p[5].toUpperCase().replace(" ", "_").replace("Ó", "O");
                EstadoPedido estado = EstadoPedido.valueOf(estadoTexto);
                
                String codigoRepartidor = p[6];
                pedidos.add(new Pedido(codigoPedido, fecha, codigoProducto, cantidad, valorPagado, estado, codigoRepartidor));
    
            }
        }
        return pedidos;
    }

    // Sobrecarga -> notificar

    /**
    * Notifica al cliente cuando se crea un nuevo pedido.
    * @param cliente El objeto Cliente que recibirá la notificación.
    * @param pedido  El objeto Pedido con los detalles de la compra.
    */
    public void notificar(Cliente cliente, Pedido pedido){
        String asunto = "Pedido realizado";
        String cuerpo = "El cliente " + cliente.getNombres() + " " + cliente.getApellidos() +
                " ha realizado un pedido con código " + pedido.getCodigoPedido() +
                " el día " + pedido.getFecha() + ".\n";

        Producto producto = Producto.buscarProducto(pedido.getCodigoProducto(), productos);
        if (producto != null) {
            cuerpo += "Producto: " + producto.getNombre() + "\n";
        } else {
            cuerpo += "Producto: Desconocido\n";
        }

        cuerpo += "Cantidad: " + pedido.getCantidad() + "\n";
        cuerpo += "Valor pagado: $" + pedido.getValorPagado() + "\n";
        cuerpo += "Estado inicial: " + pedido.getEstadoPedido() + "\n";
        cuerpo += "Gracias por su compra. Recibirá actualizaciones por este medio.";

        // Imprimir
        System.out.println("[NOTIFICACION POR CORREO]");
        System.out.println("Para: " + cliente.getCorreoElectronico());
        System.out.println("Asunto: " + asunto);
        System.out.println(cuerpo);

        // Enviar correo real
        EnviarCorreo.enviarCorreo(cliente.getCorreoElectronico(), asunto, cuerpo);
    }

    /**
    * Notifica al repartidor cuando se le asigna un pedido.
    * @param repartidor El Repartidor asignado al pedido.
    * @param pedido     El Pedido que se le asignó.
    * @param cliente    El Cliente asociado al pedido.
    */
    public void notificar(Repartidor repartidor, Pedido pedido, Cliente cliente){
        String asunto = "Nuevo pedido asignado";
        String cuerpo = "Estimado/a " + repartidor.getNombres() + " " + repartidor.getApellidos() + ",\n\n" +
                "Se le ha asignado un nuevo pedido con los siguientes detalles:\n\n" +
                "Código del pedido: " + pedido.getCodigoPedido() + "\n" +
                "Fecha del pedido: " + pedido.getFecha() + "\n" +
                "Cliente: " + cliente.getNombres() + " " + cliente.getApellidos() + "\n" +
                "Estado actual: " + pedido.getEstadoPedido() + "\n\n" +
                "Por favor, prepare la logística necesaria para la entrega.\n" +
                "Gracias por su trabajo.";

        System.out.println("[NOTIFICACION POR CORREO]");
        System.out.println("Para: " + repartidor.getCorreoElectronico());
        System.out.println("Asunto: " + asunto);
        System.out.println(cuerpo);

        EnviarCorreo.enviarCorreo(repartidor.getCorreoElectronico(), asunto, cuerpo);
    }

    /**
    * Notifica a un cliente sobre un cambio de estado en su pedido. 
    * @param cliente      El cliente que recibirá la notificación.
    * @param pedido       El pedido cuyo estado ha cambiado.
    * @param nuevoEstado  El nuevo estado del pedido (ej: EN_PREPARACION, EN_RUTA, ENTREGADO).
    */
    public void notificar(Cliente cliente, Pedido pedido, EstadoPedido nuevoEstado){
        String asunto = "Actualización del estado de su pedido";
        String cuerpo = "Estimado/a " + cliente.getNombres() + " " + cliente.getApellidos() + ",\n\n" +
                "Le informamos que el estado de su pedido con código " + pedido.getCodigoPedido() +
                " ha cambiado a: " + nuevoEstado + ".\n\n" +
                "Fecha del pedido: " + pedido.getFecha() + "\n";

        Producto producto = Producto.buscarProducto(pedido.getCodigoProducto(), productos);
        if (producto != null) {
            cuerpo += "Producto: " + producto.getNombre() + "\n";
        } else {
            cuerpo += "Producto: Desconocido\n";
        }

        cuerpo += "Repartidor asignado (código): " + pedido.getCodigoRepartidor() + "\n\n" +
                "Gracias por confiar en nosotros.";

        System.out.println("[NOTIFICACION POR CORREO]");
        System.out.println("Para: " + cliente.getCorreoElectronico());
        System.out.println("Asunto: " + asunto);
        System.out.println(cuerpo);

        EnviarCorreo.enviarCorreo(cliente.getCorreoElectronico(), asunto, cuerpo);
    }

    /**
    * Gestiona el proceso de autenticación de usuarios en el sistema.
    * @param sistema La instancia del sistema de ventas que contiene los datos de usuarios, productos y pedidos
    */
    public void inicioDeSesion(SistemaDeVentas sistema, Scanner scanner){
        
        System.out.println("===== INICIO DE SESION =====");
        System.out.print("Usuario: ");
        String usuarioIngresado = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasenaIngresada = scanner.nextLine();

        // Buscar usuario en la lista
        Usuario usuarioLogueado = null;
        for (Usuario u : usuarios) {
            if (u.getUsuario().equals(usuarioIngresado) && u.getContrasena().equals(contrasenaIngresada)) {
                usuarioLogueado = u;
                break;
            }
        }
        if (usuarioLogueado == null) {
            System.out.println("Usuario o contraseña incorrectos.");
            return;
        }
        if (usuarioLogueado instanceof Cliente) {
            Cliente cliente = (Cliente) usuarioLogueado;
            System.out.println("Usuario autenticado correctamente.");
            String rol="CLIENTE";
            
            System.out.println("Rol detectado: "+ rol);
            System.out.println("Bienvenida(o), " + cliente.getNombres() + " " + cliente.getApellidos());
            System.out.println("Celular registrado: " + cliente.getCelular());
            System.out.print("¿Este número es correcto? (S/N): ");
            String respuesta = scanner.nextLine();
            if (!respuesta.equalsIgnoreCase("S")) {
                System.out.println("Verificación fallida. Saliendo...");
                return;
            }
            System.out.println("Identidad confirmada.");
            this.mostrarMenuCliente(cliente, productos, pedidos, usuarios, scanner, sistema);

        } else if (usuarioLogueado instanceof Repartidor) {
            Repartidor repartidor = (Repartidor) usuarioLogueado;
            System.out.println("Usuario autenticado correctamente.");
            String rol="REPARTIDOR";

            System.out.println("Rol detectado: "+ rol);
            System.out.println("Bienvenida(o), " + repartidor.getNombres() + " " + repartidor.getApellidos());
            System.out.println("Empresa asignada: " + repartidor.getEmpresa());
            System.out.print("¿Esta empresa es correcta? (S/N): ");
            String respuesta = scanner.nextLine();
            if (!respuesta.equalsIgnoreCase("S")) {
                System.out.println("Verificación fallida.\nPor motivos de seguridad se cerrara la sesion.\n\nSaliendo...");
                return;
            }
            mostrarMenuRepartidor(repartidor, pedidos, productos, usuarios, scanner, sistema);
        }
        
    }

    // ================= MENU REPARTIDOR ================= //
    /**
    * Muestra el menú de opciones disponibles para un repartidor y gestiona sus interacciones.
    * @param repartidor El repartidor que inició sesión
    * @param pedidos Lista de todos los pedidos del sistema
    * @param productos Lista de todos los productos disponibles
    * @param usuarios Lista de todos los usuarios registrados
    * @param scanner Objeto Scanner para leer la entrada del usuario
    * @param sistema Instancia principal del sistema de ventas
    */
    public static void mostrarMenuRepartidor(Repartidor repartidor, ArrayList<Pedido> pedidos,ArrayList<Producto> productos, ArrayList<Usuario> usuarios, Scanner scanner, SistemaDeVentas sistema) {
        boolean salir = false;
        
        while (!salir) {
            System.out.println("===== MENÚ CLIENTE =====");
            System.out.println("1. Gestionar pedido");
            System.out.println("2. Consultar pedidos asignados");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    repartidor.gestionarPedidos(productos, usuarios, pedidos, scanner);
                    break;
                case 2:
                    repartidor.consultarPedidosAsignados(pedidos);
                    break;
                case 3:
                    salir = true;
                    System.out.println("Gracias por usar el sistema. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
        
    }
    

    // ================= MENU CLIENTE ================= //
    /**
    * Muestra el menú de opciones y gestiona las operaciones para clientes.
    * @param cliente El cliente actual que inició sesión
    * @param productos Lista de productos disponibles en el sistema
    * @param pedidos Lista de todos los pedidos registrados
    * @param usuarios Lista completa de usuarios del sistema
    * @param scanner Objeto para leer la entrada del usuario
    * @param sistema Instancia principal del sistema de ventas
    */
    private void mostrarMenuCliente(Cliente cliente, ArrayList<Producto> productos,
                                           ArrayList<Pedido> pedidos, ArrayList<Usuario> usuarios, Scanner scanner, SistemaDeVentas sistema) {
        boolean salir = false;
        
        while (!salir) {
            System.out.println("===== MENÚ CLIENTE =====");
            System.out.println("1. Comprar producto");
            System.out.println("2. Gestionar pedido");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = Integer.parseInt(scanner.nextLine());
            

            switch (opcion) {
                case 1:
                    cliente.comprarProducto(productos, usuarios, pedidos, this, scanner);
                    break;
                case 2:
                    cliente.gestionarPedidos(productos, usuarios, pedidos, scanner);
                    break;
                case 3:
                    salir = true;
                    System.out.println("Gracias por usar el sistema. ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }



    // // ----------------- = = METODO PRINCIPAL = = ------------------
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
    
        SistemaDeVentas s=new SistemaDeVentas();
        s.inicioDeSesion(s,scanner);

        
        scanner.close();
    }
}
