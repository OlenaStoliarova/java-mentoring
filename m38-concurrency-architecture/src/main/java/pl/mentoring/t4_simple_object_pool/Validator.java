package pl.mentoring.t4_simple_object_pool;

public interface Validator {

    /**
     * Checks whether the object is valid.
     *
     * @param object the object to check.
     *
     * @return true if the object is valid, else - false.
     */
    boolean isValid(Object object);

    /**
     * Performs any cleanup activities before discarding the object.
     *
     * @param object the object to invalidate
     */
    void invalidate(Object object);
}
