package kr.co.leem.utils.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ListUtils extends org.apache.commons.collections.ListUtils {
	public static boolean isNotEmpty(Collection<?> collection) throws Exception {
		if (collection == null || collection.size() < 1) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean isNotEmpty(List<?> infos) throws Exception {
		if (infos == null || infos.size() < 1) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean isEmpty(Collection<?> collection) throws Exception {
		if (collection != null && collection.size() > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean isEmpty(List<?> infos) throws Exception {
		if (infos != null && infos.size() > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 대상이 원본을 포함하고 있지 않으면, 목록에 저장
	 * @param sourceList
	 * @param targetList
	 * @return
	 * @throws Exception
	 */
	public static List<Integer> getUniqueIdListByInteger(
			List<Integer> sourceList,
			List<Integer> targetList) throws Exception {
		List<Integer> resultList = new ArrayList<Integer>();
		for (int i = 0; i < sourceList.size(); i++) {
			if (!targetList.contains(sourceList.get(i))) {
				resultList.add(sourceList.get(i));
			}
		}
		return resultList;
	}
	
	public static List<Integer> getDupIdListByInteger(
			List<Integer> sourceList,
			List<Integer> targetList) throws Exception {
		List<Integer> resultList = new ArrayList<Integer>();
		for (int i = 0; i < sourceList.size(); i++) {
			if (targetList.contains(sourceList.get(i))) {
				resultList.add(sourceList.get(i));
			}
		}
		return resultList;
	}
	
	/**
	 * 대상이 원본을 포함하고 있지 않으면, 목록에 저장
	 * @param sourceList
	 * @param targetList
	 * @return
	 * @throws Exception
	 */
	public static List<String> getUniqueIdListByString(
			List<String> sourceList,
			List<String> targetList) throws Exception {
		List<String> resultList = new ArrayList<String>();
		for (int i = 0; i < sourceList.size(); i++) {
			if (!targetList.contains(sourceList.get(i))) {
				resultList.add(sourceList.get(i));
			}
		}
		return resultList;
	}
	
	public static List<String> getDupIdListByString(
			List<String> sourceList,
			List<String> targetList) throws Exception {
		List<String> resultList = new ArrayList<String>();
		for (int i = 0; i < sourceList.size(); i++) {
			if (targetList.contains(sourceList.get(i))) {
				resultList.add(sourceList.get(i));
			}
		}
		return resultList;
	}
		
	public static <T> int getLengthIfNull(List<T> objArr) {
		if(objArr != null) {
			return objArr.size();
		} else {
			return 0;
		}
	}
	
	public static <T> int getLengthIfNull(Set<T> objArr) {
		if(objArr != null) {
			return objArr.size();
		} else {
			return 0;
		}
	}
	public static <T> List<T> setToList(Set<T> set) {
		if (getLengthIfNull(set) > 0) {
			List<T>list = new ArrayList<T>();
			Iterator<T> it = set.iterator();
			while (it.hasNext()) {
				list.add(it.next());
			}
			return list;
		}
		return null;
	}
	public static <T> Set<T> listToSet(List<T> list) {
		if (getLengthIfNull(list) > 0) {
			Set<T>set = new HashSet<T>();
			Iterator<T> it = list.iterator();
			while (it.hasNext()) {
				set.add(it.next());
			}
			return set;
		}
		return null;
	}
}
