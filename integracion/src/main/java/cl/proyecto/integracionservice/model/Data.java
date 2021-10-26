package cl.proyecto.integracionservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.Api;

@lombok.Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Api
public class Data {

	private Notification notification;

	private Data data;

	private String restricted_package_name;

	private String to;

	private String priority;	
}
