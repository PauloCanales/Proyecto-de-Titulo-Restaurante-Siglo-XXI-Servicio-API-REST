package cl.duoc.sigloxxi.model;

public class IngredienteResponse {
    private String nombre;
    private Long cantidad;
    private String insumoNombre;
    private Long insumoId;
    private String unidadMedidaNombre;
    private Long unidadMedidaId;
    

    public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}

	public String getInsumoNombre() {
		return insumoNombre;
	}

	public void setInsumoNombre(String insumoNombre) {
		this.insumoNombre = insumoNombre;
	}

	public Long getInsumoId() {
		return insumoId;
	}

	public void setInsumoId(Long insumoId) {
		this.insumoId = insumoId;
	}

	public String getUnidadMedidaNombre() {
		return unidadMedidaNombre;
	}

	public void setUnidadMedidaNombre(String unidadMedidaNombre) {
		this.unidadMedidaNombre = unidadMedidaNombre;
	}

	public Long getUnidadMedidaId() {
		return unidadMedidaId;
	}

	public void setUnidadMedidaId(Long unidadMedidaId) {
		this.unidadMedidaId = unidadMedidaId;
	}
		


}
