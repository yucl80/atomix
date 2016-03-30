/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package io.atomix.group.messaging.internal;

import io.atomix.catalyst.util.Assert;
import io.atomix.group.LocalMember;
import io.atomix.group.messaging.MessageConsumer;
import io.atomix.group.messaging.MessageProducer;

/**
 * Member message service.
 *
 * @author <a href="http://github.com/kuujo>Jordan Halterman</a>
 */
public class MemberMessageService extends AbstractMessageService {
  private final LocalMember member;

  public MemberMessageService(LocalMember member, ConnectionManager connections) {
    super(connections);
    this.member = Assert.notNull(member, "member");
  }

  @Override
  protected <T> AbstractMessageProducer<T> createProducer(String name, MessageProducer.Options options) {
    return new MemberMessageProducer<>(name, options, this, member);
  }

  @Override
  protected <T> AbstractMessageConsumer<T> createConsumer(String name, MessageConsumer.Options options) {
    return new MemberMessageConsumer<>(name, options, this);
  }

}
