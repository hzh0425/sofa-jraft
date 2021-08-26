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
package com.alipay.sofa.jraft.rhea.fsm.pipe;

import com.alipay.sofa.jraft.rhea.storage.KVState;
import com.alipay.sofa.jraft.util.BytesUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.alipay.sofa.jraft.rhea.fsm.ParallelPipeline.KvEvent;
import java.util.List;

/**
 * @author hzh (642256541@qq.com)
 */
public class ParserKeyHandlerTest extends PipeBaseTest {

    private ParserKeyHandler handler;

    @Before
    public void init() {
        this.handler = new ParserKeyHandler();
    }

    @Test
    public void testOnEvent() {
        final RecyclableKvTask task = RecyclableKvTask.newInstance();
        // Add some keys to task
        {
            final List<KVState> kvStateList = task.getKvStateList();
            final KvEvent kvEvent = new KvEvent();
            kvEvent.setTask(task);
            kvStateList.add(mockKVState("key1"));
            kvStateList.add(mockKVState("key2"));
            kvStateList.add(mockKVState("key3"));
            this.handler.onEvent(kvEvent);
        }
        {
            final BloomFilter filter = task.getFilter();
            Assert.assertTrue(filter.contains(BytesUtil.writeUtf8("key1")));
            Assert.assertTrue(filter.contains(BytesUtil.writeUtf8("key2")));
            Assert.assertTrue(filter.contains(BytesUtil.writeUtf8("key3")));
            Assert.assertFalse(filter.contains(BytesUtil.writeUtf8("key4")));
        }
        {
            Assert.assertEquals("key1", task.getMinKey());
            Assert.assertEquals("key3", task.getMaxKey());
        }
    }
}