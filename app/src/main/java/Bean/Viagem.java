package Bean;

import java.util.ArrayList;

public class Viagem {

    private String estado_origem;
    private String estado_destino;
    private String cidade_origem;
    private String cidade_destino;
    private String cidade_destino_valor;
    private String data_saida;
    private String hora_saida;
    private String data_chegada;

    private String cidade1;
    private String cidade1_data;
    private String cidade1_origem;
    private String cidade1_valor;

    private String cidade2;
    private String cidade2_data;
    private String cidade2_origem;
    private String cidade2_valor;


    private String cidade3;
    private String cidade3_data;
    private String cidade3_origem;
    private String cidade3_valor;

    private String cidade4;
    private String cidade4_data;
    private String cidade4_origem;
    private String cidade4_valor;

    private String cidade5;
    private String cidade5_data;
    private String cidade5_origem;
    private String cidade5_valor;

    private String cidade6;
    private String cidade6_data;
    private String cidade6_origem;
    private String cidade6_valor;

    private String cidade7;
    private String cidade7_data;
    private String cidade7_origem;
    private String cidade7_valor;

    private String cidade8;
    private String cidade8_data;
    private String cidade8_origem;
    private String cidade8_valor;

    private String Tipo;
    private String Retirada;
    private String descricaobike;

    private String TipoAuto;
    private String Modelo;
    private String potencia;
    private String cor;
    private String ano;
    private String placa;

    private String AviaoAeroporto;
    private String AviaoNumero;
    private String AviaoCompanhia;
    private String AviaoData;
    private String id;

    private ArrayList<Parada> paradas;

    private String Chave;

    public String getChave() {
        return Chave;
    }

    public void setChave(String chave) {
        Chave = chave;
    }

    public String getCidade1_origem() {
        return cidade1_origem;
    }

    public String getCidade_destino_valor() {
        return cidade_destino_valor;
    }

    public void setCidade_destino_valor(String cidade_destino_valor) {
        this.cidade_destino_valor = cidade_destino_valor;
    }

    public void setCidade1_origem(String cidade1_origem) {
        this.cidade1_origem = cidade1_origem;
    }

    public String getCidade1_valor() {
        return cidade1_valor;
    }

    public void setCidade1_valor(String cidade1_valor) {
        this.cidade1_valor = cidade1_valor;
    }

    public String getCidade2_origem() {
        return cidade2_origem;
    }

    public void setCidade2_origem(String cidade2_origem) {
        this.cidade2_origem = cidade2_origem;
    }

    public String getCidade2_valor() {
        return cidade2_valor;
    }

    public void setCidade2_valor(String cidade2_valor) {
        this.cidade2_valor = cidade2_valor;
    }

    public String getCidade3_origem() {
        return cidade3_origem;
    }

    public void setCidade3_origem(String cidade3_origem) {
        this.cidade3_origem = cidade3_origem;
    }

    public String getCidade3_valor() {
        return cidade3_valor;
    }

    public void setCidade3_valor(String cidade3_valor) {
        this.cidade3_valor = cidade3_valor;
    }

    public String getCidade4_origem() {
        return cidade4_origem;
    }

    public void setCidade4_origem(String cidade4_origem) {
        this.cidade4_origem = cidade4_origem;
    }

    public String getCidade4_valor() {
        return cidade4_valor;
    }

    public void setCidade4_valor(String cidade4_valor) {
        this.cidade4_valor = cidade4_valor;
    }

    public String getCidade5_origem() {
        return cidade5_origem;
    }

    public void setCidade5_origem(String cidade5_origem) {
        this.cidade5_origem = cidade5_origem;
    }

    public String getCidade5_valor() {
        return cidade5_valor;
    }

    public void setCidade5_valor(String cidade5_valor) {
        this.cidade5_valor = cidade5_valor;
    }

    public String getCidade6_origem() {
        return cidade6_origem;
    }

    public void setCidade6_origem(String cidade6_origem) {
        this.cidade6_origem = cidade6_origem;
    }

    public String getCidade6_valor() {
        return cidade6_valor;
    }

    public void setCidade6_valor(String cidade6_valor) {
        this.cidade6_valor = cidade6_valor;
    }

    public String getCidade7_origem() {
        return cidade7_origem;
    }

    public void setCidade7_origem(String cidade7_origem) {
        this.cidade7_origem = cidade7_origem;
    }

    public String getCidade7_valor() {
        return cidade7_valor;
    }

    public void setCidade7_valor(String cidade7_valor) {
        this.cidade7_valor = cidade7_valor;
    }

    public String getCidade8_origem() {
        return cidade8_origem;
    }

    public void setCidade8_origem(String cidade8_origem) {
        this.cidade8_origem = cidade8_origem;
    }

    public String getCidade8_valor() {
        return cidade8_valor;
    }

    public void setCidade8_valor(String cidade8_valor) {
        this.cidade8_valor = cidade8_valor;
    }

    public ArrayList<Parada> getParadas() {
        return paradas;
    }

    public void setParadas(ArrayList<Parada> paradas) {
        this.paradas = paradas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAviaoAeroporto() {
        return AviaoAeroporto;
    }

    public void setAviaoAeroporto(String aviaoAeroporto) {
        AviaoAeroporto = aviaoAeroporto;
    }

    public String getAviaoNumero() {
        return AviaoNumero;
    }

    public void setAviaoNumero(String aviaoNumero) {
        AviaoNumero = aviaoNumero;
    }

    public String getAviaoCompanhia() {
        return AviaoCompanhia;
    }

    public void setAviaoCompanhia(String aviaoCompanhia) {
        AviaoCompanhia = aviaoCompanhia;
    }

    public String getAviaoData() {
        return AviaoData;
    }

    public void setAviaoData(String aviaoData) {
        AviaoData = aviaoData;
    }

    public String getTipoAuto() {
        return TipoAuto;
    }

    public void setTipoAuto(String tipoAuto) {
        TipoAuto = tipoAuto;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String modelo) {
        Modelo = modelo;
    }

    public String getPotencia() {
        return potencia;
    }

    public void setPotencia(String potencia) {
        this.potencia = potencia;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getDescricaobike() {
        return descricaobike;
    }

    public void setDescricaobike(String descricaobike) {
        this.descricaobike = descricaobike;
    }

    public String getRetirada() {
        return Retirada;
    }

    public void setRetirada(String retirada) {
        Retirada = retirada;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public String getCidade1() {
        return cidade1;
    }

    public void setCidade1(String cidade1) {
        this.cidade1 = cidade1;
    }

    public String getCidade1_data() {
        return cidade1_data;
    }

    public void setCidade1_data(String cidade1_data) {
        this.cidade1_data = cidade1_data;
    }

    public String getCidade2() {
        return cidade2;
    }

    public void setCidade2(String cidade2) {
        this.cidade2 = cidade2;
    }

    public String getCidade2_data() {
        return cidade2_data;
    }

    public void setCidade2_data(String cidade2_data) {
        this.cidade2_data = cidade2_data;
    }

    public String getCidade3() {
        return cidade3;
    }

    public void setCidade3(String cidade3) {
        this.cidade3 = cidade3;
    }

    public String getCidade3_data() {
        return cidade3_data;
    }

    public void setCidade3_data(String cidade3_data) {
        this.cidade3_data = cidade3_data;
    }

    public String getCidade4() {
        return cidade4;
    }

    public void setCidade4(String cidade4) {
        this.cidade4 = cidade4;
    }

    public String getCidade4_data() {
        return cidade4_data;
    }

    public void setCidade4_data(String cidade4_data) {
        this.cidade4_data = cidade4_data;
    }

    public String getCidade5() {
        return cidade5;
    }

    public void setCidade5(String cidade5) {
        this.cidade5 = cidade5;
    }

    public String getCidade5_data() {
        return cidade5_data;
    }

    public void setCidade5_data(String cidade5_data) {
        this.cidade5_data = cidade5_data;
    }

    public String getCidade6() {
        return cidade6;
    }

    public void setCidade6(String cidade6) {
        this.cidade6 = cidade6;
    }

    public String getCidade6_data() {
        return cidade6_data;
    }

    public void setCidade6_data(String cidade6_data) {
        this.cidade6_data = cidade6_data;
    }

    public String getCidade7() {
        return cidade7;
    }

    public void setCidade7(String cidade7) {
        this.cidade7 = cidade7;
    }

    public String getCidade7_data() {
        return cidade7_data;
    }

    public void setCidade7_data(String cidade7_data) {
        this.cidade7_data = cidade7_data;
    }

    public String getCidade8() {
        return cidade8;
    }

    public void setCidade8(String cidade8) {
        this.cidade8 = cidade8;
    }

    public String getCidade8_data() {
        return cidade8_data;
    }

    public void setCidade8_data(String cidade8_data) {
        this.cidade8_data = cidade8_data;
    }

    public String getEstado_origem() {
        return estado_origem;
    }

    public void setEstado_origem(String estado_origem) {
        this.estado_origem = estado_origem;
    }

    public String getEstado_destino() {
        return estado_destino;
    }

    public void setEstado_destino(String estado_destino) {
        this.estado_destino = estado_destino;
    }

    public String getCidade_origem() {
        return cidade_origem;
    }

    public void setCidade_origem(String cidade_origem) {
        this.cidade_origem = cidade_origem;
    }

    public String getCidade_destino() {
        return cidade_destino;
    }

    public void setCidade_destino(String cidade_destino) {
        this.cidade_destino = cidade_destino;
    }

    public String getData_saida() {
        return data_saida;
    }

    public void setData_saida(String data_saida) {
        this.data_saida = data_saida;
    }

    public String getHora_saida() {
        return hora_saida;
    }

    public void setHora_saida(String hora_saida) {
        this.hora_saida = hora_saida;
    }

    public String getData_chegada() {
        return data_chegada;
    }

    public void setData_chegada(String data_chegada) {
        this.data_chegada = data_chegada;
    }
}
