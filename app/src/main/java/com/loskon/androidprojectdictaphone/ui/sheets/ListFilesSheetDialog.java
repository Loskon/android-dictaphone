package com.loskon.androidprojectdictaphone.ui.sheets;

import static java.util.Collections.emptyList;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loskon.androidprojectdictaphone.R;
import com.loskon.androidprojectdictaphone.audio.track.PlayingTrackControl;
import com.loskon.androidprojectdictaphone.audio.track.PlayingTrackThread;
import com.loskon.androidprojectdictaphone.audio.track.TrackListCallback;
import com.loskon.androidprojectdictaphone.files.FileHelper;
import com.loskon.androidprojectdictaphone.files.SortFileDate;
import com.loskon.androidprojectdictaphone.ui.recyclerview.FilesAdapter;
import com.loskon.androidprojectdictaphone.ui.recyclerview.FilesAdapterCallback;
import com.loskon.androidprojectdictaphone.utils.OnSingleClick;
import com.loskon.androidprojectdictaphone.utils.Utils;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Окно со списком файлов
 */

public class ListFilesSheetDialog extends BaseSheetDialog
        implements FilesAdapterCallback, TrackListCallback {

    private final Context context;

    private RecyclerView recyclerView;
    private TextView tvEmpty;

    private final FilesAdapter adapter = new FilesAdapter();
    private final PlayingTrackControl trackControl = new PlayingTrackControl();

    public ListFilesSheetDialog(Context context) {
        super(context);
        this.context = context;
    }

    public void show() {
        installCallbacks();
        configureDialogViews();
        configureRecyclerView();
        updateFilesList();
        installHandlers();
        super.show();
    }

    private void installCallbacks() {
        FilesAdapter.listenerCallback(this);
        PlayingTrackThread.registerCallback(this);
    }

    private void configureDialogViews() {
        setTextTitle(R.string.sheet_list_title);
        addViewToDialog();
    }

    private void addViewToDialog() {
        View view = View.inflate(context, R.layout.sheet_list_files, null);
        recyclerView = view.findViewById(R.id.recycler_view_files);
        tvEmpty = view.findViewById(R.id.tv_empty_list_files);
        setInsertView(view);
    }

    private void configureRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        Utils.removeFlickerList(recyclerView);
    }

    private void updateFilesList() {
        List<File> listFiles = getListFiles();
        adapter.setFilesList(listFiles);
    }

    private List<File> getListFiles() {
        File[] files = FileHelper.getFiles();
        List<File> listFiles = emptyList();

        if (files != null) {
            listFiles = Arrays.asList(files);
            Collections.sort(listFiles, new SortFileDate());
        }

        return listFiles;
    }

    private void installHandlers() {
        getBtnDialog().setOnClickListener(new OnSingleClick(v -> dialogCancel()));
        setOnCancelListener(dialogInterface -> stopTracking());
    }

    private void dialogCancel() {
        stopTracking();
        cancel();
    }

    private void stopTracking() {
        trackControl.stopPlaying();
    }

    @Override
    public void onClickFile(File file) {
        trackControl.startRecording(file);
    }

    @Override
    public void checkEmptyList(boolean hasEmpty) {
        Utils.setVisibleView(tvEmpty, hasEmpty);
    }

    @Override
    public void finishedPlaying() {
        adapter.hidePlayIcon();
    }
}