package cl.duoc.sigloxxi.entity;
/** Representa el la entidad en la base de datos
 * @author Andres Valencia Nuñez.
 * @version 0.1
 * @since 1.0
*/
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "apiRuta")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
public class ApiRuta implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4376342421399221127L;
	private Long idApiRuta;
	private String ruta;
	private String descripcion;
	private Date createdAt;
	private Date updatedAt;
	private Long activo;

	public ApiRuta() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getIdApiRuta() {
		return idApiRuta;
	}

	public void setIdApiRuta(Long idApiRuta) {
		this.idApiRuta = idApiRuta;
	}

	@NotBlank(message = "ruta es requerido")
	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		if (ruta != null && this.ruta != ruta) {
			this.ruta = ruta;
		}
	}

	@NotBlank(message = "descripcion es requerido")
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	@NotNull(message = "activo es requerido")
	public Long getActivo() {
		return activo;
	}

	public void setActivo(Long activo) {
		if (activo != null && this.activo != activo) {
			this.activo = activo;
		}
	}
}