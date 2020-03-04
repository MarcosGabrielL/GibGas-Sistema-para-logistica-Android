package br.com.softsaj.gibgasbeta;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Bean.Remessa;
import Bean.Veiculo;
import Bean.Viagem;
import Util.AdapterViagensPersonalizado;

public class Carregar_Lista extends Fragment {

    List<Viagem> viagens;
    ListView listaDeViagens;
    ArrayList<String> lista;
    static Remessa remessa;
    AdapterViagensPersonalizado adapterviagens;

    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Viagem viagem;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.destino_lista, container, false);
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
                        .replace(R.id.container, new Chamar_ou_Lista_ou_Adcionar()).commit();
            }
        });

        //Inicia FireBase
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        carregaLista();

        //Quando item da lista é clicado
        listaDeViagens.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View view,
                                    int posicao, long id) {
                // TODO Auto-generated method stub
                //alert("Remessa Destino: "+remessa.getEntregaCidade());
                //Remessa remessa = (Remessa) adapter.getItemAtPosition(posicao);
                //AdapterViagensPersonalizado
                viagem = adapterviagens.getItem(posicao);

                //Adiciona valor e Tipo na remessa
                remessa.setTipoTransporte(viagem.getTipo());
                remessa.setModeloTransporte(viagem.getModelo());
                remessa.setCorTransporte(viagem.getCor());
                remessa.setAnoTransporte(viagem.getAno());
                remessa.setPlacaTransporte(viagem.getPlaca());
                remessa.setIdEntregador(viagem.getId());
                remessa.setIdRemetente(user.getUid());
                Date a = new Date();
                SimpleDateFormat formatador = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                remessa.setIdEntrega(user.getUid()+"--"+remessa.getEntregaCidade()+"--"+remessa.getIdRemetente()+"--yYyYy"+formatador.format(a));


                //VERIFICA QUAL CIDADE DA VIAGEM TEM O DESTINO IGUAL AO DESTINO DA REMESSA
                try {
                    if (checkSimilarity(viagem.getCidade1(), remessa.getEntregaCidade()) >= 0.5) {
                        //SALVA CUSTO DA VIAGEM ATÉ O DESTINO NA REMESSA
                        remessa.setValorViagem(viagem.getCidade1_valor());
                    } else if (checkSimilarity(viagem.getCidade2(),remessa.getEntregaCidade()) >= 0.5){
                        //SALVA CUSTO DA VIAGEM ATÉ O DESTINO NA REMESSA
                        remessa.setValorViagem(viagem.getCidade2_valor());
                    }else if (checkSimilarity(viagem.getCidade3(),remessa.getEntregaCidade()) >= 0.5){
                        //SALVA CUSTO DA VIAGEM ATÉ O DESTINO NA REMESSA
                        remessa.setValorViagem(viagem.getCidade3_valor());
                    }else if (checkSimilarity(viagem.getCidade4(),remessa.getEntregaCidade()) >= 0.5){
                        //SALVA CUSTO DA VIAGEM ATÉ O DESTINO NA REMESSA
                        remessa.setValorViagem(viagem.getCidade4_valor());
                    }else if (checkSimilarity(viagem.getCidade5(),remessa.getEntregaCidade()) >= 0.5){
                        //SALVA CUSTO DA VIAGEM ATÉ O DESTINO NA REMESSA
                        remessa.setValorViagem(viagem.getCidade5_valor());
                    }else if (checkSimilarity(viagem.getCidade6(),remessa.getEntregaCidade()) >= 0.5){
                        //SALVA CUSTO DA VIAGEM ATÉ O DESTINO NA REMESSA
                        remessa.setValorViagem(viagem.getCidade6_valor());
                    }else if (checkSimilarity(viagem.getCidade7(),remessa.getEntregaCidade()) >= 0.5){
                        //SALVA CUSTO DA VIAGEM ATÉ O DESTINO NA REMESSA
                        remessa.setValorViagem(viagem.getCidade7_valor());
                    }else if (checkSimilarity(viagem.getCidade8(),remessa.getEntregaCidade()) >= 0.5){
                        //SALVA CUSTO DA VIAGEM ATÉ O DESTINO NA REMESSA
                        remessa.setValorViagem(viagem.getCidade8_valor());
                    }else{
                        remessa.setValorViagem("50,00");
                    }

                }catch(Exception e){

                }




                //Chama tipo de Pagamento
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Destino_modo_pagamento()).commit();
                Destino_modo_pagamento.setRemessa(remessa);
                Destino_modo_pagamento.setTipo("A");
                Destino_modo_pagamento.setCaminho("Lista");


            }
        });

    }



    public void iniciacomponentes(){

        viagem = new Viagem();
        viagens =  new ArrayList<>();
        lista = new ArrayList<>();
        remessa = Chamar_ou_Lista_ou_Adcionar.getRemessa();
        listaDeViagens = (ListView) getView().findViewById(R.id.lista);

    }

    public void carregaLista(){
        if(user == null){
            //getActivity().getFragmentManager().popBackStack();
        }else{
            //Completa os campos
            String id = user.getUid();

            //Query
            Query query1 = FirebaseDatabase.getInstance().getReference("Viagens");
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Passar os dados para a interface grafica
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                       Viagem viagem = obj.getValue(Viagem.class);

                       //Verifica se a viagem passa pela cidade de destino da remessa
                        try {
                            if(checkSimilarity(viagem.getCidade1(),remessa.getEntregaCidade())>=0.5){
                                //verifica se passa a tempo
                                if(VerificaTempo()){
                                    //verifica se cobra menos que o limite
                                    if (VerificaValor(viagem.getCidade1_valor())) {
                                        viagens.add(viagem);
                                    }
                                }
                            } else if(checkSimilarity(viagem.getCidade2(),remessa.getEntregaCidade())>=0.5){
                                if(VerificaTempo()){
                                    if (VerificaValor(viagem.getCidade2_valor())) {
                                        viagens.add(viagem);
                                    }
                                }
                            }else if(checkSimilarity(viagem.getCidade3(),remessa.getEntregaCidade())>=0.5){
                                if(VerificaTempo()){
                                    if (VerificaValor(viagem.getCidade3_valor())) {
                                        viagens.add(viagem);
                                    }
                                }
                            }else if(checkSimilarity(viagem.getCidade4(),remessa.getEntregaCidade())>=0.5){
                                if(VerificaTempo()){
                                    if (VerificaValor(viagem.getCidade4_valor())) {
                                        viagens.add(viagem);
                                    }
                                }
                            }else if(checkSimilarity(viagem.getCidade5(),remessa.getEntregaCidade())>=0.5){
                                if(VerificaTempo()){
                                    if (VerificaValor(viagem.getCidade5_valor())) {
                                        viagens.add(viagem);
                                    }
                                }
                            }else if(checkSimilarity(viagem.getCidade6(),remessa.getEntregaCidade())>=0.5){
                                if(VerificaTempo()){
                                    if (VerificaValor(viagem.getCidade6_valor())) {
                                        viagens.add(viagem);
                                    }
                                }
                            }else if(checkSimilarity(viagem.getCidade7(),remessa.getEntregaCidade())>=0.5){
                                if(VerificaTempo()){
                                    if (VerificaValor(viagem.getCidade7_valor())) {
                                        viagens.add(viagem);
                                    }
                                }
                            }else if(checkSimilarity(viagem.getCidade8(),remessa.getEntregaCidade())>=0.5){
                                if(VerificaTempo()){
                                    if (VerificaValor(viagem.getCidade8_valor())) {
                                        viagens.add(viagem);
                                    }
                                }
                            }else if(checkSimilarity(viagem.getCidade_destino(),remessa.getEntregaCidade())>=0.5){
                                if(VerificaTempo()){
                                    viagens.add(viagem);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    adapterviagens = new AdapterViagensPersonalizado(viagens, getActivity(),remessa);
                    listaDeViagens.setAdapter(adapterviagens);

                    for(Viagem viage : viagens){

                        //alert("Cidade: "+viage.getCidade1());
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Se ocorrer um erro
                    alert("Ocorreu um erro ao carregar informações sobre usuário!");
                }
            });

        }

    }

    public boolean  VerificaTempo(){
        boolean chega_a_tempo = true;
        //Pega ultimo local de transportador
        //Verifica o meio de transporte
        //Pega local destino
        //calcula a distancia
        //Calcula tempo medio para percorrer a distancia pelo meio de transporte
        //Traça a previsão de chegada
        //Verifica se chega antes do limite da entrega
                //Se chega antes
        if(chega_a_tempo){
            //
        return true;
        }else{
        //Se chega depois
        return false;
        }

    }

    private void alert(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    protected static float checkSimilarity(String sString1, String sString2) throws Exception {

        // Se as strings têm tamanho distinto, obtêm a similaridade de todas as
        // combinações em que tantos caracteres quanto a diferença entre elas são
        // inseridos na string de menor tamanho. Retorna a similaridade máxima
        // entre todas as combinações, descontando um percentual que representa
        // a diferença em número de caracteres.
        if(sString1.length() != sString2.length()) {
            int iDiff = Math.abs(sString1.length() - sString2.length());
            int iLen = Math.max(sString1.length(), sString2.length());
            String sBigger, sSmaller, sAux;

            if(iLen == sString1.length()) {
                sBigger = sString1;
                sSmaller = sString2;
            }
            else {
                sBigger = sString2;
                sSmaller = sString1;
            }

            float fSim, fMaxSimilarity = Float.MIN_VALUE;
            for(int i = 0; i <= sSmaller.length(); i++) {
                sAux = sSmaller.substring(0, i) + sBigger.substring(i, i+iDiff) + sSmaller.substring(i);
                fSim = checkSimilaritySameSize(sBigger,  sAux);
                if(fSim > fMaxSimilarity)
                    fMaxSimilarity = fSim;
            }
            return fMaxSimilarity - (1f * iDiff) / iLen;

            // Se as strings têm o mesmo tamanho, simplesmente compara-as caractere
            // a caractere. A similaridade advém das diferenças em cada posição.
        } else
            return checkSimilaritySameSize(sString1, sString2);
    }

    protected static float checkSimilaritySameSize(String sString1, String sString2) throws Exception {

        if(sString1.length() != sString2.length())
            throw new Exception("Strings devem ter o mesmo tamanho!");

        int iLen = sString1.length();
        int iDiffs = 0;

        // Conta as diferenças entre as strings
        for(int i = 0; i < iLen; i++)
            if(sString1.charAt(i) != sString2.charAt(i))
                iDiffs++;

        // Calcula um percentual entre 0 e 1, sendo 0 completamente diferente e
        // 1 completamente igual
        return 1f - (float) iDiffs / iLen;
    }

    public static void setRemessa(Remessa r){
        remessa = r;
    }

    public boolean VerificaValor(String valor){

        String valor_maximo = remessa.getValorMaximo();
        String valor_pedido = valor;

        try {
            if (Float.parseFloat(valor_maximo) >= Float.parseFloat(valor_pedido)) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            //Retira toda letra e simbolo menos o ponto/virgula
            return true;
        }

    }
}
