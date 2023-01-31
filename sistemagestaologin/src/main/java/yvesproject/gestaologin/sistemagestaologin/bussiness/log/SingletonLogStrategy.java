package yvesproject.gestaologin.sistemagestaologin.bussiness.log;

public class SingletonLogStrategy {
	private static SingletonLogStrategy singleton;

	private Log log;

	private SingletonLogStrategy() {
	}

	private void configurar(Log log) {
		this.log = log;
	}

	public static void configurarSingleton() {
		if (singleton == null) {
			singleton = new SingletonLogStrategy();
		}
		singleton.configurar(new Log());
	}

	public static SingletonLogStrategy getInstance() {
		return singleton;
	}

	public Log getLog() {
		return log;
	}
}
