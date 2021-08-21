package com.example.dict.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 公众号：IT波 on 2021/7/18 Copyright © Leon. All rights reserved.
 * Functions: 从本地assets文件中读取 信息工具类
 */
public class AssetsUtils {

    public static String getAssetsContent(Context context, String filename) {
        // 获取到Asset管理对象
        AssetManager manager = context.getResources().getAssets();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 输入流的读取
            InputStream is = manager.open(filename);
            int hasRead = 0;
            byte[] buf = new byte[1024];
            while (true) {
                hasRead = is.read(buf);

                if (hasRead == -1) {
                    // 读取完了结束循环
                    break;
                } else {
                    baos.write(buf, 0, hasRead);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return baos.toString();
    }

}
