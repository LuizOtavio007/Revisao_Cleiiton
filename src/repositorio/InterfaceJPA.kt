package repositorio

//T é uma classe genérica
//A Interface é um contrato
//As funções são clausulas
//Nossas classes que herdam essa interface
//Precisa implementar as funções
interface InterfaceJPA<T> {
    //Item é o meu parametro genérico
    fun salvar(item: T)
    fun listar()
    fun editar(item: T, id: Int)
    fun excluir(id: Int)
}