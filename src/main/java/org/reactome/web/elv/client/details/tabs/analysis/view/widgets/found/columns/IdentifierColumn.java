package org.reactome.web.elv.client.details.tabs.analysis.view.widgets.found.columns;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Style;
import org.reactome.web.elv.client.common.analysis.model.PathwayIdentifier;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class IdentifierColumn extends AbstractColumn<String> {

    private static final String explanation = "The submitted identifier";

    public IdentifierColumn(String group, String title) {
        super(new TextCell(), Style.TextAlign.LEFT, group, title, explanation);
        setHorizontalAlignment(ALIGN_LEFT);
    }

    @Override
    public String getValue(PathwayIdentifier object) {
        return object.getIdentifier();
    }
}
