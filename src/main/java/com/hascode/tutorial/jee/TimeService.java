package com.hascode.tutorial.jee;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class TimeService {
	@Inject
	private LocaleManager localeManager;

	public String getLocalizedTime(final Date date) {
		return localeManager.getSpecialDateFormat().format(date);
	}
}
