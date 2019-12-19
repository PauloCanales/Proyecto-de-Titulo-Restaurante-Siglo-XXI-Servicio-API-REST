package cl.duoc.sigloxxi.model;

public class CustomResponse {
	private String mensaje;
	private int codigo;

	public CustomResponse(String mensaje, int codigo) {
		this.setMensaje(mensaje);
		this.setCodigo(codigo);
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
}
