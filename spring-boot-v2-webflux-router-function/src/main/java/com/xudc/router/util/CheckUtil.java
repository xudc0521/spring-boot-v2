package com.xudc.router.util;

import com.xudc.router.exceptions.CheckException;

import java.util.stream.Stream;

/**
 * @author xudc
 */
public class CheckUtil {

	private static final String[] INVALID_NAMES = { "admin", "root" };
	/**
	 * 校验名字, 不成功抛出校验异常
	 * @param value
	 */
	public static void checkName(String value) {
		Stream.of(INVALID_NAMES).filter(name -> name.equalsIgnoreCase(value))
				.findAny().ifPresent(name -> {
					throw new CheckException("name", value);
				});
	}
}
