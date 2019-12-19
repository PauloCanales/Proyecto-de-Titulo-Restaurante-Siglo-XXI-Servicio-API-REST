package cl.duoc.sigloxxi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import cl.duoc.sigloxxi.entity.Dificultad;


@Repository
public interface DificultadRepository extends JpaRepository<Dificultad, Long> {
    //Acá deberian ser llamadas a Procedimientos Almacenados.
	@Query(value = "SELECT * FROM dificultad WHERE nombre = :nombre and estado = 1", nativeQuery = true)
	Dificultad findByNombre(@Param("nombre") String nombre);	
	
	@Query(value = "SELECT * FROM dificultad WHERE estado = 1", nativeQuery = true)
	List<Dificultad> findAllActive();
	
	@Query(value = "SELECT * FROM dificultad WHERE estado = 0", nativeQuery = true)
	List<Dificultad> findAllInactive();
	
	@Query(value = "SELECT * FROM dificultad WHERE id = :iddificultad and estado = 1", nativeQuery = true)
	Dificultad findOneActive(@Param("iddificultad") Long iddificultad);
}