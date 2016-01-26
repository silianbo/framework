package com.github.sunflowerlb.framework.core.utils;

/**
 * 值对， jdk里没有tuple的工具类，暂时提供一个值对的Pair工具
 * @author lb
 *
 * @param <F>
 * @param <S>
 */
public final class Pair<F, S> {
	private F first; 
	private S second; 
	
	private Pair(F first, S second){
		this.first = first; 
		this.second = second; 
	}
	/**
	 * 创建值对
	 * @param first 第一个值
	 * @param second  第二个值
	 * @param <F> 第一个值类型 
	 * @param <S> 第二个值类型
	 * @return
	 */
	public static <F, S> Pair<F, S> of(F first, S second){
		return new Pair<F, S>(first, second); 
	}
	
	/**
	 * 获取第一个值
	 * @return
	 */
	public F first(){
		return this.first; 
	}
	
	/**
	 * 获取第二个值
	 * @return
	 */
	public S second(){
		return this.second; 
	}
}
