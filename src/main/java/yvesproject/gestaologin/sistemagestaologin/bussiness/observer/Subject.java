package yvesproject.gestaologin.sistemagestaologin.bussiness.observer;

import java.awt.HeadlessException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Subject {
	private List<Observer> observers = new ArrayList<Observer>();

	public void add(Observer observer) {
		observers.add(observer);
	}

	public void notifyObservers(String status) throws HeadlessException, SQLException {
		for (Observer o : observers) {
			o.update(status);
		}
	}
}

