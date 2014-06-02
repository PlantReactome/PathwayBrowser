package org.reactome.web.elv.client.details.model.widgets;

import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.reactome.web.elv.client.common.data.model.DatabaseObject;
import org.reactome.web.elv.client.common.data.model.Publication;
import org.reactome.web.elv.client.common.data.model.Summation;
import org.reactome.web.elv.client.common.widgets.disclosure.DisclosurePanelFactory;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class SummationPanel extends DetailsPanel {
    private Summation summation;
    private VerticalPanel vp;

    public SummationPanel(Summation summation) {
        this(null, summation);
    }

    public SummationPanel(DetailsPanel parentPanel, Summation summation) {
        super(parentPanel);
        this.summation = summation;
        this.vp = new VerticalPanel();
        initialize();
    }

    private void initialize(){
        vp.add(DisclosurePanelFactory.getLoadingMessage());
        vp.addStyleName("elv-Details-OverviewDisclosure-Advanced");
        vp.setWidth("100%");
        initWidget(vp);
        dataRequired(this.summation);
    }

    @Override
    public DatabaseObject getDatabaseObject() {
        return this.summation;
    }

    @Override
    public void setReceivedData(DatabaseObject data) {
        this.summation = (Summation) data;
        HTMLPanel content = new HTMLPanel(this.summation.getText());
        content.addStyleName("elv-Details-OverviewDisclosure-summation");
        vp.clear();
        vp.add(content);

        if(!this.summation.getLiteratureReference().isEmpty()){
            DisclosurePanel literatureReferences = new DisclosurePanel("Literature references...");
            literatureReferences.setWidth("100%");
            VerticalPanel aux = new VerticalPanel();
            aux.setWidth("100%");
            for (Publication publication : this.summation.getLiteratureReference()) {
                PublicationPanel pp = new PublicationPanel(this, publication);
                pp.setWidth("99%");
                aux.add(pp);
            }
            literatureReferences.setContent(aux);
            vp.add(literatureReferences);
        }

        setLoaded(true);
    }
}