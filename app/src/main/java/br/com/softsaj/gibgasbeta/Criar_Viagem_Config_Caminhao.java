package br.com.softsaj.gibgasbeta;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Bean.Transportador;
import Bean.Veiculo;
import Bean.Viagem;

public class Criar_Viagem_Config_Caminhao extends Fragment {

    Button voltar;
    Button ok;
    Button cadastrar;
    CheckBox vouatevc;
    CheckBox vematemim;
    CheckBox negociar;
    static Spinner placa;

    static Context context;
    static ArrayList<Veiculo> veiculos;

    Viagem viagem;
    static Transportador transportador;

    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_enviar_caminhao, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();

        //--------------------------------------------------
        transportador = new Transportador();
        veiculos = new ArrayList<>();
        viagem = Criar_Viagem_MeioTransporte.getViagem();
        voltar  = (Button) getView().findViewById(R.id.button18);
        ok  = (Button) getView().findViewById(R.id.bok3);
        cadastrar = (Button) getView().findViewById(R.id.button28);
        placa = (Spinner) getView().findViewById(R.id.spinner7);
        vouatevc = (CheckBox) getView().findViewById(R.id.checkBox2);
        vematemim = (CheckBox) getView().findViewById(R.id.checkBox) ;
        negociar  = (CheckBox) getView().findViewById(R.id.checkBox3) ;

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

        cadastrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //Chama tela de cadastro de motocicleta
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Cadastrar_Caminhao()).commit();


            }
        });

        //Inicia FireBase
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


        user = FirebaseAuth.getInstance().getCurrentUser();

        verificaUser();
        busca_veiculo_no_banco();

    }

    private void PegaOp() {

        //Add Tipo de Viagem
        //Add Tipo Retirada
        viagem.setTipo("Caminhão");
        if(vouatevc.isChecked()){
            viagem.setRetirada("Vou até você");
        }else if(vematemim.isChecked()){
            viagem.setRetirada("Vem até mim");
        }else if(negociar.isChecked()){
            viagem.setRetirada("Vamos negociar");
        }

            //Add Moto
            viagem.setTipoAuto("Caminhão");
            viagem.setModelo("");
            viagem.setCor("");
            viagem.setAno("");
           // viagem.setPlaca(placa.getSelectedItem().toString());

            Date a = new Date();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            viagem.setId(user.getUid()+viagem.getPlaca()+format.format(a));


        //Chama Final
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new Criar_Viagem_Paradas()).commit();
        Criar_Viagem_Paradas.setViagem(viagem);


    }

    public void iniciacomponentes(){

        ArrayList<String> placas = new ArrayList<>();
        for(Veiculo veiculo: veiculos){
            if(veiculo.getTipo().equals("Caminhão")){
                placas.add(veiculo.getPlaca());
            }

        }
        //placa.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, placas));

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item,
                        placas); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        placa.setAdapter(spinnerArrayAdapter);




    }

    public static Transportador getTrasportador(){
        return  transportador;
    }

    public static void atualizalistamotos(Veiculo v){
        ArrayList<String> placas = new ArrayList<>();
        for(Veiculo veiculo: veiculos){
            if(veiculo.getTipo().equals("Caminhão")){
                placas.add(veiculo.getPlaca());
            }
        }
        placas.add(v.getPlaca());
        //placa.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, placas));

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (context, android.R.layout.simple_spinner_item,
                        placas); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        placa.setAdapter(spinnerArrayAdapter);
    }

    private void verificaUser() {
        if(user == null){
            //getActivity().getFragmentManager().popBackStack();
        }else{
            //Completa os campos
            String id = user.getUid();

            //Query
            Query query1 = FirebaseDatabase.getInstance().getReference("Transportador")
                    .orderByChild("id")
                    .equalTo(id);
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Passar os dados para a interface grafica
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        transportador = obj.getValue(Transportador.class);
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

    private void busca_veiculo_no_banco() {
        if(user == null){
            //getActivity().getFragmentManager().popBackStack();
        }else{
            //Completa os campos
            String id = user.getUid();

            //Query
            Query query1 = FirebaseDatabase.getInstance().getReference("Veiculos")
                    .orderByChild("id")
                    .equalTo(id);
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Passar os dados para a interface grafica
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        Veiculo veiculo = obj.getValue(Veiculo.class);
                        veiculos.add(veiculo);
                    }

                    iniciacomponentes();

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

}
