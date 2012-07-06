package cn.com.rebirth.search.demo;

import cn.com.rebirth.commons.search.annotation.AnalyzerType;
import cn.com.rebirth.commons.search.annotation.FieldAnalyzer;
import cn.com.rebirth.commons.search.annotation.FieldBoost;
import cn.com.rebirth.commons.search.annotation.FieldIndex;
import cn.com.rebirth.commons.search.annotation.FieldStore;
import cn.com.rebirth.commons.search.annotation.Index;
import cn.com.rebirth.commons.search.annotation.PKey;

@Index(indexName = "product", indexType = "product")
public class Product {

	private String id;

	private String name;

	@PKey
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@FieldIndex(value = FieldIndex.ANALYZED)
	@FieldStore(value = FieldStore.NO)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@FieldIndex(value = FieldIndex.ANALYZED)
	@FieldAnalyzer(toAnalyzer = AnalyzerType.WHITESPACE)
	@FieldStore(value = FieldStore.NO)
	public String getMall() {
		return mall;
	}

	public void setMall(String mall) {
		this.mall = mall;
	}

	@FieldIndex(value = FieldIndex.ANALYZED)
	@FieldStore(value = FieldStore.NO)
	@FieldBoost(boost = 0.5f)
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private String mall;

	private String desc;

}
