/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-demo MyNews.java 2012-4-16 11:05:31 l.xue.nong$$
 */
package cn.com.rebirth.search.demo;

import cn.com.rebirth.commons.search.annotation.FieldBoost;
import cn.com.rebirth.commons.search.annotation.FieldIndex;
import cn.com.rebirth.commons.search.annotation.FieldStore;
import cn.com.rebirth.commons.search.annotation.Index;
import cn.com.rebirth.commons.search.annotation.PKey;

/**
 * The Class MyNews.
 *
 * @author l.xue.nong
 */
@Index(indexName = "news", indexType = "news")
public class MyNews {

	/** The id. */
	private String id;

	/** The title. */
	private String title;

	/** The context. */
	private String context;

	/** The auth. */
	private String auth;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@PKey
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	@FieldIndex(value = FieldIndex.ANALYZED)
	@FieldStore(value = FieldStore.YES)
	@FieldBoost(boost = 3.0f)
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the context.
	 *
	 * @return the context
	 */
	@FieldIndex(value = FieldIndex.ANALYZED)
	@FieldStore(value = FieldStore.YES)
	public String getContext() {
		return context;
	}

	/**
	 * Sets the context.
	 *
	 * @param context the new context
	 */
	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * Gets the auth.
	 *
	 * @return the auth
	 */
	public String getAuth() {
		return auth;
	}

	/**
	 * Sets the auth.
	 *
	 * @param auth the new auth
	 */
	public void setAuth(String auth) {
		this.auth = auth;
	}

}
