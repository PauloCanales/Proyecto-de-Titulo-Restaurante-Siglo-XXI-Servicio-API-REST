package cl.duoc.sigloxxi.entity;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "sesion")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
public class Sesion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4479097646778036513L;

	private Long idSesion;
	private String token;
	private Usuario usuario;
	private Long fkUsuario;
	private Date createdAt;
	private Date updatedAt;
	private Long activo;

	public Sesion() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getIdSesion() {
		return idSesion;
	}

	public void setIdSesion(Long idSesion) {
		this.idSesion = idSesion;
	}

	@NotNull(message = "token es requerido")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		if (token != null && this.token != token) {
			this.token = token;
		}
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

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_usuario")
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

	@Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	public Long getFkRol() {
		return fkUsuario;
	}

	public void setFkUsuario(Long fkUsuario) {
		this.fkUsuario = fkUsuario;
	}
}
