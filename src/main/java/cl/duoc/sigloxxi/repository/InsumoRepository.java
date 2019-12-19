package cl.duoc.sigloxxi.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.duoc.sigloxxi.entity.Insumo;
import cl.duoc.sigloxxi.entity.Permiso;


@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Long> {
    //Acá deberian ser llamadas a Procedimientos Almacenados.
	@Query(value = "SELECT * FROM insumo WHERE nombre = :nombre and estado = 1", nativeQuery = true)
	Insumo findByNombre(@Param("nombre") String nombre);	
	
	@Query(value = "SELECT * FROM insumo WHERE estado = 1", nativeQuery = true)
	List<Insumo> findAllActive();
	
	@Query(value = "SELECT * FROM insumo WHERE estado = 0", nativeQuery = true)
	List<Insumo> findAllInactive();
	
	@Query(value = "SELECT * FROM insumo WHERE id = :id and estado = 1", nativeQuery = true)
	Insumo findOneActive(@Param("id") Long id);

	
}