package com.offla.bussinesLogic;

import javax.ejb.Local;

@Local
public interface TrueCallerRetriver {
	
	String getName(String number);

}
