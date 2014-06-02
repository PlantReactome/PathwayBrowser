package org.reactome.web.elv.client.details.tabs.analysis.view.widgets.results;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import org.reactome.web.elv.client.common.analysis.model.PathwaySummary;
import org.reactome.web.elv.client.details.tabs.analysis.view.widgets.results.columns.*;
import org.reactome.web.elv.client.details.tabs.analysis.view.widgets.results.events.ResultPathwaySelectedEvent;
import org.reactome.web.elv.client.details.tabs.analysis.view.widgets.results.handlers.ResultPathwaySelectedHandler;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class AnalysisResultTable extends DataGrid<PathwaySummary> {
    public final static Integer PAGE_SIZE = 20;

    private SingleSelectionModel<PathwaySummary> selectionModel;

    public AnalysisResultTable(List<String> expColumnNames) {
        super(PAGE_SIZE, new ProvidesKey<PathwaySummary>() {
            @Override
            public Object getKey(PathwaySummary item) {
                return item == null ? null : item.getDbId();
            }
        });
        this.setAutoHeaderRefreshDisabled(true);
        this.setWidth("100%");
        this.setVisible(true);

        List<AbstractColumn<?>> columns = new LinkedList<AbstractColumn<?>>();
        columns.add(new PathwayNameColumn());
        columns.add(new EntitiesFoundColumn(new FieldUpdater<PathwaySummary, String>() {
            @Override
            public void update(int index, PathwaySummary object, String value) {
                fireEvent(new ResultPathwaySelectedEvent(getSelectedObject()));
            }
        }));
        columns.add(new EntitiesTotalColumn());

//        if(!analysisResult.getSummary().getType().equals("EXPRESSION")){
            columns.add(new EntitiesRatioColumn());
            columns.add(new EntitiesPValueColumn());
            columns.add(new EntitiesFDRColumn());
            columns.add(new ReactionsFoundColumn());
            columns.add(new ReactionsTotalColumn());
            columns.add(new ReactionsRatioColumn());
//        }

        for (int i = 1; i < expColumnNames.size(); i++) {
            String title = expColumnNames.get(i);
            columns.add(new ExpressionColumn(i - 1, title));
        }

        columns.add(new SpeciesColumn());

        for (AbstractColumn<?> column : columns) {
            this.addColumn(column, column.buildHeader());
            this.setColumnWidth(column, column.getWidth(), com.google.gwt.dom.client.Style.Unit.PX);
        }

        this.selectionModel = new SingleSelectionModel<PathwaySummary>();
        this.setSelectionModel(selectionModel);
    }

    public HandlerRegistration addSelectionChangeHandler(SelectionChangeEvent.Handler handler){
        return this.selectionModel.addSelectionChangeHandler(handler);
    }

    public HandlerRegistration addResultPathwaySelectedHandler(ResultPathwaySelectedHandler handler){
        return this.addHandler(handler, ResultPathwaySelectedEvent.TYPE);
    }

    public PathwaySummary getSelectedObject(){
        return this.selectionModel.getSelectedObject();
    }

    public void scrollToItem(int index){
        this.getRowElement(index).scrollIntoView();
    }

    public void selectPathway(PathwaySummary pathwaySummary, Integer index){
        selectionModel.setSelected(pathwaySummary, true);
        this.getRowElement(index).scrollIntoView();
    }

    public void clearSelection(){
        this.selectionModel.clear();
    }
}
