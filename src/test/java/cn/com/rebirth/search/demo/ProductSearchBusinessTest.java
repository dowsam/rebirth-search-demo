package cn.com.rebirth.search.demo;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.rebirth.commons.Page;
import cn.com.rebirth.commons.search.SearchPageRequest;

/**
 * 
 * @author hzh
 * 商品搜索测试
 *
 */
public class ProductSearchBusinessTest {

	static ClassPathXmlApplicationContext applicationContext;

	static ProductSearchBusiness business = new ProductSearchBusiness();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.setProperty("rebirth.search.server.cluster", "rebirth-search-server-cluster");
		System.setProperty("default_operator", "and");
		applicationContext = new ClassPathXmlApplicationContext("/applicationContext.xml");
		business = new ProductSearchBusiness();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		applicationContext.close();
	}

	@Test
	public void testCreateOrUpdateIndex() {
		Product a = new Product();
		a.setId("10");
		a.setName("iphone 4s智能手机");
		a.setMall("京东 当当 亚马逊 苏宁易购");
		a.setDesc("联通合约版");
		business.createOrUpdateIndex(a);
		Product b = new Product();
		b.setId("20");
		b.setName("三星/Samsung i900手机(黑)");
		b.setMall("苏宁易购");
		b.setDesc("Andriod2.3");
		business.createOrUpdateIndex(b);
		b.setDesc("Andriod3.1");
		business.createOrUpdateIndex(b);
	}

	@Test
	public void testSearchStringPageRequest() {
		SearchPageRequest pageRequest = new SearchPageRequest();
		Page<Product> page = business.search("name:'智能'", pageRequest);
		Assert.assertEquals(1, page.getTotalItems());
		page = business.search("mall:'苏宁易购'", pageRequest);
		Assert.assertEquals(2, page.getTotalItems());
		page = business.search("mall:'当当'", pageRequest);
		Assert.assertEquals(1, page.getTotalItems());
	}

}
