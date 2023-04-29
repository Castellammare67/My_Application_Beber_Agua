/**

Esta é a classe principal da aplicação que gerencia a tela de cálculo de ingestão diária de água
e a configuração de alarmes.
 */

package com.example.myapplicationagua

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myapplicationagua.model.CalcularIngestaoDiaria
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    //Declaração de variáveis de layout
    private lateinit var edit_peso : EditText
    private lateinit var edit_idade: EditText
    private lateinit var btn_calcular:Button
    private lateinit var txt_resultado_ml:TextView
    private lateinit var ic_redefinir_dados:ImageView
    private lateinit var bt_lembrete:Button
    private lateinit var bt_alarme:Button
    private lateinit var txt_hora:TextView
    private lateinit var txt_minutos:TextView

    //Declaração de variáveis auxiliares
    private lateinit var calcularIngestaoDiaria:CalcularIngestaoDiaria
    private var resultadoMl = 0.0
    lateinit var timePikerDialog: TimePickerDialog
    lateinit var calendario: Calendar
    var horaAtual =0
    var minutoAtual =0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Inicialização dos componentes de layout
        IniciarComponentes()

        //Inicialização do objeto de cálculo de ingestão diária
        calcularIngestaoDiaria = CalcularIngestaoDiaria()

        //Ação do botão de calcular ingestão diária
        btn_calcular.setOnClickListener(){
            if (edit_peso.text.toString().isEmpty()){
                Toast.makeText(this,R.string.toast_peso,Toast.LENGTH_SHORT).show()

            }else if (edit_idade.text.toString().isEmpty()){
                Toast.makeText(this,R.string.toast_idade,Toast.LENGTH_SHORT).show()

            }else{
                val peso = edit_peso.text.toString().toDouble()
                val idade = edit_idade.text.toString().toInt()
                calcularIngestaoDiaria.CalcularTotalML(peso,idade)
                resultadoMl = calcularIngestaoDiaria.ResultadoMl()
                val formatar = NumberFormat.getNumberInstance(Locale("pt","BR"))
                formatar.isGroupingUsed = false
                txt_resultado_ml.text = formatar.format(resultadoMl)+ ""+"ml"
            }
        }

        //Ação do ícone de redefinição dos dados
        ic_redefinir_dados.setOnClickListener(){

            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Redefinir Dados")
                .setMessage("Deseja excluir todos os dados existentes ?")
                .setPositiveButton("Ok", {dialogInterface, i ->
                    edit_peso.setText("")
                    edit_idade.setText("")
                    txt_resultado_ml.setText("")
                })

            alertDialog.setNegativeButton("Cancelar", {dialogInterface, i ->

            })

            val dialog = alertDialog.create()
            dialog.show()


        }

        //Ação do botão de definição de lembrete
        bt_lembrete.setOnClickListener(){

            calendario = Calendar.getInstance()
            horaAtual = calendario.get(Calendar.HOUR_OF_DAY)
            minutoAtual = calendario.get(Calendar.MINUTE)
            timePikerDialog = TimePickerDialog(this,{timePiker:TimePicker,hourOfDay:Int,minute:Int ->
                txt_hora.text = String.format("%02d",hourOfDay)
                txt_minutos.text= String.format("%02d",minute)
            },horaAtual,minutoAtual,true)
            timePikerDialog.show()

        }


        /**

        Este método define o comportamento do botão de definir alarme.
        Quando o botão é clicado, ele verifica se os campos de hora e minutos não estão vazios.
        Se não estiverem vazios, ele cria um intent para definir um alarme com a hora e minutos inseridos,
        bem como uma mensagem de texto pré-definida para o alarme.
        Em seguida, verifica se há alguma activity disponível para lidar com o intent.
        Se houver, ele inicia a activity para definir o alarme.
        Caso contrário, um erro é registrado no Log.
         */
        bt_alarme.setOnClickListener(){

            if (!txt_hora.text.toString().isEmpty() && !txt_minutos.text.toString().isEmpty()){
                var intent = Intent(AlarmClock.ACTION_SET_ALARM)
                intent.putExtra(AlarmClock.EXTRA_HOUR, txt_hora.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MINUTES,  txt_minutos.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MESSAGE, getString(R.string.alarme_msg))

                if (intent.resolveActivity(packageManager) != null){
                    Log.d("ALARM", "Intent resolved successfully")
                    startActivity(intent)
                } else {
                    Log.e("ALARM", "No activity found to handle intent")
                }


            }
        }



    }



    /**

    Este método inicializa os componentes da interface do usuário.
    Ele encontra cada componente por sua id e atribui a uma variável correspondente.
     */
    private fun IniciarComponentes(){
        edit_peso = findViewById(R.id.edit_peso)
        edit_idade = findViewById(R.id.edit_idade)
        btn_calcular = findViewById(R.id.btn_calcular)
        txt_resultado_ml = findViewById(R.id.txt_resultado_media_diaria)
        ic_redefinir_dados = findViewById(R.id.refresh_icon)

        bt_lembrete = findViewById(R.id.btn_definir_lembrete)
        bt_alarme = findViewById(R.id.btn_definir_alarme_no_android)
        txt_hora = findViewById(R.id.txt_hora)
        txt_minutos = findViewById(R.id.txt_minutos)

    }
}