package com.example.myapplication;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash_Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Apply animation from XML
        ImageView logo = findViewById(R.id.splash_logo);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.splash_animation);
        logo.startAnimation(fadeIn);  // Use startAnimation instead of setAnimation

        // Move to MainActivity after 3 seconds
        new Handler().postDelayed(() -> {
            startActivity(new Intent(Splash_Activity.this, MainActivity.class));
            finish();
        }, 5000);
    }

}
