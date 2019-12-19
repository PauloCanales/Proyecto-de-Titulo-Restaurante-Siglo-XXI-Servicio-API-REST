package cl.duoc.sigloxxi.controller;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
/** Representa una Unidad de Medida para los insumos.
 * @author Tomás Durán.
 * @version 0.1
 * @since 1.0
*/
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.sigloxxi.constants.Constants;
import cl.duoc.sigloxxi.constants.NameMethods;
import cl.duoc.sigloxxi.entity.Funcionario;
import cl.duoc.sigloxxi.entity.Mesa;
import cl.duoc.sigloxxi.entity.MesaDisponibilidad;
import cl.duoc.sigloxxi.entity.Orden;
import cl.duoc.sigloxxi.entity.OrdenDetalle;
import cl.duoc.sigloxxi.entity.Pago;
import cl.duoc.sigloxxi.entity.Pedido;
import cl.duoc.sigloxxi.entity.Producto;
import cl.duoc.sigloxxi.entity.TipoPago;
import cl.duoc.sigloxxi.entity.TipoProducto;
import cl.duoc.sigloxxi.entity.Usuario;
import cl.duoc.sigloxxi.model.CustomResponse;
import cl.duoc.sigloxxi.model.ListaMesaDisponibilidad;
import cl.duoc.sigloxxi.model.MesaDisponibilidadRequest;
import cl.duoc.sigloxxi.model.OrdenRequest;
import cl.duoc.sigloxxi.model.OrdenResponse;
import cl.duoc.sigloxxi.model.PedidoSinPagoResponse;
import cl.duoc.sigloxxi.repository.FuncionarioRepository;
import cl.duoc.sigloxxi.repository.MesaDisponibilidadRepository;
import cl.duoc.sigloxxi.repository.MesaRepository;
import cl.duoc.sigloxxi.repository.OrdenDetalleRepository;
import cl.duoc.sigloxxi.repository.OrdenRepository;
import cl.duoc.sigloxxi.repository.PagoRepository;
import cl.duoc.sigloxxi.repository.PedidoRepository;
import cl.duoc.sigloxxi.repository.ProductoRepository;
import cl.duoc.sigloxxi.repository.TipoPagoRepository;
import cl.duoc.sigloxxi.repository.TipoProductoRepository;
import cl.duoc.sigloxxi.repository.UsuarioRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/mesa-controller")
public class MesaController {
	 /**
	  * Realiza la carga del repository para poder hacer consultas SQL
	  */
	@Autowired
	MesaRepository mesaRepository;
	
	@Autowired
	MesaDisponibilidadRepository mesaDisponibilidadRepository;
	
	@Autowired
	FuncionarioRepository funcionarioRepository;
	
	@Autowired
	PedidoRepository pedidoRepository;	
	
	@Autowired
	ProductoRepository productoRepository;
	
	@Autowired
	OrdenRepository ordenRepository;
	
	@Autowired
	OrdenDetalleRepository ordenDetalleRepository;
	
	@Autowired
	TipoProductoRepository tipoProductoRepository;
	
	@Autowired
	TipoPagoRepository tipoPagoRepository;	
	
	@Autowired
	PagoRepository pagoRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	
	 /**
	  * Muestra la lista de registros disponibles en el sistema.
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */
	@GetMapping(path = "/list", name = "Mesa: " + NameMethods.MENSAJE_LISTAR)
	public ResponseEntity<?> getAll() {
		try {
			List<Mesa> listTemp = mesaRepository.findAll();
			if (!listTemp.isEmpty()) {
				return new ResponseEntity<Object>(listTemp, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 204),
						HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	
	 /**
	  * Muestra la lista de todos los registros activos disponibles en el sistema
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */
	@GetMapping(path = "/list-active", name = "Mesa: " + NameMethods.MENSAJE_LISTAR_ACTIVO)
	public ResponseEntity<?> getAllActive() {
		try {
			

			List<Mesa> listTemp = mesaRepository.findAllActive();
			if (!listTemp.isEmpty()) {
				return new ResponseEntity<Object>(listTemp, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 204),
						HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
			
	}
	
	
	 /**
	  * Muestra la lista de todos los registros inactivos disponibles en el sistema
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */
	@GetMapping(path = "/list-inactive", name = "Mesa: " + NameMethods.MENSAJE_LISTAR_INACTIVO)

	public ResponseEntity<?> getAllInactive() {
		
		try {
		
			List<Mesa> listTemp = mesaRepository.findAllInactive();
			if (!listTemp.isEmpty()) {
				return new ResponseEntity<Object>(listTemp, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 204),
						HttpStatus.NO_CONTENT);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}

	}
	 /**
	  * Muestra la lista de todos los registros activos disponibles en el sistema
	  *	@param mesa contiene el objeto nuevo que vamos a registrar
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */

	@PostMapping(path = "/new", name = "Mesa: " + NameMethods.MENSAJE_NEW)
	public ResponseEntity<?> create(@Valid @RequestBody Mesa mesa) {
		
		//Validación Nombre
		if(mesa.getNombre() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.VALID_NOMBRE_REQUERIDO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		//Validación Capacidad
		if(mesa.getCapacidad() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.VALID_CAPACIDAD_REQUERIDO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		try {
			if (mesaRepository.findByNombre(mesa.getNombre()) == null) {
	
				mesa.setEstado(1L);
				
				mesaRepository.save(mesa);
				
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NUEVO, 200),
						HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_NOMBRE_REGISTRO_EXISTENTE, 200),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}

	
	 /**
	  * obtiene el objeto de un determinado registro segun su id
	  *	@param id es el identificador del registro
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con el registro
	  */
	
	@GetMapping(path = "/get/{id}", name = "Mesa: " + NameMethods.MENSAJE_GET_BY_ID)
	public ResponseEntity<?> getById(@PathVariable(value = "id") Long idMesa) {
		try {
			Mesa mesa = mesaRepository.findOneActive(idMesa);
			if (mesa == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			return ResponseEntity.ok().body(mesa);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}

	
	 /**
	  * Permite actualizar la informacion de un determinado registro
	  *	@param id es el identificador del registro
	  *	@param mesaDetails contiene el objeto nuevo a actualizar
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve objeto jsson con el estado final del proceso
	  */	
	/*-------------por ahi cambie el int idMesa--------------------*/
	@PutMapping(path = "/update/{id}", name = "Mesa: " + NameMethods.MENSAJE_UPDATE)
	public ResponseEntity<?> update(@PathVariable(value = "id") Long idMesa, @Valid @RequestBody Mesa mesaDetails) {
		try {

			Optional<Mesa> mesa = mesaRepository.findById(idMesa);
			
			if (mesa.get() == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			mesa.get().setNombre(mesaDetails.getNombre());
			mesa.get().setCapacidad(mesaDetails.getCapacidad());
	
			mesaRepository.save(mesa.get());
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_ACTUALIZADO, 200),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}

	
	 /**
	  * Permite desactivar un determinado registro
	  *	@param id es el identificador del registro a desactivar
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve objeto jsson con el estado final del proceso
	  */	
	
	@DeleteMapping(path = "/delete/{id}", name = "Mesa: " + NameMethods.MENSAJE_DELETE)
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long idMesa) {
		try {
			Mesa mesa = mesaRepository.findOneActive(idMesa);
			if (mesa == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			
			mesa.setEstado(0L);
	
			mesaRepository.save(mesa);
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_ELIMINADO, 200), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	}
	
	 /**
	  * Muestra la lista de todos los registros activos disponibles en el sistema
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */
	@GetMapping(path = "/list-disponible", name = "Mesa: " + NameMethods.MENSAJE_LISTAR_ACTIVO)
	public ResponseEntity<?> getAllDisponible() {
		try {
			

			List<MesaDisponibilidad> listTemp = mesaDisponibilidadRepository.getAll();
			
			if (!listTemp.isEmpty()) {
				
				List<ListaMesaDisponibilidad> listaMesaDisponibilidad = new ArrayList<>(); 
				
				for (MesaDisponibilidad mesaDisponibilidad : listTemp) {
					
					ListaMesaDisponibilidad response = new ListaMesaDisponibilidad();
					response.setCapacidad(mesaDisponibilidad.getMesa().getCapacidad());
					response.setEstado(mesaDisponibilidad.getEstado());
					response.setId(mesaDisponibilidad.getIdMesaDisponibilidad());
					response.setNombre(mesaDisponibilidad.getMesa().getNombre());
					
					listaMesaDisponibilidad.add(response);
					
				}
				
				
				return new ResponseEntity<Object>(listaMesaDisponibilidad, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 204),
						HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
			
	}
	 /**
	  * Muestra la lista de todos los registros activos disponibles en el sistema
	  *	@param mesa contiene el objeto nuevo que vamos a registrar
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */

	@PostMapping(path = "/asignar-mesa-funcionario", name = "Mesa: " + NameMethods.MENSAJE_NEW)
	public ResponseEntity<?> asignarMesaFuncionario(@RequestBody MesaDisponibilidadRequest mesaDisponibilidad) {
		
		try {
			
			Optional<Funcionario> funcionario = funcionarioRepository.findById(mesaDisponibilidad.getFkGarzon());
			
			if (funcionario.get() == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			
			for (Long idMesa : mesaDisponibilidad.getMesas()) {
				
				Optional<Mesa> mesa = mesaRepository.findById(idMesa);
				
				if(mesa.get()!=null) {
					
					MesaDisponibilidad nuevaAsignacion = new MesaDisponibilidad();
					
					Date date = new Date();
					
					nuevaAsignacion.setMesa(mesa.get());
					nuevaAsignacion.setFuncionario(funcionario.get());
					nuevaAsignacion.setEstado(1L);
					nuevaAsignacion.setFechaInicio(date);
					nuevaAsignacion.setFechaFin(date);
					
					mesaDisponibilidadRepository.save(nuevaAsignacion);
					
				}
				
			}
			
				
				
			
				
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NUEVO, 200),
						HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	 /**
	  * Permite actualizar la informacion de un determinado registro
	  *	@param id es el identificador del registro
	  *	@param mesaDetails contiene el objeto nuevo a actualizar
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve objeto jsson con el estado final del proceso
	  */	
	/*-------------por ahi cambie el int idMesa--------------------*/
	@PutMapping(path = "/update-estado/{id}", name = "Mesa: " + NameMethods.MENSAJE_UPDATE)
	public ResponseEntity<?> updateEstadoDisponibilidad(@PathVariable(value = "id") Long idMesa) {
		try {

			Optional<MesaDisponibilidad> mesa = mesaDisponibilidadRepository.findById(idMesa);
			
			if (mesa.get() == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			
			if(mesa.get().getEstado() == 1L) {
				mesa.get().setEstado(0L);	
			}else {
				
				mesa.get().setEstado(1L);
				
				Pedido pedido = new Pedido();
				
				pedido.setEstado(1L);
				pedido.setMesaDisponibilidad(mesa.get());
				
				pedidoRepository.save(pedido);
			}
			
			mesaDisponibilidadRepository.save(mesa.get());
	
			
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_ACTUALIZADO, 200),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}
	
	 /**
	  * Muestra la lista de todos los registros activos disponibles en el sistema
	  *	@param mesa contiene el objeto nuevo que vamos a registrar
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */

	@PostMapping(path = "/new-orden", name = "Mesa: " + NameMethods.MENSAJE_NEW)
	public ResponseEntity<?> createOrden(@Valid @RequestBody OrdenRequest ordenRequest) {
		
		try {
			
			Optional<Pedido> pedido = pedidoRepository.findById(ordenRequest.getIdPedido());
			
			if (pedido == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			
			
				Orden orden = new Orden();
			
				orden.setEstado(1L);
				orden.setPedido(pedido.get());
				
				ordenRepository.save(orden);
				ordenRepository.flush();

				
				for (Long idProducto : ordenRequest.getOrden()) {
					
					Optional<Producto> productoResponse = productoRepository.findById(idProducto); 
					
					OrdenDetalle ordenDetalleResponse = new OrdenDetalle();
					
					ordenDetalleResponse.setEstado(1L);
					ordenDetalleResponse.setOrden(orden);
					ordenDetalleResponse.setProducto(productoResponse.get());
					
					ordenDetalleRepository.save(ordenDetalleResponse);
					
				}
				
				
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NUEVO, 200),
						HttpStatus.OK);
				
				
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}	
	
	 /**
	  * Muestra la lista de todos los registros activos disponibles en el sistema
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */
	@GetMapping(path = "/list-ordenes/{id}", name = "Mesa: " + NameMethods.MENSAJE_LISTAR_ACTIVO)
	public ResponseEntity<?> getAllOrdenesByPedido(@PathVariable(value = "id") Long idPedido) {
		try {
			
			
			Optional<Pedido> pedido = pedidoRepository.findById(idPedido);
			
			if (pedido == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}			
			
			List<OrdenDetalle> listTemp = ordenDetalleRepository.findAllByPedido(idPedido);
			
			if (!listTemp.isEmpty()) {
				
				return new ResponseEntity<Object>(listTemp, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 204),
						HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
			
	}	
	
	 /**
	  * Muestra la lista de todos los registros activos disponibles en el sistema
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */
	@GetMapping(path = "/list-ordenes-by-type/{tipo}", name = "Mesa: " + NameMethods.MENSAJE_LISTAR_ACTIVO)
	public ResponseEntity<?> getAllOrdenesByTipo(@PathVariable(value = "tipo") Long tipo, @RequestHeader HttpHeaders headers) {
		try {
			
			List<String> authHeader = headers.get("Authorization");
			
	        String token = authHeader.get(0).substring(7);

	        Claims claims = Jwts.parser().setSigningKey("secret")
	            .parseClaimsJws(token).getBody();
	        
	        String sub = claims.get("sub").toString();
			List<OrdenDetalle> listTemp = null;
			
			
			Optional<Usuario> usuario = usuarioRepository.findById(Long.parseLong(sub));
			
			if (usuario.get() == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}		
			
			System.out.println("funcionario:" + usuario.get().getFuncionario().getId());
			
			if(tipo > 0) {
				Optional<TipoProducto> tipoProducto = tipoProductoRepository.findById(tipo);
				
				if (tipoProducto.get() == null) {
					return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
							HttpStatus.NOT_FOUND);
				}			
			
				listTemp = ordenDetalleRepository.findAllByTipoProducto(tipo);
				
			}else if(tipo == 0){
				listTemp = ordenDetalleRepository.findAllByTipoProducto0(usuario.get().getFuncionario().getId());
			}else if(tipo == -1){
				listTemp = ordenDetalleRepository.findAll();
			}
			
			
			
			
			if (!listTemp.isEmpty()) {
				
				
				List<OrdenResponse> ordenResponse = new ArrayList<>();
				
				for (OrdenDetalle ordenDetalle : listTemp) {
					OrdenResponse response = new OrdenResponse();
					
					response.setEstadoId(ordenDetalle.getEstado().toString());
					
					switch (ordenDetalle.getEstado().toString()) {
					case "1":
						response.setEstadoNombre("Solicitado");	
						break;

					case "2":
						response.setEstadoNombre("Listo");	
						break;
						
					default:
						break;
					}
					
					
					response.setId(ordenDetalle.getId().toString());
					response.setMesa(ordenDetalle.getOrden().getPedido().getMesaDisponibilidad().getMesa().getNombre());
					response.setProducto(ordenDetalle.getProducto().getNombre());
					response.setTipoProducto(ordenDetalle.getProducto().getTipoProducto().getNombre());
					response.setFechaInicio(ordenDetalle.getCreatedAt());
					response.setFechaActualizacion(ordenDetalle.getUpdatedAt());
					
					ordenResponse.add(response);
				}
				
				return new ResponseEntity<Object>(ordenResponse, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 204),
						HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			System.out.println("Exception:" + e.toString());
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
			
	}
	/**
	  * Muestra la lista de todos los registros activos disponibles en el sistema
	  *	@param mesa contiene el objeto nuevo que vamos a registrar
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */

	@PostMapping(path = "/cerrar-pedido/{idPedido}/{idTipoPago}", name = "Mesa: " + NameMethods.MENSAJE_NEW)
	public ResponseEntity<?> cerrarPedido(@PathVariable(value = "idPedido") Long idPedido,
											@PathVariable(value = "idTipoPago") Long idTipoPago) {
		
		try {
			
				Optional<Pedido> pedido = pedidoRepository.findById(idPedido);
				Optional<TipoPago> tipoPago = tipoPagoRepository.findById(idTipoPago);
			
				if (pedido == null || tipoPago == null) {
					return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
							HttpStatus.NOT_FOUND);
				}
				
				
				List<OrdenDetalle> listaOrdenes = ordenDetalleRepository.findAllByPedido(idPedido);
				
				Long valorTotalOrdenes = 0L;
				
				for (OrdenDetalle ordenDetalle : listaOrdenes) {
					valorTotalOrdenes = valorTotalOrdenes + ordenDetalle.getProducto().getValor();	
				}
				
				Pago pago = new Pago();
				
				//pago.setCajaMovimiento(null);
				pago.setEstado(2L);
				pago.setOperacion("");
				pago.setPedido(pedido.get());
				pago.setTipoPago(tipoPago.get());
				pago.setValor(valorTotalOrdenes);	
				
				pagoRepository.save(pago);
				
				pedido.get().setEstado(2L);
				pedidoRepository.save(pedido.get());
				
				
				MesaDisponibilidad mesaDisponibilidad = pedido.get().getMesaDisponibilidad();
				
				mesaDisponibilidad.setEstado(1L);
				
				mesaDisponibilidadRepository.save(mesaDisponibilidad);
				
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NUEVO, 200),
						HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}	
	
	 /**
	  * Permite actualizar la informacion de un determinado registro
	  *	@param id es el identificador del registro
	  *	@param mesaDetails contiene el objeto nuevo a actualizar
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve objeto jsson con el estado final del proceso
	  */	
	/*-------------por ahi cambie el int idMesa--------------------*/
	@PutMapping(path = "/cambiar-estado-orden/{id}", name = "Mesa: " + NameMethods.MENSAJE_UPDATE)
	public ResponseEntity<?> updateEstadoOrden(@PathVariable(value = "id") Long id) {
		try {

			Optional<OrdenDetalle> ordenDetalle = ordenDetalleRepository.findById(id);
			
			if (ordenDetalle.get() == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			ordenDetalle.get().setEstado(2L);
	
			ordenDetalleRepository.save(ordenDetalle.get());
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_ACTUALIZADO, 200),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}	
	
	
	 /**
	  * Muestra la lista de todos los registros activos disponibles en el sistema
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */
	@GetMapping(path = "/get-pedido/{idMesa}", name = "Mesa: " + NameMethods.MENSAJE_LISTAR_ACTIVO)
	public ResponseEntity<?> getPedidoByMesa(@PathVariable(value = "idMesa") Long idMesa) {
		try {
			
			Optional<MesaDisponibilidad> mesa = mesaDisponibilidadRepository.findById(idMesa);
			
			if (mesa.get() == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}

			

			Pedido pedido = pedidoRepository.findByMesaDisponiblidad(idMesa);
			
			
			if (pedido!=null) {
				
				return new ResponseEntity<Object>(pedido, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 204),
						HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
			
	}	
	
	 /**
	  * Muestra la lista de todos los registros activos disponibles en el sistema
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */
	@GetMapping(path = "/get-pagos-pendientes", name = "Mesa: " + NameMethods.MENSAJE_LISTAR_ACTIVO)
	public ResponseEntity<?> getPagosPendientes() {
		try {
			
			List<Pago> listaPagosPendientes = pagoRepository.findPagosPendientes();
			
			List<PedidoSinPagoResponse> listaResponse = new ArrayList<>();
			
			if (!listaPagosPendientes.isEmpty()) {
				
				 
				
				for (Pago pago : listaPagosPendientes) {
					
					PedidoSinPagoResponse response = new PedidoSinPagoResponse();	
					
					response.setEstado(pago.getEstado());
					response.setFormaPagoId(pago.getTipoPago().getId());
					response.setFormaPagoNombre(pago.getTipoPago().getNombre());
					response.setGarzon(pago.getPedido().getMesaDisponibilidad().getFuncionario());
					response.setId(pago.getId());
					response.setMesa(pago.getPedido().getMesaDisponibilidad().getMesa().getNombre());
					response.setValor(pago.getValor());
					
					listaResponse.add(response);
					
				}
				
				return new ResponseEntity<Object>(listaResponse, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 204),
						HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
			
	}	
	
	 /**
	  * Permite actualizar la informacion de un determinado registro
	  *	@param id es el identificador del registro
	  *	@param mesaDetails contiene el objeto nuevo a actualizar
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve objeto jsson con el estado final del proceso
	  */	
	/*-------------por ahi cambie el int idMesa--------------------*/
	@PostMapping(path = "/aprobar-pago/{id}", name = "Mesa: " + NameMethods.MENSAJE_UPDATE)
	public ResponseEntity<?> aprobarPago(@PathVariable(value = "id") Long id) {
		try {

			Optional<Pago> pago = pagoRepository.findById(id);
			
			if (pago.get() == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			pago.get().setEstado(3L);
	
			pagoRepository.save(pago.get());
	
			
			MesaDisponibilidad mesa = pago.get().getPedido().getMesaDisponibilidad();
			
			mesa.setEstado(0L);
			mesaDisponibilidadRepository.save(mesa);
			
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_ACTUALIZADO, 200),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}	
		
	
}
