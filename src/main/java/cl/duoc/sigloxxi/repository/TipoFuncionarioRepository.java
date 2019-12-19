package cl.duoc.sigloxxi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import cl.duoc.sigloxxi.entity.TipoFuncionario;


@Repository
public interface TipoFuncionarioRepository extends JpaRepository<TipoFuncionario, Long> {
    /*Acá deberian ser llamadas a Procedimientos Almacenados.*/
	@Query(value = "SELECT * FROM tipo_funcionario WHERE nombre = :nombre and estado = 1", nativeQuery = true)
	TipoFuncionario findByNombre(@Param("nombre") String nombre);	
	
	@Query(value = "SELECT * FROM tipo_funcionario WHERE estado = 1", nativeQuery = true)
	List<TipoFuncionario> findAllActive();
	
	@Query(value = "SELECT * FROM tipo_funcionario WHERE estado = 0", nativeQuery = true)
	List<TipoFuncionario> findAllInactive();
	
	@Query(value = "SELECT * FROM tipo_funcionario WHERE id_tipo_funcionario = :idTipoFuncionario and estado = 1", nativeQuery = true)
	TipoFuncionario findOneActive(@Param("idTipoFuncionario") Long idTipoFuncionario);
	
}