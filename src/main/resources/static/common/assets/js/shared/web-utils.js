

	//ajax

	function ajaxCommon(pUrl, pType, pDataType, pData, cbFunction){

//		//ajax 호출 방법
//		var data = {board_type:"notice"};
//		ajaxCommon('/comm/all/boards/limit/10', 'GET', 'json', data, ajaxCommonAfter);
//
//		//ajax 성공 시 결과 처리
//		function ajaxCommonAfter(data) {
//
//			for ( var i = 0 ; i < data.list.length ; i ++) {
//				//somthing todo
//			}
//
//		}


		$.ajax({
			url		: pUrl,
			async	: true,// false 일 경우 동기 요청으로 변경
			type	: pType, // POST, GET, PUT, DELETE
//			contentType: 'application/json',
			dataType: pDataType,
			data	: pData,
			beforeSend : function(xhr){
				   xhr.setRequestHeader("call", "AJAX");
		   	},
			success: function( data) {

				//e
				if( data.type =="error") {
//					GeneralUtil.alert('error', data.message);
					data =null;
					return false;
				}

				//success
				if (cbFunction) {
					cbFunction(data);
				}

			},
			//error : function (jqXHR, textStatus, errorThrown) {
			error : function(jqXHR, exception) {
		        if (jqXHR.status === 0) {
		            alert('Not connect.\n Verify Network.');
		        }
		        else if (jqXHR.status == 400) {
		            alert('Server understood the request, but request content was invalid. [400]');
		        }
		        else if (jqXHR.status == 401) {
		            alert('Unauthorized access. [401]');
		        }
		        else if (jqXHR.status == 403) {
		            alert('Forbidden resource can not be accessed. [403]');
		        }
		        else if (jqXHR.status == 404) {
		            alert('Requested page not found. [404]');
		        }
		        else if (jqXHR.status == 500) {
		            alert('Internal server error. [500]');
		        }
		        else if (jqXHR.status == 503) {
		            alert('Service unavailable. [503]');
		        }
		        else if (exception === 'parsererror') {
		            alert('Requested JSON parse failed. [Failed]');
		        }
		        else if (exception === 'timeout') {
		            alert('Time out error. [Timeout]');
		        }
		        else if (exception === 'abort') {
		            alert('Ajax request aborted. [Aborted]');
		        }
		        else {
		            alert('Uncaught Error.n' + jqXHR.responseText);
		        }
		   }

		});
	}







	//브라우저 호환 처리

	//log
	var console = window.console || { log: function() {} }; //아래 alert 창으로 찍고 싶으면 주석으로 막으세요.

	function log() {
	    try {
	      console.log.apply(console, arguments);                  //#1 크롬
	} catch(e){                                                //#2
		try {
			opera.postError.apply(opera, arguments);              //#3 오페라
	} catch(e){
	   // alert(Array.prototype.join.call( arguments, " "));    //#4 IE
	}
		}
	}

	//startsWith - IE에서 동작 하도록 처리 -
	if (!String.prototype.startsWith) {
	    String.prototype.startsWith = function(searchString, position){
	      position = position || 0;
	      return this.substr(position, searchString.length) === searchString;
	  };
	}