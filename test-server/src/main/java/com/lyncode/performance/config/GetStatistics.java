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

package com.lyncode.performance.config;

import com.lyncode.performance.PerformanceRequester;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class GetStatistics {
    public static void main(String... args) throws IOException, InterruptedException {
        String url = "";
        if (args.length == 1)
            url = args[0];
        PerformanceRequester requester = new PerformanceRequester();
        requester.requestDurationWithoutSum("/" + url);
        for (int i = 0; i < 1000; i++) {
            sleep(10);
            requester.requestDuration("/" + url);
        }
        System.out.println(requester.toString());;
    }
}
