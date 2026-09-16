package sistema.caixadeagua

import repositorio.CRUDCaixaDaAgua

fun listarCaixa() {
    val crudCaixaDaAgua = CRUDCaixaDaAgua()
    crudCaixaDaAgua.listar()
}