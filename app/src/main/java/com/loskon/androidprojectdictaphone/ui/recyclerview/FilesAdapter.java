package com.loskon.androidprojectdictaphone.ui.recyclerview;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loskon.androidprojectdictaphone.R;
import com.loskon.androidprojectdictaphone.utils.OnSingleClick;

import java.io.File;
import java.util.List;

/**
 * Адаптер для работы со списком файлов
 */

public class FilesAdapter extends RecyclerView.Adapter<FileViewHolder> {

    private List<File> files;

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

        holder.nameFiles.setText(getFileName(file));
        holder.radioButton.setChecked(position == lastCheckedPosition);
        holder.cardView.setOnClickListener(new OnSingleClick(v -> clickingCardView(file, holder)));
    }

    private String getFileName(File file) {
        return file.getName().replace(".ulaw", "");
    }

    public void setFilesList(List<File> files) {
        this.files = files;
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
        if (callback != null) callback.checkEmptyList(hasEmpty);
    }

    private void clickingCardView(File file, RecyclerView.ViewHolder holder) {
        lastCheckedPosition = holder.getAdapterPosition();
        notifyItemRangeChanged(0, getItemCount());
        if (callback != null) callback.onClickFile(file);
    }

    public void hidePlayIcon() {
        lastCheckedPosition = -1;
        notifyItemRangeChanged(0, getItemCount());
    }

    private static FilesAdapterCallback callback;

    public static void listenerCallback(FilesAdapterCallback callback) {
        FilesAdapter.callback = callback;
    }
}
