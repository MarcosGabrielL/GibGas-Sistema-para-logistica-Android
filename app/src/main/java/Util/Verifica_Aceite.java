package Util;


import android.app.Service;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.os.IBinder;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import Bean.Remessa;
import Bean.Transportador;
import br.com.softsaj.gibgasbeta.Criar_Viagem_Lobby;
import br.com.softsaj.gibgasbeta.R;

public class Verifica_Aceite extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // Neste exemplo, iremos supor que o service será invocado apenas
        // atraves de startService()
        return null;
    }

    private class SyncDataTask extends AsyncTask<Void, Integer, Void> {
        protected Void doInBackground(Void... p) {

            return null;
        }
    }


}