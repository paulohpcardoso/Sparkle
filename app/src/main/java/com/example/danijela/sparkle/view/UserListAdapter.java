package com.example.danijela.sparkle.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.danijela.sparkle.R;
import com.example.danijela.sparkle.databinding.UserShortItemBinding;
import com.example.danijela.sparkle.model.User;
import com.example.danijela.sparkle.viewmodel.UserItemViewModel;

import java.util.Collections;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private List<User> items;

    public UserListAdapter() {
        this.items = Collections.emptyList();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        UserShortItemBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.user_short_item,
                        parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindItems(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setList(List<User> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        UserShortItemBinding itemBinding;

        public ViewHolder(UserShortItemBinding itemBinding) {
            super(itemBinding.userItem);
            this.itemBinding = itemBinding;
        }

        void bindItems(User item) {
            if (itemBinding.getViewModel() == null) {
                itemBinding.setViewModel(
                        new UserItemViewModel(item, itemView.getContext()));
            } else {
                itemBinding.getViewModel().setItem(item);
            }
        }
    }
}
