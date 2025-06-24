package org.tanchee.common.arch.component.event;

import java.util.concurrent.BlockingQueue;

public class QueueWatchingForwardingOutputter implements EventOutputter {

    private final BlockingQueue<ComponentEvent> queue;
    private final EventOutputter downstream;
    private final Thread worker;
    private volatile boolean running = false;

    public QueueWatchingForwardingOutputter(BlockingQueue<ComponentEvent> queue, EventOutputter downstream) {
        this.queue = queue;
        this.downstream = downstream;

        this.worker = new Thread(this::process, "QueueWatchingForwardingOutputterThread");
        this.worker.setDaemon(true);
        this.worker.start();
    }

    private void process() {
        try {
            while(running) {
                ComponentEvent event = queue.take();
                downstream.emit(event);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void stop() {
        running = false;
        worker.interrupt();
    }

    @Override
    public void emit(ComponentEvent event) {
        queue.add(event);
    }

}
