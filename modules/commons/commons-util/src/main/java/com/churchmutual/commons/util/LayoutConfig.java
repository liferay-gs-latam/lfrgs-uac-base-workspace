package com.churchmutual.commons.util;

import static java.util.Arrays.asList;

import com.liferay.portal.kernel.model.LayoutConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LayoutConfig {

	public LayoutConfig addPortletKey(String portletKey) {
		portletsKeys.add(portletKey);

		return this;
	}

	public LayoutConfig addPortletsKeys(String... portletsKeys) {
		this.portletsKeys.addAll(asList(portletsKeys));

		return this;
	}

	public String getFriendlyURL() {
		return friendlyURL;
	}

	public String getName() {
		return name;
	}

	public long getParentLayoutId() {
		return parentLayoutId;
	}

	public List<String> getPortletsKeys() {
		return Collections.unmodifiableList(portletsKeys);
	}

	public boolean isHiddenPage() {
		return hiddenPage;
	}

	public boolean isPrivatePage() {
		return privatePage;
	}

	public LayoutConfig setFriendlyURL(String friendlyURL) {
		this.friendlyURL = friendlyURL;

		return this;
	}

	public LayoutConfig setHiddenPage(boolean hiddenPage) {
		this.hiddenPage = hiddenPage;

		return this;
	}

	public LayoutConfig setName(String name) {
		this.name = name;

		return this;
	}

	public LayoutConfig setParentLayoutId(long parentLayoutId) {
		this.parentLayoutId = parentLayoutId;

		return this;
	}

	public LayoutConfig setPrivatePage(boolean privatePage) {
		this.privatePage = privatePage;

		return this;
	}

	private String friendlyURL;
	private boolean hiddenPage = false;
	private String name;
	private long parentLayoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;
	private List<String> portletsKeys = new ArrayList<>();
	private boolean privatePage = true;

}