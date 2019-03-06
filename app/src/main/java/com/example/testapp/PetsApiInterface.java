package com.example.testapp;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface PetsApiInterface {
    @GET("xim/api.php")
    Observable<PetResponse> getCatsList(@Query("query") String query);
}
