package br.com.softsaj.gibgasbeta;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
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

import Bean.Local;
import Bean.Remessa;
import Util.GPSTracker;

public class rastrear_fragmento extends Fragment implements OnMapReadyCallback {

    private FirebaseAuth auth ;
    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private static final  int REQUEST_CODE_PERMISSION = 2;
    String mPermission = android.Manifest.permission.ACCESS_FINE_LOCATION;
    GPSTracker gps;
    String value = null;
    GoogleMap mMap;

    Remessa remessa;
    Button voltar;
    Local local;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_maps_acompanhar, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        remessa = Acompanhar_Remessas.getRemessa();
        voltar = (Button) getView().findViewById(R.id.button18);

        //Inicia FireBase
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //volta para o looby
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Acompanhar_Remessas()).commit();


            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

        try{
            if (ActivityCompat.checkSelfPermission(getActivity(),mPermission)!= MockPackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(),new String[]{mPermission},REQUEST_CODE_PERMISSION);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Busca Lat e Long Celular
        gps = new GPSTracker(getActivity());

        mMap = googleMap;
        if (gps.canGetLocation()){
            //Busca Localização dos Transportadores
            String id = user.getUid();
            //Query
            Query query1 = FirebaseDatabase.getInstance().getReference("Local_Transportador")
                    .orderByChild("id")
                    .equalTo(remessa.getIdEntregador());
            //databaseReference.child("Local_Remetentes").addListenerForSingleValueEvent(new ValueEventListener() {
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Passa lista de locais dos remetentes pra lista
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                       local = obj.getValue(Local.class);
                    }

                    //Pega ultima localização do transportador
                        double lat = Double.parseDouble(local.getLatitude());
                        double lon = Double.parseDouble(local.getLongitude());

                            //Marca trasportador no mapa
                            int height = 100;
                            int width = 100;
                            BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.remetente);
                            Bitmap b=bitmapdraw.getBitmap();
                            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                            LatLng rem = new LatLng(lat,lon);
                            mMap.addMarker(new MarkerOptions().position(rem)
                                    .title("Sua Remessa").icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(rem));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(17));

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Se ocorrer um erro
                    alert("Ocorreu um erro ao carregar informações!");
                    //Query
                }
            });


        }else{
            gps.showSettingsAlert();
        }


    }

    private void alert(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

}
