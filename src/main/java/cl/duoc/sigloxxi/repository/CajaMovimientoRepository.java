package cl.duoc.sigloxxi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.duoc.sigloxxi.entity.CajaMovimiento;


@Repository
public interface CajaMovimientoRepository extends JpaRepository<CajaMovimiento, Long> {
    //Acá deberian ser llamadas a Procedimientos Almacenados.
	@Query(value = "SELECT * FROM caja_movimiento WHERE nombre = :nombre and estado = 1", nativeQuery = true)
	CajaMovimiento findByNombre(@Param("nombre") String nombre);	
	
	@Query(value = "SELECT * FROM caja_movimiento WHERE estado = 1", nativeQuery = true)
	List<CajaMovimiento> findAllActive();
	
	@Query(value = "SELECT * FROM caja_movimiento WHERE estado = 0", nativeQuery = true)
	List<CajaMovimiento> findAllInactive();
	
	@Query(value = "SELECT * FROM caja_movimiento WHERE id_caja_movimiento = :idCajaMovimiento and estado = 1", nativeQuery = true)
	CajaMovimiento findOneActive(@Param("idCajaMovimiento") Long idCajaMovimiento);
}