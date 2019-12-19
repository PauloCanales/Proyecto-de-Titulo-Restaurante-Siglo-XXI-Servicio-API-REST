package cl.duoc.sigloxxi.controller;
/** Representa una Ingrediente para los insumos.
 * @author Andres Valencia Nuñez.
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
import cl.duoc.sigloxxi.entity.Ingrediente;
import cl.duoc.sigloxxi.entity.Insumo;
import cl.duoc.sigloxxi.entity.Receta;
import cl.duoc.sigloxxi.entity.UnidadMedida;
import cl.duoc.sigloxxi.model.CustomResponse;
import cl.duoc.sigloxxi.repository.IngredienteRepository;
import cl.duoc.sigloxxi.repository.InsumoRepository;
import cl.duoc.sigloxxi.repository.RecetaRepository;
import cl.duoc.sigloxxi.repository.UnidadMedidaRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/ingrediente-controller")
public class IngredienteController {
	 /**
	  * Realiza la carga del repository para poder hacer consultas SQL
	  */
	@Autowired
	IngredienteRepository ingredienteRepository;
	
	@Autowired
	RecetaRepository recetaRepository;	
	
	@Autowired
	UnidadMedidaRepository unidadMedidaRepository;
	
	@Autowired
	InsumoRepository insumoRepository;
	
	 /**
	  * Muestra la lista de registros disponibles en el sistema.
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */
	@GetMapping(path = "/list", name = "Ingrediente: " + NameMethods.MENSAJE_LISTAR)
	public ResponseEntity<?> getAll() {
		try {
			List<Ingrediente> listTemp = ingredienteRepository.findAll();
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
	@GetMapping(path = "/list-active", name = "Ingrediente: " + NameMethods.MENSAJE_LISTAR_ACTIVO)
	public ResponseEntity<?> getAllActive() {
		try {
			

			List<Ingrediente> listTemp = ingredienteRepository.findAllActive();
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
	@GetMapping(path = "/list-inactive", name = "Ingrediente: " + NameMethods.MENSAJE_LISTAR_INACTIVO)

	public ResponseEntity<?> getAllInactive() {
		
		try {
		
			List<Ingrediente> listTemp = ingredienteRepository.findAllInactive();
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
	  *	@param ingrediente contiene el objeto nuevo que vamos a registrar
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */

	@PostMapping(path = "/new", name = "Ingrediente: " + NameMethods.MENSAJE_NEW)
	public ResponseEntity<?> create(@Valid @RequestBody Ingrediente ingrediente) {
		
		//Validación Nombre
		if(ingrediente.getNombre() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.VALID_NOMBRE_REQUERIDO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		//Validación Cantidad
		if(ingrediente.getCantidad() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.VALID_CANTIDAD_REQUERIDO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		//Validación FkReceta
		if(ingrediente.getFkReceta() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		//Validación FkUnidadMedida
		if(ingrediente.getFkUnidadMedida() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		//Validación FkInsumo
		if(ingrediente.getFkInsumo() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		try {
			if (ingredienteRepository.findByNombre(ingrediente.getNombre()) == null) {
	
				
				Optional<Receta> receta = recetaRepository.findById(ingrediente.getFkReceta());
				//Validación Receta
				if(receta.get() == null) {
					return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
							HttpStatus.BAD_REQUEST);
				}
				
				Optional<UnidadMedida> unidadMedida = unidadMedidaRepository.findById(ingrediente.getFkUnidadMedida());
				//Validación Unidad Medida
				if(unidadMedida.get() == null) {
					return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
							HttpStatus.BAD_REQUEST);
				}
				
				Optional<Insumo> insumo = insumoRepository.findById(ingrediente.getFkInsumo());
				//Validación Insumo
				if(insumo.get() == null) {
					return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
							HttpStatus.BAD_REQUEST);
				}
				
				
				
				if(receta.get()!=null) {
					
					ingrediente.setReceta(receta.get());
					ingrediente.setEstado(1L);
					
					ingredienteRepository.save(ingrediente);
					
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
	
	@GetMapping(path = "/get/{id}", name = "Ingrediente: " + NameMethods.MENSAJE_GET_BY_ID)
	public ResponseEntity<?> getById(@PathVariable(value = "id") Long idIngrediente) {
		try {
			Ingrediente ingrediente = ingredienteRepository.findOneActive(idIngrediente);
			if (ingrediente == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			return ResponseEntity.ok().body(ingrediente);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}

	
	 /**
	  * Permite actualizar la informacion de un determinado registro
	  *	@param id es el identificador del registro
	  *	@param ingredienteDetails contiene el objeto nuevo a actualizar
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve objeto jsson con el estado final del proceso
	  */	
	@PutMapping(path = "/update/{id}", name = "Ingrediente: " + NameMethods.MENSAJE_UPDATE)
	public ResponseEntity<?> update(@PathVariable(value = "id") Long idIngrediente, @Valid @RequestBody Ingrediente ingredienteDetails) {
		try {

			Optional<Ingrediente> ingrediente = ingredienteRepository.findById(idIngrediente);
			
			if (ingrediente.get() == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			ingrediente.get().setNombre(ingredienteDetails.getNombre());
	
			ingredienteRepository.save(ingrediente.get());
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
	
	@DeleteMapping(path = "/delete/{id}", name = "Ingrediente: " + NameMethods.MENSAJE_DELETE)
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long idIngrediente) {
		try {
			Ingrediente ingrediente = ingredienteRepository.findOneActive(idIngrediente);
			if (ingrediente == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			
			ingrediente.setEstado(0L);
	
			ingredienteRepository.save(ingrediente);
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_ELIMINADO, 200), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	}
}
