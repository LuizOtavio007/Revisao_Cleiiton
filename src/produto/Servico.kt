package produto

import enums.Habilidade
import enums.Turno
import pessoas.Cliente
import pessoas.Instalador
import java.math.BigDecimal
import java.time.LocalDate

data class Servico(
    var instalador: Instalador = Instalador(
        nome = "",
        cpf = "",
        idade = 0,
        salario = BigDecimal.ZERO,
        turno = Turno.NOTURNO,
        habilidade = Habilidade.INSTALACAO
    ),

    var preco: String = "0.0",
    var dataInstalacao: LocalDate = LocalDate.of(1970, 1, 1),
    var cliente: Cliente = Cliente(
        nomeCliente = "",
        cpfCliente = "",
        idadeCliente = 0,
        dividasAbertas = false,
        parcelasAPagar = mutableListOf()
    )
)