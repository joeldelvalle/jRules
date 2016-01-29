package ar.com.jrules.test.service.example.clazz;

import org.springframework.stereotype.Component;

import ar.com.jrules.core.annotation.ExecuteRule;
import ar.com.jrules.test.service.example.rules.CallMe_Rule_1;
import ar.com.jrules.test.service.example.rules.CallMe_Rule_2;
import ar.com.jrules.test.service.example.rules.CallMe_Rule_3;
import ar.com.jrules.test.service.example.rules.CallMe_Rule_4;

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
@Component
public class ClazzTwo {

	@ExecuteRule(ruleSetName = { "callMeSetRule" }, ruleClass = {})
	public String callMe_withSetRuleBefore_newImplementation(String message) {
		return "me dijiste esto: " + message;
	}

	@ExecuteRule(ruleClass = CallMe_Rule_1.class, ruleSetName = {})
	public String callMe_with_CallMe_Rule_1_Before_newImplementation(String message) {
		return "mesaje recibido: " + message;
	}

	@ExecuteRule(ruleClass = CallMe_Rule_2.class, ruleSetName = {})
	public String callMe_with_CallMe_Rule_2_Before_newImplementation(String message) {
		return "me llego esto: " + message;
	}

	@ExecuteRule(ruleSetName = { "callMeSetRule" }, ruleClass = { CallMe_Rule_3.class, CallMe_Rule_4.class }, executeSetOfRulesFirst = true, onErrorStopProcess = true)
	public String callMe_with_setGroup_and_singleRule_stop_when_find_error(String message) {
		return "mensaje sin errores: " + message;
	}

	@ExecuteRule(ruleSetName = { "callMeSetRule" }, ruleClass = { CallMe_Rule_3.class, CallMe_Rule_4.class }, executeSetOfRulesFirst = true, onErrorStopProcess = false)
	public String callMe_with_setGroup_and_singleRule_acumulateErrors(String message) {
		return "mensaje sin errores: " + message;
	}

	@ExecuteRule(ruleSetName = { "callMeSetRule" }, ruleClass = { CallMe_Rule_3.class, CallMe_Rule_4.class }, executeSetOfRulesFirst = false, onErrorStopProcess = true)
	public String callMe_with_singleRule_and_setGroup__stop_when_find_error(String message) {
		return "mensaje sin errores: " + message;
	}

}
