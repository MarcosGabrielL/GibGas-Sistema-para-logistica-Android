package Dao;

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

import Bean.Remetentes;
import Bean.Transportador;
import br.com.softsaj.gibgasbeta.CadastroTransportador3;

public class RemetenteDAO {

    private FirebaseUser user ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Remetentes remetente;
    Transportador transportador;
    String retorno = "";
    String id;

    public String pegaTipo(){

        //Inicia FireBase
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();


        if(user == null){
            return "erro";
        }else{

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
                    //alert("Carregando os dados!");
                    for(DataSnapshot obj : dataSnapshot.getChildren()){
                        remetente = obj.getValue(Remetentes.class);
                    }

                    retorno = "Remetente";

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //Se ocorrer um erro
                   // alert("Ocorreu um erro ao carregar informações sobre usuário!");
                    //Query
                    Query query1 = FirebaseDatabase.getInstance().getReference("Transportador")
                            .orderByChild("id")
                            .equalTo(id);
                    query1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Passar os dados para a interface grafica
                            //alert("Carregando os dados!");
                            for(DataSnapshot obj : dataSnapshot.getChildren()){
                                transportador = obj.getValue(Transportador.class);
                            }

                            retorno = "Transportador";

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //Se ocorrer um erro
                            // alert("Ocorreu um erro ao carregar informações sobre usuário!");
                        }
                    });
                }
            });

            return retorno;

        }
    }

    public Remetentes pegaRemetente(){
            return remetente;
    }

    public Transportador pegaTransportador(){
            return transportador;
    }


}
