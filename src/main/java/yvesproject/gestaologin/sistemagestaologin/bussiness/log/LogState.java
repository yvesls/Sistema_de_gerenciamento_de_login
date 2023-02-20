package yvesproject.gestaologin.sistemagestaologin.bussiness.log;

public abstract class LogState {
	public LogState() {
	}

	public void utilizarLogCsv(Log log) {
		throw new RuntimeException("Você não realizar esta ação.");
	}

	public void utilizarLogJson(Log log) {
		throw new RuntimeException("Você não realizar esta ação.");
	}
}
