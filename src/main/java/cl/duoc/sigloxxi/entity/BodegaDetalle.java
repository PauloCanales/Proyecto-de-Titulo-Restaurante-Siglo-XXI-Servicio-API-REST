package cl.duoc.sigloxxi.entity;
import java.beans.Transient;
/** Representa el la entidad en la base de datos
 * @author Francisco Sáez Guerra.
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
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import cl.duoc.sigloxxi.constants.Constants;

@Entity
@Table(name = "bodega_detalle")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
public class BodegaDetalle implements Serializable {

	private static final long serialVersionUID = 4479097646778036513L;

	private Long idBodegaDetalle;
	private double cantidad;
	private Date createdAt;
	private Bodega bodega;
	private Proveedor proveedor;
	private TipoMovimiento tipoMovimiento;
	private Insumo insumo;
	private Long fkBodega;
	private Long fkProveedor;
	private Long fkTipoMovimiento;
	private Long fkInsumo;
	private Long estado;
	
	
	public BodegaDetalle() {
	}

	//ID Bodega_Detalle
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getIdBodegaDetalle() {
		return idBodegaDetalle;
	}

	public void setIdBodegaDetalle(Long idBodegaDetalle) {
		this.idBodegaDetalle = idBodegaDetalle;
	}
	
	//Cantidad
	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	//ID Bodega
	//Relación a ID de la tabla Bodega 1-1
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_bodega")
	public Bodega getBodega() {
		return bodega;
	}

	public void setBodega(Bodega bodega) {
		this.bodega = bodega;
	}
	
	@Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	public Long getFkBodega() {
		return fkBodega;
	}
	public void setFkBodega(Long fkBodega) {
		this.fkBodega = fkBodega;
	}	
	
	//ID Proveedor
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_proveedor")
	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
	
	@Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	public Long getFkProveedor() {
		return fkProveedor;
	}
	public void setFkProveedor(Long fkProveedor) {
		this.fkProveedor = fkProveedor;
	}	
	
	//ID Tipo Movimiento
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

	//ID Insumo
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
	
	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
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
