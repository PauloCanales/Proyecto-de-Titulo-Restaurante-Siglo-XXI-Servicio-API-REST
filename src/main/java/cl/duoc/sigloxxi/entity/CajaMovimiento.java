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
@Table(name = "CajaMovimiento")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
public class CajaMovimiento implements Serializable {

	private static final long serialVersionUID = 4479097646778036513L;

	private Long idCajaMovimiento;
	private String nombre;
	private Long valor;
	private TipoMovimiento tipoMovimiento;
	private Long fkTipoMovimiento;
	private Caja caja;
	private Long fkCaja;
	private Long estado;
	private Date createdAt;
	private Date updatedAt;
	
	
	public CajaMovimiento() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getIdCajaMovimiento() {
		return idCajaMovimiento;
	}

	public void setIdCajaMovimiento(Long idCajaMovimiento) {
		this.idCajaMovimiento = idCajaMovimiento;
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
	@JoinColumn(name = "id_tipo_movimiento")
	public TipoMovimiento getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}
	
	@Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	public Long getFkTipoMovimiento() {
		return fkTipoMovimiento;
	}
	public void setFkTipoMovimiento(Long fkTipoMovimiento) {
		this.fkTipoMovimiento = fkTipoMovimiento;
	}	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_caja")
	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}
	
	@Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	public Long getFkCaja() {
		return fkCaja;
	}
	public void setFkCaja (Long fkCaja) {
		this.fkCaja = fkCaja;
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


	
}

