package io.github.tubb.multichannel.example;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApplicationInfo info = null;
        try {
            info = getPackageManager()
                    .getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (info != null) {
            TextView tvAppName = findViewById(R.id.tv_app_name);
            String channelName = info.metaData.getString("MCP_CHANNEL");
            tvAppName.setText(String.format("Channel name: %s", channelName));
        }
    }
}
