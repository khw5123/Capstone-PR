package com.capstone.farming.dao;

import com.capstone.farming.model.ShippingArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;

@Repository
public class AreaFarmInfoDAO
{

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<ShippingArea> findAll()
    {
        return mongoTemplate.findAll(ShippingArea.class, "shippingArea");
    }

    public List<ShippingArea> find(String province, String city)
    {
        Query query = new Query();
        if(!province.equals("시도선택") && !city.equals("시군구선택"))
            query.addCriteria(Criteria.where("province").is(province).andOperator(Criteria.where("city").is(city)));
        return mongoTemplate.find(query, ShippingArea.class, "shippingArea");
    }

    public int listCountCriteria(com.capstone.farming.model.Criteria cri){
        Query query = new Query();
        return (int)mongoTemplate.count(query, "shippingArea");
    }


    public List<ShippingArea> listCriteria(com.capstone.farming.model.Criteria cri) {
        List<ShippingArea> shippingAreas = mongoTemplate.findAll(ShippingArea.class, "shippingArea");
        List<ShippingArea> listShippingAreas = new ArrayList<>();
        for (int i = cri.getPage(); i <= cri.getPage()*cri.getPerPageNum(); i++ ){
            listShippingAreas.add(shippingAreas.get(i));
        }

        return listShippingAreas;
    }


    public void insertShippingArea(ShippingArea shippingArea)
    {
        mongoTemplate.insert(shippingArea);
    }


}
