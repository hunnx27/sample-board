package com.twodollar.support.webutils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

public class ExportExcelVOList {
	private HttpServletRequest req;
	private HttpServletResponse res;
	protected HSSFSheet[] sheetArray;
	protected HSSFWorkbook wb;
	protected DownloadExcel downloadExcel;
	protected String colId[];
	protected String colN[];
	protected String fileName;
	protected CellStyle headStyle;
	protected CellStyle bodyStyle;

	public ExportExcelVOList(HttpServletRequest request, HttpServletResponse response) {
		this.req = request;
		this.res = response;
		this.sheetArray = new HSSFSheet[1];
		this.wb = new HSSFWorkbook();
		
		this.colN = request.getParameter("col_names").toString().split(",");
		this.colId = request.getParameter("col_ids").toString().split(",");
		this.fileName = request.getParameter("file_name").toString();
		
		this.setExcelFormat();
	}
	
	/* 필수 exl.setListData 로 가져온 HSSFSheet를 넘겨줘야함 */
	public void setSheetArray(int index, HSSFSheet st) {
		sheetArray[index] = st;
	}
	
	/* 필수 모든 세팅이 완료된 후 엑셀 다운로드 받는 메소드 */
	public void actionDownloadExcel() {
		downloadExcel.writeExcel();
	}
	
	/* 파라메터로 넘어온 엑셀명 혹은 다운받은 일시를 파일명으로 엑셀파일 생성*/
	private void setExcelFormat(){
		long time = System.currentTimeMillis();
		SimpleDateFormat day = new SimpleDateFormat("yyyyMMddhhmmss");
		String excelnm = day.format(time);
		this.downloadExcel = new DownloadExcel(fileName+".xls", req, res, wb);
		this.headStyle = downloadExcel.headStyle();
		this.bodyStyle = downloadExcel.bodyStyle();
	}
	
	/* 컬럼 스타일 세팅 및 컬럼제목 입력*/
	private void setColumnStyle(HSSFSheet st, HSSFRow title, int width, int addIdx){
		for (int i = 0; i < colN.length; i++) {
			title.createCell(i + addIdx).setCellValue(colN[i].replaceAll("&gt;=", ">="));
			title.getCell(i + addIdx).setCellStyle(headStyle);
			st.setColumnWidth(i + addIdx, width);
		}
	}
	
	/* VO data를 파라메터로 생성할 각 시트에 세팅*/ 
	public HSSFSheet setDataToExcelList(List<?> data, String sheetName) {
		HSSFSheet st = null;
		try {
			st = wb.createSheet(sheetName);
			HSSFRow titleRow = st.createRow(0);
			titleRow.setHeight((short) 400);
			setColumnStyle(st, titleRow, 6000, 0);	// colN을 이용한 컬럼명 처리
			for (int i = 0; i < data.size(); i++) {
				HSSFRow row = st.createRow(st.getLastRowNum() + 1);
				row = setRowData(row, data.get(i), 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return st;
	}
	
	
	/* VO data를 파라메터로 해당 데이터 셀에 입력*/ 
	private HSSFRow setRowData(HSSFRow row, Object data, int stIdx) throws IllegalArgumentException, IllegalAccessException{
		int edIdx = 0;
		for(Field field : data.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			Object value = field.get(data);
			row.createCell(stIdx+edIdx).setCellValue(value.toString());
			edIdx++;
		}
		for(int i=0; i<edIdx; i++){
			row.getCell(stIdx+i).setCellStyle(bodyStyle);
		}
		return row;
	}
	
	
	/* List<Map<String, Object>> data를 파라메터로 생성할 각 시트에 세팅*/ 
	public HSSFSheet setListMapToExcelList(List<Map<String, Object>> data, String sheetName) {
		HSSFSheet st = null;
		try {
			st = wb.createSheet(sheetName);
			HSSFRow titleRow = st.createRow(0);
			titleRow.setHeight((short) 400);
			setColumnStyle(st, titleRow, 6000, 0);	// colN을 이용한 컬럼명 처리
			for (int i = 0; i < data.size(); i++) {
				HSSFRow row = st.createRow(st.getLastRowNum() + 1);
				row = setRowDataMap(row, data.get(i), 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return st;
	}
	
	/* Map를 파라메터로 해당 데이터 셀에 입력*/ 
	private HSSFRow setRowDataMap(HSSFRow row, Map<String, Object> map , int stIdx) throws IllegalArgumentException, IllegalAccessException{
		int edIdx = 0;
		
		for(String keyName: colId) {
            String valueName = (String) map.get(keyName);
            row.createCell(stIdx+edIdx).setCellValue(valueName.toString());
			edIdx++;
		}
		
//		Set key = map.keySet(); 
//		for (Iterator iterator = key.iterator(); iterator.hasNext();) {
//            String keyName = (String) iterator.next();
//            String valueName = (String) map.get(keyName);
//
//            System.out.println(keyName +" = " +valueName);
//            
//            row.createCell(stIdx+edIdx).setCellValue(valueName.toString());
//			edIdx++;
//		}
//		

		for(int i=0; i<edIdx; i++){
			row.getCell(stIdx+i).setCellStyle(bodyStyle);
		}
		return row;
	}
	

}