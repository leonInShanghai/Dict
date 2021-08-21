/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.example.dict.utils;

import android.content.Context;

import java.io.File;

/**
 * Functions: OCR识别需要用到的类
 */
public class FileUtil {
    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }
}
