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
package io.atomix.group.internal;

import io.atomix.group.GroupMemberInfo;
import io.atomix.group.LocalMember;
import io.atomix.group.MembershipGroup;
import io.atomix.group.messaging.internal.ConnectionManager;
import io.atomix.group.messaging.internal.MemberMessageService;
import io.atomix.group.task.internal.MemberTaskService;
import io.atomix.group.util.Submitter;

import java.util.concurrent.CompletableFuture;

/**
 * Local group member.
 *
 * @author <a href="http://github.com/kuujo>Jordan Halterman</a>
 */
public class LocalGroupMember extends AbstractGroupMember implements LocalMember {
  private final MemberMessageService messages;
  private final MemberTaskService tasks;

  public LocalGroupMember(GroupMemberInfo info, MembershipGroup group, Submitter submitter, ConnectionManager connections) {
    super(info, group);
    this.messages = new MemberMessageService(this, connections);
    this.tasks = new MemberTaskService(this, submitter);
  }

  @Override
  public MemberMessageService messages() {
    return messages;
  }

  @Override
  public MemberTaskService tasks() {
    return tasks;
  }

  @Override
  public CompletableFuture<Void> leave() {
    return group.remove(memberId);
  }

  @Override
  public boolean equals(Object object) {
    return object instanceof LocalGroupMember && ((LocalGroupMember) object).memberId.equals(memberId);
  }

  @Override
  public String toString() {
    return String.format("%s[id=%s, address=%s]", getClass().getSimpleName(), memberId, address);
  }

}
