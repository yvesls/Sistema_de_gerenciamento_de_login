package yvesproject.gestaologin.sistemagestaologin.bussiness.log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import com.mycompany.sistemaregistrolog.adaptador.FactoryLog;
import com.mycompany.sistemaregistrolog.adaptador.LogAdapter;
import com.mycompany.sistemaregistrolog.model.RegistroLog;
import com.mycompany.sistemaregistrolog.service.LogService;

public class Log {
	private static LogAdapter loggerToJson = FactoryLog.criar("json", "log");
	private static LogAdapter loggerToCSV = FactoryLog.criar("csv", "log");
	private static LogService logService = new LogService(loggerToCSV);
	private LogState state;
	private String formato;
	private Properties props;

	public Log() {
		props = new Properties();
		InputStream input;
		try {
			input = new FileInputStream("config.properties");
			props.load(input);
			formato = (String) props.getProperty("LOG");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (formato == null) {
			props.setProperty("LOG", "CSV");
			try (OutputStream output = new FileOutputStream("config.properties")) {
				props.store(output, "Configuration");
				formato = (String) props.getProperty("LOG");
				this.state = new LogCsvState(this);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (formato.equals("CSV")) {
			this.state = new LogCsvState(this);

		} else if (formato.equals("JSON")) {
			this.state = new LogJsonState(this);
		}
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

	public void registrarLog(String operacao, String nome, String tipoUsuario) throws IOException {
		String dataHora = getDataEHorario();
		String[] dataHoraList = dataHora.split(" ");
		RegistroLog registrosLog = new RegistroLog(operacao, nome, dataHoraList[0], dataHoraList[1], tipoUsuario);
		logService.escrever(registrosLog);
	}

	public void registrarErroLog(String mensagemErro, String operacao, String nome, String tipoUsuario)
			throws IOException {
		String dataHora = getDataEHorario();
		String[] dataHoraList = dataHora.split(" ");
		RegistroLog registrosLog = new RegistroLog("Ocorreu uma falha: " + mensagemErro + " ao realizar a ", operacao,
				" do contato " + nome, dataHoraList[0], dataHoraList[1], tipoUsuario);
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
		if (formato.equals("CSV")) {
			props.setProperty("LOG", "JSON");
			formato = (String) props.getProperty("LOG");
			OutputStream output;
			try {
				output = new FileOutputStream("config.properties");
				props.store(output, "Configuration");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (formato.equals("JSON")) {
			props.setProperty("LOG", "CSV");
			formato = (String) props.getProperty("LOG");
			OutputStream output;
			try {
				output = new FileOutputStream("config.properties");
				props.store(output, "Configuration");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.state = state;
	}

	public void modificarParaCsvState(Log modificarState) {
		this.state.utilizarLogCsv(modificarState);
	}

	public void modificarParaJsonState(Log modificarState) {
		this.state.utilizarLogJson(modificarState);
	}
}
