package produto

import enums.Cor
import enums.Material
import java.math.BigDecimal

class CaixaDaAgua(
    /**
     * Marca, Modelo, Dimensão(altura, largura, profundidade), Cor, Material,
     * Formato, Preço e quantidade em estoque.
     */
    val marca: String,
    val modelo: String,
    val dimensao: MutableList<Double>,
    val cor: Cor,
    val material: Material,
    val formato: String,
    val preco: BigDecimal,
    val quantidadeEstoque: Int = 0
) {
    init {
        require(preco > BigDecimal.ZERO) { "O preço deve ser positivo." }
        require(quantidadeEstoque >= 0) { "O estoque não pode ser negativo." }
    }
}
