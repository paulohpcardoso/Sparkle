package com.example.danijela.sparkle.view;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import android.view.Menu;
import android.view.MenuItem;

import com.example.danijela.sparkle.R;
import com.example.danijela.sparkle.databinding.UserDetailsActivityBinding;
import com.example.danijela.sparkle.model.HabitSummary;
import com.example.danijela.sparkle.model.User;
import com.example.danijela.sparkle.viewmodel.UserDetailsViewModel;

import java.util.List;

public class UserDetailsActivity extends AppCompatActivity implements UserDetailsViewModel.MainView {

    UserDetailsActivityBinding binding;
    UserDetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding =
                DataBindingUtil.setContentView(this, R.layout.user_details_activity);
        setSupportActionBar(binding.toolbar);

        User item = getIntent().getParcelableExtra(User.KEY);
        viewModel = new UserDetailsViewModel(this, getContext(), item);
        binding.setViewModel(viewModel);

        setupListView(binding.habitList);
        displayHomeAsUpEnabled();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.refresh();
    }


    private void setupListView(RecyclerView listView) {


        HabitListAdapter adapter = new HabitListAdapter();
        listView.setAdapter(adapter);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        listView.setLayoutManager(staggeredGridLayoutManager);
    }

    @Override
    public void loadData(List<HabitSummary> list) {
        HabitListAdapter adapter = (HabitListAdapter) binding.habitList.getAdapter();
        adapter.setList(list);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_habits_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.follow_action:
                viewModel.onFollow(null); //TODO: check this
                break;

            case android.R.id.home:
                navigateUpTo(new Intent(this, HabitListActivity.class));
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    private void displayHomeAsUpEnabled() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public Context getContext() {
        return UserDetailsActivity.this;
    }

    public static Intent launch(Context context, User user) {

        Intent intent = new Intent(context, UserDetailsActivity.class);
        intent.putExtra(User.KEY, user);

        return intent;
    }
}
