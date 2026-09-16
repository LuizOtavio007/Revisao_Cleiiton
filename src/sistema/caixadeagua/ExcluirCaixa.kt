package sistema.caixadeagua

import repositorio.CRUDCaixaDaAgua
import util.lerInteiroPositivo

fun excluirCaixa() {
    val crud = CRUDCaixaDaAgua()
    crud.listar()
    val id = lerInteiroPositivo("Digite o ID que deseja excluir: ")
    crud.excluir(id)
}
