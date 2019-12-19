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
@Table(name = "ingrediente")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
public class Ingrediente implements Serializable {

	private static final long serialVersionUID = 4479097646778036513L;

	private Long id;
	private String nombre;
	private Long estado;
	private Long cantidad;
	private Date createdAt;
	private Date updatedAt;
	private Receta receta;
	private Long fkReceta;	
	private UnidadMedida unidadMedida;
	private Long fkUnidadMedida;
	private Insumo insumo;
	private Long fkInsumo;	
	
	public Ingrediente() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull(message = Constants.VALID_NOMBRE_REQUERIDO)
	public String getNombre() {
		return nombre;
	}

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
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
	
	public Long getCantidad() {
		return cantidad;
	}

	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
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
	public void setFkReceta(Long fkReceta) {
		this.fkReceta = fkReceta;
	}	

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_unidad_medida")
	public UnidadMedida getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(UnidadMedida unidadMedida) {
		this.unidadMedida = unidadMedida;
	}
	
	
	@Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	public Long getFkUnidadMedida() {
		return fkUnidadMedida;
	}
	public void setFkUnidadMedida(Long fkUnidadMedida) {
		this.fkUnidadMedida = fkUnidadMedida;
	}
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_insumo")
	public Insumo getInsumo() {
		return insumo;
	}

	public void setInsumo(Insumo insumo) {
		this.insumo = insumo;
	}
	
	
	@Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	public Long getFkInsumo() {
		return fkInsumo;
	}
	public void setFkInsumo(Long fkInsumo) {
		this.fkInsumo = fkInsumo;
	}	

}
