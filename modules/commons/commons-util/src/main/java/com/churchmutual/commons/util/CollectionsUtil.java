package com.churchmutual.commons.util;

import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CollectionsUtil {

	public static <T> T getFirst(List<T> list) {
		if (ListUtil.isEmpty(list)) {
			return null;
		}

		return list.get(0);
	}

	public static <T> T getOnlyOne(List<T> list) {
		if (list == null) {
			return null;
		}
		else if (list.isEmpty()) {
			return null;
		}
		else if (list.size() == 1) {
			return list.get(0);
		}

		throw new IllegalArgumentException(
			"Expected one element but was " + list.size());
	}

	public static boolean isEmpty(Collection<?> collection) {
		if ((collection == null) || collection.isEmpty()) {
			return true;
		}

		return false;
	}

	public static boolean isNotEmpty(Collection<?> collection) {
		return !isEmpty(collection);
	}

	public static boolean listsHaveElementsInCommon(
		List<Long> list1, List<Long> list2) {

		if ((list1 == null) || (list2 == null)) {
			return false;
		}

		return !Collections.disjoint(list1, list2);
	}

	private CollectionsUtil() {
	}

}