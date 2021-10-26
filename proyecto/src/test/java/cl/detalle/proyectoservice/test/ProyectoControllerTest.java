package cl.detalle.proyectoservice.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.detalle.proyectoservice.ProyectoServiceApplication;
import cl.detalle.proyectoservice.repository.ProyectoRepository;
import cl.detalle.proyectoservice.service.ProyectoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProyectoServiceApplication.class)
@SpringBootTest
public class ProyectoControllerTest {

	private MockMvc mockMvc;
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private WebApplicationContext wac;
	
	@Mock
	private ProyectoService service;
	
	@Mock
	private ProyectoRepository repository;
	
	@Before
	public void setup () {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		this.mockMvc = builder.build();		
		repository.deleteAll();
	}
	
	@Test
	public void obtieneProyectosByUsuario() throws Exception{
		Long idUsuario = new Long(36);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/proyecto//obtieneProyecto/{idUsuario}", idUsuario);
		this.mockMvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(MockMvcResultHandlers.print());
	}
}

