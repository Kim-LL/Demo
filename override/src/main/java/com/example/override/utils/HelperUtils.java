package com.example.override.utils;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;

public class HelperUtils {


    private static Context mContext;

    private static AtomicBoolean isInit = new AtomicBoolean(false);

    private static final Logger log = LoggerFactory.getLogger(HelperUtils.class);
    /**
     * 初始化
     *
     * @param context
     */
    public static void init(Context context) {
        mContext = context;
        isInit.set(true);
    }

    /**
     * 生成ec文件
     *
     * @param isNew 是否重新创建ec文件
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String generateEcFile(boolean isNew) {

        if (isInit.get()) {
            String versionName = AppUtils.getVersionName(mContext);

            OutputStream out = null;
            String result = null;

            try {
                Path path = Paths.get(AppUtils.getEcPath(mContext));
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }

                path = Paths.get(AppUtils.getEcPath(mContext) + File.separator + "coverage_" + versionName + "_" + DateUtils.getDateString(null) + ".ec");
                if (!Files.exists(path)) {
                    Files.createFile(path);
                }
                result = path.toString();
                out = new FileOutputStream(result, !isNew);

                Object agent = Class.forName("org.jacoco.agent.rt.RT")
                        .getMethod("getAgent")
                        .invoke(null);
                out.write((byte[]) agent.getClass().getMethod("getExecutionData", boolean.class)
                        .invoke(agent, false));

            } catch (Exception e) {
                log.error("HelperUtils -- generateEcFile" + e.getMessage(), e);
            } finally {

                try {
                    if (out != null){
                        out.close();
                        log.info("HelperUtils -- generateEcFile: " + AppUtils.getEcPath(mContext));

                    }
                } catch (IOException e) {
                    log.error(e.getLocalizedMessage(), e);
                }
            }
            log.info(getAdbPullCmd());
            return result;
        }
        return null;
    }


    /**
     * 导出jacoco生成的ec文件到项目相关目录下
     *
     * @return adb 命令
     */
    public static String getAdbPullCmd() {
        String adb = "adb pull " + AppUtils.getEcPath(mContext) + " localPath";
        log.info("导出ec文件命令行: {}", adb);
        return adb;
    }
}
