<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<title>ȸ������-�ڹٽ�ũ��Ʈ</title>
<script language="javascript">
   function validate() {
       var re = /^[a-zA-Z0-9]{4,12}$/ // ���̵�� �н����尡 �������� �˻��� ���Խ�
       var re2 = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
       // �̸����� �������� �˻��� ���Խ�

       var id = document.getElementById("id");
       var pw = document.getElementById("pw");
       var email = document.getElementById("email");
       var num1 = document.getElementById("num1");
       var num2 = document.getElementById("num2");

       var arrNum1 = new Array(); // �ֹι�ȣ ���ڸ����� 6���� ���� �迭
       var arrNum2 = new Array(); // �ֹι�ȣ ���ڸ����� 7���� ���� �迭

       // ------------ �̸��� ���� -----------

       if(!check(re,id,"���̵�� 4~12���� ���� ��ҹ��ڿ� ���ڷθ� �Է�")) {
           return false;
       }

       if(!check(re,pw,"�н������ 4~12���� ���� ��ҹ��ڿ� ���ڷθ� �Է�")) {
           return false;
       }

       if(join.pw.value != join.checkpw.value) {
           alert("��й�ȣ�� �ٸ��ϴ�. �ٽ� Ȯ���� �ּ���.");
           join.checkpw.value = "";
           join.checkpw.focus();
           return false;
       }

       if(email.value=="") {
           alert("�̸����� �Է��� �ּ���");
           email.focus();
           return false;
       }

       if(!check(re2, email, "�������� ���� �̸��� �����Դϴ�.")) {
           return false;
       }

       if(join.name.value=="") {
           alert("�̸��� �Է��� �ּ���");
           join.name.focus();
           return false;
       }

       // -------------- �ֹι�ȣ -------------

       for (var i=0; i<num1.value.length; i++) {
           arrNum1[i] = num1.value.charAt(i);
       } // �ֹι�ȣ ���ڸ��� �迭�� ������� ��´�.

       for (var i=0; i<num2.value.length; i++) {
           arrNum2[i] = num2.value.charAt(i);
       } // �ֹι�ȣ ���ڸ��� �迭�� ������� ��´�.

       var tempSum=0;

       for (var i=0; i<num1.value.length; i++) {
           tempSum += arrNum1[i] * (2+i);
       } // �ֹι�ȣ �˻����� �����Ͽ� �� ��ȣ�� ��� ����Ͽ� ����

       for (var i=0; i<num2.value.length-1; i++) {
           if(i>=2) {
               tempSum += arrNum2[i] * i;
           }
           else {
               tempSum += arrNum2[i] * (8+i);
           }
       } // ����������� �� ��ȣ ����Ѱ��� �տ� �޹�ȣ ����Ѱ��� ��� ����

       if((11-(tempSum%11))%10!=arrNum2[6]) {
           alert("�ùٸ� �ֹι�ȣ�� �ƴմϴ�.");
           num1.value = "";
           num2.value = "";
           num1.focus();
           return false;
       }else{
     	// ------------ ���� �ڵ� ��� -----------
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

       // ���ɺо�, �ڱ�Ұ� ���Է½� �϶�� �޽��� ���
       if(join.inter[0].checked==false &&
           join.inter[1].checked==false &&
           join.inter[2].checked==false &&
           join.inter[3].checked==false &&
           join.inter[4].checked==false) {
           alert("���ɺо߸� ����ּ���");
           return false;
       }

       if(join.self.value=="") {
           alert("�ڱ�Ұ��� �����ּ���");
           join.self.focus();
           return false;
       }
       
       alert("ȸ�������� �Ϸ�Ǿ����ϴ�.");
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
           <td colspan="2" style="background:navy;" ><font color=white><b>ȸ���⺻����</b></font></td>
       </tr>
       <tr>
           <td><b>���̵�:</b></td>
           <td><input type="text" style="width:170px"id="id" name="id" maxlength="12" /> ��4-12���� ���� ��ҹ��ڿ� ���ڷθ� �Է�</td>
       </tr>
       <tr>
           <td><b>���:</b></td>
           <td><input type="password"  style="width:170px"id="pw" maxlength="12"/> ��4-12���� ���� ��ҹ��ڿ� ���ڷθ� �Է�</td>
       </tr>
       <tr>
           <td><b>���Ȯ��:</b></td>
           <td><input type="password" style="width:170px" id="checkpw" maxlength="12"/></td>
       </tr>
       <tr>
           <td><b>�����ּ�:</b></td>
           <td><input type="text" style="width:170px" id="email" /> ex)wjdxo513@naver.com</td>
       </tr>
       <tr>
           <td><b>�̸�:</b></td>
           <td><input type="text" style="width:170px" name="name" maxlength="12" /></td>
       </tr>
       <tr  height="10" align="center">
           <td colspan="2" style="background:navy;"><font color=white><b>��������</b></font></td>
       </tr>
       <tr>
           <td><b>�ֹε�Ϲ�ȣ:</td>
           <td><input type="text" name="num1" id="num1" size="10" maxlength="6">-</input>
           	<input type="password" name="num2" id="num2" size="10" maxlength="7"></input>
           	��) 123456-1234567
           </td>
       </tr>
       <tr>
           <td><b>����:</b></td>
           <td>
               <input type="text" style="width:80px" id="years" readonly/>��
               <input type="text" style="width:80px" id="month" readonly/>��
               <input type="text" style="width:80px" id="day" readonly/>��
           </td>
       </tr>
       <tr>
           <td><b>���ɺо�:</b></td>
           <td><input type="checkbox" name="inter" value="��ǻ��">��ǻ��</input>
               <input type="checkbox" name="inter" value="���ͳ�">���ͳ�</input>
               <input type="checkbox" name="inter" value="����">����</input>
               <input type="checkbox" name="inter" value="��ȭ����">��ȭ����</input>
               <input type="checkbox" name="inter" value="���ǰ���">���ǰ���</input>
           </td>
       </tr>
       <tr>
           <td>
               <b>�ڱ�Ұ�:</b>
           </td>
           <td><textarea name="self" cols="40" rows="7"></textarea></td>
       </tr>
   </table>
   <center>
       <br/>
       <input type="submit" value="ȸ������" style="border-radius:5px; font-s"/>
       <input type="reset" value="�ٽ��Է�" style="border-radius:5px;" />
   </center>
</form>
</body>
</html>