package br.com.softsaj.gibgasbeta;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import Bean.Parada;
import Bean.Viagem;
import Util.AdapterParadas;
import Util.AdapterViagensPersonalizado;

public class Criar_Viagem_Paradas extends Fragment {

    Button Ok;
    Button voltar;
    Button add;

    static ArrayList<Parada> paradas;
    ListView listaDeParadas;
    ArrayList<String> lista;
    AdapterParadas adapterparadas;

    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public static Viagem viagem;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_enviar_rotas_paradas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Inicializa Firebase
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        //Inicia componentes interface gráfica
        iniciacomponentes();

        //Carrega lista de paradas da viagem
        carregaparadas();

        Ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //alert("Meio de Transporte");
              //  MeioDeTransporte();

                int i = 0;
                if(viagem.getParadas() == null || viagem.getParadas().size() == 0) {

                }else {
                    for (Parada parada : viagem.getParadas()) {
                        i++;
                        if (i == 1) {
                            viagem.setCidade1_origem(parada.getOrigem());
                            viagem.setCidade1(parada.getDestino());
                            viagem.setCidade1_data(parada.getDatachegada());
                            viagem.setCidade1_valor(parada.getValor());
                        }
                        if (i == 2) {
                            viagem.setCidade2_origem(parada.getOrigem());
                            viagem.setCidade2(parada.getDestino());
                            viagem.setCidade2_data(parada.getDatachegada());
                            viagem.setCidade2_valor(parada.getValor());
                        }
                        if (i == 3) {
                            viagem.setCidade3_origem(parada.getOrigem());
                            viagem.setCidade3(parada.getDestino());
                            viagem.setCidade3_data(parada.getDatachegada());
                            viagem.setCidade3_valor(parada.getValor());
                        }
                        if (i == 4) {
                            viagem.setCidade4_origem(parada.getOrigem());
                            viagem.setCidade4(parada.getDestino());
                            viagem.setCidade4_data(parada.getDatachegada());
                            viagem.setCidade4_valor(parada.getValor());
                        }
                        if (i == 5) {
                            viagem.setCidade5_origem(parada.getOrigem());
                            viagem.setCidade5(parada.getDestino());
                            viagem.setCidade5_data(parada.getDatachegada());
                            viagem.setCidade5_valor(parada.getValor());
                        }
                        if (i == 6) {
                            viagem.setCidade6_origem(parada.getOrigem());
                            viagem.setCidade6(parada.getDestino());
                            viagem.setCidade6_data(parada.getDatachegada());
                            viagem.setCidade6_valor(parada.getValor());
                        }
                        if (i == 1) {
                            viagem.setCidade7_origem(parada.getOrigem());
                            viagem.setCidade7(parada.getDestino());
                            viagem.setCidade7_data(parada.getDatachegada());
                            viagem.setCidade7_valor(parada.getValor());
                        }
                        if (i == 1) {
                            viagem.setCidade8_origem(parada.getOrigem());
                            viagem.setCidade8(parada.getDestino());
                            viagem.setCidade8_data(parada.getDatachegada());
                            viagem.setCidade8_valor(parada.getValor());
                        }
                    }
                }

                viagem.setId(user.getUid());
                paradas.clear();
                viagem.setParadas(paradas);

                 //add na lista Geral de Viagens Para Remetentes Verem
                databaseReference.child("Viagens").child(user.getUid() + "--" + viagem.getTipo() + "--" + viagem.getCidade_destino()).setValue(viagem);


                //Pagina final
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Criar_Confirmacao_Viagem()).commit();
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
               //volta pra origem/destino
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new Criar_Viagem_MeioTransporte()).commit();

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //Verifica se já chegou no limite
                if(paradas.size() >= 8){
                    alert("Limite de paradas atingido! Sugerimos dividir a viagem");
                }else{
                    //ppagina para adicionar parada
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, new Criar_Parada()).commit();
                    Criar_Parada.setViagem(viagem);
                }

            }
        });


    }

    public void iniciacomponentes(){

        //viagem = new Viagem();
        paradas = new ArrayList<>();
        lista = new ArrayList<>();
        listaDeParadas = (ListView) getView().findViewById(R.id.listaparadas);
        Ok = (Button) getView().findViewById(R.id.bok3);
        voltar  = (Button) getView().findViewById(R.id.button18);
        add  = (Button) getView().findViewById(R.id.button14);

    }

    public void carregaparadas() {



        if(viagem.getParadas() == null || viagem.getParadas().size() == 0) {
            Parada p = new Parada();
            p.setValor(viagem.getCidade_destino_valor());
            p.setOrigem(viagem.getCidade_origem());
            p.setDestino(viagem.getCidade_destino());
            p.setDatachegada(viagem.getData_chegada());


            ArrayList<Parada> paradas = new ArrayList<>();
            paradas.add(p);
            viagem.setParadas(paradas);

            adapterparadas = new AdapterParadas(paradas, getActivity());
            listaDeParadas.setAdapter(adapterparadas);

        }else {
            adapterparadas = new AdapterParadas(viagem.getParadas(), getActivity());
            listaDeParadas.setAdapter(adapterparadas);

        }


    }

   public static void setParada(ArrayList<Parada> lista){
        paradas = lista;
   }

    public static List<Parada> getParada(){
        return paradas;
    }

    public static Viagem getViagem(){
        return viagem;
    }

    public static void setViagem(Viagem v){
        viagem = v;
    }

    private void alert(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
