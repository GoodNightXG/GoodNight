package com.example.goodnightnote.view;
/**
 *Time:2019/04/18
 *Author: xiaoxi
 *Description:
 */
import com.example.goodnightnote.R;

import java.util.Calendar;

public class Date {
	public String getDate() {
		Calendar localCalendar = Calendar.getInstance();
		int instanceYear = localCalendar.get(1);
		int instanceMonth = localCalendar.get(2) + 1;
		int instanceDate = localCalendar.get(5);
		int l = localCalendar.get(11);
		int instanceMinute = localCalendar.get(12);
		int instanceHour = localCalendar.get(10);
		String am = "AM";
		String pm = "PM";

		if ( l >= 13 ) {
			if (instanceHour == 0) {
				instanceHour = 12;
			}
			if (instanceMinute < 10) {
				return ""+instanceYear + "-" + instanceMonth + "-" + instanceDate +
						"    " + instanceHour + ":" + "0" + instanceMinute + pm;
			}
			return ""+instanceYear + "-" + instanceMonth + "-" + instanceDate +
					"    " + instanceHour + ":" + instanceMinute + pm;
		}
		if ( instanceHour == 0 ) {
			instanceHour = 12;
		}
		if ( instanceMinute < 10 ) {
			return instanceYear + "-" + instanceMonth + "-" + instanceDate +
					"    " + instanceHour + ":" + "0" + instanceMinute + am;
		}
		return ""+instanceYear + "-" + instanceMonth + "-" + instanceDate +
				"    " + instanceHour + ":" + instanceMinute + am;
	}
}