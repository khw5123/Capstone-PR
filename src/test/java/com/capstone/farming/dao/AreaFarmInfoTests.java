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

import java.util.List;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class AreaFarmInfoTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    AreaFarmInfoDAO areaFarmInfoDAO = new AreaFarmInfoDAO();

    //db 연결확인
    @Test
    public void testConnectDB()
    {
        assertNotNull("get mongoTemplate", mongoTemplate);
    }

    @Test
    public void testFindAll()
    {
        List<ShippingArea> shippingAreaList1 = areaFarmInfoDAO.findAll();
        List<ShippingArea> shippingAreaList2 = mongoTemplate.findAll(ShippingArea.class, "shippingArea");
        System.out.println(shippingAreaList1.size()+" "+shippingAreaList2.size());
        assertThat(shippingAreaList1.size(), is(shippingAreaList2.size())); //갯수 같은지, 값은 일일이 비교하거나 array로 바꿔서 가능할듯
        //assertArrayEquals(shippingAreaList1.toArray(), shippingAreaList2.toArray()); //같은거 같은데 다르다 나온다. 주소가 달라서그런가
    }

    @Test
    public void testFind()
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("province").is("충청북도").andOperator(Criteria.where("city").is("제천시")));
        List<ShippingArea> shippingAreaList1 = areaFarmInfoDAO.find(query);
        List<ShippingArea> shippingAreaList2 = mongoTemplate.find(query, ShippingArea.class);
        System.out.println(shippingAreaList1.size()+" "+shippingAreaList2.size()); // log로 바꾸기
        assertThat(shippingAreaList1.size(), is(shippingAreaList2.size()));
    }

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
}
