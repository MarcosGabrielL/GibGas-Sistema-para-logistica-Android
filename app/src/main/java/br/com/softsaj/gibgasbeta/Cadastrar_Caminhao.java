package br.com.softsaj.gibgasbeta;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Bean.Transportador;
import Bean.Veiculo;

public class Cadastrar_Caminhao extends Fragment {


    Button voltar;
    Button cadastrar;
    EditText placa;
    EditText modelo;
    EditText cilindrada;
    EditText cor;

    Transportador transportador;
    static Veiculo veiculo;

    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;



    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cadastrar_caminhao, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iniciacomponentes();

        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //Chama tela de escolha carro
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Criar_Viagem_Config_Caminhao()).commit();

            }
        });

        cadastrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //Pega valores da moto
                veiculo.setModelo(modelo.getText().toString().trim());
                veiculo.setAno(cilindrada.getText().toString().trim());
                veiculo.setCor(cor.getText().toString().trim());
                veiculo.setPlaca(placa.getText().toString().trim());
                veiculo.setTipo("Caminhão");
                veiculo.setId(transportador.getId());


                databaseReference.child("Veiculos").child(transportador.getId()+" - "+veiculo.getPlaca()).setValue(veiculo);


                //Chama tela de escolha carro
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Criar_Viagem_Config_Caminhao()).commit();

                //Atualiza spinner com placas
                Criar_Viagem_Config_Caminhao.atualizalistamotos(veiculo);


            }
        });

        //Inicia FireBase
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


        user = FirebaseAuth.getInstance().getCurrentUser();


    }

    public void iniciacomponentes(){
        veiculo = new Veiculo();

        transportador = Criar_Viagem_Config_Moto.getTrasportador();

        voltar  = (Button) getView().findViewById(R.id.button18);
        cadastrar = (Button) getView().findViewById(R.id.bok3);

        placa = (EditText) getView().findViewById(R.id.editText6);
        modelo = (EditText) getView().findViewById(R.id.emp);
        cilindrada  = (EditText) getView().findViewById(R.id.cnpj1);
        cor = (EditText) getView().findViewById(R.id.eas);


    }

    public static Veiculo getVeiculo(){
        return veiculo;
    }

    private void alert(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
