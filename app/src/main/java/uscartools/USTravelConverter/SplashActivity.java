package uscartools.USTravelConverter;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Start main activity  https://android.jlelse.eu/right-way-to-create-splash-screen-on-android-e7f1709ba154
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            // close splash activity
            finish();
        }
    }

