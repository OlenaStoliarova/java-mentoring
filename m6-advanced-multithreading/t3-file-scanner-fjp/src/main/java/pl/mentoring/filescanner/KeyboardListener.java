package pl.mentoring.filescanner;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class KeyboardListener implements Runnable {

    public static final String INTERRUPT_COMMAND = "c";

    private AtomicBoolean cancelFlag;

    public KeyboardListener(AtomicBoolean cancelFlag) {
        this.cancelFlag = cancelFlag;
    }

    @Override
    public void run() {
            Scanner in = new Scanner(System.in);

            boolean waitForInterruption = true;
            while(waitForInterruption) {
                if(INTERRUPT_COMMAND.equals(in.nextLine())){
                    cancelFlag.set(true);
                    waitForInterruption = false;
                }
            }
    }
}
