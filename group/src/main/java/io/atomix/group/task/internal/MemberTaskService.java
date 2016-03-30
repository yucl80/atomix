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
package io.atomix.group.task.internal;

import io.atomix.catalyst.util.Assert;
import io.atomix.group.LocalMember;
import io.atomix.group.task.TaskConsumer;
import io.atomix.group.task.TaskProducer;
import io.atomix.group.util.Submitter;

/**
 * Member message service.
 *
 * @author <a href="http://github.com/kuujo>Jordan Halterman</a>
 */
public class MemberTaskService extends AbstractTaskService {
  private final LocalMember member;

  public MemberTaskService(LocalMember member, Submitter submitter) {
    super(submitter);
    this.member = Assert.notNull(member, "member");
  }

  @Override
  protected <T> AbstractTaskConsumer<T> createConsumer(String name, TaskConsumer.Options options) {
    return new MemberTaskConsumer<>(name, options, this);
  }

  @Override
  protected <T> AbstractTaskProducer<T> createProducer(String name, TaskProducer.Options options) {
    return new MemberTaskProducer<>(name, options, this, member);
  }

}
