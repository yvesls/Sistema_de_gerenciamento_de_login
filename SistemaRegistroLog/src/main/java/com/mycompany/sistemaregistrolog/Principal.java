/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sistemaregistrolog;

import com.mycompany.sistemaregistrolog.adaptador.FactoryLog;
import com.mycompany.sistemaregistrolog.adaptador.LogAdapter;
import com.mycompany.sistemaregistrolog.model.RegistroLog;
import com.mycompany.sistemaregistrolog.service.LogService;

/**
 *
 * @author Aluno
 */
public class Principal {
    public static  void main(String args[]){
    	try {

            RegistroLog[] registrosLog = new RegistroLog[]{
                new RegistroLog("FALHA", "Falha ao gravar o registro do funcionário", "admin1"),
                new RegistroLog("OPERAÇÃO", "Saldo calculado com sucesso", "balcaoA2"),
                new RegistroLog("INFORMAÇÃO", "Download concluído", "tiuser2")
            };

            LogAdapter loggerToCSV = FactoryLog.criar("csv", "log");
            LogAdapter loggerToJSON = FactoryLog.criar("json", "log");
            
            
            LogService logService = new LogService(loggerToCSV);
            logService.escrever(registrosLog);


            logService.setLog(loggerToJSON);
            logService.escrever(registrosLog);

            logService.setLog(loggerToCSV);
            logService.escrever(registrosLog);

        } catch (Exception e) {
            System.out.println("Falha:\n" + e.getMessage());
        }

    }
}
