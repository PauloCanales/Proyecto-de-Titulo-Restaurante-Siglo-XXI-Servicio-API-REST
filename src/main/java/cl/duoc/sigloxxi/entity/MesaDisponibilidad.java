package cl.duoc.sigloxxi.entity;
import java.beans.Transient;
/** Representa el la entidad en la base de datos
 * @author Tomás Durán.
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
@Table(name = "mesa_disponibilidad")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
public class MesaDisponibilidad implements Serializable {

	private static final long serialVersionUID = 4479097646778036513L;

	private Long idMesaDisponibilidad;
	private Long estado;
	private Date fechaInicio;
	private Date fechaFin;
	private Date createdAt;
	private Date updatedAt;
	private Mesa mesa;
	private Funcionario funcionario;
	
	private Long fkMesa;
	private Long fkFuncionario;	
	
	public MesaDisponibilidad() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getIdMesaDisponibilidad() {
		return idMesaDisponibilidad;
	}

	public void setIdMesaDisponibilidad(Long idMesaDisponibilidad) {
		this.idMesaDisponibilidad = idMesaDisponibilidad;
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

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_mesa")
	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_funcionario")
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}	
	
	@Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	public Long getFkMesa() {
		return fkMesa;
	}
	public void setFkMesa (Long fkMesa) {
		this.fkMesa = fkMesa;
	}
	
	@Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	public Long getFkFuncionario() {
		return fkFuncionario;
	}
	public void setFkFuncionario (Long fkFuncionario) {
		this.fkFuncionario = fkFuncionario;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}	
	
}
