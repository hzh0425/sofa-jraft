/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.sofa.jraft.rhea.fsm.pipeline.KvPipe;

import com.alipay.sofa.jraft.rhea.fsm.dag.DagTaskGraph;
import com.alipay.sofa.jraft.rhea.fsm.pipeline.AbstractPipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author hzh (642256541@qq.com)
 */
public class DetectDependencyPipe extends AbstractPipe<BatchWrapper, BatchWrapper> {
    private final DagTaskGraph<BatchWrapper> taskGraph;

    public DetectDependencyPipe(final DagTaskGraph<BatchWrapper> taskGraph) {
        this.taskGraph = taskGraph;
    }

    @Override
    public BatchWrapper doProcess(final BatchWrapper childBatch) {
        final Set<BatchWrapper> allTasks = this.taskGraph.getAllTasks();
        final List<BatchWrapper> dependencyList = new ArrayList<>();
        // Detect dependencies
        for (final BatchWrapper parent : allTasks) {
            if (doDetect(parent, childBatch)) {
                dependencyList.add(parent);
            }
        }
        // Add to taskGraph, wait to be scheduled
        this.taskGraph.add(childBatch, dependencyList);
        return childBatch;
    }

    private boolean doDetect(final BatchWrapper parent, final BatchWrapper child) {
        return true;
    }
}
