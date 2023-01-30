package com.mycompany.sistemaregistrolog.adaptador;

public class FactoryLog {
	public static LogAdapter criar(String tipoLog, String fileName) {
        if (tipoLog.equalsIgnoreCase("csv")) {
            return new LogCsvAdapter(fileName);
        } else if (tipoLog.equalsIgnoreCase("json")) {
            return new LogJsonAdapter(fileName);
        }
        throw new RuntimeException("Informe um tipo válido de log: csv ou json");
    }
}
