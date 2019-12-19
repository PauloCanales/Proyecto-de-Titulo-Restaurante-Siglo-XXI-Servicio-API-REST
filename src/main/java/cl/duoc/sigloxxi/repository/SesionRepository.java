package cl.duoc.sigloxxi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.duoc.sigloxxi.entity.Sesion;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {

    @Query(value = "SELECT * FROM sesion WHERE activo = 1 AND id_usuario = :idUsuario", nativeQuery = true)
    List<Sesion> findAllByIdUsuario(@Param("idUsuario") Long idUsuario);

    @Query(
        value = "SELECT ses.* FROM sesion ses WHERE ses.token = :tokenUsuario",
        nativeQuery = true)
    Sesion findByToken(@Param("tokenUsuario") String tokenUsuario);
    
}
