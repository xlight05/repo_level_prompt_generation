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

package pub.myt5lib;

import org.apache.tapestry.ioc.Configuration;
import org.apache.tapestry.ioc.OrderedConfiguration;
import org.apache.tapestry.ioc.services.PropertyAccess;
//import org.apache.tapestry.ioc.OrderedConfiguration;
//import org.apache.tapestry.ioc.services.PropertyAccess;
//import org.apache.tapestry.services.ComponentClassTransformWorker;
import org.apache.tapestry.services.ComponentClassTransformWorker;
import org.apache.tapestry.services.LibraryMapping;

import pub.myt5lib.services.InjectSelectionModelWorker;

//import br.org.fiec.sat.services.InjectSelectionModelWorker;

public class LibraryModule {

	public static void contributeComponentClassResolver(
			Configuration<LibraryMapping> configuration)
	{
		configuration.add(new LibraryMapping("myt5lib", "pub.myt5lib"));
	}
	
    // 20080302
    /**
     * Adds a number of standard component class transform workers:
     * <ul>
     * <li>InjectSelectionModel - generates the SelectionModel and ValueEncoder for a any marked list of objects.</li>
     * </ul>
     */
    public static void contributeComponentClassTransformWorker(
    	OrderedConfiguration<ComponentClassTransformWorker> configuration
    	, PropertyAccess propertyAccess)
    {
        configuration.add("InjectSelectionModel", 
        	new InjectSelectionModelWorker(propertyAccess), "after:Inject*");
    }
}