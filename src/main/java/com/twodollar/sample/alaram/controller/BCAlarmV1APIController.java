package com.twodollar.sample.alaram.controller;

import java.util.List;

import com.twodollar.sample.alaram.service.PUMattermost;
import com.twodollar.sample.alaram.service.PUSlack;
import com.twodollar.sample.alaram.vo.ChatPostRequest;
import com.twodollar.sample.board.BoardDAO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/alarm")
public class BCAlarmV1APIController {

    @Autowired
    private PUMattermost puMattermost;

    @Autowired
    private PUSlack puSlack;

    @Autowired
    private BoardDAO du;


    /**
     * MatterMost 전송 API
     * @param param
     * @return
     */
    @PostMapping(
            path = "/mattermost/chat.postMessage",
            produces="application/json; charset=UTF-8"
    )
    @ApiOperation(value = "MatterMost 전송 메시지", tags = "mattermost")
    public ResponseEntity<HashMap<String, Object>> mattermostPostMessage(
            ChatPostRequest param
    ) {
        HashMap<String, Object> requestMap = new HashMap<>();
        HashMap<String, Object> resultMap = new HashMap<>();
        ResponseEntity<HashMap<String, Object>> entity = null;
        String sndReqId = param.getSnd_req_id();	//요청사용자 넣어서 체크 (등록자 처리), crtr_id, upd_id, user_id 등
        try{

//            requestMap.put("채널ID", param.getChannel_id());
//            Map<String, Object> configMap = du.boardRead1ByCID(requestMap);
//
//
//            requestMap.put("snd_msg", param.getSnd_msg());
//            requestMap.put("snd_req_id", param.getSnd_req_id());
//            resultMap = puMattermost.chatPostMessage(requestMap, (HashMap)configMap); // 메시지 전송
            entity = new ResponseEntity<HashMap<String, Object>>(resultMap, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            resultMap.put("ok", false);
            resultMap.put("error", "시스템오류("+e.getMessage()+")");
            entity = new ResponseEntity<HashMap<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        }

        return entity;
    }


    //jsondata -> Map
    //매터모스트 알림 처리
	@PostMapping(path = "/mattermost")
	public ResponseEntity<HashMap<String, Object>> msgMattermost(@RequestBody Map<String, Object> param) {

		HashMap<String, Object> requestMap = new HashMap<>();
        HashMap<String, Object> resultMap = new HashMap<>();
        ResponseEntity<HashMap<String, Object>> entity = null;

		requestMap = (HashMap)param;
		
        try{

//            requestMap.put("채널ID",requestMap.get("channel_id").toString() );
//            Map<String, Object> configMap = du.boardRead1ByCID(requestMap);
//
//            resultMap = puMattermost.chatPostMessage(requestMap, (HashMap)configMap); // 메시지 전송
            entity = new ResponseEntity<HashMap<String, Object>>(resultMap, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            resultMap.put("ok", false);
            resultMap.put("error", "시스템오류("+e.getMessage()+")");
            entity = new ResponseEntity<HashMap<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        }

        return entity;

	}

    @PostMapping(path = "/meeting")
    //@RequestMapping(value = "/meeting", method = RequestMethod.GET ,produces="application/json; charset=UTF-8")
	public ResponseEntity<HashMap<String, Object>> getMeeting(@RequestBody Map<String, Object> param) {

        ResponseEntity<HashMap<String, Object>> entity = null;

		HashMap<String, Object> requestMap = new HashMap<>();
        HashMap<String, Object> resultMap = new HashMap<>();

        String snd_msg = ""; 

        snd_msg =  "[PMS-회의실 예약 " + param.get("alram_type") + "]\n" +
        "회의실 예약이 " + param.get("alram_type") + " 되었습니다.\n" +
        "▶제목: " + param.get("subject") +
        "\n▶장소: " + param.get("meetroom_floor") + "층 " + param.get("meetroom_nm") +
        "\n▶시간: " +  param.get("reserve_date")  + " "  + param.get("start_time") + " ~ " + param.get("end_time"); // 요일 추가
        
        String sms_snd_msg = snd_msg;

        param.put("snd_msg", snd_msg);
        param.put("sms_snd_msg", sms_snd_msg);
        
        requestMap = (HashMap)param;

        try{         
            // 리스트, DB 저장
//            List<Map<String, Object>> list = du.insertMeeting(requestMap);
//
//            resultMap.put("list", list);

            // 메시지 전송
            entity = new ResponseEntity<HashMap<String, Object>>(resultMap, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            resultMap.put("ok", false);
            resultMap.put("error", "시스템오류("+e.getMessage()+")");
            entity = new ResponseEntity<HashMap<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        }

        return entity;

	}

    //jsondata -> Map
    //슬랙 알림 처리
	@PostMapping(path = "/slack")
	public ResponseEntity<HashMap<String, Object>> msgSlack(@RequestBody Map<String, Object> param) {

		HashMap<String, Object> requestMap = new HashMap<>();
        HashMap<String, Object> resultMap = new HashMap<>();
        ResponseEntity<HashMap<String, Object>> entity = null;

		requestMap = (HashMap)param;
		
        try{

//            requestMap.put("채널ID",requestMap.get("channel_id").toString() );
//            Map<String, Object> configMap = du.boardRead1ByCID(requestMap);
//
//            resultMap = puSlack.chatPostMessage(requestMap, (HashMap)configMap); // 메시지 전송
            entity = new ResponseEntity<HashMap<String, Object>>(resultMap, HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            resultMap.put("ok", false);
            resultMap.put("error", "시스템오류("+e.getMessage()+")");
            entity = new ResponseEntity<HashMap<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        }

        return entity;

	}

}
