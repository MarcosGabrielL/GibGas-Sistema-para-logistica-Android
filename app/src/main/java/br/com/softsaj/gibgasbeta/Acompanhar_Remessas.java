package br.com.softsaj.gibgasbeta;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import Bean.Remessa;

public class Acompanhar_Remessas extends Fragment {

    Button rastrear;
    Button voltar;

    public static Remessa remessa;
    public static String tipo;
    public static Boolean abriuchat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.acompanhar_remessa_remetente, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       init();

        getChildFragmentManager().beginTransaction().replace(R.id.container1, new Criar_Viagem_Lobby()).commit();
        Criar_Viagem_Lobby.setRemessa(remessa);
        Criar_Viagem_Lobby.setTipo("E");

        rastrear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

               // getChildFragmentManager().beginTransaction().replace(R.id.container1,
               getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,new rastrear_fragmento()).commit();

            }
        });
        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

               // getActivity().getBaseContext().startService(new Intent(getActivity(), Maps_Principal_Fragemnt.class));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Remessas()).commit();

            }
        });
    }

    public void init(){
        rastrear = (Button) getView().findViewById(R.id.button30);
        voltar = (Button) getView().findViewById(R.id.button18);
    }

    public static void setRemessa(Remessa r){
        remessa = r;
    }
    public static void setTipo(String t){
        tipo = t;
    }

    public static Remessa getRemessa(){
        return remessa;
    }
    public static String getTipo(){
        return "T";
    }

    public static boolean getChamouChat(){
        return  abriuchat;
    }
}
