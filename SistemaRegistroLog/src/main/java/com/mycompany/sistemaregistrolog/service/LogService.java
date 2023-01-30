
package com.mycompany.sistemaregistrolog.service;

import java.io.IOException;

import com.mycompany.sistemaregistrolog.adaptador.LogAdapter;
import com.mycompany.sistemaregistrolog.model.RegistroLog;

public class LogService {
	 private LogAdapter log;

	    public LogService(LogAdapter log) {
	        this.log = log;
	    }

	    public void setLog(LogAdapter log) {
	        this.log = log;
	    }

	    public void escrever(RegistroLog... registerLog) throws IOException {
	        this.log.escreve(registerLog);
	    }
}
