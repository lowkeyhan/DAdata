/**
 * hxb
 */

/**
 * 判断年份是否为润年
 *
 * @param {Number} year
 */
function isLeapYear(year) {
    return (year % 400 == 0) || (year % 4 == 0 && year % 100 != 0);
}
/**
 * 获取某一年份的某一月份的天数
 *
 * @param {Number} year
 * @param {Number} month
 */
function getMonthDays(year, month) {
    return [31, null, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31][month] || (isLeapYear(year) ? 29 : 28);
}

/**
 * 获取某年的某天是第几周
 * @param {Number} y
 * @param {Number} m
 * @param {Number} d
 * @returns {Number}
 */
function getWeekNumber(daystr) {
    var now = new Date(daystr),
        year = now.getFullYear(),
        month = now.getMonth(),
        days = now.getDate();
    //那一天是那一年中的第多少天
    for (var i = 0; i < month; i++) {
        days += getMonthDays(year, i);
    }

    //那一年第一天是星期几
    var yearFirstDay = new Date(year, 0, 1).getDay() || 7;

    var week = null;
    if (yearFirstDay == 1) {
        week = Math.ceil(days / yearFirstDay);
    } else {
        days -= (7 - yearFirstDay + 1);
        week = Math.ceil(days / 7) + 1;
    }

    return week;
}

function getWeekFristAndLastData(daystr){
	var newday=new Date(daystr);
	w = newday.getDay();
	n = (w == 0 ? 7 : w) - 1;
	newday.setDate(newday.getDate() - n);
	var data = new Array();
	//本周第一天
	 data[0]=newday.getFullYear() + '/' + (newday.getMonth()+1) + '/' + newday.getDate();
	 newday.setDate(newday.getDate() + 6);
	 //本周最后一天
	 data[1]=newday.getFullYear() + '/' + (newday.getMonth()+1) + '/' + newday.getDate();
	 return data;
	 
}