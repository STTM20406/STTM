<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
  <title>check박스 체크 여부 확인하기</title>
 
  <script type="text/javascript">
    function CheckForm(Join){
        
        //체크박스 체크여부 확인 [하나]
        var chk1=document.frmJoin.U_checkAgreement1.checked;
        var chk2=document.frmJoin.U_checkAgreement2.checked;
        
        if(!chk1){
            alert('약관1에 동의해 주세요');
            return false;
        } 
        if(!chk2) {
            alert('약관2에 동의해 주세요');
            return false;
        }

        //체크박스 체크여부 확인 [동일 이름을 가진 체크박스 여러개일 경우]
        var isSeasonChk = false;
        var arr_Season = document.getElementsByName("SEASON[]");
        for(var i=0;i<arr_Season.length;i++){
            if(arr_Season[i].checked == true) {
                isSeasonChk = true;
                break;
            }
        }
    
        if(!isSeasonChk){
            alert("계절의 종류를 한개 이상 선택해주세요.");
            return false;
        }

    }


 </script>
 </head>
 <body>
 
  
<form name="frmJoin" action=""  onSubmit="return CheckForm(this)">
    <input type="checkbox" name="U_checkAgreement1" id="U_checkAgreement1" value="" /> 약관동의
    <input type="checkbox" name="U_checkAgreement2" id="U_checkAgreement2" value="" /> 약관동의

    <br/>
    <br/>
    <input type="checkbox" id="spring" name="SEASON[]" value="1" />봄
    <input type="checkbox" id="summer" name="SEASON[]" value="2" />여름
    <input type="checkbox" id="fall" name="SEASON[]" value="3" />가을
    <input type="checkbox" id="windter" name="SEASON[]" value="4" />겨울
    <br/>
    <br/>
    <input type="submit" value="전송">
</form>

 </body>
</html>
