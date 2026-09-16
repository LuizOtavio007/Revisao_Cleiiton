package sistema

import enums.Setor
import pessoas.Cliente
import pessoas.Fornecedor
import pessoas.Funcionario
import repositorio.CRUDCaixaDaAgua
import repositorio.CRUDMovimentacao
import repositorio.CRUDPessoa
import servico.VendaService
import sistema.caixadeagua.cadastrarNovaCaixa
import sistema.caixadeagua.editarCaixa
import sistema.caixadeagua.excluirCaixa
import sistema.caixadeagua.listarCaixa
import sistema.pagamento.pagar
import util.*

fun menuInicial() {
    while (true) {
        println("\n===== SISTEMA CAIXA D'ÁGUA =====")
        println("[1] Produtos / Estoque")
        println("[2] Pessoas")
        println("[3] Registrar venda")
        println("[4] Movimentações financeiras")
        println("[5] Registrar movimentação manual")
        println("[0] Sair")

        when (lerOpcao("Opção: ", 5)) {
            1 -> menuProdutos()
            2 -> menuPessoas()
            3 -> registrarVendaMenu()
            4 -> CRUDMovimentacao().listar()
            5 -> pagar()
            0 -> return
        }
    }
}

private fun menuProdutos() {
    println("\n[1] Cadastrar")
    println("[2] Listar")
    println("[3] Editar")
    println("[4] Excluir")
    println("[0] Voltar")
    when (lerOpcao("Opção: ", 4)) {
        1 -> cadastrarNovaCaixa()
        2 -> listarCaixa()
        3 -> editarCaixa()
        4 -> excluirCaixa()
    }
}

private fun menuPessoas() {
    println("\n[1] Cadastrar cliente")
    println("[2] Cadastrar fornecedor")
    println("[3] Cadastrar funcionário")
    println("[4] Listar pessoas")
    println("[0] Voltar")

    val crud = CRUDPessoa()
    when (lerOpcao("Opção: ", 4)) {
        1 -> {
            val nome = lerTextoObrigatorio("Nome: ")
            val cpf = lerCpf("CPF (000.000.000-00): ")
            val idade = lerInteiroPositivo("Idade: ")
            val email = lerEmailOpcional("E-mail (Enter para deixar vazio): ")
            crud.salvar(Cliente(nome, cpf, idade, false, mutableListOf(), email))
        }
        2 -> {
            val nome = lerTextoObrigatorio("Nome: ")
            val cpf = lerCpf("CPF (000.000.000-00): ")
            val idade = lerInteiroPositivo("Idade: ")
            val email = lerEmailOpcional("E-mail (Enter para deixar vazio): ")
            val empresa = lerTextoObrigatorio("Empresa: ")
            crud.salvar(Fornecedor(nome, cpf, idade, email, empresa))
        }
        3 -> {
            val nome = lerTextoObrigatorio("Nome: ")
            val cpf = lerCpf("CPF (000.000.000-00): ")
            val idade = lerInteiroPositivo("Idade: ")
            val email = lerEmailOpcional("E-mail (Enter para deixar vazio): ")
            val salario = lerDinheiroPositivo("Salário: ")
            println("Setor:")
            Setor.entries.forEach { println("[${it.ordinal}] ${it.name}") }
            val setor = Setor.entries[lerOpcao("Opção: ", Setor.entries.lastIndex)]
            crud.salvar(Funcionario(nome, cpf, idade, email, salario, setor))
        }
        4 -> crud.listarTodos()
    }
}

private fun registrarVendaMenu() {
    val produtoCrud = CRUDCaixaDaAgua()
    println("Produtos disponíveis:")
    produtoCrud.listar()
    val produtoId = lerInteiroPositivo("ID do produto: ")
    val quantidade = lerInteiroPositivo("Quantidade: ")
    val pagador = lerTextoObrigatorio("Pagador/cliente: ")
    val recebedor = lerTextoObrigatorio("Recebedor: ")
    println("Funcionários disponíveis:")
    CRUDPessoa().listarFuncionarios()
    val responsavelId = lerInteiroPositivo("ID do responsável: ")

    VendaService().registrarVenda(produtoId, quantidade, pagador, recebedor, responsavelId)
}
