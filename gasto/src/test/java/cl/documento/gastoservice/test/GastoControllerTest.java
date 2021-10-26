package cl.documento.gastoservice.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cl.documento.gastoservice.GastoServiceApplication;
import cl.documento.gastoservice.model.Documento;
import cl.documento.gastoservice.repository.GastoRepository;
import cl.documento.gastoservice.service.GastoService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = GastoServiceApplication.class)
@SpringBootTest
public class GastoControllerTest {

	private MockMvc mockMvc;
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private WebApplicationContext wac;
	
	@Mock
	private GastoService service;
	
	@Mock
	private GastoRepository repository;
	
	@Before
	public void setup() {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		this.mockMvc = builder.build();
		repository.deleteAll();
	}
	
	/*@Test
	public void registraGasto() throws Exception{
		
		MockMultipartFile file = new MockMultipartFile("file", "orig", null, "bar".getBytes());
		Long l = new Long(1);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/gasto/registraGasto/{idBoleta}/{tipoDocumento}", l,"imagen")
				.contentType(MediaType.APPLICATION_JSON);
		this.mockMvc.perform(builder)
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andDo(MockMvcResultHandlers.print());
		
	}*/
	
	private String toJson(Object object) throws JsonProcessingException {
		return mapper.writeValueAsString(object);
	}
	
	@Test
	public void actualizaDocumento() throws Exception{
		Documento documento = new Documento();
		documento.setTipoDocumento("PDF");
		
		Long l = new Long(-36);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/api/gasto/actualizaDocumento/{idDocumento}",l)
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(documento));
		this.mockMvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void eliminaDocumento() throws Exception{
		Long l = new Long(-36);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/api/gasto/eliminaDocumento/{idDocumento}",l)
				.contentType(MediaType.APPLICATION_JSON);
		this.mockMvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}
	
}
