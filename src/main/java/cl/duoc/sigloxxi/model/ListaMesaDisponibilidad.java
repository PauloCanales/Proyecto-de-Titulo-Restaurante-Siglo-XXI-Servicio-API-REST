package cl.duoc.sigloxxi.model;

import java.util.List;

import cl.duoc.sigloxxi.entity.Producto;

public class ListaMesaDisponibilidad {

	Long id;
	String nombre;
	Long capacidad;
	Long estado;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Long getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(Long capacidad) {
		this.capacidad = capacidad;
	}
	public Long getEstado() {
		return estado;
	}
	public void setEstado(Long estado) {
		this.estado = estado;
	}
	
	
	
}
