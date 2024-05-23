package es.uma.informatica.sii.spring.jpa.demo.Test;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("En el servicio de sesiones")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class SesionesTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Value(value = "${local.server.port}")
    private int port;


    @BeforeEach
    public void initializeDatabase() {
        grupoRepository.deleteAll();
        alumnoRepository.deleteAll();
    }

    private URI uri(String scheme, String host, int port, String... paths) {
        UriBuilderFactory ubf = new DefaultUriBuilderFactory();
        UriBuilder ub = ubf.builder()
            .scheme(scheme)
            .host(host).port(port);
        for (String path : paths) {
            ub = ub.path(path);
        }
        return ub.build();
    }
    private URI uriParam(String scheme, String host, int port, String path,String param, Collection<String>values) {
        UriBuilderFactory ubf = new DefaultUriBuilderFactory();
        UriBuilder ub = ubf.builder()
            .scheme(scheme)
            .host(host).port(port)
            .path(path).queryParam(param, values);
        return ub.build();
    }

    private RequestEntity<Void> get(String scheme, String host, int port, String path) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .build();
        return peticion;
    }
    private RequestEntity<Void> getParam(String scheme, String host, int port, String path, int plan) {
        URI uri = uriParam(scheme, host, port, path,"idSesion",plan);
        var peticion = RequestEntity.get(uri)
            .contentType(MediaType.APPLICATION_JSON);
        return peticion.build();
    }

    private RequestEntity<Void> delete(String scheme, String host, int port, String path) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.delete(uri)
            .build();
        return peticion;
    }
    private RequestEntity<Void> deleteParam(String scheme, String host, int port, String path,int plan) {
        URI uri = uriParam(scheme, host, port, path,"idSesion",plan);
        var peticion = RequestEntity.delete(uri)
            .build();
        return peticion;
    }

    private <T> RequestEntity<T> post(String scheme, String host, int port, String path, T object) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(object);
        return peticion;
    }
    private RequestEntity<Void> postParam(String scheme, String host, int port, String path, int plan) {
        URI uri = uriParam(scheme, host, port, path,"idSesion",plan);
        var peticion = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON);
        return peticion.build();
    }

    private <T> RequestEntity<T> put(String scheme, String host, int port, String path, T object) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.put(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .body(object);
        return peticion;
    }
    private RequestEntity<Void> putParam(String scheme, String host, int port, String path, int plan) {
        URI uri = uriParam(scheme, host, port, path,"idSesion",plan);
        var peticion = RequestEntity.put(uri)
            .contentType(MediaType.APPLICATION_JSON);
        return peticion.build();
    }
    @Nested
    @DisplayName("cuando no hay datos")
    public class SesionVacia{
        @Test
        @DisplayName("Devuelve la sesion vacia")
        public void getSesionPlan(){
            int plan = 1;
            var peticion = getParam("http", "localhost", port, "/sesion", plan);
            var respuesta = restTemplate.exchange(peticion, 
            new ParameterizedTypeReference<Set<Sesion>>() {
            });
            assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
        }
        @Test
        @DisplayName("Devuelve error cuando añade una sesion a un plan especifico")
        public void postSesionPlan(){
            int plan = 1;
            var peticion = postParam("http", "localhost", port, "/sesion", plan);
            var respuesta = restTemplate.exchange(peticion, 
            new ParameterizedTypeReference<Set<Sesion>>() {
            });
            assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
        }
        @Test
        @DisplayName("devuelve la sesion vacía")
        public void get_Sesion(){
            var peticion = get("http", "localhost", port, "/sesion/1");

            var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<Sesion>() {
                });
                assertThat(respuesta.getStatusCode().value()).isEqualTo(404);                
        }
        @Test
        @DisplayName("put sesion vacía")
        public void put_sesion(){
            var sesion = SesionDTO.builder().nombre("Sesion1").build();
            var peticion = put("http", "localhost", port, "/sesion/1",sesion);
            var respuesta = restTemplate.exchange(peticion,
                Void.class );
            assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
        }
        @Test
        @DisplayName("elimina el sesion vacía")
        public void delete_sesion(){
            var peticion = delete("http", "localhost", port, "/sesion/1");

            var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<Sesion>() {
                });
                assertThat(respuesta.getStatusCode().value()).isEqualTo(404);                
        }
    }
    @Nested
    @DisplayName("cuando hay datos")
    public class SesionDatos{
        @BeforeEach
        public void insertarDatos(){
            var sesion = new Sesion();
            grupo.setId(Long.valueOf(1));
            grupo.setNombre("Grupo 1");
            grupoRepository.save(grupo);
            var grupo2 = new Grupo();
            grupo2.setId(Long.valueOf(2));
            grupo2.setNombre("Grupo 2");
            grupoRepository.save(grupo2);
            
        }
        @Test
        @DisplayName("devuelve sesion")
        public void get_Sesion(){
            var peticion = get("http", "localhost", port, "/sesion/1");

            var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<Sesion>() {
                });
                assertThat(respuesta.getStatusCode().value()).isEqualTo(200);                
        }
        @Test
        @DisplayName("put sesion ")
        public void put_sesion(){
            var sesion = SesionDTO.builder();
            var peticion = put("http", "localhost", port, "/sesion/1",sesion);
            var respuesta = restTemplate.exchange(peticion,
                Void.class );
            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
        }

        @Test
        @DisplayName("elimina la sesion")
        public void delete_sesion(){
            var peticion = delete("http", "localhost", port, "/sesion/1");

            var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<Sesion>() {
                });
                assertThat(respuesta.getStatusCode().value()).isEqualTo(200);                
        }
        @Test
        @DisplayName("Devuelve la sesion")
        public void getSesionPlan(){
            int plan = 1;
            var peticion = getParam("http", "localhost", port, "/sesion", plan);
            var respuesta = restTemplate.exchange(peticion, 
            new ParameterizedTypeReference<Set<Sesion>>() {
            });
            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
        }
        @Test
        @DisplayName("Devuelve 200 cuando añade una sesion a un plan especifico")
        public void postSesionPlan(){
            int plan = 1;
            var peticion = postParam("http", "localhost", port, "/sesion", plan);
            var respuesta = restTemplate.exchange(peticion, 
            new ParameterizedTypeReference<Set<Sesion>>() {
            });
            assertThat(respuesta.getStatusCode().value()).isEqualTo(201);
        }
    }
}
