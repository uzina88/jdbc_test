package jdbc_00;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class jdbc_test1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// 드라이버 설정
		final String driver = "org.mariadb.jdbc.Driver";
		// 아이피 설정
		final String db_ip = "localhost";
		// 포트 설정
		final String db_port = "3306";
		// 데이터 이름 설정
		final String db_name = "student_test";
		// 데이터 url 설정
		final String db_url = "jdbc:mariadb://"+db_ip+":"+db_port+"/"+db_name; 
		
		// 연결
		Connection conn = null;
		// 쿼리 실행
		PreparedStatement pstmt = null;
		// 결과값 담기
		ResultSet rs = null;
		
		// 에러 발생 시 처리문
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속성공");
			}
			
		// 에러가 발생할 경우 실행	
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(Exception e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		
		// 쿼리 실행
		try {
			String sql = "SELECT school_id, school_name, school_area "
					   + "FROM tb_school_info ";
						// + "WHERE school_id = 1" (한개 출력 할때)
			System.out.println(sql);
			
			// 쿼리에 데이터 담아줌
			pstmt = conn.prepareStatement(sql);
			
			// 담고 있는 pstmt 를 실행하고 rs에 담아줌
			rs = pstmt.executeQuery();
			List<HashMap<String, Object>> list = new ArrayList();
			
			// 한개 출력할 때
			//	int schoolId = 0;
			//	String schoolName = null;
			//	String schoolArea = null;
			
			// rs 안에 있는 결과값을 반복해서 보여줘라
			while(rs.next()){
				HashMap<String, Object> rsMap = new HashMap<String, Object>();
				rsMap.put("schoolId", rs.getInt(1));
				rsMap.put("schoolName", rs.getString(2));
				rsMap.put("schoolArea", rs.getString(3));
				list.add(rsMap);
				//  한개 출력할 때
				//	schoolId = rs.getInt(1);
				//	schoolName = rs.getString("school_name");
				//	schoolArea = rs.getString(3);
			}
			System.out.println("학교ID\t학교이름\t학교지역");
			for(int i=0; i<list.size(); i++) {
				System.out.println(list.get(i).get("schoolId")+"\t"
								  +list.get(i).get("schoolName")+"\t"
								  +list.get(i).get("schoolArea"));
			}
			//  한개 출력할 때
			//	System.out.println("schooId : "+schoolId);
			//	System.out.println("schooId : "+schoolName);
			//  System.out.println("schooId : "+schoolArea);
						
		}catch(SQLException e) {
			
			System.out.println("erro :" + e);
			
		// 닫아주는 작업	
		}finally{
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null && conn.isClosed()) {
					conn.close();
				}
			}catch(SQLException e) {
				
				e.printStackTrace();
			}
		}
	}

}
