package com.t_academy_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class Menu1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu1);

        Button b = (Button)findViewById(R.id.button1);
        final ImageView iv = (ImageView)findViewById(R.id.imageView1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation anim = AnimationUtils
                        .loadAnimation
                                (getApplicationContext(),
                                        R.anim.set_animation);
                iv.startAnimation(anim);
            }
        });
    }
}
