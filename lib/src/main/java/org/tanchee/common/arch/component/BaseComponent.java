 package org.tanchee.common.arch.component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public abstract class BaseComponent implements InjectableComponent {

     private final String name;
     private final Set<InjectableComponent> components = new HashSet<>();

     // NOTE: may not need
     private final AtomicBoolean initialized = new AtomicBoolean(false);

     private final AtomicBoolean ready = new AtomicBoolean(false);

     public BaseComponent(String name) {
         this.name = name;
     }

     public void injectComponent(InjectableComponent component) {
         if (component == this) {
             throw new IllegalArgumentException("Cannot inject this into itself.");
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

 }

 enum ExecutionStatus {
     OK, BAD
 }

 record ExecutionResult(ExecutionStatus status, Map<String, Object> result) {}
