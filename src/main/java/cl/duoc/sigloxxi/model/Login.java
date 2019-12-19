package cl.duoc.sigloxxi.model;

import javax.validation.constraints.NotNull;

public class Login {
    private String nombreUsuario;
    private String contrasena;

    @NotNull(message = "Se requiere el nombre de Usuario")
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    @NotNull(message = "Se requiere la Contraseña")
    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

}
