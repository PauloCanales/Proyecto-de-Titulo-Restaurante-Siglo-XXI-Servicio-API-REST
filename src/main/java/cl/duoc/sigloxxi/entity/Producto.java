package cl.duoc.sigloxxi.entity;
import java.beans.Transient;
/** Representa el la entidad en la base de datos
 * @author Andres Valencia Nuñez.
 * @version 0.1
 * @since 1.0
*/
import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import cl.duoc.sigloxxi.constants.Constants;


@Entity
@Table(name = "Producto")
@EntityListeners(AuditingEntityListener.class)
public class Producto implements Serializable {

	private static final long serialVersionUID = 4479097646778036513L;

	private Long idProducto;
	private String nombre;
	private String descripcion;
	private Long valor;
	private TipoProducto tipoProducto;
	private Long fkTipoProducto;
	private Receta receta;
	private Long fkReceta;	
	private Dificultad dificultad;
	private Long fkDificultad;
	private Long estado;
	@Column(length=100000)
	@Lob
	private String imagen;
	
	
	public Producto() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	@NotNull(message = Constants.VALID_NOMBRE_REQUERIDO)
	public String getNombre() {
		return nombre;
	}

	

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		if (estado != null && this.estado != estado) {
			this.estado = estado;
		}
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public Long getValor() {
		return valor;
	}

	public void setValor(Long valor) {
		this.valor = valor;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_tipo_producto")
	public TipoProducto getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(TipoProducto tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	
	@Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	public Long getFkTipoProducto() {
		return fkTipoProducto;
	}
	public void setFkTipoProducto(Long fkTipoProducto) {
		this.fkTipoProducto = fkTipoProducto;
	}	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_dificultad")
	public Dificultad getDificultad() {
		return dificultad;
	}

	public void setDificultad(Dificultad dificultad) {
		this.dificultad = dificultad;
	}
	
	
	@Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	public Long getFkDificultad() {
		return fkDificultad;
	}
	public void setFkDificultad (Long fkDificultad) {
		this.fkDificultad = fkDificultad;
	}	
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_receta")
	public Receta getReceta() {
		return receta;
	}

	public void setReceta(Receta receta) {
		this.receta = receta;
	}
	
	
	@Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	public Long getFkReceta() {
		return fkReceta;
	}
	public void setFkReceta (Long fkReceta) {
		this.fkReceta = fkReceta;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}	
	

	
}

