<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    int listcount = ((Integer)request.getAttribute("listcount")).intValue();
//int listcount = Integer.parseInt(request.getattribute("listcount").toString());
    int nowpage = ((Integer)request.getAttribute("page")).intValue();
    int maxpage = ((Integer)request.getAttribute("maxpage")).intValue();
    int startpage = ((Integer)request.getAttribute("startpage")).intValue();
    int endpage = ((Integer)request.getAttribute("endpage")).intValue();

%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>게시판리스트</title>
		<link type="text/css" rel="stylesheet" href="css/notice_list_style.css">
		<link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR:500&display=swap" rel="stylesheet">
        <style>
        .page_num li{
            display: inline-block;
            padding-left: 10px;
            padding-right: 10px;
        }
        .bsearch{
            width: 400px;
            margin: 0 auto;
        }
        </style>
	</head>
	<body>
		<header>
			<div id="nav_up">
				<ul>
					<li>회원가입</li>
					<li>로그인</li>
					<li>고객행복센터</li>
					<li>배송지역검색</li>
					<li>기프트카드 등록</li>
				</ul>
			</div>
			
			<nav>
				<h1></h1>
				<ul>
					<li>COOKIT소개</li>
					<li>COOKIT메뉴</li>
					<li>리뷰</li>
					<li>이벤트</li>
					<li>MY쿡킷</li>
				</ul>
				<ul>
					<li><a href="#"><span>장바구니</span></a></li>
					<li><a href="#"><span>메뉴찾기</span></a></li>
				</ul>
			</nav>
		</header>
        <!-- 검색 -->
        <div class="bsearch">
            <form action="search.do" name="bsearch" method="get">
                <select name="opt">
                    <option value="all">전체</option>
                    <option value="tit">제목</option>
                    <option value="con">내용</option>
                </select>
                <input type="text" name="search">
                <input type="submit" value="검색">                
            </form>
		</div>
		<section>
			<div>
				<h1>공지사항</h1>
				<div>
					<div>
						<table>
							<colgroup>
								<col width="10%">
								<col width="*">
								<col width="15%">
								<col width="20%">
								<col width="10%">
							</colgroup>
							<thead>
								<tr>
									<th>번호</th>
									<th>제목</th>
									<th>작성자</th>
									<th>등록일</th>
									<th>조회수</th>
								</tr>
							</thead>
							<tbody>
                            
                            <c:forEach var="dto" items="${ list }">
								<tr>
									<td>${ dto.bId }</td>
									<td class="tit">
                                    <c:forEach begin="1" end="${ dto.bIndent }">▶</c:forEach>
                                    <a href="content_view.do?bId=${ dto.bId }" class="list_a">${ dto.bTitle }</a></td>
									<td>${ dto.bName }</td>
									<td>${ dto.bDate }</td>
									<td>${ dto.bHit }</td>
								</tr>
                            </c:forEach>
							</tbody>
						</table>
						
					</div>
					<div>
						<span>
							<a href="write_view.do">입력</a>
						</span>
					</div>
						
					<div>
                    
                    <c:choose>
<!--                         searchflag 가 있을 때 -->
                        <c:when test="${ searchflag!=null }">
                        <ul class="page_num">
<!--                                 첫번째 페이지로 이동 -->
                                <a href="list.do?searchflag=${ searchflag }&opt=${ opt }&search=${ search }&page=1"><li class = "first">처음 페이지로</li></a>
                                
<!--                                 이전페이지 이동버튼 -->
                                <c:if test="${ page<=1 }">
                                     <li class = "prev">이전</li>
                                </c:if>
                                <c:if test="${ page>1 }">
                                    <a href="list.do?searchflag=${ searchflag }&opt=${ opt }&search=${ search }&page=${ page-1 }"><li class = "prev">이전</li></a>
                                </c:if>
                                
<!--                                 순차적으로 페이지 range출력 -->
                                <c:forEach var="a" begin="${ startpage }" end="${ endpage }" step="1">
                                    <c:choose>
                                        <c:when test="${ a == page }">
                                            <li class = "num"><div>${ a }</div></li>
                                        </c:when>
                                        <c:when test="${ a != page }">
                                            <a href="list.do?searchflag=${ searchflag }&opt=${ opt }&search=${ search }&page=${ a }"><li class = "num"><div>${ a }</div></li></a>
                                        </c:when>
                                    </c:choose>
                                </c:forEach>
                                
<!--                                 다음페이지 이동버튼 -->
                                <c:if test="${ page>=maxpage }">
                                    <li class = "next">다음</li>
                                </c:if>
                                <c:if test="${ page<maxpage }">
                                    <a href="list.do?searchflag=${ searchflag }&opt=${ opt }&search=${ search }&page=${ page+1 }"><li class = "next">다음</li></a>
                                </c:if>
                                
<!--                                 마지막 페이지로 이동 -->
                                <a href="list.do?searchflag=${ searchflag }&opt=${ opt }&search=${ search }&page=${ maxpage }">
                                    <li class = "last">마지막 페이지로</li>
                                </a>
                            </ul>
                        </c:when>
                        
<!--                         searchflag 가 없을 때 -->
                        <c:otherwise>
                            <ul class="page_num">
<!--                                 첫번째 페이지로 이동 -->
                                <a href="list.do?page=1"><li class = "first">처음 페이지로</li></a>
                                
<!--                                 이전페이지 이동버튼 -->
                                <c:if test="${ page<=1 }">
                                     <li class = "prev">이전</li>
                                </c:if>
                                <c:if test="${ page>1 }">
                                    <a href="list.do?page=${ page-1 }"><li class = "prev">이전</li></a>
                                </c:if>
                                
<!--                                 순차적으로 페이지 range출력 -->
                                <c:forEach var="a" begin="${ startpage }" end="${ endpage }" step="1">
                                    <c:choose>
                                        <c:when test="${ a == page }">
                                            <li class = "num"><div>${ a }</div></li>
                                        </c:when>
                                        <c:when test="${ a != page }">
                                            <a href="list.do?page=${ a }"><li class = "num"><div>${ a }</div></li></a>
                                        </c:when>
                                    </c:choose>
                                </c:forEach>
                                
<!--                                 다음페이지 이동버튼 -->
                                <c:if test="${ page>=maxpage }">
                                    <li class = "next">다음</li>
                                </c:if>
                                <c:if test="${ page<maxpage }">
                                    <a href="list.do?page=${ page+1 }"><li class = "next">다음</li></a>
                                </c:if>
                                
                                <!-- 마지막 페이지로 이동 -->
                                <a href="list.do?page=${ maxpage }">
                                    <li class = "last">마지막 페이지로</li>
                                </a>
                            </ul>
                        </c:otherwise>
                    </c:choose>

                    
                    
<!-- 						<span> -->
<!-- 							<strong>1</strong> -->
<!-- 							<a href="#">2</a> -->
<!-- 						</span> -->
<!-- 						<a href="#"></a> -->
<!-- 						<a href="#"></a> -->
					</div>
				</div>
			</div>
		</section>
		
		<footer>
			<div id="footer">
				<div>
				  <h1><a href="#"></a></h1>
				  <p>© COOKIT ALL RIGHTS RESERVED</p>
				</div>
					
				<div>
					<ul>
					<li><a href="#">이용약관</a></li>
					<li class="lime"><a href="#">개인정보처리 방침</a></li>
					<li><a href="#">법적고지</a></li>
					<li><a href="#">사업자정보 확인</a></li>
					</ul>
					<p>씨제이제일제당(주)</p>
					<p>대표이사 : 손경식,강신호,신현재</p>
					<p class="right_n">사업자등록번호 : 104-86-09535</p>
					<p>주소 : 서울 중구 동호로 330 CJ제일제당 센터 (우) 04560</p>
					<p>통신판매업신고 중구 제 07767호  </p>
					<p class="right_n">개인정보보호책임자 : 조영민</p>
					<p>이메일 : cjon@cj.net</p>
					<p class="right_n">호스팅제공자 : CJ올리브네트웍스㈜</p>
					<p class="right_n">고객님은 안전거래를 위해 현금등으로 결제시 LG U+ 전자 결제의 매매보호(에스크로) 서비스를 이용하실 수 있습니다.<a href="#">가입 사실 확인</a></p>
				</div>
						
				<div>
					<button type="button"><span>CJ그룹계열사 바로가기 ▽</span></button>
					<p>고객행복센터</p><p>1668-1920</p>
					<div><a href="#">1:1문의</a></div>
				</div>
			</div>
		</footer>
	</body>
