UT = {
	handleAjaxError: function(error) {
		let status = error.responseJSON?.status;
		let message = error.responseJSON?.error || '알 수 없는 오류입니다.';
		alert(`[오류 ${status}] ${message}`);
	},
	
	formatDate: function(dateStr) {
		if (!dateStr) return "(No Data)"; // 값이 없는 경우 예외 처리
		let date = new Date(dateStr);
		if (isNaN(date.getTime())) {
			console.warn("Invalid date format:", dateStr);
			return "(Invalid Date)";
		}
		return date.toISOString().split("T")[0] + " " + date.toTimeString().split(" ")[0];
	}


}