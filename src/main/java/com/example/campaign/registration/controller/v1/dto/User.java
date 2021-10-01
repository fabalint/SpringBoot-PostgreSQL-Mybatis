package com.example.campaign.registration.controller.v1.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class User {
	/* uuid.. db user id.. currently email. */
	@Min(7)
	@Max(128)
	private String userId;
	@Pattern(regexp = "[a-zA-Z0-9_]{2,10}")
	private String territory;
	@Min(2)
	@Max(128)
	private String name;
	// TODO: IRL: split address into fields, to have better validation/clean data in
	// DB.
	@Min(20)
	@Max(128)
	private String address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTerritory() {
		return territory;
	}

	public void setTerritory(String territory) {
		this.territory = territory;
	}
}
