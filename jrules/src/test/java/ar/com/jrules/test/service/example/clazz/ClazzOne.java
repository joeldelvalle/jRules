package ar.com.jrules.test.service.example.clazz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class ClazzOne {

	@Autowired
	ClazzTwo clazzTwo;

	public String call_clazzTwo_newImplementation(String message) {
		return clazzTwo.callMe_withSetRuleBefore_newImplementation(message);
	}

	public String call_clazzTwo_to_try_rule_one_newImplementation(String message) {
		return clazzTwo.callMe_with_CallMe_Rule_1_Before_newImplementation(message);
	}

	public String call_clazzTwo_to_try_rule_two_newImplementation(String message) {
		return clazzTwo.callMe_with_CallMe_Rule_2_Before_newImplementation(message);
	}

	public String call_clazzTwo_to_try_combination_setRule_and_singleRule_stopWhenFindFirstError(String message) {
		return clazzTwo.callMe_with_setGroup_and_singleRule_stop_when_find_error(message);
	}

	public String call_clazzTwo_to_try_combination_setRule_and_singleRule_acumulatesErrors(String message) {
		return clazzTwo.callMe_with_setGroup_and_singleRule_acumulateErrors(message);
	}

	public String call_clazzTwo_to_try_combination_singleRule_and_setRule_stopWhenFindFirstError(String message) {
		return clazzTwo.callMe_with_singleRule_and_setGroup__stop_when_find_error(message);
	}

}
