package com.example.ohk1hc.demoreacticex;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ohk1hc.demoreacticex.adapter.ReposAdapter;
import com.example.ohk1hc.demoreacticex.data.Repo;
import com.example.ohk1hc.demoreacticex.service.GitHubServiceManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    GitHubServiceManager gitHubServiceManager;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.repo_list)
    RecyclerView reposRecyclerView;

    ReposAdapter reposAdapter;

    String mUsername;

    Action1<List<Repo>> getRepoCallback = new Action1<List<Repo>>() {
        @Override
        public void call(List<Repo> repos) {
            reposAdapter.removeRepos();
            reposAdapter.addRepos(repos);
            reposAdapter.notifyDataSetChanged();
            setTitle(mUsername);
        }
    };

    SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String username) {
            getReposFromUsername(username);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String username) {
            return false;
        }
    };

    void getReposFromUsername(String username) {
        if (username != null && !username.isEmpty()) {
            gitHubServiceManager.getRepos(username)
                    .subscribe(getRepoCallback);
            mUsername = username;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        reposRecyclerView.setHasFixedSize(true);
        reposRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        reposAdapter = new ReposAdapter();
        gitHubServiceManager = new GitHubServiceManager();

        getReposFromUsername("hckhanh");

        reposRecyclerView.setAdapter(reposAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setQueryHint("Enter GitHub's username...");
        searchView.setOnQueryTextListener(onQueryTextListener);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
