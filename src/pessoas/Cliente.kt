package pessoas

import java.math.BigDecimal

class Cliente(
    nomeCliente: String,
    cpfCliente: String,
    idadeCliente: Int,
    var dividasAbertas: Boolean,
    var parcelasAPagar: MutableList<Double>,
    emailCliente: String? = null
) : Pessoa(
    nome = nomeCliente,
    cpf = cpfCliente,
    idade = idadeCliente,
    email = emailCliente
) {
    fun receberConta(dinheiro: BigDecimal = BigDecimal.ZERO): BigDecimal {
        return dinheiro
    }
}
