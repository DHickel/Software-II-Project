package com.dhickel.utility;

import java.security.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * The functional interface for timestamp lambdas
 *
 * @param <T> the type parameter
 * @param <U> the type parameter
 * @param <R> the type parameter
 */
@FunctionalInterface
public interface TimeStamp<T,U, R> {
   /**
    * Apply r.
    *
    * @param t the t
    * @param u the u
    * @return the r
    */
   R apply(T t, U u);
};

