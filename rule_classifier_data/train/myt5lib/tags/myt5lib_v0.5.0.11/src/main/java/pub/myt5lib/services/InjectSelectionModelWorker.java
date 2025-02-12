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

package pub.myt5lib.services;

import java.lang.reflect.Modifier;

import org.apache.tapestry.ioc.internal.util.InternalUtils;
import org.apache.tapestry.ioc.services.PropertyAccess;
import org.apache.tapestry.ioc.util.BodyBuilder;
import org.apache.tapestry.model.MutableComponentModel;
import org.apache.tapestry.services.ClassTransformation;
import org.apache.tapestry.services.ComponentClassTransformWorker;
import org.apache.tapestry.services.TransformMethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pub.myt5lib.annotations.InjectSelectionModel;
import pub.myt5lib.models.GenericSelectionModel;
import pub.myt5lib.models.GenericValueEncoder;

public class InjectSelectionModelWorker implements ComponentClassTransformWorker {

    final private Logger _logger = LoggerFactory.getLogger(InjectSelectionModelWorker.class);
    final private PropertyAccess _access;
    
	public InjectSelectionModelWorker(PropertyAccess propertyAccess) {
		_access = propertyAccess;
	}

	public void transform(ClassTransformation transformation, MutableComponentModel componentModel) {

		for (String fieldName : transformation.findFieldsWithAnnotation(InjectSelectionModel.class)) {
			InjectSelectionModel annotation = transformation.getFieldAnnotation(fieldName, InjectSelectionModel.class);
                        
			if (_logger.isDebugEnabled()){
				_logger.debug("Creating selection model getter method for the field " + fieldName);
			}
			String accessActualName = transformation.addField(Modifier.PRIVATE
				, "org.apache.tapestry.ioc.services.PropertyAccess", "_access");
			transformation.injectField(accessActualName, _access);
                        
			addGetSelectionModelMethod(transformation, fieldName, annotation.labelField()
				, accessActualName);

				if (_logger.isDebugEnabled()){
					_logger.debug("Creating value encoder getter method for the field " + fieldName);
				}

				addGetValueEncoderMethod(transformation, fieldName, annotation.idField(), accessActualName);
                        
		}

	}

	private void addGetSelectionModelMethod(ClassTransformation transformation, String fieldName, String labelField, String accessName) {
                
		String methodName = "get" + InternalUtils.capitalize(InternalUtils
			.stripMemberPrefix(fieldName)) + "SelectionModel";
                                
		String modelQualifiedName = (GenericSelectionModel.class).getName();
		TransformMethodSignature sig = 
			new TransformMethodSignature(Modifier.PUBLIC, modelQualifiedName
			, methodName, null, null);

		BodyBuilder builder = new BodyBuilder();
		builder.begin();
		builder.addln("return new " + modelQualifiedName + "(" + fieldName 
			+ ", \"" + labelField +"\", " + accessName + ");");
		builder.end();

		transformation.addMethod(sig, builder.toString());

	}
        
	private void addGetValueEncoderMethod(ClassTransformation transformation, String fieldName, String idField, String accessName) {
                
		String methodName = "get" + InternalUtils.capitalize(InternalUtils
			.stripMemberPrefix(fieldName)) + "ValueEncoder";
                
		String encoderQualifiedName = (GenericValueEncoder.class).getName();
		TransformMethodSignature sig = 
			new TransformMethodSignature(Modifier.PUBLIC, encoderQualifiedName
			, methodName, null, null);

		BodyBuilder builder = new BodyBuilder();
		builder.begin();
		String line = "return new " + encoderQualifiedName + "(" + fieldName + ",\"" + idField +"\", " + accessName + ");";
		builder.addln(line);
		builder.end();

		transformation.addMethod(sig, builder.toString());

	}

}
