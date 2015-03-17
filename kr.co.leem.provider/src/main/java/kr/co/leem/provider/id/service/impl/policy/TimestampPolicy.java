/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kr.co.leem.provider.id.service.impl.policy;

import kr.co.leem.utils.lang.DateUtils;
import kr.co.leem.utils.lang.StringUtils;

/**
 * Timestamp 기반 아이디 값 생성.<br>
 * 
 * 날짜 : 2012.06.19<br>
 * 구분자 : '-'<br>
 * fillChar : '0'<br>
 * 아이디 값 : '12'<br>
 * 길이값 : '5'<br>
 * 결과값 : '20120619-00012'<br>
 * 
 * @author 임 성천.
 */
public class TimestampPolicy extends AsemPrefixPolicy {
	
	String pattern = "yyyyMMdd";
	
	String separator = "";
	
	public String makeId(String id) {
		return DateUtils.getCurrentDateTimeString(pattern) + separator
				+ StringUtils.fillString(id, fillChar, length);
	}
	
	/**
	 * 날짜 형식을 설정함.(기본 값 : yyyyMMdd.)
	 * 
	 * @param pattern 날짜 형식.
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	/**
	 * 구분자 값 설정.
	 * @param separator 구분자 값.
	 */
	public void setSeparator(String separator) {
		this.separator = separator;
	}
}