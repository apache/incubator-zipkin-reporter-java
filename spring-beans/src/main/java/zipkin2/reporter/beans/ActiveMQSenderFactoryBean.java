/*
 * Copyright 2016-2018 The OpenZipkin Authors
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
import zipkin2.codec.Encoding;
import zipkin2.reporter.activemq.ActiveMQSender;

/** Spring XML config does not support chained builders. This converts accordingly */
public class ActiveMQSenderFactoryBean extends AbstractFactoryBean {

  String addresses, queue;
  Encoding encoding;
  Integer connectionTimeout;
  String username, password;
  Integer messageMaxBytes;

  @Override protected ActiveMQSender createInstance() throws Exception {
    ActiveMQSender.Builder builder = ActiveMQSender.newBuilder();
    if (addresses != null) builder.addresses(addresses);
    if (encoding != null) builder.encoding(encoding);
    if (queue != null) builder.queue(queue);
    if (connectionTimeout != null) builder.connectionTimeout(connectionTimeout);
    if (username != null) builder.username(username);
    if (password != null) builder.password(password);
    if (messageMaxBytes != null) builder.messageMaxBytes(messageMaxBytes);
    return builder.build();
  }

  @Override public Class<? extends ActiveMQSender> getObjectType() {
    return ActiveMQSender.class;
  }

  @Override public boolean isSingleton() {
    return true;
  }

  @Override protected void destroyInstance(Object instance) throws Exception {
    ((ActiveMQSender) instance).close();
  }

  public void setAddresses(String addresses) {
    this.addresses = addresses;
  }

  public void setQueue(String queue) {
    this.queue = queue;
  }

  public void setEncoding(Encoding encoding) {
    this.encoding = encoding;
  }

  public void setConnectionTimeout(Integer connectionTimeout) {
    this.connectionTimeout = connectionTimeout;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setMessageMaxBytes(Integer messageMaxBytes) {
    this.messageMaxBytes = messageMaxBytes;
  }
}
