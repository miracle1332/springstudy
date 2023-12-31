<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"></c:set>
<!--{pageContext.request.contextPath}가 localhost:8081/controller/에서 controller부분이다.-->

<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="${contextPath}">스프1탄</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li class="active"><a href="${contextPath}">Home</a></li>
      <li><a href="boardMain.do">게시판</a></li>
      </ul>
      <c:if test="${empty mvo}"><!-- 회원가입 유무 판단 -->
      <ul class="nav navbar-nav navbar-right">
            <li><a href="${contextPath}/memLoginForm.do"><span class="glyphicon glyphicon-log-in"></span>로그인</a></li>
            <li><a href="${contextPath}/memberJoin.do"><span class="glyphicon glyphicon-user"></span> 회원가입</a></li>
      </ul>
      </c:if>
      <c:if test="${!empty mvo}">
       <ul class="nav navbar-nav navbar-right">
            <li><a href="${contextPath}/memUpdateForm.do"><span class="glyphicon glyphicon-wrench"></span>회원정보수정</a></li>
            <li><a href="${contextPath}/memImageForm.do"><span class="glyphicon glyphicon-picture"></span>사진등록</a></li>
            <li><a href="${contextPath}/memLogout.do"><span class="glyphicon glyphicon-log-out"></span>로그아웃</a></li>
  		<c:if test="${empty mvo.memProfile}">
  		<li><img class="img-circle" src="${contextPath}/resources/images/default.jpg" style="width:63px; height:63px;" />${mvo.memName} ❤️</li>
		</c:if>	  
		<c:if test="${!empty mvo.memProfile}">
  		<li><img class="img-circle" src="${contextPath}/resources/proimg/${mvo.memProfile}" style="width:63px; height:63px;" />${mvo.memName} ❤️</li>
		</c:if>	  
 	</c:if>
      </ul>
    </div>
  </div>
</nav>
  