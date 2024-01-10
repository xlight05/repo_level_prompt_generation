package org.seamlets.cms.comparator;

import java.util.Comparator;

import org.seamlets.cms.entities.Domain;



public class DomainComparator implements Comparator<Domain> {

	@SuppressWarnings("null")
	public int compare(Domain domain0, Domain domain1) {
		if(domain0 == null && domain1 == null) return 0;
		if(domain0 != null && domain1 == null) return -1;
		if(domain0 == null && domain1 != null) return 1;
		
		String rootUrl0 = domain0.getRootUrl();
		String rootUrl1 = domain1.getRootUrl();
		if(rootUrl0 == null && rootUrl1 == null) return 0;
		if(rootUrl0 != null && rootUrl1 == null) return -1;
		if(rootUrl0 == null && rootUrl1 != null) return 1;
		
		return rootUrl0.compareTo(rootUrl1);
	}
}