/* ****************
 *  일정 편집
 * ************** */
var editEvent = function (event, element, view) {

    $('.popover.fade.top').remove();
    $(element).popover("hide");
    console.log(element);
    $("#contextMenu").hide();
    
    if (event.allDay === true) {
        editAllDay.prop('checked', true);
    } else {
        editAllDay.prop('checked', false);
    }

    if (event.end === null) {
        event.end = event.start;
    }

    if (event.allDay === true && event.end !== event.start) {
        editEnd.val(moment(event.end).subtract(1, 'days').format('YYYY-MM-DD HH:mm'))
    } else {
        editEnd.val(event.end.format('YYYY-MM-DD HH:mm'));
    }

    modalTitle.html('일정 수정');
    editTitle.val(event.title);
    editStart.val(event.start.format('YYYY-MM-DD HH:mm'));
    editType.val(event.type);
    editDesc.val(event.description);
    editColor.val(event.backgroundColor).css('color', event.backgroundColor);

    addBtnContainer.hide();
    modifyBtnContainer.show();
    eventModal.modal('show');

    //업데이트 버튼 클릭시
    $('#updateEvent').unbind();
    $('#updateEvent').on('click', function () {

        if (editStart.val() > editEnd.val()) {
            alert('끝나는 날짜가 앞설 수 없습니다.');
            return false;
        }

        if (editTitle.val() === '') {
            alert('일정명은 필수입니다.')
            return false;
        }

        var statusAllDay;
        var startDate;
        var endDate;
        var displayDate;

        if (editAllDay.is(':checked')) {
            statusAllDay = true;
            startDate = moment(editStart.val()).format('YYYY-MM-DD');
            endDate = moment(editEnd.val()).format('YYYY-MM-DD');
            displayDate = moment(editEnd.val()).add(1, 'days').format('YYYY-MM-DD');
        } else {
            statusAllDay = false;
            startDate = editStart.val();
            endDate = editEnd.val();
            displayDate = endDate;
        }

        eventModal.modal('hide');

        event.allDay = statusAllDay;
        event.title = editTitle.val(); //wrk_nm
        event.start = startDate; //wrk_start_dt
        event.end = displayDate; //wrk_end_dt
        event.type = editType.val(); //wrk_lst_id
        event.backgroundColor = editColor.val(); //wrk_color_cd
        event.description = editDesc.val();

        $("#calendar").fullCalendar('updateEvent', event);

        //일정 업데이트
        $.ajax({
            method :"post",
            url: "/upW",
            contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
            data: "wrk_id=" +event._id +   
            	  "&wrk_nm=" + event.title +
            	  "&wrk_start_dt=" + event.start + 
            	  "&wrk_end_dt=" + event.end +
            	  "&wrk_lst_id=" + event.type + 
            	  "&wrk_color_cd=" + event.backgroundColor,
            success: function (response) {
                alert('해당 업무가 수정되었습니다.')
            }
        });

    });

    // 삭제버튼
    $('#deleteEvent').on('click', function () {
        $('#deleteEvent').unbind();
        $("#calendar").fullCalendar('removeEvents', [event._id]);
        eventModal.modal('hide');

        console.log(event._id);
        //삭제시
        $.ajax({
        	method:"post",
            url: "/delW",
            data: "wrk_id=" +event._id,  
            contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
            success: function (response) {
                alert('해당 업무가 삭제되었습니다.');
            }
        });
    });
};