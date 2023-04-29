package com.example.myapplicationagua.model


/**

Classe responsável pelo cálculo da ingestão diária de água de acordo com o peso e a idade do usuário.
 */
class CalcularIngestaoDiaria {

    // Constantes que definem a quantidade de mililitros por quilo de água recomendada para cada faixa etária
    private  val ML_Jovem = 40.0
    private  val ML_Adulto = 35.0
    private  val ML_Idoso = 30.0
    private  val ML_Mais_De_60_anos = 25.0

    // Variáveis para armazenar o resultado do cálculo da ingestão diária de água
    private var resultado_ML =0.0
    private var resultado_total_ml =0.0

    /**
     * Função que calcula a ingestão diária de água de acordo com o peso e a idade do usuário.
     * @param peso Peso do usuário em quilogramas.
     * @param idade Idade do usuário em anos.
     */
    fun CalcularTotalML(peso:Double, idade:Int){
        if (idade<=17){
            resultado_ML=peso * ML_Jovem
            resultado_total_ml = resultado_ML

        }else if (idade <= 55){
            resultado_ML= peso * ML_Adulto
            resultado_total_ml = resultado_ML
        }else if (idade <= 65){
            resultado_ML= peso * ML_Idoso
            resultado_total_ml = resultado_ML
        }else{
            resultado_ML = peso * ML_Mais_De_60_anos
            resultado_total_ml = resultado_ML
        }

    }

    /**
     * Função que retorna o resultado do cálculo da ingestão diária de água.
     * @return Resultado da ingestão diária de água em mililitros.
     */
    fun ResultadoMl(): Double{
        return resultado_total_ml
    }
}