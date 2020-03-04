package br.com.softsaj.gibgasbeta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import Bean.Remetentes;
import Bean.Transportador;
import Util.MaskEditUtil;

public class CadastroTransportador2 extends AppCompatActivity {

    private static Transportador remetente;

    private Button bcadastroremetente;
    private EditText rua;
    private EditText numero;
    private EditText bairro ;
    private Spinner estados;
    private EditText cep ;
    private EditText cidade  ;
    private EditText complemento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_transportador2);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //-------------------------------
        remetente = CadastroTransportador.getTransportador();
        // alert(remetente.getCpf()+"--Nome: "+remetente.getNome());
        bcadastroremetente = (Button) findViewById(R.id.bok2);
        rua = (EditText) findViewById(R.id.emp);
        numero = (EditText) findViewById(R.id.cnpj1);
        bairro = (EditText) findViewById(R.id.eas);
        estados = (Spinner) findViewById(R.id.ramo);
        cep = (EditText) findViewById(R.id.editText2);
        cep.addTextChangedListener(MaskEditUtil.mask(cep, MaskEditUtil.FORMAT_CEP));
        cidade  = (EditText) findViewById(R.id.editText6);
        complemento = (EditText) findViewById(R.id.editText7);

        //Estados Lista
        estados = (Spinner) findViewById(R.id.ramo);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.estados,android.R.layout.simple_spinner_item);
        estados.setAdapter(adapter);

        bcadastroremetente.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                /**
                 * Evento Botão Proxima Pagina
                 */
                PegaValores();
            }
        });
    }


    private void alert(String msg){
        Toast.makeText(CadastroTransportador2.this, msg, Toast.LENGTH_SHORT).show();
    }

    public static Transportador getTransportador(){
        return remetente;
    }

    public void PegaValores() {
        remetente.setRua(rua.getText().toString());
        remetente.setBairro(bairro.getText().toString());
        remetente.setNumero(numero.getText().toString());
        remetente.setCidade(cidade.getText().toString());
        remetente.setEstado(estados.getSelectedItem().toString());
        remetente.setCep(cep.getText().toString());
        remetente.setComplemento(complemento.getText().toString());

        if (!(remetente.getRua().equals("") && remetente.getBairro().equals("") && remetente.getNumero().equals("")
                && remetente.getCidade().equals("") && remetente.getEstado().equals("") && remetente.getCep().equals("")
                && remetente.getComplemento().equals(""))) {
            /**
             * Passa Pra pagina três cadastro remetente
             */
            startActivity(new Intent(getBaseContext(), CadastroTransportador3.class));

        }
    }

    }
