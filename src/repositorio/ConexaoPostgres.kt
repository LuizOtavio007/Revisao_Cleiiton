package repositorio

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

abstract class ConexaoPostgres(
    val user: String = "postgres",
    val senha: String = "postgres",
    val url: String = "jdbc:postgresql://localhost:5432/caixaDaAgua",
    var c: Connection? = null
) {
    fun abrirConexao(): Connection {
        Class.forName("org.postgresql.Driver")
        return DriverManager.getConnection(url, user, senha)
    }

    fun conectar() {
        try {
            c = abrirConexao()
            println("A conexão foi estabelecida")
        } catch (e: SQLException) {
            println("Erro ao conectar no banco: ${e.message}")
        }
    }
}
