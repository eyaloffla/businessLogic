package com.offla.bussinesLogic;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(TrueCallerRetriver.class)
public class TrueCallerBean implements TrueCallerRetriver{

	@Override
	public String getName(String number) {
		// TODO Auto-generated method stub
		return null;
	}

}
