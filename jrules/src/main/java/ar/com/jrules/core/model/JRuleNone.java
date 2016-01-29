package ar.com.jrules.core.model;

import ar.com.jrules.core.exception.JRuleException;

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
 *         rule class example
 */
public final class JRuleNone extends JRule {

	@Override
	public int compareTo(JRule o) {
		return 0;
	}

	@Override
	public int getRuleOrder() {
		return -1;
	}

	@Override
	public RuleResponse execute(JRuleEvaluateParameters jRuleEvaluateParameters) throws JRuleException {
		throw new JRuleException("JRuleNone isn't realy rule");
	}

}
