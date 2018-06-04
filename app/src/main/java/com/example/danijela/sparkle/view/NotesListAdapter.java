package com.example.danijela.sparkle.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.danijela.sparkle.R;
import com.example.danijela.sparkle.databinding.NoteItemBinding;
import com.example.danijela.sparkle.databinding.UserShortItemBinding;
import com.example.danijela.sparkle.model.Note;
import com.example.danijela.sparkle.model.User;
import com.example.danijela.sparkle.viewmodel.NoteItemViewModel;
import com.example.danijela.sparkle.viewmodel.UserItemViewModel;

import java.util.Collections;
import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.ViewHolder> {

    private List<Note> items;

    public NotesListAdapter() {
        this.items = Collections.emptyList();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NoteItemBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.note_item,
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

    public void setList(List<Note> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        NoteItemBinding itemBinding;

        public ViewHolder(NoteItemBinding itemBinding) {
            super(itemBinding.noteItem);
            this.itemBinding = itemBinding;
        }

        void bindItems(Note item) {
            if (itemBinding.getViewModel() == null) {
                itemBinding.setViewModel(
                        new NoteItemViewModel(item, itemView.getContext()));
            } else {
                itemBinding.getViewModel().setItem(item);
            }
        }
    }
}
