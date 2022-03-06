package com.twodollar.sample.alaram.service;

import com.google.gson.Gson;
import com.twodollar.sample.config.RequestFactoryBasicAuth;
import org.apache.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;


@Service
public class PUMattermost
{
	private static final Logger logger = LoggerFactory.getLogger(PUMattermost.class);

//	@Autowired
//	private DUMessengerHistory dumh;

	/**
	 * Mattermost 전송 프로세스
	 * @param requestMap
	 * @param configMap
	 * @return
	 */
	public HashMap<String,Object> chatPostMessage(HashMap<String,Object> requestMap, HashMap<String,Object> configMap){
		HashMap<String,Object> resultMap = new HashMap<>();
		HashMap<String,Object> messageResult = new HashMap<>();
		HashMap<String,Object> histMap = new HashMap<>();
		String ok = "N";
		boolean NoSave = false;
		String error = "";
		if(configMap!= null) {
			
			//메시지 전송
			Map<String,Object> queryParam = (Map<String, Object>) configMap.clone();
			queryParam.put("text", requestMap.get("snd_msg"));
			messageResult = this.chatPostMessage(queryParam);
			
			//결과 처리
			String message = "";
			ok = messageResult.get("ok")!=null? String.valueOf(messageResult.get("ok")) : "N";
			message = String.valueOf(messageResult.get("message"));
			resultMap.put("ok", ok);
			resultMap.put("message", message);
		}else {
			error = "저장된 Mattermost 설정정보가 없습니다. 설정을 먼저 등록하세요.";
			NoSave = true;
			logger.info(error);
			resultMap.put("ok", false);
			resultMap.put("message", error);
		}
		if(!NoSave) {
			// Mattermost 이력 저장
			logger.info("#Mattermost 이력 저장");
			histMap.put("채널ID", requestMap.get("채널ID"));
			histMap.put("요청자", requestMap.get("snd_req_id"));
			histMap.put("메시지", requestMap.get("snd_msg"));
			histMap.put("성공여부", String.valueOf(ok));
			histMap.put("로그", resultMap.get("message"));
			histMap.put("등록자", "System");
			histMap.put("수정자", "System");
//			dumh.messengerhistoryInsert(histMap);
		}
		
		return resultMap;
	}
	/**
	 * 메시지 채팅 전송
	 * @param requestMap
	 * @return
	 */
	public HashMap<String,Object> chatPostMessage(Map<String,Object> requestMap){
		// 데이터 셋팅
		HashMap<String,Object> messageResult = new HashMap<>();
		String url = String.valueOf(requestMap.get("훅주소"));
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);

		RestTemplate restTemplate = new RestTemplate();
		HttpHost host = new HttpHost(url);
		final ClientHttpRequestFactory requestFactory = new RequestFactoryBasicAuth(host);
		restTemplate = new RestTemplate(requestFactory);

		//Header
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		//Content-Type: application/x-www-form-urlencoded
		//headers.add("Content-Type", "application/x-www-form-urlencoded");
		// FIXME : 인코딩설정 필요
		//Body
		Map<String,Object> bodyJson = new HashMap<>();
		bodyJson.put("text", requestMap.get("text"));
		Gson gson = new Gson();
		String bodyJsonString = gson.toJson(bodyJson);
		//Set Entity
		HttpEntity<String> requestEntity =
				new HttpEntity<String>(bodyJsonString, headers);

		//restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor("admin", "admin")); //인증
		try {
			ResponseEntity<String> response =
					restTemplate.exchange(
							builder.buildAndExpand(requestMap).toUri(),					/*URI*/
							HttpMethod.POST,												/*Method*/
							requestEntity,														/*requestEntity*/
							new ParameterizedTypeReference<String>(){}	/*responseType*/
							);
			String result = response.getBody();
			if("ok".equals(result)){
				messageResult.put("ok", "Y");
				messageResult.put("message", result);
			}else{
				messageResult.put("ok", "N");
				messageResult.put("message", result);
			}
		}catch(Exception e) {
			logger.info("REST Template 전송 오류 : " + e.getMessage());
			messageResult.put("ok", "N");
			messageResult.put("message", "RestTemplate 전송 오류(" + e.getMessage() + ")");
		}
		return messageResult;
	}

}
