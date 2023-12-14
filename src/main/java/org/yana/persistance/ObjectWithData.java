package org.yana.persistance;

import lombok.Data;

import java.util.UUID;

@Data
public abstract class ObjectWithData {
    private UUID id;
}