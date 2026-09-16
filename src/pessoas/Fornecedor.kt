package pessoas

class Fornecedor(
    nome: String,
    cpf: String,
    idade: Int,
    email: String? = null,
    val empresa: String
) : Pessoa(nome, cpf, idade, email)
