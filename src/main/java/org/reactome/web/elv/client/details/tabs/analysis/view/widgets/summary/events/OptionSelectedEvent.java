package org.reactome.web.elv.client.details.tabs.analysis.view.widgets.summary.events;

import com.google.gwt.event.shared.GwtEvent;
import org.reactome.web.elv.client.details.tabs.analysis.view.widgets.summary.AnalysisInfoType;
import org.reactome.web.elv.client.details.tabs.analysis.view.widgets.summary.handlers.OptionSelectedHandler;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class OptionSelectedEvent extends GwtEvent<OptionSelectedHandler> {
    public static Type<OptionSelectedHandler> TYPE = new Type<OptionSelectedHandler>();

    private AnalysisInfoType analysisInfoType;

    public OptionSelectedEvent(AnalysisInfoType analysisInfoType) {
        this.analysisInfoType = analysisInfoType;
    }

    @Override
    public Type<OptionSelectedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(OptionSelectedHandler handler) {
        handler.onOptionSelected(this);
    }

    public AnalysisInfoType getAnalysisInfoType() {
        return analysisInfoType;
    }
}
