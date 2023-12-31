package camppy.reserve.action;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

//import com.itwillbs.dao.BoardDAO;

import camppy.reserve.dao.PageDTO;

import camppy.main.action.CampRegDAO;
import camppy.main.action.CampRegDTO;
import camppy.member.MemberDAO;
import camppy.member.MemberDTO;
import camppy.reserve.dao.MyReserveDAO;
import camppy.reserve.dao.MyReserveDTO;
import camppy.reserve.dao.ReserveDetailDAO;
import camppy.reserve.dao.ReserveDetailDTO;

public class ReserveService {

	ReserveDetailDAO reserveDetailDAO = null;
	MyReserveDAO myreserveDAO = null;

	public void insertReserve(HttpServletRequest request) {
		System.out.println("ReserveService insertReserve()");
		try {
			// http://localhost:8080/webProject/jsp3/insertPro.jsp?id=kim&pass=123&name=홍길동
			// 사용자가 입력한 정보를 http가 들고와서 서버에 request 에 저장
			// request 한글처리
			request.setCharacterEncoding("utf-8");
			// request id, pass,name 가져와서 -> 변수에 저장

			int member_id = Integer.parseInt(request.getParameter("member_id"));
			int res_status = 0;
			String checkin_date = (String) request.getParameter("checkin_date"); // 입실일
			String checkout_date = (String) request.getParameter("checkout_date"); // 입실일
			Timestamp res_time = new Timestamp(System.currentTimeMillis());
			int camp_id = Integer.parseInt(request.getParameter("camp_id"));
			int camp_price = Integer.parseInt(request.getParameter("camp_price"));
			String camp_name = request.getParameter("camp_name");

			// 총 숙박일 계산
			LocalDate startDate = LocalDate.parse(checkin_date);
			LocalDate endDate = LocalDate.parse(checkout_date);
			int daycount = (int) startDate.until(endDate, ChronoUnit.DAYS);

//			총 숙박료 계산
			int sprice = camp_price * daycount;


			ReserveDetailDTO reserveDetailDTO = new ReserveDetailDTO();
			reserveDetailDTO.setMember_id(member_id);
			reserveDetailDTO.setRes_status(res_status);
			reserveDetailDTO.setCheckin_date(checkin_date);
			reserveDetailDTO.setCheckout_date(checkout_date);
			reserveDetailDTO.setRes_time(res_time);
			reserveDetailDTO.setCamp_id(camp_id);
			reserveDetailDTO.setCamp_price(camp_price);
			reserveDetailDTO.setSprice(sprice);
			reserveDetailDTO.setCamp_name(camp_name);
			// ReserveDetailDAO 객체생성
			reserveDetailDAO = new ReserveDetailDAO();
			// reserveDetailDTO = reserveDetailDTO() 메서드 호출
			reserveDetailDAO.insertReserve(reserveDetailDTO);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//		
//		
//	}

	public List<ReserveDetailDTO> getReserveList(PageDTO pageDTO) {
		List<ReserveDetailDTO> reserveList = null;
		System.out.println("ReserveService getReserveList()");
		try {

			int startRow = (pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize() + 1;
			// 시작하는 행부터 끝나는 행까지 뽑아오기
//			startRow  pageSize => endRow
//			    1         10   =>   1+10-1 =>10
//			    11        10   =>   11+10-1 =>20
//		        21        10   =>   21+10-1 =>30

			int endRow = startRow + pageDTO.getPageSize() - 1;
			// pageDTO 저장 <= startRow, endRow
			pageDTO.setStartRow(startRow);
			pageDTO.setEndRow(endRow);

			reserveDetailDAO = new ReserveDetailDAO();
			reserveList = reserveDetailDAO.getReserveList(pageDTO);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return reserveList;
	}

	public List<ReserveDetailDTO> getReserveList1(PageDTO pageDTO) {
		List<ReserveDetailDTO> reserveList = null;
		System.out.println("ReserveService getReserveList()");
		try {

			int startRow = (pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize() + 1;
			// 시작하는 행부터 끝나는 행까지 뽑아오기
//			startRow  pageSize => endRow
//			    1         10   =>   1+10-1 =>10
//			    11        10   =>   11+10-1 =>20
//		        21        10   =>   21+10-1 =>30

			int endRow = startRow + pageDTO.getPageSize() - 1;
			// pageDTO 저장 <= startRow, endRow
			pageDTO.setStartRow(startRow);
			pageDTO.setEndRow(endRow);

			reserveDetailDAO = new ReserveDetailDAO();
			reserveList = reserveDetailDAO.getReserveList1(pageDTO);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return reserveList;
	}

//	public List<MyReserveDTO> getMyReserveList1(int res_id) {
//		System.out.println("ReserveService getMyReserveList1()");
//		List<MyReserveDTO> reserveList = null;
//		try {
//			myreserveDAO = new MyReserveDAO();
//			reserveList = myreserveDAO.getMyReserveList1(res_id);
//			
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return reserveList;
//	}

	public List<MyReserveDTO> getPageList(PageDTO pageDTO) {
		System.out.println("ReserveService getPageList()");
		List<MyReserveDTO> pageList = null;
		try {
			// 시작하는 행부터 10개 뽑아오기
//			페이지번호     한화면에 보여줄 글개수 => 시작하는 행번호
//			currentPage         pageSize    => startRow
//			    1                 10        => (1-1)*10+1=>0*10+1=> 0+1=>1        ~ 10
//			    2                 10        => (2-1)*10+1=>1*10+1=>10+1=>11       ~ 20
//		        3                 10        => (3-1)*10+1=>2*10+1=>20+1=>21       ~ 30			

			int startRow = (pageDTO.getCurrentPage() - 1) * pageDTO.getPageSize() + 1;
			// 시작하는 행부터 끝나는 행까지 뽑아오기
//			startRow  pageSize => endRow
//			    1         10   =>   1+10-1 =>10
//			    11        10   =>   11+10-1 =>20
//		        21        10   =>   21+10-1 =>30

			int endRow = startRow + pageDTO.getPageSize() - 1;
			// pageDTO 저장 <= startRow, endRow
			pageDTO.setStartRow(startRow);
			pageDTO.setEndRow(endRow);

			// BoardDAO 객체생성
			myreserveDAO = new MyReserveDAO();
			// boardList = getBoardList() 메서드 호출
			pageList = myreserveDAO.getPageList(pageDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageList;
	}// getPageList()

	public void insertPage(HttpServletRequest request) {
		try {
			System.out.println("ReserveService insertPage()");
			// request 한글처리
			request.setCharacterEncoding("utf-8");
			// request 파라미터 값 가져오기

			int member_id = Integer.parseInt(request.getParameter("member_id"));
			int res_status = Integer.parseInt(request.getParameter("res_status"));
			String checkin_date = request.getParameter("checkin_date");
			String checkout_date = request.getParameter("checkout_date");
			Timestamp res_time = new Timestamp(System.currentTimeMillis());
			int camp_id = Integer.parseInt(request.getParameter("camp_id"));
			int camp_price = Integer.parseInt(request.getParameter("camp_price"));
			int sprice = Integer.parseInt(request.getParameter("sprice"));
			String camp_name = request.getParameter("camp_name");
			// BoardDAO 객체생성
			myreserveDAO = new MyReserveDAO();
			int res_id = myreserveDAO.getMaxNum() + 1;
			// BoardDTO 객체생성
			MyReserveDTO myreserveDTO = new MyReserveDTO();
			// set메서드 호출 파라미터값 저장
			myreserveDTO.setRes_id(res_id);
			myreserveDTO.setMember_id(member_id);
			myreserveDTO.setRes_status(0);
			myreserveDTO.setCheckout_date(checkout_date);
			myreserveDTO.setCheckin_date(checkin_date);
			myreserveDTO.setRes_time(res_time);
			myreserveDTO.setCamp_id(camp_id);
			myreserveDTO.setCamp_price(camp_price);
			myreserveDTO.setSprice(sprice);
			// 리턴할형없음 insertBoard(boardDTO) 호출
			myreserveDAO.insertReserve(myreserveDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// insertBoard()

	public int getReserveCount(PageDTO pageDTO) {
		System.out.println("ReserveService getReserveCount()");
		int count = 0;
		try {
			// BoardDAO 객체생성
			myreserveDAO = new MyReserveDAO();
			// count = getBoardCount() 호출
			count = myreserveDAO.getReserveCount(pageDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}// getBoardCount

	public int getReserveCount1(PageDTO pageDTO) {
		System.out.println("ReserveService getReserveCount()");
		int count = 0;
		try {
			// BoardDAO 객체생성
			myreserveDAO = new MyReserveDAO();
			// count = getBoardCount() 호출
			count = myreserveDAO.getReserveCount1(pageDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public MyReserveDTO getReserve(HttpServletRequest request) {
		System.out.println("ReserveService getReserve()");
		MyReserveDTO myReserveDTO = null;
		try {
			// request 한글처리
			request.setCharacterEncoding("utf-8");
			// request 파라미터 가져오기 => int num 저장
			int res_id = Integer.parseInt(request.getParameter("res_id"));
			// BoardDAO 객체생성
			myreserveDAO = new MyReserveDAO();
			// boardDTO = getBoard(num) 메서드 호출
			myReserveDTO = myreserveDAO.getReserve(res_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myReserveDTO;
	}// getBoard

	public void deleteReserve(HttpServletRequest request) {
		System.out.println("ReserveService deleteReserve()");
		try {
			// 한글처리
			request.setCharacterEncoding("utf-8");
			// num 파라미터 값 가져오기
			int res_id = Integer.parseInt(request.getParameter("res_id"));
			// BoardDAO 객체생성
			MyReserveDAO myResreveDAO = new MyReserveDAO();
			// deleteBoard(num) 메서드 호출
			myResreveDAO.deleteReserve(res_id);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ReserveDetailDTO getReserve(int res_id) {
		ReserveDetailDTO reserveDetailDTO = null;
		try {
			// MemberDAO 객체생성
			reserveDetailDAO = new ReserveDetailDAO();
			// MemberDTO memberDTO = getMember(id) 메서드 호출
			reserveDetailDTO = reserveDetailDAO.getDetailres(res_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reserveDetailDTO;
	}// getMember()

	public void resstch(HttpServletRequest request) {
		System.out.println("ReserveService deleteReserve()");
		try {
			// 한글처리
			request.setCharacterEncoding("utf-8");
			// num 파라미터 값 가져오기
			int res_id = Integer.parseInt(request.getParameter("res_id"));
			// BoardDAO 객체생성
			MyReserveDAO myResreveDAO = new MyReserveDAO();
			// deleteBoard(num) 메서드 호출
			myResreveDAO.resstch(res_id);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
