package cl.duoc.sigloxxi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import cl.duoc.sigloxxi.constants.Constants;
import cl.duoc.sigloxxi.constants.NameMethods;
import cl.duoc.sigloxxi.entity.Funcionario;
import cl.duoc.sigloxxi.entity.Rol;
import cl.duoc.sigloxxi.entity.TipoProducto;
import cl.duoc.sigloxxi.entity.Usuario;
import cl.duoc.sigloxxi.model.CustomResponse;
import cl.duoc.sigloxxi.model.UsuarioResponse;
import cl.duoc.sigloxxi.repository.FuncionarioRepository;
import cl.duoc.sigloxxi.repository.RolRepository;
import cl.duoc.sigloxxi.repository.UsuarioRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/usuario-controller")
public class UsuarioController {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	RolRepository rolRepository;
	
	@Autowired
	FuncionarioRepository funcionarioRepository;
	
	@PostMapping(path = "/new", name = "Usuario: " + NameMethods.MENSAJE_NEW)
	public ResponseEntity<?> create(@RequestBody Usuario usuario, @RequestHeader HttpHeaders headers) {

		if(usuario.getNombre() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.VALID_NOMBRE_REQUERIDO, 400),
					HttpStatus.BAD_REQUEST);
		}		
		
		if(usuario.getContrasena() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_SIN_CLAVE, 400),
					HttpStatus.BAD_REQUEST);
		}				
		if(usuario.getFkRol() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		if(usuario.getFkFuncionario() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		
		
		Optional<Rol> rol = rolRepository.findById(usuario.getFkRol());
		
		System.out.println(rol.get());
		
		if(rol.get() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		Optional<Funcionario> funcionario = funcionarioRepository.findById(usuario.getFkFuncionario());
		
		if(funcionario.get() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
					HttpStatus.BAD_REQUEST);
		}		
		
			String pw_hash = BCrypt.hashpw(usuario.getContrasena(), BCrypt.gensalt(10));
			
			Usuario nuevoUsuario = new Usuario();
			
			nuevoUsuario.setContrasena(pw_hash);
			nuevoUsuario.setNombre(usuario.getNombre());
			nuevoUsuario.setRol(rol.get());
			nuevoUsuario.setFuncionario(funcionario.get());
			nuevoUsuario.setActivo(1L);
			
			usuarioRepository.save(nuevoUsuario);
			
			//usuarioRepository.flush();
			
			
			return new ResponseEntity<Object>(
					new CustomResponse(Constants
							.mensajeNuevoRegistro(this.getClass().getSimpleName().split("Controller")[0].trim()), 200),
					HttpStatus.OK);

	}


	@GetMapping(path = "/list", name = "Usuario: " + NameMethods.MENSAJE_LISTAR)
	public ResponseEntity<?> getAll() {
		try {
			List<Usuario> listTemp = usuarioRepository.findAll();
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
	
	@GetMapping(path = "/list-active", name = "Usuario: " + NameMethods.MENSAJE_LISTAR_ACTIVO)
	public ResponseEntity<?> getAllActive() {
		try {
			

			List<Usuario> listTemp = usuarioRepository.findAllActive();
			if (!listTemp.isEmpty()) {
				
				List<UsuarioResponse> listaUsuarioResponse = new ArrayList<>(); 
				
				for (Usuario usuario : listTemp) {
					UsuarioResponse usuarioResponse = new UsuarioResponse();
					
					usuarioResponse.setId(usuario.getId().toString());
					usuarioResponse.setNombre(usuario.getNombre());
					usuarioResponse.setIdRol(usuario.getRol().getId().toString());
					//usuarioResponse.setIdFuncionario(usuario.getFuncionario().getId().toString());
					//usuarioResponse.setNombreFuncionario(usuario.getFuncionario().getNombre());
					usuarioResponse.setNombreRol(usuario.getRol().getNombre());
					
					
					listaUsuarioResponse.add(usuarioResponse);
				}
				
				
				
				return new ResponseEntity<Object>(listaUsuarioResponse, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 204),
						HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
			
	}
	
	@GetMapping(path = "/list-inactive", name = "Usuario: " + NameMethods.MENSAJE_LISTAR_INACTIVO)

	public ResponseEntity<?> getAllInactive() {
		
		try {
		
			List<Usuario> listTemp = usuarioRepository.findAllInactive();
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
	
	@GetMapping(path = "/get/{id_usuario}", name = "Usuario: " + NameMethods.MENSAJE_GET_BY_ID)
	public ResponseEntity<?> getById(@PathVariable(value = "usuario") Long idUsuario) {
		try {
			Usuario usuario = usuarioRepository.findOneActive(idUsuario);
			if (usuario == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			return ResponseEntity.ok().body(usuario);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}

	
	@PutMapping(path = "/update/{id_usuario}", name = "Usuario: " + NameMethods.MENSAJE_UPDATE)
	public ResponseEntity<?> update(@PathVariable(value = "id_usuario") Long idUsuario, @Valid @RequestBody Usuario usuarioDetails) {
		try {

			Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
			
			if (usuario.get() == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			
			if(usuario.get().getNombre() == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.VALID_NOMBRE_REQUERIDO, 400),
						HttpStatus.BAD_REQUEST);
			}		
			
			if(usuario.get().getContrasena() == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_SIN_CLAVE, 400),
						HttpStatus.BAD_REQUEST);
			}				

			Optional<Rol> rol = rolRepository.findById(usuario.get().getFkRol());
			
			if(rol == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
						HttpStatus.BAD_REQUEST);
			}
			
			Optional<Funcionario> funcionario = funcionarioRepository.findById(usuario.get().getFkFuncionario());
			
			if(funcionario == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
						HttpStatus.BAD_REQUEST);
			}				
			
			usuario.get().setNombre(usuarioDetails.getNombre());
			usuario.get().setContrasena(usuarioDetails.getContrasena());
			usuario.get().setRol(rol.get());
			usuario.get().setFuncionario(funcionario.get());
	
		    usuarioRepository.save(usuario.get());
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_ACTUALIZADO, 200),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
	}

	
	@DeleteMapping(path = "/delete/{idUsuario}", name = "Usuario: " + NameMethods.MENSAJE_DELETE)
	public ResponseEntity<?> delete(@PathVariable(value = "idUsuario") Long idUsuario) {
		try {
			Usuario usuario = usuarioRepository.findOneActive(idUsuario);
			if (usuario == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			
			usuario.setActivo(0L);
	
			usuarioRepository.save(usuario);
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_ELIMINADO, 200), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	}
	
}