package cl.duoc.sigloxxi.controller;
import java.util.ArrayList;
/** Representa una Receta.
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
import cl.duoc.sigloxxi.model.IngredienteRequest;
import cl.duoc.sigloxxi.model.IngredienteResponse;
import cl.duoc.sigloxxi.model.RecetaRequest;
import cl.duoc.sigloxxi.model.RecetaResponse;
import cl.duoc.sigloxxi.repository.IngredienteRepository;
import cl.duoc.sigloxxi.repository.InsumoRepository;
import cl.duoc.sigloxxi.repository.RecetaRepository;
import cl.duoc.sigloxxi.repository.UnidadMedidaRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/receta-controller")
public class RecetaController {
	 /**
	  * Realiza la carga del repository para poder hacer consultas SQL
	  */
	@Autowired
	RecetaRepository recetaRepository;
	
	@Autowired
	IngredienteRepository ingredienteRepository;
	
	@Autowired
	UnidadMedidaRepository unidadMedidaRepository;
	@Autowired
	InsumoRepository insumoRepository;
	
	
	 /**
	  * Muestra la lista de registros disponibles en el sistema.
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */
	@GetMapping(path = "/list", name = "Receta: " + NameMethods.MENSAJE_LISTAR)
	public ResponseEntity<?> getAll() {
		try {
			List<Receta> listTemp = recetaRepository.findAll();
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
	@GetMapping(path = "/list-active", name = "Receta: " + NameMethods.MENSAJE_LISTAR_ACTIVO)
	public ResponseEntity<?> getAllActive() {
		try {
			

			List<Receta> listTemp = recetaRepository.findAllActive();
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
	@GetMapping(path = "/list-inactive", name = "Receta: " + NameMethods.MENSAJE_LISTAR_INACTIVO)

	public ResponseEntity<?> getAllInactive() {
		
		try {
		
			List<Receta> listTemp = recetaRepository.findAllInactive();
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
	  *	@param receta contiene el objeto nuevo que vamos a registrar
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */

	@PostMapping(path = "/new", name = "Receta: " + NameMethods.MENSAJE_NEW)
	public ResponseEntity<?> create(@Valid @RequestBody RecetaRequest receta) {
		
		
		try {
			if (recetaRepository.findByNombre(receta.getNombre()) == null) {
	
				List<Ingrediente> listaIngredientes = new ArrayList<>();
				
				for (IngredienteRequest ingrediente : receta.getIngredientes()) {
					if(ingrediente.getNombre() != null) {
						
						UnidadMedida unidadMedida = null;
						
						if(ingrediente.getUnidadMedida() == null){
							return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_INGREDIENTE_SIN_UNIDAD_MEDIDA, 400),
									HttpStatus.BAD_REQUEST);						
						}else {
							unidadMedida = unidadMedidaRepository.findByNombre(ingrediente.getUnidadMedida());
							
							if(unidadMedida == null) {
								return new ResponseEntity<Object>(new CustomResponse(Constants.UNIDAD_MEDIDA_NO_EXISTE, 400),
										HttpStatus.BAD_REQUEST);											
							}

						}
						
						Insumo insumo = null;
						
						if(ingrediente.getInsumo() == null){
							return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_INGREDIENTE_SIN_INSUMO, 400),
									HttpStatus.BAD_REQUEST);						
						}else {
							insumo = insumoRepository.findByNombre(ingrediente.getInsumo());
							
							if(insumo == null) {
								return new ResponseEntity<Object>(new CustomResponse(Constants.INSUMO_MEDIDA_NO_EXISTE, 400),
										HttpStatus.BAD_REQUEST);											
							}

						}
							
						Ingrediente ingredienteNuevo = new Ingrediente();
						
						ingredienteNuevo.setCantidad(ingrediente.getCantidad());
						ingredienteNuevo.setEstado(1L);
						ingredienteNuevo.setNombre(ingrediente.getNombre());
						ingredienteNuevo.setUnidadMedida(unidadMedida);
						ingredienteNuevo.setInsumo(insumo);
						
						listaIngredientes.add(ingredienteNuevo);	
						
					}else {
						return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_INGREDIENTE_SIN_NOMBRE, 400),
								HttpStatus.BAD_REQUEST);						
					}
					
				}
				
				try {
					Receta recetaNueva = new Receta();
					
					recetaNueva.setEstado(1L);
					recetaNueva.setNombre(receta.getNombre());
					recetaNueva.setPreparacion(receta.getPreparacion());
					
					recetaRepository.save(recetaNueva);
					recetaRepository.flush();
					
					for (Ingrediente ingrediente : listaIngredientes) {
						ingrediente.setEstado(1L);
						ingrediente.setReceta(recetaNueva);
						
						ingredienteRepository.save(ingrediente);
						
						
					}
					
					
					
					return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NUEVO, 200),
							HttpStatus.OK);	
				} catch (Exception e) {
					return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
							HttpStatus.INTERNAL_SERVER_ERROR);
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
	
	@GetMapping(path = "/get/{id}", name = "Receta: " + NameMethods.MENSAJE_GET_BY_ID)
	public ResponseEntity<?> getById(@PathVariable(value = "id") Long idReceta) {
		try {
			Receta receta = recetaRepository.findOneActive(idReceta);
			
			if (receta == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			
			RecetaResponse recetaRespose = new RecetaResponse();
			
			recetaRespose.setNombre(receta.getNombre());
			recetaRespose.setPreparacion(receta.getPreparacion());
			
			List<Ingrediente> listaIngrediente = ingredienteRepository.getAllByReceta(receta.getId());
			
			List<IngredienteResponse> listaIngredientesResponse = new ArrayList<>();
			
			for (int i = 0; i < listaIngrediente.size(); i++) {
				IngredienteResponse ingredienteResponse = new IngredienteResponse();
				
				ingredienteResponse.setCantidad(listaIngrediente.get(i).getCantidad());
				ingredienteResponse.setNombre(listaIngrediente.get(i).getNombre());
				ingredienteResponse.setUnidadMedidaId(listaIngrediente.get(i).getUnidadMedida().getId());
				ingredienteResponse.setUnidadMedidaNombre(listaIngrediente.get(i).getUnidadMedida().getNombre());
				ingredienteResponse.setInsumoId(listaIngrediente.get(i).getInsumo().getId());
				ingredienteResponse.setInsumoNombre(listaIngrediente.get(i).getInsumo().getNombre());				
				
				
				listaIngredientesResponse.add(ingredienteResponse);
				
				
			}
			
			recetaRespose.setIngredientes(listaIngredientesResponse);
			
			return ResponseEntity.ok().body(recetaRespose);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}

	
	 /**
	  * Permite actualizar la informacion de un determinado registro
	  *	@param id es el identificador del registro
	  *	@param recetaDetails contiene el objeto nuevo a actualizar
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve objeto jsson con el estado final del proceso
	  */	
	@PutMapping(path = "/update/{id}", name = "Receta: " + NameMethods.MENSAJE_UPDATE)
	public ResponseEntity<?> update(@PathVariable(value = "id") Long idReceta, @Valid @RequestBody RecetaRequest recetaDetails) {
		
		Optional<Receta> receta = recetaRepository.findById(idReceta);
		if(receta.get() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_RECETA_SIN_ID, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		
		try {
			if (recetaRepository.findByNombreAndReceta(recetaDetails.getNombre(),idReceta) == null) {
	
				List<Ingrediente> listaIngredientes = new ArrayList<>();
				
				for (IngredienteRequest ingrediente : recetaDetails.getIngredientes()) {
					if(ingrediente.getNombre() != null) {
						
						UnidadMedida unidadMedida = null;
						
						if(ingrediente.getUnidadMedida() == null){
							return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_INGREDIENTE_SIN_UNIDAD_MEDIDA, 400),
									HttpStatus.BAD_REQUEST);						
						}else {
							unidadMedida = unidadMedidaRepository.findByNombre(ingrediente.getUnidadMedida());
							
							if(unidadMedida == null) {
								return new ResponseEntity<Object>(new CustomResponse(Constants.UNIDAD_MEDIDA_NO_EXISTE, 400),
										HttpStatus.BAD_REQUEST);											
							}

						}
						
						Insumo insumo = null;
						
						if(ingrediente.getInsumo() == null){
							return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_INGREDIENTE_SIN_INSUMO, 400),
									HttpStatus.BAD_REQUEST);						
						}else {
							insumo = insumoRepository.findByNombre(ingrediente.getInsumo());
							
							if(insumo == null) {
								return new ResponseEntity<Object>(new CustomResponse(Constants.INSUMO_MEDIDA_NO_EXISTE, 400),
										HttpStatus.BAD_REQUEST);											
							}

						}						
						
							
						Ingrediente ingredienteNuevo = new Ingrediente();
						
						ingredienteNuevo.setCantidad(ingrediente.getCantidad());
						ingredienteNuevo.setEstado(1L);
						ingredienteNuevo.setNombre(ingrediente.getNombre());
						ingredienteNuevo.setUnidadMedida(unidadMedida);
						ingredienteNuevo.setInsumo(insumo);
						listaIngredientes.add(ingredienteNuevo);	
						
					}else {
						return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_INGREDIENTE_SIN_NOMBRE, 400),
								HttpStatus.BAD_REQUEST);						
					}
					
				}
				
				try {
					
					List<Ingrediente> listaIngredienteOriginal = ingredienteRepository.getAllByReceta(receta.get().getId());
					
					for (Ingrediente ingrediente : listaIngredienteOriginal) {
						ingrediente.setEstado(0L);
						ingredienteRepository.save(ingrediente);
					}
					
					
					receta.get().setNombre(recetaDetails.getNombre());
					receta.get().setPreparacion(recetaDetails.getPreparacion());
					
					recetaRepository.save(receta.get());
					recetaRepository.flush();
					
					for (Ingrediente ingrediente : listaIngredientes) {
						ingrediente.setEstado(1L);
						ingrediente.setReceta(receta.get());
						
						ingredienteRepository.save(ingrediente);
					}
					
					
					return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NUEVO, 200),
							HttpStatus.OK);	
				} catch (Exception e) {
					return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
							HttpStatus.INTERNAL_SERVER_ERROR);
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
	  * Permite desactivar un determinado registro
	  *	@param id es el identificador del registro a desactivar
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve objeto jsson con el estado final del proceso
	  */	
	
	@DeleteMapping(path = "/delete/{id}", name = "Receta: " + NameMethods.MENSAJE_DELETE)
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long idReceta) {
		try {
			Receta receta = recetaRepository.findOneActive(idReceta);
			if (receta == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			
			receta.setEstado(0L);
	
			recetaRepository.save(receta);
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_ELIMINADO, 200), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	}
}
