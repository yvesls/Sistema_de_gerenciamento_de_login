package yvesproject.gestaologin.sistemagestaologin.bussiness.log;

import java.io.IOException;

import com.mycompany.sistemaregistrolog.adaptador.FactoryLog;
import com.mycompany.sistemaregistrolog.adaptador.LogAdapter;
import com.mycompany.sistemaregistrolog.service.LogService;

public abstract class LogStrategy {
	private static LogAdapter loggerToJson = FactoryLog.criar("json", "log");
	private static LogAdapter loggerToCSV = FactoryLog.criar("csv", "log");
	protected static LogService logService = new LogService(loggerToCSV);
	
	protected void setLog() {
	}
	
	protected LogAdapter getLogCsv() {
		return loggerToCSV;
	}
	
	protected LogAdapter getLogJson() {
		return loggerToJson;
	}
	
	abstract void registrarLog(String Operacao, String nome, String tipoUsuario) throws IOException;
	
	abstract void registrarErroLog(String mensagemErro, String Operacao, String nome, String tipoUsuario) throws IOException;
}
