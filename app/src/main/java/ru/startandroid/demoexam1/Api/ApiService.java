package ru.startandroid.demoexam1.Api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.startandroid.demoexam1.ForMovies.Movies;
import ru.startandroid.demoexam1.LoginFiles.LoginRequest;
import ru.startandroid.demoexam1.LoginFiles.LoginResponse;
import ru.startandroid.demoexam1.RegisterFiles.RegisterRequest;
import ru.startandroid.demoexam1.RegisterFiles.RegisterResponse;

public interface ApiService {

    @POST("/auth/login")
    Call<LoginResponse> LoginUser(@Body LoginRequest loginRequest);

    @POST("/auth/register")
    Call<RegisterResponse> RegisterUser(@Body RegisterRequest registerRequest);


    @GET("/movies?filter=new")
    Call<List<Movies>> getMovies();

    @GET("movies/{movieId}")
    Call<Movies> getMovieDate(@Path("movieId") int movieId);
}
