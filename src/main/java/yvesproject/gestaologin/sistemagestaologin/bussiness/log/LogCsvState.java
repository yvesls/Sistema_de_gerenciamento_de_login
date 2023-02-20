package yvesproject.gestaologin.sistemagestaologin.bussiness.log;

public class LogCsvState extends LogState {
	
	public LogCsvState(Log log) {
		log.getLogService().setLog(log.getLogCsv());
	}
	
	@Override
	public void utilizarLogJson(Log log) {
		log.setState(new LogJsonState(log));
	}
}
