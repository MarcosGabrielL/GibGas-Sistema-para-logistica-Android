package br.com.softsaj.gibgasbeta;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import Bean.Viagem;
import Util.MaskEditUtil;

public class Criar_Viagem_1 extends Fragment {

    Spinner estado_origem;
    Spinner estado_destino;
    EditText cidade_origem;
    EditText cidade_destino;
    EditText data_saida;
    EditText hora_saida;
    EditText data_chegada;
    EditText valor;
    Button ok;
    Button voltar;
    public static Viagem viagem;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_enviar_rota, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iniciacomponetes();

        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
              //  alert("Aguarde!");

                Paradas();
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //Abre Mapa para escolha
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Destino_Escolhe_mapa()).commit();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Maps_Principal_Trans_Fragemnt()).commit();
            }
        });

    }

    public void iniciacomponetes(){

        viagem = new Viagem();
        ok = (Button) getView().findViewById(R.id.bok3);
        voltar = (Button) getView().findViewById(R.id.button18);
        cidade_destino = (EditText) getView().findViewById(R.id.editText22);
        cidade_origem = (EditText) getView().findViewById(R.id.editText21);
        data_saida  = (EditText) getView().findViewById(R.id.editText23);
        data_saida.addTextChangedListener(MaskEditUtil.mask(data_saida, MaskEditUtil.FORMAT_DATE));
        data_chegada  = (EditText) getView().findViewById(R.id.editText25);
        data_chegada.addTextChangedListener(MaskEditUtil.mask(data_chegada, MaskEditUtil.FORMAT_DATE));
        hora_saida = (EditText) getView().findViewById(R.id.editText24);
        valor = (EditText) getView().findViewById(R.id.editText28);

        estado_origem = (Spinner) getView().findViewById(R.id.spinner4);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),R.array.estados,android.R.layout.simple_spinner_item);
        estado_origem.setAdapter(adapter);
        estado_destino = (Spinner) getView().findViewById(R.id.spinner5);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(getActivity(),R.array.estados,android.R.layout.simple_spinner_item);
        estado_destino.setAdapter(adapter1);

    }

    public void Paradas(){
        if(cidade_origem.getText().toString().equals("") || cidade_destino.getText().toString().equals("") ||
                data_saida.getText().toString().equals("") || data_chegada.getText().toString().equals("") ||
                hora_saida.getText().toString().equals("") || valor.getText().toString().equals("")){
                    alert("Complete todos os campos!");
        }else{
            viagem.setCidade_origem(cidade_origem.getText().toString().trim());
            viagem.setCidade_destino(cidade_destino.getText().toString().trim());
            viagem.setData_saida(data_saida.getText().toString().trim());
            viagem.setData_chegada(data_chegada.getText().toString().trim());
            viagem.setEstado_origem(estado_origem.getSelectedItem().toString());
            viagem.setEstado_destino(estado_destino.getSelectedItem().toString());
            viagem.setCidade_destino_valor(valor.getText().toString().trim());

            //Chama Paradas
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new Criar_Viagem_MeioTransporte()).commit();
        }



    }

    private void alert(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public static Viagem getViagem(){
        return  viagem;
    }
}
