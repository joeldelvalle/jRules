package ar.com.jrules.core.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.com.jrules.core.annotation.ExecuteRule;
import ar.com.jrules.core.exception.JRuleException;
import ar.com.jrules.core.model.RuleResponse;
import ar.com.jrules.core.model.RuleState;
import ar.com.jrules.core.model.JRule;
import ar.com.jrules.core.model.JRuleEvaluateParameters;

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
 *         JRules execution service manager
 * 
 */
@Component
@Aspect
public final class ExcecuteRuleService {

	private static Logger log = Logger.getLogger(ExcecuteRuleService.class);

	@Autowired
	private JRulesCoreRuleManager jRulesCoreRuleManager;

	@Before(value = "( @within(ar.com.jrules.core.annotation.ExecuteRule) || "
			+ "@annotation(ar.com.jrules.core.annotation.ExecuteRule) ) && @annotation(executeRule)")
	public void excecuteRule(JoinPoint joinPoint, ExecuteRule executeRule) throws JRuleException {
		log.debug("start executeRule");

		if (executeRule.executeSetOfRulesFirst()) {
			log.debug("executing set of rules");

			List<RuleResponse> ruleResponseList = this.executeSetOfRules(joinPoint, executeRule);
			ruleResponseList.addAll(this.executeRules(joinPoint, executeRule));

			if (this.haveRuleError(ruleResponseList)) {
				throw new JRuleException("error to process rules", ruleResponseList);
			}

		} else {
			log.debug("executing rules");

			List<RuleResponse> ruleResponseList = this.executeRules(joinPoint, executeRule);
			ruleResponseList.addAll(this.executeSetOfRules(joinPoint, executeRule));

			if (this.haveRuleError(ruleResponseList)) {
				throw new JRuleException("error to process rules", ruleResponseList);
			}

		}

	}

	private boolean haveRuleError(List<RuleResponse> ruleResponseList) {
		for (RuleResponse ruleResponse : ruleResponseList) {
			if (RuleState.RULE_ERROR.equals(ruleResponse.getRuleState())) {
				return true;
			}
		}
		return false;
	}

	private List<RuleResponse> executeSetOfRules(JoinPoint joinPoint, ExecuteRule excecuteRule) {
		String[] ruleSetList = excecuteRule.ruleSetName();

		List<RuleResponse> ruleResponseList = new ArrayList<RuleResponse>();

		if (ruleSetList != null && ruleSetList.length > 0) {

			for (String ruleSet : ruleSetList) {
				log.debug("excecute rule set: " + ruleSet);

				List<JRule> rulesToExecuteList = this.getJRulesCoreRuleManager().getRuleList(ruleSet);

				if (rulesToExecuteList != null && rulesToExecuteList.size() > 0) {

					for (JRule jRule : rulesToExecuteList) {

						MethodSignature signature = (MethodSignature) joinPoint.getSignature();
						Method method = signature.getMethod();

						log.debug("execute rule: " + jRule.getRuleName() + " from rule set: " + ruleSet);
						RuleResponse result = jRule.execute(new JRuleEvaluateParameters(method, joinPoint.getArgs()));

						log.debug("execute rule: " + jRule.getRuleName() + " from rule set: " + ruleSet + " -- result: "
								+ result.getRuleState());
						if (RuleState.RULE_ERROR.equals(result.getRuleState())) {
							ruleResponseList.add(result);

							if (excecuteRule.onErrorStopProcess()) {
								throw new JRuleException("error to process set jrule: " + ruleSet, ruleResponseList);
							}

						}

					}

				}

			}

		}

		return ruleResponseList;
	}

	private List<RuleResponse> executeRules(JoinPoint joinPoint, ExecuteRule excecuteRule) {

		List<RuleResponse> ruleResponseList = new ArrayList<RuleResponse>();

		Class<? extends JRule>[] ruleList = excecuteRule.ruleClass();
		for (Class<? extends JRule> clazz : ruleList) {
			JRule jRule = this.getJRulesCoreRuleManager().getRule(clazz);

			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			Method method = signature.getMethod();

			log.debug("execute rule: " + jRule.getRuleName());
			RuleResponse result = jRule.execute(new JRuleEvaluateParameters(method, joinPoint.getArgs()));

			log.debug("execute rule: " + jRule.getRuleName() + " -- result: " + result.getRuleState());
			if (RuleState.RULE_ERROR.equals(result.getRuleState())) {
				ruleResponseList.add(result);

				if (excecuteRule.onErrorStopProcess()) {
					throw new JRuleException("error to process jrule: " + jRule.getClass().getSimpleName(),
							ruleResponseList);
				}

			}
		}

		return ruleResponseList;
	}

	private JRulesCoreRuleManager getJRulesCoreRuleManager() {
		return jRulesCoreRuleManager;
	}
}