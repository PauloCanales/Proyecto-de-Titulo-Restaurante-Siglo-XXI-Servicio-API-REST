package cl.duoc.sigloxxi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import cl.duoc.sigloxxi.entity.Mesa;


@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {
    //Acá deberian ser llamadas a Procedimientos Almacenados.
	@Query(value = "SELECT * FROM mesa WHERE nombre = :nombre and estado = 1", nativeQuery = true)
	Mesa findByNombre(@Param("nombre") String nombre);	
	
	@Query(value = "SELECT * FROM mesa WHERE estado = 1", nativeQuery = true)
	List<Mesa> findAllActive();
	
	@Query(value = "SELECT * FROM mesa WHERE estado = 0", nativeQuery = true)
	List<Mesa> findAllInactive();
	
	@Query(value = "SELECT * FROM mesa WHERE id = :idMesa and estado = 1", nativeQuery = true)
	Mesa findOneActive(@Param("idMesa") Long idMesa);
}