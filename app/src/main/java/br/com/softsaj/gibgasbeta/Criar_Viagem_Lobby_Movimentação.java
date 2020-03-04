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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Bean.Message;
import Bean.Remessa;

public class Criar_Viagem_Lobby_Movimentação extends Fragment {

    Button Produto_Entregue;
    Button Remessa_A_Caminho;
    Button Entregue;
    Button Avaliar;


    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView alerta;
    TextView titulo;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.confirmar_inicio_viagem, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();

        //Busca status da remessa
        status();

        //Inicia FireBase
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();



        Produto_Entregue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //Produto foi entregue ao transportador
                //Muda status remessa para Entregue_ao_Transportador
                Remessa remessa = Criar_Viagem_Lobby.getRemessa();

                if(remessa.getStatus().equals("NaLista") || remessa.getStatus().equals("Aceito")) {
                    if(remessa.getChave().toString().equals("")){
                        alert("Sem Chave");
                    }else {
                        remessa.setStatus("Entregue_ao_Transportador");
                        databaseReference.child("Remessa_Aceite").child(remessa.getChave()).setValue(remessa);

                        MandaMensagem("Entreguei a mercadoria sobre os cuidados do Transportador!");
                        //Muda Cor botão
                        Produto_Entregue.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
                        Produto_Entregue.setTextColor(Color.parseColor("#ffffff"));

                        Criar_Viagem_Lobby.setRemessa(remessa);
                    }
                }

            }
        });
        Remessa_A_Caminho.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {



                //Produto está a caminho do entregador



            }
        });
        Entregue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //Produto foi entregue ao destinatario

                Remessa remessa = Criar_Viagem_Lobby.getRemessa();
                if(remessa.getStatus().toString().equals("Entregador_Entregou")){
                    //Muda status remessa para Entregue
                    remessa.setStatus("Entregue");
                    databaseReference.child("Remessa_Aceite").child(remessa.getChave()).setValue(remessa);
                    //Atualiza Remessa
                    Criar_Viagem_Lobby.setRemessa(remessa);
                    //Muda Cor Botão
                    Produto_Entregue.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
                    Produto_Entregue.setTextColor(Color.parseColor("#ffffff"));

                    Remessa_A_Caminho.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
                    Remessa_A_Caminho.setTextColor(Color.parseColor("#ffffff"));

                    Entregue.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
                    Entregue.setTextColor(Color.parseColor("#ffffff"));

                    Avaliar.setBackgroundColor(Color.parseColor("#FF00DDFF"));
                    Avaliar.setTextColor(Color.parseColor("#ffffff"));
                }

            }
        });
        Avaliar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //Avaliar Transportador
                alert("Avaliar Transportador");

            }
        });
    }

    public void init(){

        Produto_Entregue = (Button) getView().findViewById(R.id.button34);
        Remessa_A_Caminho = (Button) getView().findViewById(R.id.button35);
        Entregue = (Button) getView().findViewById(R.id.button36);
        Avaliar = (Button) getView().findViewById(R.id.button37);
        alerta = (TextView) getView().findViewById(R.id.textView86);
        titulo = (TextView) getView().findViewById(R.id.textView48);

    }

    public void status(){

        Remessa remessa = Criar_Viagem_Lobby.getRemessa();

        if(remessa.getStatus().equals("Mercadoria_Entregue")){
            //Botão fica escuro com texto branco
            Produto_Entregue.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
            Produto_Entregue.setTextColor(Color.parseColor("#ffffff"));

            titulo.setText("Aguardando Transportador Iniciar Viagem!");

        }else if(remessa.getStatus().equals("Entregue_ao_Transportador")){
            Produto_Entregue.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
            Produto_Entregue.setTextColor(Color.parseColor("#ffffff"));

            titulo.setText("Aguardando Transportador Confirmar Recebimento!");

        }else if(remessa.getStatus().equals("A Caminho")){
            Produto_Entregue.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
            Produto_Entregue.setTextColor(Color.parseColor("#ffffff"));

            Remessa_A_Caminho.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
            Remessa_A_Caminho.setTextColor(Color.parseColor("#ffffff"));

            titulo.setText("Aguardando Transportador Finalizar Entrega!");

        }else if(remessa.getStatus().equals("Entregue")){
            Produto_Entregue.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
            Produto_Entregue.setTextColor(Color.parseColor("#ffffff"));

            Remessa_A_Caminho.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
            Remessa_A_Caminho.setTextColor(Color.parseColor("#ffffff"));

            Entregue.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
            Entregue.setTextColor(Color.parseColor("#ffffff"));

            Avaliar.setBackgroundColor(Color.parseColor("#FF00DDFF"));
            Avaliar.setTextColor(Color.parseColor("#ffffff"));

            titulo.setText("Avalie o Transportador!");

        }else if(remessa.getStatus().equals("Entregador_Entregou")){
            Produto_Entregue.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
            Produto_Entregue.setTextColor(Color.parseColor("#ffffff"));

            Remessa_A_Caminho.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
            Remessa_A_Caminho.setTextColor(Color.parseColor("#ffffff"));

            Entregue.setBackgroundColor(Color.parseColor("#FF00DDFF"));
            Entregue.setTextColor(Color.parseColor("#ffffff"));

            titulo.setText("Aguardando Confirmação Entrega e Avaliação!");

            alerta.setText("Transportador Entregou o produto! Verifique com o cliente, e confirme a entrega!");

        }else if(remessa.getStatus().equals("Avaliado") || remessa.getStatus().equals("Em Ánalise") || remessa.getStatus().equals("Cancelado")){
            Produto_Entregue.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
            Produto_Entregue.setTextColor(Color.parseColor("#ffffff"));

            Remessa_A_Caminho.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
            Remessa_A_Caminho.setTextColor(Color.parseColor("#ffffff"));

            Entregue.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
            Entregue.setTextColor(Color.parseColor("#ffffff"));

            Avaliar.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
            Avaliar.setTextColor(Color.parseColor("#ffffff"));
            titulo.setText("Entrega Finalizada!");

        }
    }

    public void MandaMensagem(String mensage){

        //Pega informações da mensagens
        String msg = mensage;

        /*
        if(tipo.equals("T")){
            fromid = user.getUid();
            toid = remessa.getIdRemetente();
        }else{
         */
        Remessa remessa = Criar_Viagem_Lobby.getRemessa();
          String fromid = user.getUid();
          String toid = remessa.getIdEntregador();

        long tempo = System.currentTimeMillis();

        //Cria objeto message com tais informações
        Message message = new Message();
        message.setFromId(fromid);
        message.setToId(toid);
        message.setTime(tempo);
        message.setText(msg);
        message.setIdEntrega(remessa.getIdEntrega());

        if(!message.getText().isEmpty()){
            //Salva Mensagens no Banco de dados
            databaseReference.child("Mensagens").push().setValue(message);
        }

    }

    private void alert(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
