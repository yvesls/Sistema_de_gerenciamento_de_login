package yvesproject.gestaologin.sistemagestaologin.bussiness.log;

public class SingletonLogStrategy {
	private static SingletonLogStrategy singleton;

	private LogStrategy strategy;

	private SingletonLogStrategy() {
	}

	private void configurar(LogStrategy strategy) {
		this.strategy = strategy;
	}

	public static void configurarSingleton(LogStrategy strategy) {
		if (singleton == null) {
			singleton = new SingletonLogStrategy();
		}
		singleton.configurar(strategy);
	}

	public static SingletonLogStrategy getInstance() {
		return singleton;
	}

	public LogStrategy getLogStrategy() {
		return strategy;
	}
}
