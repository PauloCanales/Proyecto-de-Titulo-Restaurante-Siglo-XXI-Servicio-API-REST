package cl.duoc.sigloxxi.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.duoc.sigloxxi.entity.Permiso;


@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Long> {
    //Acá deberian ser llamadas a Procedimientos Almacenados.
	@Query(value = "SELECT * FROM permiso WHERE nombre = :nombre and estado = 1", nativeQuery = true)
	Permiso findByNombre(@Param("nombre") String nombre);	
	
	@Query(value = "SELECT * FROM permiso WHERE estado = 1", nativeQuery = true)
	List<Permiso> findAllActive();
	
	@Query(value = "SELECT * FROM permiso WHERE estado = 0", nativeQuery = true)
	List<Permiso> findAllInactive();
	
	@Query(value = "SELECT * FROM permiso WHERE id = :id and estado = 1", nativeQuery = true)
	Permiso findOneActive(@Param("id") Long id);

	@Query(value = "SELECT permiso.* FROM permiso " + 
			"INNER JOIN api_ruta on permiso.id_api_ruta = api_ruta.id_api_ruta " + 
			"INNER JOIN rol_permiso on permiso.id = rol_permiso.id_rol_permiso " + 
			"INNER JOIN usuario on rol_permiso.id_rol = usuario.id_rol " + 
			"WHERE usuario.id = :idUsuario " + 
			"AND api_ruta.ruta = :ruta", nativeQuery = true)
	Permiso findByUsuarioRuta(@Param("idUsuario") Long idUsuario, @Param("ruta") String ruta);	
	
}