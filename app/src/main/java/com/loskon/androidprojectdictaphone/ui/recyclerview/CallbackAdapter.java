package com.loskon.androidprojectdictaphone.ui.recyclerview;

import java.io.File;

public interface CallbackAdapter {
    void onClickingFile(File file);

    void onListEmpty(boolean hasEmpty);
}
