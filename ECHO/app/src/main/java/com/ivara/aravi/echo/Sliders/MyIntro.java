package com.ivara.aravi.echo.Sliders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ivara.aravi.echo.R;
import com.ivara.aravi.echo.Sliders.*;
import com.github.paolorotolo.appintro.AppIntro;
import com.ivara.aravi.echo.MainActivity;

/**
 * Created by Aravi on 14-09-2017.
 */

public class MyIntro extends AppIntro {
    // Please DO NOT override onCreate. Use init
    @Override
    public void init(Bundle savedInstanceState) {

        //adding the three slides for introduction app you can ad as many you needed
        addSlide(AppIntroSampleSlider.newInstance(R.layout.app_intro1));
        addSlide(AppIntroSampleSlider.newInstance(R.layout.app_intro2));
        addSlide(AppIntroSampleSlider.newInstance(R.layout.app_intro3));
        addSlide(AppIntroSampleSlider.newInstance(R.layout.app_intro4));
        addSlide(AppIntroSampleSlider.newInstance(R.layout.app_intro5));
        addSlide(AppIntroSampleSlider.newInstance(R.layout.app_intro6));
        addSlide(AppIntroSampleSlider.newInstance(R.layout.app_intro7));
        addSlide(AppIntroSampleSlider.newInstance(R.layout.app_intro8));

        // Show and Hide Skip and Done buttons
        showStatusBar(false);
        showSkipButton(false);

        // Turn vibration on and set intensity
        // You will need to add VIBRATE permission in Manifest file
        setVibrate(true);
        setVibrateIntensity(30);

        //Add animation to the intro slider
        setDepthAnimation();
    }

    @Override
    public void onSkipPressed() {
        // Do something here when users click or tap on Skip button.
        Toast.makeText(getApplicationContext(),
                getString(R.string.app_intro_skip), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onNextPressed() {
        // Do something here when users click or tap on Next button.
    }

    @Override
    public void onDonePressed() {
        // Do something here when users click or tap tap on Done button.
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onSlideChanged() {
        // Do something here when slide is changed
    }
}