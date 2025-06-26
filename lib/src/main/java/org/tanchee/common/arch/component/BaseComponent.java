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
             String msg = "Cannot inject component into itself.";

             emit(new ComponentEvent(
                 ComponentEventType.INITIALIZATION, 
                 ComponentEventStatus.ERROR,
                 Map.of(ComponentEvent.MESSAGE_KEY, msg)
             ));
         }
         List<InjectableComponent> sameNamed = components.stream()
                                                .filter((existing) -> existing.getName().equals(component.getName()))
                                                .collect(Collectors.toList());
         if (sameNamed.size() > 0) {
             String commaedNames = sameNamed.stream()
                                    .map((c) -> c.getName())
                                    .collect(Collectors.joining(", "));
             String diagnosticMsg = String.format("Can't add component named \"%s\". Found %d components with the same name: %s", sameNamed.size(), commaedNames);
             // TODO: emit issue
         }
         components.add(this);
         // TODO: emit injection event
     }

     public <T extends InjectableComponent> Optional<T> getComponent(String name, Class<T> clazz) {
         List<InjectableComponent> foundComponents = components.stream()
             .filter((component) -> clazz.isInstance(clazz) && name.equals(component.getName()))
             .collect(Collectors.toList());

         int numFound = foundComponents.size();

         if (numFound == 1) {
             // TODO: emit found component
             return Optional.of(clazz.cast(foundComponents.get(0)));
         }

        String diagnosticMsg = String.format(
             "%d components found with name=%s and class=%s", 
             numFound,
             name, 
             clazz.getName()
         );

        // TODO: emit issue
         //
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

 }
