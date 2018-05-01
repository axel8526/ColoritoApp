package com.example.usuario.coloritoapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {

    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_a);

        logo=findViewById(R.id.logo);
        Fade tran=new Fade(Fade.IN);
        getWindow().setEnterTransition(tran);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                inicia();
            }
        },3000);
    }
    public void inicia(){
        Intent i=new Intent(this, Home.class);
        Slide tra=new Slide(Gravity.LEFT);

        Pair pair=new Pair<View, String>(logo,"tlogo");
        getWindow().setExitTransition(tra);
        ActivityOptions op=ActivityOptions.makeSceneTransitionAnimation(Splash.this, pair);
        startActivity(i, op.toBundle());
        finish();
    }
}
