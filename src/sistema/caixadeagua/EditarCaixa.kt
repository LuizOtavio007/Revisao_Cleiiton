package sistema.caixadeagua

import enums.Cor
import enums.Material
import produto.CaixaDaAgua
import repositorio.CRUDCaixaDaAgua
import util.*

fun editarCaixa() {
    val crud = CRUDCaixaDaAgua()
    crud.listar()
    val id = lerInteiroPositivo("Digite o ID da caixa que deseja editar: ")
    val marca = lerTextoObrigatorio("Digite a nova marca: ")
    val modelo = lerTextoObrigatorio("Digite o novo modelo: ")
    val formato = lerTextoObrigatorio("Digite o novo formato: ")
    val largura = lerDecimalPositivo("Digite a nova largura: ")
    val altura = lerDecimalPositivo("Digite a nova altura: ")
    val profundidade = lerDecimalPositivo("Digite a nova profundidade: ")

    println("Escolha a nova cor:")
    Cor.entries.forEach { println("[${it.ordinal}] ${it.name}") }
    val cor = Cor.entries[lerOpcao("Número da nova cor: ", Cor.entries.lastIndex)]

    println("Escolha o novo material:")
    Material.entries.forEach { println("[${it.ordinal}] ${it.name}") }
    val material = Material.entries[lerOpcao("Número do novo material: ", Material.entries.lastIndex)]

    val preco = lerDinheiroPositivo("Digite o novo preço: ")
    val estoque = lerInteiroNaoNegativo("Digite a nova quantidade em estoque: ")

    crud.editar(
        CaixaDaAgua(marca, modelo, mutableListOf(largura, altura, profundidade), cor, material, formato, preco, estoque),
        id
    )
}
