package mmdbs.lsi;

import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.JTextArea;

public class LSILogger {
    protected static PrintStream _stream = System.out;
    protected static JTextArea _outputPanel = null;

    public static void setOutputPanel(JTextArea outputPanel) {
        _outputPanel = outputPanel;
    }

    
    public static void log(String msg) {
        _stream.println(msg);
        if (_outputPanel != null) {
            _outputPanel.setText(_outputPanel.getText() + "\n" + msg);
        }
    }
    public static void log(int msg) {
        _stream.println(msg);
        if (_outputPanel != null) {
            _outputPanel.setText(_outputPanel.getText() + "\n" + msg);
        }
    }
    public static void log(double msg) {
        _stream.println(msg);
        if (_outputPanel != null) {
            _outputPanel.setText(_outputPanel.getText() + "\n" + msg);
        }
    }
    public static void log(float msg) {
        _stream.println(msg);
        if (_outputPanel != null) {
            _outputPanel.setText(_outputPanel.getText() + "\n" + msg);
        }
    }
    public static void log(char msg) {
        _stream.println(msg);
        if (_outputPanel != null) {
            _outputPanel.setText(_outputPanel.getText() + "\n" + msg);
        }
    }
    public static void log(int[] msg) {
        StringBuilder str = new StringBuilder();
        str.append('[');
        for (int i = 0; i < msg.length; i++) {
            str.append(msg[i] + " ");
        }
        str.append("]\n");
        log(str.toString());
    }
}