package yvesproject.gestaologin.sistemagestaologin.bussiness.log;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.mycompany.sistemaregistrolog.model.RegistroLog;

public class JsonLogStrategy extends LogStrategy {

	@Override
	public void setLog() {
		logService.setLog(this.getLogJson());
	}
	
	@Override
	public void registrarLog(String operacao, String nome, String tipoUsuario) throws IOException{
		String dataHora = getDataEHorario();
		String[] dataHoraList = dataHora.split(" ");
		RegistroLog registrosLog = new RegistroLog(operacao, nome, dataHoraList[0], dataHoraList[1], tipoUsuario);
		logService.escrever(registrosLog);
	}
	
	@Override
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
}
