package camppy.main.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CampRegController extends HttpServlet {

	CampRegService campregService = null;
	RequestDispatcher dispatcher = null;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("CampRegController doGet()");
		doProcess(request, response);
	}// doGet()

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("CampRegController doPost()");
		doProcess(request, response);
	}// doPost()

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("CampRegController doProcess()");
		// 가상주소 뽑아오기
		String sPath = request.getServletPath();
		System.out.println("뽑아온 가상주소:" + sPath);

		// 첨부파일
//		http://localhost:8080/FunWeb/fwrite.bo
		if (sPath.equals("/campreg.campreg")) {
			// 주소변경없이 이동 center/fwrite.jsp
			HttpSession session = request.getSession();
			// 세션에서 로그인 정보 가져오기
			String id = (String) session.getAttribute("id");

			dispatcher = request.getRequestDispatcher("camppymain/campreg/campreg.jsp");
			dispatcher.forward(request, response);

		}

		if (sPath.equals("/campregPro.campreg")) {
			// System.out.println("뽑은 가상주소 비교 : /fwritePro.bo"); // CampRegService 객체생성
			campregService = new CampRegService(); // 리턴할형없음 finsertCampReg(request) 메서드 호출
			campregService.finsertCampReg(request); // list.bo 주소 변경 되면서 이동
			response.sendRedirect("main.camp");
		}

		if (sPath.equals("/campupdate.campreg")) {
			System.out.println("뽑은 가상주소 비교 : /campupdate.campreg");

			campregService = new CampRegService();
			int camp_id = Integer.parseInt(request.getParameter("camp_id"));
			CampRegDTO campregDTO = campregService.getCampReg(camp_id);

			request.setAttribute("campregDTO", campregDTO);

			// 주소변경없이 이동 center/fwrite.jsp
			HttpSession session = request.getSession();
			// 세션에서 로그인 정보 가져오기
			String id = (String) session.getAttribute("id");

			dispatcher = request.getRequestDispatcher("camppymain/campreg/campupdate.jsp");
			dispatcher.forward(request, response);

		} // ("/campupdate.campreg")

		if (sPath.equals("/campupdatePro.campreg")) {
			System.out.println("뽑은 가상주소 비교 : /campregPro.campreg");

			campregService = new CampRegService();

			campregService.updateCampReg(request);

		} // ("/campregPro.campreg")

	}// doProcess()

}// 클래스
