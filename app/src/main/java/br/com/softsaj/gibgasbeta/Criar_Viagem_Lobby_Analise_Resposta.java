package br.com.softsaj.gibgasbeta;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import Bean.Analise;
import Bean.Remessa;

public class Criar_Viagem_Lobby_Analise_Resposta extends Fragment {

    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    TextView data_limite;
    TextView motivo_ou_analise;
    EditText resposta;
    Button anexo;
    Button enviar;
    Button voltar;

    static Analise analise;
    static String tipo;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.analise_resposta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();

        preenche();

        //Inicia FireBase
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //PAgina de Analise
                        String outro = Criar_Viagem_Lobby.getTipo();
                        Remessa remessa = Criar_Viagem_Lobby.getRemessa();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Criar_Viagem_Lobby()).commit();
                        Criar_Viagem_Lobby.setOutroTipo("Analise");
                        Criar_Viagem_Lobby.setAnalise(analise);
                        Criar_Viagem_Lobby.setTipo(outro);
                        Criar_Viagem_Lobby.setRemessa(remessa);

            }
        });

        anexo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                alert("Ação indisponivel no momento");
                //Carrega Anexo
                //getChildFragmentManager().beginTransaction().replace(R.id.container1, new Carregar_Anexo()).commit();

            }
        });

        enviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Atualiza();

            }
        });
    }

    private void preenche() {

        if(analise.getStatus().equals("Em Ánalise")){
            //Quixoso veio responder queixa
            data_limite.setText(analise.getData_Limite_responta());
            motivo_ou_analise.setText(analise.getMotivo_Analise());


        }else if(analise.getStatus().equals("Resultado 1")){
            //Queixoso veio replicar analise parcial
            data_limite.setText(analise.getData_Limite_replica());
            motivo_ou_analise.setText(analise.getAnalise1());

        }

    }

    public void Atualiza(){

        Date a = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        if(analise.getStatus().equals("Em Ánalise")){
            //Quixoso veio responder queixa
            analise.setResposta(resposta.getText().toString());
            analise.setData_responta(formatador.format(a));
            analise.setStatus("Respondeu");

            //Atualiza analise
            databaseReference.child("Analise").child(analise.getChave()).setValue(analise);


            String outro = Criar_Viagem_Lobby.getTipo();
            Remessa remessa = Criar_Viagem_Lobby.getRemessa();
            remessa.setStatus("Respondeu");
            //Atualiza remessa
            databaseReference.child("Remessa_Aceite").child(remessa.getChave()).setValue(remessa);

            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Criar_Viagem_Lobby()).commit();
            Criar_Viagem_Lobby.setOutroTipo("Analise");
            Criar_Viagem_Lobby.setAnalise(analise);
            Criar_Viagem_Lobby.setTipo(outro);
            Criar_Viagem_Lobby.setRemessa(remessa);


        }else if(analise.getStatus().equals("Resultado 1")){
            //Queixoso veio replicar analise parcial
            analise.setReplica(resposta.getText().toString());
            analise.setData_replica(formatador.format(a));
            analise.setStatus("Replicou");

            //atualiza analise
            databaseReference.child("Analise").child(analise.getChave()).setValue(analise);

            String outro = Criar_Viagem_Lobby.getTipo();
            Remessa remessa = Criar_Viagem_Lobby.getRemessa();
            remessa.setStatus("Replicou");
            //Atualiza remessa
            databaseReference.child("Remessa_Aceite").child(remessa.getChave()).setValue(remessa);

            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Criar_Viagem_Lobby()).commit();
            Criar_Viagem_Lobby.setOutroTipo("Analise");
            Criar_Viagem_Lobby.setAnalise(analise);
            Criar_Viagem_Lobby.setTipo(outro);
            Criar_Viagem_Lobby.setRemessa(remessa);
        }

    }

    private void init() {

        data_limite = (TextView) getView().findViewById(R.id.textView94);
        motivo_ou_analise = (TextView) getView().findViewById(R.id.textView96);
        resposta = (EditText) getView().findViewById(R.id.editText31);
        anexo = (Button) getView().findViewById(R.id.button46);
        enviar = (Button) getView().findViewById(R.id.button44);
        voltar = (Button) getView().findViewById(R.id.button18);

    }

    public static void setTipo(String tp){
        tipo = tp;
    }

    public static void setAnalise(Analise a){
        analise = a;
    }

    private void alert(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
