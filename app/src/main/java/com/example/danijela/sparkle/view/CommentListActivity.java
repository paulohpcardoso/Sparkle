package com.example.danijela.sparkle.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.danijela.sparkle.R;
import com.example.danijela.sparkle.databinding.CommentsListActivityBinding;
import com.example.danijela.sparkle.model.Comment;
import com.example.danijela.sparkle.model.Habit;
import com.example.danijela.sparkle.viewmodel.CommentListViewModel;

import java.util.List;

public class CommentListActivity extends AppCompatActivity implements CommentListViewModel.MainView, SwipeRefreshLayout.OnRefreshListener {

    private CommentsListActivityBinding binding;
    private CommentListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Habit item = getIntent().getParcelableExtra(Habit.KEY);

        binding = DataBindingUtil.setContentView(this, R.layout.comments_list_activity);
        viewModel = new CommentListViewModel(this, getContext(), item);
        binding.setViewModel(viewModel);

        setupListView(binding.commentList);

        binding.swipeRefresh.setOnRefreshListener(this);
        viewModel.refresh();
    }

    private void setupListView(RecyclerView listView) {

        CommentsListAdapter adapter = new CommentsListAdapter();
        listView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(linearLayoutManager);
    }

    public static Intent launchList(Context context, Habit habit) {
        Intent intent = new Intent(context, CommentListActivity.class);
        intent.putExtra(Habit.KEY, habit);
        return intent;
    }

    @Override
    public void onRefresh() {
        viewModel.refresh();
    }

    @Override
    public Context getContext() {
        return CommentListActivity.this;
    }

    @Override
    public void loadData(List<Comment> items) {

        CommentsListAdapter adapter = (CommentsListAdapter) binding.commentList.getAdapter();
        adapter.setList(items);

    }
}
