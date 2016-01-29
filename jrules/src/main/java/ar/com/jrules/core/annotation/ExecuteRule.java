package ar.com.jrules.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ar.com.jrules.core.model.JRule;

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
 *         annotation to identify a method that is going to run before his
 *         execution rules
 */
@Target(value = { ElementType.METHOD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ExecuteRule {

	/**
	 * list of JRule<?> to execute
	 * 
	 * @return Class<JRule<?>>[]
	 */
	Class<? extends JRule>[] ruleClass() default {};

	/**
	 * list of String with names of set/group rules
	 * 
	 * @return String[]
	 */
	String[] ruleSetName() default {};

	/**
	 * determines rules processing stops on encountering an error
	 * 
	 * @return boolean
	 */
	boolean onErrorStopProcess() default true;

	/**
	 * determines the order of excecution, by default set of rules excecute
	 * first then excecute rules class
	 * 
	 * @return boolean
	 */
	boolean executeSetOfRulesFirst() default true;

}
