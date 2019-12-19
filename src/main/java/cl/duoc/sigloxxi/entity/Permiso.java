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
import javax.persistence.ManyToOne;
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
@Table(name = "Permiso")
@EntityListeners(AuditingEntityListener.class)
public class Permiso implements Serializable {

	private static final long serialVersionUID = 4479097646778036513L;

	private Long id;
	private String nombre;
	private Long estado;
	private ApiRuta ruta;
	private Long fkRuta;
	
	public Permiso() {
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

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_api_ruta")	
	public ApiRuta getRuta() {
		return ruta;
	}

	public void setRuta(ApiRuta ruta) {
		this.ruta = ruta;
	}
	
	@Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	public Long getFkRuta() {
		return fkRuta;
	}

	public void setFkRuta(Long fkRuta) {
		this.fkRuta = fkRuta;
	}


	
	
}
