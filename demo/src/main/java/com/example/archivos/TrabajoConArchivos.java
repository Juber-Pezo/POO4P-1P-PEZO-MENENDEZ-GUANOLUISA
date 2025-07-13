package com.example.archivos;

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
        ManejoArchivos.EscribirArchivo("archivosalida.txt","Hola!");
        ManejoArchivos.EscribirArchivo("archivosalida.txt","Adios!");
        ManejoArchivos.EscribirArchivo("archivosalida.txt","NUevo!");
        ManejoArchivos.LeeFichero("archivosalida.txt");
  
        
        
    }

}
