UT = {
	handleAjaxError: function(error) {
		let status = error.responseJSON?.status;
		let message = error.responseJSON?.error || '알 수 없는 오류입니다.';
		alert(`[오류 ${status}] ${message}`);
	},


}