package cl.duoc.sigloxxi.model;

import cl.duoc.sigloxxi.entity.Funcionario;

public class PedidoSinPagoResponse {
	
	private Long id;
	private Long valor;
	private String mesa;
	private Funcionario garzon;
	private Long estado;
	private Long formaPagoId;
	private String formaPagoNombre;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getValor() {
		return valor;
	}
	public void setValor(Long valor) {
		this.valor = valor;
	}
	public String getMesa() {
		return mesa;
	}
	public void setMesa(String mesa) {
		this.mesa = mesa;
	}

	public Long getEstado() {
		return estado;
	}
	public void setEstado(Long estado) {
		this.estado = estado;
	}
	public Long getFormaPagoId() {
		return formaPagoId;
	}
	public void setFormaPagoId(Long formaPagoId) {
		this.formaPagoId = formaPagoId;
	}
	public String getFormaPagoNombre() {
		return formaPagoNombre;
	}
	public void setFormaPagoNombre(String formaPagoNombre) {
		this.formaPagoNombre = formaPagoNombre;
	}
	public Funcionario getGarzon() {
		return garzon;
	}
	public void setGarzon(Funcionario garzon) {
		this.garzon = garzon;
	}


	
	
}
