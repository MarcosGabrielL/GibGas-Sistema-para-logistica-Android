package br.com.softsaj.gibgasbeta;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import Util.GPSTracker;

import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Destino_Escolhe_mapa extends Fragment implements OnMapReadyCallback,
        OnMapClickListener, OnMapLongClickListener, OnCameraIdleListener{


    private FirebaseAuth auth ;
    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    GoogleMap mMap;
    private List<Address> addresses;

    FirebaseDatabase database;
    DatabaseReference myRef;

    private static final  int REQUEST_CODE_PERMISSION = 2;
    String mPermission = android.Manifest.permission.ACCESS_FINE_LOCATION;
    GPSTracker gps;
    String value = null;

    private Button enviar;
    static String rua = null;
    static String cidade = null;
    static String bairro = null;
    static String numero = null;
    static String cep = null;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_maps_escolher_destino, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        Button voltar = (Button) getView().findViewById(R.id.button18);
        Button aqui = (Button) getView().findViewById(R.id.button11);
        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //volta para destino
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Destino_Escolhe_1_Reme()).commit();
            }
        });
        aqui.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //volta para destino
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Destino_Escolhe_1_Reme()).commit();
                Destino_Escolhe_1_Reme.veiodomapa(cidade, rua, numero, bairro, cep);
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
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;
        //Busca Lat e Long Celular
        gps = new GPSTracker(getActivity());

        if (gps.canGetLocation()){
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            database = FirebaseDatabase.getInstance();
            alert(latitude+" "+longitude);
            myRef = database.getReference("Location");
            myRef.setValue(latitude+","+longitude);

            if(latitude==0 && longitude==0){

                //Marca Softsaj no Mapa
                LatLng softsaj = new LatLng(-12.997530, -38.518507);
                googleMap.addMarker(new MarkerOptions().position(softsaj)
                        .title("Softsaj"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(softsaj));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(17));

            }else{
                //Marca Pessoa no mapa
                LatLng sydney = new LatLng(latitude,longitude);
                googleMap.addMarker(new MarkerOptions().position(sydney)
                        .title("Local Entrega"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(17));
            }



        }else{
            gps.showSettingsAlert();
        }

        mMap.setOnMapClickListener(new OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng)
                        .title("Local Entrega"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(17));


                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                    try {
                        addresses = geocoder.getFromLocation( latLng.latitude,  latLng.longitude, 1);


                        rua = addresses.get(0).getAddressLine(0);// rua numero e bairro
                        cidade = addresses.get(0).getLocality();//cidade
                        numero = rua.replaceAll("[^0-9]", "");
                        cep = addresses.get(0).getAdminArea().replaceAll("[^0-9]", "");//cep
                        bairro = addresses.get(0).getAdminArea().replaceAll("[a-zA-Z]", "");
                       String  address = "Rua: " + rua + "Numero: " + numero + "Cidade:  " + cidade + " bairro: "
                               + bairro +"CEP:  " + cep;

                        alert("Endereço: "+address);

                    } catch (IOException e) {
                        alert(e.getMessage());
                    }

                }

        });

    }

    @Override
    public void onMapClick(LatLng point) {

        /*if(marker != null){
            marker.remove();
        }else{
            marker = mMap.addMarker(new MarkerOptions().position(point)
                    .title("Destino"));
        }
*/          alert("OnMapClique Ação");
    }

    @Override
    public void onMapLongClick(LatLng point) {
       /* if(marker != null){
            marker.remove();
        }else{
            marker = mMap.addMarker(new MarkerOptions().position(point)
                    .title("Destino"));
        }*/alert("OnMapLongClick Ação");
    }

    @Override
    public void onCameraIdle() {
       /* if(marker != null){
            marker.remove();
        }else{
            marker = mMap.addMarker(new MarkerOptions().position(mMap.getCameraPosition().target)
                    .title("Destino"));
        }
        */alert("OnCameraIdle Ação");
    }


    private void alert(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }


}
