package com.example.danijela.sparkle;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.danijela.sparkle.api.MockRepository;
import com.example.danijela.sparkle.future.InboxListActivity;
import com.example.danijela.sparkle.model.Habit;
import com.example.danijela.sparkle.view.HabitDetailActivity;
import com.example.danijela.sparkle.view.HabitEditActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Habits. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link HabitDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class HabitListActivityOld extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_list_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setTitle(getTitle());

        recyclerView = (RecyclerView) findViewById(R.id.habit_list);
        assert recyclerView != null;
        setupRecyclerView(recyclerView);

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(new ArrayList(MockRepository.ITEM_MAP.values())));

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    private SearchView searchView;

    private String filterText = "";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.habit_list_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));

            SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {

                    filterText = newText;
                    if (newText.equals("")) {
                        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(new ArrayList(MockRepository.ITEM_MAP.values())));

                        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
                        recyclerView.setLayoutManager(staggeredGridLayoutManager);
                    }

                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {

                    ArrayList<Habit> items = MockRepository.filterMyHabits(filterText.toLowerCase());
                    recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(items));

                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
                    recyclerView.setLayoutManager(staggeredGridLayoutManager);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);


        }
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        switch (menu.getItemId()) {
            case R.id.view_others_action:
                Intent viewFriendsIntent = new Intent(this, HabitListActivityOld.class);
                startActivity(viewFriendsIntent);
                break;

            case R.id.add_action:
                Intent newHabitIntent = new Intent(this, HabitEditActivity.class);
                startActivity(newHabitIntent);
                break;

            case R.id.inbox_action:
                Intent inboxIntent = new Intent(this, InboxListActivity.class);
                startActivity(inboxIntent);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(menu);
    }


    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Habit> mValues;

        private final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = (int) v.getTag();
                Habit item = mValues.get(position);

                Context context = v.getContext();
                Intent intent = new Intent(context, HabitDetailActivity.class);
                intent.putExtra(Habit.KEY, item);
                context.startActivity(intent);
            }
        };

        public SimpleItemRecyclerViewAdapter(ArrayList<Habit> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.habit_item_old, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.item = mValues.get(position);
            holder.habitTitleTextView.setText(holder.item.title);
            holder.habitDescriptionTextView.setText(holder.item.description);

            //holder.habitImageView.setImageResource(holder.item.imageResource);

            holder.view.setOnClickListener(onClickListener);
            holder.view.setTag(position);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View view;

            public final ImageView habitImageView;
            public final TextView habitTitleTextView;
            public final TextView habitDescriptionTextView;

            public Habit item;

            public ViewHolder(View view) {
                super(view);
                this.view = view;
                habitImageView = (ImageView) view.findViewById(R.id.habit_image);
                habitTitleTextView = (TextView) view.findViewById(R.id.habit_title);
                habitDescriptionTextView = (TextView) view.findViewById(R.id.habit_description);

            }

            @Override
            public String toString() {
                return super.toString() + " '" + habitTitleTextView.getText() + "'";
            }
        }
    }


}
