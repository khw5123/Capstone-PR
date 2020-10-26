package com.capstone.farming.controller;

import com.capstone.farming.dao.AreaFarmInfoDAO;
import com.capstone.farming.model.Criteria;
import com.capstone.farming.model.PageMaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class AreaFarmInfoController {

    @Autowired
    AreaFarmInfoDAO areaFarmInfoDAO;

    @GetMapping("/AreaFarmInfo")
    public String getAreaFarmInfo(String province,String city, Model model){

        model.addAttribute("province", province);
        model.addAttribute("city", city);

        model.addAttribute("shippingAreaList", areaFarmInfoDAO.find(province, city));
        if(province.equals("시도선택"))
            return "redirect:/AreaFarmInfoList";
        else
            return "AreaFarmInfo/AreaFarmInfo";
    }

    @GetMapping("/AreaFarmInfoList")
    public String getAreaInfoList(Model model, Criteria cri){
        model.addAttribute("shippingAreaList",  areaFarmInfoDAO.listCriteria(cri));
        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);

        pageMaker.setTotalCount(areaFarmInfoDAO.listCountCriteria(cri));
        model.addAttribute("pageMaker", pageMaker);

        return "AreaFarmInfo/AreaList";
    }
}
