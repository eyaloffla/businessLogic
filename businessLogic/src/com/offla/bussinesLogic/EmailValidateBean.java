package com.offla.bussinesLogic;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(IEmailValidate.class)
public class EmailValidateBean implements IEmailValidate{
	
	public boolean isEmailValid(String email){
		boolean result = false;
		
		result = EmailMyCheck.isAddressValid(email);
		
		return result;
	}

}
