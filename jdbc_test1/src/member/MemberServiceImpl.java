package member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import book.BookInfo;

public class MemberServiceImpl implements MemberService {

	MemberDAO mDAO = new MemberDAO();
	Scanner sc = new Scanner(System.in);
	@Override
	public void startProgram() {
		// TODO Auto-generated method stub
		
		
		while(true) {
			int choice = printMenu();
			int count = 0;
			
			switch (choice) {
			case 1 : 
				displayMsg("1. 회원 정보 등록");
				insertMember();
				break;
			case 2 :
				displayMsg("2. 회원 정보 수정");
				updateMember();
				break;
			case 3 : 
				displayMsg("3. 회원 정보 삭제");
				deleteMember();
				break;
			case 4 : 
				displayMsg("4. 회원 정보 출력(이름)");
				printMember();
				break;
			case 5 : 
				displayMsg("5. 회원 전체 정보 출력");
				printAllMembers();
				break;
			case 6 : 
				displayMsg("프로그램 종료~!!");
				count++;
				break;
			default :
				displayMsg("잘못된 숫자가 입력됨. 1~6 사이의 숫자 입력 가능");
				break;
			}
			// 6번이 카운팅 되면 종료
			// if(choice == 6)
			if(count == 1) {
				break;
				}
			}
	
		}
	
	// 1. 회원정보 생성
	public void insertMember() {
		// 회원 정보를 등록할 member 객체 생성
		Member member = new Member();
		
		System.out.print("회원 아이디를 입력해 주세요 : ");
		String memberId = sc.next();
		
		System.out.print("회원 비밀번호 : ");
		String memberPw = sc.next();
		
		System.out.print("회원 이름 : ");
		String memberName = sc.next();
		
		System.out.print("생년월일(ex) 19900101 : ");
		String memberBirth = sc.next();
		
		System.out.print("이메일 주소를 입력해 주세요. :");
		String memberEmail = sc.next();
		
		System.out.print("연락처 정보를 입력해 주세요. : ");
		String memberPhone = sc.next();
		
		// member에 회원 정보 셋팅
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		member.setMemberName(memberName);
		member.setMemberBirth(memberBirth);
		member.setMemberEmail(memberEmail);
		member.setMemberPhone(memberPhone);
		
		// 결과값 담아줄 변수 초기화 선언
		int chk = 0;
		chk = mDAO.insertMember(member);
		
		// 등록 성공/실패 if문
		if(chk > 0) {
			System.out.println("등록되었습나다.");
		} else {
			System.out.println("등록이 실패하셨습니다.");
		}
	}
	
	
	// 2. 회원정보 수정
	public void updateMember() {
		
		System.out.println("회원명을 입력해 주세요>>>>>");
		sc.nextLine();
		// title 변수로 담아줌. 도서명 입력받기
		String name = sc.nextLine();
		
		// title 파마레타 값 받기
		List<HashMap<String, Object>> member = new ArrayList();
		member = mDAO.printMember(name);
		
		System.out.println("회원ID\tPW\t이름\t생년월일\t\t이메일\t\t\t연락처");
		for(int i=0; i<member.size(); i++) {
			System.out.print(member.get(i).get("member_id")+"\t");
			System.out.print(member.get(i).get("member_pw")+"\t");
			System.out.print(member.get(i).get("member_name")+"\t");
			System.out.print(member.get(i).get("member_birth")+"\t");
			System.out.print(member.get(i).get("member_email")+"\t");
			System.out.println(member.get(i).get("member_phone")+"\t");
		}
		System.out.println("수정할 회원의 순번을 입력하세요>>>>>");
		int num = sc.nextInt();
		// Object int형으로 변환
		// List는 첫번째가 0번째라서 num-1을 해줘야함
		//int MemberId = (int) member.get(num-1).get("member_id");
		// pk 값음 가져와야함
		int MemberIdx = Integer.parseInt(member.get(num-1).get("member_idx").toString());
		System.out.println("변경될 이름을 입력하세요>>>>");
		sc.nextLine();
		String updateName = sc.nextLine();
		// 결과값 resultChk 안에 입력된 데이터 담아줌
		int resultChk = 0;
		// BookDAO 와 연결
		resultChk = mDAO.updateMember(MemberIdx, updateName);
		// 값이 들어가면 등록됨. 그래서 resultChk가 0보다 커야함
		if(resultChk > 0) {
			System.out.println("회원정보가 수정되었습니다.");
		} else {
			System.out.println("회원정보 수정에 실패하였습니다.");
		}
	}	
		
	
	// 3. 회원정보 삭제
	public void deleteMember() {
		
		System.out.println("삭제할 회원명을 입력해 주세요>>>>>");
		sc.nextLine();
		// title 변수로 담아줌. 도서명 입력받기
		String name = sc.nextLine();
		
		// title 파마레타 값 받기
		// List<HashMap<String, Object>> member = mDAO.printMember(name);
		List<HashMap<String, Object>> member = new ArrayList();
		member = mDAO.printMember(name);
		
		System.out.println("회원ID\tPW\t이름\t생년월일\t\t이메일\t\t\t연락처");
		for(int i=0; i<member.size(); i++) {
			System.out.print(member.get(i).get("member_id")+"\t");
			System.out.print(member.get(i).get("member_pw")+"\t");
			System.out.print(member.get(i).get("member_name")+"\t");
			System.out.print(member.get(i).get("member_birth")+"\t");
			System.out.print(member.get(i).get("member_email")+"\t");
			System.out.println(member.get(i).get("member_phone")+"\t");
		}
		System.out.println("삭제할 회원의 순번을 입력하세요>>>>>");
		int num = sc.nextInt();
		// Object int형으로 변환
		// List는 첫번째가 0번째라서 num-1을 해줘야함
		//int MemberId = (int) member.get(num-1).get("member_id");
		// pk 값음 가져와야함
		int MemberIdx = Integer.parseInt(member.get(num-1).get("member_idx").toString());
		String deleteName = sc.nextLine();
		
		int resultChk = 0;
		// BookDAO 와 연결
		resultChk = mDAO.deleteMember(MemberIdx, deleteName);
		// 값이 들어가면 등록됨. 그래서 resultChk가 0보다 커야함
		if(resultChk > 0) {
			System.out.println("삭제완료");
		} else {
			System.out.println("삭제실패");
		}
		
	}

	
	// 4.회원 정보 단일 전체 출력
	public void printMember() {
		
		// 도서명 여러개일 경우 대비해 ArrayList로 담아줌
		List<HashMap<String, Object>> member = new ArrayList();
		System.out.println("조회할 회원명을 입력해 주세요>>>>>");
		sc.nextLine();
		// title 변수로 담아줌. 도서명 입력받기
		String name = sc.nextLine();
		
		// title 파마레타 값 받기
		member = mDAO.printMember(name);
		
		System.out.println("회원ID\tPW\t이름\t생년월일\t\t이메일\t\t\t연락처");
		for(int i=0; i<member.size(); i++) {
			System.out.print(member.get(i).get("member_id")+"\t");
			System.out.print(member.get(i).get("member_pw")+"\t");
			System.out.print(member.get(i).get("member_name")+"\t");
			System.out.print(member.get(i).get("member_birth")+"\t");
			System.out.print(member.get(i).get("member_email")+"\t");
			System.out.println(member.get(i).get("member_phone")+"\t");
		}
	}
	
	
	// 5.도서 정보 전체 출력
	public void printAllMembers() {
		
		// 사용하기 위해 초기화 선언
		List<HashMap<String, Object>> member = new ArrayList();
			
		// bookList를 bookDAO와 연결(전체 도서정보를 뱉어줘야 하기 때문에 담아둘 bookList를 생성
		member = mDAO.printAllMembers();
		System.out.println("회원ID\tPW\t이름\t생년월일\t\t이메일\t\t\t연락처");
		for(int i=0; i<member.size(); i++) {
			System.out.print(member.get(i).get("member_id")+"\t");
			System.out.print(member.get(i).get("member_pw")+"\t");
			System.out.print(member.get(i).get("member_name")+"\t");
			System.out.print(member.get(i).get("member_birth")+"\t");
			System.out.print(member.get(i).get("member_email")+"\t");
			System.out.println(member.get(i).get("member_phone")+"\t");
		}
	}
	

	// 프로그램 메뉴 출력
	public int printMenu() {
		displayMsg("===== 회원 관리 프로그램 =====");
		displayMsg("1. 회원 정보 등록");
		displayMsg("2. 회원 정보 수정");
		displayMsg("3. 회원 정보 삭제");
		displayMsg("4. 회원 정보 출력(이름)");
		displayMsg("5. 회원 전체 정보 출력");
		displayMsg("6. 프로그램 종료");
		System.out.println("[선텍] : ");
		
		int choice = sc.nextInt();
		return choice;
	}
	
	
	// 메세지 출력용 메소드 생성
	public void displayMsg(String msg) {
		System.out.println(msg);
	}
	
	
	
}
