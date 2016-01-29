package ar.com.jrules.core.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import ar.com.jrules.core.model.JRule;
import ar.com.jrules.core.model.JRuleNone;

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
 *         JRules service manager
 * 
 */
@Service("jRulesCoreRuleManager")
public final class JRulesCoreRuleManager {

	private static Logger log = Logger.getLogger(JRulesCoreRuleManager.class);

	private Map<String, List<String>> ruleSetMap = new HashMap<String, List<String>>();

	private Map<String, JRule> rulesMap = new HashMap<String, JRule>();

	public void addRule(String jRuleSetName, JRule rule) {
		log.debug("add rule: " + rule.getRuleName() + "  in a set rule: " + jRuleSetName);

		jRuleSetName = this.buildCorrectClassName(jRuleSetName);
		String ruleName = this.buildCorrectClassName(rule.getRuleName());

		rulesMap.put(ruleName, rule);

		if (!ruleSetMap.containsKey(jRuleSetName)) {
			ruleSetMap.put(jRuleSetName, new ArrayList<String>());
		}

		List<String> rulesList = ruleSetMap.get(jRuleSetName);

		if (!rulesList.contains(ruleName)) {
			rulesList.add(ruleName);
		}
	}

	public void addRule(JRule rule) {
		log.debug("add rule: " + rule.getRuleName());

		String ruleName = this.buildCorrectClassName(rule.getRuleName());

		if (!rulesMap.containsKey(ruleName)) {
			rulesMap.put(ruleName, rule);
		}
	}

	public List<JRule> getRuleList(String jRuleSetName) {
		log.debug("get rule list: " + jRuleSetName);

		jRuleSetName = this.buildCorrectClassName(jRuleSetName);

		if (ruleSetMap.containsKey(jRuleSetName)) {
			return this.buildRuleSet(ruleSetMap.get(jRuleSetName));
		}

		return new ArrayList<JRule>();
	}

	public JRule getRule(Class<? extends JRule> jRuleClass) {
		log.debug("get rule: " + jRuleClass.getSimpleName());

		String ruleName = this.buildCorrectClassName(jRuleClass.getSimpleName());

		if (rulesMap.containsKey(ruleName)) {
			return rulesMap.get(ruleName);
		}

		return new JRuleNone();
	}

	private List<JRule> buildRuleSet(List<String> ruleNameList) {

		List<JRule> ruleSet = new ArrayList<JRule>();

		for (String ruleName : ruleNameList) {
			ruleSet.add(rulesMap.get(this.buildCorrectClassName(ruleName)));
		}

		Collections.sort(ruleSet);

		return ruleSet;
	}

	private String buildCorrectClassName(String className) {
		return Character.toLowerCase(className.charAt(0)) + className.substring(1);
	}

}