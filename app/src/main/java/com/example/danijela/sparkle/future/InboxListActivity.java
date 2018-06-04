package com.example.danijela.sparkle.future;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.danijela.sparkle.R;
import com.example.danijela.sparkle.api.MockRepository;
import com.example.danijela.sparkle.model.Credentials;
import com.example.danijela.sparkle.model.Message;
import com.example.danijela.sparkle.model.User;

import java.util.ArrayList;
import java.util.List;

public class InboxListActivity extends AppCompatActivity {

    public static final String ARG_USER_ID = "user_id";

    private ArrayList<Message> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inbox_list_activity);

        if (savedInstanceState == null) {
            items = MockRepository.getMessages();
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.inbox_recycler_view);
        assert recyclerView != null;

        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(items));

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

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Message> values;

        private View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                Message item = values.get(position);

                Context context = v.getContext();
                Intent intent = new Intent(context, MessageListActivity.class);

                if (item.sender.id == Credentials.getLoggedUser().id) {
                    intent.putExtra(User.KEY, item.receiver);
                } else {
                    intent.putExtra(User.KEY, item.sender);
                }

                context.startActivity(intent);
            }
        };

        public SimpleItemRecyclerViewAdapter(List<Message> items) {
            values = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.comment_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            holder.item = values.get(position);
            holder.userNameTV.setText(holder.item.sender.getFullName());
            holder.userIV.setImageResource(holder.item.sender.imageResourceId);
            holder.messageTV.setText(holder.item.content);
            holder.view.setTag(position);
            holder.view.setOnClickListener(onClickListener);
        }

        @Override
        public int getItemCount() {
            return values.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View view;

            public final ImageView userIV;
            public final TextView userNameTV;
            public final TextView messageTV;
            public final TextView dateTV;


            public Message item;

            public ViewHolder(View view) {
                super(view);
                this.view = view;
                userIV = (ImageView) view.findViewById(R.id.user_image);
                userNameTV = (TextView) view.findViewById(R.id.user_name);
                dateTV = (TextView) view.findViewById(R.id.date_text);
                messageTV = (TextView) view.findViewById(R.id.message_text);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + userNameTV.getText() + "'";
            }
        }
    }


}
