package com.example.danijela.sparkle.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.danijela.sparkle.R;
import com.example.danijela.sparkle.databinding.CommentItemBinding;
import com.example.danijela.sparkle.model.Comment;
import com.example.danijela.sparkle.viewmodel.CommentItemViewModel;

import java.util.Collections;
import java.util.List;

public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.ViewHolder> {

    private List<Comment> items;

    public CommentsListAdapter() {
        this.items = Collections.emptyList();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommentItemBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.comment_item,
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

    public void setList(List<Comment> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CommentItemBinding itemBinding;

        public ViewHolder(CommentItemBinding itemBinding) {
            super(itemBinding.commentItem);
            this.itemBinding = itemBinding;
        }

        void bindItems(Comment item) {
            if (itemBinding.getViewModel() == null) {
                itemBinding.setViewModel(
                        new CommentItemViewModel(item, itemView.getContext()));
            } else {
                itemBinding.getViewModel().setItem(item);
            }
        }
    }
}
