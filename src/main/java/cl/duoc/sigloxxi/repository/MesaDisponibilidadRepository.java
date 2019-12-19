package cl.duoc.sigloxxi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import cl.duoc.sigloxxi.entity.Mesa;
import cl.duoc.sigloxxi.entity.MesaDisponibilidad;


@Repository
public interface MesaDisponibilidadRepository extends JpaRepository<MesaDisponibilidad, Long> {
    //Acá deberian ser llamadas a Procedimientos Almacenados.
	@Query(value = "SELECT * FROM mesa_disponibilidad ", nativeQuery = true)
	List<MesaDisponibilidad> getAll();	
	
	@Query(value = "SELECT * FROM mesa_disponibilidad WHERE id_funcionario =:idFuncionario and estado = 1", nativeQuery = true)
	List<MesaDisponibilidad> getAllDisponiblesFuncionario(@Param("idFuncionario") Long idFuncionario);	
	

}