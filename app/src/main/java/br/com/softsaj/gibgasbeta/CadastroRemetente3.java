package br.com.softsaj.gibgasbeta;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

import Bean.Remetentes;
import Dao.Connector;

public class CadastroRemetente3 extends AppCompatActivity {

    private static Remetentes remetente ;

    private Button bcadastroremetente ;
    private EditText empresa;
    private EditText cnpj ;
    private EditText email;
    private Spinner ramo;
    private EditText senha;

    private FirebaseAuth auth ;
    private FirebaseUser user ;
    private FirebaseDatabase firebaseDataBase ;
    private DatabaseReference databaseReference ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_remetente3);

        //-----------------------------------------------
        remetente = CadastroRemetente2.getRemetente();
        bcadastroremetente = (Button) findViewById(R.id.bok3);
        empresa = (EditText) findViewById(R.id.emp);
        cnpj = (EditText) findViewById(R.id.cnpj1);
        email = (EditText) findViewById(R.id.eas);
        ramo = (Spinner) findViewById(R.id.ramo);
        senha = (EditText) findViewById(R.id.senha);

        auth = Connector.getFirebaseAuth();


        //Estados Lista
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.ramos_atividade,android.R.layout.simple_spinner_item);
        ramo.setAdapter(adapter);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Acção botão Cadastrar
        bcadastroremetente.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                /**
                 * Evento Botão Finaliza, Salva e abre confirmação telefone
                 */
                alert("Aguarde!");
                PegaValores();
            }
        });

        //Inicializa firebase
        FirebaseApp.initializeApp(CadastroRemetente3.this);
        firebaseDataBase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDataBase.getReference();

    }

    public void PegaValores(){

        remetente.setCNPJ(cnpj.getText().toString());
        remetente.setEmpresa(empresa.getText().toString());
        remetente.setEmailEmpresa(email.getText().toString());
        remetente.setRamo(ramo.getSelectedItem().toString());
        remetente.setSenha(senha.getText().toString());

        //Salva Usuario
        if(remetente.getSenha().equals("")){
            alert("Senha inválida!");
        }else {
            criaUsuario(remetente.getEmail(), remetente.getSenha());
        }
    }

    private void criaUsuario(String email, String senha) {
        auth.createUserWithEmailAndPassword(email,senha)
                .addOnCompleteListener(CadastroRemetente3.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            /**
                             * Salva os outros dados do remetente
                             */
                            user = Connector.getFirebaseUser();
                            if(user == null){
                                alert("Usuário não logado!");
                            }else{
                                remetente.setId(user.getUid());
                                databaseReference.child("Remetente").child(remetente.getId()).setValue(remetente);

                                alert("Usuário Cadastrado com sucesso!");
                                /**
                                 * Autentica numero fornecido
                                 */
                                Autenticar_num();
                            }


                        }else{
                            alert("Erro de Cadastro!");
                        }
                    }
                });
    }

    private void alert(String msg){
        Toast.makeText(CadastroRemetente3.this, msg, Toast.LENGTH_SHORT).show();
    }

    public void Autenticar_num(){

        ConfirmacaoTel.tipo(1);

        //Chama tela confimação telefone
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {

                //alert("Confira sua caixa de menssagens.");
            }
        }, 5000);

        handle.postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(getBaseContext(), ConfirmacaoTel.class));
            }
        }, 2000);


    }

    public static Remetentes getRemetente(){
        return remetente;
    }


    public static void receberemetente(Remetentes rem){
        remetente = rem;
    }
}
