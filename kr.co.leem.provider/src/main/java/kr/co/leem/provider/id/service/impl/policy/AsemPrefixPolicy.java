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

import kr.co.leem.provider.id.service.IdPolicy;
import kr.co.leem.utils.lang.StringUtils;

/**
 * 접두어 기반 아이디 생성.<br>
 * <br>
 * prefix : 'test-'<br>
 * fillChar : '0'<br>
 * id : '12'<br>
 * length '5'<br>
 * 
 * 결과 : 'test-00012'.<br>
 * 
 * @author 임 성천.
 */
public class AsemPrefixPolicy implements IdPolicy {
	
	private String prefix;
	
	protected int length = 5;
	
	protected char fillChar = '0';
	
	/**
	 * 기본 생성자.
	 */
	public AsemPrefixPolicy() {
		super();
	}
	
	/**
	 * 생성자.
	 * 
	 * @param prefix 접두어 값.
	 * @param length 길이 값.
	 * @param fillChar 채울 문자 값.
	 */
	public AsemPrefixPolicy(String prefix, int length, char fillChar) {
		super();
		this.prefix = prefix;
		this.length = length;
		this.fillChar = fillChar;
	}
	
	/**
	 * 접두어 설정 기반의 아이디를 생성하여 반환함..
	 * 
	 * @param id 아이디 값.
	 * @return 접두어 설정 기반의 아이디
	 */
	public String makeId(String id) {
		return prefix + StringUtils.fillString(id, fillChar, length);
	}
	
	/**
	 * 길이 값을 설정함.
	 * 
	 * @param length 길이 값.
	 */
	public void setLength(int length) {
		this.length = length;
	}
	
	/**
	 * 접두어 값을 설정.
	 * 
	 * @param prefix 접두어 값.
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	/**
	 * 채울 문자값을 설정함.
	 */
	public void setFillChar(char fillChar) {
		this.fillChar = fillChar;
	}
}