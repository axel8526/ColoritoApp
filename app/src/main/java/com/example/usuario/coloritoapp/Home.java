package com.example.usuario.coloritoapp;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    private Dialog puntajesP;
    private int m1, m2, m3, m4, m5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.home_l);

        Fade tran=new Fade(Fade.IN);
        Fade tran2=new Fade(Fade.OUT);

        getWindow().setEnterTransition(tran);
        getWindow().setReenterTransition(tran2);
        puntajesP=new Dialog(this);
        puntajesP.setContentView(R.layout.puntajes_a);
        puntajesP.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        puntajesP.setCanceledOnTouchOutside(false);
    }
    public void onResume(){
        super.onResume();
        SharedPreferences datos= PreferenceManager.getDefaultSharedPreferences(this);

        m1=datos.getInt("m1",0);
        m2=datos.getInt("m2",0);
        m3=datos.getInt("m3",0);
        m4=datos.getInt("m4",0);
        m5=datos.getInt("m5",0);

        TextView m1m, m2m, m3m, m4m, m5m;

        m1m=puntajesP.findViewById(R.id.m1m);
        m2m=puntajesP.findViewById(R.id.m2m);
        m3m=puntajesP.findViewById(R.id.m3m);
        m4m=puntajesP.findViewById(R.id.m4m);
        m5m=puntajesP.findViewById(R.id.m5m);

        m1m.setText(""+m1);
        m2m.setText(""+m2);
        m3m.setText(""+m3);
        m4m.setText(""+m4);
        m5m.setText(""+m5);

    }
    public void botones(View v){
        Intent i;
        Slide tran=new Slide(Gravity.LEFT);
        switch (v.getId()){
            case R.id.play:
                i=new Intent(this, Partida.class);
                i.putExtra("tipoP", 1);//aqui es donde se le envia el valor 1, para que inicie la partida por defecto
                getWindow().setExitTransition(tran);
                startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());


                break;

            case R.id.puntajes:
                TextView salir=puntajesP.findViewById(R.id.salir_x);

                salir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        puntajesP.dismiss();
                    }
                });
                puntajesP.show();

                break;

            case R.id.ajustes:

                i=new Intent(this, Ajustes.class);
                getWindow().setExitTransition(tran);
                startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;

        }



    }
}
