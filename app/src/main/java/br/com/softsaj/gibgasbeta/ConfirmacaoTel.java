package br.com.softsaj.gibgasbeta;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import Bean.Remetentes;
import Dao.Connector;

public class ConfirmacaoTel extends AppCompatActivity {


    private String numero;

    private String mVerificationId;
    private EditText Code;
    private FirebaseAuth auth;
    private FirebaseUser user ;
    public static int Tipo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacao_tel);

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        //------------------------------------------------------
        Remetentes remetente = CadastroRemetente3.getRemetente();
        Code = (EditText) findViewById(R.id.editText);
        TextView num = (TextView) findViewById(R.id.textView5);
        Button confirmar = (Button) findViewById(R.id.button5);
        Button depois = (Button) findViewById(R.id.button8);

        auth = Connector.getFirebaseAuth();
        user = Connector.getFirebaseUser();

        numero = remetente.getTelefone();

        num.setText(numero);

        //alert(numero);

        confirmar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                /**
                 * Evento Botão Confirmar
                 */
                Confere();
            }
        });

        depois.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                /**
                 * Evento Botão Depois
                 */
                startActivity(new Intent(getBaseContext(), GibGas.class));
            }
        });


                mandamensagem();

    }

    public void mandamensagem(){
        if(user != null){
            //Este método irá enviar o código de verificação
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+55"+numero.replace(")","").replace("(", "").replace("-",""),        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    mCallbacks);        // OnVerificationStateChangedCallbacks
        }else{
            //not signed in
        }
    }

    //Confere se codigo digitado é igual ao enviado
    public void Confere(){
        alert("Aqui3");
        //Verifica o codigo
        if(Code.getText().toString().equals("")){
            alert("Código Inválido");
        }
         verifyVerificationCode(Code.getText().toString());
    }

    // Este é o nosso callback que nos ajudará a saber se o código é enviado ou não. Tem três métodos.
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
       /*
       * este método é chamado quando a verificação é concluída.
       * Aqui temos o objeto PhoneAuthCredential, que nos dará o código, se for detectado automaticamente.
       * */
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            //Obtendo o código enviado por SMS
            String code = phoneAuthCredential.getSmsCode();

            // as vezes o código não é detectado automaticamente
            // neste caso o código será nulo
            // então o usuário tem que inserir manualmente o código
            if (code != null) {
                Code.setText(code);
                //Verifica o codigo
                verifyVerificationCode(code);
            }
        }

        /*
        *  Este método é chamado quando a verificação falha por alguns motivos,
        *  então aqui estamos exibindo apenas um simples brinde.
        * */
        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(ConfirmacaoTel.this, e.getMessage(), Toast.LENGTH_LONG).show();
            alert("Código Inválido");
        }

        /*
        * Isso é chamado quando o código é enviado com sucesso.
        * O primeiro parâmetro aqui é o nosso ID de verificação que é enviado.
        * Então, estamos armazenando em nosso objeto mVerificationId .
        *
        * */
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
            //alert(s);
        }
    };

    /*
    * Para verificar o código de verificação, usaremos esse método.
    * Se a verificação for bem-sucedida, permitiremos que o usuário faça login no aplicativo.
    *
    * */
    private void verifyVerificationCode(String otp) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);


        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //verificação bem sucedida vamos começar a atividade de perfil
                            startActivity(new Intent(getBaseContext(), BemVindo.class));

                        } else {

                            //verificação malsucedida .. exibir uma mensagem de erro

                            String message = "verificação malsucedida!";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }

                                alert(message);
                        }
                    }
                });
    }

    private void alert(String msg){
        Toast.makeText(ConfirmacaoTel.this, msg, Toast.LENGTH_SHORT).show();
    }

    public static void tipo(int t){
        Tipo = t;
    }

}


