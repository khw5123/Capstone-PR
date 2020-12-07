package com.capstone.farming.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/StatisticalAnalysis")
public class StatisticalAnalysisController {

    @RequestMapping(value = "/Chart")
    public String realTimePrice(Model model) {

        return "StatisticalAnalysis/Chart";
    }
}
