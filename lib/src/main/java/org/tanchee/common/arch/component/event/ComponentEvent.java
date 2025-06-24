package org.tanchee.common.arch.component.event;

import java.util.Map;

public class ComponentEvent {
    private final ComponentEventType type;
    private final Map<String, Object> data;

    public ComponentEvent(ComponentEventType type, Map<String, Object> data) {
        this.type = type;
        this.data = data;
    }

    public ComponentEventType getType() { return type; }

    public Map<String, Object> getData() { return this.data; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("type: %s\n", type.name()));
        sb.append("data: {\n");
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            sb.append(String.format("\t%s: %s\n", entry.getKey(), entry.getValue().toString()));
        }
        sb.append("{\n");
        return sb.toString();
    }

}
