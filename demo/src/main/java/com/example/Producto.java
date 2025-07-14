package com.example;



import java.util.ArrayList;

import com.example.enums.CategoriaProducto;
/**
 * Clase Producto
 * 
 * @author Juber Pezo
 * @version 1.0
 */
public class Producto {

    private String codigo;
    private String nombre;
    private CategoriaProducto categoria;
    private double precio;
    private int stock;

    /**
     * Constructor de Producto
     * 
     * @param codigo    Codigo del producto
     * @param nombre    Nombre del producto
     * @param categoria Categoria a la que pertenece el producto
     * @param precio    Precio del producto
     * @param stock     Stock en inventario del producto
     */
    public Producto(String codigo, String nombre, CategoriaProducto categoria, double precio, int stock){
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    // ====== MÉTODO AUXILIAR PARA BUSCAR PRODUCTO POR CÓDIGO ======
    public static Producto buscarProducto(String codigo, ArrayList<Producto> productos) {
        for (Producto producto : productos) {
            if (producto.getCodigo().equals(codigo)) {
                return producto;
            }
        }
        return null; // Si no se encuentra
    }

    /**
     * Muestra los datos del producto
     * 
     * @return codigo, categoria, nombre, precio, stock
     */
    @Override
    public String toString(){
        return codigo+"|"+categoria+"|"+nombre+"|"+precio+"|"+stock;
    }

    // Metodos Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public CategoriaProducto getCategoria() {
        return categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCategoria(CategoriaProducto categoria) {
        this.categoria = categoria;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}


