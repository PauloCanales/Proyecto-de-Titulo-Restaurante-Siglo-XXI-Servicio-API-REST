package cl.duoc.sigloxxi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.duoc.sigloxxi.entity.BodegaDetalle;


@Repository
public interface BodegaDetalleRepository extends JpaRepository<BodegaDetalle, Long> {
    //Acá deberian ser llamadas a Procedimientos Almacenados.
	@Query(value = "SELECT * FROM bodega_detalle WHERE cantidad = :cantidad and estado = 1", nativeQuery = true)
	BodegaDetalle findByNombre(@Param("cantidad") double cantidad);	
	
	@Query(value = "SELECT * FROM bodega_detalle WHERE estado = 1", nativeQuery = true)
	List<BodegaDetalle> findAllActive();
	
	@Query(value = "SELECT * FROM bodega_detalle WHERE estado = 0", nativeQuery = true)
	List<BodegaDetalle> findAllInactive();
	
	@Query(value = "SELECT * FROM bodega_detalle WHERE id_bodega_detalle = :idBodegaDetalle and estado = 1", nativeQuery = true)
	BodegaDetalle findOneActive(@Param("idBodegaDetalle") Long idBodegaDetalle);
}