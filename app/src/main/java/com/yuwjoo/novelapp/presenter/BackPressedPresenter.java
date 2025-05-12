package com.yuwjoo.novelapp.presenter;

import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;

import com.yuwjoo.novelapp.MainActivity;

import wendu.dsbridge.DWebView;

public class BackPressedPresenter implements IBackPressedPresenter {

    private long lastBackPressTime = -1L;// 上次点击返回键的时间

    public BackPressedPresenter(MainActivity mainActivity, DWebView dwebView) {
        mainActivity.getOnBackPressedDispatcher().addCallback(mainActivity, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (dwebView.canGoBack()) {
                    dwebView.goBack();
                    return;
                }
                long currentTIme = System.currentTimeMillis();
                if (lastBackPressTime == -1L || currentTIme - lastBackPressTime >= 2000) {
                    // 显示提示信息
                    Toast.makeText(mainActivity, "再按一次退出", Toast.LENGTH_SHORT).show();
                    // 记录时间
                    lastBackPressTime = currentTIme;
                } else {
                    //退出应用
                    mainActivity.finish();
                    // android.os.Process.killProcess(android.os.Process.myPid())
                    // System.exit(0) // exitProcess(0)
                    // moveTaskToBack(false)

                }
            }
        });
    }
}
