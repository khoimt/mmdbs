package mmdbs.lsi;

import java.io.OutputStream;
import java.io.PrintStream;

public class Logger {
    protected static PrintStream _stream = System.out;
    
    public static void log(String msg) {
        _stream.println(msg);
    }
    public static void log(int msg) {
        _stream.println(msg);
    }
    public static void log(double msg) {
        _stream.println(msg);
    }
    public static void log(float msg) {
        _stream.println(msg);
    }
    public static void log(char msg) {
        _stream.println(msg);
    }
    public static void log(int[] msg) {
        for (int i = 0; i < msg.length; i++) {
            _stream.print(msg[i] + " ");
        }
        _stream.println();
    }
}