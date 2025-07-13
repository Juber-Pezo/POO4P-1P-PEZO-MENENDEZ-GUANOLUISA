package com.example.archivos;

import com.example.Pedido;

/**
 * Clase TrabajoArchivo
 * 
 * Proporcionada por la profesora.
 */
public class TrabajoConArchivos {
    /**
     * Método principal que escribe varias líneas en un archivo y luego
     * lee su contenido.
     * 
     * @param arg Argumentos de línea de comando (no utilizados)
     */
    public static void main(String[] arg) {
        /* ManejoArchivos.EscribirArchivo("archivosalida.txt","Hola!");
        ManejoArchivos.EscribirArchivo("archivosalida.txt","Adios!");
        ManejoArchivos.EscribirArchivo("archivosalida.txt","NUevo!");
        ManejoArchivos.LeeFichero("archivosalida.txt"); */
  
        // Prueba de los archivos....

        /* ManejoArchivos.LeeFichero("Clientes.txt");
        ManejoArchivos.LeeFichero("Productos.txt");
        ManejoArchivos.LeeFichero("Repartidores.txt");
        ManejoArchivos.LeeFichero("Usuarios.txt"); */

        // Prueba de contador....
        Pedido p1=new Pedido(null, null, 0, 0, null, null, null);
        ManejoArchivos.EscribirArchivo("archivosalida.txt",p1.toString());
        
    }

}
