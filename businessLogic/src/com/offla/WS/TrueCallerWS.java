package com.offla.WS;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.offla.bussinesLogic.TrueCallerRetriver;


@Stateless
@Path("/truecaller")
public class TrueCallerWS {
	
	@EJB
	TrueCallerRetriver trueCallerservice;
	
	@Path("{getname}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String getName(@PathParam(value="trueCallerNumber") String trueCallerNumber){
		
		String name = trueCallerservice.getName(trueCallerNumber);
	
	
	return name;

}

}
