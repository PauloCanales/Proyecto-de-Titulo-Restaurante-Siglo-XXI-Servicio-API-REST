package cl.duoc.sigloxxi.controller;
/** Representa los detalles de bodegas del Restaurant y su estado operativo.
 * @author Francisco Sáez Guerra.
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
import cl.duoc.sigloxxi.entity.Bodega;
import cl.duoc.sigloxxi.entity.BodegaDetalle;
import cl.duoc.sigloxxi.entity.Caja;
import cl.duoc.sigloxxi.entity.CajaMovimiento;
import cl.duoc.sigloxxi.entity.Insumo;
import cl.duoc.sigloxxi.entity.Proveedor;
import cl.duoc.sigloxxi.entity.Receta;
import cl.duoc.sigloxxi.entity.TipoMovimiento;
import cl.duoc.sigloxxi.model.CustomResponse;
import cl.duoc.sigloxxi.repository.BodegaDetalleRepository;
import cl.duoc.sigloxxi.repository.BodegaRepository;
import cl.duoc.sigloxxi.repository.InsumoRepository;
import cl.duoc.sigloxxi.repository.ProveedorRepository;
import cl.duoc.sigloxxi.repository.TipoMovimientoRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/bodega-detalle-controller")
public class BodegaDetalleController {
	 /**
	  * Realiza la carga del repository para poder hacer consultas SQL
	  */
	@Autowired
	BodegaDetalleRepository bodegaDetalleRepository;
	
	@Autowired
	BodegaRepository bodegaRepository;
	
	@Autowired
	InsumoRepository insumoRepository;
	
	@Autowired
	ProveedorRepository proveedorRepository;
	
	@Autowired
	TipoMovimientoRepository tipoMovimientoRepository;
	
	
	
	 /**
	  * Muestra la lista de bodegas disponibles en el sistema.
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */
	@GetMapping(path = "/list", name = "BodegaDetalle: " + NameMethods.MENSAJE_LISTAR)
	public ResponseEntity<?> getAll() {
		try {
			List<BodegaDetalle> listTemp = bodegaDetalleRepository.findAll();
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
	@GetMapping(path = "/list-active", name = "BodegaDetalle: " + NameMethods.MENSAJE_LISTAR_ACTIVO)
	public ResponseEntity<?> getAllActive() {
		try {
			

			List<BodegaDetalle> listTemp = bodegaDetalleRepository.findAllActive();
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
	@GetMapping(path = "/list-inactive", name = "BodegaDetalle: " + NameMethods.MENSAJE_LISTAR_INACTIVO)

	public ResponseEntity<?> getAllInactive() {
		
		try {
		
			List<BodegaDetalle> listTemp = bodegaDetalleRepository.findAllInactive();
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
	  *	@param unidadMedida contiene el objeto nuevo que vamos a registrar
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */

	@PostMapping(path = "/new", name = "BodegaDetalle: " + NameMethods.MENSAJE_NEW)
	public ResponseEntity<?> create(@RequestBody BodegaDetalle bodegaDetalle, @RequestHeader HttpHeaders headers) {
		
		//Validación cantidad
		if(bodegaDetalle.getCantidad() == 0.0) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.VALID_CANTIDAD_REQUERIDO, 400),
					HttpStatus.BAD_REQUEST);
		}		
	
        //Validación FkBodega
		if(bodegaDetalle.getFkBodega() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
					HttpStatus.BAD_REQUEST);
		}	
		
		//Validación FkProveedor
		if(bodegaDetalle.getFkProveedor() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
					HttpStatus.BAD_REQUEST);
		}	
		
		//Validación FkTipoMovimiento
		if(bodegaDetalle.getFkTipoMovimiento() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
					HttpStatus.BAD_REQUEST);
		}	
		
		//Validación FkInsumo
		if(bodegaDetalle.getFkInsumo() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
					HttpStatus.BAD_REQUEST);
		}	
		
		try {
			if (bodegaDetalleRepository.findByNombre(bodegaDetalle.getCantidad()) == null) {
		
		Optional<Bodega> bodega = bodegaRepository.findById(bodegaDetalle.getFkBodega());
		//Validación Bodega
		if(bodega.get() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		Optional<Insumo> insumo = insumoRepository.findById(bodegaDetalle.getFkInsumo());
		//Validación Insumo
		if(insumo.get() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		Optional<Proveedor> proveedor = proveedorRepository.findById(bodegaDetalle.getFkProveedor());
		//Validación Proveedor
		if(proveedor.get() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		Optional<TipoMovimiento> tipoMovimiento = tipoMovimientoRepository.findById(bodegaDetalle.getFkTipoMovimiento());
		//Validación tipo Movimiento
		if(tipoMovimiento.get() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
					HttpStatus.BAD_REQUEST);
		}
	
		
		if(bodega.get()!=null) {
		
			bodegaDetalle.setBodega(bodega.get());
			bodegaDetalle.setInsumo(insumo.get());
			bodegaDetalle.setProveedor(proveedor.get());
			bodegaDetalle.setTipoMovimiento(tipoMovimiento.get());
		
			bodegaDetalle.getIdBodegaDetalle();
			bodegaDetalle.getCantidad();
			bodegaDetalle.setEstado(1l);
			bodegaDetalle.getCreatedAt();
			
			bodegaDetalleRepository.save(bodegaDetalle);
			
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NUEVO, 200),
					HttpStatus.OK);					
		}else {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 204),
					HttpStatus.NO_CONTENT);					
		}
		

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
	
	@GetMapping(path = "/get/{id_bodega_detalle}", name = "BodegaDetalle: " + NameMethods.MENSAJE_GET_BY_ID)
	public ResponseEntity<?> getById(@PathVariable(value = "bodegaDetalle") Long idBodegaDetalle) {
		try {
			BodegaDetalle bodegaDetalle = bodegaDetalleRepository.findOneActive(idBodegaDetalle);
			if (bodegaDetalle == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			return ResponseEntity.ok().body(bodegaDetalle);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}

	
	 /**
	  * Permite actualizar la informacion de un determinado registro
	  *	@param id es el identificador del registro
	  *	@param unidadMedidaDetails contiene el objeto nuevo a actualizar
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve objeto jsson con el estado final del proceso
	  */	
	@PutMapping(path = "/update/{id_bodega_detalle}", name = "BodegaDetalle: " + NameMethods.MENSAJE_UPDATE)
	public ResponseEntity<?> update(@PathVariable(value = "id_bodega_detalle") Long idBodegaDetalle, @Valid @RequestBody BodegaDetalle bodegaDetalleDetails) {
		try {

			Optional<BodegaDetalle> bodegaDetalle = bodegaDetalleRepository.findById(idBodegaDetalle);
			
			if (bodegaDetalle.get() == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			bodegaDetalle.get().setCantidad(bodegaDetalleDetails.getCantidad());
	
			bodegaDetalleRepository.save(bodegaDetalle.get());
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
	
	@DeleteMapping(path = "/delete/{id_bodega_detalle}", name = "BodegaDetalle: " + NameMethods.MENSAJE_DELETE)
	public ResponseEntity<?> delete(@PathVariable(value = "id_bodega_detalle") Long idBodegaDetalle) {
		try {
			BodegaDetalle bodegaDetalle = bodegaDetalleRepository.findOneActive(idBodegaDetalle);
			if (bodegaDetalle == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			
			bodegaDetalle.setEstado(0L);
	
			bodegaDetalleRepository.save(bodegaDetalle);
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_ELIMINADO, 200), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	}
}

