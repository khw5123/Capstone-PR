package com.capstone.farming.dao;

import com.capstone.farming.model.ShippingArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

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

    public List<ShippingArea> find(Query query)
    {
        return mongoTemplate.find(query, ShippingArea.class, "shippingArea");
    }

    public void insertShippingArea(ShippingArea shippingArea)
    {
        mongoTemplate.insert(shippingArea);
    }


}
