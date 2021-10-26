package cl.solicitud.reembolsoservice.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cl.solicitud.reembolsoservice.ReembolsoServiceApplication;
import cl.solicitud.reembolsoservice.model.SolicitudReembolso;
import cl.solicitud.reembolsoservice.repository.ReembolsoRepository;
import cl.solicitud.reembolsoservice.service.ReembolsoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ReembolsoServiceApplication.class)
@SpringBootTest
public class ReembolsoControllerTest {

	private MockMvc mockMvc;
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private WebApplicationContext wac;
	
	@Mock
	private ReembolsoService service;
	
	@Mock
	private ReembolsoRepository repository;
	
	@Before
	public void setup () {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		this.mockMvc = builder.build();
		service.obtieneAllSolicitudes();
		repository.deleteAll();
	}
	
	@Test
	public void obtieneAllSolicitudes() throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/solicitud/obtieneAllSolicitudes");
		this.mockMvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void registraSolicitud() throws Exception{
		SolicitudReembolso sol = new SolicitudReembolso();
		sol.setIdUsuario(0);
		sol.setIdEstadoReembolso(1);
		sol.setFechaSolicitud(new Date());
		sol.setObservacion("");
		
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/solicitud/registraSolicitud")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(sol));
		this.mockMvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andDo(MockMvcResultHandlers.print());
	}
	
	private String toJson(Object object) throws JsonProcessingException {
		return mapper.writeValueAsString(object);
	}	
	
	@Test
	public void actualizaSolicitud() throws Exception{
		SolicitudReembolso sol = new SolicitudReembolso();
		sol.setIdUsuario(0);
		sol.setIdEstadoReembolso(1);
		sol.setFechaSolicitud(new Date());
		sol.setObservacion("");
		Long l = new Long(1);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/api/solicitud/actualizaSolicitud/{idSolicitud}",l)
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(sol));
		this.mockMvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void eliminaSolicitud() throws Exception{
		Long l = new Long(1);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/api/solicitud/eliminaSolicitud/{idSolicitud}",l)
				.contentType(MediaType.APPLICATION_JSON);
		this.mockMvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}
}
