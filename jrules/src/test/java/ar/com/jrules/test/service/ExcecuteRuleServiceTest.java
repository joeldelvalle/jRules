package ar.com.jrules.test.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ar.com.jrules.core.exception.JRuleException;
import ar.com.jrules.core.model.RuleResponse;
import ar.com.jrules.core.service.LoadJRules;
import ar.com.jrules.test.service.example.clazz.ClazzOne;

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
@ContextConfiguration(locations = { "classpath:conf/service.bean.xml", "classpath:conf/aspect.bean.xml" }, inheritLocations = true)
public class ExcecuteRuleServiceTest {

	@Autowired
	private ApplicationContext ac;

	@Test
	public void validate_excution_set_rules_ok_newImplementation() {

		// DADO
		LoadJRules loadJRules = new LoadJRules(ac);

		// CUANDO
		loadJRules.loadJRules();

		ClazzOne clazzOne = ac.getBean(ClazzOne.class);
		String result = clazzOne.call_clazzTwo_newImplementation("message-test");

		// ENTONCES
		Assert.assertEquals("El mensaje recibido es incorrecto", "me dijiste esto: message-test", result);
	}

	@Test
	public void validate_excution_set_rules_error_rule_one_newImplementation() {

		// DADO
		LoadJRules loadJRules = new LoadJRules(ac);

		// CUANDO
		loadJRules.loadJRules();

		ClazzOne clazzOne = ac.getBean(ClazzOne.class);

		// ENTONCES
		String errorMessage = "";
		try {
			clazzOne.call_clazzTwo_newImplementation(null);
		} catch (JRuleException e) {
			List<RuleResponse> ruleResponseList = e.getRuleResponseList();

			Assert.assertEquals("La cantidad de errores debe ser 1", 1, ruleResponseList.size());

			RuleResponse ruleResponseError = ruleResponseList.get(0);

			errorMessage = ruleResponseError.getMessage();
		}

		Assert.assertEquals("La exception por error de rule es incorrecta", "error.rule.one", errorMessage);

	}

	@Test
	public void validate_excution_set_rules_error_rule_two_newImplementation() {

		// DADO
		LoadJRules loadJRules = new LoadJRules(ac);
		String invalidMessage = "chau";

		// CUANDO
		loadJRules.loadJRules();

		ClazzOne clazzOne = ac.getBean(ClazzOne.class);

		// ENTONCES
		String errorMessage = "";
		try {
			clazzOne.call_clazzTwo_newImplementation(invalidMessage);
		} catch (JRuleException e) {
			List<RuleResponse> ruleResponseList = e.getRuleResponseList();

			Assert.assertEquals("La cantidad de errores debe ser 1", 1, ruleResponseList.size());

			RuleResponse ruleResponseError = ruleResponseList.get(0);

			errorMessage = ruleResponseError.getMessage();
		}

		Assert.assertEquals("La exception por error de rule es incorrecta", "error.rule.two", errorMessage);

	}

	@Test
	public void validate_excution_rule_one_ok_newImplementation() {

		// DADO
		LoadJRules loadJRules = new LoadJRules(ac);

		// CUANDO
		loadJRules.loadJRules();

		ClazzOne clazzOne = ac.getBean(ClazzOne.class);
		String result = clazzOne.call_clazzTwo_to_try_rule_one_newImplementation("otro mensaje");

		// ENTONCES
		Assert.assertEquals("El mensaje recibido es incorrecto", "mesaje recibido: otro mensaje", result);
	}

	@Test
	public void validate_excution_rule_one_error_newImplementation() {

		// DADO
		LoadJRules loadJRules = new LoadJRules(ac);

		// CUANDO
		loadJRules.loadJRules();

		ClazzOne clazzOne = ac.getBean(ClazzOne.class);

		String errorMessage = "";
		try {
			clazzOne.call_clazzTwo_to_try_rule_one_newImplementation(null);
		} catch (JRuleException e) {
			List<RuleResponse> ruleResponseList = e.getRuleResponseList();

			Assert.assertEquals("La cantidad de errores debe ser 1", 1, ruleResponseList.size());

			RuleResponse ruleResponseError = ruleResponseList.get(0);

			errorMessage = ruleResponseError.getMessage();
		}

		Assert.assertEquals("La exception por error de rule es incorrecta", "error.rule.one", errorMessage);

	}

	@Test
	public void validate_excution_rule_two_ok_newImplementation() {

		// DADO
		LoadJRules loadJRules = new LoadJRules(ac);

		// CUANDO
		loadJRules.loadJRules();

		ClazzOne clazzOne = ac.getBean(ClazzOne.class);
		String result = clazzOne.call_clazzTwo_to_try_rule_two_newImplementation("mensaje para testear");

		// ENTONCES
		Assert.assertEquals("El mensaje recibido es incorrecto", "me llego esto: mensaje para testear", result);
	}

	@Test
	public void validate_excution_rule_two_error_newImplementation() {

		// DADO
		LoadJRules loadJRules = new LoadJRules(ac);
		String invalidMessage = "chau";

		// CUANDO
		loadJRules.loadJRules();

		ClazzOne clazzOne = ac.getBean(ClazzOne.class);

		// ENTONCES
		String errorMessage = "";
		try {
			clazzOne.call_clazzTwo_to_try_rule_two_newImplementation(invalidMessage);
		} catch (JRuleException e) {
			List<RuleResponse> ruleResponseList = e.getRuleResponseList();

			Assert.assertEquals("La cantidad de errores debe ser 1", 1, ruleResponseList.size());

			RuleResponse ruleResponseError = ruleResponseList.get(0);

			errorMessage = ruleResponseError.getMessage();
		}

		Assert.assertEquals("La exception por error de rule es incorrecta", "error.rule.two", errorMessage);

	}

	@Test
	public void validate_excution_setRule_and_singleRule_stopInFirstError() {

		// DADO
		LoadJRules loadJRules = new LoadJRules(ac);

		// CUANDO
		loadJRules.loadJRules();

		ClazzOne clazzOne = ac.getBean(ClazzOne.class);

		// ENTONCES
		try {

			clazzOne.call_clazzTwo_to_try_combination_setRule_and_singleRule_stopWhenFindFirstError(null);

		} catch (JRuleException e) {

			List<RuleResponse> ruleResponseList = e.getRuleResponseList();

			Assert.assertEquals("La cantidad de errores debe ser 1", 1, ruleResponseList.size());

			RuleResponse ruleResponseError = ruleResponseList.get(0);

			Assert.assertEquals("La exception por error de rule es incorrecta", "error.rule.one", ruleResponseError.getMessage());
		}

		try {

			clazzOne.call_clazzTwo_to_try_combination_setRule_and_singleRule_stopWhenFindFirstError("chau");

		} catch (JRuleException e) {

			List<RuleResponse> ruleResponseList = e.getRuleResponseList();

			Assert.assertEquals("La cantidad de errores debe ser 1", 1, ruleResponseList.size());

			RuleResponse ruleResponseError = ruleResponseList.get(0);

			Assert.assertEquals("La exception por error de rule es incorrecta", "error.rule.two", ruleResponseError.getMessage());
		}

		try {

			clazzOne.call_clazzTwo_to_try_combination_setRule_and_singleRule_stopWhenFindFirstError("vamo");

		} catch (JRuleException e) {

			List<RuleResponse> ruleResponseList = e.getRuleResponseList();

			Assert.assertEquals("La cantidad de errores debe ser 1", 1, ruleResponseList.size());

			RuleResponse ruleResponseError = ruleResponseList.get(0);

			Assert.assertEquals("La exception por error de rule es incorrecta", "error.rule.four", ruleResponseError.getMessage());
		}

		try {

			clazzOne.call_clazzTwo_to_try_combination_setRule_and_singleRule_stopWhenFindFirstError("   ");

		} catch (JRuleException e) {

			List<RuleResponse> ruleResponseList = e.getRuleResponseList();

			Assert.assertEquals("La cantidad de errores debe ser 1", 1, ruleResponseList.size());

			RuleResponse ruleResponseError = ruleResponseList.get(0);

			Assert.assertEquals("La exception por error de rule es incorrecta", "error.rule.three", ruleResponseError.getMessage());
		}

	}

	@Test
	public void validate_excution_setRule_and_singleRule_acumulateErrors() {

		// DADO
		LoadJRules loadJRules = new LoadJRules(ac);

		// CUANDO
		loadJRules.loadJRules();

		ClazzOne clazzOne = ac.getBean(ClazzOne.class);

		// ENTONCES
		try {

			clazzOne.call_clazzTwo_to_try_combination_setRule_and_singleRule_acumulatesErrors("chau");

		} catch (JRuleException e) {

			List<RuleResponse> ruleResponseList = e.getRuleResponseList();

			Assert.assertEquals("La cantidad de errores debe ser 2", 2, ruleResponseList.size());

			RuleResponse ruleResponseError_1 = ruleResponseList.get(0);
			Assert.assertEquals("La exception por error de rule es incorrecta", "error.rule.two", ruleResponseError_1.getMessage());

			RuleResponse ruleResponseError_2 = ruleResponseList.get(1);
			Assert.assertEquals("La exception por error de rule es incorrecta", "error.rule.four", ruleResponseError_2.getMessage());

		}

		try {

			clazzOne.call_clazzTwo_to_try_combination_setRule_and_singleRule_acumulatesErrors("   ");

		} catch (JRuleException e) {

			List<RuleResponse> ruleResponseList = e.getRuleResponseList();

			Assert.assertEquals("La cantidad de errores debe ser 1", 1, ruleResponseList.size());

			RuleResponse ruleResponseError = ruleResponseList.get(0);

			Assert.assertEquals("La exception por error de rule es incorrecta", "error.rule.three", ruleResponseError.getMessage());
		}

	}

	@Test
	public void validate_excution_singleRule_and_setRule_stopInFirstError() {

		// DADO
		LoadJRules loadJRules = new LoadJRules(ac);

		// CUANDO
		loadJRules.loadJRules();

		ClazzOne clazzOne = ac.getBean(ClazzOne.class);

		// ENTONCES
		try {

			clazzOne.call_clazzTwo_to_try_combination_setRule_and_singleRule_stopWhenFindFirstError(null);

		} catch (JRuleException e) {

			List<RuleResponse> ruleResponseList = e.getRuleResponseList();

			Assert.assertEquals("La cantidad de errores debe ser 1", 1, ruleResponseList.size());

			RuleResponse ruleResponseError = ruleResponseList.get(0);

			Assert.assertEquals("La exception por error de rule es incorrecta", "error.rule.one", ruleResponseError.getMessage());
		}

		try {

			clazzOne.call_clazzTwo_to_try_combination_setRule_and_singleRule_stopWhenFindFirstError("chau");

		} catch (JRuleException e) {

			List<RuleResponse> ruleResponseList = e.getRuleResponseList();

			Assert.assertEquals("La cantidad de errores debe ser 1", 1, ruleResponseList.size());

			RuleResponse ruleResponseError = ruleResponseList.get(0);

			Assert.assertEquals("La exception por error de rule es incorrecta", "error.rule.two", ruleResponseError.getMessage());
		}

		try {

			clazzOne.call_clazzTwo_to_try_combination_setRule_and_singleRule_stopWhenFindFirstError("   ");

		} catch (JRuleException e) {

			List<RuleResponse> ruleResponseList = e.getRuleResponseList();

			Assert.assertEquals("La cantidad de errores debe ser 1", 1, ruleResponseList.size());

			RuleResponse ruleResponseError = ruleResponseList.get(0);

			Assert.assertEquals("La exception por error de rule es incorrecta", "error.rule.three", ruleResponseError.getMessage());
		}

	}
}
