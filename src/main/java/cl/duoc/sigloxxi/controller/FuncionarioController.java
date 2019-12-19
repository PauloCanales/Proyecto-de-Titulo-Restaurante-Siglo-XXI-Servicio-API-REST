package cl.duoc.sigloxxi.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
/** Representa un Funcionario
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
import cl.duoc.sigloxxi.entity.Funcionario;
import cl.duoc.sigloxxi.entity.TipoFuncionario;
import cl.duoc.sigloxxi.model.CustomResponse;
import cl.duoc.sigloxxi.repository.FuncionarioRepository;
import cl.duoc.sigloxxi.repository.TipoFuncionarioRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/funcionario-controller")
public class FuncionarioController {
	 /**
	  * Realiza la carga del repository para poder hacer consultas SQL
	  */
	@Autowired
	FuncionarioRepository funcionarioRepository;
	
	@Autowired
	TipoFuncionarioRepository tipoFuncionarioRepository;	
	
	 /**
	  * Muestra la lista de registros disponibles en el sistema.
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */
	@GetMapping(path = "/list", name = "Funcionario: " + NameMethods.MENSAJE_LISTAR)
	public ResponseEntity<?> getAll() {
		try {
			List<Funcionario> listTemp = funcionarioRepository.findAll();
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
	@GetMapping(path = "/list-active", name = "Funcionario: " + NameMethods.MENSAJE_LISTAR_ACTIVO)
	public ResponseEntity<?> getAllActive() {
		try {
			

			List<Funcionario> listTemp = funcionarioRepository.findAllActive();
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
	
	@GetMapping(path = "/list-active-by-type/{idTipoFuncionario}", name = "TipoFuncionario: " + NameMethods.MENSAJE_LISTAR_ACTIVO)
	public ResponseEntity<?> getAllActiveByType(@PathVariable(value = "idTipoFuncionario") Long idTipoFuncionario) {
		try {
			

			List<Funcionario> listTemp = funcionarioRepository.findByType(idTipoFuncionario);
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
	@GetMapping(path = "/list-inactive", name = "Funcionario: " + NameMethods.MENSAJE_LISTAR_INACTIVO)

	public ResponseEntity<?> getAllInactive() {
		
		try {
		
			List<Funcionario> listTemp = funcionarioRepository.findAllInactive();
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
	  *	@param funcionario contiene el objeto nuevo que vamos a registrar
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */

	@PostMapping(path = "/new", name = "Funcionario: " + NameMethods.MENSAJE_NEW)
	public ResponseEntity<?> create(@Valid @RequestBody Funcionario funcionario) {
		
		//Validación Teléfono
		if(funcionario.getTelefono() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.VALID_VALOR_REQUERIDO, 400),
					HttpStatus.BAD_REQUEST);
		}		
		
		//Validación Nombre
		if(funcionario.getNombre() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.VALID_NOMBRE_REQUERIDO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		//Validación Apellido Paterno
		if(funcionario.getApPaterno() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.VALID_APATERNO_REQUERIDO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		//Validación Apellido Materno
		if(funcionario.getApMaterno() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.VALID_AMATERNO_REQUERIDO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		//Validación RUT
		if(funcionario.getRut() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.VALID_RUT_REQUERIDO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		//Validación Domicilio
		if(funcionario.getDomicilio() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.VALID_DOMICILIO_REQUERIDO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		//Validación Correo
		if(funcionario.getCorreo() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.VALID_CORREO_REQUERIDO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		//Validación FkTipoFuncionario
		if(funcionario.getFkTipoFuncionario() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		try {
			if (funcionarioRepository.findByNombre(funcionario.getNombre()) == null) {
	
				
				Optional<TipoFuncionario> tipoFuncionario = tipoFuncionarioRepository.findById(funcionario.getFkTipoFuncionario());
				//Validación Tipo Funcionario
				if(tipoFuncionario.get() == null) {
					return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
							HttpStatus.BAD_REQUEST);
				}
				
				if(tipoFuncionario.get()!=null) {
					
					funcionario.setTipoFuncionario(tipoFuncionario.get());
					funcionario.setEstado(1L);
					
					
					funcionarioRepository.save(funcionario);
					/*
{
	"nombre":"fffff", 
	"apPaterno":"333", 
	"apMaterno":"eeee", 
	"rut":"1", 
	"domicilio":"ssss", 
	"correo":"a@a.com",
	"fechaNac":"2000-01-02T00:00:00.000",
	"fkTipoFuncionario" : "136"
}					 
					 */
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
			System.out.println(e);
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
	
	@GetMapping(path = "/get/{id}", name = "Funcionario: " + NameMethods.MENSAJE_GET_BY_ID)
	public ResponseEntity<?> getById(@PathVariable(value = "id") Long idFuncionario) {
		try {
			Funcionario funcionario = funcionarioRepository.findOneActive(idFuncionario);
			if (funcionario == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			return ResponseEntity.ok().body(funcionario);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}

	
	 /**
	  * Permite actualizar la informacion de un determinado registro
	  *	@param id es el identificador del registro
	  *	@param funcionarioDetails contiene el objeto nuevo a actualizar
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve objeto jsson con el estado final del proceso
	  */	
	@PutMapping(path = "/update/{id}", name = "Funcionario: " + NameMethods.MENSAJE_UPDATE)
	public ResponseEntity<?> update(@PathVariable(value = "id") Long idFuncionario, @Valid @RequestBody Funcionario funcionarioDetails) {
		try {

			Optional<Funcionario> funcionario = funcionarioRepository.findById(idFuncionario);
			Optional<TipoFuncionario> tipoFuncionario = tipoFuncionarioRepository.findById(funcionarioDetails.getFkTipoFuncionario());
			
			if (funcionario.get() == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			if (tipoFuncionario.get() == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			
			funcionario.get().setNombre(funcionarioDetails.getNombre());
			funcionario.get().setApPaterno(funcionarioDetails.getApPaterno());
			funcionario.get().setApMaterno(funcionarioDetails.getApMaterno());
			funcionario.get().setRut(funcionarioDetails.getRut());
			funcionario.get().setDomicilio(funcionarioDetails.getDomicilio());
			funcionario.get().setCorreo(funcionarioDetails.getCorreo());
			funcionario.get().setTelefono(funcionarioDetails.getTelefono());
			funcionario.get().setTipoFuncionario(tipoFuncionario.get());
	
			funcionarioRepository.save(funcionario.get());
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
	
	@DeleteMapping(path = "/delete/{id}", name = "Funcionario: " + NameMethods.MENSAJE_DELETE)
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long idFuncionario) {
		try {
			Funcionario funcionario = funcionarioRepository.findOneActive(idFuncionario);
			if (funcionario == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			
			funcionario.setEstado(0L);
	
			funcionarioRepository.save(funcionario);
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_ELIMINADO, 200), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	}
}
