package cl.duoc.sigloxxi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.duoc.sigloxxi.entity.ApiRuta;

@Repository
public interface ApiRutaRepository extends JpaRepository<ApiRuta, Long> {

	@Query(value = "SELECT * FROM api_ruta WHERE ruta = :ruta and activo = 1", nativeQuery = true)
	ApiRuta findByRuta(@Param("ruta") String ruta);

	@Query(value = "SELECT * FROM api_ruta WHERE activo = 1", nativeQuery = true)
	List<ApiRuta> findAllActive();

	@Query(value = "SELECT * FROM api_ruta WHERE activo = 0", nativeQuery = true)
	List<ApiRuta> findAllInactive();

	@Query(value = "SELECT * FROM api_ruta WHERE id_api_ruta = :apiRutaId and activo = 1", nativeQuery = true)
	ApiRuta findOneActive(@Param("apiRutaId") Long apiRutaId);

	@Query(value = "SELECT ar.* FROM rol_api_ruta as rar join rol as rol on rol.id_rol = rar.id_rol join api_ruta as ar on ar.id_api_ruta = rar.id_api_ruta join usuario_rol as ur on ur.id_rol = rar.id_rol join usuario as usu on usu.id_usuario = ur.id_usuario "
			+ "where usu.id_usuario = :idUsuario and ar.ruta like :rutaIngresada", nativeQuery = true)
	List<ApiRuta> usuarioTinePermisosParaAccederAlServicio(@Param("idUsuario") Long idUsuario,
			@Param("rutaIngresada") String rutaIngresada);
}