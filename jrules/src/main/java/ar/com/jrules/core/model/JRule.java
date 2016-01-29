package ar.com.jrules.core.model;

import ar.com.jrules.core.annotation.Rule;
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
 *         extends this class if you want to create a custom JRule
 */
@Rule
public abstract class JRule implements Comparable<JRule> {

	private String ruleName;

	/**
	 * 
	 * method to return the rule order to execute in a set/group of rules
	 * 
	 * @return int (primitive type)
	 */
	public abstract int getRuleOrder();

	/**
	 * in this method you need to implement your validation for the principal
	 * object: T objetcToEvaluate.
	 * 
	 * @param objetcToEvaluate
	 * @param objects
	 * @return RuleResponse
	 * @throws JRuleException
	 */
	public abstract RuleResponse execute(JRuleEvaluateParameters jRuleEvaluateParameters) throws JRuleException;

	/**
	 * method to return the rule name. You can override this method to change
	 * the rule name
	 * 
	 * @return String
	 */
	public String getRuleName() {
		if (this.ruleName == null) {
			this.ruleName = getClass().getSimpleName();
		}

		return this.ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		if (obj.getClass() != getClass()) {
			return false;
		}

		JRule jRule = (JRule) obj;

		if (this.getRuleName().equals(jRule.getRuleName())) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {

		int hashCode = 3;

		hashCode = 7 * hashCode + Long.valueOf(this.getRuleOrder()).intValue();

		hashCode = 7 * hashCode + this.getRuleName().hashCode();

		return hashCode;
	}

	public int compareTo(JRule rule) {

		if (this.getRuleOrder() > rule.getRuleOrder()) {
			return 1;
		}

		return -1;
	}

}
