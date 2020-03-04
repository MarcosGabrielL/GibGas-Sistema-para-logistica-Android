package br.com.softsaj.gibgasbeta;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.ContactsContract;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.text.SimpleDateFormat;
import java.util.Date;

import Bean.Remessa;
import Bean.Transportador;

public class Chamando extends Fragment {

    Transportador transportador;
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
        return inflater.inflate(R.layout.chamando, container, false);
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

        Toca_Son_Chamada();
        Pega_Viagem();


    }

    public void aceitou(){


        remessa.setChama(false);
        remessa.setAceitou(true);
        //Remove Remessa Chamando
        databaseReference.child("Remessa_Chamando").child(remessa.getIdRemetente()).removeValue();

        remessa.setIdEntregador(user.getUid());
        remessa.setStatus("Aceito");
        Date a = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        remessa.setChave(remessa.getEntregaCidade()+"--"+remessa.getIdRemetente()+"--yYyYy"+formatador.format(a));
        remessa.setIdEntrega(user.getUid()+"--"+remessa.getEntregaCidade()+"--"+remessa.getIdRemetente()+"--yYyYy"+formatador.format(a));
        //Cria Remessa Aceitada
        databaseReference.child("Remessa_Aceite").child(remessa.getChave()).setValue(remessa);

        //Crio collection com Remessa
        /*
        FirebaseFirestore.getInstance().collection("Remessas_em_Curso")
                .document(remessa.getIdEntrega())
                .set(remessa)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                          @Override
                                          public void onSuccess(Void aVoid) {
                                              alert("Remessa em curso");
                                          }
                                      })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
         */

                        //Atualiza transportador para não chamar mais
                        transportador.setChama(false);
        transportador.setIdChamada("");
        databaseReference.child("Transportador").child(transportador.getId()).setValue(transportador);

        //GibGas.setchamou(false);

        //Abre Tela inicial
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Criar_Viagem_Lobby()).commit();
        Criar_Viagem_Lobby.setRemessa(remessa);
        Criar_Viagem_Lobby.setTipo("T");

        Para_Som();

    }

    public void recusou(){

       // Maps_Principal_Fragemnt.RecusarViagem();

        //Atualiza transportador para não chamar mais
        transportador.setChama(false);
        transportador.setIdChamada("");
        databaseReference.child("Transportador").child(transportador.getId()).setValue(transportador);

        //GibGas.setchamou(false);

        //Abre Tela inicial
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Maps_Principal_Trans_Fragemnt()).commit();

        Para_Som();

    }

    public void Completa() {

        alert("IdChamada: "+transportador.getIdChamada());
        //SystemClock.sleep(2000);

        //Query
        Query query1 = FirebaseDatabase.getInstance().getReference("Remessa_Chamando")
                .orderByChild("idChamada")
                .equalTo(transportador.getIdChamada());
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Passar os dados para a interface grafica
                for(DataSnapshot obj : dataSnapshot.getChildren()){
                    remessa = obj.getValue(Remessa.class);
                }

                alert("Aqui 2");
                //TESTANDO AQUI
                //Completa informações no Fragmento
                Local_origem.setText(remessa.getCidadeOrigem());
                Destino.setText(remessa.getEntregaCidade());
                DataLimite.setText(remessa.getEntregaLimiteData());
                Valor_maximo.setText(remessa.getValorMaximo());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Se ocorrer um erro
                alert("Ocorreu um erro ao carregar informações sobre usuário!");
            }
        });


        //Verifica se outro alguem já aceitou
      //  new Thread() {
       //     @Override
       //     public void run() {
                //Verifica_Chamada_Aceita_Por_Outro();
       //     }
       // }.start();

    }

    public void Pega_Viagem(){

       // alert("Aqui");
        //SystemClock.sleep(2000);
        Query query1 = FirebaseDatabase.getInstance().getReference("Transportador")
                .orderByChild("id")
                .equalTo(user.getUid());
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Passar os dados para a interface grafica
                for (DataSnapshot obj : dataSnapshot.getChildren()) {
                    transportador = obj.getValue(Transportador.class);
                }


                Completa();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Se ocorrer um erro
                alert("Ocorreu um erro ao carregar informações sobre usuário!");
            }
        });

    }

    public void  Verifica_Chamada_Aceita_Por_Outro(){


                    Handler handle = new Handler();
                    handle.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Query query1 = FirebaseDatabase.getInstance().getReference("Transportador")
                                    .orderByChild("id")
                                    .equalTo(transportador.getId());
                            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //Passar os dados para a interface grafica
                                    for (DataSnapshot obj : dataSnapshot.getChildren()) {
                                        transportador = obj.getValue(Transportador.class);
                                    }

                                    if (transportador.isChama()) {
                                        Verifica_Chamada_Aceita_Por_Outro1();
                                    } else {
                                        recusou();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    //Se ocorrer um erro
                                    alert("Ocorreu um erro ao carregar informações sobre usuário!");
                                }
                            });

                        }
                    }, 2000);
    }

    public void  Verifica_Chamada_Aceita_Por_Outro1(){

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {

                Query query1 = FirebaseDatabase.getInstance().getReference("Transportador")
                        .orderByChild("id")
                        .equalTo(transportador.getId());
                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Passar os dados para a interface grafica
                        for (DataSnapshot obj : dataSnapshot.getChildren()) {
                            transportador = obj.getValue(Transportador.class);
                        }

                        if (transportador.isChama()) {
                            Verifica_Chamada_Aceita_Por_Outro();
                        } else {
                            recusou();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //Se ocorrer um erro
                        alert("Ocorreu um erro ao carregar informações sobre usuário!");
                    }
                });

            }
        }, 2000);
    }

    public void Toca_Son_Chamada(){
        //Verifica se aplicativo esta fechado
        //Se estiver
        //Ativa Notificação Push
        //Se não troca tela e chama
    }

    public void Para_Som(){
        //Para o som de tocar
    }

    private void alert(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public static Remessa getRemessa(){
        return remessa;
    }
    public static String getTipo(){
        return "T";
    }
}
