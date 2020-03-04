package br.com.softsaj.gibgasbeta;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.RadioGroup;
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

import Bean.ContaBancaria;

public class Configuracao_Money extends Fragment {

    Button cad_ou_atu;
    Button cartoes;
    Spinner banco;
    EditText conta;
    EditText agencia;
    String tipo_conta;
    RadioGroup group;
    RadioButton radioButton;

    boolean existe;

    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_config_money_remetente, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Inicia FireBase
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        initicomponentes();

        carregaconta();

        cad_ou_atu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

               cadastraOuAtualiza();
            }
        });
        cartoes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Configuracao_Cartao()).commit();
                Configuracao_Cartao.RecebeTipo("C");
            }
        });

    }

    public void initicomponentes(){

        cad_ou_atu = (Button) getView().findViewById(R.id.bok2);
        cartoes =  (Button) getView().findViewById(R.id.bok3);
        conta = (EditText)  getView().findViewById(R.id.emp);
        agencia = (EditText) getView().findViewById(R.id.cnpj1);
        banco = (Spinner) getView().findViewById(R.id.spinner3);
        group = (RadioGroup) getView().findViewById(R.id.radioGroup);
        existe = false;

      ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),R.array.bancos,android.R.layout.simple_spinner_item);
      banco.setAdapter(adapter);
    }

    public void carregaconta(){
        alert("Carregando informações");
        //Carrega contas
        //Verifica se existe conta com id do usuario
        Query query1 = FirebaseDatabase.getInstance().getReference("Contas_Bancaria")
                .orderByChild("idUser")
                .equalTo(user.getUid());
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Pega Valores
                for(DataSnapshot obj : dataSnapshot.getChildren()){
                    ContaBancaria conta1 = obj.getValue(ContaBancaria.class);
                    //Se existe
                    //Carrega dados nos campos
                     existe = true;
                    conta.setText(conta1.getConta());
                    agencia.setText(conta1.getAgencia());
/*
                    if(conta1.getTipo().equals("Poupança")){
                        p.setChecked(true);
                        r.setChecked(false);
                    }else {
                        r.setChecked(true);
                        p.setChecked(false);
                    }  */
                    try {
                        if (conta1.getBanco().equals("001")) {
                            banco.setSelection(0);
                        }if (conta1.getBanco().equals("341")) {
                            banco.setSelection(1);
                        }if (conta1.getBanco().equals("033")) {
                            banco.setSelection(2);
                        }if (conta1.getBanco().equals("652")) {
                            banco.setSelection(3);
                        }if (conta1.getBanco().equals("237")) {
                            banco.setSelection(4);
                        }if (conta1.getBanco().equals("745")) {
                            banco.setSelection(5);
                        }if (conta1.getBanco().equals("399")) {
                            banco.setSelection(6);
                        }if (conta1.getBanco().equals("104")) {
                            banco.setSelection(7);
                        }if (conta1.getBanco().equals("389")) {
                            banco.setSelection(8);
                        }if (conta1.getBanco().equals("453")) {
                            banco.setSelection(9);
                        }if (conta1.getBanco().equals("422")) {
                            banco.setSelection(10);
                        }if (conta1.getBanco().equals("633")) {
                            banco.setSelection(11);
                        }if (conta1.getBanco().equals("003")) {
                            banco.setSelection(12);
                        }if (conta1.getBanco().equals("004")) {
                            banco.setSelection(13);
                        }if (conta1.getBanco().equals("745")) {
                            banco.setSelection(14);
                        }if (conta1.getBanco().equals("083")) {
                            banco.setSelection(15);
                        }if (conta1.getBanco().equals("077")) {
                            banco.setSelection(16);
                        }

                    }catch(Exception e){}

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Se ocorrer um erro
                //Se não existe
                existe = false;
            }
        });

    }

    public void cadastraOuAtualiza() {

        AlertDialog alerta;

        //Verifica se já existe conta com o id do usuario
        if (existe) {
            //Se existe pedi confirmação

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Já existe uma conta cadastrada!");
            builder.setMessage("Deseja atualizar a conta?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    //Se sim atualiza conta
                    SalvaConta();
                }
            });
            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    //Se não carrega valores novamente
                    carregaconta();
                }
            });
            alerta = builder.create();
            alerta.show();
        }else {
            //Se não, Cria conta
            SalvaConta();
        }

    }

    public void SalvaConta(){
        ContaBancaria c = new ContaBancaria();

        int radioID = group.getCheckedRadioButtonId();
        radioButton = getView().findViewById(radioID);
        if (radioButton.getText().equals("Poupança")) {
            tipo_conta = "Poupança";
        }else{
            tipo_conta = "Corrente";
        }
        c.setTipo(tipo_conta);
        c.setIdUser(user.getUid());
        c.setAgencia(agencia.getText().toString().trim());
        c.setConta(conta.getText().toString().trim());
        c.setCpftitular("");
        c.setTituar("");
        if(banco.getSelectedItem().toString().equals("Banco do Brasil S.A.")){
            c.setBanco("001");
        }else if(banco.getSelectedItem().toString().equals("Banco Itaú S.A.")){
            c.setBanco("341");
        }else if(banco.getSelectedItem().toString().equals("Banco Santander (Brasil) S.A.")){
            c.setBanco("033");
        }else if(banco.getSelectedItem().toString().equals("Itaú Unibanco Holding S.A.")){
            c.setBanco("652");
        }else if(banco.getSelectedItem().toString().equals("Banco Bradesco S.A.")){
            c.setBanco("237");
        }else if(banco.getSelectedItem().toString().equals("Banco Citibank S.A")){
            c.setBanco("745");
        }else if(banco.getSelectedItem().toString().equals("HSBC Bank Brasil S.A. – Banco Múltiplo")){
            c.setBanco("399");
        }else if(banco.getSelectedItem().toString().equals("Caixa Econômica Federal")){
            c.setBanco("104");
        }else if(banco.getSelectedItem().toString().equals("Banco Mercantil do Brasil S.A.")){
            c.setBanco("389");
        }else if(banco.getSelectedItem().toString().equals("Banco Rural S.A.")){
            c.setBanco("453");
        }else if(banco.getSelectedItem().toString().equals("Banco Safra S.A.")){
            c.setBanco("422");
        }else if(banco.getSelectedItem().toString().equals("Banco Rendimento S.A.")){
            c.setBanco("633");
        }else if(banco.getSelectedItem().toString().equals("Banco da Amazônia S.A.")){
            c.setBanco("003");
        }else if(banco.getSelectedItem().toString().equals("Banco do Nordeste do Brasil S.A.")){
            c.setBanco("004");
        }else if(banco.getSelectedItem().toString().equals("Banco Citibank S.A.")){
            c.setBanco("745");
        }else if(banco.getSelectedItem().toString().equals("Banco da China Brasil S.A.")){
            c.setBanco("083");
        }else if(banco.getSelectedItem().toString().equals("Banco Inter S.A.")){
            c.setBanco("077");
        }else{
            c.setBanco("104");
        }
        //Salva no banco de dados a conta
        databaseReference.child("Contas_Bancaria").child(c.getIdUser()).setValue(c)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        alert("Conta Salva!");
                        //Carrega valores novamente
                        carregaconta();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        alert("Erro ao salvar Conta");
                    }
                });
    }

    private void alert(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }


}
