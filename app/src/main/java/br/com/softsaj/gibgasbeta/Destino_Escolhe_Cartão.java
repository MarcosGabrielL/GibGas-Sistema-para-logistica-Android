package br.com.softsaj.gibgasbeta;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

import java.util.ArrayList;

import Bean.Card;
import Bean.Remessa;

public class Destino_Escolhe_Cartão extends Fragment {

    Button cadastrar;
    Button voltar;
    Button ok;
    Spinner cartões;
    static Remessa remessa;
    static String tipo;
    ArrayList<Card> cards;
    static String caminho;

    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_destino_escolhe_cartao, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Inicia FireBase
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        initi();

        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //Abre Tela Looby para tratamentos
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Destino_modo_pagamento()).commit();
                Destino_modo_pagamento.setRemessa(remessa);
                Destino_modo_pagamento.setTipo(tipo);
                Destino_modo_pagamento.setCaminho(caminho);
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //Pega dados do cartão
                remessa.setUltimos4numerosCartão(cartões.getSelectedItem().toString());

                //Abre tela de confirmação
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Destino_confirma_pagamento()).commit();
                Destino_confirma_pagamento.setRemessa(remessa);
                Destino_confirma_pagamento.setTipo(tipo);
                Destino_confirma_pagamento.setCaminho(caminho);

                //Abre Tela Looby para tratamentos
               // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Criar_Viagem_Lobby()).commit();
               // Criar_Viagem_Lobby.setRemessa(remessa);
               // Criar_Viagem_Lobby.setTipo(tipo);
            }
        });
        cadastrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Configuracao_Cartao()).commit();
                Configuracao_Cartao.setRemessa(remessa);
                Configuracao_Cartao.setCaminho(caminho);
                Configuracao_Cartao.RecebeTipo("A");
            }
        });
    }

    public void initi(){

        cadastrar = (Button) getView().findViewById(R.id.button6);
        voltar = (Button) getView().findViewById(R.id.button18);
        ok = (Button) getView().findViewById(R.id.bok2);
        cartões = (Spinner) getView().findViewById(R.id.ramo2);
        cards = new ArrayList<>();

        carregacartoes();
    }

    public void carregacartoes(){

        if(user == null){
            //getActivity().getFragmentManager().popBackStack();
        }else{
            cards.clear();
            //Completa os campos
            String id = user.getUid();

            //Query
            Query query1 = FirebaseDatabase.getInstance().getReference("Cards")
                    .orderByChild("id")
                    .equalTo(id);
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Passar os dados para a interface grafica
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        Card cartão = obj.getValue(Card.class);
                        cards.add(cartão);
                    }

                    ArrayList<String> cartão = new ArrayList<>();
                    for(Card card: cards){
                        cartão.add(card.getNumero().substring(15,19));
                    }
                    //placa.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, placas));

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                            (getActivity(), android.R.layout.simple_spinner_item,
                                    cartão); //selected item will look like a spinner set from XML
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                            .simple_spinner_dropdown_item);
                    cartões.setAdapter(spinnerArrayAdapter);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Se ocorrer um erro
                    alert("Ocorreu um erro ao carregar informações sobre usuário!");
                }
            });

        }


    }

    public static void setRemessa(Remessa r){
        remessa = r;
    }

    public static Remessa getRemessa(){
        return remessa;
    }

    public static void setTipo(String t){
        tipo = t;
    }

    public static void setCaminho(String t){
        caminho = t;
    }

    private void alert(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
