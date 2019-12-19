package cl.duoc.sigloxxi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.duoc.sigloxxi.entity.Pedido;
import cl.duoc.sigloxxi.entity.UnidadMedida;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	@Query(value = "SELECT * FROM pedido WHERE id_mesa_disponibilidad = :idMesa and estado = 1", nativeQuery = true)
	Pedido findByMesaDisponiblidad(@Param("idMesa") Long idMesa);	
	
	@Query(value = "SELECT * FROM pedido WHERE estado = 2", nativeQuery = true)
	List<Pedido> findPendientePago();		
	
	
	
}