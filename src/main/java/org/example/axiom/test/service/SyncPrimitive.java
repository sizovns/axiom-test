package org.example.axiom.test.service;

public interface SyncPrimitive {
    void acquireLock();

    void releaseLock();
}
