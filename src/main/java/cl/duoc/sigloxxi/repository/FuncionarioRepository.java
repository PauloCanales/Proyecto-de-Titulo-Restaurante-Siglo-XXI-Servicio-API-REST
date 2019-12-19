package cl.duoc.sigloxxi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import cl.duoc.sigloxxi.entity.Funcionario;
import cl.duoc.sigloxxi.entity.TipoFuncionario;


@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    //Acá deberian ser llamadas a Procedimientos Almacenados.
	@Query(value = "SELECT * FROM funcionario WHERE nombre = :nombre and estado = 1", nativeQuery = true)
	Funcionario findByNombre(@Param("nombre") String nombre);	
	
	@Query(value = "SELECT * FROM funcionario WHERE estado = 1", nativeQuery = true)
	List<Funcionario> findAllActive();
	
	@Query(value = "SELECT * FROM funcionario WHERE estado = 0", nativeQuery = true)
	List<Funcionario> findAllInactive();
	
	@Query(value = "SELECT * FROM funcionario WHERE id = :idFuncionario and estado = 1", nativeQuery = true)
	Funcionario findOneActive(@Param("idFuncionario") Long idFuncionario);
	

	@Query(value = "SELECT * FROM funcionario WHERE id_tipo_funcionario = :idTipoFuncionario and estado = 1", nativeQuery = true)
	List<Funcionario> findByType(@Param("idTipoFuncionario") Long idTipoFuncionario);	
}