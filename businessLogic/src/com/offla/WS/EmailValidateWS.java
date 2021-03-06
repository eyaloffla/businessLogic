package com.offla.WS;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.offla.bussinesLogic.IEmailValidate;
import com.offla.util.Util;

@Stateless
@Path("/email")
public class EmailValidateWS {
	
	@EJB
	IEmailValidate emailValidate;
	
	
	@Path("{email}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String validate(@PathParam(value="email") String email){
		
		String isValid = Util.BOOLEAN_FALSE;
		
		if(emailValidate.isEmailValid(email)){
			isValid = Util.BOOLEAN_TRUE;	
		}
	
	
	return isValid;

}

}