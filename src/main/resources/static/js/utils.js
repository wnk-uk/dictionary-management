const UT = {
	handleAjaxError: function(error) {
		let status = error.responseJSON?.status || 500;
		let message = error.responseJSON?.message || '알 수 없는 오류가 발생했습니다.';
		
		Swal.fire({
			icon: "error",
			title: `🚨 오류 [${status}]`,
			text: message
		});
	},
	formatDateTime: function(dateStr) {
		if (!dateStr) return "(No Data)"; // 값이 없는 경우 예외 처리
		let date = new Date(dateStr);
		if (isNaN(date.getTime())) {
			console.warn("Invalid date format:", dateStr);
			return "(Invalid Date)";
		}
		return date.toISOString().split("T")[0] + " " + date.toTimeString().split(" ")[0];
	},
	formatDate: function(dateStr) {
		if (!dateStr) return "(No Data)"; // 값이 없는 경우 예외 처리
		let date = new Date(dateStr);
		if (isNaN(date.getTime())) {
			console.warn("Invalid date format:", dateStr);
			return "(Invalid Date)";
		}
		return date.toISOString().split("T")[0];
	},

	toPascalCase: function(str) {
	return str
		.toLowerCase()
		.split(/[\s_\-]+/) // 공백, 밑줄, 하이픈 등 구분자로 나누기
		.map(word => word.charAt(0).toUpperCase() + word.slice(1))
		.join('');
	}
}