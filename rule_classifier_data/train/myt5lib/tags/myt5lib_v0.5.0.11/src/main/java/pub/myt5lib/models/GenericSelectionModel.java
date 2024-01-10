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

import org.apache.tapestry.OptionGroupModel;
import org.apache.tapestry.OptionModel;
import org.apache.tapestry.internal.OptionModelImpl;
import org.apache.tapestry.ioc.services.PropertyAccess;
import org.apache.tapestry.ioc.services.PropertyAdapter;
import org.apache.tapestry.util.AbstractSelectModel;

public class GenericSelectionModel<T> extends AbstractSelectModel {

	private PropertyAdapter labelFieldAdapter = null;

    private List<T> list;

    public GenericSelectionModel(List<T> list, String labelField, PropertyAccess access) {
    	if(labelField != null && !labelField.equalsIgnoreCase("null")){
    		if(list.size() > 0){
    			this.labelFieldAdapter = access.getAdapter(list.get(0).getClass())
    				.getPropertyAdapter(labelField);
    		}
    	}
    		this.list = list;
	}

	public List<OptionGroupModel> getOptionGroups() {
		return null;
	}

	public List<OptionModel> getOptions() {
		List<OptionModel> optionModelList = new ArrayList<OptionModel>();
		for (T obj : list) {
			if (labelFieldAdapter == null) {
				optionModelList.add(new OptionModelImpl(nvl(obj) + "", obj));
			} else {
				optionModelList.add(new OptionModelImpl(
					nvl(labelFieldAdapter.get(obj)), obj));
			}
		}
		return optionModelList;
	}

	private String nvl(Object o) {
		if (o == null)
			return "";
		else
			return o.toString();
	}
}
