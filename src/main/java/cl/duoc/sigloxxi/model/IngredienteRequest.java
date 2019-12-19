package cl.duoc.sigloxxi.model;


import javax.validation.constraints.NotNull;


public class IngredienteRequest {
    private String nombre;
    private Long cantidad;
    private String insumo;
    private String unidadMedida;
    

	@NotNull(message = "Debe Ingresar un Nombre para la receta")
    public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@NotNull(message = "Debe Ingresar una cantidad para el Ingrediente")
	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}
	@NotNull(message = "Debe Ingresar un insumo para el Ingrediente")
	public String getInsumo() {
		return insumo;
	}

	public void setInsumo(String insumo) {
		this.insumo = insumo;
	}
	@NotNull(message = "Debe Ingresar una Unidad de Medida para el Ingrediente")
	public String getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}
	


}
