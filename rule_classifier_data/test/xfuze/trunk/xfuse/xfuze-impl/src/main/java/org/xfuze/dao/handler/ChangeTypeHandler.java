/*
 * Copyright (c) Ringus Solution Enterprise Limited. All rights reserved.
 */
package org.xfuze.dao.handler;

import java.sql.SQLException;
import java.sql.Types;

import org.xfuze.lang.ChangeType;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

/**
 * @author Jason Chan
 *
 */
public class ChangeTypeHandler implements TypeHandlerCallback {
	public Object getResult(ResultGetter getter) throws SQLException {
		if (getter.wasNull()) {
			return null;
		}

		String value = getter.getString();
		ChangeType changeType = ChangeType.getInstance(value);

		return changeType;
	}

	public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
		if (parameter == null) {
			setter.setNull(Types.CHAR);
		} else {
			ChangeType changeType = (ChangeType) parameter;
			setter.setString(changeType.code());
		}
	}

	public Object valueOf(String s) {
		return s;
	}
}
