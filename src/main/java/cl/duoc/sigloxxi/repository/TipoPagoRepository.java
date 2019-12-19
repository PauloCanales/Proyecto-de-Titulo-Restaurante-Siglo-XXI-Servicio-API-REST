package cl.duoc.sigloxxi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.duoc.sigloxxi.entity.TipoPago;


@Repository
public interface TipoPagoRepository extends JpaRepository<TipoPago, Long> {
    //Acá deberian ser llamadas a Procedimientos Almacenados.
	@Query(value = "SELECT * FROM tipo_pago WHERE nombre = :nombre and estado = 1", nativeQuery = true)
	TipoPago findByNombre(@Param("nombre") String nombre);	
	
	@Query(value = "SELECT * FROM tipo_pago WHERE estado = 1", nativeQuery = true)
	List<TipoPago> findAllActive();
	
	@Query(value = "SELECT * FROM tipo_pago WHERE estado = 0", nativeQuery = true)
	List<TipoPago> findAllInactive();
	
	@Query(value = "SELECT * FROM tipo_pago WHERE id = :id_tipo_pago and estado = 1", nativeQuery = true)
	TipoPago findOneActive(@Param("id_tipo_pago") Long id_tipo_pago);
}