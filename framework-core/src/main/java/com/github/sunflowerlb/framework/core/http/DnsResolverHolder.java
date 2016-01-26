/**
 * 
 */
package com.github.sunflowerlb.framework.core.http;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * @author lb
 */
public class DnsResolverHolder {

	private static ArrayList<MemoryCacheDnsResolver> list = new ArrayList<MemoryCacheDnsResolver>();

	public static synchronized void add(MemoryCacheDnsResolver dnsResolver) {
		list.add(dnsResolver);
	}

	public static synchronized void remove(MemoryCacheDnsResolver dnsResolver) {
		dnsResolver.shutdown();
		list.remove(dnsResolver);
	}

	public static synchronized void shutdown() {
		Iterator<MemoryCacheDnsResolver> iter = list.iterator();
		while (iter.hasNext()) {
			MemoryCacheDnsResolver dnsResolver = iter.next();
			dnsResolver.shutdown();
			iter.remove();
		}
	}
}
