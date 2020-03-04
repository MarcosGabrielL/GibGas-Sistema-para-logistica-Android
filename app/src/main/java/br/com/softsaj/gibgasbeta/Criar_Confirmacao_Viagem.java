package br.com.softsaj.gibgasbeta;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Criar_Confirmacao_Viagem extends Fragment {

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_enviar_fim, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button voltar = (Button) getView().findViewById(R.id.button18);
        Button ok = (Button) getView().findViewById(R.id.button17);
        voltar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //Abre Mapa para escolha
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Destino_Escolhe_mapa()).commit();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Criar_Viagem_Paradas()).commit();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //Abre Mapa para escolha
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new Destino_Escolhe_mapa()).commit();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Maps_Principal_Trans_Fragemnt()).commit();

            }
        });
    }
}
