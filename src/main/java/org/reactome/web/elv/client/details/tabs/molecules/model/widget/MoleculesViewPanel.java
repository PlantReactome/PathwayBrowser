package org.reactome.web.elv.client.details.tabs.molecules.model.widget;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.reactome.web.elv.client.details.tabs.molecules.model.data.Result;

/**
 * @author Kerstin Hausmann <khaus@ebi.ac.uk>
 */
public class MoleculesViewPanel extends DockLayoutPanel {
    Result result;

    ScrollPanel scrollPanel;
    VerticalPanel containerTP; //necessary because scrollPanel can contain only ONE child, contains all TablePanels
    TablePanel chemicalsPanel;
    TablePanel proteinsPanel;
    TablePanel sequencesPanel;
    TablePanel othersPanel;

    public MoleculesViewPanel(final Result result) {
        super(Style.Unit.PX);

        this.result = result;

        //Creating and filling container for TablePanels.
        containerTP = new VerticalPanel();
        containerTP.setWidth("100%");
        containerTP.getElement().getStyle().setPaddingTop(5, Style.Unit.PX);
        containerTP.getElement().getStyle().setPaddingBottom(25, Style.Unit.PX);

        int size;
        if(result != null){
            size = result.getChemicals().size();
            if(size > 0){
                chemicalsPanel = new TablePanel("Chemical Compounds", size, result);
                chemicalsPanel.addStyleName("elv-Details-OverviewRow");
                chemicalsPanel.asWidget().setWidth("99%");

                containerTP.add(chemicalsPanel);
            }

            size = result.getProteins().size();
            if(size > 0){
                proteinsPanel = new TablePanel("Proteins", size, result);
                proteinsPanel.addStyleName("elv-Details-OverviewRow");
                proteinsPanel.asWidget().setWidth("99%");

                containerTP.add(proteinsPanel);
            }

            size = result.getSequences().size();
            if(size > 0){
                sequencesPanel = new TablePanel("Sequences", size, result);
                sequencesPanel.addStyleName("elv-Details-OverviewRow");
                sequencesPanel.asWidget().setWidth("99%");

                containerTP.add(sequencesPanel);
            }

            size = result.getOthers().size();
            if(size > 0){
                othersPanel = new TablePanel("Others", size, result);
                othersPanel.addStyleName("elv-Details-OverviewRow");
                othersPanel.asWidget().setWidth("99%");

                containerTP.add(othersPanel);
            }
        }

        scrollPanel = new ScrollPanel(containerTP);
        scrollPanel.addStyleName("elv-Details-OverviewPanel");

        this.add(scrollPanel);
    }

    //Avoids loading if Pathway-with-Diagram stays the same.
    public void update(Result result) {
        this.result = result;

        //Update all the TablePanels for the Molecules...
        int size;
        if(result != null){
            size = result.getChemicals().size();
            if(size > 0){
                chemicalsPanel.update(size, result);
            }

            size = result.getProteins().size();
            if(size > 0){
                proteinsPanel.update(size, result);
            }

            size = result.getSequences().size();
            if(size > 0){
                sequencesPanel.update(size, result);
            }

            size = result.getOthers().size();
            if(size > 0){
                othersPanel.update(size, result);
            }
        }

        //Finally set the new content.
        scrollPanel.removeFromParent();
        scrollPanel = new ScrollPanel(containerTP);
        scrollPanel.addStyleName("elv-Details-OverviewPanel");
        this.add(scrollPanel);
    }

}