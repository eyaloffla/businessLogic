package com.offla.bussinesLogic;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(IEmailValidate.class)
public class EmailValidateBean implements IEmailValidate{
	
	public boolean isEmailValid(){
		boolean result = false;
		
		return result;
	}

}
