
package com.mycompany.sistemaregistrolog.adaptador;

import java.io.File;
import java.io.IOException;

import com.mycompany.sistemaregistrolog.model.RegistroLog;

public abstract class LogAdapter {

    protected File file;

    public abstract void escreve(RegistroLog... registerLog) throws IOException;

    public LogAdapter(String fileName) {
        this.file = new File(fileName);
    }

}
