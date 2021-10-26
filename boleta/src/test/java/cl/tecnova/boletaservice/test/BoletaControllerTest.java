package cl.tecnova.boletaservice.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.text.SimpleDateFormat;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cl.tecnova.boletaservice.BoletaServiceApplication;
import cl.tecnova.boletaservice.model.Boleta;
import cl.tecnova.boletaservice.repository.BoletaRepository;
import cl.tecnova.boletaservice.service.BoletaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = BoletaServiceApplication.class)
@SpringBootTest
public class BoletaControllerTest {

	private MockMvc mockMvc;
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private WebApplicationContext wac;
	
	@Mock
	private BoletaService service;
	
	@Mock
	private BoletaRepository repository;
	
	@Before
	public void setup() {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		this.mockMvc = builder.build();
		service.obtieneAllBoletas();
		repository.deleteAll();
	}
	
	@Test
	public void obtieneAllBoletas() throws Exception{
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/boleta/obtieneAllBoletas");
		this.mockMvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void obtieneBoleta() throws Exception{
		Long l = new Long(1);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/boleta/obtieneBoleta/{idBoleta}",l);
		this.mockMvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void registraBoleta() throws Exception{
		Boleta bol = new Boleta();
		bol.setIdBoleta(new Long(12343));
		bol.setDetalleBoleta("prueba de campos nulos");
		bol.setFechaBoleta(new Date());
		bol.setFechaIngreso(null);
		bol.setIdCentroCosto("PRE-01");;
		bol.setIdEstadoBoleta(1);
		bol.setIdProyecto(0);
		bol.setIdSolicitud(12334);
		bol.setIdTipoBoleta(1);
		bol.setIdUsuario(0);
		bol.setMontoBoleta(10000);
		bol.setObservacion("prueba de campos nulos");
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/boleta/registraBoleta")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(bol));
		this.mockMvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andDo(MockMvcResultHandlers.print());		
	}
	
	private String toJson(Object object) throws JsonProcessingException {
		return mapper.writeValueAsString(object);
	}
	
	@Test
	public void eliminarBoleta() throws Exception{
		Long l = new Long(26);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.delete("/api/boleta/eliminarBoleta/{idBoleta}",l)
				.contentType(MediaType.APPLICATION_JSON);
		this.mockMvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void actualizaBoleta() throws Exception{
		Boleta bol = new Boleta();
		bol.setDetalleBoleta("");
		bol.setFechaBoleta(new Date());
		bol.setFechaIngreso(new Date());
		bol.setIdCentroCosto("PRE-01");
		bol.setIdEstadoBoleta(1);
		bol.setIdProyecto(0);
		bol.setIdSolicitud(0);
		bol.setIdTipoBoleta(1);
		bol.setIdUsuario(0);
		bol.setMontoBoleta(10000);
		bol.setObservacion("");
		Long l = new Long(26);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/api/boleta/actualizaBoleta/{idBoleta}",l)
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(bol));
		this.mockMvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}
	
	/*@Test
	public void asociarIdSolicitud() throws Exception{
		Long idUsuario = new Long(0);
		Long idSolicitud = new Long(0);
		String fechaSolcitud = "2019-02-11";
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/api/boleta/a/asociarSolicitud/{idSolicitud}/{idUsuario}/{fechaSolicitud}",
				idSolicitud, idUsuario, fechaSolcitud)
				.contentType(MediaType.APPLICATION_JSON);
		this.mockMvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcResultHandlers.print());
	}*/
	
	
}
