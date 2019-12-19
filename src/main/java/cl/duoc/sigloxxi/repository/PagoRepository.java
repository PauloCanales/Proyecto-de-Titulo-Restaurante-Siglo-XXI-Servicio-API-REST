package cl.duoc.sigloxxi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.duoc.sigloxxi.entity.Pago;
import cl.duoc.sigloxxi.entity.Pedido;
import cl.duoc.sigloxxi.entity.Rol;


@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
	
	
	@Query(value = "SELECT * FROM pago WHERE estado = 2", nativeQuery = true)
	List<Pago> findPagosPendientes();	
}