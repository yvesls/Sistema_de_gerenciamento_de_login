package yvesproject.gestaologin.sistemagestaologin.bussiness.observer;

import java.awt.HeadlessException;
import java.sql.SQLException;

public interface Observer {
	void update(String status) throws HeadlessException, SQLException;
}