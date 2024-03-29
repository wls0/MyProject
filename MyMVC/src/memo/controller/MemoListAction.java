package memo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;
import memo.model.MemoDAO;
import memo.model.MemoVo;

public class MemoListAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) 
			throws Exception {
		//현재 보여줄 페이지(cpage) 파라미터 값 받기
		String cpStr=req.getParameter("cpage");
		if(cpStr==null) {
			cpStr="1"; //디폴트 페이지를 1 페이지로 지정
		
		}
		int cpage=Integer.parseInt(cpStr.trim());
		if(cpage<=0) {
			cpage=1; //첫페이지를 지정
		}
		
		MemoDAO dao=new MemoDAO();
		//[1]총 게시글 수 가져오기
		int totalCount=dao.getTotalCount();
		//[2]한 페이지에 보여줄 목록 갯수 정하기
		int pageSize=5;
		
		//[3]페이지수 구하기
		int pageCount=((totalCount-1)/pageSize)+1;
		if(cpage>pageCount) {
			cpage=pageCount; //마지막 페이지를 지정
		}
		
		//cpag에 해당하는 데이터를 db에서 끊어 가져온다.
		//db에서 끊어 가져오기 위한 변수값 구하기
		int end=cpage*pageSize;
		int start=end-(pageSize-1);
		
		
		
		
		List<MemoVo> arr=dao.listMemo(start, end);
		req.setAttribute("memoArr", arr);
		req.setAttribute("totalCount", totalCount);
		req.setAttribute("pageSize", pageSize);
		req.setAttribute("pageCount", pageCount);
		
		this.setViewPage("/memo/memoList.jsp");
		this.setRedirect(false);
	}

}
