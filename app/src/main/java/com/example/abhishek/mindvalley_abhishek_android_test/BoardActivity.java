package com.example.abhishek.mindvalley_abhishek_android_test;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abhishek.remoteresourceloader.OnDownloadCompletionListener;
import com.example.abhishek.remoteresourceloader.ResourceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class BoardActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Gson gson;
    ResourceManager resourceManager;
    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    FloatingActionButton fab;
    SwipeRefreshLayout swipeRefreshLayout;
    static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setTitle(getString(R.string.app_name));
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        initializeNavigationDrawer();

        mContext = getApplicationContext();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchResource();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark,
                R.color.colorPrimary,
                R.color.colorAccent);
        fetchResource();
    }

    private void fetchResource() {
        resourceManager = ResourceManager.getInstance(getApplicationContext());
        gson = new Gson();
        resourceManager.getResourceLoader().fetchResource("http://pastebin.com/raw/wgkJgazE", new OnDownloadCompletionListener() {
            @Override
            public void resourceFetchedFromRemote(String url, byte[] bytes) {
                initializeElements(bytes);
            }

            @Override
            public void resourceFetchedFromMemory(String url, byte[] bytes) {
                initializeElements(bytes);
            }

            @Override
            public void resourceDownloadCancelled(String url) {

            }

        }, null);
    }

    private void initializeNavigationDrawer() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id) {
                    case R.id.home:
                        Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.settings:
                        Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.help:
                        Toast.makeText(getApplicationContext(), "Help", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.rate:
                        Toast.makeText(getApplicationContext(), "Rate", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;
                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        TextView user_email = (TextView) header.findViewById(R.id.user_email);
        TextView user_name = (TextView) header.findViewById(R.id.user_name);
        user_name.setText(getString(R.string.dummy_name));
        user_email.setText(getString(R.string.dummy_email));
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();
    }

    private void initializeElements(byte[] bytes) {
       List<Board> boards = deserializeBytes(bytes);
        if (boards != null && boards.size() > 0) {
            PinRecyclerViewAdapter pinRecyclerViewAdapter = new PinRecyclerViewAdapter(getApplicationContext(), boards);
            recyclerView.setHasFixedSize(true);
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
            recyclerView.setAdapter(pinRecyclerViewAdapter);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerView.smoothScrollToPosition(0);
                }
            });
            swipeRefreshLayout.setRefreshing(false);
        } else {
            Toast.makeText(BoardActivity.this, "Connection not established", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public static List<Board> deserializeBytes(byte[] bytes){
        String serialized = new ResourceManager(mContext).getConvertersUtils().byteArrayToUri(bytes);
        Type type = new TypeToken<List<Board>>() {
        }.getType();
        List<Board> boards = new Gson().fromJson(serialized, type);
        return boards;
    }
}
