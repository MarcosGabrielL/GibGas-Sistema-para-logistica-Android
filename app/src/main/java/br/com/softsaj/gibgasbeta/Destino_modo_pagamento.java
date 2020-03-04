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

import Bean.Remessa;

public class Destino_modo_pagamento extends Fragment {

    public static Remessa remessa;
    Button cartão;
    Button dinheiro;
    TextView valor;
    static String tipo;
    static  String caminho;


    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_destino_cartao_dinheiro, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();


        Button voltar = (Button) getView().findViewById(R.id.button18);
        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if(caminho.equals("Lista")) {
                    //Volta para dimensionar carga
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new Carregar_Lista()).commit();
                    Carregar_Lista.setRemessa(remessa);
                }else if(caminho.equals("Chamar")) {
                    //Volta para chamar
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new Chamar_transportador()).commit();
                    //cancela A Remessa aceite
                }
            }
        });
        //Botão ação
        cartão.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //Salva na remessa tipo de pagamento cartão
                remessa.setTipoPagamento("Cartão");
                remessa.setValorViagem(valor.getText().toString());

                //Abre Tela Looby para tratamentos
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Destino_Escolhe_Cartão()).commit();
                Destino_Escolhe_Cartão.setRemessa(remessa);
                Destino_Escolhe_Cartão.setTipo(tipo);
                Destino_Escolhe_Cartão.setCaminho(caminho);
            }
        });
        dinheiro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //Salva na remessa tipo de pagamento Dinheiro
                remessa.setTipoPagamento("Dinheiro");
                remessa.setValorViagem(valor.getText().toString());
                remessa.setStatus("Aceito");

                //Abre tela de confirmação
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Destino_confirma_pagamento()).commit();
                Destino_confirma_pagamento.setRemessa(remessa);
                Destino_confirma_pagamento.setTipo(tipo);
                Destino_confirma_pagamento.setCaminho(caminho);
            }
        });

        if(caminho.equals("Lista") ||caminho.equals("Confirmar") ) {
            valor.setText(remessa.getValorViagem());
        }

    }

    public void init(){

       // remessa = Destino_Dimen_Carga.getRemessa();
        cartão = (Button) getView().findViewById(R.id.button6);
        dinheiro = (Button) getView().findViewById(R.id.button7);
        valor = (TextView) getView().findViewById(R.id.textView75);

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
