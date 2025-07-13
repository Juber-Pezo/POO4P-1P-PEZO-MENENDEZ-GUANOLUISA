package com.example.usuarios;

public class Usuario {
    private String codigoUnico;
    private String cedula;
    private String nombres;
    private String apellidos;
    private String correo;
    private String usuario;
    private String contra;
    // private TipoRolUsuario rol; esta clase todavia no esta creada por eso solo
    // esta comentada

    // NOTA cuando se descomente la variable rol de tipo TipoRolUsuario agregar al
    // constructor y crear los getters y setters correspondientes

    // Constructor
    public Usuario(String uniquecode, String id, String names, String lastNames, String email, String user,
            String password) {
        codigoUnico = uniquecode;
        cedula = id;
        nombres = names;
        apellidos = lastNames;
        correo = email;
        usuario = user;
        contra = password;

    }

    // Metodos Getters
    public String getCodigoUnico() {
        return codigoUnico;
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

    public String getCorreo() {
        return correo;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContra() {
        return contra;

    }

    // Metodos Setters
    public void setCodigoUnico(String codigoUnico) {
        this.codigoUnico = codigoUnico;
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

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }
}
