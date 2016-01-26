/**
 * 
 */
package com.github.sunflowerlb.framework.usage.domain;

import javax.validation.constraints.NotNull;

/**
 * 
 * @author lb
 */
public class Product {

	private int id;
	
	@NotNull
	private String name;

	public Product() {
	    
	}
	
	public Product(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
