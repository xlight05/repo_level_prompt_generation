package net.homeip.tinwiki.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * Form bean for a Struts application.
 * Users may access 1 field on this form:
 * <ul>
 * <li>score - [your comment here]
 * </ul>
 * @version 	1.0
 * @author
 */
public class JudgeScore extends ActionForm

{

    private Integer score = null;

    /**
     * Get score
     * @return Integer
     */
    public Integer getScore() {
        return score;
    }

    /**
     * Set score
     * @param <code>Integer</code>
     */
    public void setScore(Integer s) {
        this.score = s;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {

        // Reset values are provided as samples only. Change as appropriate.

        score = null;

    }

    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {

        ActionErrors errors = new ActionErrors();
        // Validate the fields in your form, adding
        // adding each error to this.errors as found, e.g.

        // if ((field == null) || (field.length() == 0)) {
        //   errors.add("field", new org.apache.struts.action.ActionError("error.field.required"));
        // }
        return errors;

    }
}
