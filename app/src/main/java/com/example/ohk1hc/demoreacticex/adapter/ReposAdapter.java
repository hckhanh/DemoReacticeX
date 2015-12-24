package com.example.ohk1hc.demoreacticex.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ohk1hc.demoreacticex.R;
import com.example.ohk1hc.demoreacticex.data.Repo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.RepoHolder> {

    List<Repo> repos;

    public ReposAdapter() {
        this.repos = new ArrayList<>();
    }

    @Override
    public RepoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View repoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_layout, parent, false);
        return new RepoHolder(repoView);
    }

    @Override
    public void onBindViewHolder(RepoHolder holder, int position) {
        Repo repo = repos.get(position);

        holder.repoName.setText(repo.getName());
        holder.description.setText(repo.getDescription());
        holder.htmlUrl.setText(repo.getHtml_url());
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public void addRepos(List<Repo> repos) {
        this.repos.addAll(repos);
    }

    public void removeRepos() {
        this.repos.clear();
    }

    public static class RepoHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.repo_name)
        TextView repoName;

        @Bind(R.id.description)
        TextView description;

        @Bind(R.id.html_url)
        TextView htmlUrl;

        public RepoHolder(View repoView) {
            super(repoView);
            ButterKnife.bind(this, repoView);
        }

    }

}
