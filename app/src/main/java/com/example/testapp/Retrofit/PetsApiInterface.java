package com.example.testapp.Retrofit;

import com.example.testapp.Model.PetResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface PetsApiInterface {
    @GET("xim/api.php")
    Observable<PetResponse> getCatsList(@Query("query") String query);
}
