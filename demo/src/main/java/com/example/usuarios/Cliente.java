public class Cliente {
    private String celular;
    private String direccion;

    public Cliente(String celular, String direccion){
        this.celular=celular;
        this.direccion=direccion;

    }
    public void comprarProducto(){
        //codigo
    }
    public void gestionarProducto(){
        //codigo
    }
    public void setCelular(String celular){
        this.celular=celular;
    }
    public void setDireccion(String direccion){
        this.direccion=direccion;
    }
    public  String getCelular(){
        return this.celular;
    }
    public String getDireccion(){
        return this.direccion;
    }
}
