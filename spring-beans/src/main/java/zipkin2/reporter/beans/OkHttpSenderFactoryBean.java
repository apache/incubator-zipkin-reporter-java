/**
 * Copyright 2016-2017 The OpenZipkin Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package zipkin2.reporter.beans;

import org.springframework.beans.factory.config.AbstractFactoryBean;
import zipkin2.reporter.okhttp3.OkHttpSender;

/** Spring XML config does not support chained builders. This converts accordingly */
public class OkHttpSenderFactoryBean extends AbstractFactoryBean {

  String endpoint;
  Integer maxRequests;
  Boolean compressionEnabled;
  Integer messageMaxBytes;

  @Override protected OkHttpSender createInstance() throws Exception {
    OkHttpSender.Builder builder = OkHttpSender.newBuilder();
    if (endpoint != null) builder.endpoint(endpoint);
    if (maxRequests != null) builder.maxRequests(maxRequests);
    if (compressionEnabled != null) builder.compressionEnabled(compressionEnabled);
    if (messageMaxBytes != null) builder.messageMaxBytes(messageMaxBytes);
    return builder.build();
  }

  @Override public Class<? extends OkHttpSender> getObjectType() {
    return OkHttpSender.class;
  }

  @Override public boolean isSingleton() {
    return true;
  }

  @Override protected void destroyInstance(Object instance) throws Exception {
    ((OkHttpSender) instance).close();
  }

  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }

  public void setMaxRequests(Integer maxRequests) {
    this.maxRequests = maxRequests;
  }

  public void setCompressionEnabled(Boolean compressionEnabled) {
    this.compressionEnabled = compressionEnabled;
  }

  public void setMessageMaxBytes(Integer messageMaxBytes) {
    this.messageMaxBytes = messageMaxBytes;
  }
}
