package enums

enum class Contexto(val descricao: String) {
    VENDA_CAIXA_DAGUA("Venda de caixa-d'água"),
    INSTALACAO("Serviço de instalação"),
    COMPRA_ESTOQUE("Compra para o estoque"),
    PAGAMENTO_SALARIO("Pagamento de salário"),
    MANUTENCAO("Serviço de manutenção");

    override fun toString(): String {
        return descricao
    }
}