package org.reactome.web.elv.client.common.data.model;

import com.google.gwt.json.client.JSONObject;
import org.reactome.web.elv.client.common.data.factory.FactoryUtils;
import org.reactome.web.elv.client.common.data.factory.SchemaClass;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
@SuppressWarnings("UnusedDeclaration")
public class GO_CellularComponent extends DatabaseObject {
    private String accession;
    private String definition;
    private ReferenceDatabase referenceDatabase;
    private String referenceDatabaseClass;

    public GO_CellularComponent(JSONObject jsonObject) {
        this(SchemaClass.GO_CELLULAR_COMPONENT, jsonObject);
    }

    public GO_CellularComponent(SchemaClass schemaClass, JSONObject jsonObject) {
        super(schemaClass, jsonObject);

        if(jsonObject.containsKey("accession")){
            this.accession = FactoryUtils.getStringValue(jsonObject, "accession");
        }

        if(jsonObject.containsKey("definition")){
            this.definition = FactoryUtils.getStringValue(jsonObject, "definition");
        }

        if(jsonObject.containsKey("referenceDatabase")){
            this.referenceDatabase = (ReferenceDatabase) FactoryUtils.getDatabaseObject(jsonObject, "referenceDatabase");
        }

        if(jsonObject.containsKey("referenceDatabaseClass")){
            this.referenceDatabaseClass = FactoryUtils.getStringValue(jsonObject, "referenceDatabaseClass");
        }
    }

    public String getAccession() {
        return accession;
    }

    public String getDefinition() {
        return definition;
    }

    public ReferenceDatabase getReferenceDatabase() {
        return referenceDatabase;
    }

    public String getReferenceDatabaseClass() {
        return referenceDatabaseClass;
    }
}
