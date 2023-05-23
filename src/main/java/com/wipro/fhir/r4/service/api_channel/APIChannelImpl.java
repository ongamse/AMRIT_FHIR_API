package com.wipro.fhir.r4.service.api_channel;

import java.util.HashMap;
import java.util.Map;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.wipro.fhir.r4.data.request_handler.ResourceRequestHandler;
import com.wipro.fhir.r4.data.request_handler.UserAuthAPIResponse;
import com.wipro.fhir.r4.utils.exception.FHIRException;
import com.wipro.fhir.r4.utils.mapper.InputMapper;

/***
 * 
 * @author NE298657
 *
 */

@Service
@PropertySource("classpath:application.properties")
public class APIChannelImpl implements APIChannel {
	static RestTemplate restTemplate;

	@Value("${benSearchByBenIDURL}")
	private String benSearchByBenIDURL;

	@Value("${userAuthURL}")
	private String userAuthURL;

	@Value("${fhirUserName}")
	private String fhirUserName;

	@Value("${fhirPassword}")
	private String fhirPassword;

	/**
	 * search patient by beneficiary ID
	 */
	@Override
	public String benSearchByBenID(String Authorization, ResourceRequestHandler resourceRequestHandler)
			throws FHIRException {
		// search URL - will be configured in property file
		// String searchBenByBenIDURL =
		// "http://10.208.122.38:8080/commonapi-v1.0/beneficiary/searchUserByID";
		String responseBody = null;
		if (restTemplate == null)
			restTemplate = new RestTemplate();

		MultiValueMap<String, String> header = getHttpHeader(Authorization, "application/json");
		HttpEntity<Object> urlRequestOBJ = new HttpEntity<Object>(resourceRequestHandler, header);

		ResponseEntity<String> response = restTemplate.exchange(benSearchByBenIDURL, HttpMethod.POST, urlRequestOBJ,
				String.class);

		if (response.getStatusCodeValue() == 200 && response.hasBody()) {
			responseBody = response.getBody();
		} else {
			throw new FHIRException("error in patient search");
		}

		return responseBody;

	}

	// Authentication mechanism
	@Override
	public String userAuthentication() throws FHIRException {
		String responseBody = null;
		String authKey = null;

		// String userAuthenticationURL =
		// "http://10.208.122.38:8080/commonapi-v1.0/user/userAuthenticate";
		Map<String, String> userDetails = new HashMap<String, String>();
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setAlgorithm("PBEWithMD5AndDES");
		encryptor.setPassword("dev-env-secret");
		String decryptUserName = encryptor.decrypt(fhirUserName);
		String decryptPassword = encryptor.decrypt(fhirPassword);
		
		
		userDetails.put("userName", decryptUserName);
		userDetails.put("password", decryptPassword);

		// System.out.println(userDetails.get("userName"));
		// System.out.println(userDetails.get("password"));

		if (restTemplate == null)
			restTemplate = new RestTemplate();

		MultiValueMap<String, String> header = getHttpHeader(null, "application/json");
		HttpEntity<Object> urlRequestOBJ = new HttpEntity<Object>(userDetails, header);

		ResponseEntity<String> response = restTemplate.exchange(userAuthURL, HttpMethod.POST, urlRequestOBJ,
				String.class);

		if (response != null && response.getStatusCodeValue() == 200) {
			responseBody = response.getBody();

			if (responseBody != null) {
				UserAuthAPIResponse psr = (InputMapper.gson().fromJson(responseBody, UserAuthAPIResponse.class));
				if (psr != null) {
//					System.out.println("-----------------");
//					System.out.println(psr);
//					System.out.println(psr.getData().getIsAuthenticated());
//					System.out.println(psr.getData().getStatus());
//					System.out.println(psr.getData().getKey());
//					System.out.println("-----------------");
					if (psr.getData().getIsAuthenticated() && psr.getData().getStatus().equalsIgnoreCase("Active"))
						authKey = psr.getData().getKey();
				}
			}
		}
		return authKey;
	}

	private MultiValueMap<String, String> getHttpHeader(String Authorization, String contentType) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();

		if (contentType != null)
			headers.add("Content-Type", contentType);
		else
			headers.add("Content-Type", "application/json");

		if (Authorization != null)
			headers.add("AUTHORIZATION", Authorization);
		return headers;
	}

}
