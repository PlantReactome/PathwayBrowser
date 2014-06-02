package org.reactome.web.elv.client.details.tabs.overview.model.widgets.factory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import org.reactome.web.elv.client.common.data.model.*;
import org.reactome.web.elv.client.details.model.widgets.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("UnusedDeclaration")
public abstract class TableRowFactory {
    private static final boolean EMPTY_ROWS_AS_NA = !GWT.isScript();

    public static Widget getAbstractModifiedResidue(String title, List<AbstractModifiedResidue> modifiedResidues){
        List<DetailsPanel> panels = new LinkedList<DetailsPanel>();
        for (AbstractModifiedResidue modifiedResidue : modifiedResidues) {
            panels.add(new AbstractModifiedResiduePanel(modifiedResidue));
        }
        return getOverviewRow(title, panels);
    }

    public static Widget getCatalystActivityRow(String title, List<CatalystActivity> catalystActivities){
        List<DetailsPanel> panels = new LinkedList<DetailsPanel>();
        for (CatalystActivity catalystActivity : catalystActivities) {
            panels.add(new CatalystActivityPanel(catalystActivity));
        }
        return getOverviewRow(title, panels);
    }

    public static Widget getCoordinatesRow(String title, EntityWithAccessionedSequence ewas){
        if(ewas.getStartCoordinate()!=null && ewas.getEndCoordinate()!=null){
            StringBuilder sb = new StringBuilder();
            sb.append(ewas.getStartCoordinate()).append(" .. ").append(ewas.getEndCoordinate());
            return getTextPanelRow(title, sb.toString());
        }
        return getOverviewRow(title, new LinkedList<DetailsPanel>());
    }

    public static Widget getDatabaseIdentifierRow(String title, List<DatabaseIdentifier> databaseIdentifiers){
        List<DetailsPanel> panels = new LinkedList<DetailsPanel>();
        if(!databaseIdentifiers.isEmpty()){
            panels.add(new ExternalIdentifierPanel(databaseIdentifiers));
        }
        return getOverviewRow(title, panels);
    }

    public static Widget getEntityFunctionalStatusRow(String title, List<EntityFunctionalStatus> entityFunctionalStatus){
        List<DetailsPanel> panels = new LinkedList<DetailsPanel>();
        for (EntityFunctionalStatus efs : entityFunctionalStatus) {
            panels.add(new EntityFunctionalStatusPanel(efs));
        }
        return getOverviewRow(title, panels);
    }

    public static Widget getExternalOntologyRow(String title, ExternalOntology externalOntology){
        List<ExternalOntology> externalOntologies = new LinkedList<ExternalOntology>();
        if(externalOntology!=null){
            externalOntologies.add(externalOntology);
        }
        return getExternalOntologyRow(title, externalOntologies);
    }

    public static Widget getExternalOntologyRow(String title, List<? extends  ExternalOntology> externalOntologies){
        List<DetailsPanel> panels = new LinkedList<DetailsPanel>();
        for (ExternalOntology disease : externalOntologies) {
            panels.add(new ExternalOntologyPanel(disease));
        }
        return getOverviewRow(title, panels);
    }

    public static Widget getEventRow(String title, Event event){
        List<Event> events = new LinkedList<Event>();
        if(event!=null){
            events.add(event);
        }
        return getEventRow(title, events);
    }

    public static Widget getEventRow(String title, List<Event> events){
        List<DetailsPanel> panels = new LinkedList<DetailsPanel>();
        for (Event event : events) {
            panels.add(new EventPanel(event));
        }
        return getOverviewRow(title, panels);
    }

    public static Widget getGOBiologicalProcessRow(String title, GO_BiologicalProcess goBiologicalProcess){
        List<DetailsPanel> panels = new LinkedList<DetailsPanel>();
        if(goBiologicalProcess!=null){
            panels.add(new GO_BiologicalProcessPanel(goBiologicalProcess));
        }
        return getOverviewRow(title, panels);
    }

    public static Widget getGOCellularComponentRow(String title, List<? extends GO_CellularComponent> goCellularComponents){
        List<DetailsPanel> panels = new LinkedList<DetailsPanel>();
        for (GO_CellularComponent goCellularComponent : goCellularComponents) {
            panels.add(new GO_CellularComponentPanel(goCellularComponent));
        }
        return getOverviewRow(title, panels);
    }

    public static Widget getInstanceEditRow(String title, List<InstanceEdit> authored){
        List<DetailsPanel> panels = new LinkedList<DetailsPanel>();
        for (InstanceEdit instanceEdit : authored) {
            panels.add(new InstanceEditPanel(instanceEdit));
        }
        return getOverviewRow(title, panels);
    }

    public static Widget getLiteratureReferencesRow(String title, List<Publication> literatureReferences){
        List<DetailsPanel> panels = new LinkedList<DetailsPanel>();
        for (Publication literatureReference : literatureReferences) {
            panels.add(new PublicationPanel(literatureReference));
        }
        return getOverviewRow(title, panels);
    }

    public static Widget getOrthologousEventRow(String title, List<Event> orthologousEvents){
        return getOverviewRow(title, new OrthologousEventPanel(orthologousEvents));
    }

    public static Widget getOverviewRow(String title, DetailsPanel panelList ){
        List<DetailsPanel> list = new LinkedList<DetailsPanel>();
        list.add(panelList);
        return getOverviewRow(title, list);
    }

    public static OverviewRow getOverviewRow(String title, List<DetailsPanel> panelList ){
        Widget cont;
        if(panelList==null || panelList.isEmpty()){
            if(EMPTY_ROWS_AS_NA){
                cont = new HTMLPanel("N/A");
            }else{
                return null; //by returning null, the OverviewTable class will understand that nothing needs to be added
            }
        }else{
            cont = new VerticalPanel();
            for (DetailsPanel detailsPanel : panelList) {
                ((VerticalPanel) cont).add(detailsPanel);
            }
        }
        cont.setWidth("100%");

        OverviewRow container = new OverviewRow();
        container.setWidth("100%");
        container.add(title, cont);

        return container;
    }

    public static OverviewRow getPhysicalEntityRow(String title, List<PhysicalEntity> physicalEntities){
        //Processing the list to avoid duplicates (and count the number of times that the same PE appear)
        Map<PhysicalEntity, Integer> map = new HashMap<PhysicalEntity, Integer>();
        for (PhysicalEntity physicalEntity : physicalEntities) {
            int num = 1;
            if(map.containsKey(physicalEntity)){
                num = map.get(physicalEntity) + 1;
            }
            map.put(physicalEntity, num);
        }

        List<DetailsPanel> panels = new LinkedList<DetailsPanel>();
        for (PhysicalEntity physicalEntity : map.keySet()) {
            panels.add(new PhysicalEntityPanel(physicalEntity, map.get(physicalEntity)));
        }
        return getOverviewRow(title, panels);
    }

    public static OverviewRow getNormalReactionLikeEventRow(String title, List<ReactionLikeEvent> reactionLikeEvents){
        List<DetailsPanel> panels = new LinkedList<DetailsPanel>();
        for (ReactionLikeEvent reactionLikeEvent : reactionLikeEvents) {
            panels.add(new EventPanel(reactionLikeEvent));
        }
        return getOverviewRow(title, panels);
    }

    public static OverviewRow getReactionLikeEventRow(String title, List<ReactionLikeEvent> reactionLikeEvents){
        List<DetailsPanel> panels = new LinkedList<DetailsPanel>();
        for (ReactionLikeEvent reactionLikeEvent : reactionLikeEvents) {
            panels.add(new EventPanel(reactionLikeEvent));
        }
        return getOverviewRow(title, panels);
    }

    public static OverviewRow getReferenceEntityRow(String title, ReferenceEntity referenceEntity){
        List<DetailsPanel> panels = new LinkedList<DetailsPanel>();
        if(referenceEntity!=null){
            panels.add(new ReferenceEntityPanel(referenceEntity));
        }
        return getOverviewRow(title, panels);
    }

    public static OverviewRow getRegulationRow(String title, List<? extends Regulation> regulations){
        List<DetailsPanel> panels = new LinkedList<DetailsPanel>();
        for (Regulation regulation : regulations) {
            panels.add(new RegulationPanel(regulation));
        }
        return getOverviewRow(title, panels);
    }

    public static Widget getRegulatorRow(String title, List<DatabaseObject> list){
        List<DetailsPanel> panels = new LinkedList<DetailsPanel>();
        for (DatabaseObject databaseObject : list) {
            if(databaseObject instanceof PhysicalEntity){
                panels.add(new PhysicalEntityPanel((PhysicalEntity) databaseObject));
            }else if(databaseObject instanceof Event){
                panels.add(new EventPanel((Event) databaseObject));
            }else if(databaseObject instanceof CatalystActivity){
                panels.add(new CatalystActivityPanel((CatalystActivity) databaseObject));
            }
        }
        return getOverviewRow(title, panels);
    }

    public static Widget getSpeciesRow(String title, Species species){
        List<Species> list = new LinkedList<Species>();
        list.add(species);
        return getSpeciesRow(title, list);
    }

    public static Widget getSpeciesRow(String title, List<Species> species){
        List<DetailsPanel> panels = new LinkedList<DetailsPanel>();
        for (Species s : species) {
            panels.add(new SpeciesPanel(s));
        }
        return getOverviewRow(title, panels);
    }

    public static Widget getStableIdentifierRow(String title, StableIdentifier stableIdentifier){
        List<DetailsPanel> panels = new LinkedList<DetailsPanel>();
        if(stableIdentifier!=null){
            panels.add(new StableIdentifierPanel(stableIdentifier));
        }
        return getOverviewRow(title, panels);
    }

    public static Widget getSummationRow(String title, List<Summation> summations){
        List<DetailsPanel> panels = new LinkedList<DetailsPanel>();
        for (Summation summation : summations) {
            panels.add(new SummationPanel(summation));
        }
        return getOverviewRow(title, panels);
    }

    public static Widget getTextPanelRow(String title, Integer number){
        String txt = ( number == null ) ? null : number.toString() ;
        return getTextPanelRow(title, txt);
    }

    public static Widget getTextPanelRow(String title, String name){
        if(name==null){
            //With that a row with an empty content is retrieved and so can be detected
            //and removed in production time (instead of showing the "N/A")
            return getOverviewRow(title, new LinkedList<DetailsPanel>());
        }
        return getOverviewRow(title, new TextPanel(name));
    }
}
