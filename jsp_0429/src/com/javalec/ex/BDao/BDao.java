package com.javalec.ex.BDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.javalec.ex.BDto.BDto;

public class BDao {

	Context context=null;
	DataSource ds=null;
	Connection con=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	String sql=null;
	ArrayList<BDto> list = new ArrayList<BDto>();
	BDto bdto;
	
	String bName,bTitle,bContent;
	int bId,bHit,bGroup,bStep,bIndent;
	Timestamp bDate;
	
	//생성자
	public BDao() {
		try {
			context = new InitialContext();
			ds = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//게시판 리스트/전체 select
	public ArrayList<BDto> list(int page, int limit, String opt, String search) {
		
		//sql="select * from mvc_board order by bGroup desc, bStep asc";
		
		int startrow = (page-1)*10+1; //페이지의 첫번째 게시물 번호
		int endrow = startrow + limit - 1; //페이지의 마지막 게시물 번호
		
//		sql = "select * from "
//				+ "(select rownum rnum,bid,bname,btitle,bcontent,bdate,bhit,bgroup,bstep,bindent from "
//				+ "(select * from mvc_board order by bgroup desc, bstep asc)) where rnum>=? and rnum<=?";
		if(opt==null) {opt = "";}
		if(search==null) {search = "";}
		
		switch (opt) {
		case "":
			sql = "select * from "
					+ "(select rownum rnum,bid,bname,btitle,bcontent,bdate,bhit,bgroup,bstep,bindent from "
					+ "(select * from mvc_board order by bgroup desc, bstep asc)) where rnum>=? and rnum<=?";
			break;
		case "all":
			sql = "select * from "
					+ "(select rownum rnum,bid,bname,btitle,bcontent,bdate,bhit,bgroup,bstep,bindent from "
					+ "(select * from mvc_board where btitle like '%'||?||'%' or bcontent like '%'||?||'%' order by bgroup desc, bstep asc)) where rnum>=? and rnum<=?";
			break;
		case "tit":
			sql = "select * from "
					+ "(select rownum rnum,bid,bname,btitle,bcontent,bdate,bhit,bgroup,bstep,bindent from "
					+ "(select * from mvc_board where btitle like '%'||?||'%' order by bgroup desc, bstep asc)) where rnum>=? and rnum<=?";
			break;
		case "con":
			sql = "select * from "
					+ "(select rownum rnum,bid,bname,btitle,bcontent,bdate,bhit,bgroup,bstep,bindent from "
					+ "(select * from mvc_board where bcontent like '%'||?||'%' order by bgroup desc, bstep asc)) where rnum>=? and rnum<=?";
			break;
		}
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			if(opt.equals("")) {
				pstmt.setInt(1, startrow);
				pstmt.setInt(2, endrow);
			} else if(opt.equals("all")) {
				pstmt.setString(1, search);
				pstmt.setString(2, search);
				pstmt.setInt(3, startrow);
				pstmt.setInt(4, endrow);
			} else {
				pstmt.setString(1, search);
				pstmt.setInt(2, startrow);
				pstmt.setInt(3, endrow);
			}
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				bId = rs.getInt("bId");
				bName = rs.getString("bName");
				bTitle = rs.getString("bTitle");
				bContent = rs.getString("bContent");
				bDate= rs.getTimestamp("bDate");
				bHit = rs.getInt("bHit");
				bGroup = rs.getInt("bGroup");
				bStep = rs.getInt("bStep");
				bIndent = rs.getInt("bIndent");
				bdto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				list.add(bdto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}//list 전체
	
	//총 게시글 수
	public int getlistCount(String opt, String search) {
		int count=0;

//		sql="select count(*) as count from mvc_board";
		
		if(opt==null) {opt = "";}
		
		switch (opt) {
		case "":
			sql="select count(*) as count from mvc_board";
			break;
		case "all":
			sql="select count(*) as count from mvc_board where bcontent like '%'||?||'%' or btitle like '%'||?||'%'";
			break;
		case "tit":
			sql="select count(*) as count from mvc_board where btitle like '%'||?||'%'";
			break;
		case "con":
			sql="select count(*) as count from mvc_board where bcontent like '%'||?||'%'";
			break;
		}
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			if(opt.equals("all")) {
				pstmt.setString(1, search);
				pstmt.setString(2, search);
			} else if(opt.equals("tit")||opt.equals("con")) {
				pstmt.setString(1, search);
			}
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt("count");//별칭 count , 별칭 없으면 count(*) 로 써줘야함.
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(con!=null) con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return count;
	}
	
}
