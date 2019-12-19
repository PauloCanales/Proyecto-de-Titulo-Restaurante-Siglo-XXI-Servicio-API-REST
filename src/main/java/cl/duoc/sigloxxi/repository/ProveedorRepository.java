package cl.duoc.sigloxxi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import cl.duoc.sigloxxi.entity.Proveedor;


@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    /*Acá deberian ser llamadas a Procedimientos Almacenados.*/
	@Query(value = "SELECT * FROM proveedor WHERE nombre = :nombre and estado = 1", nativeQuery = true)
	Proveedor findByNombre(@Param("nombre") String nombre);	
	
	@Query(value = "SELECT * FROM proveedor WHERE estado = 1", nativeQuery = true)
	List<Proveedor> findAllActive();
	
	@Query(value = "SELECT * FROM proveedor WHERE estado = 0", nativeQuery = true)
	List<Proveedor> findAllInactive();
	
	@Query(value = "SELECT * FROM proveedor WHERE id_proveedor = :idProveedor and estado = 1", nativeQuery = true)
	Proveedor findOneActive(@Param("idProveedor") Long idProveedor);
}