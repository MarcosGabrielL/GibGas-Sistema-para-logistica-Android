package br.com.softsaj.gibgasbeta;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.Observable;

import Bean.Card;
import Bean.CreditCard;
import Bean.Remessa;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class Destino_processamento_pagamento extends Fragment{

    public static Remessa remessa;
    static String tipo;
    static  String caminho;

    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Card card = new Card();

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.processando_pagamento, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Inicia FireBase
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        //Pega Informações cartão crédito
        pegacartão();

        CreditCard creditCard = new CreditCard((Observer) Destino_processamento_pagamento.this);
        creditCard.setCardNumber(card.getNumero());
        creditCard.setName(card.getNomecartão());
        creditCard.setMonth( card.getValidade().substring(0,2) );
        creditCard.setYear( card.getValidade().substring(3,5));
        creditCard.setCvv( card.getCvv() );
        creditCard.setParcels(1);

        alert(card.getCvv()+"--"+card.getNomecartão()+"--"+card.getNumero()+"--"+card.getValidade());
        //Requesita token

        //Envia Pagamento

        //Atualiza conta transportador

    }

    public void pegacartão(){

        if(user == null){
            //getActivity().getFragmentManager().popBackStack();
        }else{

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
                      Card card1 = obj.getValue(Card.class);

                      //Verifica se é o cartão escolhido
                        if(card1.getNomecartão().substring(15,19).equals(remessa.getUltimos4numerosCartão())){
                            card = card1;
                        }

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Se ocorrer um erro
                    alert("Ocorreu um erro ao carregar informações sobre usuário!");
                }
            });

        }
    }


    private void alert(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
    public static void setTipo(String t){
        tipo = t;
    }
    public static void setCaminho(String t){
        caminho = t;
    }
    public static void setRemessa(Remessa r){
        remessa = r;
    }
    public static Remessa getRemessa(){
        return remessa;
    }
}
