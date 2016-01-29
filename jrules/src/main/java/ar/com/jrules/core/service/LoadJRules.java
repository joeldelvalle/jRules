package ar.com.jrules.core.service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import ar.com.jrules.core.annotation.ExecuteRule;
import ar.com.jrules.core.annotation.Rule;
import ar.com.jrules.core.annotation.JRuleSet;
import ar.com.jrules.core.exception.JRuleConfigurationException;
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
 *         service to load and build rules or set of rules
 * 
 */
@SuppressWarnings("rawtypes")
public final class LoadJRules {

	private static Logger log = Logger.getLogger(LoadJRules.class);

	private static final String EMPTY_STRING = "";

	private static final String JRULES_CORE_RULE_PACKAGE = "/ar/com/jrules/core";

	private JRulesCoreRuleManager jRuleCore;

	private String basePackage;

	private ApplicationContext springContext;

	private List<JRule> jRuleList;

	private List<Class<?>> clazzWithMethodThatJRulesImplement;

	public LoadJRules(ApplicationContext springContext) {
		this.basePackage = EMPTY_STRING;
		this.springContext = springContext;
		this.jRuleList = new ArrayList<JRule>();
		this.clazzWithMethodThatJRulesImplement = new ArrayList<Class<?>>();
	}

	public LoadJRules(String basePackage, WebApplicationContext springContext) {

		if (basePackage.endsWith(".")) {
			basePackage = basePackage.substring(0, basePackage.length() - 1);
		}

		if (basePackage.endsWith(".*")) {
			basePackage = basePackage.substring(0, basePackage.length() - 2);
		}

		this.basePackage = basePackage;
		this.springContext = springContext;
		this.jRuleList = new ArrayList<JRule>();
		this.clazzWithMethodThatJRulesImplement = new ArrayList<Class<?>>();
	}

	public void loadJRules() {

		log.debug("Load and Build JRule");

		this.loadJRule();

		log.debug("Total found " + jRuleList.size() + " JRule");

		this.buildRulesMap();

		log.debug("Validate JRule used");

		this.validateExcuteRuleAnnotationParameters();
	}

	private void validateExcuteRuleAnnotationParameters() {
		log.debug("Found " + this.clazzWithMethodThatJRulesImplement.size() + " class with JRule implementation");

		for (Class<?> clazz : this.clazzWithMethodThatJRulesImplement) {

			for (Method method : clazz.getMethods()) {

				ExecuteRule executeRuleAnnotation = method.getAnnotation(ExecuteRule.class);

				if (executeRuleAnnotation != null) {

					if (executeRuleAnnotation.ruleClass().length == 0 && executeRuleAnnotation.ruleSetName().length == 0) {
						throw new JRuleConfigurationException("Found error in ExecuteRule configuration. The method '"
								+ method.getName() + "' in class " + clazz.getCanonicalName()
								+ " has not properly declared annotation @ExecuteRule. Remember to declare a 'ruleClass' or 'ruleSetName'");
					}
				}
			}
		}
	}

	private void buildRulesMap() {

		for (JRule jRule : jRuleList) {

			JRuleSet jRuleSet = jRule.getClass().getAnnotation(JRuleSet.class);

			if (jRuleSet != null) {

				for (String setName : jRuleSet.jRuleSetNames()) {

					if (setName != null && setName.trim() != EMPTY_STRING) {
						getJRulesCoreRuleManager().addRule(setName, jRule);
					}

				}

			} else {

				getJRulesCoreRuleManager().addRule(jRule);

			}
		}
	}

	@SuppressWarnings("unchecked")
	private void loadJRule() {

		Class[] classes = this.getClassesInPackage(this.basePackage, Rule.class);
		for (Class c : classes) {

			JRule newInstance;

			try {
				newInstance = (JRule) springContext.getBean(c);
			} catch (NoSuchBeanDefinitionException noBean) {
				try {
					newInstance = (JRule) c.newInstance();
				} catch (Exception e) {
					throw new JRuleConfigurationException("Error to create JRule", e);
				}

			}

			log.debug("   " + newInstance.getClass().getCanonicalName());
			jRuleList.add(newInstance);
		}
	}

	private Class[] getClassesInPackage(String basePackage, Class<?> classType) {

		File directory = this.getPackageDirectory(basePackage);

		if (!directory.exists()) {
			throw new JRuleConfigurationException("Could not get directory resource for package " + basePackage + ".");
		}

		return this.getClassesInPackage(basePackage, directory, classType);
	}

	private File getPackageDirectory(String basePackage) {

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		if (classLoader == null) {
			throw new JRuleConfigurationException("Can't get class loader.");
		}

		URL resource = classLoader.getResource(basePackage.replace('.', '/'));
		if (resource == null) {
			throw new JRuleConfigurationException("Package " + basePackage + " not found on classpath.");
		}

		return new File(resource.getFile());
	}

	@SuppressWarnings("unchecked")
	private Class[] getClassesInPackage(String basePackage, File directory, Class classType) {

		List<String> className = new ArrayList<String>();
		this.findAllClassInPackage(directory.getAbsolutePath(), directory, className);

		List<Class> classes = new ArrayList<Class>();

		for (String filename : className) {

			if (filename.endsWith(".class")) {
				String classname = this.buildClassname(basePackage, filename);
				try {

					Class<?> clazz = Class.forName(classname);

					if (clazz.getSuperclass() != null
							&& clazz.getSuperclass().getCanonicalName().equals(JRule.class.getCanonicalName())) {

						if (clazz.getSuperclass().getAnnotation(classType) != null) {
							classes.add(clazz);
						}
					}

					if (this.haveAnyMethodWithJRuleImplementation(clazz)) {
						this.clazzWithMethodThatJRulesImplement.add(clazz);
					}

				} catch (ClassNotFoundException e) {
					log.error("Error creating class " + classname);
				}
			}

		}

		return classes.toArray(new Class[classes.size()]);
	}

	private boolean haveAnyMethodWithJRuleImplementation(Class<?> clazz) {

		for (Method method : clazz.getMethods()) {
			if (method.getAnnotation(ExecuteRule.class) != null) {
				return true;
			}
		}

		return false;
	}

	private void findAllClassInPackage(String baseDirectory, File file, List<String> all) {

		try {

			File[] children = file.listFiles();

			if (children != null) {
				for (File child : children) {
					if (!child.isDirectory() && !this.invalidDirecory(baseDirectory, file.getCanonicalPath())) {
						all.add(child.getAbsolutePath().substring(baseDirectory.length()));
					}
					findAllClassInPackage(baseDirectory, child, all);
				}
			}

		} catch (IOException ioe) {
			throw new JRuleConfigurationException("Error to find JRule");
		}
	}

	private String buildClassname(String pckgname, String filename) {
		String classPath = pckgname + filename.replace(".class", "").replace("/", ".");

		if (classPath.startsWith(".")) {
			classPath = classPath.substring(1);
		}

		return classPath;
	}

	private boolean invalidDirecory(String baseDirectory, String filePath) {

		filePath = filePath.substring(baseDirectory.length());

		return filePath.startsWith(JRULES_CORE_RULE_PACKAGE);
	}

	private JRulesCoreRuleManager getJRulesCoreRuleManager() {
		if (jRuleCore == null) {
			jRuleCore = springContext.getBean("jRulesCoreRuleManager", JRulesCoreRuleManager.class);
		}
		return jRuleCore;
	}

}
