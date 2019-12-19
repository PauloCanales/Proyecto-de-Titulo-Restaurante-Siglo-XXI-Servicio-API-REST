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
import cl.duoc.sigloxxi.entity.TipoFuncionario;
import cl.duoc.sigloxxi.model.CustomResponse;
import cl.duoc.sigloxxi.repository.TipoFuncionarioRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tipo-funcionario-controller")
public class TipoFuncionarioController {
	 /**
	  * Realiza la carga del repository para poder hacer consultas SQL
	  */
	@Autowired
	TipoFuncionarioRepository tipoFuncionarioRepository;
	
	
	 /**
	  * Muestra la lista de registros disponibles en el sistema.
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */
	@GetMapping(path = "/list", name = "TipoFuncionario: " + NameMethods.MENSAJE_LISTAR)
	public ResponseEntity<?> getAll() {
		try {
			List<TipoFuncionario> listTemp = tipoFuncionarioRepository.findAll();
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
	@GetMapping(path = "/list-active", name = "TipoFuncionario: " + NameMethods.MENSAJE_LISTAR_ACTIVO)
	public ResponseEntity<?> getAllActive() {
		try {
			

			List<TipoFuncionario> listTemp = tipoFuncionarioRepository.findAllActive();
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
	@GetMapping(path = "/list-inactive", name = "TipoFuncionario: " + NameMethods.MENSAJE_LISTAR_INACTIVO)

	public ResponseEntity<?> getAllInactive() {
		
		try {
		
			List<TipoFuncionario> listTemp = tipoFuncionarioRepository.findAllInactive();
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
	  *	@param tipoFuncionario contiene el objeto nuevo que vamos a registrar
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */

	@PostMapping(path = "/new", name = "TipoFuncionario: " + NameMethods.MENSAJE_NEW)
	public ResponseEntity<?> create(@Valid @RequestBody TipoFuncionario tipoFuncionario) {
		
		//Validación Nombre
		if(tipoFuncionario.getNombre() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.VALID_NOMBRE_REQUERIDO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		try {
			if (tipoFuncionarioRepository.findByNombre(tipoFuncionario.getNombre()) == null) {
	
				tipoFuncionario.setEstado(1L);
				
				tipoFuncionarioRepository.save(tipoFuncionario);
				
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
	
	@GetMapping(path = "/get/{id}", name = "TipoFuncionario: " + NameMethods.MENSAJE_GET_BY_ID)
	public ResponseEntity<?> getById(@PathVariable(value = "id") Long idTipoFuncionario) {
		try {
			TipoFuncionario tipoFuncionario = tipoFuncionarioRepository.findOneActive(idTipoFuncionario);
			if (tipoFuncionario == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			return ResponseEntity.ok().body(tipoFuncionario);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}

	
	 /**
	  * Permite actualizar la informacion de un determinado registro
	  *	@param id es el identificador del registro
	  *	@param tipoFuncionarioDetails contiene el objeto nuevo a actualizar
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve objeto jsson con el estado final del proceso
	  */	
	/*-------------por ahi cambie el int idTipoFuncionario--------------------*/
	@PutMapping(path = "/update/{id}", name = "TipoFuncionario: " + NameMethods.MENSAJE_UPDATE)
	public ResponseEntity<?> update(@PathVariable(value = "id") Long idTipoFuncionario, @Valid @RequestBody TipoFuncionario tipoFuncionarioDetails) {
		try {

			Optional<TipoFuncionario> tipoFuncionario = tipoFuncionarioRepository.findById(idTipoFuncionario);
			
			if (tipoFuncionario.get() == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			tipoFuncionario.get().setNombre(tipoFuncionarioDetails.getNombre());
	
			tipoFuncionarioRepository.save(tipoFuncionario.get());
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
	
	@DeleteMapping(path = "/delete/{id}", name = "TipoFuncionario: " + NameMethods.MENSAJE_DELETE)
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long idTipoFuncionario) {
		try {
			TipoFuncionario tipoFuncionario = tipoFuncionarioRepository.findOneActive(idTipoFuncionario);
			if (tipoFuncionario == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			
			tipoFuncionario.setEstado(0L);
	
			tipoFuncionarioRepository.save(tipoFuncionario);
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_ELIMINADO, 200), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	}
}
