package cl.duoc.sigloxxi.controller;
/** Representa el controllador de la entidad
 * @author Andres Valencia Nuñez.
 * @version 0.1
 * @since 1.0
*/
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import cl.duoc.sigloxxi.constants.Constants;
import cl.duoc.sigloxxi.constants.NameMethods;
import cl.duoc.sigloxxi.entity.ApiRuta;
import cl.duoc.sigloxxi.entity.MesaDisponibilidad;
import cl.duoc.sigloxxi.entity.Permiso;
import cl.duoc.sigloxxi.entity.Producto;
import cl.duoc.sigloxxi.entity.Sesion;
import cl.duoc.sigloxxi.entity.Usuario;
import cl.duoc.sigloxxi.model.CustomResponse;
import cl.duoc.sigloxxi.model.Login;
import cl.duoc.sigloxxi.model.MesaDisponibilidadResponse;
import cl.duoc.sigloxxi.repository.ApiRutaRepository;
import cl.duoc.sigloxxi.repository.MesaDisponibilidadRepository;
import cl.duoc.sigloxxi.repository.PermisoRepository;
import cl.duoc.sigloxxi.repository.SesionRepository;
import cl.duoc.sigloxxi.repository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth-controller")
public class AuthController {

	ObjectMapper mapper = new ObjectMapper();
	
	/** CARGA DE REPOSITORYS */
	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	SesionRepository sesionRepository;

	@Autowired
	ApiRutaRepository apiRutaRepository;
	
	@Autowired
	PermisoRepository permisoRepository;
	
	@Autowired
	MesaDisponibilidadRepository mesaDisponibilidadRepository;	
	 /**
	  * valida que el token aun pueda utilizarse para realizar peticiones
	  * @throws Ante cualquier excepcion mostrara mensaje de respuesta erronea.
	  * @return retorna un objeto json con el token
	  */	
	@PostMapping(path = "/login", name = "Auth: " + NameMethods.MENSAJE_LOGIN)
	public ResponseEntity<?> login(HttpServletResponse response, @Valid @RequestBody Login loginForm)
			throws IOException {
		String token = null;
		Usuario usuario = usuarioRepository.findByNombreUsuario(loginForm.getNombreUsuario());
		
		if (usuario != null) {
			
			List<MesaDisponibilidadResponse> listaMesas = new ArrayList<>();
			
			if(usuario.getFuncionario()!=null) {
				List<MesaDisponibilidad> listaMesasResponse = mesaDisponibilidadRepository.getAllDisponiblesFuncionario(usuario.getFuncionario().getId());
				
				if(!listaMesasResponse.isEmpty()) {
					
					List<MesaDisponibilidadResponse> listaMesasDisponibles = new ArrayList<>();
					
					for (MesaDisponibilidad mesaDisponibilidad : listaMesasResponse) {
					
						MesaDisponibilidadResponse mesa = new MesaDisponibilidadResponse();
						
						mesa.setIdMesaDisponiblidad(mesaDisponibilidad.getIdMesaDisponibilidad().toString());
						mesa.setNombreMesa(mesaDisponibilidad.getMesa().getNombre());
						
						
						listaMesasDisponibles.add(mesa);
					}
					
					if(!listaMesasDisponibles.isEmpty()) {
						listaMesas = listaMesasDisponibles;
					}
					
				}
				
			}
			
			if (BCrypt.checkpw(loginForm.getContrasena(), usuario.getContrasena())) {
				token = Jwts.builder().setSubject(usuario.getId().toString())
						.claim("roles", usuario.getRol())
						.claim("nombreUsuario", usuario.getNombre())
						.claim("funcionario", usuario.getFuncionario())
						.claim("mesas", listaMesas)
						.setIssuedAt(new Date(new Date().getTime() + (86400000 * 5)))
						.signWith(SignatureAlgorithm.HS256, "secret")
						.compact();
				
				ObjectNode res = mapper.createObjectNode();
				res.put("token", token);				
/*


				Sesion sesion = new Sesion();
				sesion.setToken(token);
				sesion.setUsuario(usuario);
				sesion.setActivo(1L);

				List<Sesion> sesiones = sesionRepository.findAllByIdUsuario(usuario.getId());

				if (sesiones.size() > 0) {
					for (int i = 0; i < sesiones.size(); i++) {
						Sesion sesionCambio = sesiones.get(i);
						sesionCambio.setActivo(0L);
						sesionRepository.save(sesionCambio);
					}
				}
				sesionRepository.save(sesion);
*/
				return ResponseEntity.ok(res);
			} else {
				return new ResponseEntity<Object>(new CustomResponse("Usuario o contraseña incorrectos.", 400),
						HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<Object>(new CustomResponse("Usuario o contraseña incorrectos.", 400),
					HttpStatus.BAD_REQUEST);
		}

	}
	
	 /**
	  * Muestra la lista de todlas rutas disponibles en el sistema
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */
	@GetMapping(path = "/list-rout-active", name = "Auth: " + NameMethods.MENSAJE_LISTAR_ACTIVO)
	public ResponseEntity<?> getAllActive() {
		try {
			

			List<ApiRuta> listTemp = apiRutaRepository.findAllActive();
			if (!listTemp.isEmpty()) {
				return new ResponseEntity<Object>(listTemp, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 204),
						HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
			
	}
	
	 /**
	  * Muestra la lista de todlas rutas disponibles en el sistema
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */
	public Permiso validaPermiso(Long idUsuario, String ruta) {
		
		Permiso permisoResponse = new Permiso();
		
		try {
			
			permisoResponse = permisoRepository.findByUsuarioRuta(idUsuario, ruta);
			System.out.println(permisoResponse.toString());

		} catch (Exception e) {
			System.out.println(e);
			
		}
		return permisoResponse;
			
	}	

}
