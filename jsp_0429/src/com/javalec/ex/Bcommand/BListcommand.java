package com.javalec.ex.Bcommand;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javalec.ex.BDao.BDao;
import com.javalec.ex.BDto.BDto;

public class BListcommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		//페이징부분
		int page=1; //최초 기본 1페이지 
		int limit=10; // 1페이지 당 게시글 10개
		String searchflag=request.getParameter("searchflag"); //검색체크
		//검색부분
		String opt = request.getParameter("opt");
		String search = request.getParameter("search");

		if(opt==null) {opt = "";}
		if(search==null) {search = "";}
		
		// 넘어온 페이지가 있을 때 예) 4페이지가 넘어오면 4페이지가 page에 들어가겠지
		//넘어온 페이지가 없으면 1페이지 자동
		if(request.getParameter("page")!=null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		//게시글 리스트 출력
		BDao dao = new BDao();
		//페이지에 출력될 게시글에 관한..(게시글 넘버 확인)
		//왜 page, limit을 보내나? 그래야 그 페이지에서 출력할 게시글들 번호를 알수 있으니까
		ArrayList<BDto> dtos =	dao.list(page,limit,opt,search);
		//전체 게시글 count(*) 
		int listcount = dao.getlistCount(opt,search);
		//최대 페이지 수(총 rage의 마지막 페이지 숫자)
		int maxpage = (int)((double)listcount/limit+0.95);
		//처음 페이지(해당 range의 처음 페이지 숫자)
		int startpage = ((int)((double)page/10+0.9)-1)*10+1;
		//마지막 페이지(해당 range의 마지막 페이지 숫자)
		int endpage = maxpage; //maxpage가 1~10이내이면 maxpage가 endpage가 됨
		//즉 최종 페이지 수(최대페잊이수)가 range 범위보다 작으면 그냥 maxpage를 endpage에 대입시키면 되는거 
		//근데 맥스 넣어서 아래 돌려봤는데 앤드페이지가 startpage+10-1보다 크면 아래처럼..즉 페이지가 range 범위보다 커지면 아래와 같은 식을 적용
		if(endpage>startpage+10-1) endpage = startpage+10-1;
		
		request.setAttribute("list", dtos);
		request.setAttribute("listcount", listcount);
		request.setAttribute("page", page);
		request.setAttribute("maxpage", maxpage);
		request.setAttribute("startpage", startpage);
		request.setAttribute("endpage", endpage);
		
		if(search!="") {
			searchflag = "1";
			request.setAttribute("searchflag", searchflag);
			request.setAttribute("opt", opt);
			request.setAttribute("search", search);
		}
	}

}
