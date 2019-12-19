package cl.duoc.sigloxxi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import cl.duoc.sigloxxi.entity.Ingrediente;


@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
    //Acá deberian ser llamadas a Procedimientos Almacenados.
	@Query(value = "SELECT * FROM ingrediente WHERE nombre = :nombre and estado = 1", nativeQuery = true)
	Ingrediente findByNombre(@Param("nombre") String nombre);	
	
	@Query(value = "SELECT * FROM ingrediente WHERE estado = 1", nativeQuery = true)
	List<Ingrediente> findAllActive();
	
	@Query(value = "SELECT * FROM ingrediente WHERE estado = 0", nativeQuery = true)
	List<Ingrediente> findAllInactive();
	
	@Query(value = "SELECT * FROM ingrediente WHERE id = :idIngrediente and estado = 1", nativeQuery = true)
	Ingrediente findOneActive(@Param("idIngrediente") Long idIngrediente);
	
	@Query(value = "SELECT * FROM ingrediente WHERE id_receta = :idReceta and estado = 1", nativeQuery = true)
	List<Ingrediente> getAllByReceta(@Param("idReceta") Long idReceta);
	
}