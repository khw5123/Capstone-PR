package com.capstone.farming.dao;

import com.capstone.farming.model.ShippingArea;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class AreaFarmInfoTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    AreaFarmInfoDAO areaFarmInfoDAO;

    //db 연결확인
    @Test
    public void testConnectDB()
    {
        assertNotNull("get mongoTemplate", mongoTemplate);
    }

    @Test
    public void testFindAll()
    {
        List<ShippingArea> shippingAreaDAOList = areaFarmInfoDAO.findAll();
        List<ShippingArea> shippingAreaMongoList = mongoTemplate.findAll(ShippingArea.class, "shippingArea");
        System.out.println(shippingAreaDAOList.size()+" "+shippingAreaMongoList.size());
        assertThat(shippingAreaDAOList.size(), is(shippingAreaMongoList.size())); //갯수 같은지, 값은 일일이 비교하거나 array로 바꿔서 가능할듯
        //assertArrayEquals(shippingAreaList1.toArray(), shippingAreaList2.toArray()); //같은거 같은데 다르다 나온다. 주소가 달라서그런가
    }

    @Test
    public void testFind()
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("province").is("충청북도").andOperator(Criteria.where("city").is("제천시")));
        List<ShippingArea> shippingAreaDAOList = areaFarmInfoDAO.find("충청북도", "제천시");
        List<ShippingArea> shippingAreaMongoList = mongoTemplate.find(query, ShippingArea.class);
        System.out.println(shippingAreaDAOList.size()+" "+shippingAreaMongoList.size()); // log로 바꾸기
        assertThat(shippingAreaDAOList.size(), is(shippingAreaMongoList.size()));
    }

    @Test
    public void testListCriteria(){
        com.capstone.farming.model.Criteria cri = new com.capstone.farming.model.Criteria();
        List daolist = areaFarmInfoDAO.listCriteria(cri);
        assertThat(daolist.size(), is(10));
    }

    @Test
    public void testListCountCriteria(){
        Query query = new Query();
        com.capstone.farming.model.Criteria cri = new com.capstone.farming.model.Criteria();
        int daoCount = areaFarmInfoDAO.listCountCriteria(cri);
        int mongoCount =  (int)mongoTemplate.count(query, "shippingArea");
        System.out.println(daoCount +" "+ mongoCount);
        assertThat(daoCount, is(mongoCount));
    }

    /*
    @Test
    public void testIsInsert()
    {
        ShippingArea shippingArea = new ShippingArea();
        shippingArea.setProvince("충청북도");
        shippingArea.setCity("제천시");
        shippingArea.setItem("황기");
        shippingArea.setFamily_management_scale("12,000");
        shippingArea.setAverage_investment_cost("10,000,000");
        shippingArea.setAnnual_operating_cost("5,000,000");
        shippingArea.setAverage_income("25,000,000");
        shippingArea.setAverage_farmland_price("1000 / 150,000");
        //System.out.println(mongoTemplate.insert(shippingArea));
        //areaFarmInfoDAO.insertShippingArea(shippingArea);
    }
    */
}
