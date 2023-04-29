package com.example.myapplicationagua.model

class CalcularIngestaoDiaria {

    private  val ML_Jovem = 40.0
    private  val ML_Adulto = 35.0
    private  val ML_Idoso = 30.0
    private  val ML_Mais_De_60_anos = 25.0

    private var resultado_ML =0.0
    private var resultado_total_ml =0.0

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

    fun ResultadoMl(): Double{
        return resultado_total_ml
    }
}