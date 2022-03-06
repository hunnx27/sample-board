package com.twodollar.support.webutils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DataUnit {

	@Autowired
	private SqlSession sqlSession;


	private final Logger log = LoggerFactory.getLogger(this.getClass());


	protected int insert(String sqlId, HashMap<String, Object> requstMap) {

		Map<String, Object> paramMap = requstMap;
		sqlLogging(sqlId, paramMap);

		return sqlSession.insert(sqlId, requstMap);
	}


	protected List<Map<String, Object>> selectList(String sqlId, HashMap<String, Object> requstMap) {
		Map<String, Object> paramMap = requstMap;

		sqlLogging(sqlId, paramMap);

		return sqlSession.selectList(sqlId, requstMap);
	}

	protected Map<String, Object> selectOne(String sqlId, HashMap<String, Object> requstMap) {
		Map<String, Object> paramMap = requstMap;

		sqlLogging(sqlId, paramMap);

		return sqlSession.selectOne(sqlId, requstMap);
	}



	protected int update(String sqlId, HashMap<String, Object> requstMap) {
		Map<String, Object> paramMap = requstMap;
		sqlLogging(sqlId, paramMap);

		return sqlSession.update(sqlId, requstMap);
	}



	protected int delete(String sqlId, HashMap<String, Object> requstMap) {
		Map<String, Object> paramMap = requstMap;

		sqlLogging(sqlId, paramMap);

		return sqlSession.delete(sqlId, requstMap);
	}


	private void sqlLogging(String sqlId, Map<String, Object> paramMap) {
		String sql = sqlSession.getConfiguration().getMappedStatement(sqlId).getBoundSql(paramMap).getSql();
        List<ParameterMapping> parameterMappings = sqlSession.getConfiguration().getMappedStatement(sqlId).getBoundSql(paramMap).getParameterMappings();

        for (ParameterMapping parameterMapping : parameterMappings) {
            String param = (String) paramMap.get(parameterMapping.getProperty());
            sql = sql.replaceFirst("\\?", "'" + param + "'");
        }

//        System.out.println(" :: Sql Pram정보   :: ==> " + paramMap+"\n");
//
//        System.out.println(" :: Sql Exe 정보   :: ==> " + sqlId+"\n");
//        System.out.println(sql);
//        System.out.println("\n");
//        System.out.println("\n");
//
//

//        if(log.isDebugEnabled()){// && log.isErrorEnabled()){
//			StringBuilder builder = new StringBuilder();
//			builder.append("[" + sqlId + "]");
//			builder.append(String.format("\n%s", "################################################################################################################"));
//			builder.append(String.format("\n%s\n", "-------PARAMETER------------------------------------------------------------------------------------------------"));
//			builder.append(paramMap);
//			builder.append(String.format("\n%s\n", "-------QUERY----------------------------------------------------------------------------------------------------"));
//            builder.append(sql == null ? "SQL is null" : sql);
//            builder.append(String.format("\n%s\n", "################################################################################################################"));
//            log.debug(builder.toString());
//        }


		StringBuilder builder = new StringBuilder();
		builder.append("[" + sqlId + "]");
		builder.append(String.format("\n%s", "################################################################################################################"));
		builder.append(String.format("\n%s\n", "-------PARAMETER------------------------------------------------------------------------------------------------"));
		builder.append(paramMap);
		builder.append(String.format("\n%s\n", "-------QUERY----------------------------------------------------------------------------------------------------"));
        builder.append(sql == null ? "SQL is null" : sql);
        builder.append(String.format("\n%s\n", "################################################################################################################"));
        System.out.println(builder);


	}




}
