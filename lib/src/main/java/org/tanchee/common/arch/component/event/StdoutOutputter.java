package org.tanchee.common.arch.component.event;

public class StdoutOutputter implements EventOutputter {
    @Override
    public void emit(ComponentEvent event) {
        System.out.println(event.toString());
    }
}
