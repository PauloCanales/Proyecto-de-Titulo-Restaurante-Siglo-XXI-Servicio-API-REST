package cl.duoc.sigloxxi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import cl.duoc.sigloxxi.entity.Receta;


@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {
    //Acá deberian ser llamadas a Procedimientos Almacenados.
	@Query(value = "SELECT * FROM receta WHERE nombre = :nombre and estado = 1", nativeQuery = true)
	Receta findByNombre(@Param("nombre") String nombre);	
	
	@Query(value = "SELECT * FROM receta WHERE estado = 1", nativeQuery = true)
	List<Receta> findAllActive();
	
	@Query(value = "SELECT * FROM receta WHERE estado = 0", nativeQuery = true)
	List<Receta> findAllInactive();
	
	@Query(value = "SELECT * FROM receta WHERE id = :idReceta and estado = 1", nativeQuery = true)
	Receta findOneActive(@Param("idReceta") Long idReceta);
	
	@Query(value = "SELECT * FROM receta WHERE nombre = :nombre and id !=:idReceta and estado = 1", nativeQuery = true)
	Receta findByNombreAndReceta(@Param("nombre") String nombre, @Param("idReceta") Long idReceta);
}