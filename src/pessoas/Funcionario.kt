package pessoas

import enums.Setor
import java.math.BigDecimal

open class Funcionario(
    nome: String,
    cpf: String,
    idade: Int,
    email: String? = null,
    val salario: BigDecimal,
    var setor: Setor
) : Pessoa(nome, cpf, idade, email) {
    init {
        require(salario >= BigDecimal.ZERO) { "O salário não pode ser negativo." }
    }
}
