package com.kb.myRetailRestApi.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.kb.myRetailRestApi.model.Price;
import com.kb.myRetailRestApi.repository.MyRetailRepository;

/*
 * Service Layer to invoke DB related calls through MyRetailRepository which is an implementation of MongoRepository 
 * to maintain Price related details:
 *  "current_price": {
        "value": 199,
        "currency_code": "USD"
    }
 *
 */
@Service("myretailservice")
public class MyRetailService {

	@Autowired
	private MyRetailRepository myretailRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	/*
	 * @Purpose: Method fetches all Price details
	 * @Throws: SQLException 
	 */
	public List<Price> getAllPriceDetails() throws SQLException{
		List<Price> priceList= myretailRepository.findAll();
		return priceList;
	}
	
	/*
	 * @Purpose: Method fetches Price details for the given Product Id
	 * @Param: productId
	 * @Throws: SQLException
	 */
	public Price getPriceByProductId(int productId) throws SQLException{
		Price price = myretailRepository.findByProductId(productId);
		return price; 
	}
	
	/*
	 * @Purpose: Method to add Price details to Mongo DB, for the given Price Object
	 * @Param: Price object 
	 * @Throws: SQLException
	 */
	public Price addPrice(Price price) throws SQLException{
		Price priceObj = myretailRepository.save(price);
		return priceObj;
	}
	
	/*
	 * @Purpose: Method to edit Price details for the given Price Id
	 * @Param: Price object 
	 * @Throws: SQLException
	 */
	public void updatePrice(Price price) throws SQLException{
		Query query = new Query();
		query.addCriteria(Criteria.where("productId").is(price.getProductId()));
		Update update = new Update();
		update.set("price_value", price.getPriceValue());
		update.set("currency_code", price.getCurrencyCode());
		mongoTemplate.updateFirst(query, update, Price.class);
	}
	
	/*
	 * @Purpose: Method to delete Price details for the given Price Id
	 * @Param: productId
	 * @Throws: SQLException
	 */
	public void deletePrice(int productId) throws SQLException{
		 myretailRepository.deleteByProductId(productId);
	}
	
}
