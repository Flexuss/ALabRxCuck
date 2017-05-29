package com.example.dmitry.alabrxcuck;

import com.example.dmitry.alabrxcuck.pojo.Joke;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Dmitry on 29.05.2017.
 */

public interface ApiService {

    @GET("jokes/random")
    Observable<Joke> getJoke();
}
