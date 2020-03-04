package br.com.softsaj.gibgasbeta;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import Bean.Viagem;

public class Criar_Viagem_Config_Bike extends Fragment {

    Button voltar;
    Button ok;
    CheckBox vouatevc;
    CheckBox vematemim;
    CheckBox negociar;
    EditText tipobike;

    Viagem viagem;

    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_enviar_bike, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iniciacomponentes();

        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Criar_Viagem_MeioTransporte()).commit();

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                PegaOp();

            }
        });

        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    private void PegaOp() {
        //Pega opção e Tipo de Viagem
        viagem.setTipo("Bike");
        //Add tipo retirada
        if(vouatevc.isChecked()){
            viagem.setRetirada("Vou até você");
        }else if(vematemim.isChecked()){
            viagem.setRetirada("Vem até mim");
        }else if(negociar.isChecked()){
            viagem.setRetirada("Vamos negociar");
        }

        //Add Descrição Bike
        viagem.setDescricaobike(tipobike.getText().toString().trim());
        Date a = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        viagem.setId(user.getUid()+viagem.getDescricaobike()+format.format(a));

        //Add a lista de Viagens
        if(user==null){

        }else{

                //Chama Final
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Criar_Viagem_Paradas()).commit();
                Criar_Viagem_Paradas.setViagem(viagem);
            }


    }

    public void iniciacomponentes(){

        viagem = Criar_Viagem_MeioTransporte.getViagem();

        voltar  = (Button) getView().findViewById(R.id.button18);
        ok  = (Button) getView().findViewById(R.id.bok3);

        vouatevc = (CheckBox) getView().findViewById(R.id.checkBox2);
        vematemim = (CheckBox) getView().findViewById(R.id.checkBox) ;
        negociar  = (CheckBox) getView().findViewById(R.id.checkBox3) ;
        tipobike = (EditText) getView().findViewById(R.id.editText17);
    }

    private void alert(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

}
