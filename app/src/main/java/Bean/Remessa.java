package Bean;

public class Remessa {

    private String IdEntrega;
    private String Data_inicial_entrega;
    private String Hora_nincial_entrega;
    private String EntregaLimiteData;

    private String TipoTransporte;
    private String ModeloTransporte;
    private String CorTransporte;
    private String AnoTransporte;
    private String placaTransporte;

    private String IdRemetente;
    private String EmailRemetente;

    private String IdEntregador;
    private String EmailEntregador;

    private String CidadeOrigem;

    private String EntregaRua;
    private String EntregaNumeroCasa;
    private String EntregaBairro;
    private String EntregaEstado;
    private String EntregaCep;
    private String EntregaCidade;
    private String EntregaComplemento;


    private String PacoteAltura;
    private String PacoteLargura;
    private String PacoteComprimento;
    private String PacoteDescriçãosimples;
    private String PacoteCuidadostransporte;
    private String PacoteDescriçãocompleta  ;
    private String PacoteValor;
    private String PacotePeso;

    private String status;

    private boolean chama;
    private String idChamada;
    private boolean Aceitou;

    private String TipoPagamento;
    private String Ultimos4numerosCartão;
    private String ValorViagem;
    private String ValorMaximo;

    private String Chave;

    private String Motivo_Analise;
    private String Data_Inicio_Analise;

    public String getMotivo_Analise() {
        return Motivo_Analise;
    }

    public void setMotivo_Analise(String motivo_Analise) {
        Motivo_Analise = motivo_Analise;
    }

    public String getData_Inicio_Analise() {
        return Data_Inicio_Analise;
    }

    public void setData_Inicio_Analise(String data_Inicio_Analise) {
        Data_Inicio_Analise = data_Inicio_Analise;
    }

    public String getChave() {
        return Chave;
    }

    public void setChave(String chave) {
        Chave = chave;
    }

    public String getValorMaximo() {
        return ValorMaximo;
    }

    public void setValorMaximo(String valorMaximo) {
        ValorMaximo = valorMaximo;
    }

    public String getValorViagem() {
        return ValorViagem;
    }

    public void setValorViagem(String valorViagem) {
        ValorViagem = valorViagem;
    }

    public String getTipoPagamento() {
        return TipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        TipoPagamento = tipoPagamento;
    }

    public String getUltimos4numerosCartão() {
        return Ultimos4numerosCartão;
    }

    public void setUltimos4numerosCartão(String ultimos4numerosCartão) {
        Ultimos4numerosCartão = ultimos4numerosCartão;
    }

    public String getTipoTransporte() {
        return TipoTransporte;
    }

    public void setTipoTransporte(String tipoTransporte) {
        TipoTransporte = tipoTransporte;
    }

    public String getModeloTransporte() {
        return ModeloTransporte;
    }

    public void setModeloTransporte(String modeloTransporte) {
        ModeloTransporte = modeloTransporte;
    }

    public String getCorTransporte() {
        return CorTransporte;
    }

    public void setCorTransporte(String corTransporte) {
        CorTransporte = corTransporte;
    }

    public String getAnoTransporte() {
        return AnoTransporte;
    }

    public void setAnoTransporte(String anoTransporte) {
        AnoTransporte = anoTransporte;
    }

    public String getPlacaTransporte() {
        return placaTransporte;
    }

    public void setPlacaTransporte(String placaTransporte) {
        this.placaTransporte = placaTransporte;
    }

    public String getCidadeOrigem() {
        return CidadeOrigem;
    }

    public void setCidadeOrigem(String cidadeOrigem) {
        CidadeOrigem = cidadeOrigem;
    }

    public boolean isAceitou() {
        return Aceitou;
    }

    public void setAceitou(boolean aceitou) {
        Aceitou = aceitou;
    }

    public boolean isChama() {
        return chama;
    }

    public void setChama(boolean chama) {
        this.chama = chama;
    }

    public String getIdChamada() {
        return idChamada;
    }

    public void setIdChamada(String idChamada) {
        this.idChamada = idChamada;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEntregaLimiteData() {
        return EntregaLimiteData;
    }

    public void setEntregaLimiteData(String entregaLimiteData) {
        EntregaLimiteData = entregaLimiteData;
    }

    public String getPacoteAltura() {
        return PacoteAltura;
    }

    public void setPacoteAltura(String pacoteAltura) {
        PacoteAltura = pacoteAltura;
    }

    public String getPacoteLargura() {
        return PacoteLargura;
    }

    public void setPacoteLargura(String pacoteLargura) {
        PacoteLargura = pacoteLargura;
    }

    public String getPacoteComprimento() {
        return PacoteComprimento;
    }

    public void setPacoteComprimento(String pacoteComprimento) {
        PacoteComprimento = pacoteComprimento;
    }

    public String getPacoteDescriçãosimples() {
        return PacoteDescriçãosimples;
    }

    public void setPacoteDescriçãosimples(String pacoteDescriçãosimples) {
        PacoteDescriçãosimples = pacoteDescriçãosimples;
    }

    public String getPacoteCuidadostransporte() {
        return PacoteCuidadostransporte;
    }

    public void setPacoteCuidadostransporte(String pacoteCuidadostransporte) {
        PacoteCuidadostransporte = pacoteCuidadostransporte;
    }

    public String getPacoteDescriçãocompleta() {
        return PacoteDescriçãocompleta;
    }

    public void setPacoteDescriçãocompleta(String pacoteDescriçãocompleta) {
        PacoteDescriçãocompleta = pacoteDescriçãocompleta;
    }

    public String getPacoteValor() {
        return PacoteValor;
    }

    public void setPacoteValor(String pacoteValor) {
        PacoteValor = pacoteValor;
    }

    public String getPacotePeso() {
        return PacotePeso;
    }

    public void setPacotePeso(String pacotePeso) {
        PacotePeso = pacotePeso;
    }

    public String getIdEntrega() {
        return IdEntrega;
    }

    public void setIdEntrega(String idEntrega) {
        IdEntrega = idEntrega;
    }

    public String getData_inicial_entrega() {
        return Data_inicial_entrega;
    }

    public void setData_inicial_entrega(String data_inicial_entrega) {
        Data_inicial_entrega = data_inicial_entrega;
    }

    public String getHora_nincial_entrega() {
        return Hora_nincial_entrega;
    }

    public void setHora_nincial_entrega(String hora_nincial_entrega) {
        Hora_nincial_entrega = hora_nincial_entrega;
    }

    public String getIdRemetente() {
        return IdRemetente;
    }

    public void setIdRemetente(String idRemetente) {
        IdRemetente = idRemetente;
    }

    public String getEmailRemetente() {
        return EmailRemetente;
    }

    public void setEmailRemetente(String emailRemetente) {
        EmailRemetente = emailRemetente;
    }

    public String getIdEntregador() {
        return IdEntregador;
    }

    public void setIdEntregador(String idEntregador) {
        IdEntregador = idEntregador;
    }

    public String getEmailEntregador() {
        return EmailEntregador;
    }

    public void setEmailEntregador(String emailEntregador) {
        EmailEntregador = emailEntregador;
    }

    public String getEntregaRua() {
        return EntregaRua;
    }

    public void setEntregaRua(String entregaRua) {
        EntregaRua = entregaRua;
    }

    public String getEntregaNumeroCasa() {
        return EntregaNumeroCasa;
    }

    public void setEntregaNumeroCasa(String entregaNumeroCasa) {
        EntregaNumeroCasa = entregaNumeroCasa;
    }

    public String getEntregaBairro() {
        return EntregaBairro;
    }

    public void setEntregaBairro(String entregaBairro) {
        EntregaBairro = entregaBairro;
    }

    public String getEntregaEstado() {
        return EntregaEstado;
    }

    public void setEntregaEstado(String entregaEstado) {
        EntregaEstado = entregaEstado;
    }

    public String getEntregaCep() {
        return EntregaCep;
    }

    public void setEntregaCep(String entregaCep) {
        EntregaCep = entregaCep;
    }

    public String getEntregaCidade() {
        return EntregaCidade;
    }

    public void setEntregaCidade(String entregaCidade) {
        EntregaCidade = entregaCidade;
    }

    public String getEntregaComplemento() {
        return EntregaComplemento;
    }

    public void setEntregaComplemento(String entregaComplemento) {
        EntregaComplemento = entregaComplemento;
    }
}
