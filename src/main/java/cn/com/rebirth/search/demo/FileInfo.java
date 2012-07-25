/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-demo FileInfo.java 2012-7-25 10:54:02 l.xue.nong$$
 */
package cn.com.rebirth.search.demo;

import java.util.Date;

import cn.com.rebirth.commons.search.annotation.FieldIndex;
import cn.com.rebirth.commons.search.annotation.FieldStore;
import cn.com.rebirth.commons.search.annotation.Index;
import cn.com.rebirth.commons.search.annotation.PKey;

/**
 * The Class FileInfo.
 *
 * @author l.xue.nong
 */
@Index(indexName = "rebirth.demo.index.file.info", indexType = "rebirth.demo.index.file.info")
public class FileInfo implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6758604537560569077L;

	/** The doc id. */
	private Integer docId;

	/** The title. */
	private String fileName;

	/** The content. */
	private String content;

	/** The last time. */
	private Date lastTime;

	/** The size. */
	private Long size;

	/** The type. */
	private String type;

	/** The path. */
	private String path;

	private String title;

	/**
	 * Gets the doc id.
	 *
	 * @return the doc id
	 */
	@PKey
	public Integer getDocId() {
		return docId;
	}

	/**
	 * Sets the doc id.
	 *
	 * @param docId the new doc id
	 */
	public void setDocId(Integer docId) {
		this.docId = docId;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	@FieldIndex(value = FieldIndex.NO_ANALYZED)
	@FieldStore(value = FieldStore.NO)
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	@FieldIndex(value = FieldIndex.ANALYZED)
	@FieldStore(value = FieldStore.NO)
	public String getContent() {
		return content;
	}

	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Gets the last time.
	 *
	 * @return the last time
	 */
	@FieldIndex(value = FieldIndex.NO_ANALYZED)
	@FieldStore(value = FieldStore.NO)
	public Date getLastTime() {
		return lastTime;
	}

	/**
	 * Sets the last time.
	 *
	 * @param lastTime the new last time
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	@FieldIndex(value = FieldIndex.NO_ANALYZED, type = FieldIndex.NUMERICFIELD)
	@FieldStore(value = FieldStore.NO)
	public Long getSize() {
		return size;
	}

	/**
	 * Sets the size.
	 *
	 * @param size the new size
	 */
	public void setSize(Long size) {
		this.size = size;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	@FieldIndex(value = FieldIndex.NO_ANALYZED)
	@FieldStore(value = FieldStore.NO)
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	@FieldIndex(value = FieldIndex.NO_ANALYZED)
	@FieldStore(value = FieldStore.NO)
	public String getPath() {
		return path;
	}

	/**
	 * Sets the path.
	 *
	 * @param path the new path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	@FieldIndex(value = FieldIndex.ANALYZED)
	@FieldStore(value = FieldStore.NO)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
