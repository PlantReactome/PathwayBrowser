package org.reactome.web.elv.client.details.tabs.analysis.view.widgets.results.columns;

import com.google.gwt.dom.client.Style;
import org.reactome.web.elv.client.common.analysis.model.PathwaySummary;
import org.reactome.web.elv.client.details.tabs.analysis.view.widgets.common.cells.CustomNumberCell;


/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class ReactionsFoundColumn extends AbstractColumn<Number> {

    private static final String explanation = "The number of reactions in this pathway that contain at least one molecule from the query set for the selected molecular type";

    public ReactionsFoundColumn() {
        super(new CustomNumberCell(Style.FontStyle.ITALIC), "Reactions", "found", explanation);
        setWidth(85);
    }

    @Override
    public Integer getValue(PathwaySummary object) {
        if (object == null) return null;
        return object.getReactions().getFound();
    }
}
