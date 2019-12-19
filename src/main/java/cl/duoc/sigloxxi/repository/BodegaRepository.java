package cl.duoc.sigloxxi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.duoc.sigloxxi.entity.Bodega;


@Repository
public interface BodegaRepository extends JpaRepository<Bodega, Long> {
    //Acá deberian ser llamadas a Procedimientos Almacenados.
	@Query(value = "SELECT * FROM bodega WHERE nombre = :nombre and estado = 1", nativeQuery = true)
	Bodega findByNombre(@Param("nombre") String nombre);
	
	@Query(value = "SELECT * FROM bodega WHERE estado = 1", nativeQuery = true)
	List<Bodega> findAllActive();
	
	@Query(value = "SELECT * FROM bodega WHERE estado = 0", nativeQuery = true)
	List<Bodega> findAllInactive();
	
	@Query(value = "SELECT * FROM bodega WHERE id = :id and estado = 1", nativeQuery = true)
	Bodega findOneActive(@Param("id") Long id);
}