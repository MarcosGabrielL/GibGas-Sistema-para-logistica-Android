package Bean;

public class ContaBancaria {

    String banco;
    String conta;
    String agencia;
    String tipo;
    String tituar;
    String cpftitular;
    String idUser;

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTituar() {
        return tituar;
    }

    public void setTituar(String tituar) {
        this.tituar = tituar;
    }

    public String getCpftitular() {
        return cpftitular;
    }

    public void setCpftitular(String cpftitular) {
        this.cpftitular = cpftitular;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
