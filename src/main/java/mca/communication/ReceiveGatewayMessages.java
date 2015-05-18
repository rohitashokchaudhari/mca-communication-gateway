package mca.communication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;



@Path("/message")
public class ReceiveGatewayMessages {

	@POST
	@Path("/saveMessages")
	@Consumes(MediaType.APPLICATION_JSON)
	public MessageDetails saveMessages(MessageDetails message) {
		
		System.out.println("Inside --------------saveMessages-------------" + message);
		
		Writer writer = null;

		try {
			
		    
		    File output = new File("E:\\Project\\MultiChoice\\Mule-Example\\OutPutFile\\outputfile.txt");
		    try{
		    //PrintWriter out = new PrintWriter(output);
		    /*PrintWriter out = new PrintWriter(new FileWriter(output), true);
		    out.append("******* " + output.toString() +"******* " + "\n");
		    out.append("Number : " + message.getNumber() + " Message : " + message.getMessage());
		    out.close();*/
		    	
		    	File file = new File("E:\\Project\\MultiChoice\\Mule-Example\\OutPutFile\\outputfile.txt");
				if (!file.exists()) {
					file.createNewFile();
				}
				
				FileWriter fileWritter = new FileWriter(file, true);
				BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
				bufferWritter.write("Number : " + message.getNumber() + " Message : " + message.getMessage() + "\n");
				bufferWritter.close();
		    	
		    }catch(IOException e){
		        System.out.println("COULD NOT LOG!!");
		    }
		} catch (Exception ex) {
		  ex.printStackTrace();
		} 
		
		return message;
	}
	
	@GET
	public String getName() {
		
		System.out.println("Inside --------------getName-------------");
		
		return "test";
	}
}
