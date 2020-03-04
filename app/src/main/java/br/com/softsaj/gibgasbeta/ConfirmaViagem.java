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
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import Bean.Remessa;

public class ConfirmaViagem extends Fragment {

    static Remessa remessa;

    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private Button Aceitar;
    private Button Recusar;
    private TextView Local_origem;
    private TextView Destino;
    private TextView DataLimite;
    private TextView Valor_maximo;


    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.remetente_confirma_viagem, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Aceitar = (Button) getView().findViewById(R.id.button3);
        Recusar = (Button) getView().findViewById(R.id.button4);
        Local_origem = (TextView) getView().findViewById(R.id.textView67);
        Destino = (TextView) getView().findViewById(R.id.textView68);
        DataLimite = (TextView) getView().findViewById(R.id.textView69);
        Valor_maximo = (TextView) getView().findViewById(R.id.textView84);

        Aceitar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                aceitou();

            }
        });
        Recusar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                recusou();

            }
        });

        //Inicia FireBase
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        completa();

    }

    public void aceitou(){

        Date a = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        remessa.setIdEntrega(user.getUid()+"--"+remessa.getEntregaCidade()+"--"+remessa.getIdRemetente()+"--yYyYy"+formatador.format(a));
        //Cria Remessa Aceitada
        remessa.setStatus("Aceitado");
        remessa.setChave(remessa.getEntregaCidade()+"--"+remessa.getIdRemetente()+"--yYyYy"+formatador.format(a));
        databaseReference.child("Remessa_Aceite").child(remessa.getChave()).setValue(remessa);

        //Exclui Remessa Espera
        databaseReference.child("Remessa_Aguarda").child(remessa.getChave()).removeValue();

        //Chama tipo de Pagamento
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Destino_modo_pagamento()).commit();
        Destino_modo_pagamento.setRemessa(remessa);
        Destino_modo_pagamento.setTipo("C");
        Destino_modo_pagamento.setCaminho("Confirmar");


    }

    public void recusou(){
        //Exclui
        databaseReference.child("Remessa_Aguarda").child(remessa.getChave()).removeValue();

        //volta
        startActivity(new Intent(getActivity().getBaseContext(), GibGas.class));

    }

    public void completa(){

        Local_origem.setText(remessa.getCidadeOrigem());
        Destino.setText(remessa.getEntregaCidade());
        DataLimite.setText(remessa.getEntregaLimiteData());
        Valor_maximo.setText(remessa.getValorViagem());

    }

    public static void setRemessa(Remessa r){
        remessa = new Remessa();
        remessa = r;
    }

}
