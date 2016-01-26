package com.github.sunflowerlb.framework.core.dubbo.trace.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;

/**
 * Created by lb on 12/5/14.
 */
public class EnvInfo {

	private static final Logger LOG = LoggerFactory.getLogger(EnvInfo.class);

	private static final Joiner JOINER = Joiner.on("-").skipNulls();

	/**
	 * 获取环境信息
	 * 
	 * @return
	 */
	public static String getEnvInfo() {
		String result = null;
		try {

			result = JOINER.join(new String[] { InetAddress.getLocalHost().toString(), TraceVersion.getVersion() });
		} catch (UnknownHostException e) {
			LOG.error("get env info error ", e);
		}

		return result;

	}

	public static void main(String[] args) {
		Map<String, String> getenv = System.getenv();
		for (Map.Entry<String, String> entry : getenv.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			System.out.println(key + ":" + value);
		}
	}
}
