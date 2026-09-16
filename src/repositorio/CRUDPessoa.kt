package repositorio

import pessoas.Cliente
import pessoas.Fornecedor
import pessoas.Funcionario
import pessoas.Pessoa
import java.sql.SQLException

class CRUDPessoa : ConexaoPostgres() {
    fun salvar(pessoa: Pessoa) {
        val tipo = when (pessoa) {
            is Funcionario -> "FUNCIONARIO"
            is Fornecedor -> "FORNECEDOR"
            is Cliente -> "CLIENTE"
            else -> "PESSOA"
        }

        try {
            abrirConexao().use { conexao ->
                val sql = """
                    INSERT INTO pessoa (nome, cpf, idade, email, tipo, salario, setor, empresa)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """.trimIndent()
                conexao.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, pessoa.nome)
                    stmt.setString(2, pessoa.cpf)
                    stmt.setInt(3, pessoa.idade)
                    stmt.setString(4, pessoa.email)
                    stmt.setString(5, tipo)
                    if (pessoa is Funcionario) stmt.setBigDecimal(6, pessoa.salario) else stmt.setNull(6, java.sql.Types.NUMERIC)
                    if (pessoa is Funcionario) stmt.setString(7, pessoa.setor.name) else stmt.setNull(7, java.sql.Types.VARCHAR)
                    if (pessoa is Fornecedor) stmt.setString(8, pessoa.empresa) else stmt.setNull(8, java.sql.Types.VARCHAR)
                    stmt.executeUpdate()
                }
            }
            println("Pessoa cadastrada com sucesso!")
        } catch (e: SQLException) {
            println("Erro ao cadastrar pessoa: ${e.message}")
        }
    }

    fun listarTodos() {
        try {
            abrirConexao().use { conexao ->
                conexao.createStatement().use { stmt ->
                    stmt.executeQuery("SELECT id, nome, cpf, tipo, setor FROM pessoa ORDER BY id").use { rs ->
                        while (rs.next()) {
                            println("ID: ${rs.getInt("id")} | Nome: ${rs.getString("nome")} | CPF: ${rs.getString("cpf")} | Tipo: ${rs.getString("tipo")} | Setor: ${rs.getString("setor") ?: "-"}")
                        }
                    }
                }
            }
        } catch (e: SQLException) {
            println("Erro ao listar pessoas: ${e.message}")
        }
    }

    fun listarFuncionarios() {
        try {
            abrirConexao().use { conexao ->
                conexao.createStatement().use { stmt ->
                    stmt.executeQuery("SELECT id, nome, setor FROM pessoa WHERE tipo = 'FUNCIONARIO' ORDER BY id").use { rs ->
                        while (rs.next()) {
                            println("ID: ${rs.getInt("id")} | ${rs.getString("nome")} | Setor: ${rs.getString("setor")}")
                        }
                    }
                }
            }
        } catch (e: SQLException) {
            println("Erro ao listar funcionários: ${e.message}")
        }
    }
}
