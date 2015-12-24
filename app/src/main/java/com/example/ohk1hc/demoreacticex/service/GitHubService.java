package com.example.ohk1hc.demoreacticex.service;

import com.example.ohk1hc.demoreacticex.data.Repo;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

public interface GitHubService {

    String GITHUB_SERVICE_URL = "https://api.github.com";

    @GET("/users/{username}/repos")
    Call<List<Repo>> getRepos(@Path("username") String username);

}
