package yvesproject.gestaologin.sistemagestaologin;

import java.awt.EventQueue;

import com.mycompany.sistemaregistrolog.adaptador.FactoryLog;
import com.mycompany.sistemaregistrolog.adaptador.LogAdapter;
import com.mycompany.sistemaregistrolog.model.RegistroLog;
import com.mycompany.sistemaregistrolog.service.LogService;
import yvesproject.gestaologin.sistemagestaologin.presenter.LoginPresenter;
import yvesproject.gestaologin.sistemagestaologin.view.LogarView;

public class App {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogarView window = new LogarView();
					new LoginPresenter(window);

		            /*RegistroLog[] registrosLog = new RegistroLog[]{
		                new RegistroLog("FALHA", "Falha ao gravar o registro do funcionário", "admin1"),
		                new RegistroLog("OPERAÇÃO", "Saldo calculado com sucesso", "balcaoA2"),
		                new RegistroLog("INFORMAÇÃO", "Download concluído", "tiuser2")
		            };

		            LogAdapter loggerToCSV = FactoryLog.criar("csv", "log");
		            LogAdapter loggerToJSON = FactoryLog.criar("json", "log");
		            
		            
		            LogService logService = new LogService(loggerToCSV);
		            logService.escrever(registrosLog);


		            logService.setLog(loggerToJSON);
		            logService.escrever(registrosLog);

		            logService.setLog(loggerToCSV);
		            logService.escrever(registrosLog);*/

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
