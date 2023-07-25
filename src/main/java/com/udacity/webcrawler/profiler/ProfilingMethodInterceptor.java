package com.udacity.webcrawler.profiler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A method interceptor that checks whether {@link Method}s are annotated with the {@link Profiled}
 * annotation. If they are, the method interceptor records how long the method invocation took.
 */
final class ProfilingMethodInterceptor implements InvocationHandler {

  private final Clock clock;
  private final ProfilingState state;
  private  final ZonedDateTime startTime;
  private final Object delegate;


  // TODO: You will need to add more instance fields and constructor arguments to this class.
  ProfilingMethodInterceptor(Clock clock, ProfilingState state, ZonedDateTime startTime, Object delegate) {

    this.clock = Objects.requireNonNull(clock);
    this.state = Objects.requireNonNull(state);
    this.startTime = Objects.requireNonNull(startTime);
    this.delegate = Objects.requireNonNull(delegate);

  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    // TODO: This method interceptor should inspect the called method to see if it is a profiled
    //       method. For profiled methods, the interceptor should record the start time, then
    //       invoke the method using the object that is being profiled. Finally, for profiled
    //       methods, the interceptor should record how long the method call took, using the
    //       ProfilingState methods.

    Instant startTimeStamp = null;
    Instant endTimeStamp =  null;
    Object objectFromMethod;

    if(method.isAnnotationPresent(Profiled.class)) {
      clock.instant();
      startTimeStamp = clock.instant();
    }

    try{
      objectFromMethod = method.invoke(delegate, args);
    }catch (InvocationTargetException ex){
      throw ex.getTargetException();
    }finally {
      if(method.isAnnotationPresent(Profiled.class)) {
        endTimeStamp = clock.instant();
        Duration elapsed = Duration.between(startTimeStamp, endTimeStamp);
        state.record(delegate.getClass(), method, elapsed);
      }
    }



    return objectFromMethod;
  }
}
