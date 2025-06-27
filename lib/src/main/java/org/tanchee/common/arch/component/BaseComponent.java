package org.tanchee.common.arch.component;

import org.tanchee.common.arch.component.event.EventOutputter;
import org.tanchee.common.arch.component.event.ComponentEvent;
import org.tanchee.common.arch.component.event.ComponentEventType;
import org.tanchee.common.arch.component.event.ComponentEventStatus;
import org.tanchee.common.arch.component.event.StdoutOutputter;


import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public abstract class BaseComponent implements InjectableComponent {

     private final String name;
     private final Set<InjectableComponent> components = new HashSet<>();
     private final AtomicBoolean initialized = new AtomicBoolean(false);
     private final AtomicBoolean ready = new AtomicBoolean(false);
     private final EventOutputter eventOutputter;

     public BaseComponent(String name) {
         this(name, new StdoutOutputter());
     }

     public BaseComponent(String name, EventOutputter eventOutputter) {
         this(name, eventOutputter, new HashMap<String, Object>());
     }

     public BaseComponent(String name, EventOutputter eventOutputter, Map<String, Object> properties) {
         this.name = name;
         this.eventOutputter = eventOutputter;
         initialize(properties);
     }

     public void injectComponent(InjectableComponent component) {
         if (component == this) {
            emit(
                buildFailedInjectionEvent(component, "cannot inject component into itself.")
            );
            return;
         }

         List<InjectableComponent> sameNamed = components.stream()
                                                .filter((existing) -> existing.getName().equals(component.getName()))
                                                .collect(Collectors.toList());
         if (sameNamed.size() > 0) {
            emit(
                buildFailedInjectionEvent(component, 
                    String.format(
                        "Not injecting, found %d components with the same name: \"%s\"", 
                        sameNamed.size(), 
                        sameNamed.get(0).getName()
                    )
                )
            );
            return;
         }

         components.add(component);

         emit(
            buildSuccessfulInjectionEvent(component)
         );
     }

    public <T extends InjectableComponent> Optional<T> getComponent(String name, Class<T> clazz) {
         List<InjectableComponent> foundComponents = components.stream()
             .filter((component) -> clazz.isInstance(clazz) && name.equals(component.getName()))
             .collect(Collectors.toList());

         int numFound = foundComponents.size();

        if (numFound == 1) {
            emit(
                new ComponentEvent(
                    ComponentEventType.WORK, 
                    ComponentEventStatus.OK,
                    ComponentEvent.msgMapf(
                        "Found component with name=%s and class=%s", 
                        name, 
                        clazz.getName()
                    )
                )
            );

            return Optional.of(clazz.cast(foundComponents.get(0)));
        }

        emit(
            new ComponentEvent(
                ComponentEventType.WORK, 
                ComponentEventStatus.ERROR,
                ComponentEvent.msgMapf(
                    "Found %d components with name=%s and class=%s", 
                    numFound, 
                    name, 
                    clazz.getName()
                )
            )
        );

        return Optional.empty();
     }

     public abstract void init(Map<String, Object> properties);

     public final synchronized void initialize(Map<String, Object> properties) {
         if (isInitialized()) return;

         // TODO: emit start init event

         init(properties);
         
         // TODO: catch failure
         
         setInitialized(true);
         setReady(true);
     }

     private void emit(ComponentEvent event) {
         eventOutputter.emit(event);
     }

     public abstract ExecutionResult execute(Map<String, Object> args);

     // GETTERS & SETTERS
     
     public String getName() { return this.name; }

     public synchronized void setInitialized(boolean initialized) {
         this.initialized.set(initialized);
     }

     public synchronized boolean isInitialized() {
         return initialized.get();
     }

     public synchronized void setReady(boolean ready) {
         this.ready.set(ready);
     }

     public synchronized boolean isReady() {
         return ready.get();
     }

     public EventOutputter getEventOutputter() { return this.eventOutputter; }


    /* EVENT GENERATION */
    
    private ComponentEvent buildSuccessfulInjectionEvent(InjectableComponent injected) {
        return buildInjectionEvent(injected, true);
    }

    private ComponentEvent buildFailedInjectionEvent(InjectableComponent injected, String detailMsg) {
        ComponentEvent event = buildFailedInjectionEvent(injected);
        event.getData().put("msg_detail", detailMsg);
        return event;
    }

    private ComponentEvent buildFailedInjectionEvent(InjectableComponent injected) {
        return buildInjectionEvent(injected, false);
    }

    private ComponentEvent buildInjectionEvent(InjectableComponent injected, boolean succesful) {
        String injectedName = 
            (injected.getName() == null || injected.getName().isBlank()) 
            ? injected.toString() 
            : injected.getName();

        String componentName = 
            (this.getName() == null || this.getName().isBlank()) 
            ? this.toString() 
            : this.getName();

        String msg = ( (succesful) ? "Succesfully injected " : "Failed to inject" )
                    + String.format("%s into %s", injectedName, componentName);

        return new ComponentEvent(
            ComponentEventType.CONFIG,
            (succesful) ? ComponentEventStatus.OK : ComponentEventStatus.ERROR,
            ComponentEvent.msgMap(msg)
        );
    }


 }
