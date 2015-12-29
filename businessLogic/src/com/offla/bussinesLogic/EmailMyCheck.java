package com.offla.bussinesLogic;

import java.net.*;
import java.io.*;
import javax.naming.*;
import java.util.*;
import javax.naming.directory.*;

public class EmailMyCheck {

	private static int hear(BufferedReader in) throws IOException {
		String lines = null;
		int result = 0;

		while ((lines = in.readLine()) != null) {
			String pfx = lines.substring(0, 3);
			try {
				result = Integer.parseInt(pfx);
			} catch (Exception ex) {
				result = -1;
			}
			if (lines.charAt(3) != '-') break;
		}
		return result;
	}

	private static void saying(BufferedWriter wr, String text) throws IOException {
		wr.write(text + "\r\n");
		wr.flush();
		return;
	}

	private static ArrayList getMX(String hostName) throws NamingException {
		
	     // DNS lookup for MX records in the domain
		Hashtable env = new Hashtable();
		env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
		DirContext iconectx = new InitialDirContext(env);
		Attributes attrs = iconectx.getAttributes(hostName, new String[]{"MX"});
		Attribute attr = attrs.get("MX");

	    // if MX record not exists, try this machine code
		if ((attr == null) || (attr.size() == 0)) {
			attrs = iconectx.getAttributes(hostName, new String[]{"G"});
			attr = attrs.get("G");
			if (attr == null) {
				throw new NamingException("No match for name '" + hostName + "'");
			}
		}

		//Let's try if this is works properly
		ArrayList result = new ArrayList();
		NamingEnumeration en = attr.getAll();

		while (en.hasMore()) {
			String mailhost;
			String xox = (String) en.next();
			String fan[] = xox.split(" ");

			if (fan.length == 1) mailhost = fan[0];
			else if (fan[1].endsWith(".")) 
				mailhost = fan[1].substring(0, (fan[1].length() - 1));
			else mailhost = fan[1];
			result.add(mailhost);
		}
		return result;
	}

	//Main function that checks id mail exists
	public static boolean isAddressValid(String address) {
		int position = address.indexOf('@');
		if (position == -1) return false;
		
		String dom = address.substring(++position);
		ArrayList mxList = null;
	      // Isolate the domain/machine name and get a list of mail exchangers
		try { mxList = getMX(dom);
		} catch (NamingException ex) {
			return false;
		}

	    //if the domain is correct, it doesn't mean that whole email is correct
		if (mxList.isEmpty())   return false;
	
		//Now we make SMTP validation, we will try each exchanger. One of checks can pass and another doesn't
		for (int mx = 0; mx < mxList.size(); mx++) {
			boolean valid = false;
			try {
				int result;
				Socket socet = new Socket((String) mxList.get(mx), 25);
				BufferedReader rd = new BufferedReader(new InputStreamReader(socet.getInputStream()));
				BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socet.getOutputStream()));

				result = hear(rd);
				if (result != 220) {
					throw new Exception("The header is incorrect " + result);
				}
				saying(wr, "EHLO off-la.com");

				result = hear(rd);
				if (result != 250) {
					throw new Exception("ESMTP not valued " + result);
				}


	             // Validates the sender address
				saying(wr, "MAIL FROM: <xxx.yyyyyyy@gmail.com>");
				result = hear(rd);
				if (result != 250) {
					throw new Exception("Sender not valued " + result);
				}

				saying(wr, "RCPT TO: <" + address + ">");
				result = hear(rd);
				
				saying(wr, "RSET");
				hear(rd);
			
				saying(wr, "QUIT");
				hear(rd);
				if (result != 250) {
					throw new Exception("This address uncorrect " + result);
				}

				valid = true;
				rd.close();
				wr.close();
				socet.close();
			} catch (Exception ex) {
				//System.out.println(ex.getMessage() + " Trying next host");

			} finally {
				if (valid) return true;
			}
		}
		return false;
	}

	public static void main(String args[]) {
		Scanner input = new Scanner(System.in);
		String email = input.nextLine().trim();
		System.out.println(isAddressValid(email));
	}
}
