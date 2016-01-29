package ar.com.jrules.test.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ar.com.jrules.core.model.RuleResponse;
import ar.com.jrules.core.model.JRule;
import ar.com.jrules.core.service.LoadJRules;
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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:conf/service.bean.xml" }, inheritLocations = true)
public class LoadJRulesTest {

	@Autowired
	private ApplicationContext ac;

	@Test
	public void test_load_rules() {

		// DADO
		String groupName = "groupTest";
		LoadJRules loadJRules = new LoadJRules(ac);

		// CUANDO
		loadJRules.loadJRules();

		// ENTONCES
		JRulesCoreRuleManager jRulesCoreRuleManager = ac.getBean(JRulesCoreRuleManager.class);

		List<JRule> rules = jRulesCoreRuleManager.getRuleList(groupName);

		Assert.assertEquals("la cantidad de rules debe ser 2", rules.size(), 2);

		for (JRule jRule : rules) {
			RuleResponse result = jRule.execute(null);

			if (jRule.getClass().getSimpleName().toLowerCase().equals(RuleOneOk.class.getSimpleName().toLowerCase())) {
				Assert.assertEquals("El mensaje debe ser rule.one.ok", "rule.one.ok", result.getMessage());
			}

			if (jRule.getClass().getSimpleName().toLowerCase().equals(RuleTwoOk.class.getSimpleName().toLowerCase())) {
				Assert.assertEquals("El mensaje debe ser rule.two.ok", "rule.two.ok", result.getMessage());
			}
		}

	}

}