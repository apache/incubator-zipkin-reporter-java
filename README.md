[![Gitter chat](http://img.shields.io/badge/gitter-join%20chat%20%E2%86%92-brightgreen.svg)](https://gitter.im/openzipkin/zipkin) [![Build Status](https://travis-ci.org/openzipkin/zipkin-java-reporter.svg?branch=master)](https://travis-ci.org/openzipkin/zipkin-java-reporter) [![Download](https://api.bintray.com/packages/openzipkin/maven/zipkin-java-reporter/images/download.svg) ](https://bintray.com/openzipkin/maven/zipkin-java-reporter/_latestVersion)

# zipkin-java-reporter
Shared library for reporting zipkin spans on transports including http and kafka.

# Usage
These components can be called when spans have been recorded and ready to send to zipkin.

For example, you may have a class called Recorder, which flushes on an interval. The reporter
component handles the last step.

```java
class Recorder implements Flushable {

  --snip--
  URLConnectionReporter reporter = URLConnectionReporter.builder()
                                                        .postUrl("http://localhost:9411/api/v1/spans)
                                                        .build();

  Callback callback = new IncrementSpanMetricsCallback(metrics);

  @Override
  public void flush() {
    if (pending.isEmpty()) return;
    List<Span> drained = new ArrayList<Span>(pending.size());
    pending.drainTo(drained);
    if (drained.isEmpty()) return;

    reporter.accept(drained, callback);
  }
```