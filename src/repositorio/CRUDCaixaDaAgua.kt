package repositorio

import produto.CaixaDaAgua
import java.sql.SQLException

class CRUDCaixaDaAgua : InterfaceJPA<CaixaDaAgua>, ConexaoPostgres() {
    override fun salvar(item: CaixaDaAgua) {
        try {
            abrirConexao().use { conexao ->
                val sql = """
                    INSERT INTO caixa_da_agua
                    (marca, modelo, dimensao, cor, material, formato, preco, quantidade_estoque)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """.trimIndent()
                conexao.prepareStatement(sql).use { stmt ->
                    val dimensoes = conexao.createArrayOf("float8", item.dimensao.toTypedArray())
                    stmt.setString(1, item.marca)
                    stmt.setString(2, item.modelo)
                    stmt.setArray(3, dimensoes)
                    stmt.setString(4, item.cor.name)
                    stmt.setString(5, item.material.name)
                    stmt.setString(6, item.formato)
                    stmt.setBigDecimal(7, item.preco)
                    stmt.setInt(8, item.quantidadeEstoque)
                    stmt.executeUpdate()
                }
            }
            println("Caixa-d'água salva com sucesso!")
        } catch (e: SQLException) {
            println("Erro ao salvar caixa-d'água: ${e.message}")
        }
    }

    override fun listar() {
        try {
            abrirConexao().use { conexao ->
                conexao.createStatement().use { stmt ->
                    stmt.executeQuery("SELECT * FROM caixa_da_agua ORDER BY id").use { rs ->
                        while (rs.next()) {
                            println("ID: ${rs.getInt("id")} | ${rs.getString("marca")} ${rs.getString("modelo")} | Preço: ${rs.getBigDecimal("preco")} | Estoque: ${rs.getInt("quantidade_estoque")}")
                        }
                    }
                }
            }
        } catch (e: SQLException) {
            println("Erro ao listar caixas-d'água: ${e.message}")
        }
    }

    override fun editar(item: CaixaDaAgua, id: Int) {
        try {
            abrirConexao().use { conexao ->
                val sql = """
                    UPDATE caixa_da_agua SET
                    preco = ?, marca = ?, modelo = ?, formato = ?, dimensao = ?, cor = ?, material = ?, quantidade_estoque = ?
                    WHERE id = ?
                """.trimIndent()
                conexao.prepareStatement(sql).use { stmt ->
                    stmt.setBigDecimal(1, item.preco)
                    stmt.setString(2, item.marca)
                    stmt.setString(3, item.modelo)
                    stmt.setString(4, item.formato)
                    stmt.setArray(5, conexao.createArrayOf("float8", item.dimensao.toTypedArray()))
                    stmt.setString(6, item.cor.name)
                    stmt.setString(7, item.material.name)
                    stmt.setInt(8, item.quantidadeEstoque)
                    stmt.setInt(9, id)
                    val linhas = stmt.executeUpdate()
                    println(if (linhas > 0) "Caixa-d'água atualizada com sucesso!" else "ID não encontrado.")
                }
            }
        } catch (e: SQLException) {
            println("Erro ao editar caixa-d'água: ${e.message}")
        }
    }

    override fun excluir(id: Int) {
        try {
            abrirConexao().use { conexao ->
                conexao.prepareStatement("DELETE FROM caixa_da_agua WHERE id = ?").use { stmt ->
                    stmt.setInt(1, id)
                    val linhas = stmt.executeUpdate()
                    println(if (linhas > 0) "Caixa-d'água excluída." else "ID não encontrado.")
                }
            }
        } catch (e: SQLException) {
            println("Erro ao excluir caixa-d'água: ${e.message}")
        }
    }
}
