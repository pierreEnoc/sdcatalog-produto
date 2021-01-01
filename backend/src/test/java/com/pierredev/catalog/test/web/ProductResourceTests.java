package com.pierredev.catalog.test.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.pierredev.catalog.dto.ProductDTO;
import com.pierredev.catalog.services.ProductService;
import com.pierredev.catalog.services.exceptions.DatabaseException;
import com.pierredev.catalog.services.exceptions.ResourceNotFoundException;
import com.pierredev.catalog.test.factory.ProductFactory;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductResourceTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductService service;

	@Value("${security.oauth2.client.client-id}")
	private String clientId;
	
	@Value("${security.oauth2.client.client-secret}")
	private String clientSecret;
	
	private Long existingId;
	private Long nonExistingId;
	private ProductDTO newProductDTO;
	private ProductDTO existingProductDTO;
	private PageImpl<ProductDTO> page;
	private Long dependentId;
	
	@BeforeEach
	void setup() throws Exception {
		existingId = 1L;
		nonExistingId = 2L;
		dependentId = 3L;
		
		newProductDTO = ProductFactory.createProductDTO(null);
		existingProductDTO = ProductFactory.createProductDTO(existingId);
		
		page = new PageImpl<>(List.of(existingProductDTO));
		

		Mockito.when(service.findById(existingId)).thenReturn(existingProductDTO);
		Mockito.when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
		
		Mockito.when(service.findAllPaged(any(), anyString(), any())).thenReturn(page);
		
		Mockito.when(service.insert(any())).thenReturn(existingProductDTO);
		
		Mockito.when(service.update(eq(existingId), any())).thenReturn(existingProductDTO);
		Mockito.when(service.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);

		Mockito.doNothing().when(service).delete(existingId);
		Mockito.doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);
		Mockito.doThrow(DatabaseException.class).when(service).delete(dependentId);

		
	}
	
	
	@Test
	public void findAllShouldReturnPage() throws Exception {
		ResultActions result = 
				mockMvc.perform(get("/products")
						.accept(MediaType.APPLICATION_JSON));
		
				result.andExpect(status().isOk());
				result.andExpect(jsonPath("$.content").exists());		
	}
	
	@Test
	public void findByIdShouldReturnProductWhenIdExists() throws Exception {
		ResultActions result = 
				mockMvc.perform(get("/products/{id}", existingId)
						.accept(MediaType.APPLICATION_JSON));
		
				result.andExpect(status().isOk());
				result.andExpect(jsonPath("$.id").exists());
				result.andExpect(jsonPath("$.id").value(existingId));			
	}
	
	@Test
	public void findByIdShouldReturnProductWhenIdDoesNotExist() throws Exception  {
		
		ResultActions result = 
				mockMvc.perform(get("/products/{id}", nonExistingId)
						.accept(MediaType.APPLICATION_JSON));
		
				result.andExpect(status().isNotFound());	
	}
	
	private String obtainAccessToken(String username, String password) throws Exception {
		 
	    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("grant_type", "password");
	    params.add("client_id", clientId);
	    params.add("username", username);
	    params.add("password", password);
	 
	    ResultActions result 
	    	= mockMvc.perform(post("/oauth/token")
	    		.params(params)
	    		//.with(httpBasic(clientId, clientSecret))
	    		.accept("application/json;charset=UTF-8"))
	        	.andExpect(status().isOk())
	        	.andExpect(content().contentType("application/json;charset=UTF-8"));
	 
	    String resultString = result.andReturn().getResponse().getContentAsString();
	 
	    JacksonJsonParser jsonParser = new JacksonJsonParser();
	    return jsonParser.parseMap(resultString).get("access_token").toString();
	}	

		
}