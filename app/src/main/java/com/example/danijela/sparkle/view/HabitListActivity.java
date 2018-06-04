package com.example.danijela.sparkle.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.example.danijela.sparkle.*;
import com.example.danijela.sparkle.databinding.HabitListActivityBinding;
import com.example.danijela.sparkle.future.InboxListActivity;
import com.example.danijela.sparkle.model.HabitSummary;
import com.example.danijela.sparkle.viewmodel.HabitListViewModel;

import java.util.List;

public class HabitListActivity extends AppCompatActivity implements HabitListViewModel.MainView, SwipeRefreshLayout.OnRefreshListener {


    public static final String ARE_MY_HABITS = "AreMyHabits";
    private HabitListActivityBinding binding;
    private HabitListViewModel viewModel;
    private boolean areMyHabits = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().hasExtra("AreMyHabits"))
            areMyHabits = getIntent().getBooleanExtra(ARE_MY_HABITS, true);

        binding = DataBindingUtil.setContentView(this, R.layout.habit_list_activity);
        viewModel = new HabitListViewModel(this, getContext(), areMyHabits);
        binding.setHabitListViewModel(viewModel);

        setSupportActionBar(binding.toolbar);
        setupListView(binding.habitList);

        //binding.habitList.setHasFixedSize(true);
        //binding.habitList.setItemViewCacheSize(20);
        //binding.habitList.setDrawingCacheEnabled(true);
        //binding.habitList.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


        binding.swipeRefresh.setOnRefreshListener(this);
        viewModel.refresh();
    }

    private void setupListView(RecyclerView listView) {


        HabitListAdapter adapter = new HabitListAdapter();
        listView.setAdapter(adapter);

        adapter.setIsUserShown(!areMyHabits);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        listView.setLayoutManager(staggeredGridLayoutManager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.destroy();
    }

    @Override
    public void setProgress(boolean inProgress) {
        binding.swipeRefresh.setRefreshing(inProgress);
    }

    @Override
    public Context getContext() {
        return HabitListActivity.this;
    }

    @Override
    public void loadData(List<HabitSummary> list) {
        if (areMyHabits) {
            ((HabitListAdapter) binding.habitList.getAdapter()).setList(list);
        } else {
            ((HabitListAdapter) binding.habitList.getAdapter()).setList(list);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.habit_list_menu, menu);
        return true;
    }

    @Override
    public void onRefresh() {
        viewModel.refresh();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.view_others_action:
                startOthersHabitListActivity();
                break;

            case R.id.add_action:
                startNewHabitActivity();
                break;

            case R.id.inbox_action:
                startInboxActivity();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startOthersHabitListActivity() {
        Intent intent = new Intent(this, HabitListActivity.class);
        intent.putExtra(ARE_MY_HABITS, false);
        startActivity(intent);
    }

    private void startNewHabitActivity() {
        startActivity(new Intent(this, HabitEditActivity.class));
    }

    private void startInboxActivity() {
        startActivity(new Intent(this, InboxListActivity.class));
    }
}