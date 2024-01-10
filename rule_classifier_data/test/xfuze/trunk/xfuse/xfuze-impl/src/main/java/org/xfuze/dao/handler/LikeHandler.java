/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.xfuze.dao.handler;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

/**
 * @author Jason Chan
 *
 */
public class LikeHandler implements TypeHandlerCallback {
	public Object getResult(ResultGetter getter) throws SQLException {
		return getter.getObject();
	}

	public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
		String result = ((String) parameter);
		setter.setString("%" + result + "%");
	}

	public Object valueOf(String s) {
		return s;
	}
}
