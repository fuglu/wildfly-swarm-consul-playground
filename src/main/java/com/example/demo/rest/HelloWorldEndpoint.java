package com.example.demo.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

@Path("/")
public class HelloWorldEndpoint
{
	@GET
	@Produces("text/plain")
	public Response get()
	{
		final Client client = ClientBuilder.newClient();
		final WebTarget TARGET = client.target("http://10.42.42.23:3000");
		final Response response = TARGET.path("/")
			.request()
			.get();

		// https://docs.oracle.com/javaee/7/tutorial/jaxrs-client001.htm
		client.close();

		return Response.ok(response.readEntity(String.class))
			.build();
	}
}