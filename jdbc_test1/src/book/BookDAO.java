package book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class BookDAO {

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
	
	
	// 정상적으로 수행이 되는지 체크하기 위해 0으로 선언
	// 1. 도서정보 입력
	public int insertBook(BookInfo bookInfo) {
		// 초기값 0으로 설정
		int resultChk = 0;
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
		// 데이터 베이스 접속 끝
		// 쿼리 작성 시작
		try {
			String sql = "INSERT INTO tb_book_info(\r\n"
					+ "book_title,\r\n"
					+ "book_price,\r\n"
					+ "book_author,\r\n"
					+ "book_publisher,\r\n"
					+ "book_pubYear,\r\n"
					+ "book_isbn,\r\n"
					+ "book_page\r\n"
					+ ") VALUES (\r\n"
					+ "	?,?,?,?,?,?,?\r\n"
					+ ");";


			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bookInfo.getTitle());
			pstmt.setInt(2, bookInfo.getPrice());
			pstmt.setString(3, bookInfo.getAuthor());
			pstmt.setString(4, bookInfo.getPublisher());
			pstmt.setString(5, bookInfo.getPubYear());
			pstmt.setString(6, bookInfo.getIsbn());
			pstmt.setInt(7, bookInfo.getPage());
			
			// rs =  pstmt.executeQuery 는 셀렉트(실행) 할때 사용
			resultChk = pstmt.executeUpdate();
			
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
		
		return resultChk;
	}
	
	
	// 3. 도서정보 삭제
	public int deleteBook(String title) {
		// 초기값 0으로 설정
		int resultChk = 0;
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
			
			String sql = "DELETE FROM tb_book_info WHERE book_title = ?;";

			pstmt = conn.prepareStatement(sql);
			// ? 물음표에 대한 값 설정
			pstmt.setString(1,title);
			// ? 물음표에 대한 값 설정
			resultChk = pstmt.executeUpdate();
		
		
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
		
		return resultChk;
	}
	
	
	//4. 도서정보 검색 출력
	public List<HashMap<String, Object>> printSearchBooks(String title){
		   // 리스트 초기화 선언(담아둘 공간 생성)
		   List<HashMap<String, Object>> bookList = new ArrayList();
		
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
			String sql = "SELECT book_id,\r\n"
					+ "book_title,\r\n"
					+ "book_price,\r\n"
					+ "book_author,\r\n"
					+ "book_publisher,\r\n"
					+ "book_pubYear,\r\n"
					+ "book_isbn,\r\n"
					+ "book_page,\r\n"
					+ "create_date,\r\n"
					+ "update_date\r\n"
					+ "FROM tb_book_info\r\n"
					+ "WHERE book_title like concat('%', ?, '%');\r\n"
					+ "";


			pstmt = conn.prepareStatement(sql);
			// ? 물음표에 대한 값 설정
			pstmt.setString(1,title);
			rs = pstmt.executeQuery();
		
			
			while(rs.next()) {
				HashMap<String, Object> rsMap = new HashMap<String, Object>();
				rsMap.put("book_id", rs.getInt("book_id")); 
				rsMap.put("book_title", rs.getString("book_title"));
				rsMap.put("book_price", rs.getInt("book_price"));
				rsMap.put("book_author", rs.getString("book_author"));
				rsMap.put("book_publisher", rs.getString("book_publisher"));
				rsMap.put("book_pubYear", rs.getString("book_pubYear"));
				rsMap.put("book_isbn", rs.getString("book_isbn"));
				rsMap.put("book_page", rs.getInt("book_page"));
				rsMap.put("create_date", rs.getString("create_date"));
				rsMap.put("update_date", rs.getString("update_date"));
				bookList.add(rsMap);

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
		
		return bookList;
	}
	
	// 5. 도서정보 모든 출력
	public List<HashMap<String, Object>> printAllBooks(){
		// 사용하기 위해 초기화 선언
		List<HashMap<String, Object>> bookList = new ArrayList();
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
			String sql = "SELECT book_id,\r\n"
					+ "book_title,\r\n"
					+ "book_price,\r\n"
					+ "book_author,\r\n"
					+ "book_publisher,\r\n"
					+ "book_pubYear,\r\n"
					+ "book_isbn,\r\n"
					+ "book_page,\r\n"
					+ "create_date,\r\n"
					+ "update_date\r\n"
					+ "FROM tb_book_info;\r\n"
					+ "";


			pstmt = conn.prepareStatement(sql);
			// rs = pstmt.executeQuery(); 값을 담아줌(셀렉트 할때만 사용)
			rs = pstmt.executeQuery();
		
			// while 참일 때 계속 돌아감, 거짓일 때 멈춤
			// rs.next()는 다음게 있는지 반복
			while(rs.next()) {
				// HashMap 바구니안에 값을 넣어줘라 key값과 value값으로 구성
				// String 문자, Object 객체(아무거나 상관없이 들어감)
				HashMap<String, Object> rsMap = new HashMap<String, Object>();
				rsMap.put("book_id", rs.getInt("book_id")); 
				rsMap.put("book_title", rs.getString("book_title"));
				rsMap.put("book_price", rs.getInt("book_price"));
				rsMap.put("book_author", rs.getString("book_author"));
				rsMap.put("book_publisher", rs.getString("book_publisher"));
				rsMap.put("book_pubYear", rs.getString("book_pubYear"));
				rsMap.put("book_isbn", rs.getString("book_isbn"));
				rsMap.put("book_page", rs.getInt("book_page"));
				rsMap.put("create_date", rs.getString("create_date"));
				rsMap.put("update_date", rs.getString("update_date"));
				
				// HashMap 리스트 바구니에 값음 넣어줌
				bookList.add(rsMap);
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
		return bookList;
	}
	
}
