package com.twodollar.sample.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.twodollar.support.webutils.DataUnit;


@Repository
public class BoardDAO extends DataUnit
{


	public int boardInsert(HashMap<String, Object> requestMap) {
		
		int cnt = insert("BoardDAO.insertBoard", requestMap);
		return cnt;
	}

	public List<Map<String, Object>> boardRead(HashMap<String, Object> requestMap) {
		
		List<Map<String, Object>> list = selectList("BoardDAO.readBoard", requestMap);
		return list;
	}
	
	public List<Map<String, Object>> messengerCnt(HashMap<String, Object> requestMap) {
		
		List<Map<String, Object>> list = selectList("BoardDAO.boardCnt", requestMap);
		return list;
	}

	public List<Map<String, Object>> messengerRead1(HashMap<String, Object> requestMap) {

		List<Map<String, Object>> list = selectList("BoardDAO.readMessenger1", requestMap);
		return list;
	}

	public int boardDelete(HashMap<String, Object> requestMap) {

		int num = delete("BoardDAO.deleteBoard", requestMap);
		return num;
	}

	public List<Map<String, Object>> boardUpdate(HashMap<String, Object> requestMap) {
		
		List<Map<String, Object>> list = selectList("BoardDAO.updateBoard", requestMap);
		return list;
	}

	public int boardMemoInsert(HashMap<String, Object> requestMap) {

		int cnt = insert("BoardDAO.insertBoardMemo", requestMap);
		return cnt;
	}

	public List<Map<String, Object>> boardMemoRead(HashMap<String, Object> requestMap) {

		List<Map<String, Object>> list = selectList("BoardDAO.readBoardMemo", requestMap);
		return list;
	}

	public int boardMemoDelete(HashMap<String, Object> requestMap) {

		int num2 = delete("BoardDAO.deleteBoardMemo", requestMap);
		return num2;
	}
}
