package util

import java.math.BigDecimal

private val regexCpf = Regex("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$")
private val regexEmail = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

fun lerTextoObrigatorio(mensagem: String): String {
    while (true) {
        print(mensagem)
        val texto = readlnOrNull()?.trim()
        if (!texto.isNullOrBlank()) return texto
        println("O campo não pode ficar vazio.")
    }
}

fun lerCpf(mensagem: String): String {
    while (true) {
        val cpf = lerTextoObrigatorio(mensagem)
        if (regexCpf.matches(cpf)) return cpf
        println("CPF inválido. Use o formato 000.000.000-00.")
    }
}

fun lerEmailOpcional(mensagem: String): String? {
    while (true) {
        print(mensagem)
        val email = readlnOrNull()?.trim().orEmpty()
        if (email.isBlank()) return null
        if (regexEmail.matches(email)) return email
        println("E-mail inválido.")
    }
}

fun lerDecimalPositivo(mensagem: String): Double {
    while (true) {
        print(mensagem)
        val numero = readlnOrNull()?.trim()?.replace(',', '.')?.toDoubleOrNull()
        if (numero != null && numero > 0) return numero
        println("Digite um número positivo válido.")
    }
}

fun lerDinheiroPositivo(mensagem: String): BigDecimal {
    while (true) {
        print(mensagem)
        val valor = readlnOrNull()?.trim()?.replace(',', '.')?.toBigDecimalOrNull()
        if (valor != null && valor > BigDecimal.ZERO) return valor
        println("Digite um valor monetário positivo.")
    }
}

fun lerOpcao(mensagem: String, ultimoIndice: Int): Int {
    while (true) {
        print(mensagem)
        val opcao = readlnOrNull()?.trim()?.toIntOrNull()
        if (opcao != null && opcao in 0..ultimoIndice) return opcao
        println("Escolha uma opção válida.")
    }
}

fun lerInteiroPositivo(mensagem: String): Int {
    while (true) {
        print(mensagem)
        val numero = readlnOrNull()?.trim()?.toIntOrNull()
        if (numero != null && numero > 0) return numero
        println("Digite um número inteiro positivo.")
    }
}

fun lerInteiroNaoNegativo(mensagem: String): Int {
    while (true) {
        print(mensagem)
        val numero = readlnOrNull()?.trim()?.toIntOrNull()
        if (numero != null && numero >= 0) return numero
        println("Digite um número inteiro maior ou igual a zero.")
    }
}
