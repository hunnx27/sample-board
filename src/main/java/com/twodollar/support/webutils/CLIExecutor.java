package com.twodollar.support.webutils;

import java.io.*;
import java.util.*;

public class CLIExecutor {
	
	/**
     * cmd 명령어 실행(void)
     *
     * @param cmd
     */
	public static void execute(String cmd) {
		executeMap(cmd);
	}
	/**
     * cmd 명령어 실행(String)
     *
     * @param cmd
     */
	public static String executeString(String cmd) {
		Map<String,Object> result = executeMap(cmd);
		String resultMsg = result.get("resultMsg")!=null? (String)result.get("resultMsg") : "";
		return resultMsg;
	}
    /**
     * cmd 명령어 실행(Map)
     *
     * @param cmd
     */
    public static Map<String,Object> executeMap(String cmd) {
    	Map<String,Object> resultMap = new HashMap<>();
    	String resultMsg = "";
        Process process = null;
        Runtime runtime = Runtime.getRuntime();
        StringBuffer successOutput = new StringBuffer(); // 성공 스트링 버퍼
        StringBuffer errorOutput = new StringBuffer(); // 오류 스트링 버퍼
        BufferedReader successBufferReader = null; // 성공 버퍼
        BufferedReader errorBufferReader = null; // 오류 버퍼
        String msg = null; // 메시지
 
        List<String> cmdList = new ArrayList<String>();
 
        // 운영체제 구분 (window, window 가 아니면 무조건 linux 로 판단)
        if (System.getProperty("os.name").indexOf("Windows") > -1) {
            cmdList.add("cmd");
            cmdList.add("/c");
            // cmdList.add("cd C:\\Users\\박희운\\AppData\\Local\\UiPath\\app-21.4.4\\UiPathAssistant");
        } else {
            cmdList.add("/bin/sh");
            cmdList.add("-c");
        }
        // 명령어 셋팅
        cmdList.add(cmd);
        String[] array = cmdList.toArray(new String[cmdList.size()]);
 
        try {
 
            // 명령어 실행
            process = runtime.exec(array);
 
            // shell 실행이 정상 동작했을 경우
            //successBufferReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            successBufferReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "euc-kr"));
 
            while ((msg = successBufferReader.readLine()) != null) {
                successOutput.append(msg + System.getProperty("line.separator"));
            }
 
            // shell 실행시 에러가 발생했을 경우
            // errorBufferReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));
            errorBufferReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), "euc-kr"));
            while ((msg = errorBufferReader.readLine()) != null) {
                errorOutput.append(msg + System.getProperty("line.separator"));
            }
 
            // 프로세스의 수행이 끝날때까지 대기
            process.waitFor();
 
            // shell 실행이 정상 종료되었을 경우
            if (process.exitValue() == 0) {
                System.out.println("성공");
                resultMsg = successOutput.toString();
                resultMap.put("status", "001");//success
                resultMap.put("resultMsg", resultMsg);
                System.out.println(resultMsg);
            } else {
                // shell 실행이 비정상 종료되었을 경우
                System.out.println("비정상 종료");
                resultMsg +="Execution Status : 비정상 종료\r";
                resultMsg +=successOutput.toString();
                resultMsg +=errorOutput.toString();
                resultMap.put("status", "002");//fail
                resultMap.put("resultMsg", resultMsg);
                System.out.println(resultMsg);
            }
 
 
        } catch (IOException e) {
            e.printStackTrace();
            resultMsg = e.getMessage();
            resultMap.put("status", "002");//fail
            resultMap.put("resultMsg", resultMsg);
        } catch (InterruptedException e) {
            e.printStackTrace();
            resultMsg = e.getMessage();
            resultMap.put("status", "002");//fail
            resultMap.put("resultMsg", resultMsg);
        } finally {
            try {
                process.destroy();
                if (successBufferReader != null) successBufferReader.close();
                if (errorBufferReader != null) errorBufferReader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
                resultMap.put("status", "002");//fail
                resultMap.put("resultMsg", resultMsg);
            }
        }
        return resultMap;
    }
}
