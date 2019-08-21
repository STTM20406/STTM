<script type="text/javascript">
  <!--
   function Check(form)

   {
        //'확인' 버튼을 클릭했을 때 실행되는 메서드
        var msg = "";

 

        if (form.cb1.checked)
             msg += form.cb1.value + "\n";
        if (form.cb2.checked)
             msg += form.cb2.value + "\n";
        if (form.cb3.checked)
             msg += form.cb3.value + "\n";

        alert(msg);
   }
  //-->
</script>

<form name="form1">
      <input type="checkbox" name="cb1" value="1"> C# <br />
      <input type="checkbox" name="cb2" value="2"> ASP.NET <br />
      <input type="checkbox" name="cb3" value="3"> XML <br />
      <input type="button" value="확인" onclick="Check(this.form);">
</form>