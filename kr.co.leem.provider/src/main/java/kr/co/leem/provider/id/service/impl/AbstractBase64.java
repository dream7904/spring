package kr.co.leem.provider.id.service.impl;

/**
 * @author 임 성천.
 */
public abstract class AbstractBase64 {
	/**
	 * 기본 생성자.
	 */
	private AbstractBase64() {
		
	}
	
	private static final char[] S_BASE64CHAR = { 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', '+', '/' };
	
	private static final char S_BASE64PAD = '=';
	
	private static final byte[] S_DECODETABLE = new byte[128];
	
	static {
		for (int i = 0; i < S_DECODETABLE.length; i++)
			S_DECODETABLE[i] = Byte.MAX_VALUE; // 127
		for (int i = 0; i < S_BASE64CHAR.length; i++)
			// 0 to 63
			S_DECODETABLE[S_BASE64CHAR[i]] = (byte) i;
	}
	
	/**
	 * 바이트 값을 인코딩한 문자열을 반환함.
	 * 
	 * @param data byte 값.
	 * @return 바이트 값을 인코딩한 문자열.
	 */
	public static String encode(byte[] data) {
		return encode(data, 0, data.length);
	}

	/**
	 * 바이트 값을 인코딩한 문자열을 반환함.

	 * 
	 * @param data byte 값.
	 * @param offset 시작위치 값.
	 * @param length 길이 값.
	 * @return 인코딩된 문자열.
	 */
	public static String encode(byte[] data, int offset, int length) {
		if (length <= 0)
			return "";
		char[] out = new char[length / 3 * 4 + 4];
		int rIndex = offset;
		int wIndex = 0;
		int rest = length;
		while (rest >= 3) {
			int i = ((data[rIndex] & 0xff) << 16)
					+ ((data[rIndex + 1] & 0xff) << 8)
					+ (data[rIndex + 2] & 0xff);
			out[wIndex++] = S_BASE64CHAR[i >> 18];
			out[wIndex++] = S_BASE64CHAR[(i >> 12) & 0x3f];
			out[wIndex++] = S_BASE64CHAR[(i >> 6) & 0x3f];
			out[wIndex++] = S_BASE64CHAR[i & 0x3f];
			rIndex += 3;
			rest -= 3;
		}
		if (rest == 1) {
			int i = data[rIndex] & 0xff;
			out[wIndex++] = S_BASE64CHAR[i >> 2];
			out[wIndex++] = S_BASE64CHAR[(i << 4) & 0x3f];
			out[wIndex++] = S_BASE64PAD;
			out[wIndex++] = S_BASE64PAD;
		} else if (rest == 2) {
			int i = ((data[rIndex] & 0xff) << 8) + (data[rIndex + 1] & 0xff);
			out[wIndex++] = S_BASE64CHAR[i >> 10];
			out[wIndex++] = S_BASE64CHAR[(i >> 4) & 0x3f];
			out[wIndex++] = S_BASE64CHAR[(i << 2) & 0x3f];
			out[wIndex++] = S_BASE64PAD;
		}
		return new String(out, 0, wIndex);
	}
}