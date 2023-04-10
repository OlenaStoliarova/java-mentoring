package pl.mentoring.t4_simple_object_pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Pool that block when it has not any items or if full
 */
public class BlockingObjectPool {

    private static final Logger logger = LoggerFactory.getLogger(BlockingObjectPool.class);

    private final BlockingQueue<Object> pool;

    private final ObjectFactory objectFactory;

    private final Validator validator;

    private volatile boolean shutdownCalled;

    /**
     * Creates filled pool of passed size
     *
     * @param size          of pool
     * @param objectFactory Object factory for creating new objects to be used in an object pool
     * @param validator     Represents the functionality to
     *                      * validate an object of the pool
     *                      * and to subsequently perform cleanup activities.
     */
    public BlockingObjectPool(int size, ObjectFactory objectFactory, Validator validator) {
        this.objectFactory = objectFactory;
        this.validator = validator;

        pool = new LinkedBlockingQueue<>(size);
        for (int i = 0; i < size; i++) {
            Object aNew = objectFactory.createNew();
            pool.add(aNew);
            logger.info("Initial object pool entry {} - {} ", i, aNew);
        }

        shutdownCalled = false;
    }

    /**
     * Gets object from pool or blocks if pool is empty
     *
     * @return object from pool
     */
    public Object get() {
        shutdownCheck();

        Object poolEntry = null;

        try {
            poolEntry = pool.take();
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        logger.info("Thread {} took object from pool {} ", Thread.currentThread().getId(), poolEntry);
        return poolEntry;
    }

    /**
     * Puts object to pool or blocks if pool is full
     *
     * @param object to be put back to pool
     */
    public void put(Object object) {
        shutdownCheck();

        Object objectToReturnToPool;
        if (validator.isValid(object)) {
            objectToReturnToPool = object;
        } else {
            objectToReturnToPool = objectFactory.createNew();
        }

        try {
            pool.put(objectToReturnToPool);
            logger.info("Thread {} returned object to pool {} ", Thread.currentThread().getId(), objectToReturnToPool);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        shutdownCalled = true;

        int i = 0;
        for (Object poolEntry : pool) {
            logger.info("On-shutdown object pool entry {} - {} ", i, poolEntry);
            validator.invalidate(poolEntry);
            i++;
        }
    }

    private void shutdownCheck() {
        if (shutdownCalled) {
            logger.error("Thread {}. Object pool is already shutdown", Thread.currentThread().getId());
            throw new IllegalStateException("Object pool is already shutdown");
        }
    }
}
