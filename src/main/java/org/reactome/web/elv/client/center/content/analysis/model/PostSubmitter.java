package org.reactome.web.elv.client.center.content.analysis.model;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.InitializeEvent;
import com.google.gwt.event.logical.shared.InitializeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.ui.*;
import org.reactome.diagram.client.AlertPopup;
import org.reactome.web.elv.client.center.content.analysis.event.AnalysisCompletedEvent;
import org.reactome.web.elv.client.center.content.analysis.event.AnalysisErrorEvent;
import org.reactome.web.elv.client.center.content.analysis.event.AnalysisErrorType;
import org.reactome.web.elv.client.center.content.analysis.handler.AnalysisCompletedEventHandler;
import org.reactome.web.elv.client.center.content.analysis.handler.AnalysisErrorEventHandler;
import org.reactome.web.elv.client.center.content.analysis.resources.AnalysisExamples;
import org.reactome.web.elv.client.center.content.analysis.style.AnalysisStyleFactory;
import org.reactome.web.elv.client.common.ReactomeImages;
import org.reactome.web.elv.client.common.analysis.factory.AnalysisModelException;
import org.reactome.web.elv.client.common.analysis.factory.AnalysisModelFactory;
import org.reactome.web.elv.client.common.analysis.model.AnalysisResult;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class PostSubmitter extends DockLayoutPanel implements ClickHandler {
    private static final String POST_ANALYSIS = "/AnalysisService/identifiers/?page=1";
    private static final String POST_ANALYSIS_PROJECTION = "/AnalysisService/identifiers/projection?page=1";

    private CheckBox projection;
    private TextArea textArea;
    private Image loading;
    private Integer height = 310;

    public PostSubmitter() {
        super(Style.Unit.PX);
        //noinspection GWTStyleCheck
        setStyleName("clearfix");
        addStyleName(AnalysisStyleFactory.getAnalysisStyle().analysisBlock());
        setHeight(this.height + "px");
        getElement().getStyle().setPadding(0, Style.Unit.PX);

        FlowPanel submissionPanel = new FlowPanel();
        submissionPanel.getElement().getStyle().setPaddingLeft(5, Style.Unit.PX);
        submissionPanel.getElement().getStyle().setPaddingRight(5, Style.Unit.PX);
        submissionPanel.addStyleName(AnalysisStyleFactory.getAnalysisStyle().analysisSubmission());
        submissionPanel.getElement().getStyle().setPaddingRight(5, Style.Unit.PX);
        Button clear = new Button("Clear", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                textArea.setText("");
            }
        });
        clear.addStyleName(AnalysisStyleFactory.getAnalysisStyle().postSubmitterClear());
        submissionPanel.add(clear);
        submissionPanel.add(new Button("Analyse", this));
        this.loading = new Image(ReactomeImages.INSTANCE.loader());
        this.loading.setVisible(false);
        submissionPanel.add(this.loading);
        this.projection = new CheckBox("Project to human");
        this.projection.setValue(true);
        submissionPanel.add(this.projection);
        addSouth(submissionPanel, 40);

        addEast(this.getExampleButtons(), 210);

        FlowPanel north = new FlowPanel();
//        SimplePanel explanation = new SimplePanel();
//        explanation.getElement().setInnerHTML(AnalysisExamples.EXAMPLES.analysisExamples().getText());
//        north.add(explanation);
        Label label = new Label("Paste the data to analyse");
        label.getElement().getStyle().setMarginLeft(15, Style.Unit.PX);
        label.getElement().getStyle().setMarginTop(5, Style.Unit.PX);
        north.add(label);
        addNorth(north, 30);

        this.textArea = new TextArea();
        this.textArea.getElement().setAttribute("style", "font-family: Consolas;");
        add(this.textArea);
    }

    public HandlerRegistration addAnalysisCompletedEventHandler(AnalysisCompletedEventHandler handler){
        return this.addHandler(handler, AnalysisCompletedEvent.TYPE);
    }

    public HandlerRegistration addAnalysisErrorEventHandler(AnalysisErrorEventHandler handler){
        return this.addHandler(handler, AnalysisErrorEvent.TYPE);
    }

    public Integer getHeight() {
        return height;
    }

    @Override
    public void onClick(ClickEvent event) {
        if(this.textArea.getText().isEmpty()) {
            AlertPopup.alert("Please add the identifiers to analyse");
            return;
        }

        this.loading.setVisible(true);
        String url = this.projection.getValue() ? POST_ANALYSIS_PROJECTION : POST_ANALYSIS;
        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, url);
        requestBuilder.setHeader("Content-Type", "text/plain");
        requestBuilder.setHeader("Accept", "application/json");
        try {
            requestBuilder.sendRequest(this.getData(), new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    if(response.getStatusCode() != Response.SC_OK){
                        loading.setVisible(false);
                        fireEvent(new AnalysisErrorEvent(AnalysisErrorType.FROM_RESPONSE.setMessage(response)));
                    }else{
                        try {
                            AnalysisResult result = AnalysisModelFactory.getModelObject(AnalysisResult.class, response.getText());
                            fireEvent(new AnalysisCompletedEvent(result));
                        } catch (AnalysisModelException e) {
                            fireEvent(new AnalysisErrorEvent(AnalysisErrorType.RESULT_FORMAT));
                        }
                        loading.setVisible(false);
                    }
                }

                @Override
                public void onError(Request request, Throwable exception) {
                    loading.setVisible(false);
                    fireEvent(new AnalysisErrorEvent(AnalysisErrorType.SERVICE_UNAVAILABLE));
                }
            });
        }catch (RequestException ex) {
            loading.setVisible(false);
            fireEvent(new AnalysisErrorEvent(AnalysisErrorType.SERVICE_UNAVAILABLE));
        }
    }

    private String getData(){
        return this.textArea.getText();
    }

    private Widget getExampleButtons(){
        FlowPanel examples = new FlowPanel();
        examples.addStyleName(AnalysisStyleFactory.getAnalysisStyle().postSubmitterExamples());
        Label label = new Label("Some examples:");
        label.getElement().getStyle().setMargin(5, Style.Unit.PX);
        examples.add(label);
        examples.add(new Button("Uniprot accession list", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                textArea.setText(AnalysisExamples.EXAMPLES.getUniprot().getText());
            }
        }));
        examples.add(new Button("Gene name list", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                textArea.setText(AnalysisExamples.EXAMPLES.getGeneNames().getText());
            }
        }));
        examples.add(new Button("Gene NCBI / Entrez list", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                textArea.setText(AnalysisExamples.EXAMPLES.geneNCBI().getText());
            }
        }));
        examples.add(new Button("Small molecules (ChEBI)", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                textArea.setText(AnalysisExamples.EXAMPLES.getChEBI().getText());
            }
        }));
        examples.add(new Button("Small molecules (KEGG)", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                textArea.setText(AnalysisExamples.EXAMPLES.getKegg().getText());
            }
        }));
        examples.add(new Button("Microarray data", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                textArea.setText(AnalysisExamples.EXAMPLES.getExpression().getText());
            }
        }));
        examples.add(new Button("Metabolomics data", new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                textArea.setText(AnalysisExamples.EXAMPLES.getMetabolomics().getText());
            }
        }));
        return examples;
    }
}
