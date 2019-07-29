<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script>
	$(document).ready(function(){

		$(".workList_set").hide();

		$(".workList_set_i").on("click", function(){
			$(this).next(".workList_set").fadeIn(300);
		});

		$(".workList_set").mouseleave(function(){
			$(this).fadeOut(300);
		});
	});
</script>


<div class="sub_menu">
	<ul class="sub_menu_item">
		<li><a href="">업무</a></li>
		<li><a href="">간트차트</a></li>
		<li><a href="">업무분석</a></li>
		<li><a href="">파일&amp;링크</a></li>
	</ul>
	<div class="sub_btn">
		<ul>
			<li><input type="button" value="4"></li>
			<li><input type="button" value="프로젝트 대화"></li>
			<li><input type="button" value="프로젝트 설정"></li>
		</ul>
	</div>
</div>

<section class="contents">
	<h2>병원 사이트 리뉴얼</h2>

	<div class="work_list_wrap">
		<div class="workList">
			<div class="workList_hd">
				<dl>
					<dt>월요일</dt>
					<dd>
						<input type="button" class="workList_add_i" value="새업무 추가">
						<a href="javascript:;" class="workList_set_i">업무리스트 설정</a>
						<div class="workList_set">
							<input type="button" value="업무리스트 삭제">
						</div>
					</dd>
				</dl>
				<ul>
					<li>
						<p>진행중 업무 <span>4</span></p>
						<a href="javascript:;">완료된업무보기 <span>2</span></a>
					</li>
					<li class="graph"></li>
				</ul>
			</div>
			<div class="workList_content">
				<div class="wkList_item">
					
				</div>
			</div>
		</div>
		<div class="workList">
			<div class="workList_hd">
				<dl>
					<dt>화요일</dt>
					<dd>
						<input type="button" class="workList_add_i" value="새업무 추가">
						<a href="javascript:;" class="workList_set_i">업무리스트 설정</a>
						<div class="workList_set">
							<input type="button" value="업무리스트 삭제">
						</div>
					</dd>
				</dl>
				<ul>
					<li>
						<p>진행중 업무 <span>4</span></p>
						<a href="javascript:;">완료된업무보기 <span>2</span></a>
					</li>
					<li class="graph"></li>
				</ul>
			</div>
			<div class="workList_content">
				<div class="wkList_item">
					
				</div>
			</div>
		</div>
	</div>
</section>

<br><br><br><br>
<h2 class="btn_style_h2">button style</h2>

<a href="" class="a_style_01">확인</a>
<a href="" class="a_style_02">취소</a>
<a href="" class="a_style_03">목록</a>
<a href="" class="a_style_04">수정</a><br><br>

<button type="button" class="btn_style_01">확인</button>
<button type="button" class="btn_style_02">취소</button>
<button type="button" class="btn_style_03">목록</button>
<button type="button" class="btn_style_04">수정</button><br><br>

<input type="button" class="inp_style_01" value="취소">
<input type="button" class="inp_style_02" value="확인">
<input type="button" class="inp_style_03" value="목록">
<input type="button" class="inp_style_04" value="수정">


<h2 class="btn_style_h2">table style</h2>

<!-- table search box start -->
<div class="searchBox">
	<div class="tb_sch_wr">
		<fieldset id="hd_sch">
               <legend>사이트 내 전체검색</legend>
               <form name="" method="" action="" onsubmit="">
                <select>
                	<option>검색옵션</option>
                	<option>검색옵션느라</option>
                	<option>없음빼라</option>
                </select>
                <input type="text" name="" id="" maxlength="20" placeholder="검색어를 입력해주세요">
                <button type="submit" id="sch_submit" value="검색">검색</button>
               </form>
          	</fieldset>
         	</div>
        	</div>
<!-- table search box end -->


<!-- table style start -->
<table class="tb_style_01">
	<caption>테이블 이름</caption>
	<tr>
		<th>ID</th>
		<th>Subject</th>
		<th>Write</th>
		<th>Date</th>
		<th>Count</th>
	</tr>

	<tr>
		<td>1</td>
		<td class="subject">덴마크 민트라떼 커피 한잔에 담긴 브라질의 태양</td>
		<td>민트박</td>
		<td>2019-07-03</td>
		<td>32</td>
	</tr>
	<tr>
		<td>2</td>
		<td class="subject">꼭 이것만을 알고 먹자!!</td>
		<td>민트박</td>
		<td>2019-07-03</td>
		<td>32</td>
	</tr>
	<tr>
		<td>3</td>
		<td class="subject">허브의 일종인 민트는 산뜻한 향으로 음식의..</td>
		<td>민트박</td>
		<td>2019-07-03</td>
		<td>32</td>
	</tr>
</table>
<!-- table style end -->


<br><br><h2 class="btn_style_h2">pagination style</h2>

<div class="pagination">
	<a href="" class="btn_first"></a>
	<span>1</span>
	<a href="">2</a>
	<a href="">3</a>
	<a href="" class="btn_last"></a>
</div>