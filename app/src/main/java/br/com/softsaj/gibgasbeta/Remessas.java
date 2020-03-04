package br.com.softsaj.gibgasbeta;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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

import Bean.Remessa;
import Util.AdapterRemessaAceite;

public class Remessas extends Fragment {

    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Remessa remessa;
    ArrayList<Remessa> lista_de_remessas;
    ListView listaView;
    AdapterRemessaAceite adapterRemessa;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.acompanahr_remessas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iniciacomponentes();

        Button voltar = (Button) getView().findViewById(R.id.button18);
        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //Abre Mapa para escolha
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Destino_Escolhe_mapa()).commit();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Maps_Principal_Fragemnt()).commit();
            }
        });

        //Inicia FireBase
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        //Carrega Lista de Remeessas Aceitada Transportador
        carregaLista();

        //Quando item da lista é clicado
        listaView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View view,
                                    int posicao, long id) {
                // TODO Auto-generated method stub
                alert("Escolheu");
               //Remessa remessa = (Remessa) adapter.getItemAtPosition(posicao);
                Remessa remessa = adapterRemessa.getItem(posicao);
                alert("Remessa idChamada: "+ remessa.getIdChamada());

                //Abre Tela inicial
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Acompanhar_Remessas()).commit();
                Acompanhar_Remessas.setRemessa(remessa);
                Acompanhar_Remessas.setTipo("E");

            }
        });

    }


    public void iniciacomponentes(){
        remessa = new Remessa();
        lista_de_remessas = new ArrayList<>();
        listaView = (ListView) getView().findViewById(R.id.listarem);
    }

    public void carregaLista(){
        if(user == null){
            //getActivity().getFragmentManager().popBackStack();
        }else{
            String id = user.getUid();
            //Query

            Query query1 = FirebaseDatabase.getInstance().getReference("Remessa_Aceite")
                    .orderByChild("idRemetente")
                    .equalTo(id);
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Passar os dados para a interface grafica
                    // alert("Carregando os dados!");
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        Remessa remessa = obj.getValue(Remessa.class);
                        lista_de_remessas.add(remessa);
                    }

                    //Ordena por data chegada

                    //Adicinoa na listView
                    adapterRemessa = new AdapterRemessaAceite(lista_de_remessas, getActivity());
                    listaView.setAdapter(adapterRemessa);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Se ocorrer um erro
                   // alert("Ocorreu um erro ao carregar informações sobre usuário!");
                    //Query
                }
            });

        }
    }



    private void alert(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
