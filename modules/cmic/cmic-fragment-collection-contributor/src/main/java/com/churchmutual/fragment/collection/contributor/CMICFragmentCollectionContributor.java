package com.churchmutual.fragment.collection.contributor;

import com.liferay.fragment.contributor.BaseFragmentCollectionContributor;
import com.liferay.fragment.contributor.FragmentCollectionContributor;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.ServletContext;

/**
 * @author Matthew Chan
 */
@Component(service = FragmentCollectionContributor.class)
public class CMICFragmentCollectionContributor extends BaseFragmentCollectionContributor {

	@Override
	public String getFragmentCollectionKey() {
		return "cmic";
	}

	@Override
	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Reference(target = "(osgi.web.symbolicname=com.churchmutual.fragment.collection.contributor)")
	private ServletContext _servletContext;

}