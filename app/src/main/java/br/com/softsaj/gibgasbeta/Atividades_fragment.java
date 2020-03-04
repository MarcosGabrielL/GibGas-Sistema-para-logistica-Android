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
import Util.AdapterViagensLista;
import Util.AdapterViagensPersonalizado;

public class Atividades_fragment extends Fragment {

    Button BRemessas;
    Button BViages;
    ListView lista;
    Button voltar;
    List<Remessa> Listremessas;
    AdapterViagensLista adapterviagens;
    AdapterRemessaAceite adapterremessas;
    ArrayList<String> cidades;
    ArrayList<Viagem> viagens;
    Boolean VI;
    Remessa remessa;
    Viagem viagem1;

    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_atividades, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        alert("Sou Remetente");

        //Inicia componentes
        initi();

        //Inicia FireBase
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        //Carrega Lista de cidades com remessa na lista ou que já tiveram remessas entregues
        CarregaCidades();

        //Carrega Lista de viagens para Aceitar
        CarregaViagens();

        //Cria ação ao clicar na lista
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View view,
                                    int posicao, long id) {
                // TODO Auto-generated method stub

                AlertDialog alerta;

                //Se lista de Viagens
                if(VI) {

                    //Pega viagem clicada
                    viagem1 = adapterviagens.getItem(posicao);

                    //Confirmação
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Confirmação");
                    builder.setMessage("Deseja Criar uma remessa para enviar ou selecionar uma existente?");
                    builder.setPositiveButton("Existente", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

                            //Carrega Lista com Remessas Existentes
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, new SelecionarRemessa()).commit();
                            SelecionarRemessa.setViagem(viagem1);
                            //Pedi pra selecionar uma
                            //Carrega Modo Pagamento
                           // alert("Carrega Lista");
                           // alert("Selecione a remessa que deseja enviar");


                        }
                    });
                    builder.setNeutralButton("Criar Remessa", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

                            // /Abre pagina para cadastrar remessa
                            // Cadastra Destino (Lista com as cidades da viagem)
                            // Cadastra Carga
                            // Modo Pagamento
                            // Looby *&
                            alert("Criar Remessa");


                        }
                    });
                    alerta = builder.create();
                    alerta.show();

                }else{

                    //Pega remessa clicada
                    remessa = adapterremessas.getItem(posicao);

                    //Opções de Editar/Excluir
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Opções para Remessa!");
                    builder.setMessage("Tem certeza que deseja excluir Remessa?");
                    builder.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

                            //alert("Editar Remessa");
                            //Abre tela editar remessa *&
                        }
                    });
                    builder.setNegativeButton("Excluir", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

                           // alert("Excluir Remessa da lista");
                            //Cancela e Exclui Remessa
                            databaseReference.child("Remessas").child(remessa.getChave()).removeValue();


                            //atualiza lista de remessas
                            CarregaRemessas();
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
                        .replace(R.id.container, new Maps_Principal_Fragemnt()).commit();
            }
        });

        //Cria ação ao clicar no botão Remessa
        BRemessas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                VI = false;

                //Carrega Lista de Remessas Criadas
                CarregaRemessas();

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

                //Carrega Lista de Viagens
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
        BRemessas = (Button) getView().findViewById(R.id.button32);
        BViages = (Button) getView().findViewById(R.id.button31);
        voltar = (Button) getView().findViewById(R.id.button18);
        lista = (ListView) getView().findViewById(R.id.list);
        cidades = new ArrayList<>();
        viagens = new ArrayList<>();
        Listremessas = new ArrayList<>();
        VI = true;
    }

    public void CarregaRemessas(){

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

    public void CarregaViagens(){

        viagens.clear();

        if(user == null){
            //getActivity().getFragmentManager().popBackStack();
        }else{
            //Completa os campos
            String id = user.getUid();

            Query query1 = FirebaseDatabase.getInstance().getReference("Viagens");
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Passar os dados para a interface grafica
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        Viagem viagem = obj.getValue(Viagem.class);
                        viagem.setChave(obj.getKey());

                        //Percorre todas as cidades da lista para filtrar
                        for(String ciudad : cidades) {
                            Viagem v = new Viagem();
                            //Verifica se a viagem passa pela cidade de destino da remessa
                            try {
                                if (checkSimilarity(viagem.getCidade1(), ciudad) >= 0.5) {

                                    v.setCidade_destino(viagem.getCidade1());
                                    v.setCidade1_origem(viagem.getCidade_origem());
                                    v.setCidade_destino_valor(viagem.getCidade1_valor());
                                    v.setData_chegada(viagem.getCidade1_data());
                                    v.setTipo(viagem.getTipo());
                                    v.setRetirada(viagem.getRetirada());
                                    viagens.add(v);
                                    break;

                                } else if (checkSimilarity(viagem.getCidade2(), ciudad) >= 0.5) {

                                    v.setCidade_destino(viagem.getCidade2());
                                    v.setCidade1_origem(viagem.getCidade_origem());
                                    v.setCidade_destino_valor(viagem.getCidade2_valor());
                                    v.setData_chegada(viagem.getCidade2_data());
                                    v.setTipo(viagem.getTipo());
                                    v.setRetirada(viagem.getRetirada());
                                    viagens.add(v);
                                    break;

                                } else if (checkSimilarity(viagem.getCidade3(), ciudad) >= 0.5) {

                                    v.setCidade_destino(viagem.getCidade3());
                                    v.setCidade1_origem(viagem.getCidade_origem());
                                    v.setCidade_destino_valor(viagem.getCidade3_valor());
                                    v.setData_chegada(viagem.getCidade3_data());
                                    v.setTipo(viagem.getTipo());
                                    v.setRetirada(viagem.getRetirada());
                                    viagens.add(v);
                                    break;

                                } else if (checkSimilarity(viagem.getCidade4(), ciudad) >= 0.5) {

                                    v.setCidade_destino(viagem.getCidade4());
                                    v.setCidade1_origem(viagem.getCidade_origem());
                                    v.setCidade_destino_valor(viagem.getCidade4_valor());
                                    v.setData_chegada(viagem.getCidade4_data());
                                    v.setTipo(viagem.getTipo());
                                    v.setRetirada(viagem.getRetirada());
                                    viagens.add(v);
                                    break;

                                } else if (checkSimilarity(viagem.getCidade5(), ciudad) >= 0.5) {

                                    v.setCidade_destino(viagem.getCidade5());
                                    v.setCidade1_origem(viagem.getCidade_origem());
                                    v.setCidade_destino_valor(viagem.getCidade5_valor());
                                    v.setData_chegada(viagem.getCidade5_data());
                                    v.setTipo(viagem.getTipo());
                                    v.setRetirada(viagem.getRetirada());
                                    viagens.add(v);
                                    break;

                                } else if (checkSimilarity(viagem.getCidade6(), ciudad) >= 0.5) {

                                    v.setCidade_destino(viagem.getCidade6());
                                    v.setCidade1_origem(viagem.getCidade_origem());
                                    v.setCidade_destino_valor(viagem.getCidade6_valor());
                                    v.setData_chegada(viagem.getCidade6_data());
                                    v.setTipo(viagem.getTipo());
                                    v.setRetirada(viagem.getRetirada());
                                    viagens.add(v);
                                    break;

                                } else if (checkSimilarity(viagem.getCidade7(), ciudad) >= 0.5) {

                                    v.setCidade_destino(viagem.getCidade7());
                                    v.setCidade1_origem(viagem.getCidade_origem());
                                    v.setCidade_destino_valor(viagem.getCidade7_valor());
                                    v.setData_chegada(viagem.getCidade7_data());
                                    v.setTipo(viagem.getTipo());
                                    v.setRetirada(viagem.getRetirada());
                                    viagens.add(v);
                                    break;

                                } else if (checkSimilarity(viagem.getCidade8(), ciudad) >= 0.5) {

                                    v.setCidade_destino(viagem.getCidade8());
                                    v.setCidade1_origem(viagem.getCidade_origem());
                                    v.setCidade_destino_valor(viagem.getCidade8_valor());
                                    v.setData_chegada(viagem.getCidade8_data());
                                    v.setTipo(viagem.getTipo());
                                    v.setRetirada(viagem.getRetirada());
                                    viagens.add(v);
                                    break;

                                } else if (checkSimilarity(viagem.getCidade_destino(), ciudad) >= 0.5) {

                                    v.setCidade_destino(viagem.getCidade_destino());
                                    v.setCidade1_origem(viagem.getCidade_origem());
                                    v.setCidade_destino_valor(viagem.getCidade_destino_valor());
                                    v.setData_chegada(viagem.getData_chegada());
                                    v.setTipo(viagem.getTipo());
                                    v.setRetirada(viagem.getRetirada());
                                    viagens.add(v);
                                    break;

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }


                    adapterviagens = new AdapterViagensLista(viagens, getActivity(), cidades);
                    lista.setAdapter(adapterviagens);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Se ocorrer um erro
                    alert("Ocorreu um erro ao carregar informações sobre usuário!");
                }
            });

        }

    }

    public void CarregaCidades(){

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
                        cidades.add(remessa.getEntregaCidade());
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Se ocorrer um erro
                    alert("Ocorreu um erro ao carregar informações sobre usuário!");
                }
            });

        }

        //Busca remessas aceitadas e add as cidades na lista
        //Busca nas remessas da lista e add as cidades de destino
        final ArrayList<String> citys = new ArrayList<>();

        if(user == null){
            //getActivity().getFragmentManager().popBackStack();
        }else{
            //Completa os campos
            String id = user.getUid();

            //Query
            Query query1 = FirebaseDatabase.getInstance().getReference("Remessa_Aceite")
                    .orderByChild("idRemetente")
                    .equalTo(id);
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Passar os dados para a interface grafica
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        Remessa remessa = obj.getValue(Remessa.class);
                        cidades.add(remessa.getEntregaCidade());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Se ocorrer um erro
                    alert("Ocorreu um erro ao carregar informações sobre usuário!");
                }
            });

        }


        //Busca remessas finalizadas e add as cidades na lista *&


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
