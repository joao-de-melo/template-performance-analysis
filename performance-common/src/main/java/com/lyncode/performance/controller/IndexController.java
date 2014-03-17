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

package com.lyncode.performance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static java.util.Arrays.asList;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String indexAction (ModelMap model) {
//        System.out.println("Started");
        model.addAttribute("name", "Performance");
        return "index";
    }

    @RequestMapping("/complex")
    public String complexAction (ModelMap model) {
//        System.out.println("Started 2");

        List<String> list = asList(
                "one",
                "two",
                "three",
                "four",
                "five",
                "six",
                "seven",
                "eight",
                "nine",
                "ten"
        );

        model
                .addAttribute("name", "Performance")
                .addAttribute("list", list)
        ;

        return "complex";
    }
}
