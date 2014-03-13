/**
 * Copyright 2012 Lyncode
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lyncode.performance;

import com.lyncode.performance.test.PerformanceRequester;
import org.junit.BeforeClass;
import org.junit.Test;

public class JtwigPerformanceApplicationTest {
    private PerformanceRequester requester = new PerformanceRequester();
    @BeforeClass
    public static void startServer () throws Exception {
        JtwigPerformanceApplication.main(new String[]{});
    }

    @Test
    public void test () throws Exception {
        requester.requestDuration("/");
        System.out.println(requester);
    }
}
