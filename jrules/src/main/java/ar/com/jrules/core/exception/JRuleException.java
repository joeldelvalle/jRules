package ar.com.jrules.core.exception;

import java.util.ArrayList;
import java.util.List;

import ar.com.jrules.core.model.RuleResponse;

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
 *         exception throws when any error is returned in custom JRule
 * 
 */
public final class JRuleException extends RuntimeException {

	private static final long serialVersionUID = 4565738601984697053L;

	private List<RuleResponse> ruleResponseList = new ArrayList<RuleResponse>();

	public JRuleException(String message) {
		super(message);
	}

	public JRuleException(Throwable exception) {
		super(exception);
	}

	public JRuleException(String message, Throwable exception) {
		super(message, exception);
	}

	public JRuleException(String message, List<RuleResponse> ruleResponseList) {
		super(message);
		this.ruleResponseList = ruleResponseList;
	}

	public JRuleException(Throwable exception, List<RuleResponse> ruleResponseList) {
		super(exception);
		this.ruleResponseList = ruleResponseList;
	}

	public JRuleException(String message, Throwable exception, List<RuleResponse> ruleResponseList) {
		super(message, exception);
		this.ruleResponseList = ruleResponseList;
	}

	public List<RuleResponse> getRuleResponseList() {
		return this.ruleResponseList;
	}

}