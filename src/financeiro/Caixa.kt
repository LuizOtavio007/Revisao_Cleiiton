package financeiro

import java.math.BigDecimal

class Caixa(
    val saldo: BigDecimal //NÂO POSSO MEXER VIA CODIGO
    //SOMENTE NO BANCO
) {
    fun receita(valor: BigDecimal) : BigDecimal {
        return valor

    }

    fun despesa(valor: BigDecimal) : BigDecimal {
        return valor.multiply("-1".toBigDecimal())

    }
}
