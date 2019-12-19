package cl.duoc.sigloxxi.constants;
/** Representa las constantes que se ocuparan para retornar mensajes.
 * @author Andres Valencia Nuñez.
 * @version 0.1
 * @since 1.0
*/
import java.util.ArrayList;

public class Constants {

	
	public static final String VALID_NOMBRE_REQUERIDO = "El nombre es requerido";
	public static final String VALID_DESCRIPCION_REQUERIDO = "La descripcion es requerida";
	public static final String VALID_RUT_REQUERIDO = "El RUT es requerido";
	public static final String VALID_APATERNO_REQUERIDO = "El apellido Paterno es requerido";
	public static final String VALID_AMATERNO_REQUERIDO = "El apellido Materno es requerido";
	public static final String VALID_DOMICILIO_REQUERIDO = "El domicilio es requerido";
	public static final String VALID_CORREO_REQUERIDO = "El correo es requerido";
	public static final String VALID_FONO_REQUERIDO = "El teléfono es requerido";
	public static final String VALID_ESTADO_REQUERIDO = "El estado es requerido";
	public static final String MENSAJE_REGISTRO_NO_ENCONTRADO = "No se han encontrado datos en la consulta realizada.";
	public static final String VALID_CANTIDAD_REQUERIDO = "La cantidad es requerida";
	public static final String VALID_CAPACIDAD_REQUERIDO = "La capacidad es requerida";
	public static final String VALID_VALOR_REQUERIDO = "El valor es requerido";
	
	public static final String MENSAJE_NOMBRE_REGISTRO_EXISTENTE = "Ya existe un registro con el mismo nombre.";
	public static final String MENSAJE_REGISTRO_ACTUALIZADO = "Registro actualizado satisfactoriamente.";
	public static final String MENSAJE_REGISTRO_ELIMINADO = "Registro eliminado satisfactoriamente.";
	public static final String MENSAJE_REGISTRO_NUEVO = "Registro creado correctamente.";
	public static final String MENSAJE_ID_BODEGA = "El ID Bodega es requerido";
	public static final String MENSAJE_ID_INSUMO = "El ID Insumo es requerido";
	public static final String MENSAJE_ID_TIPO_MOVIMIENTO = "El ID Tipo Movimiento es requerido";
	public static final String MENSAJE_ID_PROVEEDOR = "El ID Proveedor es requerido";
	
	public static final String MENSAJE_ERROR_SERVER = "Ocurrio un error en el servidor, vuelva a intentarlo";
	
	public static final String MENSAJE_RECETA_SIN_ID = "No se encuentra la Receta";
	public static final String MENSAJE_INGREDIENTE_SIN_NOMBRE = "El ingrediente no tiene nombre";
	public static final String MENSAJE_INGREDIENTE_SIN_UNIDAD_MEDIDA = "El ingrediente no tiene Unidad de Medida";
	public static final String MENSAJE_INGREDIENTE_SIN_INSUMO = "El ingrediente no tiene Insumo";
	public static final String UNIDAD_MEDIDA_NO_EXISTE = "No se encuentra la Unidad de Medida";
	public static final String INSUMO_MEDIDA_NO_EXISTE = "No se encuentra el Insumo";
	
	public static final String MENSAJE_SIN_RUTA = "No se encuentra la ruta.";
	public static final String MENSAJE_SIN_PERMISOS = "No se encuentran los permisos";
	public static final String MENSAJE_SIN_CLAVE = "Debe ingresar una clave";
	
	
	

	public static String mensajeNuevoRegistro(String tabla) {
		return tabla.substring(0, 1).toUpperCase() + tabla.substring(1).toLowerCase()
				+ " ha sido guardado satisfactoriamente.";
	}

	public static String mensajeFaltaEntidadDeRelacion(String tablaFaltante, Long fkRegistroTabla) {
		return "No se ha encontrado el registro de " + tablaFaltante.substring(0, 1).toUpperCase()
				+ tablaFaltante.substring(1).toLowerCase() + ", con id: " + fkRegistroTabla
				+ " para realizar la creación del nuevo registro.";
	}
	
	public static String mensajeFaltaEntidadDeRelacionMuchosAMuchos(String tablaFaltante, ArrayList<Long> fkRegistrosTabla) {
		return "No se han encontrado los registros de " + tablaFaltante.substring(0, 1).toUpperCase()
				+ tablaFaltante.substring(1).toLowerCase() + ", con los id: " + fkRegistrosTabla.toString()
				+ " para realizar la creación de los nuevos registros.";
	}

	public static String campoRequerido(String campo) {
		return campo + " es requerido.";
	}
}
