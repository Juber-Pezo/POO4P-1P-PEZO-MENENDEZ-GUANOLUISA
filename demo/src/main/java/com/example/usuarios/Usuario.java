package com.example.usuarios;

import java.util.ArrayList;
import java.util.Scanner;

import com.example.Pedido;
import com.example.Producto;

/**
 * Clase abstracta Usuario
 * 
 * Representa un usuario base del sistema, puede ser un cliente o un repartidor.
 * Contiene información común a ambos tipos de usuarios.
 * 
 * @author Guanoluisa Jelthon
 */
public abstract class Usuario {
    
    protected String codigo;
    protected String cedula;
    protected String nombres;
    protected String apellidos;
    protected String correoElectronico;
    protected String usuario;
    protected String contrasena;
    protected char rol; // 'C' = Cliente, 'R' = Repartidor

    /**
     * Constructor de Usuario
     * 
     * @param codigo Identificador único del usuario
     * @param cedula Número de cédula del usuario
     * @param nombres Nombres completos del usuario
     * @param apellidos Apellidos completos del usuario
     * @param usuario Nombre de usuario para login
     * @param contrasena Contraseña para autenticación
     * @param correo Correo electrónico principal
     * @param rol Rol del usuario ('C' para cliente, 'R' para repartidor)
     */
    public Usuario(String codigo, String cedula, String nombres, String apellidos, 
                  String usuario, String contrasena, String correo, char rol) {
        this.codigo= codigo;
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.correoElectronico = correo;
        this.rol=rol;
    }

    /**
     * Método abstracto para que los subclases gestionen pedidos
     * 
     * @param productos Lista de productos disponibles
     * @param usuarios Lista de usuarios registrados
     * @param pedidos Lista de pedidos existentes
     * @param sistema Instancia del sistema para operaciones auxiliares
     */
    public abstract void gestionarPedidos(ArrayList<Producto> productos, ArrayList<Usuario> usuarios, ArrayList<Pedido> pedidos, Scanner scanner);
    
    /**
     * Representación en String del objeto Usuario
     * 
     * @return Cadena con los datos separados por "|"
     */
    @Override
    public String toString() {
        return codigo+"|"+cedula+"|"+nombres+"|"+apellidos+"|"+usuario+"|"+contrasena+"|"+correoElectronico+"|"+rol;
    }

    // Metodos Getters y setters
    public String getCodigo() {
        return codigo;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public char getRol(){
        return rol;
    }

    public void setRol(char rol){
        this.rol = rol;
    }

    public void setCodigo(String codigoUnico) {
        this.codigo = codigoUnico;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setCorreo(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

}

