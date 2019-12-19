package cl.duoc.sigloxxi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.duoc.sigloxxi.entity.Orden;
import cl.duoc.sigloxxi.entity.OrdenDetalle;
import cl.duoc.sigloxxi.entity.Pedido;
import cl.duoc.sigloxxi.entity.UnidadMedida;


@Repository
public interface OrdenDetalleRepository extends JpaRepository<OrdenDetalle, Long> {
	
	@Query(value = " SELECT * FROM orden_detalle "
			+ " INNER JOIN orden ON orden_detalle.id_orden = orden.id "
			+ " WHERE orden.id_pedido = :idPedido and "
			+ " orden.estado = 1 "
			+ "", nativeQuery = true)
	List<OrdenDetalle> findAllByPedido(@Param("idPedido") Long idPedido);
	
	@Query(value = " SELECT * FROM orden_detalle "
			+ " INNER JOIN orden ON orden_detalle.id_orden = orden.id "
			+ " INNER JOIN producto ON orden_detalle.id_producto = producto.id_producto "
			+ " WHERE producto.id_tipo_producto = :idTipo and "
			+ " orden.estado = 1 AND "
			+ " producto.estado = 1 "
			+ "", nativeQuery = true)
	List<OrdenDetalle> findAllByTipoProducto(@Param("idTipo") Long idTipo);
	
	@Query(value = " SELECT * FROM orden_detalle "
			+ " INNER JOIN orden ON orden_detalle.id_orden = orden.id "
			+ " INNER JOIN producto ON orden_detalle.id_producto = producto.id_producto "
			+ " INNER JOIN pedido ON orden.id_pedido = pedido.id "
			+ " INNER JOIN mesa_disponibilidad ON pedido.id_mesa_disponibilidad = mesa_disponibilidad.id_mesa_disponibilidad "
			+ " WHERE orden.estado = 1 AND "
			+ " producto.estado = 1 AND "
			+ " mesa_disponibilidad.id_funcionario =:idFuncionario  "
			+ "", nativeQuery = true)
	List<OrdenDetalle> findAllByTipoProducto0(@Param("idFuncionario") Long idFuncionario);
	
	@Query(value = " SELECT * FROM orden_detalle "
			+ " INNER JOIN orden ON orden_detalle.id_orden = orden.id "
			+ " INNER JOIN producto ON orden_detalle.id_producto = producto.id_producto "
			+ " INNER JOIN pedido ON orden.id_pedido = pedido.id "
			+ " INNER JOIN mesa_disponibilidad ON pedido.id_mesa_disponibilidad = mesa_disponibilidad.id_mesa_disponibilidad "
			+ " WHERE orden.estado = 1 AND "
			+ " producto.estado = 1 "
			+ "", nativeQuery = true)
	List<OrdenDetalle> findAll();	
	
}