package org.reactome.web.elv.client.details.tabs.analysis.view.widgets.found;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import org.reactome.web.elv.client.details.tabs.analysis.presenter.providers.FoundAsyncDataProvider;
import org.reactome.web.elv.client.details.tabs.analysis.view.widgets.common.CustomPager;
import org.reactome.web.elv.client.details.tabs.analysis.view.widgets.notfound.NotFoundTable;

import java.util.Arrays;
import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class FoundPanel extends DockLayoutPanel {
    private String token;
    private String resource = "TOTAL";
    private Long pathwayId;
    private boolean forceLoad = true;

    private CustomPager pager;

    public FoundPanel() {
        super(Style.Unit.EM);

        this.pager = new CustomPager(); // Create paging controls.
        this.pager.getElement().getStyle().setDisplay(Style.Display.INLINE_BLOCK);
        this.pager.setPageSize(NotFoundTable.PAGE_SIZE);
    }

    public void showFound(List<String> resources, List<String> columnNames){
        if(!forceLoad) return; //Will only force to reload the data when the analysis details has been changed
        this.forceLoad = false;

        FoundTable table;
        if(this.resource.equals("TOTAL")){
            table = new FoundTable(resources, columnNames);
        }else{
            table = new FoundTable(Arrays.asList(this.resource), columnNames);
        }
        this.pager.setDisplay(table);

        new FoundAsyncDataProvider(table, this.pager, this.token, this.pathwayId, this.resource);

        this.clear();
        FlowPanel pagerPanel = new FlowPanel();
        pagerPanel.setWidth("100%");
        pagerPanel.getElement().getStyle().setTextAlign(Style.TextAlign.CENTER);
        pagerPanel.add(pager);
        this.addSouth(pagerPanel, 2);

        this.add(table);
    }

    public void setAnalysisDetails(String token, Long pathwayId) {
        this.token = token;
        this.pathwayId = pathwayId;
        this.forceLoad = true;
    }

    public void setResource(String resource) {
        this.resource = resource;
        this.forceLoad = true;
    }
}
