/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-demo MyNewsTest.java 2012-4-16 11:12:52 l.xue.nong$$
 */
package cn.com.rebirth.search.demo;

import java.util.Date;

import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.rebirth.commons.Page;
import cn.com.rebirth.commons.search.SearchPageRequest;
import cn.com.rebirth.commons.utils.ConvertUtils;
import cn.com.rebirth.commons.utils.DateUtils;

/**
 * The Class MyNewsTest.
 *
 * @author l.xue.nong
 */
public class MyNewsTest {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		DateConverter dc = new DateConverter();
		dc.setUseLocaleFormat(true);
		dc.setPatterns(new String[] { "yyyyMMddHHmmss" });
		org.apache.commons.beanutils.ConvertUtils.register(dc, Date.class);
		Date date = DateUtils.getCurrentDateTime();
		String str = ConvertUtils.convertObjectToObject(date, String.class);
		System.out.println(str);
		System.setProperty("rebirth.search.server.cluster", "rebirth-search-server-cluster");
		System.setProperty("default_operator", "and");
		System.setProperty("zk.zkConnect", "192.168.2.179");
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"/applicationContext.xml");
		MyNewsSumMallSearchBusiness business = new MyNewsSumMallSearchBusiness();
		MyNews object = new MyNews();
		object.setAuth("小里");
		object.setTitle("金像奖落幕：《桃姐》完胜 刘德华叶德娴称帝后");
		object.setContext("腾讯娱乐香港金像奖前方报道组(文,柏小莲)4月15日晚,第三十一届香港金像奖在香港文化中心举行.当晚最大赢家理所当然是<<桃姐>>,一共拿下了包括影帝影后、最佳导演在内的多个重量级大奖，而《龙门飞甲》几乎包圆所有技术类奖项。最佳男女配角照例慷慨赠与参演《夺命金》的两位老戏骨——卢海鹏与'清楚明白'的苏杏璇。");
		object.setId("1");
		business.createOrUpdateIndex(object);
		SearchPageRequest pageRequest = new SearchPageRequest();
		Page<MyNews> page = business.search("context:'影帝'", pageRequest);
		System.out.println("命中:" + page.getTotalItems());
		business.deleteIndex(object);
		applicationContext.close();
	}

}
