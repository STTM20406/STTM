<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<title>회원가입-자바스크립트</title>
<script language="javascript">
   function validate() {
       var re = /^[a-zA-Z0-9]{4,12}$/ // 아이디와 패스워드가 적합한지 검사할 정규식
       var re2 = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
       // 이메일이 적합한지 검사할 정규식

       var id = document.getElementById("id");
       var pw = document.getElementById("pw");
       var email = document.getElementById("email");
       var num1 = document.getElementById("num1");
       var num2 = document.getElementById("num2");

       var arrNum1 = new Array(); // 주민번호 앞자리숫자 6개를 담을 배열
       var arrNum2 = new Array(); // 주민번호 뒷자리숫자 7개를 담을 배열

       // ------------ 이메일 까지 -----------

       if(!check(re,id,"아이디는 4~12자의 영문 대소문자와 숫자로만 입력")) {
           return false;
       }

       if(!check(re,pw,"패스워드는 4~12자의 영문 대소문자와 숫자로만 입력")) {
           return false;
       }

       if(join.pw.value != join.checkpw.value) {
           alert("비밀번호가 다릅니다. 다시 확인해 주세요.");
           join.checkpw.value = "";
           join.checkpw.focus();
           return false;
       }

       if(email.value=="") {
           alert("이메일을 입력해 주세요");
           email.focus();
           return false;
       }

       if(!check(re2, email, "적합하지 않은 이메일 형식입니다.")) {
           return false;
       }

       if(join.name.value=="") {
           alert("이름을 입력해 주세요");
           join.name.focus();
           return false;
       }

       // -------------- 주민번호 -------------

       for (var i=0; i<num1.value.length; i++) {
           arrNum1[i] = num1.value.charAt(i);
       } // 주민번호 앞자리를 배열에 순서대로 담는다.

       for (var i=0; i<num2.value.length; i++) {
           arrNum2[i] = num2.value.charAt(i);
       } // 주민번호 뒷자리를 배열에 순서대로 담는다.

       var tempSum=0;

       for (var i=0; i<num1.value.length; i++) {
           tempSum += arrNum1[i] * (2+i);
       } // 주민번호 검사방법을 적용하여 앞 번호를 모두 계산하여 더함

       for (var i=0; i<num2.value.length-1; i++) {
           if(i>=2) {
               tempSum += arrNum2[i] * i;
           }
           else {
               tempSum += arrNum2[i] * (8+i);
           }
       } // 같은방식으로 앞 번호 계산한것의 합에 뒷번호 계산한것을 모두 더함

       if((11-(tempSum%11))%10!=arrNum2[6]) {
           alert("올바른 주민번호가 아닙니다.");
           num1.value = "";
           num2.value = "";
           num1.focus();
           return false;
       }else{
     	// ------------ 생일 자동 등록 -----------
           if(arrNum2[0]==1 || arrNum2[0]==2) {
               var y = parseInt(num1.value.substring(0,2));
               var m = parseInt(num1.value.substring(2,4));
               var d = parseInt(num1.value.substring(4,6));
               join.years.value = 1900 + y;
               join.month.value = m;
               join.day.value = d;
           }
           else if(arrNum2[0]==3 || arrNum2[0]==4) {
               var y = parseInt(num1.value.substring(0,2));
               var m = parseInt(num1.value.substring(2,4));
               var d = parseInt(num1.value.substring(4,6));
               join.years.value == 2000 + y;
               join.month.value = m;
               join.day.value = d;
           }
       }

       // 관심분야, 자기소개 미입력시 하라는 메시지 출력
       if(join.inter[0].checked==false &&
           join.inter[1].checked==false &&
           join.inter[2].checked==false &&
           join.inter[3].checked==false &&
           join.inter[4].checked==false) {
           alert("관심분야를 골라주세요");
           return false;
       }

       if(join.self.value=="") {
           alert("자기소개를 적어주세요");
           join.self.focus();
           return false;
       }
       
       alert("회원가입이 완료되었습니다.");
   }

   function check(re, what, message) {
       if(re.test(what.value)) {
           return true;
       }
       alert(message);
       what.value = "";
       what.focus();
       //return false;
   }
</script>
<style>
   @import url(http://fonts.googleapis.com/earlyaccess/nanumpenscript.css);
   body{font-family: 'Nanum Pen Script';}
</style>
</head>
<body>
<h1 align="center" style="font-size:50px;"><font color="navy"><b>SIGN UP</b></font></h1>
<form name="join" onsubmit="return validate();" action="http://coding-factory.tistory.com/196" method="post" enctype="text/plain">
   <table width="600" height="400" border="1" align="center" cellspacing="0">
       <tr height="10" align="center">
           <td colspan="2" style="background:navy;" ><font color=white><b>회원기본정보</b></font></td>
       </tr>
       <tr>
           <td><b>아이디:</b></td>
           <td><input type="text" style="width:170px"id="id" name="id" maxlength="12" /> ※4-12자의 영문 대소문자와 숫자로만 입력</td>
       </tr>
       <tr>
           <td><b>비번:</b></td>
           <td><input type="password"  style="width:170px"id="pw" maxlength="12"/> ※4-12자의 영문 대소문자와 숫자로만 입력</td>
       </tr>
       <tr>
           <td><b>비번확인:</b></td>
           <td><input type="password" style="width:170px" id="checkpw" maxlength="12"/></td>
       </tr>
       <tr>
           <td><b>메일주소:</b></td>
           <td><input type="text" style="width:170px" id="email" /> ex)wjdxo513@naver.com</td>
       </tr>
       <tr>
           <td><b>이름:</b></td>
           <td><input type="text" style="width:170px" name="name" maxlength="12" /></td>
       </tr>
       <tr  height="10" align="center">
           <td colspan="2" style="background:navy;"><font color=white><b>개인정보</b></font></td>
       </tr>
       <tr>
           <td><b>주민등록번호:</td>
           <td><input type="text" name="num1" id="num1" size="10" maxlength="6">-</input>
           	<input type="password" name="num2" id="num2" size="10" maxlength="7"></input>
           	예) 123456-1234567
           </td>
       </tr>
       <tr>
           <td><b>생일:</b></td>
           <td>
               <input type="text" style="width:80px" id="years" readonly/>년
               <input type="text" style="width:80px" id="month" readonly/>월
               <input type="text" style="width:80px" id="day" readonly/>일
           </td>
       </tr>
       <tr>
           <td><b>관심분야:</b></td>
           <td><input type="checkbox" name="inter" value="컴퓨터">컴퓨터</input>
               <input type="checkbox" name="inter" value="인터넷">인터넷</input>
               <input type="checkbox" name="inter" value="여행">여행</input>
               <input type="checkbox" name="inter" value="영화감상">영화감상</input>
               <input type="checkbox" name="inter" value="음악감상">음악감상</input>
           </td>
       </tr>
       <tr>
           <td>
               <b>자기소개:</b>
           </td>
           <td><textarea name="self" cols="40" rows="7"></textarea></td>
       </tr>
   </table>
   <center>
       <br/>
       <input type="submit" value="회원가입" style="border-radius:5px; font-s"/>
       <input type="reset" value="다시입력" style="border-radius:5px;" />
   </center>
</form>
</body>
</html>