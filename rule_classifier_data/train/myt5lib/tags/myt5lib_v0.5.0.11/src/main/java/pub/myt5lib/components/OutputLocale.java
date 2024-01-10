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

package pub.myt5lib.components;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.tapestry.ComponentResources;
import org.apache.tapestry.MarkupWriter;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.ioc.annotations.Inject;
import org.apache.tapestry.ioc.internal.util.InternalUtils;
import org.apache.tapestry.services.RequestGlobals;

/** component that formats a value and outputs it. */
public class OutputLocale {
	@Parameter(required = true)
	private Object _value;

	@Parameter(required = true)
	private Object _format;

	@Parameter("componentResources.elementName")
	private String _elementName;

    @Inject
    private RequestGlobals _requestGlobals;
    
	@Inject
	private ComponentResources _resources;

	boolean beginRender(MarkupWriter writer) throws Exception {
		String formatted = null;

		if (_format instanceof String) {
			if (_format.toString().equalsIgnoreCase("integer"))	// Syntax: format="literal:integer"

				formatted = integer().format(_value);

			else if (_format.toString().equalsIgnoreCase("decimal"))	// Syntax: format="literal:decimal"

				formatted = decimal().format(_value);

			else if (_format.toString().equalsIgnoreCase("currency"))	// Syntax: format="literal:currency"

				formatted = currency().format(_value);

			// Tratando Data nula.
			else if (_format.toString().substring(0, 4).equalsIgnoreCase("date")
					&& _value == null)
						formatted = "";
			
			else if (_format.toString().substring(0, 4).equalsIgnoreCase("date")) 	// Syntax: format="literal:date" or format="literal:date(MM/dd/yyyy)"

				if (_format.toString().indexOf('(') >= 0) {

					if ((_format.toString().indexOf(')') < 0) ||
						(_format.toString().indexOf(')') < _format.toString().indexOf('(')
						|| _format.toString().indexOf(')') != _format.toString().length() - 1))
						formatted = "?literal:"+_format.toString()+"?"; 
					else {
						try {
							formatted = formatDate((Date) _value, _format.toString().substring(
								_format.toString().indexOf('(') + 1, 
								_format.toString().indexOf(')')));
						}
						catch (Exception e) {
							formatted = "?literal:"+_format.toString()+"?"; }
					}

				} else {
					if (_format.toString().length() > 4)
						formatted = "?literal:"+_format.toString()+"?";
					else
						formatted = date().format(_value);
				}

			else
				
				formatted = "?literal:"+_format.toString()+"?";  // Unknown literal format.
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

			if (_elementName != null)
				writer.end();
		}

		return false;
	}

	public NumberFormat integer() {
		NumberFormat fmt = NumberFormat.getInstance(
			_requestGlobals.getRequest().getLocale());
		fmt.setMaximumFractionDigits(0);
		return fmt;
	}

	public DecimalFormat decimal() {
		return (DecimalFormat) NumberFormat.getInstance(
			_requestGlobals.getRequest().getLocale());
	}

	public DecimalFormat currency() {
		DecimalFormat fmt = (DecimalFormat) NumberFormat.getCurrencyInstance(
			_requestGlobals.getRequest().getLocale());
		fmt.setDecimalSeparatorAlwaysShown(true);
		fmt.setMaximumFractionDigits(2);
		fmt.setMinimumFractionDigits(2);
		return fmt;
	}

	public DateFormat date() {
		return (DateFormat) DateFormat.getDateInstance(DateFormat.MEDIUM,
			_requestGlobals.getRequest().getLocale());
	}

	public String formatDate(Date d, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern, 
			_requestGlobals.getRequest().getLocale());
		return sdf.format(d);
	}

}
