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
import android.widget.AdapterView;
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
import Bean.Viagem;
import Util.AdapterRemessaAceite;

public class SelecionarRemessa extends Fragment {

    Button voltar;
    ListView lista;
    List<Remessa> Listremessas;
    Remessa remessa;
    static Viagem viagem;

    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    static String Valor;

    AdapterRemessaAceite adapterremessas;
    Boolean compativel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.selecionar_remessa, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Init
        voltar = (Button) getView().findViewById(R.id.button18);
        lista = (ListView) getView().findViewById(R.id.listaa);
        Listremessas = new ArrayList<>();
        //viagem = new Viagem();

        //Inicia FireBase
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        CarregaRemessas();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View view,
                                    int posicao, long id) {
                // TODO Auto-generated method stub

                AlertDialog alerta;


                    //Pega remessa clicada
                    remessa = adapterremessas.getItem(posicao);

                    compativel = true;

                    //Confirmação
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Confirmação");
                    builder.setMessage("Tem certeza que deseja essa viagem?");
                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

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
                                if (checkSimilarity(viagem.getCidade1(), remessa.getEntregaCidade()) >= 0.5 && viagem.getCidade1_valor().toString().equals("")==false) {

                                    compativel = true;
                                    //alert("Valor: "+viagem.getCidade1_valor());

                                }else if (checkSimilarity(viagem.getCidade2(), remessa.getEntregaCidade()) >= 0.5 && viagem.getCidade2_valor().toString().equals("")==false) {

                                    //compativel = true;
                                    alert("Valor: " + viagem.getCidade2_valor());

                                }else if (checkSimilarity(viagem.getCidade3(), remessa.getEntregaCidade()) >= 0.5 && viagem.getCidade3_valor().toString().equals("")==false) {

                                    //compativel = true;
                                    alert("Valor: " + viagem.getCidade3_valor());

                                }else if (checkSimilarity(viagem.getCidade4(), remessa.getEntregaCidade()) >= 0.5 && viagem.getCidade4_valor().toString().equals("")==false) {

                                   // compativel = true;
                                    alert("Valor: " + viagem.getCidade4_valor());

                                }else if (checkSimilarity(viagem.getCidade5(), remessa.getEntregaCidade()) >= 0.5 && viagem.getCidade5_valor().toString().equals("")==false) {

                                    //compativel = true;
                                    alert("Valor: " + viagem.getCidade5_valor());

                                }else if (checkSimilarity(viagem.getCidade6(), remessa.getEntregaCidade()) >= 0.5 && viagem.getCidade6_valor().toString().equals("")==false) {

                                   // compativel = true;
                                    alert("Valor: " + viagem.getCidade6_valor());

                                }else if (checkSimilarity(viagem.getCidade7(), remessa.getEntregaCidade()) >= 0.5 && viagem.getCidade7_valor().toString().equals("")==false) {

                                   // compativel = true;
                                   alert("Valor: " + viagem.getCidade7_valor());

                                }else if (checkSimilarity(viagem.getCidade8(), remessa.getEntregaCidade()) >= 0.5 && viagem.getCidade8_valor().toString().equals("")==false) {

                                    alert("Valor: " + viagem.getCidade8_valor());

                                }else{
                                    //SE NÃO PASSA NÃO É COMPATIVEL
                                    compativel = false;
                                }
                            }catch(Exception e){

                            }



                            if(compativel) {
                                Boolean passa = false;
                                //VERIFICA SE A VIAGEM PASSA NA CIDADE DE ORIGEM DA REMESSA
                                try{
                                    String origem_remessa = remessa.getCidadeOrigem();
                                    if(checkSimilarity(viagem.getCidade1_origem(), origem_remessa ) >= 0.5){
                                        passa = true;
                                    }else if(checkSimilarity(viagem.getCidade2_origem(), origem_remessa ) >= 0.5){
                                        passa = true;
                                    }else if(checkSimilarity(viagem.getCidade3_origem(), origem_remessa ) >= 0.5){
                                        passa = true;
                                    }else if(checkSimilarity(viagem.getCidade4_origem(), origem_remessa ) >= 0.5){
                                        passa = true;
                                    }else if(checkSimilarity(viagem.getCidade5_origem(), origem_remessa ) >= 0.5){
                                        passa = true;
                                    }else if(checkSimilarity(viagem.getCidade6_origem(), origem_remessa ) >= 0.5){
                                        passa = true;
                                    }else if(checkSimilarity(viagem.getCidade7_origem(), origem_remessa ) >= 0.5){
                                        passa = true;
                                    }else if(checkSimilarity(viagem.getCidade8_origem(), origem_remessa ) >= 0.5){
                                        passa = true;
                                    }else if(checkSimilarity(viagem.getCidade_origem(), origem_remessa ) >= 0.5){
                                        passa = true;
                                    }
                                }catch (Exception e){
                                }

                                if(passa==false){
                                    AlertDialog alerta;
                                    //Confirmação
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setTitle("A Viagem não tem parada na cidade de origem da remessa!");
                                    builder.setMessage("Deseja Continuar mesmo assim?");
                                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface arg0, int arg1) {

                                            remessa.setValorViagem(Valor);
                                            alert("Valor: " + remessa.getValorViagem());

                                            //Chama tipo de Pagamento
                                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Destino_modo_pagamento()).commit();
                                            Destino_modo_pagamento.setRemessa(remessa);
                                            Destino_modo_pagamento.setTipo("A");
                                            Destino_modo_pagamento.setCaminho("Lista");


                                        }
                                    });
                                    builder.setNeutralButton("Não", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface arg0, int arg1) {

                                           alert("Escolha outra remessa");


                                        }
                                    });
                                    alerta = builder.create();
                                    alerta.show();
                                }else {

                                    remessa.setValorViagem(Valor);
                                    alert("Valor: " + remessa.getValorViagem());

                                    //Chama tipo de Pagamento
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Destino_modo_pagamento()).commit();
                                    Destino_modo_pagamento.setRemessa(remessa);
                                    Destino_modo_pagamento.setTipo("A");
                                    Destino_modo_pagamento.setCaminho("Lista");
                                }
                            }
                        }
                    });
                    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });
                    alerta = builder.create();
                    alerta.show();

            }
        });

                //Cria ação ao clicar em voltar
                voltar.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View arg0) {
                        //Volta ao inicio
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new Atividades_fragment()).commit();
                    }
                });



    }

    private void CarregaRemessas() {

        //Busca nas remessas da lista e add as cidades de destino

        if(user == null){
            //getActivity().getFragmentManager().popBackStack();
        }else{
            //Completa os campos
            String id = user.getUid();

            //Query
            Query query1 = FirebaseDatabase.getInstance().getReference("Remessas")
                    .orderByChild("idRemetente")
                    .equalTo(id);
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Passar os dados para a interface grafica
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        Remessa remessa = obj.getValue(Remessa.class);
                        remessa.setChave(obj.getKey());
                        Listremessas.add(remessa);
                    }

                    adapterremessas = new AdapterRemessaAceite(Listremessas, getActivity());
                    lista.setAdapter(adapterremessas);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Se ocorrer um erro
                    alert("Ocorreu um erro ao carregar informações sobre usuário!");
                }
            });

        }
    }

    public static void setViagem(Viagem v){
        viagem = v;

        Valor = viagem.getCidade_destino_valor();

        //Busca viagem
        Query query1 = FirebaseDatabase.getInstance().getReference("Viagens");
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Passar os dados para a interface grafica
                for(DataSnapshot obj : dataSnapshot.getChildren()) {
                    Viagem viagem1 = obj.getValue(Viagem.class);
                    if(obj.getKey().equals(viagem.getChave())){
                        viagem = viagem1;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Se ocorrer um erro
               // alert("Ocorreu um erro ao carregar informações sobre usuário!");
            }
        });

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

    private void alert(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
