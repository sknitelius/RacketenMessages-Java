/*
 * The MIT License
 *
 * Copyright 2017 Stephan Knitelius.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.knitelius.raketenmessages;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.knitelius.racketenmessages.model.Message;

import okhttp3.ResponseBody;
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
