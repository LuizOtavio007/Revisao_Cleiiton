package financeiro

import enums.Contexto
import java.math.BigDecimal
import java.time.LocalDateTime

class Movimentacao(
    val valor: BigDecimal,
    val dataHora: LocalDateTime,
    val contexto: Contexto,
    val pagador: String,
    val recebedor: String,
    val responsavelId: Int
) {
    init {
        require(valor > BigDecimal.ZERO) { "O valor da movimentação deve ser positivo." }
        require(pagador.isNotBlank()) { "O pagador é obrigatório." }
        require(recebedor.isNotBlank()) { "O recebedor é obrigatório." }
        require(responsavelId > 0) { "O responsável é obrigatório." }
    }
}
