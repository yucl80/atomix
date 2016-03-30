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
package io.atomix.group.election.internal;

import io.atomix.catalyst.util.Assert;
import io.atomix.catalyst.util.Listener;
import io.atomix.catalyst.util.Listeners;
import io.atomix.group.Member;
import io.atomix.group.MembershipGroup;
import io.atomix.group.election.ElectionClient;
import io.atomix.group.election.Term;

import java.util.function.Consumer;

/**
 * Group election client.
 *
 * @author <a href="http://github.com/kuujo>Jordan Halterman</a>
 */
public class GroupElectionClient implements ElectionClient {
  private final MembershipGroup group;
  private final Listeners<Term> electionListeners = new Listeners<>();
  private volatile GroupTerm term;

  public GroupElectionClient(MembershipGroup group) {
    this.group = Assert.notNull(group, "group");
  }

  @Override
  public Term term() {
    return term;
  }

  @Override
  public Listener<Term> onElection(Consumer<Term> callback) {
    Listener<Term> listener = electionListeners.add(callback);
    if (term != null) {
      listener.accept(term);
    }
    return listener;
  }

  /**
   * Called when the term changes.
   */
  public void onTerm(long term) {
    this.term = new GroupTerm(term);
  }

  /**
   * Called when a member is elected.
   */
  public void onElection(Member leader) {
    term.setLeader(leader);
    electionListeners.accept(term);
  }

}
