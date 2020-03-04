package br.com.softsaj.gibgasbeta;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import Bean.Analise;
import Bean.Remessa;
import Bean.Remetentes;
import Util.AdapterRemessaAceite;

public class Criar_Viagem_Lobby_Analise extends Fragment {

    TextView motivo;
    TextView dataa;
    TextView status;
    static Analise analise;


    Button informar;
    Button replica;
    Button resultado1;
    Button resultado_final;
    LinearLayout aguardando_;
    LinearLayout analise_;
    LinearLayout aguardando_replica;
    LinearLayout decisao_;
    TextView aguardando1;
    TextView aguardando2;

    String tipo;
    Remetentes remetente;

    TextView data_final_resposta;
    TextView data_final_analise1;
    TextView data_final_replica;
    TextView data_final_final;

    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.analise_viagem, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();

        //Inicia FireBase
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        pegaTipo();

        informar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if(status.getText().toString().equals("Esperando resposta do Remetente")){
                    //Sou remetente?
                    if(tipo.equals("R")){
                        //Sou remetente
                        String outro = Criar_Viagem_Lobby.getTipo();
                        Remessa remessa = Criar_Viagem_Lobby.getRemessa();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Criar_Viagem_Lobby()).commit();
                        Criar_Viagem_Lobby.setOutroTipo("Resposta");
                        Criar_Viagem_Lobby.setAnalise(analise);
                        Criar_Viagem_Lobby.setTipo(outro);
                        Criar_Viagem_Lobby.setRemessa(remessa);


                    }else{
                        //Não sou remetente
                        alert("Ação invalida!");
                    }
                }else if(status.getText().toString().equals("Analisando informações")){
                    alert("Ação invalida!");
                }else if(status.getText().toString().equals("Aguardando Replica do Remetente")){
                    alert("Ação invalida!");
                }else if(status.getText().toString().equals("Decisão definitiva")){
                    alert("Ação invalida!");
                }else if(status.getText().toString().equals("Esperando resposta do Transportador")){
                    //Sou transportador?
                    if(tipo.equals("T")){
                        //Sou transportador
                        String outro = Criar_Viagem_Lobby.getTipo();
                        Remessa remessa = Criar_Viagem_Lobby.getRemessa();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Criar_Viagem_Lobby()).commit();
                        Criar_Viagem_Lobby.setOutroTipo("Resposta");
                        Criar_Viagem_Lobby.setAnalise(analise);
                        Criar_Viagem_Lobby.setTipo(outro);
                        Criar_Viagem_Lobby.setRemessa(remessa);
                    }else{
                        //Não sou transportador
                        alert("Ação invalida!");
                    }

                }else if(status.getText().toString().equals("Aguardando Replica do Transportador")){
                    alert("Ação invalida!");
                }
            }
        });

        replica.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                if(status.getText().toString().equals("Esperando resposta do Remetente")){
                    alert("Ação invalida!");
                }else if(status.getText().toString().equals("Analisando informações")){
                    alert("Ação invalida!");
                }else if(status.getText().toString().equals("Aguardando Replica do Remetente")){
                    //Sou remetente?
                    if(tipo.equals("R")){
                        //Sou remetente
                        String outro = Criar_Viagem_Lobby.getTipo();
                        Remessa remessa = Criar_Viagem_Lobby.getRemessa();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Criar_Viagem_Lobby()).commit();
                        Criar_Viagem_Lobby.setOutroTipo("Resposta");
                        Criar_Viagem_Lobby.setAnalise(analise);
                        Criar_Viagem_Lobby.setTipo(outro);
                        Criar_Viagem_Lobby.setRemessa(remessa);
                    }else{
                        //Não sou remetente
                        alert("Ação invalida!");
                    }
                }else if(status.getText().toString().equals("Decisão definitiva")){
                    alert("Ação invalida!");
                }else if(status.getText().toString().equals("Esperando resposta do Transportador")){
                    alert("Ação invalida!");
                }else if(status.getText().toString().equals("Aguardando Replica do Transportador")){
                    //Sou transportador?
                    if(tipo.equals("T")){
                        //Sou transportador
                        String outro = Criar_Viagem_Lobby.getTipo();
                        Remessa remessa = Criar_Viagem_Lobby.getRemessa();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Criar_Viagem_Lobby()).commit();
                        Criar_Viagem_Lobby.setOutroTipo("Resposta");
                        Criar_Viagem_Lobby.setAnalise(analise);
                        Criar_Viagem_Lobby.setTipo(outro);
                        Criar_Viagem_Lobby.setRemessa(remessa);
                    }else{
                        //Não sou transportador
                        alert("Ação invalida!");
                    }
                }
            }
        });

        resultado1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(status.getText().toString().equals("Analisando informações")){
                   alert("Abre pagina com informação");
                }else{
                    alert("Ação invalida!");
                }
            }
        });
        resultado_final.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(status.getText().toString().equals("Decisão definitiva")){
                    alert("Abre pagina com informação");
                }else{
                    alert("Ação invalida!");
                }
            }
        });

    }

    public void pegaTipo(){

        remetente = new Remetentes();
        String id = user.getUid();
        tipo = "T";

        Query query1 = FirebaseDatabase.getInstance().getReference("Remetente")
                .orderByChild("id")
                .equalTo(id);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Passar os dados para a interface grafica
                // alert("Carregando os dados!");
                for(DataSnapshot obj : dataSnapshot.getChildren()){
                   remetente = obj.getValue(Remetentes.class);
                    tipo = "R";
                }

                CarregaAnalise();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Se ocorrer um erro
              //  alert("Ocorreu um erro ao carregar informações sobre usuário!");
                //Query
            }
        });



    }

    private void CarregaAnalise() {

        analise = new Analise();
        alert(Criar_Viagem_Lobby.getRemessa().getChave().toString());

        FirebaseDatabase.getInstance().getReference("Analise")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Passar os dados para a interface grafica
                for(DataSnapshot obj : dataSnapshot.getChildren()){
                    Analise analise1 = obj.getValue(Analise.class);
                     if(analise1.getChave().equals(Criar_Viagem_Lobby.getRemessa().getChave().toString())){
                         analise = analise1;
                     }
                }

                Getstatus();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Se ocorrer um erro
                // alert("Ocorreu um erro ao carregar informações sobre usuário!");
                //Query
            }
        });

    }

    private void Getstatus() {


        motivo.setText(analise.getMotivo_Analise());
        dataa.setText(analise.getData_Inicio_Analise());

        data_final_resposta.setText(analise.getData_Limite_responta());
        data_final_analise1.setText(analise.getData_Limite_analise());
        data_final_replica.setText(analise.getData_Limite_replica());
        data_final_final.setText(analise.getData_Limite_resposta_final());

        if(tipo.equals("T")){
            //Sou Transportador

            if(analise.getQueixou().equals("Transportador")){
                //Eu Reportei

                if(analise.getStatus().equals("Em Ánalise")){

                    status.setText("Esperando resposta do Remetente");
                    informar.setBackgroundColor(Color.parseColor("#666666"));
                    aguardando1.setText("O Remetente deve responder a sua reclamação, Aguarde!");

                }else if(analise.getStatus().equals("Respondeu")){

                    status.setText("Analisando informações");
                    //Muda cor dos componentes
                    informar.setBackgroundColor(Color.parseColor("#666666"));
                    aguardando1.setText("O Remetente respondeu a sua reclamação, Aguarde!");
                    aguardando_.setBackgroundColor(Color.parseColor("#666666"));
                    analise_.setBackgroundColor(Color.parseColor("#FFCC2B46"));

                }else if(analise.getStatus().equals("Resultado 1")){
                    status.setText("Aguardando Replica do Remetente");
                    informar.setBackgroundColor(Color.parseColor("#666666"));
                    aguardando1.setText("O Remetente respondeu a sua reclamação, Aguarde!");
                    aguardando_.setBackgroundColor(Color.parseColor("#666666"));
                    analise_.setBackgroundColor(Color.parseColor("#666666"));
                    aguardando_replica.setBackgroundColor(Color.parseColor("#FFCC2B46"));
                    replica.setBackgroundColor(Color.parseColor("#FFCC2B46"));
                    resultado1.setBackgroundColor(Color.parseColor("#FFCC2B46"));
                    aguardando2.setText("O remetente deve responder à analise parcial, Aguarde!");

                }else if(analise.getStatus().equals("Replicou")){
                    status.setText("Decisão definitiva");
                    informar.setBackgroundColor(Color.parseColor("#666666"));
                    aguardando1.setText("O Remetente respondeu a sua reclamação, Aguarde!");
                    aguardando_.setBackgroundColor(Color.parseColor("#666666"));
                    analise_.setBackgroundColor(Color.parseColor("#666666"));
                    aguardando_replica.setBackgroundColor(Color.parseColor("#666666"));
                    replica.setBackgroundColor(Color.parseColor("#666666"));
                    aguardando2.setText("O remetente respondeu à analise parcial, Aguarde!");
                    decisao_.setBackgroundColor(Color.parseColor("#FFCC2B46"));
                    resultado_final.setBackgroundColor(Color.parseColor("#FFCC2B46"));
                }

            }else{
                //Remetente Reportou

                if(analise.getStatus().equals("Em Ánalise")){

                    status.setText("Esperando resposta do Transportador");
                    informar.setBackgroundColor(Color.parseColor("#FFCC2B46"));
                    aguardando1.setText("Você deve responder a reclamação, Aguarde!");

                }else if(analise.getStatus().equals("Respondeu")){

                    status.setText("Analisando informações");
                    //Muda cor dos componentes
                    informar.setBackgroundColor(Color.parseColor("#666666"));
                    aguardando1.setText("Você respondeu a reclamação, Aguarde!");
                    aguardando_.setBackgroundColor(Color.parseColor("#666666"));
                    analise_.setBackgroundColor(Color.parseColor("#FFCC2B46"));

                }else if(analise.getStatus().equals("Resultado 1")){
                    status.setText("Aguardando Replica do Transportador");
                    informar.setBackgroundColor(Color.parseColor("#666666"));
                    aguardando1.setText("Você respondeu a reclamação, Aguarde!");
                    aguardando_.setBackgroundColor(Color.parseColor("#666666"));
                    analise_.setBackgroundColor(Color.parseColor("#666666"));
                    aguardando_replica.setBackgroundColor(Color.parseColor("#FFCC2B46"));
                    resultado1.setBackgroundColor(Color.parseColor("#FFCC2B46"));
                    replica.setBackgroundColor(Color.parseColor("#FFCC2B46"));
                    aguardando2.setText("Você deve responder à analise parcial!");

                }else if(analise.getStatus().equals("Replicou")){
                    status.setText("Decisão definitiva");
                    informar.setBackgroundColor(Color.parseColor("#666666"));
                    aguardando1.setText("Você respondeu a reclamação, Aguarde!");
                    aguardando_.setBackgroundColor(Color.parseColor("#666666"));
                    analise_.setBackgroundColor(Color.parseColor("#666666"));
                    aguardando_replica.setBackgroundColor(Color.parseColor("#666666"));
                    replica.setBackgroundColor(Color.parseColor("#666666"));
                    aguardando2.setText("Você respondeu à analise parcial, Aguarde!");
                    decisao_.setBackgroundColor(Color.parseColor("#FFCC2B46"));
                    resultado_final.setBackgroundColor(Color.parseColor("#FFCC2B46"));
                }
            }
        }else{
            //Sou Remetente

            if(analise.getQueixou().equals("Remetente")){
                //Eu Reportei

                if(analise.getStatus().equals("Em Ánalise")){

                    status.setText("Esperando resposta do Transportador");
                    informar.setBackgroundColor(Color.parseColor("#666666"));
                    aguardando1.setText("O Transportador deve responder a sua reclamação, Aguarde!");

                }else if(analise.getStatus().equals("Respondeu")){

                    status.setText("Analisando informações");
                    //Muda cor dos componentes
                    informar.setBackgroundColor(Color.parseColor("#666666"));
                    aguardando1.setText("O Transportador respondeu a sua reclamação, Aguarde!");
                    aguardando_.setBackgroundColor(Color.parseColor("#666666"));
                    analise_.setBackgroundColor(Color.parseColor("#FFCC2B46"));

                }else if(analise.getStatus().equals("Resultado 1")){
                    status.setText("Aguardando Replica do Transportador");
                    informar.setBackgroundColor(Color.parseColor("#666666"));
                    aguardando1.setText("O Transportador respondeu a sua reclamação, Aguarde!");
                    aguardando_.setBackgroundColor(Color.parseColor("#666666"));
                    analise_.setBackgroundColor(Color.parseColor("#666666"));
                    aguardando_replica.setBackgroundColor(Color.parseColor("#FFCC2B46"));
                    resultado1.setBackgroundColor(Color.parseColor("#FFCC2B46"));
                    replica.setBackgroundColor(Color.parseColor("#FFCC2B46"));
                    aguardando2.setText("O Transportador deve responder à analise parcial, Aguarde!");

                }else if(analise.getStatus().equals("Replicou")){
                    status.setText("Decisão definitiva");
                    informar.setBackgroundColor(Color.parseColor("#666666"));
                    aguardando1.setText("O Transportador respondeu a sua reclamação, Aguarde!");
                    aguardando_.setBackgroundColor(Color.parseColor("#666666"));
                    analise_.setBackgroundColor(Color.parseColor("#666666"));
                    aguardando_replica.setBackgroundColor(Color.parseColor("#666666"));
                    replica.setBackgroundColor(Color.parseColor("#666666"));
                    aguardando2.setText("O Transportador respondeu à analise parcial, Aguarde!");
                    decisao_.setBackgroundColor(Color.parseColor("#FFCC2B46"));
                    resultado_final.setBackgroundColor(Color.parseColor("#FFCC2B46"));
                }

            }else{
                //Transportador Reportou

                if(analise.getStatus().equals("Em Ánalise")){

                    status.setText("Esperando resposta do Remetente");
                    informar.setBackgroundColor(Color.parseColor("#FFCC2B46"));
                    aguardando1.setText("Você deve responder a reclamação, Aguarde!");

                }else if(analise.getStatus().equals("Respondeu")){

                    status.setText("Analisando informações");
                    //Muda cor dos componentes
                    informar.setBackgroundColor(Color.parseColor("#666666"));
                    aguardando1.setText("Você respondeu a reclamação, Aguarde!");
                    aguardando_.setBackgroundColor(Color.parseColor("#666666"));
                    analise_.setBackgroundColor(Color.parseColor("#FFCC2B46"));

                }else if(analise.getStatus().equals("Resultado 1")){
                    status.setText("Aguardando Replica do Remetente");
                    informar.setBackgroundColor(Color.parseColor("#666666"));
                    aguardando1.setText("Você respondeu a reclamação, Aguarde!");
                    aguardando_.setBackgroundColor(Color.parseColor("#666666"));
                    analise_.setBackgroundColor(Color.parseColor("#666666"));
                    aguardando_replica.setBackgroundColor(Color.parseColor("#FFCC2B46"));
                    resultado1.setBackgroundColor(Color.parseColor("#FFCC2B46"));
                    replica.setBackgroundColor(Color.parseColor("#FFCC2B46"));
                    aguardando2.setText("Você deve responder à analise parcial!");

                }else if(analise.getStatus().equals("Replicou")){
                    status.setText("Decisão definitiva");
                    informar.setBackgroundColor(Color.parseColor("#666666"));
                    aguardando1.setText("Você respondeu a reclamação, Aguarde!");
                    aguardando_.setBackgroundColor(Color.parseColor("#666666"));
                    analise_.setBackgroundColor(Color.parseColor("#666666"));
                    aguardando_replica.setBackgroundColor(Color.parseColor("#666666"));
                    replica.setBackgroundColor(Color.parseColor("#666666"));
                    aguardando2.setText("Você respondeu à analise parcial, Aguarde!");
                    decisao_.setBackgroundColor(Color.parseColor("#FFCC2B46"));
                    resultado_final.setBackgroundColor(Color.parseColor("#FFCC2B46"));
                }
            }
        }

    }

    private void init() {

        analise = new Analise();

        motivo = (TextView) getView().findViewById(R.id.textView91);
        dataa = (TextView) getView().findViewById(R.id.textView92);
        status = (TextView) getView().findViewById(R.id.textView93);
        aguardando1 = (TextView) getView().findViewById(R.id.textView96);
        aguardando2 = (TextView) getView().findViewById(R.id.textView99);
        informar = (Button) getView().findViewById(R.id.button44);
        replica = (Button) getView().findViewById(R.id.button45);

        resultado1 = (Button) getView().findViewById(R.id.button47);
        resultado_final = (Button) getView().findViewById(R.id.button48);

        aguardando_ = (LinearLayout) getView().findViewById(R.id.linearLayout6);
        analise_ = (LinearLayout) getView().findViewById(R.id.linearLayout7);
        aguardando_replica = (LinearLayout) getView().findViewById(R.id.linearLayoutd7);
        decisao_ = (LinearLayout) getView().findViewById(R.id.linearLayoutf7);

        data_final_resposta = (TextView) getView().findViewById(R.id.textView94);
        data_final_analise1 = (TextView) getView().findViewById(R.id.textView8);
        data_final_replica = (TextView) getView().findViewById(R.id.textView98);
        data_final_final = (TextView) getView().findViewById(R.id.textView9h8);

    }

    private void alert(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

}
