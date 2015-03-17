package kr.co.leem.provider.id.service;

/**
 * IdStrategy.
 * 
 * @author 임 성천.
 */
public interface IdPolicy {
	/**
	 * 변경 룰에 따른 아이디를 생성한 값을 반환함.
	 * 
	 * @param id 변경할 아이디 값.
	 * @return 변경 룰에 따른 아이디를 생성한 값
	 */
	public String makeId(String id);
}