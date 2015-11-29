package org.myas.tags;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

/**
 * Custom tag class. Provides functionality to create form with single button. Gives opportunity
 * to set button class and id, servlet to communicate with and what command execute on click.
 */
public class ButtonFormTag extends BodyTagSupport {
    /**
     * Represents html <code>class</code> attribute equivalent.
     */
    private String styleClass;

    /**
     * Represents html <code>id</code> attribute equivalent.
     */
    private String styleId;

    /**
     * Represents servlet mapping name.
     */
    private String action;

    /**
     * Represents name of command to execute.
     */
    private String command;

    /**
     * Represents id of object in some collection.
     */
    private Integer model;

    /**
     * @param styleClass class of button in html.
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    /**
     * @param styleId id of button in html.
     */
    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    /**
     * @param action servlet mapping name.
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @param command name of command to execute.
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * @param model id of object we want to manipulate with on servlet.
     */
    public void setModel(Integer model) {
        this.model = model;
    }

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        StringBuilder b = new StringBuilder("<form action=\"");
        b.append(action).append("\" method=\"post\">\n");
        b.append("\t<input type=\"hidden\" name=\"command\" value=\"").append(command).append("\">\n");
        b.append("\t<input type=\"hidden\" name=\"id\" value=\"").append(model).append("\">\n");
        b.append("\t<button type=\"submit\" class=\"").append(styleClass).append("\" id=\"").
                append(styleId).append("\">\n");

        try {
            out.write(b.toString());
        } catch (IOException e) {
            Logger.getLogger(getClass()).error(e);
        }
        return EVAL_BODY_BUFFERED;
    }

    @Override
    public int doAfterBody() throws JspException {
        BodyContent bodyContent = getBodyContent();
        String bodyString = bodyContent.getString();
        JspWriter out = bodyContent.getEnclosingWriter();

        try {
            out.print(bodyString);
            out.print("</button>\n</form>");
        } catch (IOException e) {
            Logger.getLogger(getClass()).error(e);
        }
        return SKIP_BODY;
    }
}
