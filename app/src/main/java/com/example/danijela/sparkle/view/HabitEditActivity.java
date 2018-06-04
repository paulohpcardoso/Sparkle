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
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;

import com.example.danijela.sparkle.R;
import com.example.danijela.sparkle.databinding.HabitEditActivityBinding;
import com.example.danijela.sparkle.model.Habit;
import com.example.danijela.sparkle.viewmodel.HabitEditViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class HabitEditActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener, HabitEditViewModel.MainView {


    Habit item;
    private boolean editMode = false;
    private HabitEditActivityBinding binding;
    HabitEditViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding =
                DataBindingUtil.setContentView(this, R.layout.habit_edit_activity);
        setSupportActionBar(binding.toolbar);

        Habit item = getIntent().getParcelableExtra(Habit.KEY);
        viewModel = new HabitEditViewModel(this, getContext(), item);
        binding.setViewModel(viewModel);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        viewModel.refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.habit_edit_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        int id = menuItem.getItemId();
        switch (id) {
            case R.id.done_action:
                viewModel.done(null); //TODO: check this
                break;

            case R.id.share_action:
                viewModel.share(null); //TODO: check this
                break;

            case android.R.id.home:
                navigateUpTo(new Intent(this, HabitListActivity.class));
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(HabitEditActivity.this);
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
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
                //toolbar.setBackground(thumbnailDrawable);

            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null,
                        null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);


                BitmapDrawable thumbnailDrawable = new BitmapDrawable(getResources(), bm);
                //toolbar.setBackground(thumbnailDrawable);

            }
        }
    }

    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

        switch (group.getId()) {

            //TODO:implement this with binding
//            case R.id.habit_type_radio_group:
//                if (checkedId == R.id.simple_option) {
//                    conditionalLL.setVisibility(View.GONE);
//                } else {
//                    conditionalLL.setVisibility(View.VISIBLE);
//                }
//                break;
//            case R.id.end_target_radio_group:
//                if (checkedId == R.id.date_radio_option) {
//                    targetDaysET.setVisibility(View.GONE);
//                    endDateET.setVisibility(View.VISIBLE);
//                } else {
//                    targetDaysET.setVisibility(View.VISIBLE);
//                    endDateET.setVisibility(View.GONE);
//                }
//                break;

            default:
                break;
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //TODO: implement this with binding
//        if (parent.getId() == R.id.occurrence_type_spinner) {
//            if (position == 2) {
//                weekDaysLL.setVisibility(View.VISIBLE);
//            } else {
//                weekDaysLL.setVisibility(View.GONE);
//            }
//        }
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View view) {
        switch ((view.getId())) {
            case R.id.change_photo_button:
                selectImage();
                break;

            default:
                break;
        }
    }

    public static Intent launchEdit(Context context, Habit item) {
        Intent intent = new Intent(context, HabitEditActivity.class);
        intent.putExtra(Habit.KEY, item);
        return intent;
    }

    @Override
    public Context getContext() {
        return HabitEditActivity.this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.destroy();
    }

//TODO: select image

//    private void selectImage() {
//        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(com.example.danijela.sparkle.HabitEditActivity.this);
//        builder.setTitle("Add Photo!");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                if (items[item].equals("Take Photo")) {
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(intent, REQUEST_CAMERA);
//                } else if (items[item].equals("Choose from Library")) {
//                    Intent intent = new Intent(
//                            Intent.ACTION_PICK,
//                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    intent.setType("image/*");
//                    startActivityForResult(
//                            Intent.createChooser(intent, "Select File"),
//                            SELECT_FILE);
//                } else if (items[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }
//
//    private static final Integer REQUEST_CAMERA = 1;
//    private static final Integer SELECT_FILE = 2;
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK) {
//            if (requestCode == REQUEST_CAMERA) {
//                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//                File destination = new File(Environment.getExternalStorageDirectory(),
//                        System.currentTimeMillis() + ".jpg");
//                FileOutputStream fo;
//                try {
//                    destination.createNewFile();
//                    fo = new FileOutputStream(destination);
//                    fo.write(bytes.toByteArray());
//                    fo.close();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                BitmapDrawable thumbnailDrawable = new BitmapDrawable(getResources(), thumbnail);
//                toolbar.setBackground(thumbnailDrawable);
//
//            } else if (requestCode == SELECT_FILE) {
//                Uri selectedImageUri = data.getData();
//                String[] projection = {MediaStore.MediaColumns.DATA};
//                CursorLoader cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null,
//                        null);
//                Cursor cursor = cursorLoader.loadInBackground();
//                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//                cursor.moveToFirst();
//                String selectedImagePath = cursor.getString(column_index);
//                Bitmap bm;
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inJustDecodeBounds = true;
//                BitmapFactory.decodeFile(selectedImagePath, options);
//                final int REQUIRED_SIZE = 200;
//                int scale = 1;
//                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
//                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
//                    scale *= 2;
//                options.inSampleSize = scale;
//                options.inJustDecodeBounds = false;
//                bm = BitmapFactory.decodeFile(selectedImagePath, options);
//
//
//                BitmapDrawable thumbnailDrawable = new BitmapDrawable(getResources(), bm);
//                toolbar.setBackground(thumbnailDrawable);
//
//            }
//        }
//    }

}
