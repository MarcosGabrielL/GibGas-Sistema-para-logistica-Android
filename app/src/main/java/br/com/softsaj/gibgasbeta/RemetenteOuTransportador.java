package br.com.softsaj.gibgasbeta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class RemetenteOuTransportador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remetente_ou_transportador);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button bcadastroremetente = (Button) findViewById(R.id.button7);
        bcadastroremetente.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                /**
                 * Evento Botão Cadastrar Remetente
                 */
                startActivity(new Intent(getBaseContext(), CadastroRemetente.class));
            }
        });
        Button bcadastrotranspor = (Button) findViewById(R.id.button6);
        bcadastrotranspor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                /**
                 * Evento Botão Cadastrar Transportador
                 */
                startActivity(new Intent(getBaseContext(), CadastroTransportador.class));
            }
        });

    }
}
