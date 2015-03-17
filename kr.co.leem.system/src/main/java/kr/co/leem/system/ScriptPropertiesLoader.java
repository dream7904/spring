package kr.co.leem.system;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * Load scripts.properties.
 * 
 * @author 임 성천.
 * 
 */
public class ScriptPropertiesLoader {
	
	private Properties properties = new Properties();
	
	/**
	 * 프로퍼티 기본 경로.
	 */
	public static final String DEFAULT_SCRIPTS_PROPERTIES_LOCATION = "classpath:/kr/co/leem/system/scripts.properties";
	
	private boolean loaded = false;
	
	/**
	 * 프로퍼티 로드.
	 */
	public void load() {
		load(DEFAULT_SCRIPTS_PROPERTIES_LOCATION);
	}
	
	/**
	 * 프로퍼티 로드.
	 * 
	 * @param path 프로퍼티 기본 경로.
	 */
	public void load(String path) {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource(path);
		try {
			properties.load(resource.getInputStream());
			loaded = true;
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalStateException("scripts.properteis Loading Error", e);
		}
	}
	
	/**
	 * 프로퍼티 로드 여부를 반환함.
	 * 
	 * @return 로드되었으면 true.
	 */
	public boolean isLoaded() {
		return loaded;
	}
	
	/**
	 * 프로퍼티 객체를 반환함.
	 * 
	 * @return 프로퍼티 객체.
	 */
	public Properties getProperties() {
		return properties;
	}
}