
package com.mycompany.sistemaregistrolog.adaptador;

import java.io.FileWriter;
import java.io.IOException;

import com.mycompany.sistemaregistrolog.model.RegistroLog;
import com.opencsv.CSVWriter;


public class LogCsvAdapter extends LogAdapter {
	private CSVWriter csvWriter;
	 public LogCsvAdapter(String fileName) {
		super(fileName+".csv");
		// TODO Auto-generated constructor stub
	}




	    @Override
	    public void escreve(RegistroLog... registrosLog) throws IOException {

	        csvWriter = new CSVWriter(new FileWriter(file, true));

	        adicionaCabecalho(csvWriter);

	        for (RegistroLog registroLog : registrosLog) {
	            String[] logString = {
	                    registroLog.getOperacao(),
	                    registroLog.getNome(),
	                    registroLog.getData(),
	                    registroLog.getHora(),
	                    registroLog.getTipoUsuario()
	            };
	            csvWriter.writeNext(logString);
	        }
	        csvWriter.close();
	    }

	    private void adicionaCabecalho(CSVWriter csvWriter) {
	        if (file.length() == 0) {
	            csvWriter.writeNext(
	                    new String[]{"tipo", "informacao", "usuario", "dataHora"},
	                    false
	            );
	        }
	    }

}
