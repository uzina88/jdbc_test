package member;

public class MemberController {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// memberService 변수에 담아줌
		MemberService memberService = new MemberServiceImpl();
		
		memberService.startProgram();
		
	}

}
