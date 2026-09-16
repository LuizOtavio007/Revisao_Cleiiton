package sistema.pagamento

import enums.Contexto
import financeiro.Movimentacao
import repositorio.CRUDMovimentacao
import repositorio.CRUDPessoa
import util.lerDinheiroPositivo
import util.lerInteiroPositivo
import util.lerOpcao
import util.lerTextoObrigatorio
import java.time.LocalDateTime

fun escolherContexto(): Contexto {
    println("Escolha o motivo da movimentação:")
    Contexto.entries.forEachIndexed { indice, contexto ->
        println("[$indice] ${contexto.descricao}")
    }
    return Contexto.entries[lerOpcao("Digite uma opção: ", Contexto.entries.lastIndex)]
}

fun pagar() {
    val contexto = escolherContexto()
    val valor = lerDinheiroPositivo("Digite o valor: ")
    val pagador = lerTextoObrigatorio("Quem pagou? ")
    val recebedor = lerTextoObrigatorio("Quem recebeu? ")

    println("Funcionários disponíveis para responsável:")
    CRUDPessoa().listarFuncionarios()
    val responsavelId = lerInteiroPositivo("ID do responsável: ")

    val movimentacao = Movimentacao(
        contexto = contexto,
        valor = valor,
        dataHora = LocalDateTime.now(),
        pagador = pagador,
        recebedor = recebedor,
        responsavelId = responsavelId
    )

    CRUDMovimentacao().salvar(movimentacao)
    println("Movimentação registrada com sucesso!")
}
