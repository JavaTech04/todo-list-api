package com.javatech.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TodoStatus {
    @JsonProperty("incomplete")
    INCOMPLETE,
    @JsonProperty("in_progress")
    IN_PROGRESS,
    @JsonProperty("complete")
    COMPLETE
}
