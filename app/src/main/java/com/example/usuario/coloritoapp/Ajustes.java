package com.example.usuario.coloritoapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.view.Window;
import android.view.WindowManager;

public class Ajustes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new Pref()).commit();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.ajustes_a);

        Fade tran=new Fade(Fade.IN);
        getWindow().setEnterTransition(tran);
        Fade tran2=new Fade(Fade.OUT);
        getWindow().setReturnTransition(tran2);
    }
    public void startActivity(Intent intent){

        Fade tran=new Fade(Fade.OUT);
        getWindow().setExitTransition(tran);
        super.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        finish();
    }
}
