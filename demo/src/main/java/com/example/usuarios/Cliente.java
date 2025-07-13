package com.example.usuarios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import com.example.Pedido;
import com.example.Producto;
import com.example.SistemaDeVentas;
import com.example.archivos.ManejoArchivos;
import com.example.enums.CategoriaProducto;
import com.example.enums.EstadoPedido;

/**
 * Clase Cliente
 * 
 * @author Menendez Anai
 */
public class Cliente extends Usuario {
    
    private String celular;
    private String direccion;
    

    // Constructor
    /**
    * Crea una instancia de Cliente con todos sus datos personales y de contacto.
    * @param codigo Identificador único del cliente
    * @param cedula Número de cédula del cliente
    * @param nombres Nombres completos
    * @param apellidos Apellidos completos
    * @param usuario Nombre de usuario para login
    * @param contrasena Contraseña para autenticación
    * @param correo Correo electrónico principal
    * @param rol Rol del usuario (debe ser 'C' para cliente)
    * @param celular Número de teléfono móvil
    * @param direccion Dirección física de entrega
    */
    public Cliente(String codigo, String cedula, String nombres, String apellidos, 
                  String usuario, String contrasena, String correo, char rol,String celular, String direccion){
        super(codigo, cedula, nombres, apellidos, usuario, contrasena, correo, 'C');
        this.celular = celular;
        this.direccion = direccion;
    }

    /**
    * Permite al cliente consultar sobre su pedido.
    * @param productos   Lista completa de productos disponibles en el sistema (ArrayList<Producto>)
    * @param usuarios    Lista de todos los usuarios registrados (ArrayList<Usuario>)
    * @param pedidos     Lista mutable de pedidos que será actualizada (ArrayList<Pedido>)
    * @param sistema     Instancia del sistema para acceder a métodos auxiliares (SistemaDeVentas)
    */
    @Override 
    public void gestionarPedidos(ArrayList<Producto> productos, ArrayList<Usuario> usuarios, ArrayList<Pedido> pedidos, Scanner scanner) {
        
        // Implementación específica para cliente
        System.out.println("===== CONSULTA DE ESTADO DE PEDIDO ===== ");
        System.out.print("Ingrese el codigo del pedido: ");
        String codigoPedido = scanner.nextLine().trim().toUpperCase();

        boolean encontrado=false;
        for (Pedido p : pedidos) {

            if(p.getCodigoPedido().equals(codigoPedido)){ 
                encontrado = true;
                System.out.println("");
                System.out.println("Fecha del pedido: "+p.getFecha());
                for(Producto proc : productos){

                    if(proc.getCodigo().equals(p.getCodigoProducto())){ 
                        System.out.println("Producto comprado: "+proc.getNombre()+"(Codigo: "+p.getCodigoProducto()+")");
                        System.out.println("Cantidad: "+p.getCantidad());
                        System.out.println("Valor pagado: $"+p.getValorPagado());
                        System.out.println("Estado actual: "+p.getEstadoPedido());

                        for(Usuario ur : usuarios){

                            if(p.getCodigoRepartidor().equals(ur.getCodigo())){ 
                                System.out.println("Repartidor: "+ur.getNombres()+" "+ur.getApellidos());
                                System.out.println("---------------------------------------------------------------------");

                                if(p.getEstadoPedido()==EstadoPedido.EN_PREPARACION){
                                    System.out.println("Su pedido esta siendo preparado para su envio.");
                                }else if(p.getEstadoPedido()==EstadoPedido.EN_RUTA){
                                    System.out.println("Su pedido esta en camino para ser entregado.");
                                }else{
                                    System.out.println("Su pedido ha sido entregado.");
                                }
                            }
                        }
                    }
                }
                break;
                
            }
        }
        // si no se encontró nada
        if (!encontrado) {
            System.out.println("No se encontró un pedido con ese código.");
        }
        
    }
    /**
    * Procesa la compra de un producto y crea un nuevo pedido.
    * @param productos Productos disponibles (ArrayList<Producto>)
    * @param usuarios Usuarios registrados (ArrayList<Usuario>)
    * @param pedidos Lista donde se añadirá el pedido (ArrayList<Pedido>)
    * @param sistema Sistema principal (SistemaDeVentas)
    */
    public void comprarProducto(ArrayList<Producto> productos, ArrayList<Usuario> usuarios, ArrayList<Pedido> pedidos, SistemaDeVentas sistema, Scanner scanner){
        System.out.println("===== Realizar compra =====");
        System.out.println("Categorias disponibles:\n Opcion 1: "+CategoriaProducto.ROPA+"\n Opcion 2: "+CategoriaProducto.TECNOLOGIA+"\n Opcion 3: "+CategoriaProducto.DEPORTES+
                        "\n Opcion 4: "+CategoriaProducto.HOGAR);
        
        System.out.print("Ingrese la categoria a consultar: ");
        int opcion=scanner.nextInt();
        scanner.nextLine();
        
        switch (opcion) {
            case 1:
                for (Producto p : productos) {
                    if(p.getCategoria()==CategoriaProducto.ROPA){
                        System.out.println("Nombre del producto: "+p.getNombre()+" Codigo:"+p.getCodigo());
                    }
                }
                break;
            case 2:
                for (Producto p : productos) {
                    if(p.getCategoria()==CategoriaProducto.TECNOLOGIA){
                        System.out.println("Nombre del producto: "+p.getNombre()+" Codigo:"+p.getCodigo());
                    }
                }
                break;
            case 3:
                for (Producto p : productos) {
                    if(p.getCategoria()==CategoriaProducto.DEPORTES){
                        System.out.println("Nombre del producto: "+p.getNombre()+" Codigo:"+p.getCodigo());
                    }
                }
                break;
            case 4:
                for (Producto p : productos) {
                    if(p.getCategoria()==CategoriaProducto.HOGAR){
                        System.out.println("Nombre del producto: "+p.getNombre()+" Codigo:"+p.getCodigo());
                    }
                }
                break;
            default:
                System.out.println("La opcion ingresada no es valida.");
                break;
            
        }

        if(opcion<5 && opcion>0){
            // El cliente debe elegir el producto a comprar (se lo realizara mediante el codigo del producto)
            System.out.println("----- Elija el producto a comprar (mediante el codigo del producto) -----");
        
            System.out.print("Ingrese el codigo del producto: ");
            String codigoIngresado=scanner.nextLine();
            System.out.print("Ingrese la cantidad a comprar: ");
            int c=scanner.nextInt();
            scanner.nextLine();
            double total=0.0;;
            boolean encontrado = false;
            for (Producto p : productos) {
                
                if(p.getCodigo().equals(codigoIngresado)){ //.equals()
                    encontrado = true;
                    if(p.getStock()>c && c!=0){
                    
                        total=total+c*p.getPrecio();
                        if(total!=0.0){
                            System.out.println("El total a pagar es: "+total+" $");
                            System.out.print("Ingrese su numero de tarjeta: ");
                            long numTarj=scanner.nextLong();
                            scanner.nextLine();
                            if(numTarj<=0){
                                System.out.println("Error con la tarjeta...");
                                break;
                            }
            
            
                            System.out.println("- Se completo el pago -");
                            p.setStock(p.getStock()-c);
                            // Se debe asignar un repartidor aleatorio
                            
                        
                            ArrayList<Repartidor> repartidores = new ArrayList<>();
                            for (Usuario u : usuarios) {
                                if (u instanceof Repartidor) {
                                repartidores.add((Repartidor) u);
                                }
                            }
                            if (repartidores.size() > 0) {
                                Random rand = new Random();
                                int indice = rand.nextInt(repartidores.size()); // entre 0 y repartidores.size()-1
                                Repartidor repartidor = repartidores.get(indice);

                                
                                String codigoRepartidor = repartidor.getCodigo();
                                //System.out.println(repartidor.nombres);
                                
                                Pedido nuevoPedido=new Pedido(LocalDate.now(), codigoIngresado, c, total, null, codigoRepartidor, this.getCodigo());
                                pedidos.add(nuevoPedido);

                        
                                // Registrar los datos al archivo pedidos.txt
                                
                                ManejoArchivos.EscribirArchivo("Pedidos.txt", nuevoPedido.toString());
                                
                        
                                System.out.println("- Exito -");

                                // Notificacion por correo con los datos del pedido (Eso es tarea del sistema)
                                sistema.notificar(this, nuevoPedido); // this -> para cliente actual
                                // Notificar al repartidor por correo
                                sistema.notificar(repartidor, nuevoPedido, this); // uso de repartidor
                            }else{
                                System.out.println("No hay repartidores disponibles.");
                            }
                        }
                    }else{
                    System.out.println("No hay suficiente stock para el producto seleccionado.");
                    }
                    break; // para no seguir buscando
                }
            }
            // Si termino el for sin encontrar nada
            if (!encontrado) {
                System.out.println("Producto no encontrado.");
            }
        }  
       
    }

    /**
    * @return String Devuelve una representación en formato String del objeto actual.
    */
    @Override
    public String toString() {
        return codigo+"|"+cedula+"|"+nombres+"|"+apellidos+"|"+celular+"|"+direccion;
    }

    // Metodos Getters y Setters
    public void setCelular(String celular){
        this.celular = celular;
    }
    public void setDireccion(String direccion){
        this.direccion = direccion;
    }
    
    public  String getCelular(){
        return this.celular;
    }
    public String getDireccion(){
        return this.direccion;
    }
}
