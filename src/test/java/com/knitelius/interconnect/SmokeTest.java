package com.knitelius.interconnect;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.knitelius.interconnect.model.Message;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SmokeTest {

	private static MessageClientTestService service;
	
	@BeforeClass
	public static void beforeClass() {
		String url = System.getProperty("url");
		url = url == null ? "http://localhost:9080/RacketenMessages/" : url;
		Retrofit retrofit = new Retrofit.Builder()
			    .baseUrl(url)
			    .addConverterFactory(GsonConverterFactory.create())
			    .build();
		service = retrofit.create(MessageClientTestService.class);
	}
	
	@Test
	public void testAddMessage() throws Exception {
		Message message = new Message();
		message.setMsg(String.format("Current time %s", LocalDateTime.now()));
		message.setUsr("smocker");
		
		Call<Message> storeMessgeRequest = service.saveMessage(message);		
		Response<Message> storeMessageResponse = storeMessgeRequest.execute();
		assertTrue(storeMessageResponse.isSuccessful());
		
		Long id = storeMessageResponse.body().getId();
		Call<Message> messageRequest = service.getMessage(id);
		Response<Message> messageResponse = messageRequest.execute();
		assertTrue(messageResponse.isSuccessful());
		assertEquals(id, messageResponse.body().getId());
		
		Call<List<Message>> allMessagesRequest = service.getAllMessages();
		Response<List<Message>> allMessagesResponse = allMessagesRequest.execute();
		assertTrue(!allMessagesResponse.body().isEmpty());
		assertTrue(allMessagesResponse.body().contains(messageResponse.body()));
	}
}
