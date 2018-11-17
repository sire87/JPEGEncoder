package at.aau.itec.emmt.jpeg.enc;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JPEGEncoderExecutor {

    public static final int NUM_THREADS = 8;

    public static void main(String[] args) {
        String[] images = new String[]{
                "lena_113x113",
                "lena_122x123",
                "lena_128x128",
                "lena_500x500",
                "lena_512x512",
        };
        String[] subsamplingModes = new String[]{
                "4:4:4",
                "4:2:2",
                "4:2:0",
        };

        String[] qualities = new String[]{
                "100",
                "80",
                "50",
                "20",
                "10"
        };

        List<String[]> commands = new ArrayList<>();

        for (String quality : qualities) {
            for (String image : images) {
                for (String subsamplingMode : subsamplingModes) {
                    String oImage = image + "_" + subsamplingMode.replace(":", "") + "_" + quality + ".jpg";
                    String[] command = new String[]{
                            "-s", subsamplingMode,
                            "-q", quality,
                            "-o", "encoded/" + oImage,
                            "data/" + image + ".png"
                    };
                    commands.add(command);
                }
            }
        }

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        for (String[] command : commands) {
            Runnable worker = new WorkerThread(command);
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("\nFinished all threads");
    }

    public static class WorkerThread implements Runnable {

        private String[] command;

        public WorkerThread(String[] s) {
            this.command = s;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " Start. Command = " + Arrays.toString(command));
            File f = new File(command[command.length - 2]);
            if (!f.exists()) {
                processCommand();
            }
            System.out.println(Thread.currentThread().getName() + " End.");
        }

        private void processCommand() {
            JPEGEncoder.main(command);
        }

        @Override
        public String toString() {
            return Arrays.toString(command);
        }
    }
}
