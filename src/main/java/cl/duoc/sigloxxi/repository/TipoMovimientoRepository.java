package cl.duoc.sigloxxi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import cl.duoc.sigloxxi.entity.TipoMovimiento;


@Repository
public interface TipoMovimientoRepository extends JpaRepository<TipoMovimiento, Long> {
    /*Acá deberian ser llamadas a Procedimientos Almacenados.*/
	@Query(value = "SELECT * FROM tipo_movimiento WHERE nombre = :nombre and estado = 1", nativeQuery = true)
	TipoMovimiento findByNombre(@Param("nombre") String nombre);	
	
	@Query(value = "SELECT * FROM tipo_movimiento WHERE estado = 1", nativeQuery = true)
	List<TipoMovimiento> findAllActive();
	
	@Query(value = "SELECT * FROM tipo_movimiento WHERE estado = 0", nativeQuery = true)
	List<TipoMovimiento> findAllInactive();
	
	@Query(value = "SELECT * FROM tipo_movimiento WHERE id_tipo_movimiento = :idTipoMovimiento and estado = 1", nativeQuery = true)
	TipoMovimiento findOneActive(@Param("idTipoMovimiento") Long idTipoMovimiento);
}