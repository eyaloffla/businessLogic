package com.offla.bussinesLogic;

import javax.ejb.Local;

@Local
public interface IEmailValidate {
	
	public boolean isEmailValid(String email);

}
