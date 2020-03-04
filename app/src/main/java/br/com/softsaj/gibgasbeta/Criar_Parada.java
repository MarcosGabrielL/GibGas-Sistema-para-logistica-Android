package br.com.softsaj.gibgasbeta;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Bean.Parada;
import Bean.Viagem;

public class Criar_Parada extends Fragment {

    static Viagem viagem;
    Button voltar;
    Button ok;
    EditText origem;
    EditText destino;
    EditText datas;
    EditText valor;
    TextView taxa;
    static ArrayList<Parada> paradas;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_enviar_criar_paradas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Inicializa os componentes
        init();

        //Ação focuslost edittext valor
        valor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //PEGA VALORES DA PARADA
                String val = valor.getText().toString().trim();
                String ori = origem.getText().toString().trim();
                String dest = destino.getText().toString().trim();

                float valf = Float.parseFloat(val);

                //CALCULA DISTANCIA ENTRE AS CIDADES *&
                int distancia = CalculaDistancia(ori,dest);

                //PEGA O TIPO DE TRANSPORTE
                String tipo_transporte = viagem.getTipoAuto();

                //CALCULA CUSTO MEDIO DA VIAGEM

                //CALCULA VALOR DA VIAGEM

                //CALCULA TAXA
                taxa.setText("Taxa de R$ "+Float.parseFloat(valor.getText().toString().trim())*0.20);

                //MOSTRA INFORMAÇÕES NA TELA

            }
        });


        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //volta pra origem/destino
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Criar_Viagem_Paradas()).commit();
                Criar_Viagem_Paradas.setViagem(viagem);
                Criar_Viagem_Paradas.setParada(paradas);
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //Adiciona parada
                addparadas();

                //volta pra origem/destino
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Criar_Viagem_Paradas()).commit();
                Criar_Viagem_Paradas.setViagem(viagem);
            }
        });

    }

    public void init(){
        voltar = (Button) getView().findViewById(R.id.button18) ;
        ok = (Button) getView().findViewById(R.id.bok3) ;
        origem = (EditText) getView().findViewById(R.id.editText20) ;
        destino = (EditText) getView().findViewById(R.id.editText19) ;
        datas = (EditText) getView().findViewById(R.id.editText26) ;
        valor = (EditText) getView().findViewById(R.id.editText27) ;
        taxa = (TextView) getView().findViewById(R.id.textView83) ;
        paradas = new ArrayList<Parada>();
    }

    public void addparadas(){

        if(origem.getText().toString().isEmpty() ||
                destino.getText().toString().isEmpty() ||
                datas.getText().toString().isEmpty() ||
                valor.getText().toString().isEmpty()){
            alert("Complete todos os campos obrigatórios!");
        }else{
            Parada parada = new Parada();
            parada.setDatachegada(datas.getText().toString().trim());
            parada.setDestino(destino.getText().toString().trim());
            parada.setOrigem(origem.getText().toString().trim());
            parada.setValor(valor.getText().toString().trim());

            if(viagem.getParadas() == null || viagem.getParadas().size() == 0) {
                paradas.add(parada);
                viagem.setParadas(paradas);
            }else {
                paradas = viagem.getParadas();
                paradas.add(parada);
                viagem.setParadas(paradas);
            }
        }
    }

    public int CalculaDistancia(String Cidade_Origem, String Cidade_Destino){

        //PEGA LATITUDE E LONGITUDE DAS CIDADES

        //CALCULA DISTANCIA ENTRE ELES


        return 0;
    }

    public static void setViagem(Viagem v){
        viagem = v;
    }

    private void alert(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }


}
