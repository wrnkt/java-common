package org.tanchee.common.arch.component;

public record ExecutionResult(ExecutionStatus status, Map<String, Object> result) {}
