package com.clearing.movies_data.remote

import com.clearing.movies_data.remote.dto.MovieDetailsResponse
import com.clearing.movies_data.remote.dto.MovieReleaseDateResponse
import com.clearing.movies_data.remote.dto.MoviesResponse
import com.clearing.movies_data.remote.dto.SearchMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("/3/discover/movie")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("sorted_by") sorting: String
    ): MoviesResponse


    @GET("/3/search/movie")
    suspend fun searchMovies(
        @Query("page") page: Int,
        @Query("query") query: String,
    ): SearchMoviesResponse

    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): MovieDetailsResponse

    @GET("/3/movie/{movie_id}")
    suspend fun getMovieReleaseDate(
        @Path("movie_id") movieId: Int
    ): MovieReleaseDateResponse

}