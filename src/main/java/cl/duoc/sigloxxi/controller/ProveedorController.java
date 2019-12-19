package cl.duoc.sigloxxi.controller;
/** Representa una Unidad de Medida para los insumos.
 * @author Tomás Durán
 * @version 0.1
 * @since 1.0
*/
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.sigloxxi.constants.Constants;
import cl.duoc.sigloxxi.constants.NameMethods;
import cl.duoc.sigloxxi.entity.Proveedor;
import cl.duoc.sigloxxi.model.CustomResponse;
import cl.duoc.sigloxxi.repository.ProveedorRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/proveedor-controller")
public class ProveedorController {
	 /**
	  * Realiza la carga del repository para poder hacer consultas SQL
	  */
	@Autowired
	ProveedorRepository proveedorRepository;
	
	
	 /**
	  * Muestra la lista de registros disponibles en el sistema.
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */
	@GetMapping(path = "/list", name = "Proveedor: " + NameMethods.MENSAJE_LISTAR)
	public ResponseEntity<?> getAll() {
		try {
			List<Proveedor> listTemp = proveedorRepository.findAll();
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
	@GetMapping(path = "/list-active", name = "Proveedor: " + NameMethods.MENSAJE_LISTAR_ACTIVO)
	public ResponseEntity<?> getAllActive() {
		try {
			

			List<Proveedor> listTemp = proveedorRepository.findAllActive();
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
	@GetMapping(path = "/list-inactive", name = "Proveedor: " + NameMethods.MENSAJE_LISTAR_INACTIVO)

	public ResponseEntity<?> getAllInactive() {
		
		try {
		
			List<Proveedor> listTemp = proveedorRepository.findAllInactive();
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
	  *	@param proveedor contiene el objeto nuevo que vamos a registrar
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */

	@PostMapping(path = "/new", name = "Proveedor: " + NameMethods.MENSAJE_NEW)
	public ResponseEntity<?> create(@Valid @RequestBody Proveedor proveedor) {
		
		//Validación Nombre
		if(proveedor.getNombre() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.VALID_NOMBRE_REQUERIDO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		//Validación Email
		if(proveedor.getEmail() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.VALID_CORREO_REQUERIDO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		//Validación Teléfono
		if(proveedor.getTelefono() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.VALID_FONO_REQUERIDO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		try {
			if (proveedorRepository.findByNombre(proveedor.getNombre()) == null) {
	
				proveedor.setEstado(1L);
				
				proveedorRepository.save(proveedor);
				
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
	
	@GetMapping(path = "/get/{id}", name = "Proveedor: " + NameMethods.MENSAJE_GET_BY_ID)
	public ResponseEntity<?> getById(@PathVariable(value = "idProveedor") Long idProveedor) {
		try {
			Proveedor proveedor = proveedorRepository.findOneActive(idProveedor);
			if (proveedor == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			return ResponseEntity.ok().body(proveedor);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}

	
	 /**
	  * Permite actualizar la informacion de un determinado registro
	  *	@param id es el identificador del registro
	  *	@param proveedorDetails contiene el objeto nuevo a actualizar
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve objeto jsson con el estado final del proceso
	  */	
	/*-------------por ahi cambie el int idProveedor--------------------*/
	@PutMapping(path = "/update/{id}", name = "Proveedor: " + NameMethods.MENSAJE_UPDATE)
	public ResponseEntity<?> update(@PathVariable(value = "id") Long idProveedor, @Valid @RequestBody Proveedor proveedorDetails) {
		try {

			Optional<Proveedor> proveedor = proveedorRepository.findById(idProveedor);
			
			if (proveedor.get() == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			proveedor.get().setNombre(proveedorDetails.getNombre());
			proveedor.get().setTelefono(proveedorDetails.getTelefono());
			proveedor.get().setEmail(proveedorDetails.getEmail());
	
			proveedorRepository.save(proveedor.get());
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
	
	@DeleteMapping(path = "/delete/{id}", name = "Proveedor: " + NameMethods.MENSAJE_DELETE)
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long idProveedor) {
		try {
			Proveedor proveedor = proveedorRepository.findOneActive(idProveedor);
			if (proveedor == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			
			proveedor.setEstado(0L);
	
			proveedorRepository.save(proveedor);
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_ELIMINADO, 200), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	}
}
