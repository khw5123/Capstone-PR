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
    //dao 전체정보가져오기
    //현재위치 클릭 시 -> ajax로 url 파라미터를 통해 해당지역 가져오기
    @GetMapping("/AreaFarmInfo")
    public String getAllAreaFarmInfo(Model model){
        //로그 추가하는 법 알기
        model.addAttribute("shippingAreaList", areaFarmInfoDAO.findAll());
        return "AreaFarmInfo/AreaFarmInfo";
    }

    @GetMapping("/AreaInfo")
    public String getAreaFarmInfo(String province,String city, Model model){
        //로그 추가하는 법 알기
        model.addAttribute("province", province);
        model.addAttribute("city", city);
        model.addAttribute("shippingAreaList", areaFarmInfoDAO.find(province, city));
        return "AreaFarmInfo/AreaFarmInfo";
    }

    @GetMapping("/AreaList")
    public String getAreaInfoList(Model model, Criteria cri){
        model.addAttribute("shippingAreaList",  areaFarmInfoDAO.listCriteria(cri));
        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(cri);

        pageMaker.setTotalCount(areaFarmInfoDAO.listCountCriteria(cri));
        model.addAttribute("pageMaker", pageMaker);

        return "AreaFarmInfo/AreaList";
    }
}
