package cl.duoc.sigloxxi.controller;
import java.util.ArrayList;
/** Representa una Unidad de Medida para los insumos.
 * @author Andres Valencia Nuñez.
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
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import cl.duoc.sigloxxi.constants.Constants;
import cl.duoc.sigloxxi.constants.NameMethods;
import cl.duoc.sigloxxi.entity.Dificultad;
import cl.duoc.sigloxxi.entity.Producto;
import cl.duoc.sigloxxi.entity.Receta;
import cl.duoc.sigloxxi.entity.TipoProducto;
import cl.duoc.sigloxxi.entity.Usuario;
import cl.duoc.sigloxxi.model.CartaMesa;
import cl.duoc.sigloxxi.model.CustomResponse;
import cl.duoc.sigloxxi.repository.DificultadRepository;
import cl.duoc.sigloxxi.repository.ProductoRepository;
import cl.duoc.sigloxxi.repository.RecetaRepository;
import cl.duoc.sigloxxi.repository.TipoProductoRepository;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/producto-controller")
public class ProductoController {
	 /**
	  * Realiza la carga del repository para poder hacer consultas SQL
	  */
	@Autowired
	ProductoRepository productoRepository;
	
	
	@Autowired
	TipoProductoRepository tipoProductoRepository;
	
	
	@Autowired
	DificultadRepository dificultadRepository;
	
	@Autowired
	RecetaRepository recetaRepository;
	
	
	 /**
	  * Muestra la lista de registros disponibles en el sistema.
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */
	@GetMapping(path = "/list", name = "Producto: " + NameMethods.MENSAJE_LISTAR)
	public ResponseEntity<?> getAll() {
		try {
			List<Producto> listTemp = productoRepository.findAll();
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
	@GetMapping(path = "/list-active", name = "Producto: " + NameMethods.MENSAJE_LISTAR_ACTIVO)
	public ResponseEntity<?> getAllActive() {
		try {
			

			List<Producto> listTemp = productoRepository.findAllActive();
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
	  * Muestra la lista de todos los registros activos disponibles en el sistema
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */
	@GetMapping(path = "/list-by-tipo", name = "Producto: " + NameMethods.MENSAJE_LISTAR_ACTIVO)
	public ResponseEntity<?> getAllActiveByType() {
		try {
			
			
			List<CartaMesa> cartaMesa = new ArrayList<>();
			
			List<TipoProducto> listaTipoProducto = tipoProductoRepository.findAllActive(); 
			
			for (TipoProducto tipoProducto : listaTipoProducto) {
				
				List<Producto> listProductos = productoRepository.findAllActiveByType(tipoProducto.getId());
				
				if(listProductos.size()>0) {
					
					CartaMesa response = new CartaMesa();  
					
					response.setTipoProducto(tipoProducto.getNombre());
					response.setProductos(listProductos);
					
					cartaMesa.add(response);
				}
				
			}
			
			
			
			
			if (!cartaMesa.isEmpty()) {
				return new ResponseEntity<Object>(cartaMesa, HttpStatus.OK);
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
	  * Muestra la lista de todos los registros inactivos disponibles en el sistema
	  * @throws Ante cualquier excepcion mostrara el error 500 de respuesta.
	  * @return devuelve un objeto json con la lista de registros
	  */
	@GetMapping(path = "/list-inactive", name = "Producto: " + NameMethods.MENSAJE_LISTAR_INACTIVO)

	public ResponseEntity<?> getAllInactive() {
		
		try {
		
			List<Producto> listTemp = productoRepository.findAllInactive();
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

	@PostMapping(path = "/new", name = "Producto: " + NameMethods.MENSAJE_NEW)
	public ResponseEntity<?> create(@RequestBody Producto producto, @RequestHeader HttpHeaders headers) {
		
		//Validación Nombre
		if(producto.getNombre() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.VALID_NOMBRE_REQUERIDO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		//Validación Descripcion
		if(producto.getDescripcion() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.VALID_DESCRIPCION_REQUERIDO, 400),
					HttpStatus.BAD_REQUEST);
		}		
		
		//Validación Valor
		if(producto.getValor() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.VALID_VALOR_REQUERIDO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		//Validación FkTipoProducto
		if(producto.getFkTipoProducto() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		//Validación FkReceta
		if(producto.getFkReceta() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		//Validación FkDificultad
		if(producto.getFkDificultad() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		
		try {
			if (productoRepository.findByNombre(producto.getNombre()) == null) {
	
		
		Optional<TipoProducto> tipoProducto = tipoProductoRepository.findById(producto.getFkTipoProducto());
		//Validación Tipo Producto
		if(tipoProducto.get() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		Optional<Dificultad> dificultad = dificultadRepository.findById(producto.getFkDificultad());
		//Validación Dificultad
		if(dificultad.get() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		Optional<Receta> receta = recetaRepository.findById(producto.getFkReceta());
		//Validación Receta
		if(receta.get() == null) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 400),
					HttpStatus.BAD_REQUEST);
		}
		
		if(tipoProducto.get()!=null) {
		
			producto.setTipoProducto(tipoProducto.get());
			producto.setDificultad(dificultad.get());
			producto.setReceta(receta.get());
			producto.setEstado(1l);
			
			productoRepository.save(producto);
			
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
	
	@GetMapping(path = "/get/{id_producto}", name = "Producto: " + NameMethods.MENSAJE_GET_BY_ID)
	public ResponseEntity<?> getById(@PathVariable(value = "producto") Long idproducto) {
		try {
			Producto producto = productoRepository.findOneActive(idproducto);
			if (producto == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			return ResponseEntity.ok().body(producto);
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
	@PutMapping(path = "/update/{id_producto}", name = "Producto: " + NameMethods.MENSAJE_UPDATE)
	public ResponseEntity<?> update(@PathVariable(value = "id_producto") Long idproducto, @Valid @RequestBody Producto productoDetails) {
		try {

			Optional<Producto> producto = productoRepository.findById(idproducto);
			
			if (producto.get() == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			producto.get().setNombre(productoDetails.getNombre());
	
			productoRepository.save(producto.get());
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
	
	@DeleteMapping(path = "/delete/{id}", name = "Producto: " + NameMethods.MENSAJE_DELETE)
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		try {
			Optional<Producto> producto = productoRepository.findById(id);
			if (producto.get() == null) {
				return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_NO_ENCONTRADO, 404),
						HttpStatus.NOT_FOUND);
			}
			
			producto.get().setEstado(0L);
	
			productoRepository.save(producto.get());
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_REGISTRO_ELIMINADO, 200), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(new CustomResponse(Constants.MENSAJE_ERROR_SERVER, 500),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
	}
}

