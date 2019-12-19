package cl.duoc.sigloxxi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.duoc.sigloxxi.entity.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	@Query(value = "SELECT usu.* FROM usuario usu WHERE usu.nombre = :nombreUsuario and usu.activo = 1", nativeQuery = true)
	Usuario findByNombreUsuario(@Param("nombreUsuario") String nombreUsuario);

	@Query(value = "SELECT * FROM usuario WHERE activo = 1", nativeQuery = true)
	List<Usuario> findAllActive();

	@Query(value = "SELECT * FROM usuario WHERE activo = 0", nativeQuery = true)
	List<Usuario> findAllInactive();

	@Query(value = "SELECT * FROM usuario WHERE id = :idUsuario and activo = 1", nativeQuery = true)
	Usuario findOneActive(@Param("idUsuario") Long idUsuario);
	
	
}