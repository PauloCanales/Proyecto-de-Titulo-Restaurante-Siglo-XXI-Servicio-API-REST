package cl.duoc.sigloxxi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.duoc.sigloxxi.entity.Orden;
import cl.duoc.sigloxxi.entity.Pedido;
import cl.duoc.sigloxxi.entity.UnidadMedida;


@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {
    //Acá deberian ser llamadas a Procedimientos Almacenados.
	@Query(value = "SELECT * FROM unidad_medida WHERE nombre = :nombre and estado = 1", nativeQuery = true)
	UnidadMedida findByNombre(@Param("nombre") String nombre);	
	
	@Query(value = "SELECT * FROM unidad_medida WHERE estado = 1", nativeQuery = true)
	List<UnidadMedida> findAllActive();
	
	@Query(value = "SELECT * FROM unidad_medida WHERE estado = 0", nativeQuery = true)
	List<UnidadMedida> findAllInactive();
	
	@Query(value = "SELECT * FROM unidad_medida WHERE id = :idUnidadMedida and estado = 1", nativeQuery = true)
	UnidadMedida findOneActive(@Param("idUnidadMedida") Long idUnidadMedida);
	
	
}