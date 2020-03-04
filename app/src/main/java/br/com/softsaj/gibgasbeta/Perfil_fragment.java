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
import Dao.RemetenteDAO;
import Util.MaskEditUtil;

public class Perfil_fragment extends Fragment {


    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Remetentes remetente;
    Transportador transportador;

    private Button boair ;
    private EditText nome;
    private EditText email;
    private EditText telefone;
    private EditText nascimento ;
    private EditText cpf ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_perfil, container, false);

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
                alert("Aguarde!");
                Logout();
                getActivity().getFragmentManager().popBackStack();
            }
        });


        verificaUser();
    }

    private void iniciacomponentes() {
        remetente = new Remetentes();
        transportador = new Transportador();
        boair = (Button) getView().findViewById(R.id.button10);
        nome = (EditText) getView().findViewById(R.id.emp);
        email = (EditText) getView().findViewById(R.id.cnpj1);
        telefone = (EditText) getView().findViewById(R.id.eas);
        telefone.addTextChangedListener(MaskEditUtil.mask(telefone, MaskEditUtil.FORMAT_FONE));
        nascimento  = (EditText) getView().findViewById(R.id.editText6);
        nascimento.addTextChangedListener(MaskEditUtil.mask(nascimento, MaskEditUtil.FORMAT_DATE));
        cpf = (EditText) getView().findViewById(R.id.editText7);
        cpf.addTextChangedListener(MaskEditUtil.mask(cpf, MaskEditUtil.FORMAT_CPF));

    }

    private void verificaUser() {
        if(user == null){
            getActivity().getFragmentManager().popBackStack();
        }else{
            //Completa os campos
            String id = user.getUid();

            //Query
            Query query1 = FirebaseDatabase.getInstance().getReference("Remetente")
                    .orderByChild("id")
                    .equalTo(id);
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Passar os dados para a interface grafica
                    alert("Carregando os dados!");
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        remetente = obj.getValue(Remetentes.class);
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
       /**
            if (remetente.getEstado().equals("AC")) {
                estados.setSelection(0);
            }
            if (remetente.getEstado().equals("AL")) {
                estados.setSelection(1);
            }
            if (remetente.getEstado().equals("AM")) {
                estados.setSelection(2);
            }
            if (remetente.getEstado().equals("AP")) {
                estados.setSelection(3);
            }
            if (remetente.getEstado().equals("BA")) {
                estados.setSelection(4);
            }
            if (remetente.getEstado().equals("CE")) {
                estados.setSelection(5);
            }
            if (remetente.getEstado().equals("DF")) {
                estados.setSelection(6);
            }
            if (remetente.getEstado().equals("ES")) {
                estados.setSelection(7);
            }
            if (remetente.getEstado().equals("GO")) {
                estados.setSelection(8);
            }
            if (remetente.getEstado().equals("MA")) {
                estados.setSelection(9);
            }
            if (remetente.getEstado().equals("MG")) {
                estados.setSelection(10);
            }
            if (remetente.getEstado().equals("MT")) {
                estados.setSelection(11);
            }
            if (remetente.getEstado().equals("PA")) {
                estados.setSelection(12);
            }
            if (remetente.getEstado().equals("PB")) {
                estados.setSelection(13);
            }
            if (remetente.getEstado().equals("PE")) {
                estados.setSelection(14);
            }
            if (remetente.getEstado().equals("PI")) {
                estados.setSelection(15);
            }
            if (remetente.getEstado().equals("PR")) {
                estados.setSelection(16);
            }
            if (remetente.getEstado().equals("RJ")) {
                estados.setSelection(17);
            }
            if (remetente.getEstado().equals("RN")) {
                estados.setSelection(18);
            }
            if (remetente.getEstado().equals("RO")) {
                estados.setSelection(19);
            }
            if (remetente.getEstado().equals("RR")) {
                estados.setSelection(20);
            }
            if (remetente.getEstado().equals("RS")) {
                estados.setSelection(21);
            }
            if (remetente.getEstado().equals("SC")) {
                estados.setSelection(22);
            }
            if (remetente.getEstado().equals("SE")) {
                estados.setSelection(23);
            }
            if (remetente.getEstado().equals("SP")) {
                estados.setSelection(24);
            }
            if (remetente.getEstado().equals("TO")) {
                estados.setSelection(25);
            }**/

    }

    public void completa1(){

        nome.setText(transportador.getNome());
        email.setText(transportador.getEmail());
        telefone.setText(transportador.getTelefone());
        cpf.setText(transportador.getCpf());
        nascimento.setText(transportador.getNascimento());
        /**
         if (remetente.getEstado().equals("AC")) {
         estados.setSelection(0);
         }
         if (remetente.getEstado().equals("AL")) {
         estados.setSelection(1);
         }
         if (remetente.getEstado().equals("AM")) {
         estados.setSelection(2);
         }
         if (remetente.getEstado().equals("AP")) {
         estados.setSelection(3);
         }
         if (remetente.getEstado().equals("BA")) {
         estados.setSelection(4);
         }
         if (remetente.getEstado().equals("CE")) {
         estados.setSelection(5);
         }
         if (remetente.getEstado().equals("DF")) {
         estados.setSelection(6);
         }
         if (remetente.getEstado().equals("ES")) {
         estados.setSelection(7);
         }
         if (remetente.getEstado().equals("GO")) {
         estados.setSelection(8);
         }
         if (remetente.getEstado().equals("MA")) {
         estados.setSelection(9);
         }
         if (remetente.getEstado().equals("MG")) {
         estados.setSelection(10);
         }
         if (remetente.getEstado().equals("MT")) {
         estados.setSelection(11);
         }
         if (remetente.getEstado().equals("PA")) {
         estados.setSelection(12);
         }
         if (remetente.getEstado().equals("PB")) {
         estados.setSelection(13);
         }
         if (remetente.getEstado().equals("PE")) {
         estados.setSelection(14);
         }
         if (remetente.getEstado().equals("PI")) {
         estados.setSelection(15);
         }
         if (remetente.getEstado().equals("PR")) {
         estados.setSelection(16);
         }
         if (remetente.getEstado().equals("RJ")) {
         estados.setSelection(17);
         }
         if (remetente.getEstado().equals("RN")) {
         estados.setSelection(18);
         }
         if (remetente.getEstado().equals("RO")) {
         estados.setSelection(19);
         }
         if (remetente.getEstado().equals("RR")) {
         estados.setSelection(20);
         }
         if (remetente.getEstado().equals("RS")) {
         estados.setSelection(21);
         }
         if (remetente.getEstado().equals("SC")) {
         estados.setSelection(22);
         }
         if (remetente.getEstado().equals("SE")) {
         estados.setSelection(23);
         }
         if (remetente.getEstado().equals("SP")) {
         estados.setSelection(24);
         }
         if (remetente.getEstado().equals("TO")) {
         estados.setSelection(25);
         }**/

    }

    public void atualiza(){

        remetente.setNome(nome.getText().toString().trim());
        remetente.setEmail(email.getText().toString().trim());
        remetente.setTelefone(telefone.getText().toString().trim());
        remetente.setCpf(cpf.getText().toString().trim());
        remetente.setNascimento(nascimento.getText().toString().trim());

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
