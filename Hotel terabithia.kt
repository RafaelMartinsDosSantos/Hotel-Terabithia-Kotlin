import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.ceil
import kotlin.system.exitProcess

val nomedohotel = "Hotel Sete Luas" //Nome do hote e nome de usuario eu optei por usar de forma global ja que ele é solicitado em outros momentos do codigo.
var nomedeusuario = ""
var senha = 2678

val listadequartos = mutableListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)
val reservasdequartos = mutableListOf<reservas>()
val pikachucadastro = mutableListOf<hospedescomhora?>()
val agendadeauditorio = mutableListOf<semanaehora>()
val cadastroempresasarcondicionado = mutableListOf<empresastercerizadas>()

data class reservas(
    val nomecompleto: String,
    val numeroquarto: Int,
    val tipoquarto: String,
    val diarias: Int,
    val subtotal: Double = 0.0,
    val taxa: Double,
    val valortotal: Double = 0.0,
)

data class hospedescomhora(
    val nome: String,
    val dataehora: LocalDateTime
)

data class semanaehora (
    val convidadosselecionados: Int,
    val adutorioselecionados: String,
    val diasemana: String,
    val horasemana: Int,
    val duracao: Int,
    val empresa: String,
    val status: String,
    val quantidadegarcons: Int,
    val valorgarcons: Double,
    val cafequant: Double,
    val aguaquant: Double,
    val salgadoquant: Int,
    val custobuffet: Double,
    val custoeventototal: Double,
)

data class empresastercerizadas(
    val nomeempresa: String,
    val valoporaparelho: Int,
    val quantidadedeaparelhos: Int,
    val descontovariavel: Double,
    val minimodesconto: Int,
    val deslocamento: Int,
    val totalarcondicioado: Double,
) // Questao das data class e listas so sao possiveis usá-las de forma global

// Verifica se um texto que deveria ser um nome nao foi digitado como numero/vazio
fun nomeInvalido(texto: String?): Boolean {
    return texto.isNullOrBlank() || texto.toDoubleOrNull() != null
}

fun main(){
    usuarioesenha()
}

fun usuarioesenha(){
    println("Bem vindo ao nosso $nomedohotel")
    print("Digite o usuario: ")
    val usuarioInput = readln()

    if (nomeInvalido(usuarioInput)) {
        erro()
        return
    }
    nomedeusuario = usuarioInput

    print("Digite a senha: ")
    var senhaprovisoria = readln().toIntOrNull()

    var contador = 1
    var acesso = false

    while (contador <= 3 && !acesso) {
        if (senha == senhaprovisoria) {
            println("Bem vindo ao $nomedohotel, $nomedeusuario. É um imenso prazer ter você por aqui!")
            acesso = true
            menu()
        }
        else if (contador < 3) {
            println("Senha incorreta, tente novamente.")
            print("Digite a senha: ")
            senhaprovisoria = readln().toIntOrNull()
            contador += 1
        }
        else {
            contador += 1
        }
    }

    if (!acesso) {
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
    val hospedagemInput = readln().toDoubleOrNull()

    if (hospedagemInput == null) {
        erro()
        return
    }

    var hospedagem = hospedagemInput
    val diariasescolhidas = hospedagemInput.toInt()

    if (hospedagem <= 0 || hospedagem > 30) {
        println("Valor Inválido, $nomedeusuario")
        menu()
        return
    }

    print("Informe o seu nome completo: ")
    val nomeInput = readlnOrNull()

    if (nomeInvalido(nomeInput)) {
        erro()
        return
    }
    val nome = nomeInput!!

    hospedagem *= valorreserva

    print("Tipo de quarto (S/E/L): ")
    val escolhatemp = readln().uppercase(Locale.getDefault())

    var estiloquarto = ""

    when(escolhatemp){
        "S" -> {
            estiloquarto = "Standard"
        }
        "E" -> {
            estiloquarto = "Executivo"
            hospedagem *= 1.35
        }
        "L" -> {
            estiloquarto = "Luxo"
            hospedagem *= 1.65
        }
        else -> {
            erro()
            return
        }
    }

    for (linha in 1..4) {
        for (coluna in 1..5) {

            val quarto = (linha - 1) * 5 + coluna

            if (listadequartos.contains(quarto)) {
                print("[$quarto:L] ")
            } else {
                print("[$quarto:O] ")
            }
        }
        println()
    }

    var numeroquartotemp = 0
    var quartoValido = false

    while (!quartoValido) {
        println("Escolha o numero do seu quarto: ")
        val numeroquartotempInput = readln().toIntOrNull()

        if (numeroquartotempInput == null) {
            erro()
            return
        }
        numeroquartotemp = numeroquartotempInput

        if (numeroquartotemp <= 0 || numeroquartotemp > 20){
            println("Valor invalido, $nomedeusuario")
        }
        else if (!listadequartos.contains(numeroquartotemp)){
            println("Esse quarto esta ocupado! Quartos livres: $listadequartos")
            println("Escolha outro numero de quarto.")
        }
        else {
            quartoValido = true
        }
    }

    val total = hospedagem * 0.10

    val reserva = reservas (
        nomecompleto = nome,
        numeroquarto = numeroquartotemp,
        tipoquarto = estiloquarto,
        diarias = diariasescolhidas,
        subtotal = hospedagem,
        taxa = total,
        valortotal = hospedagem + total
    )

    println("""
        |Resumo:
        |Hóspede: ${reserva.nomecompleto}
        |Quarto: ${reserva.numeroquarto} (${reserva.tipoquarto})
        |Diárias: ${reserva.diarias}
        |Subtotal: ${reserva.subtotal}
        |Taxa de serviço (10%): ${reserva.taxa}
        |Total: ${reserva.valortotal}
    """.trimMargin())

    print("$nomedeusuario, confirma a reserva? (S/N): ")
    val simounaoreserva = readln().uppercase(Locale.getDefault())

    when (simounaoreserva){
        "S" -> {
            println("Reserva efetuada com sucesso.")
            listadequartos.remove(numeroquartotemp)
            reservasdequartos.add(reserva)
            menu()
        }
        "N" -> {
            println("Reserva não efetuada.")
            menu()
        }
        else -> {
            erro()
            return
        }
    }
}

fun cadastrodehospedes(){
    println("""
Escolha uma opcão:
1- Cadastrar
2- Pesquisar por nome exato
3- Pesquisar por prefixo
4- Listar ordenado (A-Z)
5- Atualizar cadastro
6- Remover cadastro
7- Voltar""")

    val escolhaInput = readln().toIntOrNull()

    if (escolhaInput == null) {
        erro2()
        return
    }
    val escolha = escolhaInput

    when(escolha) {
        1 -> {
            cadastrar()
        }

        2 -> {
            pesquisarnomeexato()
        }

        3 -> {
            pesquisarporprefixo()
        }

        4 -> {
            listarodenado()
        }

        5 -> {
            atualizarcadastro()
        }

        6 -> {
            removercadastro()
        }

        7 -> {
            menu()
        }

        else -> erro2()
    }
}

fun cadastrar(){
    print("Nome completo para cadastro: ")
    val nomecadastro = readln()

    if (nomeInvalido(nomecadastro)) {
        erro2()
        return
    }

    if (pikachucadastro.size > 14) {
        println("Máximo de cadastros atingido!")
        cadastrodehospedes()
    }
    else if (pikachucadastro.any { it?.nome.equals(nomecadastro, ignoreCase = true) }){
        println("Esse hospede ja esta cadastrado!")
        cadastrodehospedes()
    }
    else {
        val hospede = hospedescomhora(
            nome = nomecadastro,
            dataehora = LocalDateTime.now()
        )

        pikachucadastro.add(hospede)

        println("Hospede cadastrado!")
        cadastrodehospedes()
    }
}

fun pesquisarnomeexato(){
    print("Nome para pesquisar: ")
    val blazikenpesquisar = readln()

    val hospede = pikachucadastro.find {
        it?.nome.equals(blazikenpesquisar, ignoreCase = true)
    }

    if (hospede != null) {
        println("Hospede ${hospede.nome} foi encontrado!")
        cadastrodehospedes()
    }
    else {
        println("Hospede não encontrado!")
        cadastrodehospedes()
    }
}

fun pesquisarporprefixo(){
    println("Digite o nome para pesquisar: ")
    val pesquisar = readln()

    var encontrou = false

    for (hospede in pikachucadastro){
        if (hospede?.nome?.startsWith(pesquisar, ignoreCase = true) == true){
            println(hospede?.nome)
            encontrou = true
        }
    }

    if (!encontrou) {
        println("Hospede não encontrado")
    }

    cadastrodehospedes()
}

fun listarodenado(){
    println("Esses são os hospedes cadastrados: ")

    val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    for ((indice, hospede) in pikachucadastro.sortedBy { it?.nome }.withIndex()){
        println(
            "Índice: $indice | Nome: ${hospede?.nome} | Data/Hora do cadastro: ${hospede?.dataehora?.format(formato)}"
        )
    }

    cadastrodehospedes()
}

fun atualizarcadastro() {
    println("Esses sao os hospedes cadastrados: ")
    val listaOrdenada = pikachucadastro
        .withIndex()
        .sortedBy { it.value?.nome }

    for (item in listaOrdenada) {
        println("${item.index} - ${item.value?.nome}")
    }
    var contador = 1

    while (contador < 3) {

        println("Qual hospede deseja atualizar? (de 0 a ${pikachucadastro.size - 1})")
        val escolhaInput = readln().toIntOrNull()

        if (escolhaInput == null) {
            erro2()
            return
        }
        val escolha = escolhaInput

        if (escolha < 0 || escolha >= pikachucadastro.size ) {
            println("Por favor digite uma opcao valida.")
        }
        else {
            val hospede = pikachucadastro[escolha]

            println("Esse hospede que deseja atualizar? (S/N): ${hospede?.nome}")
            val opcao = readln().uppercase(Locale.getDefault())
            when (opcao) {
                "S" -> {
                    println("Ok, vamos la!")

                    print("Digite o novo nome: ")
                    val escolha2 = readln()

                    if (nomeInvalido(escolha2)) {
                        erro2()
                        return
                    }

                    val adicionar = hospedescomhora (nome = escolha2, dataehora = LocalDateTime.now())
                    pikachucadastro[escolha] = adicionar
                    println("Operação realizada com sucesso")
                    contador += 4
                }
                "N" -> {
                    println("Ok, vamos la!")
                    contador += 4
                    cadastrodehospedes()
                }
                else -> {
                    erro2()
                    return
                }
            }

        }
    }
    menu()


}

fun removercadastro(){
    println("Esses sao os hospedes cadastrados: ")
    val listaOrdenada = pikachucadastro
        .withIndex()
        .sortedBy { it.value?.nome }

    for (item in listaOrdenada) {
        println("${item.index} - ${item.value?.nome}")
    }
    var contador = 0

    while (contador < 3) {

        println("Qual hospede deseja remover? (de 0 a ${pikachucadastro.size - 1})")
        val escolha3Input = readln().toIntOrNull()

        if (escolha3Input == null) {
            erro2()
            return
        }
        val escolha3 = escolha3Input

        if (escolha3 < 0 || escolha3 >= pikachucadastro.size ) {
            println("Por favor digite uma opcao valida.")
        }
        else {pikachucadastro.removeAt(escolha3)
            println("Hospede removido com sucesso!")
            cadastrodehospedes()
        }
    }
}

fun eventos(){
    var auditorioselect = ""

    println("""
    |O nosso hotel possui espaço maximo de 350 convidados.
    |Sendo um deles o Auditório Laranja, com limite de 150 lugares + 70 cadeiras extras (máx. 220)
    |E o Auditório Colorado com limite de 350 lugares.
    |
    |Vamos organizar o evento do hotel.
""".trimMargin())
    var soma = 0

    print("Informe quantos convidados: ")
    val quantconvidadosInput = readln().toIntOrNull()

    if (quantconvidadosInput == null) {
        erro()
        return
    }
    val quantconvidados = quantconvidadosInput


    if (quantconvidados < 0){
        println("Quantidade inválida")
        menu()
        return
    }
    else if (quantconvidados > 350){
        println("Capacidade excedida")
        menu()
        return
    }

    if(quantconvidados > 150 && quantconvidados <= 220){

        soma += quantconvidados - 150

        println("Para a quantidade de convidados recomendamos o Auditório Laranja, considerando que serão necessarios $soma cadeiras adicionais. ")
        print("Deseja prosseguir? (S/N): ")
        val escolhasimounao = readln().uppercase(Locale.getDefault())

        when (escolhasimounao) {
            "S" -> {
                println("Ok, vamos la!")
                auditorioselect += "Laranja"
            }
            "N" -> {
                println("Ok, Obrigado!")
                menu()
                return
            }
            else -> {
                erro()
                return
            }
        }
    }
    else if (quantconvidados <= 150) {
        println("Para a quantidade de convidados recomendamos o Auditório Laranja")
        print("Deseja prosseguir? (S/N): ")
        val escolhasimounao2 = readln().uppercase(Locale.getDefault())

        when (escolhasimounao2) {
            "S" -> {
                println("Ok, vamos la!")
                auditorioselect += "Laranja"
            }
            "N" -> {
                println("Ok, Obrigado!")
                menu()
                return
            }
            else -> {
                erro()
                return
            }
        }
    }
    else if (quantconvidados > 220){
        println("Para a quantidade de convidados recomendamos o Auditório Colorado")
        print("Deseja prosseguir? (S/N): ")
        val escolhasimounao3 = readln().uppercase(Locale.getDefault())

        when (escolhasimounao3) {
            "S" -> {
                println("Ok, vamos la!")
                auditorioselect += "Colorado"
            }
            "N" -> {
                println("Ok, Obrigado!")
                menu()
                return
            }
            else -> {
                erro()
                return
            }
        }
    }

    println("""O Hotel possui uma disponibilidade de horario de segunda a segunda mas com algumas especificações que deve ser seguidas.
        |Segunda a sexta: 07h–23h
        |Sábado e domingo: 07h–15h
    """.trimMargin())

    print("Informe o dia da semana do evento: ")
    val dataevento = readln().lowercase(Locale.getDefault())
    when (dataevento) {
        "segunda" -> {
            println("Dia escolhido: $dataevento")
        }
        "terca" -> {
            println("Dia escolhido: $dataevento")
        }
        "quarta" -> {
            println("Dia escolhido: $dataevento")
        }
        "quinta" -> {
            println("Dia escolhido: $dataevento")
        }
        "sexta" -> {
            println("Dia escolhido: $dataevento")
        }
        "sabado" -> {
            println("Dia escolhido: $dataevento")
        }
        "domingo" -> {
            println("Dia escolhido: $dataevento")
        }
        else -> {
            erro()
            return
        }
    }



    var horarioinicioreal = 0
    var horaduracaoreal = 0
    var limitehorafinal = 23

    if (dataevento == "segunda" || dataevento == "terca" || dataevento == "quarta" || dataevento == "quinta" || dataevento == "sexta") {
        limitehorafinal = 23
        println("Horario disponivel: 07h–23h")

        print("Informe o horario de inicio do evento: ")
        val horarioinicioleInput = readln().toIntOrNull()
        if (horarioinicioleInput == null) {
            erro()
            return
        }
        val horarioinicioler = horarioinicioleInput
        if (horarioinicioler < 7 || horarioinicioler > 23){
            erro()
            return
        }
        else {
            println("Horario inicio registrado!")
            horarioinicioreal += horarioinicioler
        }

        print("Vamos definir a duração do evento (Max 12h.): ")
        val horaduracaoleInput = readln().toIntOrNull()
        if (horaduracaoleInput == null) {
            erro()
            return
        }
        val horaduracaoler = horaduracaoleInput
        if (horaduracaoler > 12 || horaduracaoler < 1) {
            erro()
            return
        }
        else {
            println("duração do evento registrada!")
            horaduracaoreal += horaduracaoler
        }
    }

    else {
        limitehorafinal = 15
        println("Horario disponivel: 07h–15h")

        print("Informe o horario de inicio do evento: ")
        val rayquazahorarioinicioleInput = readln().toIntOrNull()
        if (rayquazahorarioinicioleInput == null) {
            erro()
            return
        }
        val rayquazahorarioinicioler = rayquazahorarioinicioleInput
        if (rayquazahorarioinicioler < 7 || rayquazahorarioinicioler > 15){
            erro()
            return
        }
        else {
            println("Horario inicio registrado!")
            horarioinicioreal += rayquazahorarioinicioler
        }

        print("Vamos definir a duração do evento (Max 12h.): ")
        val scizorhoraduracaoleInput = readln().toIntOrNull()
        if (scizorhoraduracaoleInput == null) {
            erro()
            return
        }
        val scizorhoraduracaoler = scizorhoraduracaoleInput
        if (scizorhoraduracaoler > 12 || scizorhoraduracaoler < 1) {
            erro()
            return
        }
        else {
            println("duração do evento registrada!")
            horaduracaoreal += scizorhoraduracaoler
        }
    }

    if (horarioinicioreal + horaduracaoreal > limitehorafinal) {
        println("O evento ultrapassa o horario disponivel do auditorio, $nomedeusuario.")
        menu()
        return
    }

    println("Auditorio disponivel!")
    val statusauditorio = "Auditorio disponivel!"

    print("Informe o nome da empresa: ")
    val empresaevento = readln()

    if (nomeInvalido(empresaevento)) {
        erro()
        return
    }


    // Conta e valores dos garcons


    var basegarcons = 0
    var Reforçoduracaogarcons = 0
    var totalgarcons = 0

    basegarcons += ceil(quantconvidados / 12.0).toInt()

    Reforçoduracaogarcons += horaduracaoreal / 2

    totalgarcons += Reforçoduracaogarcons + basegarcons

    var custogarcons = 0.0

    custogarcons = totalgarcons * horaduracaoreal * 10.50

// Conta e valores do buffet

    var quantidadecafe = quantconvidados * 0.2
    var custocafe = quantidadecafe * 0.8


    var quantidadeagua = quantconvidados * 0.5
    var custoagua = quantidadeagua * 0.4

    var quantidadesalgado = quantconvidados * 7
    var custosalgado = quantidadesalgado / 100.0 * 34

    var custototalbuffet = custosalgado + custoagua + custocafe

    var totaldoevento = custototalbuffet + custogarcons

    val semanaehoras = semanaehora (
        convidadosselecionados = quantconvidados,
        adutorioselecionados = auditorioselect,
        diasemana = dataevento,
        horasemana = horarioinicioreal,
        duracao = horaduracaoreal,
        empresa = empresaevento,
        status = statusauditorio,
        quantidadegarcons = totalgarcons,
        valorgarcons = custogarcons,
        cafequant = quantidadecafe,
        aguaquant = quantidadeagua,
        salgadoquant = quantidadesalgado,
        custobuffet = custototalbuffet,
        custoeventototal = totaldoevento,
    )

    println("""[Eventos]
        |Convidados: ${semanaehoras.convidadosselecionados}
        |Auditório selecionado: ${semanaehoras.adutorioselecionados} ($soma cadeiras adicionais)
        |
        |Dia: ${semanaehoras.diasemana}
        |Hora inicial: ${semanaehoras.horasemana}h
        |Hora final: ${semanaehoras.horasemana + semanaehoras.duracao}h
        |Duração: ${semanaehoras.duracao}h
        |Empresa: ${semanaehoras.empresa}
        |Status: ${semanaehoras.status}
        |
        |Garçons necessários: ${semanaehoras.quantidadegarcons}
        |Custo com garçons: R$ ${semanaehoras.valorgarcons}
        |
        |Buffet:
        |Café: ${semanaehoras.cafequant}
        |Água: ${semanaehoras.aguaquant}
        |Salgados: ${semanaehoras.salgadoquant}
        |Custo buffet: ${semanaehoras.custobuffet}
        |
        |Total do evento: ${semanaehoras.custoeventototal}
    """.trimMargin())
    print("Confirmar reserva? (S/N): ")
    val reservasimounao = readln().uppercase(Locale.getDefault())

    when (reservasimounao) {
        "S" -> {
            println("Reserva efetuada com sucesso.")
            agendadeauditorio.add(semanaehoras)
        }
        "N" -> {
            println("Que pena! volte sempre.")
        }
        else -> {
            erro()
            return
        }
    }

    menu()









}

fun arcondicionado(){
    println("Aqui vamos informar os dados das empresas tercerizadas responsaveis pela manutenção dos ar-condicionados do hotel")

    print("Informe o nome da empresa: ")
    var krokodilenomeempresa = readln()

    if (nomeInvalido(krokodilenomeempresa)) {
        erro()
        return
    }

    print("Informe o valor por aparelho: ")
    val entrada1 = readln().toIntOrNull()

    if (entrada1 == null) {
        erro()
        return
    }

    var infernapeevaloraparelho = entrada1

    print("Informe a quantidade de aparelhos: ")
    val entrada2 = readln().toIntOrNull()

    if (entrada2 == null) {
        erro()
        return
    }

    var charizardquantidadedeaparelhos = entrada2

    print("Informe percentual de desconto: ")
    val entrada3 = readln().toDoubleOrNull()

    if (entrada3 == null) {
        erro()
        return
    }

    var percentualdedesconto = entrada3

    print("Informe a quantidade minima para desconto: ")
    val entrada4 = readln().toIntOrNull()

    if (entrada4 == null) {
        erro()
        return
    }

    var quantidademinimadesconto = entrada4

    print("Informe valor de deslocamento: ")
    val entrada5 = readln().toIntOrNull()

    if (entrada5 == null) {
        erro()
        return
    }

    var valordeslocamento = entrada5


    var valortotaldetudo = 0.0

    var valorbruto = infernapeevaloraparelho * charizardquantidadedeaparelhos

    if (charizardquantidadedeaparelhos >= quantidademinimadesconto){
        valortotaldetudo = valorbruto - (valorbruto * percentualdedesconto / 100) + valordeslocamento
    }
    else {
        valortotaldetudo = (valorbruto + valordeslocamento).toDouble()
    }

    var empresaprovisoria = empresastercerizadas (
        nomeempresa = krokodilenomeempresa,
        valoporaparelho = infernapeevaloraparelho,
        quantidadedeaparelhos = charizardquantidadedeaparelhos,
        descontovariavel = percentualdedesconto,
        minimodesconto = quantidademinimadesconto,
        deslocamento = valordeslocamento,
        totalarcondicioado = valortotaldetudo
    )
    println("""[Ar-Condicionado]
        |Empresa: ${empresaprovisoria.nomeempresa}
        |Valor por aparelho: ${empresaprovisoria.valoporaparelho}
        |Quantidade: ${empresaprovisoria.quantidadedeaparelhos}
        |Desconto (%): ${empresaprovisoria.descontovariavel}
        |Mínimo para desconto: ${empresaprovisoria.minimodesconto}
        |Deslocamento: ${empresaprovisoria.deslocamento}
        |Total: ${empresaprovisoria.totalarcondicioado}
    """.trimMargin())

    cadastroempresasarcondicionado.add(empresaprovisoria)

    print("Deseja informar novos dados, $nomedeusuario? (S/N): ")
    val empresasimounao = readln().uppercase(Locale.getDefault())

    when (empresasimounao) {
        "S" -> {
            arcondicionado()
            return
        }
        "N" -> {
            val menor = cadastroempresasarcondicionado.minByOrNull { it.totalarcondicioado }
            val maior = cadastroempresasarcondicionado.maxByOrNull { it.totalarcondicioado }

            val diferencapercentual =
                ((maior!!.totalarcondicioado - menor!!.totalarcondicioado) / menor.totalarcondicioado) * 100

            println("O orçamento de menor valor é o de ${menor.nomeempresa} por R$ ${menor.totalarcondicioado}")

            println("O orçamento de maior valor é o de ${maior.nomeempresa} por R$ ${maior.totalarcondicioado}")

            println("A diferença percentual entre a melhor e a pior proposta é de ${diferencapercentual}%")
        }
        else -> {
            erro()
            return
        }
    }
    menu()
}

fun abastecimento(){
    println("""Aqui faremos o abastecimento dos veiculos do hotel
        |para o passeio de nossos hospedes.
        |Utilizando os postos do nosso hotel, sendo eles:
        |Wayne Oil
        |Stark Petrol
    """.trimMargin())

    println("[POSTO WAYNE OIL]")

    print("Informe o valor da gasolina: ")
    var entrada1 = readln().toDoubleOrNull()

    if (entrada1 == null) {
        erro()
        return
    }

    var gasolinawayneoriginal = entrada1

    print("Informe o valor do Etanol: ")
    var entrada2 = readln().toDoubleOrNull()

    if (entrada2 == null) {
        erro()
        return
    }

    var etanolwayneoriginal = entrada2

    println("[POSTO STARK PETROL]")

    print("Informe o valor da gasolina: ")
    var entrada3 = readln().toDoubleOrNull()

    if (entrada3 == null) {
        erro()
        return
    }

    var gasolinastarkoriginal = entrada3

    print("Informe o valor do Etanol: ")
    var entrada4 = readln().toDoubleOrNull()

    if (entrada4 == null) {
        erro()
        return
    }

    var etanolstarkoriginal = entrada4

    println("""[ABASTECIMENTO]
        |Wayne Oil -> Etanol: R$ $etanolwayneoriginal || Gasolina: R$ $gasolinawayneoriginal
        |Stark Petrol -> Etanol: R$ $etanolstarkoriginal || Gasolina: R$ $gasolinastarkoriginal
    """.trimMargin())

    val totaletanolwayne = etanolwayneoriginal * 42
    val totalgasolinawayne = gasolinawayneoriginal * 42
    val totaletanolstark = etanolstarkoriginal * 42
    val totalgasolinastark = gasolinastarkoriginal * 42

    var waynetotal = 0.0
    var starktotal = 0.0

    if (etanolwayneoriginal <= gasolinawayneoriginal * 0.70) {
        waynetotal = totaletanolwayne
        println("Wayne Oil: melhor opção = Etanol | Total (42L) = R$ $waynetotal")
    }
    else {
        waynetotal = totalgasolinawayne
        println("Wayne Oil: melhor opção = Gasolina | Total (42L) = R$ $waynetotal")
    }

    if (etanolstarkoriginal <= gasolinastarkoriginal * 0.70) {
        starktotal = totaletanolstark
        println("Stark Petrol: melhor opção = Etanol | Total (42L) = R$ $starktotal")
    }
    else {
        starktotal = totalgasolinastark
        println("Stark Petrol: melhor opção = Gasolina | Total (42L) = R$ $starktotal")
    }

    if (totaletanolwayne < totaletanolstark) {
        println("$nomedeusuario, é mais barato abastecer com Etanol no posto Wayne Oil.")
    }
    else {
        println("$nomedeusuario, é mais barato abastecer com Etanol no posto Stark Petrol.")
    }

    if (totalgasolinawayne < totalgasolinastark) {
        println("E mais barato abastecer com Gasolina no posto Wayne Oil.")
    }
    else {
        println("E mais barato abastecer com Gasolina no posto Stark Petrol.")
    }

    println("[RANKING DE PREÇOS]")

    if (waynetotal < starktotal) {
        println("1º - Wayne Oil: R$ $waynetotal")
        println("2º - Stark Petrol: R$ $starktotal")
    }
    else {
        println("1º - Stark Petrol: R$ $starktotal")
        println("2º - Wayne Oil: R$ $waynetotal")
    }
    menu()
}

fun relatoriosOperacionais() {
    val quartosocupados = 20 - listadequartos.size
    val taxaocupacao = quartosocupados.toDouble() / 20 * 100

    println("Aqui esta os relatórios completos do hotel contendo todas as Informações necessarias: ")
    println("""Total de reservas de quartos confirmadas: ${reservasdequartos.size}
        |Taxa de ocupação atual: $taxaocupacao%
        |Quantidade de hóspedes cadastrados: ${pikachucadastro.size}
        |Quantidade de eventos confirmados: ${agendadeauditorio.size}
    """.trimMargin())

    var receitahospedagem = 0.0
    var receitaeventos = 0.0

    receitahospedagem = reservasdequartos.sumOf { it.valortotal }
    receitaeventos = agendadeauditorio.sumOf { it.custoeventototal }

    println("""[RECEITA ACUMULADA]
        |Hospedagem: $receitahospedagem
        |Eventos: $receitaeventos
        |Total geral: ${receitahospedagem + receitaeventos}
    """.trimMargin())

    menu()

}

fun erro(){
    println("Por favor digite uma opcão válida.")
    menu()
}

fun erro2(){
    println("Por favor digite uma opcão válida.")
    cadastrodehospedes()
}

fun sair(){
    println("Muito obrigado e até logo, $nomedeusuario.")
    exitProcess(0)
}

//Arquitetura Modular:
//O código foi desenvolvido utilizando uma arquitetura modular,
// dividindo as funcionalidades do sistema em diferentes funções.
// Cada função é responsável por uma tarefa específica, como cadastro de hóspedes, gerenciamento de quartos,
// cálculo de valores e validação de senha. Essa organização facilita a manutenção, leitura e reutilização do código,
// além de evitar a concentração de todas as funcionalidades na função main() ou menu().