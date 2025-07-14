package com.example.usuarios;

import java.util.ArrayList;
import java.util.Scanner;

import com.example.Pedido;
import com.example.Producto;
import com.example.SistemaDeVentas;
import com.example.enums.EstadoPedido;

/**
 * Clase Repartidor que hereda de Usuario
 * 
 * @author Juber Pezo
 * @version 1.0
 */
public class Repartidor extends Usuario {

    private String empresa;

    /**
     * Constructor de la clase Repartidor
     * 
     * @param codigo     Codigo del Repartidor
     * @param cedula     Cedula del Repartidor
     * @param nombres    Nombre del Repartidor
     * @param apellidos  Apellido del Repartidor
     * @param usuario    Usuario de acceso del Repartidor
     * @param contrasena Constraseña de acceso del Repartidor
     * @param correo     Correo del Repartidor
     * @param rol        Rol del Repartidor R
     * @param empresa    Empresa que pertenece el Repartidor
     */
    public Repartidor(String codigo, String cedula, String nombres, String apellidos,
            String usuario, String contrasena, String correo, char rol,
            String empresa) {
        super(codigo, cedula, nombres, apellidos, usuario, contrasena, correo, 'R');
        this.empresa = empresa;
    }

    /**
     * Metodo Sobreescrito gestionarPedidos
     * Permite que el Repartidor gestione pedidos con un menu de opciones obteniendo
     * informacion de todo el pedido
     * 
     * @param productos Es una lista de los productos disponibles
     * @param usuarios  Es una lista de los usuarios disponibles
     * @param pedidos   Es una lista de pedidos disponibles
     * @param sistema   Permite acceder a los metodos de la clase sistema
     */

    @Override
    public void gestionarPedidos(ArrayList<Producto> productos, ArrayList<Usuario> usuarios, ArrayList<Pedido> pedidos,
            Scanner scanner) {

        SistemaDeVentas s = new SistemaDeVentas();
        // Implementación específica para repartidor
        System.out.println("===== GESTIONAR ESTADO DE PEDIDO =====");
        System.out.print("Ingrese el codigo del pedido que desea gestionar: ");
        String codigoPedido = scanner.nextLine();
        boolean encontrado = false;
        for (Pedido p : pedidos) {
            if (p.getCodigoPedido().equals(codigoPedido)) {
                encontrado = true;
                System.out.println(("Pedido encontrado."));
                System.out.println("Fecha del pedido: " + p.getFecha());
                System.out.println("Codigo del producto: " + p.getCodigoProducto());
                System.out.println("Estado actual: " + p.getEstadoPedido());

                System.out.println("Seleccione el nuevo estado:");
                System.out.println("1. " + EstadoPedido.EN_RUTA);
                System.out.println("2. " + EstadoPedido.ENTREGADO);
                System.out.print("Opcion: ");
                int opcion = scanner.nextInt();
                scanner.nextLine();
                System.out.println("----------------------------------------------------");

                if (opcion == 1) {
                    EstadoPedido op = EstadoPedido.EN_RUTA;
                    if (p.getEstadoPedido().equals(EstadoPedido.EN_PREPARACION)) {// si son =
                        p.setEstadoPedido(op);
                        System.out.println("Estado actualizado a " + EstadoPedido.EN_RUTA);
                        System.out.println("Notificacion enviada al cliente: El pedido " + p.getCodigoPedido()
                                + " ha sido despachado y esta en camino.");
                        System.out.println("----------------------------------------------------");

                        // Notificar
                        for (Usuario u : usuarios) {
                            if (u instanceof Cliente && u.getCodigo().equals(p.getCodigoCliente())) {
                                Cliente cliente = (Cliente) u;
                                s.notificar(cliente, p, EstadoPedido.EN_RUTA);
                                break;
                            }
                        }
                    }

                } else if (opcion == 2) {
                    EstadoPedido op = EstadoPedido.ENTREGADO;
                    if (p.getEstadoPedido().equals(EstadoPedido.EN_RUTA)) {
                        p.setEstadoPedido(op);
                        System.out.println("Estado actualizado correctamente a " + EstadoPedido.ENTREGADO);
                        System.out.println(
                                "Notificacion enviada al cliente: El pedido " + p.getCodigoPedido() + " ha entregado.");
                        System.out.println("----------------------------------------------------");

                        // Notificar
                        for (Usuario u : usuarios) {
                            if (u instanceof Cliente && u.getCodigo().equals(p.getCodigoCliente())) {
                                Cliente cliente = (Cliente) u;
                                s.notificar(cliente, p, EstadoPedido.ENTREGADO);
                                break;
                            }
                        }
                    } else {
                        System.out.println("Error: No se puede cambiar directamente de " + EstadoPedido.EN_PREPARACION
                                + " a " + EstadoPedido.ENTREGADO
                                + " debe cambiarlo primero a " + EstadoPedido.EN_RUTA);
                        while (opcion == 2) {
                            System.out.println("Seleccione el nuevo estado:");
                            System.out.println("1. " + EstadoPedido.EN_RUTA);
                            System.out.println("2. " + EstadoPedido.ENTREGADO);
                            System.out.print("Opcion: ");
                            opcion = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("----------------------------------------------------");
                        }
                        System.out.println("Estado actualizado a " + EstadoPedido.ENTREGADO);
                        System.out.println("Notificacion enviada al cliente: El pedido " + p.getCodigoPedido()
                                + " ha sido entregado.");
                        System.out.println("----------------------------------------------------");

                        // Notificar
                        for (Usuario u : usuarios) {
                            if (u instanceof Cliente && u.getCodigo().equals(p.getCodigoCliente())) {
                                Cliente cliente = (Cliente) u;
                                s.notificar(cliente, p, EstadoPedido.ENTREGADO);
                                break;
                            }
                        }
                        p.setEstadoPedido(op);
                    }
                }
            }
        }
        if (encontrado == false) {
            System.out.println("Pedido no encontrado.");
        }

    }

    /**
     * Metodo para consultar pedidos asignados al Repartidor
     * 
     * @param pedidos Lista de pedidos disponibles
     */
    public void consultarPedidosAsignados(ArrayList<Pedido> pedidos) {
        System.out.println("===== PEDIDOS ASIGNADOS =====");
        int contador = 0;
        boolean tienePedidos = false;
        System.out.println("Buscando pedidos asignados no entregados...");

        for (Pedido p : pedidos) {
            if (p.getCodigoRepartidor().equals(codigo) && !p.getEstadoPedido().equals(EstadoPedido.ENTREGADO)) {
                contador++;
                if (contador == 1) {
                    System.out.println("Pedidos encontrados:");
                }

                tienePedidos = true;
                System.out.println(contador + ". Código: " + p.getCodigoPedido());
                System.out.println("   Fecha: " + p.getFecha());
                System.out.println("   Estado actual: " + p.getEstadoPedido());
            }
        }
        System.out.println("-------------------------------------------------");
        if (!tienePedidos) {
            System.out.println("No tienes pedidos asignados pendientes.");
        } else {
            System.out.println("Total de pedidos pendientes: " + (contador));
        }
        System.out.println("Recuerde que solo puede gestionar los pedidos que se encuentren "
                + EstadoPedido.EN_PREPARACION + " o " + EstadoPedido.EN_RUTA + ".");
    }

    /**
     * Metodo Sobreescrito toString
     * 
     * @return Informacion del Repartidor
     */
    @Override
    public String toString() {
        return codigo + "|" + cedula + "|" + nombres + "|" + apellidos + "|" + empresa + "|" + correoElectronico;
    }

    // Metodos Getters y Setters
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getEmpresa() {
        return this.empresa;
    }

}
