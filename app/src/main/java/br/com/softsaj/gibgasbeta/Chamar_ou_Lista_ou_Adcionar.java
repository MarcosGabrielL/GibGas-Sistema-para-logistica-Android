package br.com.softsaj.gibgasbeta;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import Bean.Remessa;

public class Chamar_ou_Lista_ou_Adcionar extends Fragment {

    private Button Chamar;
    private Button Lista;
    private Button Add;
    public static Remessa remessa;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.destino_chamar_lista_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        remessa = Destino_Dimen_Carga.getRemessa();
        Chamar = (Button) getView().findViewById(R.id.button13);
        Lista = (Button) getView().findViewById(R.id.button15);
        Add = (Button) getView().findViewById(R.id.button16);
        Button voltar = (Button) getView().findViewById(R.id.button18);
        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //Abre Mapa para escolha
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Destino_Escolhe_mapa()).commit();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Destino_Dimen_Carga()).commit();
            }
        });

        Chamar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Chamar_transportador()).commit();
            }
        });
        Lista.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Carregar_Lista()).commit();
                Carregar_Lista.setRemessa(remessa);

            }
        });
        Add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Add_Remessas_Disponiveis()).commit();

            }
        });


    }

    public static Remessa getRemessa(){
        return remessa;
    }

}
