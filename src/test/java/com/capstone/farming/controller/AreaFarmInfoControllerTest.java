package com.capstone.farming.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(AreaFarmInfoController.class)
public class AreaFarmInfoControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void testAreaFarmInfoPage() throws Exception{
        mockMvc.perform(get("/AreaFarmInfo"))
                .andDo(print()) // 처리 내용 출력
                .andExpect(status().isOk()) // expects http200
                .andExpect(view().name("AreaFarmInfo"));
    }
}
