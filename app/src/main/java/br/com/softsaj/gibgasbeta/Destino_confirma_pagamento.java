package br.com.softsaj.gibgasbeta;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import Bean.Remessa;

public class Destino_confirma_pagamento extends Fragment {

    public static Remessa remessa;
    Button confirmar;
    TextView tipo_pagamento;
    TextView final_cartao_ou_dinheiro;
    TextView valor;
    static String tipo;
    static  String caminho;


    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_confirmacao_pagamento, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();


        Button voltar = (Button) getView().findViewById(R.id.button18);
        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //Abre selecionar modo pagamento
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Destino_modo_pagamento()).commit();
                Destino_modo_pagamento.setRemessa(remessa);
                Destino_modo_pagamento.setTipo(tipo);
                Destino_modo_pagamento.setCaminho(caminho);
            }
        });

        //Botão ação
        confirmar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //Abre tela de processamento
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Destino_processamento_pagamento()).commit();
                Destino_processamento_pagamento.setRemessa(remessa);
                Destino_processamento_pagamento.setTipo(tipo);
                Destino_processamento_pagamento.setCaminho(caminho);

                //Abre Tela Looby para tratamentos
               // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Criar_Viagem_Lobby()).commit();
               // Criar_Viagem_Lobby.setRemessa(remessa);
                //Criar_Viagem_Lobby.setTipo(tipo);
            }
        });

        if(caminho.equals("Lista") ||caminho.equals("Confirmar") ) {

            valor.setText("R$ "+remessa.getValorViagem());
            tipo_pagamento.setText("Meio de Pagamento: " + remessa.getTipoPagamento());

            if(remessa.getTipoPagamento().equals("Cartão")){
                final_cartao_ou_dinheiro.setText("FINAL: "+ remessa.getUltimos4numerosCartão());
            } else if(remessa.getTipoPagamento().equals("Dinheiro")){
                final_cartao_ou_dinheiro.setText("");
            }

        }

    }

    public void init(){


        confirmar = (Button) getView().findViewById(R.id.button6);
        valor = (TextView) getView().findViewById(R.id.textView15);
        tipo_pagamento = (TextView) getView().findViewById(R.id.textView11);
        final_cartao_ou_dinheiro = (TextView) getView().findViewById(R.id.textView3);

    }

    private void alert(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void setTipo(String t){
        tipo = t;
    }
    public static void setCaminho(String t){
        caminho = t;
    }

    public static void setRemessa(Remessa r){
        remessa = r;
    }

    public static Remessa getRemessa(){
        return remessa;
    }
}
