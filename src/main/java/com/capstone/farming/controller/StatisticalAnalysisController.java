package com.capstone.farming.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/StatisticalAnalysis")
public class StatisticalAnalysisController {

//    @RequestMapping(value = "/Chart")
//    public String realTimePrice(Model model) {
//
//        return "StatisticalAnalysis/Chart";
//    }

    @RequestMapping(value = "/AverageIncomeChart")
    public String averageIncomeChart(Model model) {

        return "StatisticalAnalysis/AverageIncomeChart";
    }

    @RequestMapping(value = "/CostChartAmount")
    public String costChartAmount(Model model) {

        return "StatisticalAnalysis/CostChartAmount";
    }

    @RequestMapping(value = "/CostChartAmountRegion")
    public String costChartAmountRegion(Model model) {

        return "StatisticalAnalysis/CostChartAmountRegion";
    }

    @RequestMapping(value = "/CostChartRatio")
    public String costChartRatio(Model model) {

        return "StatisticalAnalysis/CostChartRatio";
    }

    @RequestMapping(value = "/CostChartRatioRegion")
    public String costChartRatioRegion(Model model) {

        return "StatisticalAnalysis/CostChartRatioRegion";
    }

    @RequestMapping(value = "/ManagementScale")
    public String managementScale(Model model) {

        return "StatisticalAnalysis/ManagementScale";
    }

    @RequestMapping(value = "/farmlandLeaseChart")
    public String farmlandLeaseChart(Model model) {

        return "StatisticalAnalysis/farmlandLeaseChart";
    }

    @RequestMapping(value = "/farmlandTradingChart")
    public String farmlandTradingChart(Model model) {

        return "StatisticalAnalysis/farmlandTradingChart";
    }
}
