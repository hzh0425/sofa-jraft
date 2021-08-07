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

import com.alipay.sofa.jraft.rhea.storage.KVState;
import com.alipay.sofa.jraft.rhea.util.BloomFilter;

import java.util.List;

/**
 * @author hzh (642256541@qq.com)
 */
public class BatchWrapper {

    private final List<KVState>       kvStateList;
    private final BloomFilter<byte[]> filter;

    public BatchWrapper(final List<KVState> kvStateList, BloomFilter<byte[]> filter) {
        this.kvStateList = kvStateList;
        this.filter = filter;
    }

    public BloomFilter<byte[]> getFilter() {
        return filter;
    }

    public List<KVState> getKvStateList() {
        return kvStateList;
    }
}
