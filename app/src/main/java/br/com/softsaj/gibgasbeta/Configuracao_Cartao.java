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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
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

import java.util.ArrayList;

import Bean.Card;
import Bean.ContaBancaria;
import Bean.Remessa;
import Bean.Veiculo;
import Util.MaskEditUtil;

public class Configuracao_Cartao extends Fragment {

    Button cadastrar;
    Button remover;
    Spinner cartões;
    EditText numero;
    EditText nome;
    EditText validade;
    EditText cvv;
    Button voltar;
    ArrayList<Card> cards;
    static String tipo;
    static String caminho;
    static Remessa remessa;

    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_config_cartao, container, false);
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

        //Carega cartões do usuario no spinner
        carregacartoes();

        cadastrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

        cadastracartao();

               /*
                if(tipo.equals("C")) {
                }else{
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Destino_Escolhe_Cartão()).commit();
                    Destino_Escolhe_Cartão.setRemessa(remessa);
                    Destino_Escolhe_Cartão.setTipo(tipo);
                    Destino_Escolhe_Cartão.setCaminho(caminho);
                }
                */

            }
        });
        remover.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

        removecartao();
        }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(tipo.equals("C")) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Configuracao_Money()).commit();

                }else{
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Destino_Escolhe_Cartão()).commit();
                    Destino_Escolhe_Cartão.setRemessa(remessa);
                    Destino_Escolhe_Cartão.setTipo(tipo);
                    Destino_Escolhe_Cartão.setCaminho(caminho);
                }
            }
        });


    }

    public void initi(){

        cadastrar = (Button) getView().findViewById(R.id.bok2);
        remover =  (Button) getView().findViewById(R.id.bok8);
        voltar =  (Button) getView().findViewById(R.id.button18);
        numero = (EditText)  getView().findViewById(R.id.editText18);
        numero.addTextChangedListener(MaskEditUtil.mask(numero, MaskEditUtil.FORMAT_NCARD));
        nome = (EditText) getView().findViewById(R.id.editText188);
        validade = (EditText) getView().findViewById(R.id.editText8);
        validade.addTextChangedListener(MaskEditUtil.mask(validade, MaskEditUtil.FORMAT_VAL));
        cvv = (EditText) getView().findViewById(R.id.editText8d);
        cvv.addTextChangedListener(MaskEditUtil.mask(cvv, MaskEditUtil.FORMAT_CVV));
        cartões = (Spinner) getView().findViewById(R.id.spinner3);
        cards = new ArrayList<>();

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

    public void removecartao(){

        //Pega ultimos 4 numeros do cartão
        final String ultimos4 = numero.getText().toString().trim().substring(15,19);
        //Busca os cartões
        Query query1 = FirebaseDatabase.getInstance().getReference("Cards");
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Pega Valores
                for(DataSnapshot obj : dataSnapshot.getChildren()){
                    Card cartão1 = obj.getValue(Card.class);
                    //Pega cartão com id do usuario e final do numero de cartão = final
                    if(cartão1.getNumero().substring(15,19).equals(ultimos4)){
                        if(cartão1.getId().equals(user.getUid())){
                            //remove o cartão do banco

                            //Atualiza lista
                            carregacartoes();
                            //limpa campos
                            cvv.setText(null);
                            nome.setText(null);
                            numero.setText(null);
                            validade.setText(null);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Se ocorrer um erro
            }
        });


    }

    public void cadastracartao(){

        //Pega valores dos campos
        Card card = new Card();
        //Preenche cartão
        card.setCvv(cvv.getText().toString().trim());
        card.setId(user.getUid());
        card.setNomecartão(nome.getText().toString().trim());
        card.setNumero(numero.getText().toString().trim());
        card.setValidade(validade.getText().toString().trim());

        //Salva Cartão no banco
        databaseReference.child("Cards").push().setValue(card)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        alert("Conta Salva!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        alert("Erro ao salvar Conta");
                    }
                });


            //limpa campos
            cvv.setText(null);
            nome.setText(null);
            numero.setText(null);
            validade.setText(null);

            //Atualiza lista
            carregacartoes();

    }

    private void alert(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void RecebeTipo(String t){
        tipo = t;
    }
    public static void setCaminho(String t){
        caminho = t;
    }

    public static void setRemessa(Remessa r){
        remessa = r;
    }

}
