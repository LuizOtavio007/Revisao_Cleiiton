package repositorio

import financeiro.Movimentacao
import java.sql.SQLException
import java.sql.Timestamp

class CRUDMovimentacao : ConexaoPostgres(), InterfaceJPA<Movimentacao> {
    override fun salvar(item: Movimentacao) {
        try {
            abrirConexao().use { conexao ->
                val sql = """
                    INSERT INTO movimentacao
                    (valor, data_hora, descricao, pagador, recebedor, responsavel_id)
                    VALUES (?, ?, ?, ?, ?, ?)
                """.trimIndent()

                conexao.prepareStatement(sql).use { stmt ->
                    stmt.setBigDecimal(1, item.valor)
                    stmt.setTimestamp(2, Timestamp.valueOf(item.dataHora))
                    stmt.setString(3, item.contexto.name)
                    stmt.setString(4, item.pagador)
                    stmt.setString(5, item.recebedor)
                    stmt.setInt(6, item.responsavelId)
                    stmt.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            println("Erro ao salvar movimentação: ${e.message}")
        }
    }

    override fun editar(item: Movimentacao, id: Int) {} // Não será implementado
    override fun excluir(id: Int) {} // Não será implementado

    override fun listar() {
        try {
            abrirConexao().use { conexao ->
                conexao.createStatement().use { stmt ->
                    stmt.executeQuery("SELECT * FROM movimentacao ORDER BY data_hora DESC").use { rs ->
                        while (rs.next()) {
                            println("ID: ${rs.getInt("id")}")
                            println("Valor: ${rs.getBigDecimal("valor")}")
                            println("Data/hora: ${rs.getTimestamp("data_hora")}")
                            println("Descrição: ${rs.getString("descricao")}")
                            println("Pagador: ${rs.getString("pagador")}")
                            println("Recebedor: ${rs.getString("recebedor")}")
                            println("Responsável ID: ${rs.getInt("responsavel_id")}")
                            println("---------------------------------------------------------------")
                        }
                    }
                }
            }
        } catch (e: SQLException) {
            println("Erro ao listar movimentações: ${e.message}")
        }
    }
}
