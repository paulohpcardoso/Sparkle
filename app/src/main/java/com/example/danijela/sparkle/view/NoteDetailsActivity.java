package com.example.danijela.sparkle.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.danijela.sparkle.R;
import com.example.danijela.sparkle.databinding.NoteDetailsActivityBinding;
import com.example.danijela.sparkle.model.Note;
import com.example.danijela.sparkle.viewmodel.NoteDetailsViewModel;

public class NoteDetailsActivity extends AppCompatActivity implements NoteDetailsViewModel.MainView {

    Note item;
    NoteDetailsViewModel viewModel;
    NoteDetailsActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.note_details_activity);

        item = getIntent().getExtras().getParcelable(Note.KEY);
        viewModel = new NoteDetailsViewModel(this, getContext(), item);
        binding.setViewModel(viewModel);
        displayHomeAsUpEnabled();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_details_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.edit_action:
               viewModel.edit();
                break;

            case R.id.delete_action:
                viewModel.delete();
                break;

            case R.id.share_action:
                viewModel.share();
                break;

            case android.R.id.home:
                navigateUpTo(new Intent(this, HabitDetailActivity.class));
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayHomeAsUpEnabled() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public Context getContext() {
        return this;
    }

    public static Intent launchDetail (Context context, Note note)
    {
        Intent intent = new Intent(context, NoteDetailsActivity.class);
        intent.putExtra(Note.KEY, note);
        return intent;
    }
}
