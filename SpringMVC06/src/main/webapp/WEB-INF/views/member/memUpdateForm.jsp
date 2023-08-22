<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="mvo" value="${SPRING_SECURITY_CONTEXT.authentication.principal}"/>
<c:set var="auth" value="${SPRING_SECURITY_CONTEXT.authentication.authorities}"/>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <script type="text/javascript">
  $(document).ready(function() {
	 if(${!empty msgType}){ //msgType이 비어있지 않다는 것은 실패메세지가 들어있다는 뜻!!
			$("#messageType").attr("class","modal-content panel-warning");
			 $("#myMessage").modal("show");
	 } 
  });
  	function registerCheck() {
  		var memID=$("#memID").val(); 
  		$.ajax({
  			url : "${contextPath}/memRegisterCheck.do",
  			type : "get",
  			data : {"memID":memID},
  			success : function(result) {
  				//중복유무 출력(result=1 : 사용할 수 있는 아이디, 0:사용할 수 없는 아이디)
 			 if(result==1) {
 				 $("#checkMessege").html("사용가능한 아이디입니다."); 
 				 $("#checkType").attr("class","modal-content panel-success");
 			 }else{ 
 				 $("#checkMessege").html("사용할 수 없는 아이디입니다."); 
 				 $("#checkType").attr("class","modal-content panel-warning");
 			}
  				$("#myModal").modal("show"); //모달html의 아이디로 modal("show")라는 코드를 입력해야 띄어진다.
  			},
  			error : function(){alert("errorr..");}
  		});
  	}
  	function passwordCheck(){
  		memPassword1=$("#memPassword1").val();
  		memPassword2=$("#memPassword2").val();
  		var target = document.getElementById("passMessage");
  		if(memPassword1 != memPassword2){
  			target.style.color="red";
  			 $("#passMessage").html("비밀번호가 서로 일치하지 않습니다.");
  		}else { 
  			target.style.color="blue";
  			 $("#passMessage").html("비밀번호가 일치합니다.");
  			 $("#memPassword").val(memPassword1);
  		} 		
  	}
  	function goUpdate(){
  		var memAge=$("#memAge").val();
  		if(memAge==null || memAge=="") {
  			alert("나이를 입력하세요.");
  			return false;
  		}
  		document.frm.submit(); //전송
  	}
  </script>
  </head>
<body>
<div class="container">
 <jsp:include page="../common/header.jsp"/>
  <h2>Spring mvc03</h2>
  <div class="panel panel-default">
    <div class="panel-heading">회원정보 수정 양식</div>
    <div class="panel-body">
    	<form name="frm" action="${contextPath}/memUpdate.do?" method="post">
            <input type="hidden" id="memID" name="memID" value="${mvo.member.memID}"/>
    	<input type="hidden" id="memPassword" name="memPassword" value=""/>
    		<table class="table table-bordered" style="text-align: center; border: 1px solid #dddd;">
    		  <tr>
    		  	<td style="width: 110px; vertical-align:middle;">아이디</td>
    		  	<td>${mvo.memID }</td>
    		  </tr>
    		  <tr>
    		  	<td style="width: 110px; vertical-align:middle;">비밀번호</td>
    		  	<td colspan="2"><input type="password" id="memPassword1" name="memPassword1" class="form-control" maxlength="20" placeholder="비밀번호를 입력하세요."/></td>
    		  </tr>
    		   <tr>
    		  	<td style="width: 110px; vertical-align:middle;">비밀번호확인</td>
    		  	<td colspan="2"><input type="password" id="memPassword2" name="memPassword2" onkeyup="passwordCheck()" class="form-control" maxlength="20" placeholder="비밀번호를 확인하세요."/></td>
    		  </tr>
    		  <tr>
    		  	<td style="width: 110px; vertical-align:middle;">사용자 이름</td>
    		  	<td colspan="2"><input type="text" id="memName" name="memName" class="form-control" maxlength="20" placeholder="이름을 입력하세요." value="${mvo.memName }"/></td>
    		  </tr>
    		  <tr>
    		  	<td style="width: 110px; vertical-align:middle;">나이</td>
    		  	<td colspan="2"><input type="number" id="memAge" name="memAge" class="form-control" maxlength="20" placeholder="나이를 입력하세요."  value="${mvo.memAge }"/></td>
    		  </tr>
    		  <tr>
    		  	<td style="width: 110px; vertical-align:middle;">성별</td>
    		  	<td colspan="2">
	    		  	<div class="form-group" style="text-align: center; margin: 0 auto;">
		    		  	<div class="btn-group" data-toggle="buttons">
		    		  	<label class="btn btn-primary <c:if test="${mvo.memGender eq '남자' }">active</c:if>">
		    		  		<input type="radio" id="memGender" name="memGender" autocomplete="off" value="남자" 
		    		  		<c:if test="${mvo.memGender eq '남자' }">checked</c:if>/> 남자
		    		  	</label>
		    			<label class="btn btn-primary <c:if test="${mvo.memGender eq '여자' }">active</c:if>">
		    		  		<input type="radio" id="memGender" name="memGender" autocomplete="off" value="여자"
		    		  		<c:if test="${mvo.memGender eq '여자' }">checked</c:if>  /> 여자
		    		  	</label>
		    		  	</div>
	    		  	</div>
    		  	</td>
    		  </tr>
    		   <tr>
    		  	<td style="width: 110px; vertical-align:middle;">이메일</td>
    		  	<td colspan="2"><input type="text" id="memEmail" name="memEmail" class="form-control" maxlength="50" placeholder="이메일 주소를 입력하세요." value="${mvo.memEmail}"/></td>
    		  </tr>
    		  <!-- 선택한 권한 출력하기 -->
    		  <tr>
    		  	<td style="width: 110px; vertical-align:middle;">사용자 권한</td>
    		  	<td colspan="2">
    		  		<input type="checkbox" name="authList[0].auth" value="ROLE_USER"  
    		  			<c:forEach var="authVO" items="${mvo.authList}">
    		  				<c:if test="${authVO.auth eq 'ROLE_USER'}">
    		  				checked
    		  				</c:if>
    		  			</c:forEach>
    		  		/>ROLE_USER	
    		  		<input type="checkbox" name="authList[1].auth" value="ROLE_MANAGER"
    		  			<c:forEach var="authVO" items="${mvo.authList}">
    		  				<c:if test="${authVO.auth eq 'ROLE_MANAGER'}">
    		  				checked
    		  				</c:if>
    		  			</c:forEach>
    		  		/>ROLE_MANAGER
    		  		
    		  		<input type="checkbox" name="authList[2].auth" value="ROLE_ADMIN"
    		  			<c:forEach var="authVO" items="${mvo.authList}">
    		  				<c:if test="${authVO.auth eq 'ROLE_ADMIN'}">
    		  				checked
    		  				</c:if>
    		  			</c:forEach>
    		  		/>ROLE_ADMIN
    		  	</td>
    		  </tr>
    		  <tr>
    		  	  	<td colspan="3" style="text-align:left;">
    		  		<span  id="passMessage" ></span><input type="button" class="btn btn-primary btn-sm pull-right" value="수정" onclick="goUpdate()"/>
    		  		</td>
    		  </tr>
    		</table>
    		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    	</form>
    </div>
   
	<!-- 실패메시지 출력 -->
	<div id="myMessage" class="modal fade" role="dialog">
	  <div class="modal-dialog">
	    <!-- Modal content-->
	    <div id="messageType" class="modal-content panel-info" >
	      <div class="modal-header  panel-heading">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">${msgType}</h4>
	      </div>
	      <div class="modal-body">
	        <p>${msg}</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	      </div>
	    </div>
  	  </div>
	</div>
    <div class="panel-footer">스프링 공부중 mvc03_오혜린</div>
  </div>
</div>

</body>
</html>
