package org.example.axiom.test.service.impl;

import org.example.axiom.test.service.SyncPrimitive;

import java.util.concurrent.Semaphore;

public class SemaphoreSyncPrimitive implements SyncPrimitive {
    private final Semaphore semaphore;

    public SemaphoreSyncPrimitive() {
        this.semaphore = new Semaphore(1);
    }

    @Override
    public void acquireLock() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void releaseLock() {
        semaphore.release();
    }
}
