package com.loskon.androidprojectdictaphone.ui.recyclerview;

import java.io.File;

public interface FilesAdapterCallback {
    void onClickFile(File file);

    void checkEmptyList(boolean hasEmpty);
}
