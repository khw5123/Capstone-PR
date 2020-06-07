package com.capstone.farming.controller;

import com.capstone.farming.dao.AreaFarmInfoDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
//@WebMvcTest(AreaFarmInfoController.class)
public class AreaFarmInfoControllerTest {


    @Autowired
    AreaFarmInfoDAO areaFarmInfoDAO;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAllAreaFarmInfo() throws Exception{
        mockMvc.perform(get("/AreaFarmInfo"))
                .andDo(print()) // 처리 내용 출력
                .andExpect(status().isOk()) // expects http200
                .andExpect(view().name("AreaFarmInfo"));
    }

    @Test
    public void testFindAreaFarmInfo() throws Exception{
        mockMvc.perform(get("/AreaInfo").param("province","충청남도").param("city","천안시"))
                .andDo(print()) // 처리 내용 출력
                .andExpect(status().isOk()) // expects http200
                .andExpect(view().name("AreaFarmInfo"))
                .andExpect(model().attributeExists("province"))
                .andExpect(model().attributeExists("city"))
                .andExpect(model().attributeExists("shippingAreaList"));
                //.andExpect(model().attribute("shippingAreaList", areaFarmInfoDAO.find("충청남도", "천안시")));
    }
}
