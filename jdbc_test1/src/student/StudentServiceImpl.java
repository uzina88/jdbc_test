package student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class StudentServiceImpl implements StudentService{
	
	// 초기화 생성
	StudentDAO studentDAO = new StudentDAO();
	Scanner sc = new Scanner(System.in);

	@Override
	public void startProgram() {
		// TODO Auto-generated method stub
		
		while(true) {
			int menu = printMenu();
			
			switch(menu) {
			case 1 :
				System.out.println("1. 학생등록");
				insertStudent();
				break;
			case 2 :
				System.out.println("2. 성적등록");
				insertScore();
				break;
			case 3 : 
				System.out.println("3. 학생 정보 수정");
				updateStudent();
				break;
			case 4 : 
				System.out.println("4. 학생 정보 삭제");
				deleteStudent();
				break;
			case 5 : 
				System.out.println("5. 학생 성적 수정");
				updateScore();
				break;
			case 6 : 
				System.out.println("6. 학생 성적 삭제");
				deleteScore();
				break;
			case 7 : 
				System.out.println("7. 학생 정보 검색(전체)");
				printAllStudent();
				break;
			case 8 : 
				System.out.println("8. 학생정보 검색(이름)");
				printSearchStudent();
				break;
			case 9 : 
				System.out.println("9. 프로그램 종료");
				break;
			default :
				break;
			}
		}
	}
	
	public int printMenu() {
		System.out.println("*****학생 성적관리 프로그램******");
		System.out.println("1. 학생등록");
		System.out.println("2. 성적등록");
		System.out.println("3. 학생정보 수정");
		System.out.println("4. 학생정보 삭제");
		System.out.println("5. 학생 성적 수정");
		System.out.println("6. 학생 성적 삭제");
		System.out.println("7. 학생 정보 검색(전체)");
		System.out.println("8. 학생 정보 검색(이름)");
		System.out.println("9. 프로그램 종료");
		System.out.print("[선택] : ");
		int menu = sc.nextInt();
		
		return menu;
	}
	
	// 학생 정보 등록
	public void insertStudent() {
		System.out.println("학생 이름을 입력하세요.");
		sc.nextLine();
		String studentName = sc.nextLine();
		
		System.out.println("학년을 입력하세요.");
		int studentGrade = sc.nextInt();
		// sc.nextInt(); 다음에는 sc.nextLine();을 써줘야 씹히지 않음
		System.out.println("학교를 입력하세요.");
		sc.nextLine();
		String studentSchool = sc.nextLine();
		
		System.out.println("학생의 주소를 입력하세요.");
		String studentAddr = sc.nextLine();
		
		System.out.println("학생 연락처를 입력하세요.");
		String studentPhone = sc.nextLine();
		
		HashMap<String, Object> studentMap = new HashMap<String, Object>();
		studentMap.put("studentName", studentName);
		studentMap.put("studentGrade", studentGrade);
		studentMap.put("studentSchool", studentSchool);
		studentMap.put("studentAddr", studentAddr);
		studentMap.put("studentPhone", studentPhone);
		
		int resultChk = 0;
		// DAO에 넘겨주기
		resultChk = studentDAO.insertStudent(studentMap);
		if(resultChk > 0) {
			System.out.println("등록 완료");
		}else {
			System.out.println("등록 실패");
		}
		
	}
	
	// 성적등록
	public void insertScore() {
		System.out.print("성적을 입력할 학생의 이름을 입력해주세요>>>>>");
		sc.nextLine();
		String studentName = sc.nextLine();
		List<HashMap<String, Object>> studentList = new ArrayList();
		studentList = studentDAO.printSearchStudentInfo(studentName);
		System.out.println("학교\t학생이름\t학년");
		for(int i = 0; i<studentList.size(); i++) {
			System.out.print(studentList.get(i).get("studentSchool")+"\t");
			System.out.print(studentList.get(i).get("studentName")+"\t");
			System.out.println(studentList.get(i).get("studentGrade"));
		}
		System.out.println("성적을 입력할 학생을 선택해주세요>>>>");
		int choice = sc.nextInt();
		int studentIdx = Integer.parseInt(studentList.get(choice-1).get("studentIdx").toString());
		System.out.print("년도를 입력해주세요>>>>>");
		sc.nextLine();
		String season = sc.nextLine();
		System.out.print("학기를 입력해주세요>>>>>");
		int semester = sc.nextInt();
		System.out.print("시험 분류를 입력해주세요.(중간고사 : M, 기말고사 : F) >>>>>");
		sc.nextLine();
		String examType = sc.nextLine();
		if(!"M".equals(examType) && !"F".equals(examType)) {
			System.out.println("시험분류는 중간고사는 M, 기말고사는 F 둘중 하나만 입력 가능합니다.");
			return;
		}
		System.out.print("과목명을 입력해주세요.>>>>>");
		String subject = sc.nextLine();
		System.out.print("점수를 입력해주세요.>>>>>");
		int point = sc.nextInt();
		
		HashMap<String, Object> scoreMap = new HashMap<String, Object>();
		scoreMap.put("studentIdx", studentIdx);
		scoreMap.put("season", season);
		scoreMap.put("semester", semester);
		scoreMap.put("examType", examType);
		scoreMap.put("subject", subject);
		scoreMap.put("point", point);
		
		int resultChk = 0;
		resultChk = studentDAO.insertScore(scoreMap);
		if(resultChk > 0) {
			System.out.println("성적이 등록되었습니다.");
		}else {
			System.out.println("성적 등록에 실패하였습니다.");
		}
	}
	
	// 학생정보 수정
	public void updateStudent() {
		System.out.print("정보를 수정할 학생의 이름을 입력해주세요>>>>>");
		sc.nextLine();
		String studentName = sc.nextLine();
		List<HashMap<String, Object>> studentList = new ArrayList();
		studentList = studentDAO.printSearchStudentInfo(studentName);
		System.out.println("학교\t학생이름\t학년");
		for(int i = 0; i<studentList.size(); i++) {
			System.out.print(studentList.get(i).get("studentSchool")+"\t");
			System.out.print(studentList.get(i).get("studentName")+"\t");
			System.out.println(studentList.get(i).get("studentGrade"));
		}
		// 순번 선택
		System.out.println("정보를 수정할 학생을 선택해주세요>>>>");
		int choice = sc.nextInt();
		int studentIdx = Integer.parseInt(studentList.get(choice-1).get("studentIdx").toString());
		
		System.out.println("수정할 학생 정보를 선택해주세요.");
		System.out.println("이름 : 1, 학교 : 2, 학년 : 3, 연락처 : 4, 주소 : 5");
		int updateChoice = sc.nextInt();
		System.out.println("변경할 내용을 입력하세요>>>>>");
		sc.nextLine();
		String updateContents = sc.nextLine();
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("studentIdx", studentIdx);
		paramMap.put("updateChoice", updateChoice);
		paramMap.put("updateContents", updateContents);
		
		int resultChk = 0;
		// DAO로 값 넘겨줌
		resultChk = studentDAO.updateStudent(paramMap);
		if(resultChk > 0) {
			System.out.println("수정완료");
		}else {
			System.out.println("수정실패");
		}
	}
	
	// 학생정보 삭제
	public void deleteStudent() {
		System.out.print("정보를 삭제할 학생의 이름을 입력해주세요>>>>>");
		sc.nextLine();
		String studentName = sc.nextLine();
		List<HashMap<String, Object>> studentList = new ArrayList();
		studentList = studentDAO.printSearchStudentInfo(studentName);
		System.out.println("학교\t학생이름\t학년");
		for(int i = 0; i<studentList.size(); i++) {
			System.out.print(studentList.get(i).get("studentSchool")+"\t");
			System.out.print(studentList.get(i).get("studentName")+"\t");
			System.out.println(studentList.get(i).get("studentGrade"));
		}
		System.out.println("정보를 삭제할 학생을 선택해주세요>>>>");
		int choice = sc.nextInt();
		int studentIdx = Integer.parseInt(studentList.get(choice-1).get("studentIdx").toString());
		
		int resultChk = 0;
		// 성적을 먼저 삭제해줘야함 DAO 추가(건수 체크)
		int chk = studentDAO.selectStudentScoreCnt(studentIdx);
		if(chk > 0) {
			// 카운트가 있으면 성적과 학생정보를 지워
			resultChk = studentDAO.deleteStudentScore(studentIdx);
			if(resultChk > 0) {
				resultChk = studentDAO.deleteStudent(studentIdx);
				
				if(resultChk > 0) {
					System.out.println("삭제완료");
				}else {
					System.out.println("삭제실패");
				}
			} else {
				System.out.println("삭제실패");
			}
		} else {
			// 성적이 없으면 바로 학생을 지워라
			resultChk = studentDAO.deleteStudent(studentIdx);
			
			if(resultChk > 0) {
				System.out.println("삭제완료");
			}else {
				System.out.println("삭제실패");
			}
		}
		
	}
	
	// 학생 성적 수정
	public void updateScore() {
		// 입력 받기
		System.out.print("성적을 수정할 학생의 이름을 입력해주세요>>>>>");
		String studentName = sc.nextLine();
		List<HashMap<String, Object>> list = new ArrayList();
		// 조회 printSearchStudent 에서 score_idx 추가해야함
		list = studentDAO.printSearchStudent(studentName);
		System.out.println("학교명\t\t학생이름\t학년\t주소\t연락처\t\t년도\t학기\t구분\t과목\t점수");
		for(int i = 0; i<list.size(); i++) {
			System.out.print(list.get(i).get("studentSchool")+"\t");
			System.out.print(list.get(i).get("studentName")+"\t");
			System.out.print(list.get(i).get("studentGrade")+"\t");
			System.out.print(list.get(i).get("studentAddr")+"\t");
			System.out.print(list.get(i).get("studentPhone")+"\t");
			System.out.print(list.get(i).get("scoreIdx")+"\t");
			System.out.print(list.get(i).get("scoreSeason")+"\t");
			System.out.print(list.get(i).get("scoreSemester")+"\t");
			System.out.print(list.get(i).get("scoreExamType")+"\t");
			System.out.print(list.get(i).get("scoreSubject")+"\t");
			System.out.println(list.get(i).get("scorePoint")+"\t");
		}
		// 순번 입력 받기
		System.out.println("수정할 학생의 성적을 선택해주세요>>>>");
		int choice = sc.nextInt();
		int scoreIdx = Integer.parseInt(list.get(choice-1).get("scoreIdx").toString());
		// 점수 입력 받기
		System.out.println("수정할 점수를 입력해주세요>>>>>");
		int updateScore = sc.nextInt();
		int resultChk =0;
		// DAO 파라미터 데이터 주기
		resultChk = studentDAO.updateScore(scoreIdx, updateScore);
		if(resultChk > 0) {
			System.out.println("수정완료");
		}else {
			System.out.println("수정실패");
		}
		
		
	}
	
	// 학생 성적 삭제
	public void deleteScore() {
		System.out.print("성적을 삭제할 학생의 이름을 입력해주세요>>>>>");
		sc.nextLine();
		String studentName = sc.nextLine();
		List<HashMap<String, Object>> studentList = new ArrayList();
		studentList = studentDAO.printSearchStudent(studentName);
		System.out.println("학교\t학생이름\t학년");
		for(int i = 0; i<studentList.size(); i++) {
			System.out.print(studentList.get(i).get("studentSchool")+"\t");
			System.out.print(studentList.get(i).get("studentName")+"\t");
			System.out.println(studentList.get(i).get("studentGrade"));
		}
		System.out.println("성적을 삭제할 학생을 선택해주세요>>>>");
		int choice = sc.nextInt();
		int studentIdx = Integer.parseInt(studentList.get(choice-1).get("studentIdx").toString());
		
		// 학생 성적 리스트 뽑아라 
		// 1. DAO에 메소트 만드는방법 2. 전체학생 조회시 활용
		// 조회 printSearchStudent 에서 score_idx 추가해야함
		List<HashMap<String, Object>> scoreList = new ArrayList();
		scoreList = studentDAO.printSearchScore(studentIdx);
		System.out.println("년도\t학기\t시험\t과목\t점수");
		// List<HashMap<String, Object>> scoreList = studentDAO.printAllScore(studentIdx);
		for(int j=0; j<scoreList.size(); j++) {
			System.out.print(scoreList.get(j).get("scoreSeason")+"\t");
			System.out.print(scoreList.get(j).get("scoreSemester")+"\t");
			System.out.print(scoreList.get(j).get("scoreExamType")+"\t");
			System.out.print(scoreList.get(j).get("scoreSubject")+"\t");
			System.out.println(scoreList.get(j).get("scorePoint")+"\t");
		}
		
		System.out.print("삭제할 성적의 년도를 입력해주세요>>>>>");
		sc.nextLine();
		String season = sc.nextLine();
		System.out.print("삭제할 성적의 학기를 입력해주세요>>>>>");
		int semester = sc.nextInt();
		System.out.print("삭제할 성적의 시험을 입력해주세요>>>>>");
		sc.nextLine();
		String examType = sc.nextLine();
		System.out.print("삭제할 성적의 과목를 입력해주세요>>>>>");
		String subject = sc.nextLine();
		
		HashMap<String, Object> scoreMap = new HashMap<String, Object>();
		scoreMap.put("studentIdx", studentIdx);
		scoreMap.put("season", season);
		scoreMap.put("semester", semester);
		scoreMap.put("examType", examType);
		scoreMap.put("subject", subject);
		
		int resultChk = 0;
		resultChk = studentDAO.deleteScore(scoreMap);
		if(resultChk > 0) {
			System.out.println("삭제되었습니다.");
		}else {
			System.out.println("삭제에 실패하였습니다.");
		}
		
	}
	
	// 학생 정보 전체 검색
	public void printAllStudent() {
		System.out.println("학교명\t\t학생이름\t학년\t주소\t연락처\t\t년도\t학기\t구분\t과목\t점수");
		List<HashMap<String, Object>> list = new ArrayList();
		list = studentDAO.printAllStudent();
		for(int i=0; i<list.size(); i++) {
			System.out.print(list.get(i).get("studentSchool")+"\t");
			System.out.print(list.get(i).get("studentName")+"\t");
			System.out.print(list.get(i).get("studentGrade")+"\t");
			System.out.print(list.get(i).get("studentAddr")+"\t");
			System.out.print(list.get(i).get("studentPhone")+"\t");
			System.out.print(list.get(i).get("scoreSeason")+"\t");
			System.out.print(list.get(i).get("scoreSemester")+"\t");
			System.out.print(list.get(i).get("scoreExamType")+"\t");
			System.out.print(list.get(i).get("scoreSubject")+"\t");
			System.out.println(list.get(i).get("scorePoint")+"\t");
		}
	}
	
	// 학생 정보 이름으로 검색
	public void printSearchStudent() {
		
		System.out.print("검색할 학생의 이름을 입력해주세요>>>>>");
		sc.nextLine();
		String studentName = sc.nextLine();
		// 학생정보 검색 -> DB
		List<HashMap<String, Object>> list = new ArrayList();
		list = studentDAO.printSearchStudent(studentName);
		
		System.out.println("학교명\t\t학생이름\t학년\t주소\t연락처\t년도\t학기\t구분\t과목\t점수");
		for(int i=0; i<list.size(); i++) {
			System.out.print(list.get(i).get("studentSchool")+"\t");
			System.out.print(list.get(i).get("studentName")+"\t");
			System.out.print(list.get(i).get("studentGrade")+"\t");
			System.out.print(list.get(i).get("studentAddr")+"\t");
			System.out.print(list.get(i).get("studentPhone")+"\t");
			System.out.print(list.get(i).get("scoreSeason")+"\t");
			System.out.print(list.get(i).get("scoreSemester")+"\t");
			System.out.print(list.get(i).get("scoreExamType")+"\t");
			System.out.print(list.get(i).get("scoreSubject")+"\t");
			System.out.println(list.get(i).get("scorePoint")+"\t");
		}
		
	}

}

