package cl.duoc.sigloxxi;
/** Es la clase que inicializa todo nuestra app
 * @author Andres Valencia Nuñez.
 * @version 0.1
 * @since 1.0
*/
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import cl.duoc.sigloxxi.entity.ApiRuta;
import cl.duoc.sigloxxi.repository.ApiRutaRepository;

@SpringBootApplication
@EnableJpaAuditing
@EnableAutoConfiguration
@Configuration
@ComponentScan
@Order(HIGHEST_PRECEDENCE)
public class SigloxxiApplication {

	/** CARGA DE REPOSITORYS */

	@Autowired
	RequestMappingHandlerMapping requestMappingHandlerMapping;

	@Autowired
	ApiRutaRepository apiRutaRepository;

	/** Declaracion de un bean para ser usado en las contraseñas */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	/** El listener de la app esperando eventos */
	@EventListener(ApplicationReadyEvent.class)
	
	 /**
	  * Inserta las rutas de los servicios disponibles 
	  */
	public void insertarRutas() {

		Object[] rutas = requestMappingHandlerMapping.getHandlerMethods().keySet().stream()
				.map(t -> (t.getMethodsCondition().getMethods().size() == 0 ? "GET"
						: t.getMethodsCondition().getMethods().toArray()[0]) + "===="
						+ t.getPatternsCondition().getPatterns().toArray()[0] + "====" + t.getName())
				.toArray();

		for (Object x : rutas) {

			String rutaUrl = x.toString().split("====")[1];
			String rutaDescripcion = x.toString().split("====")[2];

			ApiRuta apiRuta = new ApiRuta();
			apiRuta.setDescripcion(rutaDescripcion);
			apiRuta.setRuta(rutaUrl);
			apiRuta.setActivo(1L);

			if (apiRuta.getRuta().trim() != "/error" && apiRuta.getDescripcion().trim() != "null"
					&& apiRuta.getDescripcion() != null) {

				ApiRuta apiRutaExistente = apiRutaRepository.findByRuta(apiRuta.getRuta());

				if (apiRutaExistente == null) {
					apiRutaRepository.save(apiRuta);
				} else {
					apiRutaExistente.setDescripcion(apiRuta.getDescripcion());
					apiRutaRepository.save(apiRutaExistente);
				}
			}

		}
	}
    
	public static void main(String[] args) {
		SpringApplication.run(SigloxxiApplication.class, args);
	}

}
