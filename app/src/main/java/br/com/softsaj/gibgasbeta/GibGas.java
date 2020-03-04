package br.com.softsaj.gibgasbeta;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import Bean.Remetentes;
import Bean.Transportador;
import Util.Notify_Chamada;

public class GibGas extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private GoogleMap mMap;
    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    public static Transportador transportador;
    public static Remetentes remetente;

    static String retorno = "Nada";
    String id;
    static Context context;
    public static boolean chamou;

    String tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert("Notificações");
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        remetente = new Remetentes();
        transportador = new Transportador();

        //Inicia FireBase
        FirebaseApp.initializeApp(getApplicationContext());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();

        chamou = false;
        context = this;
        pegaTipo();

        //Verificar notificações

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_back) {
          //  alert("Notificações");
            //return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.nav_inicio){
            //Volta ao inicio
            if(retorno == "Remetente") {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new Maps_Principal_Fragemnt()).commit();
            }else{
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new Maps_Principal_Trans_Fragemnt()).commit();
            }
        }else if (id == R.id.nav_perfil) {
            //Chama Perfil
            if(retorno == "Remetente") {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new Perfil_fragment()).commit();
            }else{
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new Perfil_remetente()).commit();
            }
        } else if (id == R.id.nav_atividades) {
            //Chama Atividades
            if(retorno == "Remetente") {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new Atividades_fragment()).commit();
            }else{
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new Atividades_Transportador()).commit();
            }
        } else if (id == R.id.nav_acompanhar) {
            //Chama Acompanahr
            if(retorno == "Remetente"){
                //Chama mapa para rastrear encomenda
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new Remessas()).commit();
            } else {
                //Chama tela com prazos e proximas entregas
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new Remessas_e_Viagens()).commit();
            }

        } else if (id == R.id.nav_config) {
            //Chama Configurações
            if(retorno == "Remetente") {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new Configucarao_fragment()).commit();
            }else{
               // configurações Transportador;
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new Configucarao_fragment_transpo()).commit();

            }
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }else if (id == R.id.nav_sair) {
            FirebaseAuth.getInstance().signOut();
            finishAffinity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private static void alert(String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    public void pegaTipo(){

        //alert("PegaTipo");
            //Completa os campos
            id = user.getUid();
            //Query

            Query query1 = FirebaseDatabase.getInstance().getReference("Remetente")
                    .orderByChild("id")
                    .equalTo(id);
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Passar os dados para a interface grafica
                   // alert("Carregando os dados!");
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        remetente = obj.getValue(Remetentes.class);
                    }
                     if(remetente.getId()==null || remetente.getId().equals("")){

                         getSupportFragmentManager().beginTransaction().replace(R.id.container, new Maps_Principal_Trans_Fragemnt()).commit();

                    }else{

                         getSupportFragmentManager().beginTransaction().replace(R.id.container, new Maps_Principal_Fragemnt()).commit();
                         retorno = "Remetente";
                     }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Se ocorrer um erro
                    alert("Ocorreu um erro ao carregar informações sobre usuário!");
                    //Query
                }
            });

        //Chama Configurações

    }



    public static String getRetorno() {
        return retorno;
    }

    public static void setchamou(boolean c){
        chamou = c;
    }



    public static Transportador getTransportador(){
        return  transportador;
    }
}
