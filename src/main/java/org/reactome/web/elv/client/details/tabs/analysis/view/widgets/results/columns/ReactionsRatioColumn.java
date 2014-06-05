package org.reactome.web.elv.client.details.tabs.analysis.view.widgets.results.columns;

import com.google.gwt.dom.client.Style;
import com.google.gwt.i18n.client.NumberFormat;
import org.reactome.web.elv.client.common.analysis.model.PathwaySummary;
import org.reactome.web.elv.client.details.tabs.analysis.view.widgets.common.cells.CustomNumberCell;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class ReactionsRatioColumn extends AbstractColumn<Number> {

    private static final String explanation = "The total reactions in the pathway divided by the total number of reactions for the entire species for the selected molecular type";

    public ReactionsRatioColumn() {
        super(new CustomNumberCell(Style.FontStyle.ITALIC, NumberFormat.getDecimalFormat()), "Reactions", "ratio", explanation);
        setWidth(85);
    }

    @Override
    public Number getValue(PathwaySummary object) {
        if(object==null) return null;
        return object.getReactions().getRatio();
    }
}
