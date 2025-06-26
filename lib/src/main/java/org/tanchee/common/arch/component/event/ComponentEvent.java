package org.tanchee.common.arch.component.event;

import java.util.Map;

public class ComponentEvent {

    public static final String MESSAGE_KEY = "message";

    private final ComponentEventType type;
    private final ComponentEventStatus status;
    private final Map<String, Object> data;

    public ComponentEvent(ComponentEventType type, Map<String, Object> data) {
        this.type = type;
        this.data = data;
        this.status = ComponentEventStatus.OK;
    }

    public ComponentEvent(ComponentEventType type, ComponentEventStatus status, Map<String, Object> data) {
        this.type = type;
        this.status = status;
        this.data = data;
    }

    public ComponentEventType getType() { return type; }

    public ComponentEventStatus getStatus() { return status; }

    public Map<String, Object> getData() { return this.data; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("type: %s\n", type.name()));
        sb.append(String.format("status: %s\n", status.name()));
        sb.append("data: {\n");
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            sb.append(String.format("\t%s: %s\n", entry.getKey(), entry.getValue().toString()));
        }
        sb.append("{\n");
        return sb.toString();
    }

}
