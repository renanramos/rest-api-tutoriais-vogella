package com.vogella.jersey.todo.client;

import java.net.URI;
import java.util.concurrent.ExecutionException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.ws.Response;

import org.glassfish.jersey.client.ClientConfig;

import com.vogella.jersey.todo.model.Todo;

public class Tester {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget service = client.target(getBaseURI());
		
		Todo todo = new Todo("3", "Blabla");
		Response response = service.path("rest").path(todo.getId())
				.request(MediaType.APPLICATION_XML).put(Entity.entity(todo, MediaType.APPLICATION_XML), Response.class);

		System.out.println(response.get());
		
		System.out.println(service.path("rest").path("todos").request().accept(MediaType.TEXT_XML).get(String.class));
		
		System.out.println(service.path("rest").path("todos").request().accept(MediaType.APPLICATION_XML).get(String.class));				
		
		Response checkDelete = (Response) service.path("rest").path("todos/1").request()
				.accept(MediaType.APPLICATION_XML).get();
		
		service.path("rest").path("todos/1").request().delete();
		
		
		System.out.println(service.path("rest").path("todos").request().accept(MediaType.APPLICATION_XML).get(String.class));
		
		Form form = new Form();
		form.param("id", "4");
		form.param("summary", "Demonstration of the client lib for forms");
		response = service.path("rest").path("todos").request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED), Response.class);

		System.out.println("Form response " + response);
		
		System.out.println(service.path("rest").path("todos").request().accept(MediaType.APPLICATION_XML).get(String.class));
		
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/com.vogella.jersey.todo").build();
	}

}
