package com.example.demo.rest;

import com.orbitz.consul.Consul;
import com.orbitz.consul.PreparedQueryClient;
import com.orbitz.consul.model.health.ServiceHealth;
import com.orbitz.consul.model.query.QueryResults;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/consul")
public class HelloWorldConsulEndpoint
{
	private static final PreparedQueryClient CONSUL = Consul.builder()
		.build()
		.preparedQueryClient();

	@GET
	@Produces("text/plain")
	public Response getWithConsul()
	{
		final QueryResults execute = CONSUL.execute("test-service");
		final Optional<ServiceHealth> first = execute.nodes()
			.stream()
			.findFirst();
		final int port = first.get()
			.getService()
			.getPort();
		final String address = first.get()
			.getNode()
			.getAddress();
		final Client client = ClientBuilder.newClient();
		final WebTarget TARGET = client.target("http://" + address + ":" + port + "/");
		final Response response = TARGET.path("/")
			.request()
			.get();

		// https://docs.oracle.com/javaee/7/tutorial/jaxrs-client001.htm
		client.close();

		return Response.ok(response.readEntity(String.class))
			.build();
	}
}