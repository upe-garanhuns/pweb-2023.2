package br.upe.garanhuns.esw.pweb.model;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.servlet.http.HttpServletRequest;

public class ImgService {
	private ConnectionAPI img;
	private List<ImgDTO> imgList;
	private JsonArray imgJsonArray;
	private JsonArrayBuilder jsonArrayBuilder;
	
	public ImgService() {
		img = new ConnectionAPI();
		
		jsonArrayBuilder = Json.createArrayBuilder();
	}
	
	public JsonArray getAllImages() {
		imgList = img.transformData(img.findData());
		jsonArrayBuilder = Json.createArrayBuilder();
		
		try {
			for(ImgDTO imgDTO : imgList) {
				JsonObject imgObject = buildImgObject(imgDTO);
				jsonArrayBuilder.add(imgObject);
			}
			
			imgJsonArray = jsonArrayBuilder.build();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return imgJsonArray;
	}
	
	public String getIdRequest(HttpServletRequest request) {
		JsonReader reader = null;
		
		try {
			reader = Json.createReader(request.getReader());
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		JsonObject requestBody = reader.readObject();
		
		return requestBody.getString("id");
	}
	
	public JsonArray getImgId(String id) {
		
		jsonArrayBuilder = Json.createArrayBuilder();
	    imgJsonArray = jsonArrayBuilder.build();
	    
		try {
			
			if (imgList == null) {
		        getAllImages();
		        }
			
			for(ImgDTO imgDTO : imgList) {
				if(imgDTO.getId().equals(id)) {
					JsonObject imgArray = buildImgObject(imgDTO);
					jsonArrayBuilder.add(imgArray);
				}
			}
			
			 imgJsonArray = jsonArrayBuilder.build();
			 
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return imgJsonArray;
	}
	
	protected JsonObject buildImgObject(ImgDTO imgDTO) {
		return Json.createObjectBuilder()
				.add("id", imgDTO.getId())
				.add("author", imgDTO.getAuthor())
				.add("width", imgDTO.getWidth())
				.add("height", imgDTO.getHeight())
				.add("url", imgDTO.getUrl())
				.add("downloadUrl", imgDTO.getDownloadUrl()).build();
	}
}
