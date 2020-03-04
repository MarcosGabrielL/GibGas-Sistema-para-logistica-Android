package br.com.softsaj.gibgasbeta;

import android.content.Intent;
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

import Bean.Remessa;
import Util.MaskEditUtil;

public class Destino_Dimen_Carga extends Fragment {



    public static Remessa remessa;

    private Button Ok;

    private EditText altura;
    private EditText largura;
    private EditText comprimento;
    private EditText descriçãosimples;
    private EditText cuidadostransporte;
    private EditText descriçãocompleta  ;
    private EditText valor;
    private EditText peso;
    private EditText limite;


    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.escolhe_carga_1_reme, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iniciacomponentes();

        Button voltar = (Button) getView().findViewById(R.id.button18);
        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //Abre Mapa para escolha
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Destino_Escolhe_mapa()).commit();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Destino_Escolhe_1_Reme()).commit();
            }
        });

        Ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
               // alert("Aguarde!");

                Chamar();
            }
        });
    }

    public void Chamar(){

        remessa.setPacoteAltura(altura.getText().toString().trim());
        remessa.setPacoteLargura(largura.getText().toString().trim());
        remessa.setPacoteComprimento(comprimento.getText().toString().trim());
        remessa.setPacoteDescriçãosimples(descriçãosimples.getText().toString().trim());
        remessa.setPacoteCuidadostransporte(cuidadostransporte.getText().toString().trim());
        remessa.setPacoteDescriçãocompleta(descriçãocompleta.getText().toString().trim());
        remessa.setPacoteValor(valor.getText().toString().trim());
        remessa.setPacotePeso(peso.getText().toString().trim());
        remessa.setEntregaLimiteData(limite.getText().toString().trim());
        remessa.setValorMaximo(calculavalormaximo());//*&

        if(altura.getText().toString().trim().equals("") || largura.getText().toString().trim().equals("") ||
        comprimento.getText().toString().trim().equals("") || descriçãosimples.getText().toString().trim().equals("") ||
        cuidadostransporte.getText().toString().trim().equals("") || descriçãocompleta.getText().toString().trim().equals("") ||
        valor.getText().toString().trim().equals("") || peso.getText().toString().trim().equals("") || limite.getText().toString().trim().equals("")){

            alert("Complete todos os dados obriatórios!");
        }else{
            try{
                float v = 0;
                v = Float.parseFloat(altura.getText().toString().trim().replace(",","").replace(".",""));
                v = Float.parseFloat(largura.getText().toString().trim().replace(",","").replace(".",""));
                v = Float.parseFloat(comprimento.getText().toString().trim().replace(",","").replace(".",""));
                v = Float.parseFloat(valor.getText().toString().trim().replace(",","").replace(".",""));
                v = Float.parseFloat(peso.getText().toString().trim().replace(",","").replace(".",""));


                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Chamar_ou_Lista_ou_Adcionar()).commit();

            }catch(Exception e){
                alert("Valor informador não é um número!");
            }
        }
    }

    private String calculavalormaximo() {


        //calcula valor remessa
        String valor_maximo = "999,99";

        return valor_maximo;
    }

    public void iniciacomponentes(){
        remessa = Destino_Escolhe_1_Reme.getRemessa();

        Ok = (Button) getView().findViewById(R.id.bok3);

        limite = (EditText) getView().findViewById(R.id.editText10);
        limite.addTextChangedListener(MaskEditUtil.mask(limite, MaskEditUtil.FORMAT_DATE));

        altura = (EditText) getView().findViewById(R.id.editText11);
        largura = (EditText) getView().findViewById(R.id.editText13);
        comprimento = (EditText) getView().findViewById(R.id.editText5);
        descriçãosimples = (EditText) getView().findViewById(R.id.editText15);
        cuidadostransporte = (EditText) getView().findViewById(R.id.editText16);
        descriçãocompleta  = (EditText) getView().findViewById(R.id.editText17);
        valor = (EditText) getView().findViewById(R.id.editText9);
        peso = (EditText) getView().findViewById(R.id.editText8);

    }

    private void alert(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public static Remessa getRemessa(){
        return remessa;
    }

}
