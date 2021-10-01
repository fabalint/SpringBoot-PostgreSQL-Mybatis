package com.example.campaign.registration.svc.api.v1.dto;

public class UserSvcBean {
	/* uuid.. db user id.. currently email. */
	private String userId;
	private String territory;
	private String name;
	// TODO: IRL: split address into fields, to have better validation/clean data in
	// DB.
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
