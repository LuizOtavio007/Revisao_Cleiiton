package sistema.caixadeagua

import enums.Cor
import enums.Material
import produto.CaixaDaAgua
import repositorio.CRUDCaixaDaAgua
import util.*

fun cadastrarNovaCaixa() {
    val marca = lerTextoObrigatorio("Digite a marca: ")
    val modelo = lerTextoObrigatorio("Digite o modelo: ")
    val largura = lerDecimalPositivo("Digite a largura: ")
    val altura = lerDecimalPositivo("Digite a altura: ")
    val profundidade = lerDecimalPositivo("Digite a profundidade: ")
    val dimensao = mutableListOf(largura, altura, profundidade)

    println("Escolha a cor:")
    Cor.entries.forEach { cor -> println("[${cor.ordinal}] ${cor.name}") }
    val cor = Cor.entries[lerOpcao("Número da cor: ", Cor.entries.lastIndex)]

    println("Escolha o material:")
    Material.entries.forEach { material -> println("[${material.ordinal}] ${material.name}") }
    val material = Material.entries[lerOpcao("Número do material: ", Material.entries.lastIndex)]

    val formato = lerTextoObrigatorio("Digite o formato: ")
    val preco = lerDinheiroPositivo("Digite o preço: ")
    val estoque = lerInteiroNaoNegativo("Quantidade inicial em estoque: ")

    CRUDCaixaDaAgua().salvar(
        CaixaDaAgua(marca, modelo, dimensao, cor, material, formato, preco, estoque)
    )
}
