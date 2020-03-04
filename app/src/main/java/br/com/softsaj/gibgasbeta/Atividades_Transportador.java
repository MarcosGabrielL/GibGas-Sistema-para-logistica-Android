package br.com.softsaj.gibgasbeta;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import Util.AdapterViagens;
import Util.AdapterViagensLista;
import Util.AdapterViagensPersonalizado;

public class Atividades_Transportador extends Fragment {

    Button BRemessas;
    Button BViages;
    ListView lista;
    Button voltar;
    List<Remessa> Listremessas;
    AdapterViagens adapterviagens;
    AdapterRemessaAceite adapterremessas;
    ArrayList<String> cidades;
    ArrayList<Viagem> viagens;
    Boolean VI;
    String id;
    Viagem viagem;

    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_transportador_atividades, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Inicia componentes
        BRemessas = (Button) getView().findViewById(R.id.button32);
        BViages = (Button) getView().findViewById(R.id.button31);
        voltar = (Button) getView().findViewById(R.id.button18);
        lista = (ListView) getView().findViewById(R.id.lista1);
        cidades = new ArrayList<>();
        viagens = new ArrayList<>();
        // Listremessas = new ArrayList<>();
        VI = false;

        //Inicia FireBase
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        //Carrega Lista de cidades com viagem na lista ou que já tiveram remessas entregues
        CarregaCidades();



        //Cria ação ao clicar na lista

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View view,
                                    int posicao, long id) {
                // TODO Auto-generated method stub

                AlertDialog alerta;
                //Se lista de Viagens
                if(VI) {

                    //Pega viagem clicada
                    viagem = adapterviagens.getItem(posicao);

                    //Opções de Editar/Excluir
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Opções para Viagem!");
                    builder.setMessage("Tem certeza que deseja excluir viagem?");
                    /* builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

                            alert("Editar Viagem");
                            //Abre tela editar Viagem *&
                        }
                    });*/
                    builder.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

                          //  alert("Editar Viagem");
                            //Abre tela editar Viagem
                        }
                    });
                    builder.setNegativeButton("Excluir", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {


                            //Cancela e Exclui Viagem
                            databaseReference.child("Viagens").child(viagem.getChave()).removeValue();
                            alert("Excluido com sucesso!");
                            //atualiza lista de viagens
                            CarregaViagens();
                        }
                    });
                    alerta = builder.create();
                    alerta.show();


                }else{

                    //Pega remessa clicada
                    final Remessa remessa = adapterremessas.getItem(posicao);

                    //Confirmação
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Confirmação!");
                    builder.setMessage("Tem certeza que deseja selecionar esta remessa?");
                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

                            //Evia notificação ao remetente *&
                            AvisaRemetente(remessa);
                            alert("Espera remetente confirmar a viagem!");

                        }
                    });
                    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            //Faz nada
                        }
                    });
                    alerta = builder.create();
                    alerta.show();

                }


            }
        });


        //Cria ação ao clicar em voltar
        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Volta ao inicio
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Maps_Principal_Trans_Fragemnt()).commit();
            }
        });

        //Cria ação ao clicar no botão Remessa
        BRemessas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                VI = false;

                //Carrega Lista de Remessas para aceitar
                CarregaCidades();

                //Muda backgroung e foreground do botões
                //Botão fica escuro com texto branco
                BRemessas.setBackgroundResource(R.drawable.button_quadrado_escuro);
                BRemessas.setTextColor(Color.parseColor("#ffffff"));
                //Outro botão fica branco com texto escuro
                BViages.setBackgroundResource(R.drawable.button_quadrado_borda_branco);
                BViages.setTextColor(Color.parseColor("#333333"));


            }
        });


        //Cria ação ao clicar no botão Viagens
        BViages.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                VI = true;

                //Carrega Lista de Viagens Criadas
                CarregaViagens();

                //Muda backgroung e foreground do botões
                //Botão fica escuro com texto branco
                BViages.setBackgroundResource(R.drawable.button_quadrado_escuro);
                BViages.setTextColor(Color.parseColor("#ffffff"));
                //Outro botão fica branco com texto escuro
                BRemessas.setBackgroundResource(R.drawable.button_quadrado_borda_branco);
                BRemessas.setTextColor(Color.parseColor("#333333"));

            }
        });
    }

    public void initi(){

    }

    public void CarregaCidades(){

        //Busca nas remessas da lista e add as cidades de destino
        cidades.clear();

        if(user == null){
            //getActivity().getFragmentManager().popBackStack();
        }else {
            //Completa os campos
            id = user.getUid();

            //Query
            Query query1 = FirebaseDatabase.getInstance().getReference("Viagens")
                    .orderByChild("id")
                    .equalTo(id);
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Passar os dados para a interface grafica
                    for (DataSnapshot obj : dataSnapshot.getChildren()) {

                        Viagem viagem = obj.getValue(Viagem.class);

                        cidades.add(viagem.getCidade_destino());


                            cidades.add(viagem.getCidade1());
                            cidades.add(viagem.getCidade2());
                            cidades.add(viagem.getCidade3());
                            cidades.add(viagem.getCidade4());
                            cidades.add(viagem.getCidade5());
                            cidades.add(viagem.getCidade6());
                            cidades.add(viagem.getCidade7());
                            cidades.add(viagem.getCidade8());
                        }

                    FirebaseDatabase.getInstance().getReference("Remessa_Aceite")
                            .orderByChild("idEntregador")
                            .equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Passar os dados para a interface grafica
                            for(DataSnapshot obj : dataSnapshot.getChildren()){
                                Remessa remessa = obj.getValue(Remessa.class);
                                // alert("Cidade: "+remessa.getEntregaCidade());
                                cidades.add(remessa.getEntregaCidade());

                            }


                            //Carrega Lista de Remessas para aceitar
                            FirebaseDatabase.getInstance().getReference("Remessas")
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //Passar os dados para a interface grafica
                                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                                        Remessa remessa = obj.getValue(Remessa.class);

                                        Listremessas = new ArrayList<>();
                                       // Listremessas.add(remessa);



                                           //Percorre todas as cidades da lista para filtrar
                                            for(String ciudad : cidades) {
                                                //Verifica se a a remessa tem como destino uma das cidades das viagens cujo transportador salvou
                                                try {
                                                    if (checkSimilarity(remessa.getEntregaCidade(), ciudad) >= 0.5) {
                                                        Listremessas.add(remessa);
                                                        break;
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                    }

                                   adapterremessas = new AdapterRemessaAceite(Listremessas, getActivity());
                                   lista.setAdapter(adapterremessas);
                                    alert("AQui");

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    //Se ocorrer um erro
                                    alert("Ocorreu um erro ao carregar informações sobre usuário!");
                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //Se ocorrer um erro
                            alert("Ocorreu um erro ao carregar informações sobre usuário!");
                        }
                    });

                    }


                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Se ocorrer um erro
                    alert("Ocorreu um erro ao carregar informações sobre usuário!");
                }
            });


        }

    }


    public void CarregaViagens(){

        viagens.clear();

        FirebaseDatabase.getInstance().getReference("Viagens")
                    .orderByChild("id")
                    .equalTo(id)
        .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Passar os dados para a interface grafica
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        Viagem viagem = obj.getValue(Viagem.class);
                        viagem.setChave(obj.getKey());
                        viagens.add(viagem);
                    }

                    adapterviagens = new AdapterViagens(viagens, getActivity());
                    lista.setAdapter(adapterviagens);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Se ocorrer um erro
                    alert("Ocorreu um erro ao carregar informações sobre usuário!");
                }
            });


    }

    public void AvisaRemetente(final Remessa remessa){

        //Pega valor da Viagem
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Valor Cobrado pela Viagem");
        // I'm using fragment here so I'm using getView() to provide ViewGroup
        // but you can provide here any other instance of ViewGroup from your Fragment / Activity
        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.text_inpu_password, (ViewGroup) getView(), false);
        // Set up the input
        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(viewInflated);

        // Set up the buttons
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //Verifica se é numero
                if(input.getText().toString().equals("")){
                   alert("Digite um valor!");
                }else{
                    try{
                        //Verifica se é numero
                        Float f = Float.parseFloat(input.getText().toString());
                        //Cria Remessa_Aguarda
                        Date a = new Date();
                        SimpleDateFormat formatador = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        remessa.setValorViagem(input.getText().toString());
                        remessa.setIdEntregador(user.getUid());
                        remessa.setChave(remessa.getEntregaCidade()+"--"+remessa.getIdRemetente()+"--yYyYy"+formatador.format(a));
                        databaseReference.child("Remessa_Aguarda").child(remessa.getChave()).setValue(remessa);

                    }catch (Exception e){
                        alert("O Texto não é um número");
                    }
                }

            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();


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
