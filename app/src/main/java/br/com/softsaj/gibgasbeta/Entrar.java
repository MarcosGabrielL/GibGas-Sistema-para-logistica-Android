package br.com.softsaj.gibgasbeta;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import Dao.Connector;

public class Entrar extends AppCompatActivity {

    private FirebaseAuth auth ;
    private Button blogin;
    private Button besqueceu ;
    private EditText email ;
    private EditText senha ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //-------------------------------------
        auth = Connector.getFirebaseAuth();
        blogin = (Button) findViewById(R.id.button3);
        besqueceu = (Button) findViewById(R.id.button4);
        email = (EditText) findViewById(R.id.editText3);
        senha = (EditText) findViewById(R.id.editText4);

        blogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                /**
                 * Login e abre tela principal
                 */
                login(email.getText().toString().trim(),senha.getText().toString().trim());
            }
        });

        besqueceu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                /**
                 * Evento Redefinição de Senha
                 */
                startActivity(new Intent(getBaseContext(),ResetarSenha.class));
            }
        });
    }

    private void login(String Email, String Senha){
        auth.signInWithEmailAndPassword(Email,Senha)
                .addOnCompleteListener(Entrar.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Abrir Pagina Principal
                            startActivity(new Intent(getBaseContext(),GibGas.class));
                            finish();
                        }else{
                            alert("Dados incorretos!");
                        }
                    }
                });
    }

    private void alert(String msg){
        Toast.makeText(Entrar.this, msg, Toast.LENGTH_SHORT).show();
    }
}
