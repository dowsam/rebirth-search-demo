package cn.com.rebirth.search.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.joda.time.DateTime;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.rebirth.commons.Page;
import cn.com.rebirth.commons.PageRequest.Direction;
import cn.com.rebirth.commons.PageRequest.Sort;
import cn.com.rebirth.commons.search.SearchPageRequest;
import cn.com.rebirth.commons.search.SortType;
import cn.com.rebirth.commons.utils.DateUtils;
import cn.com.rebirth.search.client.FacetPage;
import cn.com.rebirth.search.client.FacetPageRequest;
import cn.com.rebirth.search.client.HighlightSearchPageRequest;
import cn.com.rebirth.search.client.HighlightSearchPageRequest.HighligthSearchField;
import cn.com.rebirth.search.client.group.LuceneGroup;
import cn.com.rebirth.search.client.group.LuceneGroupField;

import com.google.common.collect.Lists;

public class FileInfoBusinessTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("search.core.discovery.initial_state_timeout", "5m");
		System.setProperty("rebirth.search.server.cluster", "rebirth-search-server-cluster-test-123");
		System.setProperty("default_operator", "and");
		System.setProperty("zk.zkConnect", "192.168.2.179");
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"/applicationContext.xml");
		List<FileInfo> fileInfos = null;
		FileInfoBusiness fileInfoBusiness = new FileInfoBusiness();
		try {
			fileInfos = buildData();
			//create lucene index
			fileInfoBusiness.batchCreateOrUpdateIndex(fileInfos);
			//flush
			fileInfoBusiness.flush();
			//refresh
			fileInfoBusiness.refresh();
			//search lucene index
			SearchPageRequest pageRequest = new SearchPageRequest();
			//search content 
			Page<FileInfo> page = fileInfoBusiness.search("content:'新科技'", pageRequest);
			System.out.println(page.getTotalItems());
			println(page);

			//search content highlight
			List<HighligthSearchField> highligthSearchFields = Lists.newArrayList();
			highligthSearchFields.add(new HighligthSearchField("content", 100));//摘要100
			pageRequest = new HighlightSearchPageRequest(highligthSearchFields);
			page = fileInfoBusiness.search("content:'新科技'", pageRequest);
			println(page);
			//search content facet
			List<LuceneGroupField> luceneGroupFields = Lists.newArrayList();
			LuceneGroupField groupField = new LuceneGroupField();
			groupField.setGroupField("type");
			groupField.setGroupFieldTop(100);
			luceneGroupFields.add(groupField);
			pageRequest = new FacetPageRequest(luceneGroupFields);
			page = fileInfoBusiness.search("content:'新科技'", pageRequest);
			println(page);
			//search content facet and highlight
			((FacetPageRequest) pageRequest).setHighligthSearchFields(highligthSearchFields);
			page = fileInfoBusiness.search("content:'新科技'", pageRequest);
			println(page);
			//search content facet and highlight and sort
			Sort sort = new Sort(new SearchPageRequest.SearchOrder(Direction.DESC, "lastTime", SortType.LONG),
					new SearchPageRequest.SearchOrder(Direction.ASC, "type", SortType.STRING),
					new SearchPageRequest.SearchOrder(Direction.DESC, "size", SortType.LONG));
			pageRequest.setSort(sort);
			page = fileInfoBusiness.search("content:'新科技'", pageRequest);
			println(page);
			//search content facet and highlight and sort and find type doc
			page = fileInfoBusiness.search("content:'新科技' && type='doc'", pageRequest);
			println(page);
			//search content facet and highlight and sort and find type doc and lastTime >12 lastTime<13:20:20:60
			DateTime beginTime = new DateTime(2012, 7, 25, 12, 0);
			long begin = beginTime.toDate().getTime();
			DateTime endTime = new DateTime(2012, 7, 25, 13, 20, 20, 60);
			long end = endTime.toDate().getTime();
			page = fileInfoBusiness.search("content:'新科技' && type='doc' && lastTime:{" + begin + "," + end + "]",
					pageRequest);
			println(page);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fileInfos != null)
				//delete lucene index
				fileInfoBusiness.batchDeleteIndex(fileInfos);
			applicationContext.close();
		}
	}

	private static void println(Page<FileInfo> page) {
		if (page == null || page.getTotalItems() == -1)
			return;
		System.out.println(page.getTotalItems());
		for (FileInfo fileInfo : page) {
			System.out.println("fileName:" + fileInfo.getFileName());
			System.out.println("content:" + fileInfo.getContent());
			System.out.println("lastTime:" + DateUtils.formatDate(fileInfo.getLastTime(), null));
			System.out.println("type:" + fileInfo.getType());
			System.out.println("size:" + fileInfo.getSize());
		}
		if (page instanceof FacetPage) {
			FacetPage<FileInfo> facetPage = (FacetPage<FileInfo>) page;
			Map<String, List<LuceneGroup>> map = facetPage.getGroups();
			for (Map.Entry<String, List<LuceneGroup>> entry : map.entrySet()) {
				String key = entry.getKey();
				Collection<LuceneGroup> value = entry.getValue();
				System.out.println("-----" + key + "-----");
				for (LuceneGroup luceneGroup : value) {
					System.out.println(luceneGroup);
					if (luceneGroup.getChildren() != null && !luceneGroup.getChildren().isEmpty()) {
						Collection<LuceneGroup> childen = luceneGroup.getChildren();
						for (LuceneGroup luceneGroup2 : childen) {
							System.out.println("----:" + luceneGroup2);
						}
					}
				}
			}
		}
	}

	public static List<FileInfo> buildData() throws Exception {
		Collection<File> files = FileUtils.listFiles(new File(System.getProperty("user.dir")
				+ "/src/test/resources/doc"), null, true);
		List<FileInfo> fileInfos = Lists.newArrayListWithCapacity(files.size());
		for (File file : files) {
			FileInfo fileInfo = generator(file);
			if (fileInfo != null) {
				fileInfos.add(fileInfo);
			}
		}
		return fileInfos;
	}

	private static FileInfo generator(File f) throws FileNotFoundException, IOException, TikaException {
		if (f.isDirectory())
			return null;
		FileInfo fileInfo = new FileInfo();
		Metadata metadata = new Metadata();
		String content = new Tika().parseToString(new FileInputStream(f), metadata).replaceAll("[\\s*\\t\\n\\r]", "");
		fileInfo.setContent(content);

		fileInfo.setTitle(FilenameUtils.getBaseName(f.getName()));
		fileInfo.setLastTime(new Date(f.lastModified()));
		fileInfo.setDocId(cn.com.rebirth.commons.utils.NonceUtils.randomInt());
		fileInfo.setFileName(f.getName());
		fileInfo.setPath(f.getAbsolutePath());
		fileInfo.setType(FilenameUtils.getExtension(f.getName()));
		fileInfo.setSize((f.length() / 1024));
		return fileInfo;
	}

}
