package br.com.softsaj.gibgasbeta;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import Dao.Connector;

public class ResetarSenha extends AppCompatActivity {

    private FirebaseAuth auth = Connector.getFirebaseAuth();

    private Button rese ;
    private EditText email ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetar_senha);

        //-----------------------------------------------------
        rese = (Button) findViewById(R.id.resetar);
        email = (EditText) findViewById(R.id.editText3);

        rese.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                /**
                 * Envia E-mail para resetar
                 */
                ResetSenha(email.getText().toString().trim());
            }
        });
    }

    private void ResetSenha(String email){
        auth.sendPasswordResetEmail(email).addOnCompleteListener(ResetarSenha.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    alert(" Um e-mail foi enviado para alterar sua senha!");
                }else{
                    alert("E-mail não registrado!");
                }
            }
        });
    }

    private void alert(String msg){
        Toast.makeText(ResetarSenha.this, msg, Toast.LENGTH_SHORT).show();
    }

}
