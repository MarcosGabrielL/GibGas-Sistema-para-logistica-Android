package br.com.softsaj.gibgasbeta;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import Bean.Remessa;

public class Add_Remessas_Disponiveis extends Fragment {


    Button ok;
    Remessa remessa;

    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.destino_remessa_disponivel_confirma, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        remessa = Chamar_ou_Lista_ou_Adcionar.getRemessa();
        ok = (Button) getView().findViewById(R.id.button17);


        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //Abre Mapa para escolha
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Destino_Escolhe_mapa()).commit();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Maps_Principal_Fragemnt()).commit();
            }
        });

        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        alert(remessa.getEntregaCidade());
        Add_na_lista();
    }

    public void Add_na_lista(){

        remessa.setStatus("NaLista");
        remessa.setIdRemetente(user.getUid());
        remessa.setEmailRemetente(user.getEmail());


        //Add a lista de Remessas
            //add na lista Geral de Viagens Para Remetentes Verem *&
        remessa.setIdRemetente(user.getUid());
        Date a = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        remessa.setChave(remessa.getEntregaCidade()+"--"+remessa.getIdRemetente()+"--yYyYy"+formatador.format(a));
        databaseReference.child("Remessas").child(remessa.getChave()).setValue(remessa);

        //databaseReference.child("Remessas").child(user.getUid()+"--"+remessa.getEntregaCidade()).setValue(remessa);


    }

    private void alert(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
