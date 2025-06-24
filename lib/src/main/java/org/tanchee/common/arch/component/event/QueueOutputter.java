package org.tanchee.common.arch.component.event;

import java.util.concurrent.BlockingQueue;

public class QueueOutputter implements EventOutputter {
    private final BlockingQueue<ComponentEvent> queue;

    public QueueOutputter(BlockingQueue<ComponentEvent> queue) {
        this.queue = queue;
    }

    @Override
    public void emit(ComponentEvent event) {
        queue.offer(event);
    }
    
}
