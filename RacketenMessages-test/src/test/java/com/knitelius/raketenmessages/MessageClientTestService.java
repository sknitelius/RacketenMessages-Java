package com.knitelius.raketenmessages;

import java.util.List;

import com.knitelius.racketenmessages.model.Message;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MessageClientTestService {
	@GET("message")
	Call<List<Message>> getAllMessages();
	@PUT("message")
	Call<Message> saveMessage(@Body Message message);
	@GET("message/{id}")
	Call<Message> getMessage(@Path("id")long id);
}
