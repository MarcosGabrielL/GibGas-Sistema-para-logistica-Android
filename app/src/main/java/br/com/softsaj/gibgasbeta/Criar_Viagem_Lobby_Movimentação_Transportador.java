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

public class Criar_Viagem_Lobby_Movimentação_Transportador extends Fragment {

    Button Produto_Entregue;
    Button Remessa_A_Caminho;
    Button Entregue;
    Button Avaliar;
    String status;
    TextView alerta;
    TextView titulo;

    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.confirmar_inicio_viagem_transportador, container, false);
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
                Remessa remessa = Criar_Viagem_Lobby.getRemessa();

                if(status.equals("Mercadoria_Entregue")
                        || status.equals("A Caminho") || status.equals("Entregue")
                        || status.equals("Avaliado")){
                    alert("Ação inválida!");
                }else{
                    //Muda status remessa para Entregue
                    remessa.setStatus("Mercadoria_Entregue");
                    databaseReference.child("Remessa_Aceite").child(remessa.getChave()).setValue(remessa);
                    //Muda Cor botão
                    Produto_Entregue.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
                    Produto_Entregue.setTextColor(Color.parseColor("#ffffff"));
                    status = "Mercadoria_Entregue";
                    //Manda mensagem
                    MandaMensagem("Recebi a Mercadoria sobre os meus cuidados!");
                    if(alerta.getText().equals("Remetente Marcou Mercadoria como Entregue ao Transportador! Se você Recebeu, recomendamos confirmar, se não relate um problema")) {
                        alerta.setText("");
                    }
                    Criar_Viagem_Lobby.setRemessa(remessa);
                }


            }
        });
        Remessa_A_Caminho.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Remessa remessa = Criar_Viagem_Lobby.getRemessa();

                //Produto está a caminho do entregador
                if(status.equals("A Caminho") || status.equals("Entregue")
                        || status.equals("Avaliado") || status.equals("Aceito")
                        || status.equals("Entregue_ao_transportador")){
                    alert("Ação inválida!");
                }else {

                    //Muda status remessa para A Caminho
                    remessa.setStatus("A Caminho");
                    databaseReference.child("Remessa_Aceite").child(remessa.getChave()).setValue(remessa);
                    //Muda Cor botão
                    Remessa_A_Caminho.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
                    Remessa_A_Caminho.setTextColor(Color.parseColor("#ffffff"));
                    MandaMensagem("Iniciei a entrega do produto");
                    Criar_Viagem_Lobby.setRemessa(remessa);
                }

            }
        });
        Entregue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Remessa remessa = Criar_Viagem_Lobby.getRemessa();
                //Produto foi entregue ao destinatario
                if( status.equals("Avaliado") || status.equals("Aceito")
                        || status.equals("Entregue_ao_transportador") || status.equals("Mercadoria_Entregue")){
                    alert("Ação inválida!");
                }else {

                    //Muda status remessa para Entregue
                    remessa.setStatus("Entregador_Entregou");
                    databaseReference.child("Remessa_Aceite").child(remessa.getChave()).setValue(remessa);
                    //Muda Cor botão
                    Entregue.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
                    Entregue.setTextColor(Color.parseColor("#ffffff"));
                    //MAnda MEnsagem
                    MandaMensagem("Entreguei o produto no local especificado");
                    //Atualiza Remessa
                    Criar_Viagem_Lobby.setRemessa(remessa);
                }
            }
        });
        Avaliar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //Avaliar Transportador
                /*
                if(status.equals("A Caminho") || status.equals("Avaliado") || status.equals("Aceito")
                        || status.equals("Entregue_ao_transportador") || status.equals("Mercadoria_Entregue")){
                    alert("Ação inválida!");
                }else {
                    //Abre pagina de avaliação
                    //Muda Cor botão
                }
                 */
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

            titulo.setText("Aguardando Recebimento da Mercadoria!");

            status = "Mercadoria_Entregue";

        }else if(remessa.getStatus().equals("Entregue_ao_Transportador")){
            //Botão fica escuro com texto branco
            Produto_Entregue.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
            Produto_Entregue.setTextColor(Color.parseColor("#ffffff"));

           // Remessa_A_Caminho.setBackgroundColor(Color.parseColor("FF00DDFF"));
           // Remessa_A_Caminho.setTextColor(Color.parseColor("#ffffff"));

            titulo.setText("Inicie a viagem!");
            alerta.setText("Remetente Marcou Mercadoria como Entregue ao Transportador! Se você Recebeu, recomendamos confirmar, se não relate um problema");
            status = "Entregue_ao_transportador";

        }else if(remessa.getStatus().equals("A Caminho")){
            Produto_Entregue.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
            Produto_Entregue.setTextColor(Color.parseColor("#ffffff"));

            Remessa_A_Caminho.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
            Remessa_A_Caminho.setTextColor(Color.parseColor("#ffffff"));

            titulo.setText("Entregue a mercadoria!!");
            status = "A Caminho";

        }else if(remessa.getStatus().equals("Entregue")){
            Produto_Entregue.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
            Produto_Entregue.setTextColor(Color.parseColor("#ffffff"));

            Remessa_A_Caminho.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
            Remessa_A_Caminho.setTextColor(Color.parseColor("#ffffff"));

            Entregue.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
            Entregue.setTextColor(Color.parseColor("#ffffff"));

            titulo.setText("Aguardando Avaliação!");

          //  Avaliar.setBackgroundColor(Color.parseColor("#FF00DDFF"));
          //  Avaliar.setTextColor(Color.parseColor("#ffffff"));
            status = "Entregue";


        }else if(remessa.getStatus().equals("Entregador_Entregou")){
            Produto_Entregue.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
            Produto_Entregue.setTextColor(Color.parseColor("#ffffff"));

            Remessa_A_Caminho.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
            Remessa_A_Caminho.setTextColor(Color.parseColor("#ffffff"));

            Entregue.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
            Entregue.setTextColor(Color.parseColor("#ffffff"));

            titulo.setText("Aguardando Avaliação!");

            alerta.setText("Esperando Confirmação de entrega do Remetenet/Destinatario");

        }else if(remessa.getStatus().equals("Avaliado") || remessa.getStatus().equals("Em Ánalise") || remessa.getStatus().equals("Cancelado")){
            Produto_Entregue.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
            Produto_Entregue.setTextColor(Color.parseColor("#ffffff"));

            Remessa_A_Caminho.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
            Remessa_A_Caminho.setTextColor(Color.parseColor("#ffffff"));

            Entregue.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
            Entregue.setTextColor(Color.parseColor("#ffffff"));

            Avaliar.setBackgroundColor(Color.parseColor("#FFAAAAAA"));
            Avaliar.setTextColor(Color.parseColor("#ffffff"));
            status = "Avaliado";
            titulo.setText("Entrega Finalizada!");
        }

    }

    public void MandaMensagem(String mensage){

        //Pega informações da mensagens
        String msg = mensage;

        Remessa remessa = Criar_Viagem_Lobby.getRemessa();


        String fromid = user.getUid();
        String toid = remessa.getIdRemetente();

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
