package ar.com.jrules.core.exception;

/**
 * Copyright 2014 Joel del Valle <joelmarcosdelvalle@gmail.com>
 * 
 * This file is part of jrules, project of jrules.
 * 
 * jrules is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * jrules is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * jrules. If not, see <http://www.gnu.org/licenses/>.
 * 
 * 
 * @author joel.delvalle
 * 
 *         exception throws when any error is detected when JRules is
 *         configuring on startup
 * 
 */
public final class JRuleConfigurationException extends RuntimeException {

	private static final long serialVersionUID = -945327505402781004L;

	public JRuleConfigurationException(String message) {
		super(message);
	}

	public JRuleConfigurationException(Throwable exception) {
		super(exception);
	}

	public JRuleConfigurationException(String message, Throwable exception) {
		super(message, exception);
	}

}
