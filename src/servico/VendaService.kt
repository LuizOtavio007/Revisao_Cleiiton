package servico

import enums.Contexto
import repositorio.ConexaoPostgres
import java.math.BigDecimal
import java.sql.SQLException
import java.sql.Timestamp
import java.time.LocalDateTime

class VendaService : ConexaoPostgres() {
    fun registrarVenda(
        produtoId: Int,
        quantidade: Int,
        pagador: String,
        recebedor: String,
        responsavelId: Int
    ) {
        require(produtoId > 0)
        require(quantidade > 0)
        require(pagador.isNotBlank())
        require(recebedor.isNotBlank())
        require(responsavelId > 0)

        val conexao = abrirConexao()
        try {
            conexao.autoCommit = false

            val consulta = conexao.prepareStatement(
                "SELECT preco, quantidade_estoque FROM caixa_da_agua WHERE id = ? FOR UPDATE"
            )
            consulta.use { stmt ->
                stmt.setInt(1, produtoId)
                stmt.executeQuery().use { rs ->
                    if (!rs.next()) throw IllegalArgumentException("Produto não encontrado.")
                    val preco = rs.getBigDecimal("preco")
                    val estoque = rs.getInt("quantidade_estoque")
                    if (estoque < quantidade) throw IllegalArgumentException("Estoque insuficiente.")

                    val total = preco.multiply(BigDecimal.valueOf(quantidade.toLong()))
                    val agora = LocalDateTime.now()

                    conexao.prepareStatement(
                        "INSERT INTO venda (data_hora, pagador, recebedor, responsavel_id, valor_total) VALUES (?, ?, ?, ?, ?)",
                        java.sql.Statement.RETURN_GENERATED_KEYS
                    ).use { vendaStmt ->
                        vendaStmt.setTimestamp(1, Timestamp.valueOf(agora))
                        vendaStmt.setString(2, pagador)
                        vendaStmt.setString(3, recebedor)
                        vendaStmt.setInt(4, responsavelId)
                        vendaStmt.setBigDecimal(5, total)
                        vendaStmt.executeUpdate()

                        vendaStmt.generatedKeys.use { keys ->
                            if (!keys.next()) throw SQLException("Não foi possível obter o ID da venda.")
                            val vendaId = keys.getInt(1)

                            conexao.prepareStatement(
                                "INSERT INTO item_venda (venda_id, produto_id, quantidade, valor_unitario) VALUES (?, ?, ?, ?)"
                            ).use { itemStmt ->
                                itemStmt.setInt(1, vendaId)
                                itemStmt.setInt(2, produtoId)
                                itemStmt.setInt(3, quantidade)
                                itemStmt.setBigDecimal(4, preco)
                                itemStmt.executeUpdate()
                            }
                        }
                    }

                    conexao.prepareStatement(
                        "UPDATE caixa_da_agua SET quantidade_estoque = quantidade_estoque - ? WHERE id = ?"
                    ).use { estoqueStmt ->
                        estoqueStmt.setInt(1, quantidade)
                        estoqueStmt.setInt(2, produtoId)
                        estoqueStmt.executeUpdate()
                    }

                    // A movimentação financeira é criada automaticamente dentro da mesma transação.
                    conexao.prepareStatement(
                        """
                        INSERT INTO movimentacao
                        (valor, data_hora, descricao, pagador, recebedor, responsavel_id)
                        VALUES (?, ?, ?, ?, ?, ?)
                        """.trimIndent()
                    ).use { movStmt ->
                        movStmt.setBigDecimal(1, total)
                        movStmt.setTimestamp(2, Timestamp.valueOf(agora))
                        movStmt.setString(3, Contexto.VENDA_CAIXA_DAGUA.name)
                        movStmt.setString(4, pagador)
                        movStmt.setString(5, recebedor)
                        movStmt.setInt(6, responsavelId)
                        movStmt.executeUpdate()
                    }
                }
            }

            conexao.commit()
            println("Venda registrada com sucesso.")
        } catch (e: Exception) {
            try {
                conexao.rollback()
            } catch (_: SQLException) {
            }
            println("Venda cancelada: ${e.message}")
        } finally {
            try {
                conexao.autoCommit = true
                conexao.close()
            } catch (_: SQLException) {
            }
        }
    }
}
