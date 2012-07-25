package cn.com.rebirth.search.demo;

import java.util.regex.Pattern;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

public class ClassPathScanningCandidateComponentProviderTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			RegexPatternTypeFilter filter = new RegexPatternTypeFilter(Pattern.compile("cn.com.rebirth.*.controller.*"));
			String packageSearchPath = "classpath*:cn/com/rebirth/**/*.class";
			ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
			MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
			Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
			for (Resource resource : resources) {
				if (resource.isReadable()) {
					try {
						System.out.println("b:" + resource.getFilename());
						MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
						boolean b = filter.match(metadataReader, metadataReaderFactory);
						if (b) {
							System.out.println(resource.getFilename());
						}
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			}
			//			ClassPathScanningCandidateComponentProvider a = new ClassPathScanningCandidateComponentProvider(true);
			//
			//			Set<BeanDefinition> beanDefinitions = a.findCandidateComponents("cn.com.rebirth");
			//			System.out.println(beanDefinitions.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
