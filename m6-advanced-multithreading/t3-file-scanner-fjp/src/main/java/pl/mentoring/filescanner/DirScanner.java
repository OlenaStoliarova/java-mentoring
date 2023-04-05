package pl.mentoring.filescanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(DirScanner.class);
    @Parameters(index = "0", description = "the directory to scan")
    private File dir;

    @Option(names = {"?", "-h", "--help"}, usageHelp = true, description = "display this help message and exit")
    boolean usageHelpRequested;

    @Override
    public void run() {
        if (dir.isDirectory()) {
            printDetailedDirStatistics();
        } else if (dir.isFile()) {
            logger.error("Please provide directory, not a file");
        } else {
            logger.error("Please provide valid directory path");
        }
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new DirScanner()).execute(args);
        System.exit(exitCode);
    }

    private void printDetailedDirStatistics() {
        logger.info("Detailed statistics for {}\n", dir.getPath());

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
                putCurrentThreadToSleep(300);
            }
        }

        try {
            if (statistics.isCancelled()) {
                logger.warn("\ndir scan is cancelled by user");
            } else {
                logger.info("\r{}\n", statistics.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
        }
    }

    private class ProgressBar extends Thread {
        boolean showProgress = true;

        @Override
        public void run() {
            logger.info("work in progress");
            while (showProgress) {
                logger.info(".");
                putCurrentThreadToSleep(2000);
            }
        }
    }

    private void putCurrentThreadToSleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            logger.warn("Interrupted!", e);
            // Restore interrupted state...
            Thread.currentThread().interrupt();
        }
    }
}
