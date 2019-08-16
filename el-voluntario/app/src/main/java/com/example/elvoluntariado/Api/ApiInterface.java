package com.example.elvoluntariado.Api;

import com.example.elvoluntariado.models.AccessToken;
import com.example.elvoluntariado.models.Proyectos;
import com.example.elvoluntariado.models.Usuario;
import com.example.elvoluntariado.models.foundation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {


    @GET("Foundations")
    Call<List<foundation>> getFoundations(@Header("Authorization") String authorization);

    @POST("Foundations")
    Call<foundation> SendInfo(@Body foundation foundation, @Header("Authorization") String authorization);

    @POST("Users/login")
    Call<AccessToken> login(@Body Usuario usuario);

    @POST("Users")
    Call<Usuario> signUp(@Body Usuario usuario);

    @GET("Proyectos")
    Call<List<Proyectos>> getProyectos(@Header("Authorization") String authorization);




 }
