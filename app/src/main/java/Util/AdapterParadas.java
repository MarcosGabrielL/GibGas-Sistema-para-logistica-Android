package Util;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import Bean.Parada;
import Bean.Remessa;
import Bean.Viagem;
import br.com.softsaj.gibgasbeta.R;

public class AdapterParadas extends BaseAdapter {

    private final List<Parada> paradas;
    private final Activity act;

    public AdapterParadas(List<Parada> cursos, Activity Act) {
        this.paradas = cursos;
        this.act = Act;
    }

    @Override
    public int getCount() {
        return paradas.size();
    }

    @Override
    public Parada getItem(int position) {
        return paradas.get(position);
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater()
                .inflate(R.layout.lista_de_paradas, parent, false);

        Parada parada  = paradas.get(position);

        //pegando as referências das Views
        TextView origem = (TextView)
                view.findViewById(R.id.textView51);
        TextView destino = (TextView)
                view.findViewById(R.id.textView53);
        TextView datas = (TextView)
                view.findViewById(R.id.textView77);
        TextView valor = (TextView)
                view.findViewById(R.id.textView79);

        //populando as Views
        origem.setText(parada.getOrigem());
        destino.setText(parada.getDestino());
        datas.setText(parada.getDatachegada());
        valor.setText(parada.getValor());

        return view;
    }
}
