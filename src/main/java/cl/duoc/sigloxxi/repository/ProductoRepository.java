package cl.duoc.sigloxxi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import cl.duoc.sigloxxi.entity.Producto;


@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    //Acá deberian ser llamadas a Procedimientos Almacenados.
	@Query(value = "SELECT * FROM producto WHERE nombre = :nombre and estado = 1", nativeQuery = true)
	Producto findByNombre(@Param("nombre") String nombre);	
	
	@Query(value = "SELECT * FROM producto WHERE estado = 1", nativeQuery = true)
	List<Producto> findAllActive();
	
	@Query(value = "SELECT * FROM producto WHERE estado = 1 and id_tipo_producto =:tipo", nativeQuery = true)
	List<Producto> findAllActiveByType(@Param("tipo") Long tipo);	
	
	@Query(value = "SELECT * FROM producto WHERE estado = 0", nativeQuery = true)
	List<Producto> findAllInactive();
	
	@Query(value = "SELECT * FROM producto WHERE id_producto = :idproducto and estado = 1", nativeQuery = true)
	Producto findOneActive(@Param("idproducto") Long idproducto);
}