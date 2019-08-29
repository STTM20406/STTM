var eventModal = $('#eventModal');

var modalTitle = $('.modal-title');
var editAllDay = $('#edit-allDay');
var editTitle = $('#edit-title');
var editStart = $('#edit-start');
var editEnd = $('#edit-end');
var editType = $('#edit-type');
var editColor = $('#edit-color');
var editDesc = $('#edit-desc');

var addBtnContainer = $('.modalBtnContainer-addEvent');
var modifyBtnContainer = $('.modalBtnContainer-modifyEvent');


/* ****************
 *  새로운 일정 생성
 * ************** */
var newEvent = function (start, end, eventType) {

	$("#prj_selected").prop("selected", true);
	
    $("#contextMenu").hide(0); //메뉴 숨김

    modalTitle.html('새로운 일정');
    editStart.val(start);
    editEnd.val(end);
    editType.val(eventType).prop("selected", true);
    
    
    var optionText = "";
    editType.on('change', function(){
    	optionText =$(this).children("option:checked").text();
    })

    addBtnContainer.show();
    modifyBtnContainer.hide();
    eventModal.modal('show');


    //새로운 일정 저장버튼 클릭
    $('#save-event').unbind();
    $('#save-event').on('click', function () {
    	console.log(editType);
        var eventData = {
//            _id: eventId,
            title: editTitle.val(), //업무명?
            start: editStart.val(), //
            end: editEnd.val(),
            description: editDesc.val(),
            type: editType.val(), //업무 리스트
            username: optionText, //업무리스트
            backgroundColor: editColor.val(),//배경색
            textColor: '#ffffff', 
            allDay: false
        };

        if (eventData.start > eventData.end) {
 			$(".ctxt").text("끝나는 날짜가 앞설 수 없습니다.");
			layer_popup("#layer2");
 			return false;
        }

        if (eventData.title === '') {
        	$(".ctxt").text("일정명은 필수 입력사항입니다.");
			layer_popup("#layer2");
        	return false;
        }

        var realEndDay;

        if (editAllDay.is(':checked')) {
            eventData.start = moment(eventData.start).format('YYYY-MM-DD');
            //render시 날짜표기수정
            eventData.end = moment(eventData.end).add(1, 'days').format('YYYY-MM-DD');
            //DB에 넣을때(선택)
            realEndDay = moment(eventData.end).format('YYYY-MM-DD');

            eventData.allDay = true;
        }

        $("#calendar").fullCalendar('renderEvent', eventData, true);
        eventModal.find('input, textarea').val('');
        editAllDay.prop('checked', false);
        eventModal.modal('hide');
        
        //새로운 일정 저장
        $.ajax({
        	method:"post",
            url: "/addEvent",
            contentType: "application/json",
            dataType : "json",
            data: JSON.stringify(eventData), //컨트롤러에서 필요한것만 뽑아쓰면된다 Vo를 하나 만들어서 Vo로 받자!
//            data: "user_id="+userId, //컨트롤러에서 필요한것만 뽑아쓰면된다 Vo를 하나 만들어서 Vo로 받자!
            success: function (response) {
            	//등록후 알림창뜨게!
                $(".ctxt").text("해당 업무가 Calendar에 등록이 되었습니다.");
        			layer_popup("#layer2");
        			
            	//DB연동시 중복이벤트 방지를 위한
                $('#calendar').fullCalendar('removeEvents');
                $('#calendar').fullCalendar('refetchEvents');

            }
        });
    });
};
