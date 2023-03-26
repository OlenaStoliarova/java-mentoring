package pl.mentoring.filescanner;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

@Command(name = "dirstat")
public class DirScanner implements Runnable {

    @Parameters(index = "0", description = "the directory to scan")
    private File dir;

    @Option(names = {"?", "-h", "--help"}, usageHelp = true, description = "display this help message and exit")
    boolean usageHelpRequested;

    @Override
    public void run() {
        if (dir.isDirectory()) {
            System.out.println("Detailed statistics for " + dir.getPath());

            AtomicBoolean canceledByUser = new AtomicBoolean(false);
            Thread keyboardListenerThread = new Thread(new KeyboardListener(canceledByUser));
            keyboardListenerThread.setDaemon(true);
            keyboardListenerThread.start();

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future<DirStatistics> statistics = executorService.submit(new DirStatisticsProvider(dir));

            ProgressBar pb = new ProgressBar();
            pb.start();

            while (!statistics.isDone()) {
                if (canceledByUser.get()) {
                    statistics.cancel(true);
                    pb.showProgress = false;
                } else {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            try {
                if (statistics.isCancelled()) {
                    System.out.println("\ndir scan is cancelled by user");
                } else {
                    System.out.print("\r" + statistics.get() + "\n");
                }
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        } else if (dir.isFile()) {
            System.out.println("Please provide directory, not a file");
        } else {
            System.out.println("Please provide valid directory path");
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new DirScanner()).execute(args);
        System.exit(exitCode);
    }

    private class ProgressBar extends Thread {
        boolean showProgress = true;

        public void run() {
            System.out.print("work in progress");
            while (showProgress) {
                System.out.print(".");
                try {
                    Thread.sleep(2000);
                } catch (Exception ignored) {
                }
            }
        }
    }
}
