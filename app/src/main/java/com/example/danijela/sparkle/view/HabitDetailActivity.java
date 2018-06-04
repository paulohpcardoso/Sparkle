package com.example.danijela.sparkle.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.danijela.sparkle.R;
import com.example.danijela.sparkle.databinding.HabitDetailActivityBinding;
import com.example.danijela.sparkle.model.HabitSummary;
import com.example.danijela.sparkle.model.Note;
import com.example.danijela.sparkle.viewmodel.HabitDetailViewModel;

import java.util.List;

public class HabitDetailActivity extends AppCompatActivity
        implements HabitDetailViewModel.MainView {

    private HabitDetailActivityBinding binding;
    private HabitDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =
                DataBindingUtil.setContentView(this, R.layout.habit_detail_activity);
        setSupportActionBar(binding.toolbar);
        setupListView(binding.noteList);

        HabitSummary itemSummary = getIntent().getParcelableExtra(HabitSummary.KEY);
        viewModel = new HabitDetailViewModel(this, getContext(), itemSummary);
        binding.setViewModel(viewModel);

        displayHomeAsUpEnabled();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.onRefresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.habit_details_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setupListView(RecyclerView recyclerView) {

        recyclerView.setAdapter(new NotesListAdapter());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
    public void loadData(List<Note> items) {
        NotesListAdapter adapter = (NotesListAdapter) binding.noteList.getAdapter();
        adapter.setList(items);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.edit_action:
                viewModel.onEdit(this);
                break;

            case R.id.share_action:
                viewModel.onShare();
                break;

            case android.R.id.home:
                navigateUpTo(new Intent(this, HabitListActivity.class));
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(menuItem);
    }



    public static Intent launch(Context context, HabitSummary item) {
        Intent intent = new Intent(context, HabitDetailActivity.class);
        intent.putExtra(HabitSummary.KEY, item);
        return intent;
    }

    private void displayHomeAsUpEnabled() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public Context getContext() {
        return HabitDetailActivity.this;
    }
}
