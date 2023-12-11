package camppy.review;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import camppy.review.ReviewDTO;
import camppy.review.ReviewService;

public class ReviewController extends HttpServlet {

	RequestDispatcher dispatcher = null;
	ReviewService reviewService = null;

	// HttpServlet 상속 => Servlet 처리담당자 => 동작
	// 자동으로 doGet(), doPost() 호출
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ReviewController doGet()");
		// doProcess() 호출
		doProcess(request, response);
	}// doGet()

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ReviewController doPost()");
		// doProcess() 호출
		doProcess(request, response);
	}// doPost()

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ReviewController doProcess()");
		// 가상주소 => 가상 주소 비교 => 실 주소 연결(이동)
		String sPath = request.getServletPath();
		System.out.println("가상주소 뽑아오기 : " + sPath);

//		http://localhost:8080/Camppy/reviewInsert.rv
		if (sPath.equals("/reviewInsert.rv")) {
			// 주소변경 없이 => board/write.jsp 이동
			dispatcher = request.getRequestDispatcher("review/insert/reviewInsert.jsp");
			dispatcher.forward(request, response);
		} // if

		if (sPath.equals("/reviewInsertPro.rv")) {
			request.setCharacterEncoding("utf-8");
			// BoardService 객체생성
			reviewService = new ReviewService();
			// insertBoard(request) 메서드 호출
			String ratingcheck = request.getParameter("rating");

			System.out.println(ratingcheck);
			if (ratingcheck == null) {

				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script type='text/javascript'>");
				out.println("alert('별점을 입력해 주세요')");
				out.println("history.back()");
				// 부모창 리프레쉬\r\n"
				out.println("</script>");
			} else {
				reviewService.insertReview(request);
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script type='text/javascript'>");
				out.println("alert('작성이 완료되었습니다')");
				out.println(
						"setTimeout(function() {\r\n" + "				    opener.location.reload(); //부모창 리프레쉬\r\n"
								+ "				    self.close(); //현재창 닫기\r\n" + "				    });");
				out.println("</script>");
			}

		} // if
		if (sPath.equals("/campReviewList.rv")) {
			// BoardService 객체생성
			reviewService = new ReviewService();
			// List<BoardDTO> boardList = getBoardList()
			List<ReviewDTO> reviewList = reviewService.getReviewList();

			// request 데이터(boardList) 담아서
			request.setAttribute("reviewList", reviewList);

			dispatcher = request.getRequestDispatcher("review/camp.reviewlist/campReviewList.jsp");
			dispatcher.forward(request, response);
		}
		if (sPath.equals("/mypageReviewList.rv")) {
			HttpSession session = request.getSession();
			String id = (String) session.getAttribute("id");
			if (id == null) {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script type='text/javascript'>");
				out.println("alert('로그인을 해주세요')");
				out.println("parent.location.replace('main.camp')");
				out.println("</script>");
				out.close();
			} else {
				int memberid = (int) session.getAttribute("memberid");
				// BoardService 객체생성
				reviewService = new ReviewService();
				// List<BoardDTO> boardList = getBoardList()
				List<ReviewDTO> reviewList = reviewService.getReviewList();

				// request 데이터(boardList) 담아서
				request.setAttribute("reviewList", reviewList);

				dispatcher = request.getRequestDispatcher("review/mypagelist/mypageReviewList.jsp");
				dispatcher.forward(request, response);
			}
		} //

		if (sPath.equals("/update.rv")) {
			System.out.println("뽑은 가상주소 비교 : /update.rv");

			// ReviewService 객체 생성
			reviewService = new ReviewService();

			// ReviewDTO reviewDTO=getReview(request)
			ReviewDTO reviewDTO = reviewService.getReview(request);

			// request에 "reviewDTO" reviewDTO 담기
			request.setAttribute("reviewDTO", reviewDTO);

			// review/update/reviewUpdate.jsp 주소 변경 없이 이동
			dispatcher = request.getRequestDispatcher("review/update/reviewUpdate.jsp");
			dispatcher.forward(request, response);
		} // ("/update.rv")

		if (sPath.equals("/updatePro.rv")) {
			System.out.println("뽑은 가상주소 비교 : /updatePro.bo");

			// ReviewService 객체 생성
			reviewService = new ReviewService();

			// updateBoard(request) 메서드 호출
			reviewService.updateReview(request);

			// 글목록 mypage_reserve.re 주소 변경되면서 이동
			response.sendRedirect("mypage_reserve.re");
		}

	}// doProcess()

}// 클래스
