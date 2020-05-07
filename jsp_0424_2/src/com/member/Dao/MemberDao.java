package com.member.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.member.Dto.MemberDto;

public class MemberDao {

	private MemberDao() {}
	
	private static MemberDao instance = new MemberDao();
	
	public static MemberDao getInstance() {
		return instance;
	}
	
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	MemberDto mdto;
	String sql;
	
	//전체 회원 데이터 select
	public ArrayList<MemberDto> getMembers(){
		ArrayList<MemberDto> list = new ArrayList<MemberDto>();
		sql ="select * from b_member order by b_date";
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			
			
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
	}//getmembers()
	
	
	//멤버 등록
	public int insertMember(MemberDto mdto) {
		int check=0;
		
		return check;
	}
	
	
	//멤버 수정
	public int updateMember(String id) {
		int check = 0;
		
		return check;
	}
	
	//멤버 삭제
	public int deleteMember(String id) {
		int check = 0;
		
		return check;
	}
	
	
	
	
	
	
	//커넥션풀
	private Connection getConnection() {
		Context context;
		DataSource ds;
		try {
			context = new InitialContext();
			ds = (DataSource) context.lookup("java:comp/env/jdbc/Oracle11g");
			con = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}//getconnection()
	
}
