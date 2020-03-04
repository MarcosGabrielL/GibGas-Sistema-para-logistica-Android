package br.com.softsaj.gibgasbeta;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.test.mock.MockPackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Bean.Local;
import Bean.Remetentes;
import Bean.Transportador;
import Util.GPSTracker;

public class Maps_Principal_Trans_Fragemnt extends Fragment implements OnMapReadyCallback{


    private FirebaseAuth auth ;
    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseDatabase database;
    DatabaseReference myRef;
    Local local;
    ArrayList<Local> locais;

    private static final  int REQUEST_CODE_PERMISSION = 2;
    String mPermission = android.Manifest.permission.ACCESS_FINE_LOCATION;
    GPSTracker gps;

    double latitude;
    double longitude;
    GoogleMap mMap;
    String value = null;
    static String id;

    public static boolean chamou;
    public static Transportador transportador;
    private Button enviar;
    static Context context;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_maps_adicoinar_viagem, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        local = new Local();
        chamou = false;
        enviar = (Button) getView().findViewById(R.id.button11);

        //Inicia FireBase
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        locais = new ArrayList<>();
        id = user.getUid();
        context = getActivity();

        enviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //Escolher Destino 1
                // getChildFragmentManager().beginTransaction().replace(R.id.container, new Destino_Escolhe_1_Reme()).commit();
                //startActivity(new Intent(getActivity(),Destino_Escolhe_mapa.class));
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Criar_Viagem_1()).commit();

            }
        });

        //Checa permissão
        try{
            if (ActivityCompat.checkSelfPermission(getActivity(),mPermission)!= MockPackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(),new String[]{mPermission},REQUEST_CODE_PERMISSION);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map3);
        mapFragment.getMapAsync(this);



    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        //Busca Lat e Long Celular
        gps = new GPSTracker(getActivity());

        mMap = googleMap;
        if (gps.canGetLocation()){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("Location");
            myRef.setValue(latitude+","+longitude);

            if(latitude==0 && longitude==0){

                //Marca Softsaj no Mapa
                LatLng softsaj = new LatLng(-12.978148, -39.281246);
                googleMap.addMarker(new MarkerOptions().position(softsaj)
                        .title("Softsaj"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(softsaj));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(17));

            }else{


                            //Atualiza posição do Transportador a cada 30 segundos
                            //Marca Pessoa no mapa

                int height = 100;
                int width = 100;
                BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.marker);
                Bitmap b=bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                LatLng rem = new LatLng(latitude,longitude);
                mMap.addMarker(new MarkerOptions().position(rem)
                        .title("Eu").icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(rem));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(17));
                 local.setLatitude(String.valueOf(latitude));
                 local.setLongitude(String.valueOf(longitude));
                 local.setId(user.getUid());
                 databaseReference.child("Local_Transportador").child(user.getUid()).setValue(local);


            /*
            int height = 100;
                int width = 100;
                BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.foguete);
                Bitmap b=bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

                LatLng rem = new LatLng(-12.969037, -39.263259);
                googleMap.addMarker(new MarkerOptions().position(rem)
                        .title("Remetente").icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                LatLng rem1 = new LatLng(-12.969309, -39.264546);
                googleMap.addMarker(new MarkerOptions().position(rem1)
                        .title("Remetente").icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                LatLng rem2 = new LatLng(-12.968807, -39.261188);
                googleMap.addMarker(new MarkerOptions().position(rem2)
                        .title("Remetente").icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

               */

                //Busca Localização dos Remetentes
                String id = user.getUid();
                locais.clear();
                //Query
                Query query1 = FirebaseDatabase.getInstance().getReference("Local_Remetentes");
                //databaseReference.child("Local_Remetentes").addListenerForSingleValueEvent(new ValueEventListener() {
                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Passa lista de locais dos remetentes pra lista
                        for(DataSnapshot obj : dataSnapshot.getChildren()){
                            Local local = obj.getValue(Local.class);
                            locais.add(local);
                        }

                        //Percorre os locais
                        for(Local l : locais){
                            double lat = Double.parseDouble(l.getLatitude());
                            double lon = Double.parseDouble(l.getLongitude());

                            //Verifica se é menor que a distancia limite
                            if(distance(latitude, longitude,lat,lon,"K") < 5){
                                //Marca Remetente no mapa
                                int height = 70;
                                int width = 70;
                                BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.remetente);
                                Bitmap b=bitmapdraw.getBitmap();
                                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                                LatLng rem = new LatLng(lat,lon);
                                mMap.addMarker(new MarkerOptions().position(rem)
                                        .title("Remetente").icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                            }
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //Se ocorrer um erro
                        alert("Ocorreu um erro ao carregar informações!");
                        //Query
                    }
                });

            }
        }else{
            gps.showSettingsAlert();
        }


        //SystemClock.sleep(10000);

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                VerificaSeTemChamada();
            }
        }, 10000);


    }

    private void buscaTransportadores() {

        //Criz Quadrado LAT e LGN
        //Pesquisa as localizações dos transportadores
        //Se LatLong + x < Localização
        //Marca no mapa
        // alert("Aqui3");
    }


    private static void alert(String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public void pegarLocalRemetentes(){
    //Busca Localização dos Remetentes
    String id = user.getUid();
    locais.clear();
    //Query
        databaseReference.child("Local_Remetentes").addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            alert("Aqui");
            //Passa lista de locais dos remetentes pra lista
            for(DataSnapshot obj : dataSnapshot.getChildren()){
                Local local = obj.getValue(Local.class);
                locais.add(local);
            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            //Se ocorrer um erro
            alert("Ocorreu um erro ao carregar informações!");
            //Query
        }
    });
    }

    private double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
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
           // alert("Distancia: "+dist);
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

    public void VerificaSeTemChamada(){
        //Se usuario == TRANSPORTADOR
        //Se não ta chamando
        //alert("Verifica se tem chamada");
        //SystemClock.sleep(1000);
        if (chamou==false) {

            //Verifica se Transportador Chama == true
            Query query1 = FirebaseDatabase.getInstance().getReference("Transportador")
                    .orderByChild("id")
                    .equalTo(id);
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //alert("Carregando os dados!");
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        transportador = obj.getValue(Transportador.class);
                    }
                    //Se chamar == true Abre a pagina de chamada
                    if(transportador.isChama()) {
                        //alert("Chamou");
                        //Chama celular dos Tranportaddores perto/com viagem para o destino
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Chamando()).commit();
                        //Chamou vira verdadeiro e para de verificar se precisa chamar
                        chamou = true;
                    }else{
                        //Se chamar == false continua verificando se precisa chamar
                        Verifica_Chamar();
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

    public  void Verifica_Chamar(){

        //Se usuario == TRANSPORTADOR
        //Se não ta chamando
        if (chamou==false) {

            //Verifica se Transportador Chama == true
            Query query1 = FirebaseDatabase.getInstance().getReference("Transportador")
                    .orderByChild("id")
                    .equalTo(id);
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                   // alert("Carregando os dados!");
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        transportador = obj.getValue(Transportador.class);
                    }
                    //Se chamar == true Abre a pagina de chamada
                    if(transportador.isChama()) {
                        //Chama celular dos Tranportaddores perto/com viagem para o destino
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Chamando()).commit();
                        //Chamou vira verdadeiro e para de verificar se precisa chamar
                        chamou = true;
                    }else{
                        //Se chamar == false continua verificando se precisa chamar
                        VerificaSeTemChamada();
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

    public static void RecusarViagem(){

       chamou = false;

        //new Thread() {
        // @Override
        //  public void run() {
         //    VerificaSeTemChamada();
        // }
       // }.start();
    }

    public static Transportador getTransportador(){
        return  transportador;
    }

}
