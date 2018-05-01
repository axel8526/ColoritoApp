package com.example.usuario.coloritoapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteAbortException;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class Partida extends AppCompatActivity {

    private String[] colores={"AMARILLO","AZUL","ROJO","VERDE"};
    private int tipoP, score, words, intentos, bien, mal, palabraColor, palabraLetra;
    private Long tiempoPartida, tiempoPalabra;
    private boolean timeOn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.partida_a);

        Slide tran=new Slide(Gravity.LEFT);
        getWindow().setEnterTransition(tran);

        Bundle datos=getIntent().getExtras();
        try {
            tipoP = datos.getInt("tipoP");
        }catch (Exception e){
            tipoP=0;
        }
        score=0; words=0; bien=0; mal=0;
    }
    public void onResume(){
        super.onResume();
        SharedPreferences datos= PreferenceManager.getDefaultSharedPreferences(this);

        timeOn=datos.getBoolean("timeOn",false);
        tiempoPartida=Long.parseLong(datos.getString("tiempoPartida","10000"));
        tiempoPartida=tiempoPartida*1000;

        String recupera=datos.getString("intentos","segundo");
        if(recupera.equals("primero")){
            intentos=2;
        }else if(recupera.equals("tercero")){
            intentos=5;
        }else if(recupera.equals("cuarto")){
            intentos=7;
        }else {
            intentos=3;
        }

        String recuperaTW=datos.getString("duracionPalabra","segundo");
        if(recuperaTW.equals("primero")){
            tiempoPalabra=2000l;
        }else if(recuperaTW.equals("tercero")){
            tiempoPalabra=4000l;
        }else if(recuperaTW.equals("cuarto")){
            tiempoPalabra=5000l;
        }else {
            tiempoPalabra=3000l;

        }

        iniciaPartida();

    }
    public void onPause(){
        super.onPause();
        SharedPreferences datos=PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor guarda=datos.edit();

        if(tipoP==1){
            guarda.putInt("score", score);
        }

        guarda.apply();
    }
    public void iniciaPartida(){
        ImageView palabraModoPartida=findViewById(R.id.palabraModoPartida);
        if(tipoP==1){
            palabraModoPartida.setImageResource(R.drawable.intentos);
            intentos=3;
            tiempoPalabra=3000l;
            juego();
        }else if(tipoP==0){

            if(timeOn){
                palabraModoPartida.setImageResource(R.drawable.palabra_timepo);
                iniciaTiempoPartida();
                juego();

            }else{
                palabraModoPartida.setImageResource(R.drawable.intentos);
                juego();
            }
        }
    }
    public void juego(){
        ImageButton correctob, incorrectob;

        TextView valorModoPartida=findViewById(R.id.valorModoPartida);
        if(tipoP==1){
            valorModoPartida.setText(""+intentos);

            if(intentos==0){
                finPartida();
            }

        }else if(!timeOn){
                valorModoPartida.setText(""+intentos);

                if(intentos==0){
                    finPartida();
                }
            }


        words++;
        mostrar();
        duracionPalabra();

        correctob=findViewById(R.id.correctob);
        incorrectob=findViewById(R.id.incorrectob);

        correctob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(palabraColor==palabraLetra){
                    bien++;
                    score++;
                }else{
                    mal++;
                    intentos--;
                }
                timerPalabra.cancel();
                juego();


            }
        });

        incorrectob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(palabraColor!=palabraLetra){
                    bien++;
                    score++;
                }else{
                    mal++;
                    intentos--;
                }
                timerPalabra.cancel();
                juego();

            }
        });

    }
    public void iniciaTiempoPartida(){

        CountDownTimer a=new CountDownTimer(tiempoPartida,1000) {
            @Override
            public void onTick(long l) {
                TextView tiempo=findViewById(R.id.valorModoPartida);
                if(l/1000>9){
                    tiempo.setText(""+(l/1000));
                }else{
                    tiempo.setText("0"+(l/1000));
                }

            }

            @Override
            public void onFinish() {
                finPartida();
                timerPalabra.cancel();

            }
        }.start();
    }
    CountDownTimer timerPalabra;
    public void duracionPalabra(){
        timerPalabra=new CountDownTimer(tiempoPalabra, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                mal++;
                intentos--;
                juego();

            }
        }.start();
    }
    public int aleatorio(){
        int numero;
        Random a=new Random();
        numero=a.nextInt(colores.length);
        return  numero;
    }
    public void colorAzar(){
        palabraColor=aleatorio();
        TextView colorPalabra=findViewById(R.id.palabra);
        switch (palabraColor){
            case 0:
                colorPalabra.setTextColor(Color.YELLOW);
                break;

            case 1:
                colorPalabra.setTextColor(Color.BLUE);
                break;
            case 2:
                colorPalabra.setTextColor(Color.RED);
                break;
            case 3:
                colorPalabra.setTextColor(Color.GREEN);
                break;

        }
    }
    public void mostrar(){
        TextView scorem, wordsm, bien_mal, palabra;
        scorem=findViewById(R.id.mscore);
        wordsm=findViewById(R.id.mwords);
        bien_mal=findViewById(R.id.bien_mal);
        palabra=findViewById(R.id.palabra);

        scorem.setText(""+score);
        wordsm.setText(""+words);
        bien_mal.setText(bien+"/"+mal);

        palabraLetra=aleatorio();
        colorAzar();
        palabra.setText(colores[palabraLetra]);
    }
    public void finPartida(){
        TextView msoore, bien_mal;
        msoore=findViewById(R.id.mscore);
        bien_mal=findViewById(R.id.bien_mal);

        Intent i=new Intent(this, FinPartida.class);
        i.putExtra("tipoP",tipoP);
        i.putExtra("score",score);
        i.putExtra("bien",bien);
        i.putExtra("mal",mal);

        Slide tran=new Slide(Gravity.LEFT);

        Pair[] pair=new Pair[2];
        pair[0]=new Pair<View, String>(msoore,"tscore");
        pair[1]=new Pair<View, String>(bien_mal,"tbien_mal");


        getWindow().setExitTransition(tran);
        ActivityOptions op=ActivityOptions.makeSceneTransitionAnimation(this,pair);

        startActivity(i, op.toBundle());
        finish();
    }
    public void onBackPressed(){

    }

}
