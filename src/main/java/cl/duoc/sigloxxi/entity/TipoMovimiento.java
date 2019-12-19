package cl.duoc.sigloxxi.entity;
/** Representa el la entidad en la base de datos
 * @author Tomás Durán
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

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cl.duoc.sigloxxi.constants.Constants;

@Entity
@Table(name = "tipoMovimiento")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
public class TipoMovimiento implements Serializable {

	private static final long serialVersionUID = 4479097646778036513L;

	private Long idTipoMovimiento, estado;
	private String nombre;
	private Date createdAt, updatedAt;
	
	public TipoMovimiento() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getIdTipoMovimiento() {
		return idTipoMovimiento;
	}

	public void setIdTipoMovimiento(Long idTipoMovimiento) {
		this.idTipoMovimiento = idTipoMovimiento;
	}
	
	@NotNull(message = Constants.VALID_NOMBRE_REQUERIDO)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	
}
