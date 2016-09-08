package com.mkyong.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class JerseyClientPost {

	public static void main(String[] args) {

		try {
			Path path = Paths.get("C:\\kth.jpg");
			byte[] data = Files.readAllBytes(path);
			String base64String = Base64.getEncoder().encodeToString(data);

			Client client = Client.create();

			WebResource webResource = client
//					.resource("http://127.0.0.1:8080/api/people");
					.resource("http://127.0.0.1:8080/api/entitytest-2-s");


			String input = "{\"test1\":\"" + base64String + "\", \"test1ContentType\":\"image/jpeg\"}";

			ClientResponse response = webResource.type("application/json")
					.post(ClientResponse.class, input);

			if (response.getStatus() != 201) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			System.out.println("Output from Server .... \n");
			String output = response.getEntity(String.class);
			System.out.println(output);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}
