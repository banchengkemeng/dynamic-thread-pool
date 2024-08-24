package cn.notcoder.dtp.sdk.dynamicthreadpoolspringbootstarter.trigger.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.TimeUnit;

@Data
@NoArgsConstructor
public class AlarmCanSendStateChangeJob {

    private Runnable task;

    private Thread thread;

    public AlarmCanSendStateChangeJob(Runnable task) {
        this.task = task;
    }

    public void run(int delay) {
        if (thread == null) {
            start(delay);
            return;
        }

        if (thread.isAlive()) {
            thread.interrupt();
        }

        start(delay);
    }

    private void start(int delay) {
        thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(delay);
                task.run();
            } catch (Exception ignored) {}
        });
        thread.start();
    }
}
