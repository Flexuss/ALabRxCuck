package com.example.dmitry.alabrxcuck;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

import com.example.dmitry.alabrxcuck.pojo.Joke;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Dmitry on 29.05.2017.
 */

public class RxFragment extends Fragment {

    TaskInterface mTaskListener;
    Observable<Joke> joke;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mTaskListener = (TaskInterface) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mTaskListener = (TaskInterface) activity;
    }

    public void getJoke(){
    Gson gson = new GsonBuilder().create();
    Retrofit retrofit = new Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("http://api.icndb.com/")
            .build();
    ApiService api = retrofit.create(ApiService.class);
    joke = api.getJoke();
        joke.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(joke->mTaskListener.onFinish(joke.getValue().getJoke()));
    }
}
