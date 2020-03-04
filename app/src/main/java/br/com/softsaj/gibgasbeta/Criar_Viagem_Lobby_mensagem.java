package br.com.softsaj.gibgasbeta;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import Bean.Message;
import Bean.Remessa;
import Bean.Transportador;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class Criar_Viagem_Lobby_mensagem extends Fragment {

    GroupAdapter adapter;
    RecyclerView rc;
    static String tipo;
    static Remessa remessa;
    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String fromid = "";
    String toid = "";
    ArrayList<Message> messages;

    Button enviar;
    EditText txt;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.destino_ajuste_final_mensagem, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        messages = new ArrayList<Message>();
        initiComponentes();
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        enviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                sendMessage();

            }
        });

        boolean abriu = Criar_Viagem_Lobby.getChamouChat();
        //Carrega Mensagens antigas
        if(!abriu){
            fetchMessages();
        }

        //Adiciona novas menssagens ao chat assim que adicionadas ao banco
        FirebaseDatabase.getInstance().getReference("Mensagens")
        .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Message newmessage = dataSnapshot.getValue(Message.class);
                if(newmessage.getIdEntrega().equals(remessa.getIdEntrega())) {
                    adapter.add(new MessageItem(newmessage));
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });


    }

    class ComparadorDeTimes implements Comparator<Message> {
        public int compare(Message o1, Message o2) {
            if (o1.getTime() < o2.getTime()) return -1;
            else if (o1.getTime() > o2.getTime()) return +1;
            else return 0;
        }
    }

    public void sendMessage(){

        //Pega informações da mensagens
        String msg = txt.getText().toString().trim();
        txt.setText("");

        if(tipo.equals("T")){
            fromid = user.getUid();
            toid = remessa.getIdRemetente();
        }else{
            fromid = user.getUid();
            toid = remessa.getIdEntregador();
        }
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


    private void fetchMessages() {

        FirebaseDatabase.getInstance().getReference("Mensagens")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Passar os dados para a interface grafica
                for (DataSnapshot obj : dataSnapshot.getChildren()) {
                   Message message = obj.getValue(Message.class);
                    if(message.getIdEntrega().equals(remessa.getIdEntrega())) {
                        messages.add(message);
                    }
                }

                //Ordena as mensagens por tempo
                Collections.sort (messages, new ComparadorDeTimes());
                //Adiciona mensagens ao chat
                for(Message message : messages){
                    adapter.add(new MessageItem(message));
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Se ocorrer um erro
                alert("Ocorreu um erro ao carregar informações sobre usuário!");
            }
        });
    }


    private void initiComponentes() {
        rc = getView().findViewById(R.id.recyclechat);

        adapter = new GroupAdapter();
        rc.setLayoutManager(new LinearLayoutManager(getActivity()));
        rc.setAdapter(adapter);

        tipo = Criar_Viagem_Lobby.getTipo();
        remessa = Criar_Viagem_Lobby.getRemessa();

        enviar = (Button) getView().findViewById(R.id.button29);
        txt = (EditText) getView().findViewById(R.id.editText12);

    }

    private class MessageItem extends Item<ViewHolder> {

        private final Message message;

        private MessageItem(Message message) {
            this.message = message;
        }


        @Override
        public void bind(@Nullable ViewHolder viewHolder, int position){
            TextView msg = viewHolder.itemView.findViewById(R.id.textView70);

            msg.setText(message.getText());
        }
        @Override
        public int getLayout(){
            return message.getFromId().equals(FirebaseAuth.getInstance().getUid())
                    ? R.layout.actibity_to_message
                    : R.layout.actibity_from_message;
        }

    }

    public static void setTipo(String tp){
        tipo = tp;
    }

    private void alert(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
