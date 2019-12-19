package cl.duoc.sigloxxi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import cl.duoc.sigloxxi.entity.TipoProducto;


@Repository
public interface TipoProductoRepository extends JpaRepository<TipoProducto, Long> {
    //Acá deberian ser llamadas a Procedimientos Almacenados.
	@Query(value = "SELECT * FROM tipo_producto WHERE nombre = :nombre and estado = 1", nativeQuery = true)
	TipoProducto findByNombre(@Param("nombre") String nombre);	
	
	@Query(value = "SELECT * FROM tipo_producto WHERE estado = 1", nativeQuery = true)
	List<TipoProducto> findAllActive();
	
	@Query(value = "SELECT * FROM tipo_producto WHERE estado = 0", nativeQuery = true)
	List<TipoProducto> findAllInactive();
	
	@Query(value = "SELECT * FROM tipo_producto WHERE id = :id and estado = 1", nativeQuery = true)
	TipoProducto findOneActive(@Param("id") Long id);
}