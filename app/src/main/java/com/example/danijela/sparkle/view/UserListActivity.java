package com.example.danijela.sparkle.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;

import com.example.danijela.sparkle.R;
import com.example.danijela.sparkle.databinding.UserListBinding;
import com.example.danijela.sparkle.model.User;
import com.example.danijela.sparkle.viewmodel.UserListViewModel;

import java.util.List;

public class UserListActivity extends AppCompatActivity implements UserListViewModel.MainView {

    private static final String TYPE = "Type";

    private UserListBinding binding;
    private UserListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int type = getIntent().getExtras().getInt(TYPE);
        User user = getIntent().getParcelableExtra(User.KEY);

        binding = DataBindingUtil.setContentView(this, R.layout.user_list);
        viewModel = new UserListViewModel(this, getContext(), user, type);
        binding.setViewModel(viewModel);

        setSupportActionBar(binding.toolbar);
        setupListView(binding.userList);

        viewModel.onRefresh();

    }

    private void setupListView(RecyclerView recyclerView) {

        recyclerView.setAdapter(new UserListAdapter());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private SearchView searchView;
    private SearchView.OnQueryTextListener queryTextListener;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.user_habits_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public Context getContext() {
        return UserListActivity.this;
    }

    @Override
    public void loadData(List<User> items) {
        UserListAdapter adapter = (UserListAdapter) binding.userList.getAdapter();
        adapter.setList(items);
    }

    public static Intent launch (Context context, User user, int type) {

        Intent intent = new Intent(context, UserListActivity.class);
        intent.putExtra(User.KEY, user);
        intent.putExtra(TYPE, type);

        return intent;
    }

}
