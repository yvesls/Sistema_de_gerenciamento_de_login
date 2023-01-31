package yvesproject.gestaologin.sistemagestaologin.bussiness.log;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.mycompany.sistemaregistrolog.adaptador.FactoryLog;
import com.mycompany.sistemaregistrolog.adaptador.LogAdapter;
import com.mycompany.sistemaregistrolog.model.RegistroLog;
import com.mycompany.sistemaregistrolog.service.LogService;

import yvesproject.gestaologin.sistemagestaologin.model.Usuario;

public class Log {
	private static LogAdapter loggerToJson = FactoryLog.criar("json", "log");
	private static LogAdapter loggerToCSV = FactoryLog.criar("csv", "log");
	private static LogService logService = new LogService(loggerToCSV);
	private LogState state;
	
	public Log() {
		this.state = new LogCsvState(this);
	}

	public LogAdapter getLogCsv() {
		return loggerToCSV;
	}
	
	public LogAdapter getLogJson() {
		return loggerToJson;
	}
	
	public LogService getLogService() {
		return logService;
	}
	
	public void registrarLog(String operacao, String nome, String tipoUsuario) throws IOException{
		String dataHora = getDataEHorario();
		String[] dataHoraList = dataHora.split(" ");
		RegistroLog registrosLog = new RegistroLog(operacao, nome, dataHoraList[0], dataHoraList[1], tipoUsuario);
		logService.escrever(registrosLog);
	}
	
	public void registrarErroLog(String mensagemErro, String operacao, String nome, String tipoUsuario) throws IOException {
		String dataHora = getDataEHorario();
		String[] dataHoraList = dataHora.split(" ");
		RegistroLog registrosLog = new RegistroLog("Ocorreu uma falha: "+mensagemErro + " ao realizar a ", operacao," do contato "+ nome, dataHoraList[0], dataHoraList[1], tipoUsuario);
		logService.escrever(registrosLog);
	}
	
	private String getDataEHorario() {
		LocalDateTime data = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataFormatada = data.format(formato);
		return dataFormatada;
	}

	public void setState(LogState state) {
		this.state = state;
	}
	
	public void modificarParaCsvState(Log modificarState) {
        this.state.utilizarLogCsv(modificarState);
    }

	public void modificarParaJsonState(Log modificarState) {
        this.state.utilizarLogJson(modificarState);
    }
}
