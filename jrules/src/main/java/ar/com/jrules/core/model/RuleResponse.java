package ar.com.jrules.core.model;

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
 *         class to map a custom JRule response
 * 
 */
public class RuleResponse {

	public RuleResponse(RuleState ruleState) {
		this.ruleState = ruleState;
	}

	public RuleResponse(RuleState ruleState, Exception exception) {
		this.ruleState = ruleState;
		this.exception = exception;
	}

	private RuleState ruleState;

	private String message;

	private Exception exception;

	public RuleState getRuleState() {
		return ruleState;
	}

	public void setRuleState(RuleState ruleState) {
		this.ruleState = ruleState;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
