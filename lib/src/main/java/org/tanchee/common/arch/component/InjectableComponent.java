package org.tanchee.common.arch.component;

public interface InjectableComponent {
    public String getName();
    public void injectDependency(InjectableComponent component);
}
