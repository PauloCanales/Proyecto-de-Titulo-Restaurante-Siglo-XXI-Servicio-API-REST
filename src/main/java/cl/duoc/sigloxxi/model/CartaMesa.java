package cl.duoc.sigloxxi.model;

import java.util.List;

import cl.duoc.sigloxxi.entity.Producto;

public class CartaMesa {

	String tipoProducto;
	List<Producto> productos;
	public String getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	public List<Producto> getProductos() {
		return productos;
	}
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	
	
}
