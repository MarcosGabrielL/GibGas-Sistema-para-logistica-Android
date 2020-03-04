package br.com.softsaj.gibgasbeta;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.Timer;
import java.util.TimerTask;

import Bean.Local;
import Bean.Remessa;
import Bean.Transportador;
import Bean.Viagem;
import Util.AdapterViagensPersonalizado;
import Util.GPSTracker;
import Util.Notify_Chamada;

public class Chamar_transportador extends Fragment {

    private FirebaseAuth auth ;
    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    GPSTracker gps;
    String idd;

    double latitude;
    double longitude;
    double lat;
    double lon;
    boolean cancelou;
    Transportador transportador;
    ArrayList<Transportador> transportadors_chamados;
    ArrayList<Viagem> viagens;

    Remessa remessa;
    Remessa re;
    ArrayList<Local> locais;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.destino_chamar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Inicia FireBase
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        transportadors_chamados = new ArrayList<Transportador>();

        remessa = Chamar_ou_Lista_ou_Adcionar.getRemessa();
        Date a = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        remessa.setIdRemetente(user.getUid());

        locais = new ArrayList<Local>();
        viagens = new ArrayList<Viagem>();

        Button voltar = (Button) getView().findViewById(R.id.button18);
        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                cancelar();
                //Abre Mapa para escolha
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Destino_Escolhe_mapa()).commit();


            }
        });

        chamarTransportadores();

    }

    public void chamarTransportadores(){

        //Busca Lat e Long Celular
        gps = new GPSTracker(getActivity());
        //Busca Localização do Remetente
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();
        transportador = new Transportador();

        //Verifica o ultimo local dos transportadores
        String id = user.getUid();
        //Query
        Query query1 = FirebaseDatabase.getInstance().getReference("Local_Transportador");
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Passa lista de locais dos transportadores pra lista
                for(DataSnapshot obj : dataSnapshot.getChildren()){
                    Local local = obj.getValue(Local.class);
                    locais.add(local);
                }

                //Percorre os locais
                for(Local l : locais){
                    lat = Double.parseDouble(l.getLatitude());
                    lon = Double.parseDouble(l.getLongitude());
                    //Query
                    Query query1 = FirebaseDatabase.getInstance().getReference("Viagens");
                    query1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot obj : dataSnapshot.getChildren()){
                                Viagem viagem = obj.getValue(Viagem.class);

                                boolean passa = false;
                                //Verifica se a viagem passa na cidade destino
                                //Verifica se chega até a data limite
                                Date data_chegada;
                                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                                try {
                                    //Pega data limite
                                    Date data_limite = format.parse(remessa.getEntregaLimiteData());
                                    //VERIFICA SE VIAGEM PASSA NO LOCAL DA ENTREGA
                                    if(checkSimilarity(viagem.getCidade1(),remessa.getEntregaCidade())>=0.5){
                                    //VERIFICA SE DATA PREVISTA DE CHEGADA DO TRANSPORTADOR É ANTERIOR A DATA LIMITE DE ENTREGA
                                        data_chegada = format.parse(viagem.getCidade1_data());
                                        if(data_chegada.after(data_limite)) {
                                        }else {
                                            // VERIFICA SE VALOR É MAIOR OU IGUAL AO LIMITE DADO PELO REMETENTE *&
                                            if(VerificaValor(viagem.getCidade1_valor())){
                                                passa = true;
                                            }
                                        }
                                    } else if(checkSimilarity(viagem.getCidade2(),remessa.getEntregaCidade())>=0.5){
                                        data_chegada = format.parse(viagem.getCidade2_data());
                                        if(data_chegada.after(data_limite)) {
                                        }else {
                                            if(VerificaValor(viagem.getCidade2_valor())){
                                                passa = true;
                                            }
                                        }
                                    }else if(checkSimilarity(viagem.getCidade3(),remessa.getEntregaCidade())>=0.5){
                                        data_chegada = format.parse(viagem.getCidade3_data());
                                        if(data_chegada.after(data_limite)) {
                                        }else {
                                            if(VerificaValor(viagem.getCidade3_valor())){
                                                passa = true;
                                            }
                                        }
                                    }else if(checkSimilarity(viagem.getCidade4(),remessa.getEntregaCidade())>=0.5){
                                        data_chegada = format.parse(viagem.getCidade4_data());
                                        if(data_chegada.after(data_limite)) {
                                        }else {
                                            if(VerificaValor(viagem.getCidade4_valor())){
                                                passa = true;
                                            }
                                        }
                                    }else if(checkSimilarity(viagem.getCidade5(),remessa.getEntregaCidade())>=0.5) {
                                        data_chegada = format.parse(viagem.getCidade5_data());
                                        if (data_chegada.after(data_limite)) {
                                        } else {
                                            if (VerificaValor(viagem.getCidade5_valor())) {
                                                passa = true;
                                            }
                                        }
                                    }else if(checkSimilarity(viagem.getCidade6(),remessa.getEntregaCidade())>=0.5) {
                                        data_chegada = format.parse(viagem.getCidade6_data());
                                        if (data_chegada.after(data_limite)) {
                                        } else {
                                            if (VerificaValor(viagem.getCidade6_valor())) {
                                                passa = true;
                                            }
                                        }
                                    }else if(checkSimilarity(viagem.getCidade7(),remessa.getEntregaCidade())>=0.5) {
                                        data_chegada = format.parse(viagem.getCidade7_data());
                                        if (data_chegada.after(data_limite)) {
                                        } else {
                                            if (VerificaValor(viagem.getCidade7_valor())) {
                                                passa = true;
                                            }
                                        }
                                    }else if(checkSimilarity(viagem.getCidade8(),remessa.getEntregaCidade())>=0.5) {
                                        data_chegada = format.parse(viagem.getCidade8_data());
                                        if (data_chegada.after(data_limite)) {
                                        } else {
                                            if (VerificaValor(viagem.getCidade8_valor())) {
                                                passa = true;
                                            }
                                        }
                                    }else if(checkSimilarity(viagem.getCidade_destino(),remessa.getEntregaCidade())>=0.5){
                                        data_chegada = format.parse(viagem.getData_chegada());
                                        if(data_chegada.after(data_limite)) {
                                        }else {
                                                passa = true;
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                //VERIFICA SE ESTA PERTO OU SE VIAGEM PASSA NA CIDADE DESTINO
                                if(distance(latitude, longitude,lat,lon,"K") < 5 || passa == true ){
                                    viagens.add(viagem);
                                    //Carrega Transportador com id da viagem que passa no local
                                        idd =  viagem.getId();
                                        Query query1 = FirebaseDatabase.getInstance().getReference("Transportador")
                                                .orderByChild("id")
                                                .equalTo(idd);
                                        query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                for(DataSnapshot obj : dataSnapshot.getChildren()){
                                                    transportador = obj.getValue(Transportador.class);
                                                }
                                                //Veirifica se transportador já esta sendo chamado
                                                if(transportador.isChama()){
                                                    //já esta sendo chamado para outra encomenda
                                                }else {
                                                    //Atualiza campo CHAMA = true do Transportador;
                                                    transportador.setChama(true);
                                                    Date a = new Date();
                                                    SimpleDateFormat formatador = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                                                    transportador.setIdChamada(remessa.getIdRemetente()+formatador.format(a));
                                                    databaseReference.child("Transportador").child(transportador.getId()).setValue(transportador);
                                                    transportadors_chamados.add(transportador);
                                                    //alert(transportador.getNome());
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                //Se ocorrer um erro
                                                alert("Ocorreu um erro ao carregar informações sobre Transportador!");
                                            }
                                        });

                                }
                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //Se ocorrer um erro
                            alert("Ocorreu um erro ao carregar informações sobre Viagens!");
                        }
                    });


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Se ocorrer um erro
                alert("Ocorreu um erro ao carregar informações sobre Local!");
                //Query
            }
        });



        //Salva No Banco a Remessa que esta chamando
        remessa.setChama(true);
        Date a = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        remessa.setIdChamada(remessa.getIdRemetente()+formatador.format(a));
        //Salva Remessa que esta chamando
        databaseReference.child("Remessa_Chamando").child(remessa.getIdChamada()).setValue(remessa);

        //getActivity().startService(new Intent(getActivity(), Notify_Chamada.class));
        new Thread(new Runnable() {
            @Override
            public void run() {
                Verifica_Aceite();
            }
        }).start();
        /*final Handler handler = new Handler(Looper.getMainLooper());
        final int tempoDeEspera = 1000;

        new Thread(new Runnable() {
            @Override
            public void run() {
                    SystemClock.sleep(tempoDeEspera);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            //alert("Dentro do loop");
                           Verifica_Aceite();

                        }
                    });
            }
         }).start();
        Timer t;
        int TimeCounter = 0;

        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alert("Dentro do loop");
                    }
                });
            }
        },1000, 1000);
        */

    }

    public void cancelar(){
        cancelou = true;
        alert("Cancelando Chamada");
        // Chama == false em todos os transportadores_chamados
        for(Transportador transportador : transportadors_chamados){
            transportador.setChama(false);
            transportador.setIdChamada("");
            databaseReference.child("Transportador").child(transportador.getId()).setValue(transportador);
        }
        remessa.setChama(false);
        databaseReference.child("Remessa_Chamando").child(remessa.getIdChamada()).setValue(remessa);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new Chamar_ou_Lista_ou_Adcionar()).commit();

    }

    private void alert(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }

        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::    This function converts decimal degrees to radians                         :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::    This function converts radians to decimal degrees                         :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
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

    public void Verifica_Aceite(){

        if(cancelou == true){

        }else {

            // SystemClock.sleep(2000);

            //alert("Verificando Aceite");

            //Verifica se a remessa foi Aceita
            //Query
            Query query1 = FirebaseDatabase.getInstance().getReference("Remessa_Chamando")
                    .orderByChild("idChamada")
                    .equalTo(remessa.getIdChamada());
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Passar os dados para a interface grafica
                    for (DataSnapshot obj : dataSnapshot.getChildren()) {
                        re = obj.getValue(Remessa.class);
                    }

                    //Verifica se alguem aceitou a viagem ( Aceite = true)
                    if (re.isAceitou()) {
                        //Se aceitou
                        // Chama == false em todos os transportadores_chamados
                        for (Transportador transportador : transportadors_chamados) {
                            transportador.setChama(false);
                            transportador.setIdChamada("");
                            databaseReference.child("Transportador").child(transportador.getId()).setValue(transportador);
                        }
                        //Chama tipo de Pagamento
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Destino_modo_pagamento()).commit();
                        Destino_modo_pagamento.setRemessa(remessa);
                        Destino_modo_pagamento.setTipo("R");
                        Destino_modo_pagamento.setCaminho("Chamar");

                    } else {
                        Verifica_Aceite2();
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

    public void Verifica_Aceite2(){
        if(cancelou == true){

        }else {

            //SystemClock.sleep(1000);

            //alert("Verificando Aceite 2");

            //Verifica se a remessa foi Aceita
            //Query
            Query query1 = FirebaseDatabase.getInstance().getReference("Remessa_Chamando")
                    .orderByChild("idChamada")
                    .equalTo(remessa.getIdChamada());
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Passar os dados para a interface grafica
                    for (DataSnapshot obj : dataSnapshot.getChildren()) {
                        re = obj.getValue(Remessa.class);
                    }

                    //Verifica se alguem aceitou a viagem ( Aceite = true)
                    if (re.isAceitou()) {
                        //Se aceitou
                        // Chama == false em todos os transportadores_chamados
                        for (Transportador transportador : transportadors_chamados) {
                            transportador.setChama(false);
                            transportador.setIdChamada("");
                            databaseReference.child("Transportador").child(transportador.getId()).setValue(transportador);
                        }
                        //Chama tipo de Pagamento
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Destino_modo_pagamento()).commit();
                        Destino_modo_pagamento.setRemessa(remessa);
                        Destino_modo_pagamento.setTipo("R");
                        Destino_modo_pagamento.setCaminho("Chamar");

                    } else {
                        Verifica_Aceite();
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
