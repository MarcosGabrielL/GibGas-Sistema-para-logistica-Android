package br.com.softsaj.gibgasbeta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Tutorial extends AppCompatActivity {

    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);


        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        count = 0;


        //viewFlipper.setFlipInterval(2000);
        //viewFlipper.startFlipping();

        Button voltar = (Button) findViewById(R.id.button49);
        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if(count == 0){
                    //Volta para fragment anterior
                    startActivity(new Intent(getBaseContext(), Login.class));

                }else if(count == 1){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new Tutorial1()).commit();
                    count = 0;

                }else if(count == 2){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new Tutorial2()).commit();
                    count = 1;
                }

            }
        });
        Button proximo = (Button) findViewById(R.id.button50);
        proximo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if(count == 0){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new Tutorial2()).commit();
                    count = 1;

                }else if(count == 1){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new Tutorial3()).commit();
                    count = 2;

                }else if(count == 2){
                    //Vai pra pagina seguinte
                    //Pagian escolha
                    startActivity(new Intent(getBaseContext(), RemetenteOuTransportador.class));
                }

            }
        });

        //Primeiro Slide
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new Tutorial1()).commit();

    }


}
