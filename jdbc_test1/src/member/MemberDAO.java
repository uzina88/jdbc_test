package member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MemberDAO {

	
	// 드라이버 설정
	final String driver = "org.mariadb.jdbc.Driver";
	// 아이피 설정
	final String db_ip = "localhost";
	// 포트 설정
	final String db_port = "3306";
	// 데이터 이름 설정
	final String db_name = "jdbc_test";
	// 데이터 url 설정
	final String db_url = "jdbc:mariadb://"+db_ip+":"+db_port+"/"+db_name; 
	
	// 연결
	Connection conn = null;
	// 쿼리 실행
	PreparedStatement pstmt = null;
	// 결과값 담기
	ResultSet rs = null;

	
	// 1. 회원정보 등록
	public int insertMember(Member member) {
		int chk = 0;

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속성공");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		// 데이터 베이스 접속 끝
		// 쿼리 작성 시작
		try {
			String sql = "INSERT INTO tb_member_info(\r\n"
					+ "	member_id,\r\n"
					+ "    member_pw,\r\n"
					+ "    member_name,\r\n"
					+ "    member_birth,\r\n"
					+ "    member_email,\r\n"
					+ "    member_phone\r\n"
					+ ") VALUES (\r\n"
					+ "	?,?,?,?,?,?\r\n"
					+ ");";


			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getMemberBirth());
			pstmt.setString(5, member.getMemberEmail());
			pstmt.setString(6, member.getMemberPhone());
			
			// rs =  pstmt.executeQuery 는 셀렉트(실행) 할때 사용
			chk = pstmt.executeUpdate();
			
		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println("error :" + e);
		}finally {
			try {
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
		
		return chk;
	}
	
	
	// 2. 도서정보 수정(update, insert, delect는 public int로 해야함
	public int updateMember(int MemberIdx, String updateName) {
	// 초기값 0으로 설정
		int chk = 0;
		// 데이터 베이스 접속 시작
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속성공");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		// 쿼리문 실행
		try {
			
			String sql = "UPDATE tb_member_info\r\n"
					+ "SET member_name = ?\r\n"
					+ "WHERE member_idx =?;";

			pstmt = conn.prepareStatement(sql);
			// ? 물음표에 대한 값 설정
			pstmt.setString(1,updateName);
			pstmt.setInt(2,MemberIdx);
			// ? 물음표에 대한 값 설정
			chk = pstmt.executeUpdate();
		
		
		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println("error :" + e);
		}finally {
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
		
		return chk;
	}
	
	// 3.회원정보 삭제
	public int deleteMember(int MemberIdx, String deleteName) {
		// 초기값 0으로 설정
		int chk = 0;
		// 데이터 베이스 접속 시작
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속성공");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		// 쿼리문 실행
		try {
			
			String sql = "DELETE FROM tb_member_info WHERE member_idx = ?;";

			pstmt = conn.prepareStatement(sql);
			// ? 물음표에 대한 값 설정
			pstmt.setInt(1,MemberIdx);
			pstmt.setString(2,deleteName);
			// ? 물음표에 대한 값 설정
			chk = pstmt.executeUpdate();
		
		
		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println("error :" + e);
		}finally {
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
		
		return chk;
	}
	
	
	//4. 회원정보 검색 출력
	public List<HashMap<String, Object>> printMember(String name){
		   // 리스트 초기화 선언(담아둘 공간 생성)
		   List<HashMap<String, Object>> memberList = new ArrayList();
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속성공");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		
		try {
			String sql = "SELECT member_idx,\r\n"
					+ "member_id,\r\n"
					+ "member_pw,\r\n"
					+ "member_name,\r\n"
					+ "member_birth,\r\n"
					+ "member_email,\r\n"
					+ "member_phone\r\n"
					+ "FROM tb_member_info\r\n"
					+ "WHERE member_name = ?;";


			pstmt = conn.prepareStatement(sql);
			// ? 물음표에 대한 값 설정
			pstmt.setString(1,name);
			rs = pstmt.executeQuery();
		
			
			while(rs.next()) {
				HashMap<String, Object> rsMap = new HashMap<String, Object>();
				rsMap.put("member_idx", rs.getString("member_idx")); 
				rsMap.put("member_id", rs.getString("member_id")); 
				rsMap.put("member_pw", rs.getString("member_pw"));
				rsMap.put("member_name", rs.getString("member_name"));
				rsMap.put("member_birth", rs.getString("member_birth"));
				rsMap.put("member_email", rs.getString("member_email"));
				rsMap.put("member_phone", rs.getString("member_phone"));
				
				memberList.add(rsMap);

			}
		
		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println("error :" + e);
		}finally {
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
		
		return memberList;
	}
	
	
	// 5. 회원정보 모든 출력
	public List<HashMap<String, Object>> printAllMembers(){
		
		// 사용하기 위해 초기화 선언
		List<HashMap<String, Object>> memberList = new ArrayList();
				
		int chk = 0;

		// 데이터 연결
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(db_url, "root", "1234");
			if(conn != null) {
				System.out.println("접속성공");
			}
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		}catch(SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		}
		// 쿼리 입력
		try {
			String sql = "SELECT member_id,\r\n"
					+ "member_pw,\r\n"
					+ "member_name,\r\n"
					+ "member_birth,\r\n"
					+ "member_email,\r\n"
					+ "member_phone\r\n"
					+ "FROM tb_member_info;";

			pstmt = conn.prepareStatement(sql);
			// rs = pstmt.executeQuery(); 값을 담아줌(셀렉트 할때만 사용)
			rs = pstmt.executeQuery();
		
			// while 참일 때 계속 돌아감, 거짓일 때 멈춤
			// rs.next()는 다음게 있는지 반복
			while(rs.next()) {
				// HashMap 바구니안에 값을 넣어줘라 key값과 value값으로 구성
				// String 문자, Object 객체(아무거나 상관없이 들어감)
				HashMap<String, Object> rsMap = new HashMap<String, Object>();
				rsMap.put("member_id", rs.getString("member_id")); 
				rsMap.put("member_pw", rs.getString("member_pw"));
				rsMap.put("member_name", rs.getString("member_name"));
				rsMap.put("member_birth", rs.getString("member_birth"));
				rsMap.put("member_email", rs.getString("member_email"));
				rsMap.put("member_phone", rs.getString("member_phone"));
				
				// HashMap 리스트 바구니에 값음 넣어줌
				memberList.add(rsMap);
			}
		
		}catch (SQLException e) {
			// TODO: handle exception
			System.out.println("error :" + e);
		}finally {
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
		// 값을 반환(담아줌) 
		// 여러데이터를 반환해야 하기 떄문에 memberList 이걸로 담아줌
		return memberList;
	}
	
	
	
	
}
