package com.example;

import java.time.LocalDate;
import java.util.ArrayList;

import com.example.archivos.ManejoArchivos;
import com.example.enums.EstadoPedido;
/**
 * Clase Pedido
 * 
 * @author Juber Pezo
 * @version 1.0
 */
public class Pedido {
    
    private static int contadorPedidos = cargarContador();
    private String codigoPedido;
    private String codigoProducto;
    private int cantidad;
    private double valorPagado;
    private String codigoRepartidor;
    private EstadoPedido estadoPedido; // EN_PREPARACION, EN_RUTA, ENTREGADO (Enum)
    private LocalDate fecha;
    private String codigoCliente;

    /**
     * Metodo guardar contador
     * 
     * Guardar la iteraccion del contador
     */
    public static void guardarContador(){
        String s = Integer.toString(contadorPedidos); // o String.valueOf(contadorPedidos)
        ManejoArchivos.EscribirArchivo("Contador.txt",s); // dejar asi-> linea 1: 1000 ; linea 2: (espacio vacio)
    }

    /**
     * Metodo cargar contador
     * 
     * Realiza la iteracion a partir de la ultima iteracion
     * 
     * @return el numero de pedido actual
     */
    public static int cargarContador(){
        ArrayList<String> lineas = ManejoArchivos.LeeFicheroCont("Contador.txt");
        String ultima = lineas.get(lineas.size() - 1);
        //System.out.println("Última línea: " + ultima);
        contadorPedidos = Integer.parseInt(ultima);
        contadorPedidos++;

        guardarContador();

        return contadorPedidos;
    }

    /**
     * Constructor de Pedido ; para Pedidos.txt
     * 
     * @param fecha            Fecha del pedido
     * @param codigoProducto   Codigo del producto en el pedido
     * @param cantidad         Cantidad del producto en el pedido
     * @param valorPagado      Valor a pagar por el pedido
     * @param estadoPedido     Estado actual del pedido
     * @param codigoRepartidor Codigo del Repartidor encargado del pedido
     * @param codigoCliente    Codigo del cliente que ordeno el pedido
     */
    public Pedido(LocalDate fecha, String codigoProducto, int cantidad, double valorPagado, EstadoPedido estadoPedido,
    String codigoRepartidor, String codigoCliente) {
        
        this.codigoPedido = "PED" + (contadorPedidos);
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
        this.valorPagado = valorPagado;
        this.codigoRepartidor = codigoRepartidor;
        this.estadoPedido = EstadoPedido.EN_PREPARACION;
        this.fecha = fecha;
        this.codigoCliente=codigoCliente;
    }

    /**
     * Sobrecarga del Constructor de Pedido
     * no recibe un contador de pedidos
     * 
     * @param codigoPedido     Codigo del Pedido
     * @param fecha            Fecha del Pedido
     * @param codigoProducto   Codigo del Producto
     * @param cantidad         Cantidad del producto en el pedido
     * @param valorPagado      Valor a pagar por el pedido
     * @param estado           Estado actual del pedido
     * @param codigoRepartidor Codigo del Repartidor encargado del pedido
     * @param codigoCliente    Codigo del cliente que ordeno el pedido
     */
    public Pedido(String codigoPedido, LocalDate fecha, String codigoProducto, int cantidad,
                  double valorPagado, EstadoPedido estado, String codigoRepartidor, String codigoCliente) {
        this.codigoPedido = codigoPedido;
        this.fecha = fecha;
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
        this.valorPagado = valorPagado;
        this.estadoPedido = EstadoPedido.EN_PREPARACION;
        this.codigoRepartidor = codigoRepartidor;
        this.codigoCliente = codigoCliente;
    }

    /**
     * Metodo Sobreescrito toString
     * 
     * @return Informacion del pedido
     */
    @Override
    public String toString(){
        return codigoPedido +"|"+fecha+"|"+codigoProducto+"|"+cantidad+"|"+valorPagado+"|"+estadoPedido+"|"+codigoRepartidor;
    }

    // Metodos Getters y Setters
    public String getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getValorPagado() {
        return valorPagado;
    }

    public void setValorPagado(double valorPagado) {
        this.valorPagado = valorPagado;
    }

    public String getCodigoRepartidor() {
        return codigoRepartidor;
    }

    public void setCodigoRepartidor(String codigoRepartidor) {
        this.codigoRepartidor = codigoRepartidor;
    }

    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente=codigoCliente;
    }
    
    
}
