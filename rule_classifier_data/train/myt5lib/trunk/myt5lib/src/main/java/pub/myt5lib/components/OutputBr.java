package pub.myt5lib.components;

//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at

//http://www.apache.org/licenses/LICENSE-2.0

//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.

import java.text.Format;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;

public class OutputBr {

	@Parameter(required = true)
	private Object _value;

	@Parameter(required = true)
	private Object _format;

	@Parameter("componentResources.elementName")
	private String _elementName;

	@Inject
	private ComponentResources _resources;

	private String[] formatArray;

	private String[] resultArray; 

	private String[] numberArray;

	boolean beginRender(MarkupWriter writer) {
		String formatted = null;

		if (_format instanceof String) {
			if (_format.toString().equalsIgnoreCase("cpf")) {
				if (_value == null){
					_value = "";
				}
				formatted = numberFormat(_value.toString(), "###.###.###-##");
			}	

			else if (_format.toString().equalsIgnoreCase("cnpj")){
				if (_value == null){
					_value = "";
				}
				formatted = numberFormat(_value.toString(), "##.###.###/####-##");
			} 

			else {
				formatted = "?literal: "+_format.toString()+"?";
			}
		}

		else {
			formatted = ((Format) _format).format(_value);
		}

		if (InternalUtils.isNonBlank(formatted)) {
			if (_elementName != null) {
				writer.element(_elementName);
				_resources.renderInformalParameters(writer);
			}

			writer.write(formatted);

			if (_elementName != null) {
				writer.end();
			}
		}

		return false;
	}

	public String numberFormat(String number, String format){
		this.formatArray = new String[format.length()];
		this.resultArray = new String[format.length()];
		String res = "";
		int subNumber = 0;
		int sub = 0;

		if (_format.equals("cpf")){
			subNumber = (11 - number.length());
			this.numberArray = new String[ (number.length() + subNumber) ];

			if (number.equals("")){
				return "-";
			}

			if(!(number.length() > 11)) {
				if(!(number.toString().length() == 0)){
					if (!(number.length() == 11)){
						for(int i = number.length(); i < (number.length() + subNumber); i++){
							this.numberArray[i] = "?";
						}
					}

					for (int i = 0; i < number.length(); i++)
						numberArray[i] = number.substring(i, i+1);

					for (int i = 0; i < format.length(); i++){
						formatArray[i] = format.substring(i, i+1);
						if(formatArray[i].equals("#")) {
							resultArray[i] = numberArray[sub];
							sub = sub + 1;
						} else { resultArray[i] = formatArray[i]; }
						res = res + resultArray[i];
					}
					return res;
				}
			} else {
				for (int i = 0; i < 11; i++)
					numberArray[i] = number.substring(i, i+1);
				for (int i = 0; i < format.length(); i++){
					formatArray[i] = format.substring(i, i+1);
					if(formatArray[i].equals("#")) {
						resultArray[i] = numberArray[sub];
						sub = sub + 1;
					} else { resultArray[i] = formatArray[i]; }
					res = res + resultArray[i];
				}
				return res+"...";
			}
		} 

		if (_format.equals("cnpj")){
			subNumber = (14 - number.length());
			this.numberArray = new String[ (number.length() + subNumber) ];

			if (number.equals("")){
				return "-";
			}

			if(!(number.length() > 14)) {
				if(!(number.toString().length() == 0)){
					if (!(number.length() == 14)){
						for(int i = number.length(); i < (number.length() + subNumber); i++){
							this.numberArray[i] = "?";
						}
					}

					for (int i = 0; i < number.length(); i++)
						numberArray[i] = number.substring(i, i+1);

					for (int i = 0; i < format.length(); i++){
						formatArray[i] = format.substring(i, i+1);
						if(formatArray[i].equals("#")) {
							resultArray[i] = numberArray[sub];
							sub = sub + 1;
						} else { resultArray[i] = formatArray[i]; }
						res = res + resultArray[i];
					}
					return res;
				}
			} else {
				for (int i = 0; i < 14; i++)
					numberArray[i] = number.substring(i, i+1);
				for (int i = 0; i < format.length(); i++){
					formatArray[i] = format.substring(i, i+1);
					if(formatArray[i].equals("#")) {
						resultArray[i] = numberArray[sub];
						sub = sub + 1;
					} else { resultArray[i] = formatArray[i]; }
					res = res + resultArray[i];
				}
				return res+"...";
			}
		} 

		return null;
	}
}