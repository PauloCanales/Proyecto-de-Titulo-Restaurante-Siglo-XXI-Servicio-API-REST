package cl.duoc.sigloxxi.model;

import java.util.List;

public class RecetaResponse {
    private String nombre;
    private String preparacion;
    private List<IngredienteResponse> ingredientes;
    
	public RecetaResponse() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPreparacion() {
		return preparacion;
	}

	public void setPreparacion(String preparacion) {
		this.preparacion = preparacion;
	}

	public List<IngredienteResponse> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<IngredienteResponse> ingredientes) {
		this.ingredientes = ingredientes;
	}

}
