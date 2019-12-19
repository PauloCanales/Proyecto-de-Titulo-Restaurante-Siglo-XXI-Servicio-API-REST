package cl.duoc.sigloxxi.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import cl.duoc.sigloxxi.entity.OrdenDetalle;

public class OrdenRequest {
	
    private Long idPedido;
    private List<Long> orden;
	public Long getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}
	public List<Long> getOrden() {
		return orden;
	}
	public void setOrden(List<Long> orden) {
		this.orden = orden;
	}





}
