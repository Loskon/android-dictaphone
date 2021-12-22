package com.loskon.androidprojectdictaphone.ui.recyclerview;

import static java.util.Collections.emptyList;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loskon.androidprojectdictaphone.R;
import com.loskon.androidprojectdictaphone.files.SortFileDate;
import com.loskon.androidprojectdictaphone.utils.OnSingleClickListener;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Адаптер для работы со списком файлов
 */

public class FilesAdapter extends RecyclerView.Adapter<FileViewHolder> {

    private List<File> files = emptyList();

    private int lastCheckedPosition = -1;

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_file, parent, false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        File file = files.get(position);

        String fileName = file.getName().replace(".ulaw", "");
        holder.nameFiles.setText(fileName);

        holder.radioButton.setChecked(position == lastCheckedPosition);

        holder.cardView.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                clickingCardView(file, holder);
            }
        });
    }

    public void setFilesList(List<File> files) {
        this.files = files;
        Collections.sort(files, new SortFileDate());
        checkEmptyFilesList();
        updateChangedList();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateChangedList() {
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    private void checkEmptyFilesList() {
        boolean hasEmpty = (files.size() == 0);
        if (callback != null) callback.onListEmpty(hasEmpty);
    }

    private void clickingCardView(File file, RecyclerView.ViewHolder holder) {
        lastCheckedPosition = holder.getAdapterPosition();
        notifyItemRangeChanged(0, getItemCount());
        if (callback != null) callback.onClickingFile(file);
    }

    public void resetPlayIcon() {
        lastCheckedPosition = -1;
        notifyItemRangeChanged(0, getItemCount());
    }

    private static CallbackAdapter callback;

    public static void listenerCallback(CallbackAdapter callback) {
        FilesAdapter.callback = callback;
    }
}
