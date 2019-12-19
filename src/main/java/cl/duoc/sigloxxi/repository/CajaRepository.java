package cl.duoc.sigloxxi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.duoc.sigloxxi.entity.Caja;



@Repository
public interface CajaRepository extends JpaRepository<Caja, Long> {
    //Acá deberian ser llamadas a Procedimientos Almacenados.
	@Query(value = "SELECT * FROM caja WHERE nombre = :nombre and estado = 1", nativeQuery = true)
	Caja findByNombre(@Param("nombre") String nombre);	
	
	@Query(value = "SELECT * FROM caja WHERE estado = 1", nativeQuery = true)
	List<Caja> findAllActive();
	
	@Query(value = "SELECT * FROM caja WHERE estado = 0", nativeQuery = true)
	List<Caja> findAllInactive();
	
	@Query(value = "SELECT * FROM caja WHERE id = :idcaja and estado = 1", nativeQuery = true)
	Caja findOneActive(@Param("idcaja") Long idcaja);
}