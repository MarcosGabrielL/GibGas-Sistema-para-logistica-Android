package br.com.softsaj.gibgasbeta;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import Bean.Analise;
import Bean.Remessa;

public class Criar_Viagem_Lobby_Cancelar extends Fragment {

    Button Cancelar;
    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
    CheckBox checkBox4;
    CheckBox checkBox5;
    CheckBox checkBox6;
    CheckBox checkBox7;
    CheckBox checkBox8;
    CheckBox checkBox9;
    EditText Razão;

    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cancelar_viagem, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();

        //Inicia FireBase
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        Cancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                IniciaPRocessoCancelamento();

            }
        });

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    // perform logic
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox5.setChecked(false);
                    checkBox6.setChecked(false);
                    checkBox7.setChecked(false);
                    checkBox8.setChecked(false);
                    checkBox9.setChecked(false);
                }

            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    // perform logic
                    checkBox1.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox5.setChecked(false);
                    checkBox6.setChecked(false);
                    checkBox7.setChecked(false);
                    checkBox8.setChecked(false);
                    checkBox9.setChecked(false);
                }

            }
        });
        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    // perform logic
                    checkBox2.setChecked(false);
                    checkBox1.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox5.setChecked(false);
                    checkBox6.setChecked(false);
                    checkBox7.setChecked(false);
                    checkBox8.setChecked(false);
                    checkBox9.setChecked(false);
                }

            }
        });
        checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox5.setChecked(false);
                    checkBox6.setChecked(false);
                    checkBox7.setChecked(false);
                    checkBox8.setChecked(false);
                    checkBox9.setChecked(false);
                }

            }
        });
        checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox6.setChecked(false);
                    checkBox7.setChecked(false);
                    checkBox8.setChecked(false);
                    checkBox9.setChecked(false);
                }

            }
        });
        checkBox6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox5.setChecked(false);
                    checkBox7.setChecked(false);
                    checkBox8.setChecked(false);
                    checkBox9.setChecked(false);
                }

            }
        });
        checkBox7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox5.setChecked(false);
                    checkBox6.setChecked(false);
                    checkBox8.setChecked(false);
                    checkBox9.setChecked(false);
                }

            }
        });
        checkBox8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox5.setChecked(false);
                    checkBox6.setChecked(false);
                    checkBox7.setChecked(false);
                    checkBox9.setChecked(false);
                }

            }
        });
        checkBox9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox5.setChecked(false);
                    checkBox6.setChecked(false);
                    checkBox7.setChecked(false);
                    checkBox8.setChecked(false);
                }

            }
        });


    }

    public void init(){
        Cancelar = (Button) getView().findViewById(R.id.button38);
        checkBox1 = (CheckBox) getView().findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox) getView().findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox) getView().findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox) getView().findViewById(R.id.checkBox4);
        checkBox5 = (CheckBox) getView().findViewById(R.id.checkBox5);
        checkBox6 = (CheckBox) getView().findViewById(R.id.checkBox6);
        checkBox7 = (CheckBox) getView().findViewById(R.id.checkBox7);
        checkBox8 = (CheckBox) getView().findViewById(R.id.checkBox8);
        checkBox9 = (CheckBox) getView().findViewById(R.id.checkBox9);
        Razão = (EditText) getView().findViewById(R.id.editText29);
    }

    public void IniciaPRocessoCancelamento(){

        //Verifica Se Transportador Já Pegou a mercadoria
        Remessa remessa = Criar_Viagem_Lobby.getRemessa();

        //Se sim
        if(remessa.getStatus().toString().equals("Mercadoria_Entregue")
                || remessa.getStatus().toString().equals("Entregue_ao_Transportador")
                || remessa.getStatus().toString().equals("A Caminho")
                || remessa.getStatus().toString().equals("Entregue")
                || remessa.getStatus().toString().equals("Entregador_Entregou")
                || remessa.getStatus().toString().equals("Avaliado")){

            //Atualiza status remessa
            remessa.setStatus("Em Ánalise");

            //Pega informações
            String motivo = null;
            if(checkBox1.isChecked()){
                motivo = checkBox1.getText().toString();
            }else if(checkBox2.isChecked()){
                motivo = checkBox2.getText().toString();
            }else if(checkBox3.isChecked()){
                motivo = checkBox3.getText().toString();
            }else if(checkBox4.isChecked()){
                motivo = checkBox4.getText().toString();
            }else if(checkBox5.isChecked()){
                motivo = checkBox5.getText().toString();
            }else if(checkBox6.isChecked()){
                motivo = checkBox6.getText().toString();
            }else if(checkBox7.isChecked()){
                motivo = checkBox7.getText().toString();
            }else if(checkBox8.isChecked()){
                motivo = checkBox8.getText().toString();
            }else if(checkBox9.isChecked()){
                motivo = Razão.getText().toString();
            }

            Date a = new Date();
            SimpleDateFormat formatador = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            // Adiciona 10 dias a data atual
            Date d10 = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(d10);
            c.add(Calendar.DATE, 10);
            d10 = c.getTime();
            String Dezdias = formatador.format(d10);
            // Adiciona 13 dias a data atual
            Date d13 = new Date();
            Calendar c1 = Calendar.getInstance();
            c1.setTime(d13);
            c1.add(Calendar.DATE, 13);
            d13 = c1.getTime();
            String Trezedias = formatador.format(d13);
            // Adiciona 10 dias a data atual
            Date d23 = new Date();
            Calendar c2 = Calendar.getInstance();
            c2.setTime(d23);
            c2.add(Calendar.DATE, 23);
            d23 = c2.getTime();
            String Vintresdias = formatador.format(d23);
            // Adiciona 10 dias a data atual
            Date d30 = new Date();
            Calendar c3 = Calendar.getInstance();
            c3.setTime(d30);
            c3.add(Calendar.DATE, 30);
            d30 = c3.getTime();
            String Trintadias = formatador.format(d30);


            //e inicia análise
            Analise analise = new Analise();
            remessa.setMotivo_Analise("Remetente: "+motivo);
            remessa.setData_Inicio_Analise(formatador.format(a));

            databaseReference.child("Remessa_Aceite").child(remessa.getChave()).setValue(remessa);
            //Salva Analise
            analise.setChave(remessa.getChave());
            analise.setIdEntrega(remessa.getIdEntrega());
            analise.setData_Inicio_Analise(remessa.getData_Inicio_Analise());
            analise.setStatus(remessa.getStatus());
            analise.setData_Limite_responta(Dezdias);
            analise.setData_Limite_analise(Trezedias);
            analise.setData_Limite_replica(Vintresdias);
            analise.setData_Limite_resposta_final(Trintadias);
            analise.setMotivo_Analise(remessa.getMotivo_Analise());
            analise.setQueixado("Transportador");
            analise.setQueixou("Remetente");
            analise.setIdRemetente(remessa.getIdRemetente());
            analise.setIdTransportador(remessa.getIdEntregador());

            databaseReference.child("Analise").child(analise.getChave()).setValue(analise);

            //Abre pagina de analise
            getChildFragmentManager().beginTransaction().replace(R.id.container1, new Criar_Viagem_Lobby_Analise()).commit();

            //Criar_Viagem_Lobby.mudaicone();

        }//Se não
        else{

            //Atualiza status remessa
            remessa.setStatus("Cancelado");
            databaseReference.child("Remessa_Aceite").child(remessa.getChave()).setValue(remessa);
            //Volta dinheiro para conta no GibGas do Remetente *&

            //Vai pra inicio
            startActivity(new Intent(getActivity().getBaseContext(), GibGas.class));
        }

        //Solicita Avaliação Transportador


    }
}
