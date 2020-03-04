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

public class AdapterViagensPersonalizado extends BaseAdapter {

    private final List<Viagem> viagens;
    private final Activity act;
    private final Remessa remessa;

    public AdapterViagensPersonalizado(List<Viagem> cursos, Activity Act, Remessa r) {
        this.viagens = cursos;
        this.act = Act;
        this.remessa = r;
    }

    @Override
    public int getCount() {
        return viagens.size();
    }

    @Override
    public Viagem getItem(int position) {
        return viagens.get(position);
    }

    @Override
    public long getItemId(int position) {
        Viagem vi = new Viagem();
        vi = viagens.get(position);
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater()
                .inflate(R.layout.lista_de_viagens, parent, false);

        Viagem viagem  = viagens.get(position);

        //pegando as referências das Views
        TextView nome = (TextView)
                view.findViewById(R.id.lista_curso_personalizada_nome);
        TextView descricao = (TextView)
                view.findViewById(R.id.lista_curso_personalizada_descricao);
        ImageView imagem = (ImageView)
                view.findViewById(R.id.lista_curso_personalizada_imagem);
        ImageView retirada = (ImageView)
                view.findViewById(R.id.lista_curso_personalizada_retirada);
        TextView valor = (TextView)
                view.findViewById(R.id.textView58);

        //populando as Views
        try {
            if(checkSimilarity(viagem.getCidade1(),remessa.getEntregaCidade())>=0.5){
                    descricao.setText("Data Chegada" + viagem.getCidade1_data());
                        valor.setText("R$: "+viagem.getCidade1_valor());
            } else if(checkSimilarity(viagem.getCidade2(),remessa.getEntregaCidade())>=0.5){
                    descricao.setText("Data Chegada" + viagem.getCidade2_data());
                        valor.setText("R$: "+viagem.getCidade2_valor());
            }else if(checkSimilarity(viagem.getCidade3(),remessa.getEntregaCidade())>=0.5){
                    descricao.setText("Data Chegada" + viagem.getCidade3_data());
                        valor.setText("R$: "+viagem.getCidade3_valor());
            }else if(checkSimilarity(viagem.getCidade4(),remessa.getEntregaCidade())>=0.5){
                    descricao.setText("Data Chegada" + viagem.getCidade4_data());
                        valor.setText("R$: "+viagem.getCidade4_valor());
            }else if(checkSimilarity(viagem.getCidade5(),remessa.getEntregaCidade())>=0.5){
                descricao.setText("Data Chegada" + viagem.getCidade5_data());
                valor.setText("R$: "+viagem.getCidade5_valor());
            }else if(checkSimilarity(viagem.getCidade6(),remessa.getEntregaCidade())>=0.5){
                descricao.setText("Data Chegada" + viagem.getCidade6_data());
                valor.setText("R$: "+viagem.getCidade6_valor());
            }else if(checkSimilarity(viagem.getCidade7(),remessa.getEntregaCidade())>=0.5){
                descricao.setText("Data Chegada" + viagem.getCidade7_data());
                valor.setText("R$: "+viagem.getCidade7_valor());
            }else if(checkSimilarity(viagem.getCidade8(),remessa.getEntregaCidade())>=0.5){
                descricao.setText("Data Chegada" + viagem.getCidade8_data());
                valor.setText("R$: "+viagem.getCidade8_valor());
            }else if(checkSimilarity(viagem.getCidade_destino(),remessa.getEntregaCidade())>=0.5){
                    descricao.setText("Data Chegada" + viagem.getData_chegada());
                        valor.setText("R$: "+viagem.getCidade_destino_valor());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        nome.setText("Data de Saida: " + viagem.getData_saida());


        if (viagem.getTipo().equals("Avião")) {
            imagem.setImageResource(R.drawable.airplane);
        } else if (viagem.getTipo().equals("Bike")) {
            imagem.setImageResource(R.drawable.bicycle);
        } else if (viagem.getTipo().equals("Caminhão")) {
            imagem.setImageResource(R.drawable.truck);
        }else if (viagem.getTipo().equals("Carro")) {
            imagem.setImageResource(R.drawable.car);
        }else if (viagem.getTipo().equals("Motocicleta")) {
            imagem.setImageResource(R.drawable.motorbike);
        }else if (viagem.getTipo().equals("Pé")) {
            imagem.setImageResource(R.drawable.walker);
        }else if (viagem.getTipo().equals("Transporte Público")) {
            imagem.setImageResource(R.drawable.bus);
        }

        if(viagem.getRetirada().equals("Vou até você")){
            retirada.setBackgroundColor(Color.rgb(0, 153, 51));
        } else if(viagem.getRetirada().equals("Vem até mim")){
            retirada.setBackgroundColor(Color.rgb(221, 46, 68));
        } else if(viagem.getRetirada().equals("Vamos negociar")){
            retirada.setBackgroundColor(Color.rgb(0, 102, 255));
        }

        return view;
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
}
