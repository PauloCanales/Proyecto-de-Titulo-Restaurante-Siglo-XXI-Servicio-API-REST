package cl.duoc.sigloxxi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import cl.duoc.sigloxxi.entity.Rol;


@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    //Acá deberian ser llamadas a Procedimientos Almacenados.
	@Query(value = "SELECT * FROM rol WHERE nombre = :nombre and estado = 1", nativeQuery = true)
	Rol findByNombre(@Param("nombre") String nombre);	
	
	@Query(value = "SELECT * FROM rol WHERE estado = 1", nativeQuery = true)
	List<Rol> findAllActive();
	
	@Query(value = "SELECT * FROM rol WHERE estado = 0", nativeQuery = true)
	List<Rol> findAllInactive();
	
	@Query(value = "SELECT * FROM rol WHERE id = :idrol and estado = 1", nativeQuery = true)
	Rol findOneActive(@Param("idrol") Long idrol);
}