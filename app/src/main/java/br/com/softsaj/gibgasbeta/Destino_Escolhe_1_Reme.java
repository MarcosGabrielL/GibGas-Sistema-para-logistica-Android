package br.com.softsaj.gibgasbeta;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import Bean.Remessa;

public class Destino_Escolhe_1_Reme extends Fragment  {

    public static Remessa remessa;

    private Button EscolherNoMapa;
    private Button Ok;
    private Button voltar;

    private static EditText cidade_origem;
    private static EditText rua;
    private static EditText numero;
    private static EditText bairro ;
    private static Spinner estados;
    private static EditText cep ;
    private static EditText cidade  ;
    private static EditText complemento;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.escolhe_destino_1_reme, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        iniciacomponetes();

        Ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //alert("Aguarde!");

                DimensionarCarga();
                     }
        });
        EscolherNoMapa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
               // alert("Aguarde!");
                //Abre Mapa para escolha
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Destino_Escolhe_mapa()).commit();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Destino_Escolhe_mapa()).commit();
            }
        });
        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //Abre Mapa para escolha
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Destino_Escolhe_mapa()).commit();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Maps_Principal_Fragemnt()).commit();
            }
        });

    }

    public void DimensionarCarga(){

        remessa.setCidadeOrigem(cidade_origem.getText().toString().trim());
        remessa.setEntregaCidade(cidade.getText().toString().trim());
        remessa.setEntregaCep(cep.getText().toString().trim());
        remessa.setEntregaEstado(estados.getSelectedItem().toString().trim());
        remessa.setEntregaRua(rua.getText().toString().trim());
        remessa.setEntregaNumeroCasa(numero.getText().toString().trim());
        remessa.setEntregaBairro(bairro.getText().toString().trim());
        remessa.setEntregaComplemento(complemento.getText().toString().trim());

        if(cidade.getText().toString().trim().equals("") || cep.getText().toString().trim().equals("") ||
        estados.getSelectedItem().toString().trim().equals("") || rua.getText().toString().trim().equals("") ||
        numero.getText().toString().trim().equals("") || bairro.getText().toString().trim().equals("") ||
        complemento.getText().toString().trim().equals("") ||
                cidade_origem.getText().toString().trim().equals("")){

            alert("Complete todos os dados obriatórios!");
        }else{
            //Proxima Pagina de Envio
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Destino_Dimen_Carga()).commit();

        }

    }

    public static void veiodomapa(String city, String street, String num, String ba, String Cep){
        cidade.setText(city);
        //estados.setText(Destino_Escolhe_mapa.adminArea);
        rua.setText(street);
        numero.setText(num);
        bairro.setText(ba);
        cep.setText(Cep);

    }

    public void iniciacomponetes(){

        cidade_origem = (EditText) getView().findViewById(R.id.editText32);

        remessa = new Remessa();

        EscolherNoMapa = (Button) getView().findViewById(R.id.button12);
        Ok = (Button) getView().findViewById(R.id.bok3);
        voltar = (Button) getView().findViewById(R.id.button18);

        rua = (EditText) getView().findViewById(R.id.editText11);
        numero = (EditText) getView().findViewById(R.id.editText14);
        bairro = (EditText) getView().findViewById(R.id.editText17);

        estados = (Spinner) getView().findViewById(R.id.spinner2);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),R.array.estados,android.R.layout.simple_spinner_item);
        estados.setAdapter(adapter);

        cep = (EditText) getView().findViewById(R.id.editText15);
        cidade  = (EditText) getView().findViewById(R.id.editText13);
        complemento= (EditText) getView().findViewById(R.id.editText16);




    }

    public static Remessa getRemessa(){
        return remessa;
    }

    private void alert(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
