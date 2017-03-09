package com.knitelius.racketenmessages.rest.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import com.knitelius.racketenmessages.model.Message;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.Entity;
import java.lang.Long;
import java.util.List;
import javax.ws.rs.core.GenericType;

public class MessagesBoundaryClient {

	final String resourceUrl;
	
	public MessagesBoundaryClient(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}
	
	public Response invokeAddMessage(Message m){
		Client client = ClientBuilder.newBuilder().build();
		WebTarget target = client.target(resourceUrl + "/message");
		Invocation.Builder builder = target.request("application/json");
		
		Response response = builder.put(Entity.entity(m, "application/json"));
		return response; 
	}

	public Message invokeMessage(Long l){
		Client client = ClientBuilder.newBuilder().build();
		WebTarget target = client.target(resourceUrl + "/message").path("{id}");
		target = target.resolveTemplate("id", l);
		
		Invocation.Builder builder = target.request("application/json");
		Response response = builder.get();
		return response.readEntity(Message.class);
	}

	public List<Message> invokeMessages(){
		Client client = ClientBuilder.newBuilder().build();
		WebTarget target = client.target(resourceUrl + "/message/all");
		Invocation.Builder builder = target.request("application/json");
		Response response = builder.get();
		return response.readEntity(new GenericType<List<Message>>() {} );
	}

}
