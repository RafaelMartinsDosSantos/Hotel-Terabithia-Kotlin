import java.util.Locale
import kotlin.system.exitProcess

val nomedohotel = "Hotel Sete Luas"
var nomedeusuario = ""
var senha = 2678

val listadequartos = mutableListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)

data class reservas(
    val nomecompleto: String,
    val numeroquarto: Int,
    val tipoquarto: String,
    val subtotal: Double = 0.0,
    val taxa: Double,
    val valortotal: Double = 0.0,
)


fun main(){
    usuarioesenha()
}

fun usuarioesenha(){
    println("Bem vindo ao nosso $nomedohotel")
    print("Digite o usuario: ")
    nomedeusuario = readln()
    print("Digite a senha: ")
    var senhaprovisoria = readln().toIntOrNull()

    var contador = 0
    while (contador < 3)
    if (senha == senhaprovisoria){
        println("Bem vindo ao $nomedohotel, $nomedeusuario. É um imenso prazer ter você por aqui!")
        contador += 4
        menu()
    }
    else {
    println("Senha incorreta, tente novamente.")
        println("Digite a senha: ")
        senhaprovisoria = readln()?.toIntOrNull()
        contador += 1
    }

    if (contador == 3){
        println("Numeros de tentativas excedida!")
        exitProcess(0)
    }

}

fun menu(){
    println("""O que deseja fazer hoje?
        |1 - Reservas de Quartos
        |2 - Cadastro de Hóspedes
        |3 - Eventos
        |4 - Ar-Condicionado
        |5 - Abastecimento
        |6 - Relatórios Operacionais
        |7 - Sair
    """.trimMargin())

    val escolha = readln().toIntOrNull()

    when(escolha){
        1 -> {
            reservasdeQuartos()
        }
        2 -> {
            cadastrodehospedes()
        }
        3 -> {
            eventos()
        }
        4 -> {
            arcondicionado()
        }
        5 -> {
            abastecimento()
        }
        6 -> {
            relatoriosOperacionais()
        }
        7 -> {
            sair()
        }
        else -> erro()



    }




}

fun reservasdeQuartos(){


  println("Vamos reserver o seu quarto!")
    var valorreserva = 100.00
    println("O valor da nossa reserva está R$$valorreserva")
    print("Quantos dias deseja hospedar? (1-30): ")
    var hospedagem = readln().toDouble()

    if (hospedagem <= 0 || hospedagem > 30) {
        println("Valor Inválido, $nomedeusuario")
        menu()
    }

    else

    print("Informe o seu nome completo: ")
    val nome = readlnOrNull().toString()

    print("Tipo de quarto (S/E/L): ")
    val escolhatemp = readln().uppercase(Locale.getDefault())

    var estiloquarto = ""

    when(escolhatemp){
        "S" -> {
            estiloquarto = "Standard"
        }
        "E" -> {
            estiloquarto = "Executivo"
        }
        "L" -> {
            estiloquarto = "Luxo"
        }
        else -> {
            println("Opcão inválida")
            menu()
        }

    }


    println("Esses são os quartos disponiveis: $listadequartos")

    println("Escolha o numero do seu quarto: ")
    val numeroquartotemp = readln().toInt()
    if (numeroquartotemp <= 0 || numeroquartotemp > 20){
        println("Valor invalido, $nomedeusuario")
        menu()
    }
    else if (listadequartos.any {it.equals(numeroquartotemp)}){
    listadequartos.remove(numeroquartotemp)
    }
    else{
        println("Esse quarto já está ocupado!")
        menu()
    }

    hospedagem *= 100

    val total = hospedagem * 0.10




   val reserva = reservas (nomecompleto = nome, numeroquarto = numeroquartotemp, tipoquarto = estiloquarto, subtotal = hospedagem, taxa = total, valortotal = hospedagem + total )

    print(reserva)



}

fun cadastrodehospedes(){

}

fun eventos(){


}

fun arcondicionado(){



}

fun abastecimento(){



}

fun relatoriosOperacionais(){



}

fun erro(){
    println("Por favor digite uma opcão válida.")
    menu()
}

fun sair(){

}