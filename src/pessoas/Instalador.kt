package pessoas

import enums.Habilidade
import enums.Setor
import enums.Turno
import java.math.BigDecimal

class Instalador(
    nome: String,
    cpf: String,
    idade: Int,
    salario: BigDecimal = "2000".toBigDecimal(),
    val turno: Turno,
    val habilidade: Habilidade,
    setor: Setor = Setor.LOGISTICA,
    email: String? = null
) : Funcionario(nome, cpf, idade, email, salario, setor)
