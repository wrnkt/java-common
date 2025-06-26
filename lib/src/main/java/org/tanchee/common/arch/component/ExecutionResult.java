package org.tanchee.common.arch.component;

import java.util.Map;

public record ExecutionResult(ExecutionStatus status, Map<String, Object> result) {}
