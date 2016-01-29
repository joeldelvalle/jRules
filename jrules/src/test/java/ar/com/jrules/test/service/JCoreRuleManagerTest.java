package ar.com.jrules.test.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import ar.com.jrules.core.model.RuleResponse;
import ar.com.jrules.core.model.JRule;
import ar.com.jrules.core.service.JRulesCoreRuleManager;
import ar.com.jrules.test.service.example.rules.RuleOneOk;
import ar.com.jrules.test.service.example.rules.RuleTwoOk;

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
 */
public class JCoreRuleManagerTest {

	@Test
	public void add_rules_in_a_set_rule() {

		// DADO
		JRulesCoreRuleManager jrulesCoreRuleManager = new JRulesCoreRuleManager();
		RuleOneOk ruleOne = new RuleOneOk();
		RuleTwoOk ruleTwo = new RuleTwoOk();

		String groupName = "groupTest";

		// CUANDO
		jrulesCoreRuleManager.addRule(groupName, ruleOne);
		jrulesCoreRuleManager.addRule(groupName, ruleTwo);

		// ENTONCES
		List<JRule> rules = jrulesCoreRuleManager.getRuleList(groupName);

		Assert.assertEquals("la cantidad de rules debe ser 2", rules.size(), 2);

		for (JRule jruleRule : rules) {
			RuleResponse result = jruleRule.execute(null);

			if (jruleRule.getClass().getSimpleName().toLowerCase().equals(ruleOne.getClass().getSimpleName().toLowerCase())) {
				Assert.assertEquals("El mensaje debe ser rule.one.ok", "rule.one.ok", result.getMessage());
			}

			if (jruleRule.getClass().getSimpleName().toLowerCase().equals(ruleTwo.getClass().getSimpleName().toLowerCase())) {
				Assert.assertEquals("El mensaje debe ser rule.two.ok", "rule.two.ok", result.getMessage());
			}
		}
	}

	@Test
	public void add_rule() {

		// DADO
		JRulesCoreRuleManager jRulesCoreRuleManager = new JRulesCoreRuleManager();
		RuleOneOk ruleOne = new RuleOneOk();

		// CUANDO
		jRulesCoreRuleManager.addRule(ruleOne);

		// ENTONCES
		JRule rule = jRulesCoreRuleManager.getRule(RuleOneOk.class);

		Assert.assertNotNull("la rule no puede ser null", rule);

		RuleResponse result = rule.execute(null);
		Assert.assertEquals("El mensaje debe ser rule.one.ok", "rule.one.ok", result.getMessage());
	}

}
