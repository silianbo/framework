package com.github.sunflowerlb.framework.core.dubbo.trace.sampler;

import java.util.Random;

/**
 * User: lb Date: 14-8-13 Time: 16:41
 */
public class DefaultSampler implements Sampler {

	public static final String DEFAULT_SAMPLE_RATE = "0.7"; // 默认采样率

	private float rate = Float.valueOf(DefaultSampler.DEFAULT_SAMPLE_RATE); // 默认的采样率

	public DefaultSampler(float rate) {
		this.rate = rate;
	}

	public DefaultSampler() {
	}

	public static void main(String[] args) {
		Sampler sampler = new DefaultSampler(1);
		for (int i = 0; i < 10; i++) {

			boolean sample = sampler.isSample();
			System.out.println(sample);
		}
	}

	@Override
	public boolean isSample() {

		Random random = new Random();

		// 默认70%采用率
		return random.nextFloat() <= rate;
	}
}
