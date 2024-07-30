package book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class BookServiceImpl implements BookService { 

	BookDAO bookDAO = new BookDAO();
	Scanner sc = new Scanner(System.in);
	
	@Override
	public void startProgram() {
		
		int count = 0;
		
		while(true) {
			
			int choice = printMenu();
			
			switch (choice) {
			case 1 : 
				System.out.println("1. 도서 정보 등록");
				insertBook();
				break;
			case 2 :
				System.out.println("2. 도서 정보 수정");
				// updateBook();
				break;
			case 3 : 
				System.out.println("3. 도서 정보 삭제");
				deleteBook();
				break;
			case 4 : 
				System.out.println("4. 도서 정보 출력(도서명)");
				printBook();
				break;
			case 5 : 
				System.out.println("5. 도서 전체 정보 출력");
				printAllBook();
				break;
			case 6 : 
				System.out.println("프로그램 종료~!!");
				break;
			default :
				System.out.println("잘못된 숫자가 입력됨. 1~6 사이의 숫자 입력 가능");
				break;
			}
		}
	}

	@Override
	public int printMenu() {
		System.out.println("===== 도서 관리 프로그램 =====");
		System.out.println("1. 도서 정보 등록");
		System.out.println("2. 도서 정보 수정");
		System.out.println("3. 도서 정보 삭제");
		System.out.println("4. 도서 정보 출력(도서명)");
		System.out.println("5. 도서 전체 정보 출력");
		System.out.println("6. 프로그램 종료");
		System.out.println("[선텍] : ");
		
		int choice = sc.nextInt();
		return choice;
	}
	
	// 1.도서 정보 등록
	public void insertBook() {
		System.out.println("도서명을 입력하세요>>>>>");
		sc.nextLine(); // 입력 오류 방지
		String title = sc.nextLine();
		
		System.out.println("도서가격을 입력하세요>>>>>");
		int price = sc.nextInt();
		
		System.out.println("저자를 입력하세요>>>>>");		
		sc.nextLine(); // 입력 오류 방지
		String author = sc.nextLine();
		
		System.out.println("출판사를 입력하세요>>>>>");
		String publisher = sc.nextLine();
		
		System.out.println("출판년도를 입력하세요>>>>>");
		String pubYear = sc.nextLine();
		
		System.out.println("ISBN을 입력하세요>>>>>");
		String isbn = sc.nextLine();
		
		System.out.println("총 페이지 수를 입력하세요>>>>>");
		int page = sc.nextInt();
	
		// new BookInfo 으로 사용할거다. 초기화 선언 
		// bookInfo 안에 값을 넣어줌
		BookInfo bookInfo = new BookInfo();
		bookInfo.setTitle(title);
		bookInfo.setPrice(price);
		bookInfo.setAuthor(author);
		bookInfo.setPublisher(publisher);
		bookInfo.setPubYear(pubYear);
		bookInfo.setIsbn(isbn);
		bookInfo.setPage(page);
		
		// 결과값 resultChk 안에 입력된 데이터 담아줌
		int resultChk = 0;
		
		// BookDAO 와 연결
		resultChk = bookDAO.insertBook(bookInfo);
		// 값이 들어가면 등록됨. 그래서 resultChk가 0보다 커야함
		if(resultChk > 0) {
			System.out.println("도서가 등록되었습니다.");
		} else {
			System.out.println("도서 등록에 실패하였습니다.");
		}
	}
	
	
	// 3.도서 정보 삭제
	public void deleteBook() {
		
		System.out.println("삭제할 도서명 입력해 주세요>>>>>");
		sc.nextLine();
		// title 변수로 담아줌. 도서명 입력받기
		String title = sc.nextLine();
		
		// 결과값 resultChk 안에 입력된 데이터 담아줌
		int resultChk = 0;
		
		// BookDAO 와 연결
		resultChk = bookDAO.deleteBook(title);
		// 값이 들어가면 등록됨. 그래서 resultChk가 0보다 커야함
		if(resultChk > 0) {
			System.out.println("도서가 삭제되었습니다.");
		} else {
			System.out.println("도서 등록에 실패하였습니다.");
		}
	}	
		
	
	// 4.도서 정보 단일 전체 출력
	public void printBook() {
		
		// 도서명 여러개일 경우 대비해 ArrayList로 담아줌
		List<HashMap<String, Object>> bookList = new ArrayList();
		System.out.println("조회할 도서명 입력해 주세요>>>>>");
		sc.nextLine();
		// title 변수로 담아줌. 도서명 입력받기
		String title = sc.nextLine();
		
		// title 파마레타 값 받기
		bookList = bookDAO.printSearchBooks(title);
		
		System.out.println("도서명\t\t저자\t\t출판사\t\t발행년도");
		for(int i=0; i<bookList.size(); i++) {
			System.out.print(bookList.get(i).get("book_title")+"\t");
			System.out.print(bookList.get(i).get("book_author")+"\t");
			System.out.print(bookList.get(i).get("book_publisher")+"\t\t");
			System.out.println(bookList.get(i).get("book_pubYear")+"\t\t");
		}
	}
	
	
	// 5.도서 정보 전체 출력
	public void printAllBook() {
		
		// 사용하기 위해 초기화 선언
		List<HashMap<String, Object>> bookList = new ArrayList();
		// bookList를 bookDAO와 연결(전체 도서정보를 뱉어줘야 하기 때문에 담아둘 bookList를 생성
		bookList = bookDAO.printAllBooks();
		System.out.println("도서명\t저자\t출판사\t발행년도");
		for(int i=0; i<bookList.size(); i++) {
			System.out.print(bookList.get(i).get("book_title")+"\t");
			System.out.print(bookList.get(i).get("book_author")+"\t");
			System.out.print(bookList.get(i).get("book_publisher")+"\t");
			System.out.println(bookList.get(i).get("book_pubYear")+"\t");
		}
	}

}
