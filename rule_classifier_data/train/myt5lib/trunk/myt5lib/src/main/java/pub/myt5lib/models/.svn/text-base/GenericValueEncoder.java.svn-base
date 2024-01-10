// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package pub.myt5lib.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.services.PropertyAccess;
import org.apache.tapestry5.ioc.services.PropertyAdapter;

public class GenericValueEncoder<T> implements ValueEncoder<T> {

	private PropertyAdapter idFieldAdapter = null;
	private List<T> list;

	public GenericValueEncoder(List<T> list, String idField, PropertyAccess access) {
		if(list == null) list = new ArrayList<T>();
		if (idField != null && !idField.equalsIgnoreCase("null")){
			if(list.size() > 0){
				this.idFieldAdapter = access.getAdapter(list.get(0).getClass()).getPropertyAdapter(idField);
			}
		}
		this.list = list;
	}

	public String toClient(T obj) {
		if (idFieldAdapter == null) {
			return nvl(obj);
		} else {
			return nvl(idFieldAdapter.get(obj));
		}
	}

	public T toValue(String string) {
		if (idFieldAdapter == null) {
			for (T obj : list) {
				if (nvl(obj).equals(string)) return obj;
			}
		} else {
			for (T obj : list) {
				if (nvl(idFieldAdapter.get(obj)).equals(string)) return obj;
			}
		}
		return null;
	}

	private String nvl(Object o) {
		if (o == null)
			return "";
		else
			return o.toString();
	}
}