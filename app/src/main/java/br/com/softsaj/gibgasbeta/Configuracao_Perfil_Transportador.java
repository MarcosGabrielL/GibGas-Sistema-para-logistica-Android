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

import Bean.Remetentes;
import Bean.Transportador;
import Dao.Connector;
import Util.MaskEditUtil;

public class Configuracao_Perfil_Transportador extends Fragment {

    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Transportador remetente;
    Transportador transportador;

    private Button boair ;
    private EditText nome;
    private EditText email;
    private EditText telefone;
    private EditText nascimento ;
    private EditText cpf ;

    private EditText cep;
    private EditText cidade;
    private EditText rua;
    private EditText numero;
    private EditText bairro;
    private EditText complemento;
    private Spinner estado;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_config_perfil_transportador, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iniciacomponentes();

        //Inicia FireBase
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


        user = FirebaseAuth.getInstance().getCurrentUser();


        boair.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                alert("Aguarde; Atualizando!");
                atualiza();
            }
        });


        verificaUser();
    }

    private void iniciacomponentes() {
        remetente = new Transportador();
        transportador = new Transportador();
        boair = (Button) getView().findViewById(R.id.button11);
        nome = (EditText) getView().findViewById(R.id.emp);
        email = (EditText) getView().findViewById(R.id.cnpj1);
        telefone = (EditText) getView().findViewById(R.id.eas);
        telefone.addTextChangedListener(MaskEditUtil.mask(telefone, MaskEditUtil.FORMAT_FONE));
        nascimento  = (EditText) getView().findViewById(R.id.editText6);
        nascimento.addTextChangedListener(MaskEditUtil.mask(nascimento, MaskEditUtil.FORMAT_DATE));
        cpf = (EditText) getView().findViewById(R.id.editText7);
        cpf.addTextChangedListener(MaskEditUtil.mask(cpf, MaskEditUtil.FORMAT_CPF));

        cep = (EditText) getView().findViewById(R.id.editcep);
        cidade = (EditText) getView().findViewById(R.id.editcidade);
        rua = (EditText) getView().findViewById(R.id.editrua);
        numero = (EditText) getView().findViewById(R.id.editnumero);
        bairro = (EditText) getView().findViewById(R.id.editbairro);
        complemento = (EditText) getView().findViewById(R.id.editcomplemento);
        estado = (Spinner) getView().findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),R.array.estados,android.R.layout.simple_spinner_item);
        estado.setAdapter(adapter);

    }

    private void verificaUser() {
        if(user == null){
           // getActivity().getFragmentManager().popBackStack();
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
                    alert("Carregando os dados!");
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        remetente = obj.getValue(Transportador.class);
                    }

                    completa();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Se ocorrer um erro
                    alert("Ocorreu um erro ao carregar informações sobre usuário!");
                }
            });

        }
    }

    public void completa(){

        nome.setText(remetente.getNome());
        email.setText(remetente.getEmail());
        telefone.setText(remetente.getTelefone());
        cpf.setText(remetente.getCpf());
        nascimento.setText(remetente.getNascimento());

        cep.setText(remetente.getCep());
        cidade.setText(remetente.getCidade());
        rua.setText(remetente.getRua());
        numero.setText(remetente.getNumero());
        bairro.setText(remetente.getBairro());
        complemento.setText(remetente.getComplemento());

        try {
            if (remetente.getEstado().equals("AC")) {
                estado.setSelection(0);
            }
            if (remetente.getEstado().equals("AL")) {
                estado.setSelection(1);
            }
            if (remetente.getEstado().equals("AM")) {
                estado.setSelection(2);
            }
            if (remetente.getEstado().equals("AP")) {
                estado.setSelection(3);
            }
            if (remetente.getEstado().equals("BA")) {
                estado.setSelection(4);
            }
            if (remetente.getEstado().equals("CE")) {
                estado.setSelection(5);
            }
            if (remetente.getEstado().equals("DF")) {
                estado.setSelection(6);
            }
            if (remetente.getEstado().equals("ES")) {
                estado.setSelection(7);
            }
            if (remetente.getEstado().equals("GO")) {
                estado.setSelection(8);
            }
            if (remetente.getEstado().equals("MA")) {
                estado.setSelection(9);
            }
            if (remetente.getEstado().equals("MG")) {
                estado.setSelection(10);
            }
            if (remetente.getEstado().equals("MT")) {
                estado.setSelection(11);
            }
            if (remetente.getEstado().equals("PA")) {
                estado.setSelection(12);
            }
            if (remetente.getEstado().equals("PB")) {
                estado.setSelection(13);
            }
            if (remetente.getEstado().equals("PE")) {
                estado.setSelection(14);
            }
            if (remetente.getEstado().equals("PI")) {
                estado.setSelection(15);
            }
            if (remetente.getEstado().equals("PR")) {
                estado.setSelection(16);
            }
            if (remetente.getEstado().equals("RJ")) {
                estado.setSelection(17);
            }
            if (remetente.getEstado().equals("RN")) {
                estado.setSelection(18);
            }
            if (remetente.getEstado().equals("RO")) {
                estado.setSelection(19);
            }
            if (remetente.getEstado().equals("RR")) {
                estado.setSelection(20);
            }
            if (remetente.getEstado().equals("RS")) {
                estado.setSelection(21);
            }
            if (remetente.getEstado().equals("SC")) {
                estado.setSelection(22);
            }
            if (remetente.getEstado().equals("SE")) {
                estado.setSelection(23);
            }
            if (remetente.getEstado().equals("SP")) {
                estado.setSelection(24);
            }
            if (remetente.getEstado().equals("TO")) {
                estado.setSelection(25);
            }
        }catch (Exception e){

        }

    }

    public void atualiza(){

        remetente.setNome(nome.getText().toString().trim());
        remetente.setEmail(email.getText().toString().trim());
        remetente.setTelefone(telefone.getText().toString().trim());
        remetente.setCpf(cpf.getText().toString().trim());
        remetente.setNascimento(nascimento.getText().toString().trim());

        remetente.setCep(cep.getText().toString().trim());
        remetente.setCidade(cidade.getText().toString().trim());
        remetente.setRua(rua.getText().toString().trim());
        remetente.setNumero(numero.getText().toString().trim());
        remetente.setBairro(bairro.getText().toString().trim());
        remetente.setComplemento(complemento.getText().toString().trim());
        remetente.setEstado(estado.getSelectedItem().toString());

        databaseReference.child("Remetente").child(remetente.getId()).setValue(remetente);
        alert("Usuário Atualizado com sucesso!");

    }

    private void Logout(){
        Connector.logout();
        getActivity().getFragmentManager().popBackStack();
    }

    private void alert(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
