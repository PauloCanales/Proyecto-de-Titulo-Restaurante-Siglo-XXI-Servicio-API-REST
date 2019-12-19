package cl.duoc.sigloxxi.entity;
import java.beans.Transient;
/** Representa el la entidad en la base de datos
 * @author Andres Valencia Nuñez.
 * @version 0.1
 * @since 1.0
*/
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import cl.duoc.sigloxxi.constants.Constants;

@EnableTransactionManagement
@Entity
@Table(name = "Rol")
@EntityListeners(AuditingEntityListener.class)
public class Rol implements Serializable {

	private static final long serialVersionUID = 4479097646778036513L;

	private Long id;
	private String nombre;
	private Long estado;
	private Set<Permiso> permisos = new HashSet<>();
	private ArrayList<Long> permisosFk = new ArrayList<>();
	
	public Rol() {
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

	

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "rol_permiso", joinColumns = { @JoinColumn(name = "id_rol") }, inverseJoinColumns = {
			@JoinColumn(name = "id_permiso") })
	public Set<Permiso> getPermisos() {
		return permisos;
	}

	public void setPermisos(Set<Permiso> permisos) {
		this.permisos = permisos;
	}
	
	@Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	public ArrayList<Long> getPermisosFk() {
		return permisosFk;
	}
	public void setPermisosFk(ArrayList<Long> permisosFk) {
		this.permisosFk = permisosFk;
	}

	


	
}