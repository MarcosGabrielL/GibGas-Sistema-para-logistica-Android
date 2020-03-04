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
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import Bean.Analise;
import Bean.Remessa;

public class Criar_Viagem_Lobby extends Fragment {

    Button message;
    static Button call;
    Button ok;
    Button cancel;
    Button erro;
    Button minimizar;
    public static Remessa remessa;
    public static String tipo;
    public static Boolean abriuchat;
    public static String outrotipo;
    public static Analise analise;

    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.destino_ajuste_final_lobby, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Inicia FireBase
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


        initicomponentes();

         //alert("Entregador: "+remessa.getIdEntregador());
        if(tipo.equals("A")){
            Date a = new Date();
            SimpleDateFormat formatador = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            remessa.setStatus("Aceito");
            remessa.setChave(remessa.getEntregaCidade()+"--"+remessa.getIdRemetente()+"--yYyYy"+formatador.format(a));
            databaseReference.child("Remessa_Aceite").child(remessa.getChave()).setValue(remessa);
            getChildFragmentManager().beginTransaction().replace(R.id.container1, new Criar_Viagem_Lobby_mensagem()).commit();
        }else{
            if(outrotipo==null){
                alert("outro = null");
                getChildFragmentManager().beginTransaction().replace(R.id.container1, new Criar_Viagem_Lobby_mensagem()).commit();
            }else{
                if(outrotipo.equals("Resposta")){

                    //PAgina de Resposta/Replica
                    getChildFragmentManager().beginTransaction().replace(R.id.container1, new Criar_Viagem_Lobby_Analise_Resposta()).commit();
                    Criar_Viagem_Lobby_Analise_Resposta.setAnalise(analise);
                    Criar_Viagem_Lobby_Analise_Resposta.setTipo(outrotipo);

                }else if(outrotipo.equals("Analise")){
                    //PAgina de Analise
                    getChildFragmentManager().beginTransaction().replace(R.id.container1, new Criar_Viagem_Lobby_Analise()).commit();
                }else {
                    getChildFragmentManager().beginTransaction().replace(R.id.container1, new Criar_Viagem_Lobby_mensagem()).commit();
                }
            }

        }

        abriuchat = true;

        message.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Chama tela de escolha carro
               // alert("Enviar Mensagem");
                getChildFragmentManager().beginTransaction().replace(R.id.container1, new Criar_Viagem_Lobby_mensagem()).commit();

                abriuchat = true;
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //PAgina de Analise
                getChildFragmentManager().beginTransaction().replace(R.id.container1, new Criar_Viagem_Lobby_Analise()).commit();

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //Movimentação
                //Vê se é Remetente ou Transportador
                if(GibGas.getRetorno().equals("Remetente")){

                    getChildFragmentManager().beginTransaction().replace(R.id.container1, new Criar_Viagem_Lobby_Movimentação()).commit();

                }else{
                    getChildFragmentManager().beginTransaction().replace(R.id.container1, new Criar_Viagem_Lobby_Movimentação_Transportador()).commit();

                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //Cancela Entrega
                if(remessa.getStatus().equals("Em Ánalise") || remessa.getStatus().equals("Cancelado")){
                    alert("Ação Inválida!");
                }else {
                    //Vê se é Remetente ou Transportador
                    if (GibGas.getRetorno().equals("Remetente")) {

                        getChildFragmentManager().beginTransaction().replace(R.id.container1, new Criar_Viagem_Lobby_Cancelar()).commit();

                    } else {

                        getChildFragmentManager().beginTransaction().replace(R.id.container1, new Criar_Viagem_Lobby_Cancelar_Transportador()).commit();
                    }
                }

            }
        });
        erro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if(remessa.getStatus().equals("Em Ánalise") || remessa.getStatus().equals("Cancelado")){
                    alert("Ação Inválida!");
                }else {

                    //Reporta Erro
                    if (GibGas.getRetorno().equals("Remetente")) {

                        getChildFragmentManager().beginTransaction().replace(R.id.container1, new Criar_Viagem_Lobby_Reportar()).commit();

                    } else {
                        getChildFragmentManager().beginTransaction().replace(R.id.container1, new Criar_Viagem_Lobby_Reportar_Transportador()).commit();

                    }
                }

            }
        });
        minimizar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //minimiza a tela lobby e volta ao mapa
               voltar();

            }
        });

        if(remessa.getStatus().toString().equals("Em Ánalise")
                || remessa.getStatus().toString().equals("Cancelado")
                || remessa.getStatus().toString().equals("Respondeu")
                || remessa.getStatus().toString().equals("Resultado 1")
                || remessa.getStatus().toString().equals("Replicou")){
            call.setBackgroundResource(R.drawable.ic_broken_image_redss_24dp);
        }


    }
    public void voltar(){

        //Verifica o tipo

        startActivity(new Intent(getActivity().getBaseContext(), GibGas.class));
    }

    public void initicomponentes(){
        message = (Button) getView().findViewById(R.id.button19);
        call = (Button) getView().findViewById(R.id.button20);
        ok = (Button) getView().findViewById(R.id.button22);
        cancel = (Button) getView().findViewById(R.id.button21);
        erro = (Button) getView().findViewById(R.id.button23);
        minimizar = (Button) getView().findViewById(R.id.button18);

        abriuchat = false;

    }

    public static void mudaicone(){

        call.setBackgroundResource(R.drawable.ic_broken_image_redss_24dp);
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

    public static void setOutroTipo(String s){
        outrotipo = s;
    }

    public static void setAnalise(Analise a){
        analise = a;
    }

    private void alert(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

}
