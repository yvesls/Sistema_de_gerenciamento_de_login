package yvesproject.gestaologin.sistemagestaologin.bussiness.log;

public class LogJsonState extends LogState {

	public LogJsonState(Log log) {
		log.getLogService().setLog(log.getLogJson());
	}
	
	@Override
	public void utilizarLogCsv(Log log) {
		log.setState(new LogCsvState(log));
	}
}
