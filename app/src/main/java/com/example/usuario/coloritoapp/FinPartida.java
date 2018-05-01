package com.example.usuario.coloritoapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteAbortException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class FinPartida extends AppCompatActivity {

    private int m1, m2, m3, m4, m5, tipoP, score, record;
    TextView msoore, bien_mal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fin_partida_a);
        Slide tran=new Slide(Gravity.RIGHT);
        getWindow().setEnterTransition(tran);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Bundle datos=getIntent().getExtras();

        tipoP=datos.getInt("tipoP");


        msoore=findViewById(R.id.mscore);
        bien_mal=findViewById(R.id.bien_mal);

        msoore.setText(""+datos.getInt("score"));
        bien_mal.setText(""+datos.get("bien")+"/"+datos.getInt("mal"));

        m1=0; m2=0; m3=0; m4=0; m5=0;

    }
    public void onResume(){
        super.onResume();
        SharedPreferences datos= PreferenceManager.getDefaultSharedPreferences(this);

            score=datos.getInt("score",0);


            m1=datos.getInt("m1",0);
            m2=datos.getInt("m2",0);
            m3=datos.getInt("m3",0);
            m4=datos.getInt("m4",0);
            m5=datos.getInt("m5",0);

            record();

            if(tipoP==1){
                puntajes();
            }





    }
    public void onPause(){
        super.onPause();
        SharedPreferences datos=PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor guarda=datos.edit();

        if(tipoP==1){
            guarda.putInt("m1",m1);
            guarda.putInt("m2",m2);
            guarda.putInt("m3",m3);
            guarda.putInt("m4",m4);
            guarda.putInt("m5",m5);
        }

        guarda.apply();
    }
    public void record(){
        ImageView palabraRecord=findViewById(R.id.palabraRecord);
        TextView recordm=findViewById(R.id.record);
        if(score>m1){
            palabraRecord.setImageResource(R.drawable.newrecord);
            recordm.setText(""+score);
        }else{
            palabraRecord.setImageResource(R.drawable.record);
            recordm.setText(""+m1);
        }
    }
    public void puntajes(){
        if(score>=m1){
            m5=m4;
            m4=m3;
            m3=m2;
            m2=m1;
            m1=score;
        }else if(score<m1 && score>=m2){
            m5=m4;
            m4=m3;
            m3=m2;
            m2=score;
        }else if(score<m2 && score>=m3){
            m5=m4;
            m4=m3;
            m3=score;

        }else if(score<m3 && score>=m4){
            m5=m4;
            m4=score;

        }else if(score<m4 && score>=m5){
            m5=score;

        }
    }

    public void botones(View v){
        Intent i;
        Transition tran;

        switch (v.getId()){
            case R.id.play:
                i=new Intent(this, Partida.class);
                i.putExtra("tipoP",tipoP);
                tran=new Slide(Gravity.RIGHT);


                Pair[] pair=new Pair[2];
                pair[0]=new Pair<View, String>(msoore,"tscore");
                pair[1]=new Pair<View, String>(bien_mal,"tbien_mal");


                getWindow().setExitTransition(tran);
                ActivityOptions op=ActivityOptions.makeSceneTransitionAnimation(this,pair);
                startActivity(i, op.toBundle());
                finish();
                break;

            case R.id.ajustes:
                i=new Intent(this, Ajustes.class);

                tran=new Fade(Fade.OUT);

                getWindow().setExitTransition(tran);
                startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                finish();
                break;

            case R.id.homero:
                i=new Intent(this, Home.class);

                tran=new Fade(Fade.OUT);

                getWindow().setExitTransition(tran);
                startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                finish();
                break;
        }

    }

}
