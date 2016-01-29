package ar.com.jrules.core.model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 *         class that includes all the parameters of the signature of the method
 *         to which you run rules
 * 
 */
public final class JRuleEvaluateParameters {

	private Map<String, Object> methodParametersMap;

	private List<Object> methodParametersListOrder;

	public JRuleEvaluateParameters(Method method, Object[] methodArguments) {
		methodParametersMap = new HashMap<String, Object>();
		methodParametersListOrder = new ArrayList<Object>();

		int argumentPosition = 0;
		for (Object arg : methodArguments) {
			if (arg != null) {
				methodParametersMap.put(arg.getClass().getName().toLowerCase(), arg);
				methodParametersListOrder.add(argumentPosition, arg);
			} else {
				Class<?> parameterType = method.getParameterTypes()[argumentPosition];
				methodParametersMap.put(parameterType.getCanonicalName().toLowerCase(), arg);
				methodParametersListOrder.add(argumentPosition, arg);
			}
			argumentPosition++;
		}
	}

	public <T> T getParameter(Class<T> clazz) {
		Object parameter = methodParametersMap.get(clazz.getName().toLowerCase());
		return clazz.cast(parameter);
	}

	public Object getParameter(Integer argumentPosition) {
		return methodParametersListOrder.get(argumentPosition);
	}

	public <T> T getParameter(Integer argumentPosition, Class<T> clazz) {
		return clazz.cast(methodParametersListOrder.get(argumentPosition));
	}

	public List<Object> getParametersInOrder() {
		return this.methodParametersListOrder;
	}

}