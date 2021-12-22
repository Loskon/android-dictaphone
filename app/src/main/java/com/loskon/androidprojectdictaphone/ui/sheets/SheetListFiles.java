package com.loskon.androidprojectdictaphone.ui.sheets;

import static com.loskon.androidprojectdictaphone.files.FileManager.getListFile;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.loskon.androidprojectdictaphone.R;
import com.loskon.androidprojectdictaphone.audio.track.CallbackFinishedPlaying;
import com.loskon.androidprojectdictaphone.audio.track.PlayingTrackControl;
import com.loskon.androidprojectdictaphone.audio.track.PlayingTrackThread;
import com.loskon.androidprojectdictaphone.ui.recyclerview.CallbackAdapter;
import com.loskon.androidprojectdictaphone.ui.recyclerview.FilesAdapter;
import com.loskon.androidprojectdictaphone.utils.OnSingleClickListener;
import com.loskon.androidprojectdictaphone.utils.WidgetUtils;

import java.io.File;
import java.util.Arrays;

/**
 * Окно со списком файлов
 */

public class SheetListFiles implements CallbackAdapter, CallbackFinishedPlaying {

    private final Context context;
    private BaseSheetDialog dialog;

    private View insertView;
    private RecyclerView recyclerView;
    private TextView tvEmpty;

    private final FilesAdapter adapter = new FilesAdapter();
    private final PlayingTrackControl trackControl = new PlayingTrackControl();

    public SheetListFiles(Context context) {
        this.context = context;
        createInsertView();
    }

    private void createInsertView() {
        insertView = View.inflate(context, R.layout.sheet_list_files, null);
        recyclerView = insertView.findViewById(R.id.recycler_view_files);
        tvEmpty = insertView.findViewById(R.id.tv_empty_list_files);
    }

    public void show() {
        dialog = new BaseSheetDialog(context);
        configureDialogViews();
        installCallbacks();
        configureRecyclerView();
        updateFilesList();
        installHandlers();
        dialog.show();
    }

    private void configureDialogViews() {
        dialog.setInsertView(insertView);
        dialog.setTextTitle(R.string.sheet_list_title);
    }

    private void installCallbacks() {
        FilesAdapter.listenerCallback(this);
        PlayingTrackThread.listenerCallback(this);
    }

    private void configureRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        removeFlicker();
    }

    public void removeFlicker() {
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
    }

    private void updateFilesList() {
        File[] files = getListFile();
        if (files != null) adapter.setFilesList(Arrays.asList(files));
    }

    private void installHandlers() {
        dialog.getBtnDialog().setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                stopTracking();
                dialog.cancel();
            }
        });

        dialog.setOnCancelListener(dialogInterface -> stopTracking());
    }

    private void stopTracking() {
        trackControl.stopRecording();
    }

    @Override
    public void onClickingFile(File file) {
        trackControl.startRecording(file);
    }

    @Override
    public void onListEmpty(boolean hasEmpty) {
        WidgetUtils.setVisibleView(tvEmpty, hasEmpty);
    }

    @Override
    public void onFinishedPlaying() {
        adapter.resetPlayIcon();
    }
}