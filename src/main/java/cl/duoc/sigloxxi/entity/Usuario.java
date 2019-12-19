package cl.duoc.sigloxxi.entity;
/** Representa el la entidad en la base de datos
 * @author Andres Valencia Nuñez.
 * @version 0.1
 * @since 1.0
*/
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "usuario")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
public class Usuario implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4479097646778036513L;

	private Long id;
	private String nombre;
	private String contrasena;
	
	private Date createdAt;
	private Date updatedAt;
	private Long activo;
	private Rol rol;
	private Long fkRol;
	private Funcionario funcionario;
	private Long fkFuncionario;

	public Usuario() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull(message = "nombre es requerido")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		if (nombre != null && this.nombre != nombre) {
			this.nombre = nombre;
		}
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		if (contrasena != null && this.contrasena != contrasena) {
			this.contrasena = contrasena;
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

	@NotNull(message = "activo es requerido")
	public Long getActivo() {
		return activo;
	}

	public void setActivo(Long activo) {
		if (activo != null && this.activo != activo) {
			this.activo = activo;
		}
	}
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_rol")
	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	@Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	public Long getFkRol() {
		return fkRol;
	}
	public void setFkRol(Long fkRol) {
		this.fkRol = fkRol;
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
	public Long getFkFuncionario() {
		return fkFuncionario;
	}
	public void setFkFuncionario(Long fkFuncionario) {
		this.fkFuncionario = fkFuncionario;
	}	
}
