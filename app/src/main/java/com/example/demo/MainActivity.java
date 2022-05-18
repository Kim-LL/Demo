package com.example.demo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.demo.utils.PermissionUtils;
import com.example.login.ui.login.LoginActivity;
import com.example.override.service.StatisticService;
import com.example.override.utils.HelperUtils;
import com.example.setting.SettingsActivity;

import lombok.extern.slf4j.Slf4j;

/**
 * 合并测试
 */
@Slf4j
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnMethod, btnLogin, btnSetting, btnReport, btnUpload;

    private TextView tvContentBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAction();

        initView();

        initListener();
    }

    @Override
    protected void onPause() {
        log.info("onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        log.info("onStop");
        super.onStop();
    }

    private void initAction(){
        // 启动 jacoco 服务统计，生成一个 service，在 service 做定时统计
        Intent intent = new Intent(this, StatisticService.class);
        startService(intent);
        PermissionUtils.checkPermission(this);
    }

    private void initView() {
        btnMethod = findViewById(R.id.btn_method);
        btnLogin = findViewById(R.id.btn_login);
        btnSetting = findViewById(R.id.btn_setting);
        btnReport = findViewById(R.id.btn_report);
        btnUpload = findViewById(R.id.btn_upload);
        tvContentBottom = findViewById(R.id.tv_content_bottom);
    }

    private void initListener(){
        btnMethod.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
        btnReport.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        log.info("report: {}", "开始。。。。。。。。");
        tvContentBottom.setText(HelperUtils.getAdbPullCmd());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            HelperUtils.generateEcFile(true);
        }
        log.info("report: {}", "结束。。。。。。。。");
        super.onDestroy();
    }

    private void function(){
        Toast.makeText(getApplication(),"方法调用",Toast.LENGTH_SHORT).show();
    }

    private void report(){
        tvContentBottom.setText(HelperUtils.getAdbPullCmd());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            new Thread(() -> {
                String path = HelperUtils.generateEcFile(true);
                Looper.prepare();
                Toast.makeText(MainActivity.this, "生成路径 " + path, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }).start();
        }
    }

    private void upload(){
        new Thread(new Runnable() {
            @Override
            public void run() {
//                String DEFAULT_COVERAGE_FILE_PATH = Environment.getExternalStorageDirectory()
//                        .getPath() + "/coverage.ec";

                Looper.prepare();
                Toast.makeText(MainActivity.this, "暂无执行", Toast.LENGTH_SHORT).show();
                Looper.loop();

            }
        }).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            // 说明这个是申请权限的返回
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permissions[i]);
                    if (showRequestPermission) {
                        PermissionUtils.checkPermission(this);
                        return;
                    }
                }else{
                    Toast.makeText(this, "权限" + permissions[i] + "获取成功", Toast.LENGTH_SHORT).show();
                }
            }

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.btn_method:
                function();
                break;
            case R.id.btn_login:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_setting:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_report:
                report();
                break;
            case R.id.btn_upload:
                upload();
                break;
        }
    }
}