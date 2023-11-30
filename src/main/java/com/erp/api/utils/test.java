package com.erp.api.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.erp.api.exceptions.ValidationException;

public class test {

	public static void main(String[] args) throws ParseException {
		String testDate="2023-10-28";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dateOfPurchase = java.sql.Date.valueOf(testDate);
		
		
		LocalDate minusDate = LocalDate.now().minusDays(30);
		Date mintoday = java.sql.Date.valueOf(minusDate);
		System.out.println("------mintoday------------"+mintoday);
		
		LocalDate curDate = LocalDate.now().plusDays(30);
		Date plustoday = java.sql.Date.valueOf(curDate);
		System.out.println("------plustoday------------"+plustoday);
		
		if ( dateOfPurchase.after(mintoday)  && dateOfPurchase.before(plustoday)) {
			System.out.println("----eeee-------------"+testDate);
			//continue;
		}else {
		System.out.println("----cccccccccccccccccccc-------------"+testDate);
		}
		
	}

}
