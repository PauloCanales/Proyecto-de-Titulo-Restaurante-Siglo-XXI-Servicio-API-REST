package cl.duoc.sigloxxi.model;

import java.util.List;

import javax.validation.constraints.NotNull;

public class RecetaRequest {
	
    private String nombre;
    private String preparacion;
    private List<IngredienteRequest> ingredientes;

	@NotNull(message = "Debe Ingresar un Nombre para la receta")
    public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@NotNull(message = "Se requiere una preparacion")
	public String getPreparacion() {
		return preparacion;
	}
	@NotNull(message = "Se requiere de al menos un ingrediente")
	public List<IngredienteRequest> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<IngredienteRequest> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public void setPreparacion(String preparacion) {
		this.preparacion = preparacion;
	}


}
