package com.example.override.constant;

import android.os.Environment;

import java.io.File;

public class PathConstant {

    //ec文件手机存放的的路径
    public static String DEFAULT_COVERAGE_FILE_PATH = Environment.getExternalStorageDirectory()
            .getPath() + File.separator + "ACoverages";


}
