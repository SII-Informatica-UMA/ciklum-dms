package es.uma.informatica.sii.spring.jpa.demo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;      
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import es.uma.informatica.sii.spring.jpa.demo.dtos.PlanDTO;
import es.uma.informatica.sii.spring.jpa.demo.dtos.SesionDTO;
import es.uma.informatica.sii.spring.jpa.demo.dtos.SesionNuevaDTO;
import es.uma.informatica.sii.spring.jpa.demo.entities.Sesion;
import es.uma.informatica.sii.spring.jpa.demo.repositories.SesionRepository;
import es.uma.informatica.sii.spring.jpa.demo.security.JwtUtil;


import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("En el servicio de sesiones")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class SesionesTest {
    PlanDTO plan = new PlanDTO(1L);

    private String token;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SesionRepository sesionRepo;
    
    @Autowired
    private RestTemplate restTemplateMock;

    @Autowired
    private JwtUtil jwtUtil;
    
    private ObjectMapper mapper = new ObjectMapper();


    @Value(value = "${local.server.port}")
    private int port;

    private int entrenaPort = 9001;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void initializeDatabase() {
        sesionRepo.deleteAll();
        token = jwtUtil.generateToken(jwtUtil.createUserDetails("1","",List.of("ROLE_USER")));
        mockServer = MockRestServiceServer.createServer(restTemplateMock);
       
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
    private URI uriParam(String scheme, String host, int port, String path,String param, Collection<String> values) {
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
            .header("Authorization", "Bearer "+token)
            .build();
        return peticion;
    }
   
    private RequestEntity<Void> delete(String scheme, String host, int port, String path) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.delete(uri)
            .header("Authorization", "Bearer "+token)
            .build();
        return peticion;
    }

    private RequestEntity<Void> getParam(String scheme, String host, int port, String path,String query ,Long plan) {
        List<String> planes = new ArrayList<>();
        planes.add(plan.toString());
        URI uri = uriParam(scheme, host, port, path,query,planes);
        var peticion = RequestEntity.get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer "+token);
        return peticion.build();
    }


    private RequestEntity<Void> deleteParam(String scheme, String host, int port, String path,String query,Long plan) {
        List<String> planes = new ArrayList<>();
        planes.add(plan.toString());
        URI uri = uriParam(scheme, host, port, path,query,planes);
        var peticion = RequestEntity.delete(uri)
            .header("Authorization", "Bearer "+token)
            .build();
        return peticion;
    }

    private <T> RequestEntity<T> post(String scheme, String host, int port, String path, T object) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer "+token)
            .body(object);
        return peticion;
    }


    private <T> RequestEntity<T> postParam(String scheme, String host, int port, String path, String query,Long plan, T object) {
        List<String> planes = new ArrayList<>();
        planes.add(plan.toString());
        URI uri = uriParam(scheme, host, port, path,query,planes);
        var peticion = RequestEntity.post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer "+token)
            .body(object);
        return peticion;
    }

    private <T> RequestEntity<T> put(String scheme, String host, int port, String path, T object) {
        URI uri = uri(scheme, host, port, path);
        var peticion = RequestEntity.put(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer "+token)
            .body(object);
        return peticion;
    }


    private <T> RequestEntity<T> putParam(String scheme, String host, int port, String path, String query,Long plan, T object) {
        List<String> planes = new ArrayList<>();
        planes.add(plan.toString());
        URI uri = uriParam(scheme, host, port, path,query,planes);
        var peticion = RequestEntity.put(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer " + token)
            .body(object);
        return peticion;
    }

    @Nested
    @DisplayName("cuando no hay datos")
    public class SesionVacia{
        @Test
        @DisplayName("Devuelve error al llamar a un plan que no existe")
        public void getSesionPlan() throws JsonProcessingException, URISyntaxException{
            mockServer.expect(requestTo(new URI("http://localhost:" + entrenaPort + "/plan/1")))
            .andExpect(method(org.springframework.http.HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.NOT_FOUND)
            .contentType(MediaType.APPLICATION_JSON)
            .body(mapper.writeValueAsString(plan)));

            Long idplan = 1L;
            var peticion = getParam("http", "localhost", port, "/sesion", "plan",idplan);
            var respuesta = restTemplate.exchange(peticion, 
            new ParameterizedTypeReference<Set<Sesion>>() {
            });
            assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
        }
        

        @Test
        @DisplayName("Devuelve error cuando añade una sesion a un plan especifico")
        public void postSesionPlan() throws JsonProcessingException, URISyntaxException{
            mockServer.expect(requestTo(new URI("http://localhost:" + entrenaPort + "/plan/1")))
            .andExpect(method(org.springframework.http.HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.NOT_FOUND)
            .contentType(MediaType.APPLICATION_JSON)
            .body(mapper.writeValueAsString(plan)));

            Long planID = 1L;
            SesionDTO s = new SesionDTO(1L, planID, null, null, "Sentadillas", List.of("Video"), token, true, List.of("Malo","Bien"));
            var peticion = postParam("http", "localhost", port, "/sesion", "plan",planID,s);
            var respuesta = restTemplate.exchange(peticion, 
            new ParameterizedTypeReference<Void>() {
            });
            assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
        }


        @Test
        @DisplayName("Devuelve error al pedir una sesion específica")
        public void get_Sesion(){
            var peticion = get("http", "localhost", port, "/sesion/1");

            var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<SesionDTO>() {
                });
                assertThat(respuesta.getStatusCode().value()).isEqualTo(404);                
        }


        @Test
        @DisplayName("Devuelve error al actualizar una sesion vacía")
        public void put_sesion() throws JsonProcessingException, URISyntaxException{
            mockServer.expect(requestTo(new URI("http://localhost:" + entrenaPort + "/plan/1")))
            .andExpect(method(org.springframework.http.HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.NOT_FOUND)
            .contentType(MediaType.APPLICATION_JSON)
            .body(mapper.writeValueAsString(plan)));

            var sesion = new SesionDTO(1L, 1L, null, null, "Flexion",
                 List.of("Video"), "Descripcion", true, List.of("Datos buenos"));
            var peticion = put("http", "localhost", port, "/sesion/1",sesion);
            var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<SesionDTO>() {
                } );
            assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
        }


        @Test
        @DisplayName("Devuelve error al eliminar una sesion")
        public void delete_sesion(){
            var peticion = delete("http", "localhost", port, "/sesion/1");

            var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<Sesion>() {
                });
                assertThat(respuesta.getStatusCode().value()).isEqualTo(404);                
        }
    }

    @Nested
    @DisplayName("cuando no esta autorizado")
    public class SesionSinAutorizar{

        @BeforeEach
        public void sinautorizar(){
            token=null;
        }

        @Test
        @DisplayName("Devuelve error cuando intenta obtener una sesion asociada a un plan especifico cuando no está autorizado")
        public void getSesionPlan_403(){
            Long plan = 1L;
            var peticion = getParam("http", "localhost", port, "/sesion", "plan",plan);
            var respuesta = restTemplate.exchange(peticion, 
            new ParameterizedTypeReference<Set<Sesion>>() {
            });
            assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
        }
        
        @Test
        @DisplayName("Devuelve error cuando añade una sesion a un plan especifico cuando no está autorizado")
        public void postSesionPlan_403(){
            Long plan = 1L;
            SesionDTO s = new SesionDTO(1L, plan, null, null, "Sentadillas", List.of("Video"), token, true, List.of("Malo","Bien"));
            var peticion = postParam("http", "localhost", port, "/sesion", "plan",plan,s);
            var respuesta = restTemplate.exchange(peticion, 
                new ParameterizedTypeReference<Void>() {
                });
            assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
        }


        @Test
        @DisplayName("Devuelve error al pedir una sesion específica cuando no está autorizado")
        public void get_Sesion_403(){
            var peticion = get("http", "localhost", port, "/sesion/1");

            var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<Sesion>() {
                });
                assertThat(respuesta.getStatusCode().value()).isEqualTo(403);                
        }


        @Test
        @DisplayName("Devuelve error al actualizar una sesión cuando no está autorizado")
        public void put_sesion_403(){
            var sesion = SesionDTO.builder().trabajoRealizado("Sesion1").build();
            var peticion = put("http", "localhost", port, "/sesion/1",sesion);
            var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<Void>() {
                } );
            assertThat(respuesta.getStatusCode().value()).isEqualTo(403);
        }

        @Test
        @DisplayName("Devuelve error al eliminar una sesion cuando no está autorizado")
        public void delete_sesion_403(){
            var peticion = delete("http", "localhost", port, "/sesion/1");

            var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<Sesion>() {
                });
                assertThat(respuesta.getStatusCode().value()).isEqualTo(403);                
        }


    }
    
    @Nested
    @DisplayName("cuando hay datos")
    public class SesionDatos{
        Long idSesion;

        @BeforeEach
        public void insertarDatos(){ 
            plan.setReglaRecurrencia("Semanal");
            plan.setIdRutina(1L);

            var sesion1 = new Sesion();
            sesion1.setIdPlan(plan.getId());
            sesion1.setDatosSalud(List.of("Mal","Bien"));
            sesion1.setInicio(null);
            sesion1.setFin(null);
            sesion1.setDescripcion("Hola");
            sesion1.setMultimedia(List.of("https://informatica.cv.uma.es/"));
            sesion1.setPresencial(true);
            sesion1.setTrabajoRealizado("Nada");

            var sesion2 = new Sesion();
            sesion2.setIdPlan(plan.getId());
            sesion2.setDatosSalud(List.of("Bien","Regular"));
            sesion2.setInicio(null);
            sesion2.setFin(null);
            sesion2.setDescripcion("Hola Hola");
            sesion2.setMultimedia(List.of("https://informatica.cv.uma.es/"));
            sesion2.setPresencial(false);
            sesion2.setTrabajoRealizado("Nada de nada");
            
            sesionRepo.save(sesion1);
            sesionRepo.save(sesion2);

            idSesion = sesion1.getId();
        }
        @Test
        @DisplayName("Devuelve una sesion correctamente")
        public void get_Sesion(){
            var peticion = get("http", "localhost", port, "/sesion/" + idSesion);

            var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<SesionDTO>() {
                });
                assertThat(respuesta.getStatusCode().value()).isEqualTo(200);                
        }
        @Test
        @DisplayName("Actualiza una sesion correctamente")
        public void put_sesion() throws URISyntaxException, JsonProcessingException{
            mockServer.expect(requestTo(new URI("http://localhost:" + entrenaPort + "/plan/1")))
            .andExpect(method(org.springframework.http.HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(mapper.writeValueAsString(plan)));

            var sesion = new SesionDTO(0L, plan.getId(), null, null, "Probandooooo", List.of("Video2"), "No hice nada", false, List.of("malo","bueno"));
            var peticion = put("http", "localhost", port, "/sesion/" + idSesion,sesion);
            var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<SesionDTO>() {
                });
            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
        }

        @Test
        @DisplayName("Devuelve error al intentar actualizar una sesion que no existe")
        public void put_sesionError() throws URISyntaxException, JsonProcessingException{
            mockServer.expect(requestTo(new URI("http://localhost:" + entrenaPort + "/plan/1")))
            .andExpect(method(org.springframework.http.HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(mapper.writeValueAsString(plan)));

            var sesion = new SesionDTO(0L, plan.getId(), null, null, "Probandooooo", List.of("Video2"), "No hice nada", false, List.of("malo","bueno"));
            var peticion = put("http", "localhost", port, "/sesion/9999",sesion);
            var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<SesionDTO>() {
                });
            assertThat(respuesta.getStatusCode().value()).isEqualTo(404);
        }
        

        @Test
        @DisplayName("Elimina la sesion correctamente")
        public void delete_sesion(){
            var peticion = delete("http", "localhost", port, "/sesion/" + idSesion);

            var respuesta = restTemplate.exchange(peticion,
                new ParameterizedTypeReference<SesionDTO>() {
                });
                assertThat(respuesta.getStatusCode().value()).isEqualTo(200);                
        }
      
        @Test
        @DisplayName("Devuelve las sesiones asociadas a un plan correctamente")
        public void getSesionPlan() throws JsonProcessingException, URISyntaxException{
            mockServer.expect(requestTo(new URI("http://localhost:" + entrenaPort + "/plan/1")))
            .andExpect(method(org.springframework.http.HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(mapper.writeValueAsString(plan)));

            var peticion = getParam("http", "localhost", port, "/sesion", "plan",plan.getId());
            var respuesta = restTemplate.exchange(peticion, 
            new ParameterizedTypeReference<Set<Sesion>>() {
            });
            assertThat(respuesta.getStatusCode().value()).isEqualTo(200);
        }


        @Test
        @DisplayName("Añade una sesion a un plan especifico correctamente")
        public void postSesionPlan() throws JsonProcessingException, URISyntaxException{
            mockServer.expect(requestTo(new URI("http://localhost:" + entrenaPort + "/plan/1")))
            .andExpect(method(org.springframework.http.HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(mapper.writeValueAsString(plan)));

            SesionNuevaDTO s = new SesionNuevaDTO(null, null,"Trabajo",List.of("Video1"),"Descripcion",false,List.of("Mejor"),plan.getId());
            var peticion = postParam("http", "localhost", port, "/sesion", "plan",plan.getId(),s);
            var respuesta = restTemplate.exchange(peticion, 
            new ParameterizedTypeReference<Set<Sesion>>() {
            });
            assertThat(respuesta.getStatusCode().value()).isEqualTo(201);
        }
    } 
}
