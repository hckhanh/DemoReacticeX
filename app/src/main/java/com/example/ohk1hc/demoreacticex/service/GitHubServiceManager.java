package com.example.ohk1hc.demoreacticex.service;

import com.example.ohk1hc.demoreacticex.data.Repo;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GitHubServiceManager {

    GitHubService gitHubService;

    public GitHubServiceManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GitHubService.GITHUB_SERVICE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        gitHubService = retrofit.create(GitHubService.class);
    }

    public Observable<List<Repo>> getRepos(final String username) {
        return Observable.create(new Observable.OnSubscribe<List<Repo>>() {
            @Override
            public void call(Subscriber<? super List<Repo>> subscriber) {
                try {
                    Call<List<Repo>> callResponse = gitHubService.getRepos(username);
                    Response<List<Repo>> response = callResponse.execute();
                    subscriber.onNext(response.body());
                    subscriber.onCompleted();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void getReposAsync(String username, Callback<List<Repo>> callback) {
        gitHubService.getRepos(username).enqueue(callback);
    }

}
