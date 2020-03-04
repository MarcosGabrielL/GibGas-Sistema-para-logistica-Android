package br.com.softsaj.gibgasbeta;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import Bean.Viagem;

public class Criar_Viagem_MeioTransporte extends Fragment {

    Button voltar;
    ImageView pe;
    ImageView bike;
    ImageView moto;
    ImageView trans_publico;
    ImageView carro;
    ImageView caminhao;
    ImageView aviao;
    public static Viagem viagem;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_enviar_meio_transporte, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iniciacomponentes();

        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Criar_Viagem_1()).commit();
            }
        });

        pe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //Configurações da viagem a pé
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Criar_Viagem_Config_Pe()).commit();

            }
        });

        bike.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Criar_Viagem_Config_Bike()).commit();
                //activity_enviar_bike);
            }
        });
        moto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Criar_Viagem_Config_Moto()).commit();
               // alert("Motocicleta");
            }
        });
        trans_publico.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Criar_Viagem_Config_Publico()).commit();
                //alert("Transporte Público");
            }
        });
        carro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Criar_Viagem_Config_Carro()).commit();
                //alert("Carro Proprio");
            }
        });
        caminhao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Criar_Viagem_Config_Caminhao()).commit();
                //alert("Caminhão");
            }
        });
        aviao.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Criar_Viagem_Config_Aviao()).commit();
                //alert("Avião");
            }
        });


    }

    public void iniciacomponentes(){

        viagem = Criar_Viagem_1.getViagem();
        voltar  = (Button) getView().findViewById(R.id.button18);

        pe = (ImageView) getView().findViewById(R.id.imageView5);
        bike = (ImageView) getView().findViewById(R.id.imageView6);
        moto = (ImageView) getView().findViewById(R.id.imageView7);
        trans_publico = (ImageView) getView().findViewById(R.id.imageView9);
        carro = (ImageView) getView().findViewById(R.id.imageView8);
        caminhao = (ImageView) getView().findViewById(R.id.imageView10);
        aviao = (ImageView) getView().findViewById(R.id.imageView11);

    }

    public static Viagem getViagem(){
        return viagem;
    }

    private void alert(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}


