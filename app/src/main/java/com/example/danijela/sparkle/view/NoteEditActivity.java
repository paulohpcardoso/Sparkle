package com.example.danijela.sparkle.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.danijela.sparkle.*;
import com.example.danijela.sparkle.databinding.NoteEditActivityBinding;
import com.example.danijela.sparkle.model.Note;
import com.example.danijela.sparkle.viewmodel.NoteEditViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class NoteEditActivity extends AppCompatActivity implements NoteEditViewModel.MainView {

    private boolean editMode = false;
    private NoteEditActivityBinding binding;
    private NoteEditViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =
                DataBindingUtil.setContentView(this, R.layout.note_edit_activity);

        Note item = getIntent().getParcelableExtra(Note.KEY);

        if (item == null) {
            long habitId = getIntent().getLongExtra(Note.HABIT_ID_KEY, 0);
            item = new Note(-1, habitId);
        }

        viewModel = new NoteEditViewModel(this, getContext(), item);
        binding.setViewModel(viewModel);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //TODO: implement new note logic

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, HabitDetailActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent launchEdit(Context context, Note item) {
        Intent intent = new Intent(context, NoteEditActivity.class);
        intent.putExtra(Note.KEY, item);
        return intent;
    }

    public static Intent launchCreate(Context context, long habitId) {
        Intent intent = new Intent(context, NoteEditActivity.class);
        intent.putExtra(Note.HABIT_ID_KEY, habitId);
        return intent;
    }

    @Override
    public Context getContext() {
        return this;
    }




    public void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(NoteEditActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private static final Integer REQUEST_CAMERA = 1;
    private static final Integer SELECT_FILE = 2;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {


                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                BitmapDrawable thumbnailDrawable = new BitmapDrawable(getResources(), thumbnail);


            } else if (requestCode == SELECT_FILE) {
                viewModel.getItem().imageUrl = data.getData().getPath();
                viewModel.notify();
            }
        }
    }
}
