package Util;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import Bean.Remessa;
import Bean.Viagem;
import br.com.softsaj.gibgasbeta.R;

public class AdapterRemessaAceite extends BaseAdapter {

    private final List<Remessa> remessas;
    private final Activity act;

    public AdapterRemessaAceite(List<Remessa> cursos, Activity Act) {
        this.remessas = cursos;
        this.act = Act;
    }

    @Override
    public int getCount() {
        return remessas.size();
    }

    @Override
    public Remessa getItem(int position) {
        return remessas.get(position);
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater()
                .inflate(R.layout.lista_remessa_em_curso, parent, false);

        Remessa remessa  = remessas.get(position);

        //pegando as referências das Views
        TextView cidadeorigem = (TextView) view.findViewById(R.id.saida_cidade);
        TextView cidadedestino = (TextView) view.findViewById(R.id.destino_cidade);
        TextView datalimite = (TextView) view.findViewById(R.id.destino_data_limite);
        TextView transporte = (TextView)  view.findViewById(R.id.destino_transporte);

        //populando as Views
        cidadeorigem.setText(remessa.getCidadeOrigem());
        cidadedestino.setText(remessa.getEntregaCidade());
        datalimite.setText(remessa.getEntregaLimiteData());
        transporte.setText(remessa.getStatus());
        try {
            if(remessa.getStatus().equals("Aceite")){
                    //Cinza
                transporte.setTextColor(Color.rgb(51, 51, 51));
            } else if(remessa.getStatus().equals("Transito")){
                    //Rosa
                transporte.setTextColor(Color.rgb(221, 46, 68));
            }else if(remessa.getStatus().equals("Cancelado")){
                    //Vermelho
                transporte.setTextColor(Color.rgb(237, 2, 2));
            }else if(remessa.getStatus().equals("Reportado")){
                   //Amarelo
                transporte.setTextColor(Color.rgb(237, 217, 2));
            }else if(remessa.getStatus().equals("Entregue")){
                   //Verde
                transporte.setTextColor(Color.rgb(38, 130, 22));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }
}
