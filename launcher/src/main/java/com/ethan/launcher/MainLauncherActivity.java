package com.ethan.launcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ethan.launcher.launch.LauncherConfig;

import static com.ethan.launcher.launch.LauncherConstance.LAUNCHER_CONFIG_SETTING;

public class MainLauncherActivity extends AppCompatActivity {
    private static final String TAG = "MainLauncherActivity";
    private TextView testText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_launcher);
        testText = findViewById(R.id.id_launcher_activity_text);
        testText.setOnClickListener(view -> {
            setResult(10086);
            finish();
        });
        getIntentData();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        Object o = intent.getExtras().get(LAUNCHER_CONFIG_SETTING);
        if ((o instanceof LauncherConfig)) {
            Log.d(TAG, "LauncherConfig = " + o.toString());
        }
    }
}