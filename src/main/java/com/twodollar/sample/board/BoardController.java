package com.twodollar.sample.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardController {

	@Autowired
	private BoardDAO boardDAO;

	@GetMapping(path = "/board")
	public ResponseEntity<HashMap<String, Object>> messengerRead(@RequestParam Map<String, Object> param) {

		ResponseEntity<HashMap<String, Object>> entity = null;

		HashMap<String, Object> requestMap = new HashMap<>();
		HashMap<String, Object> resultMap = new HashMap<>();


		requestMap = (HashMap)param;

		List<Map<String, Object>> resultListMap = boardDAO.boardRead(requestMap);

		List<Map<String, Object>> listTotalCnt = boardDAO.messengerCnt(requestMap);

		resultMap.put("total_cnt", listTotalCnt.get(0).get("tot_cnt"));

		resultMap.put("list", resultListMap);
		entity = new ResponseEntity<HashMap<String, Object>>(resultMap,HttpStatus.OK);

		return entity;

	}

	@RequestMapping(value = "/board", method = RequestMethod.PUT ,produces="application/json; charset=UTF-8")
	public ResponseEntity<HashMap<String, Object>> messengerUpdate(@RequestParam Map<String, Object> param ) {

		ResponseEntity<HashMap<String, Object>> entity = null;
		HashMap<String, Object> requestMap = new HashMap<>();
		HashMap<String, Object> resultMap = new HashMap<>();


		requestMap = (HashMap)param;

		List<Map<String, Object>> resultListMap = boardDAO.boardUpdate(requestMap);

		resultMap.put("list", resultListMap);
		entity = new ResponseEntity<HashMap<String, Object>>(resultMap,HttpStatus.OK);

		return entity;


	}

	@RequestMapping(method = RequestMethod.POST, path = "/board")
	public ResponseEntity<HashMap<String, Object>> messengerInsert(@RequestParam Map<String, Object> param ) {

		ResponseEntity<HashMap<String, Object>> entity = null;

		HashMap<String, Object> requestMap = new HashMap<>();
		HashMap<String, Object> resultMap = new HashMap<>();


		requestMap = (HashMap)param;

//		List<Map<String, Object>> resultListMap = du.messengerInsert(requestMap);
		int cnt = boardDAO.boardInsert(requestMap);
		entity = new ResponseEntity<HashMap<String, Object>>(resultMap,HttpStatus.OK);

		return entity;
	}


	@GetMapping(path = "/board1")
	public ResponseEntity<HashMap<String, Object>> messengerRead1(@RequestParam Map<String, Object> param) {

		ResponseEntity<HashMap<String, Object>> entity = null;
		HashMap<String, Object> requestMap = new HashMap<>();
		HashMap<String, Object> resultMap = new HashMap<>();


		requestMap = (HashMap)param;
		List<Map<String, Object>> resultListMap = boardDAO.messengerRead1(requestMap);
		resultMap.put("list", resultListMap);
		entity = new ResponseEntity<HashMap<String, Object>>(resultMap,HttpStatus.OK);

		return entity;

	}

	@DeleteMapping("/board/{uid}")
	public ResponseEntity<HashMap<String, Object>> messengerDelete(@PathVariable("uid") Integer uid) {

		Map<String, Object> param = new HashMap<>();
		param.put("uid", uid);


		ResponseEntity<HashMap<String, Object>> entity = null;
		HashMap<String, Object> requestMap = new HashMap<>();
		HashMap<String, Object> resultMap = new HashMap<>();


		requestMap = (HashMap)param;
		int num = boardDAO.boardDelete(requestMap);
		entity = new ResponseEntity<HashMap<String, Object>>(resultMap,HttpStatus.OK);

		return entity;
	}

	@GetMapping(path = "/boardmemo")
	public ResponseEntity<HashMap<String, Object>> boardMemoRead(@RequestParam Map<String, Object> param) {

		ResponseEntity<HashMap<String, Object>> entity = null;

		HashMap<String, Object> requestMap = new HashMap<>();
		HashMap<String, Object> resultMap = new HashMap<>();


		requestMap = (HashMap)param;

		List<Map<String, Object>> resultListMap = boardDAO.boardMemoRead(requestMap);

		resultMap.put("list", resultListMap);
		entity = new ResponseEntity<HashMap<String, Object>>(resultMap,HttpStatus.OK);

		return entity;

	}
	@RequestMapping(method = RequestMethod.POST, path = "/boardmemo")
	public ResponseEntity<HashMap<String, Object>> boardMemoInsert(@RequestParam Map<String, Object> param ) {

		ResponseEntity<HashMap<String, Object>> entity = null;

		HashMap<String, Object> requestMap = new HashMap<>();
		HashMap<String, Object> resultMap = new HashMap<>();


		requestMap = (HashMap)param;

		int cnt = boardDAO.boardMemoInsert(requestMap);

		entity = new ResponseEntity<HashMap<String, Object>>(resultMap,HttpStatus.OK);

		return entity;
	}
	@DeleteMapping("/boardmemo/{memo_uid}")
	public ResponseEntity<HashMap<String, Object>> boardMemoDelete(@PathVariable("memo_uid") Integer memo_uid) {

		Map<String, Object> param = new HashMap<>();
		param.put("memo_uid", memo_uid);


		ResponseEntity<HashMap<String, Object>> entity = null;

		HashMap<String, Object> requestMap = new HashMap<>();
		HashMap<String, Object> resultMap = new HashMap<>();


		requestMap = (HashMap)param;

		int num2 = boardDAO.boardMemoDelete(requestMap);

		entity = new ResponseEntity<HashMap<String, Object>>(resultMap,HttpStatus.OK);

		return entity;
	}

}


