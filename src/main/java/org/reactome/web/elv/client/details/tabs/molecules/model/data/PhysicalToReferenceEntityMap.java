package org.reactome.web.elv.client.details.tabs.molecules.model.data;

import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONObject;
import org.reactome.web.elv.client.common.data.factory.FactoryUtils;
import org.reactome.web.elv.client.common.data.factory.SchemaClass;
import org.reactome.web.elv.client.common.data.model.StableIdentifier;
import org.reactome.web.elv.client.common.utils.Console;

/**
 * @author Kerstin Hausmann <khaus@ebi.ac.uk>
 */

/**
 * Instead of adding peDbId to the Class DatabaseObject PhysicalToReferenceEntityMap was created.
 * Everything except peDbId is similar to DatabaseObject.
 */
public class PhysicalToReferenceEntityMap {
    private Long dbId;
    private String _displayName;
    private StableIdentifier stableIdentifier;

    private Long peDbId;
    private String displayName;
    //private boolean disease = false; Changes for disease flag
    private SchemaClass schemaClass;

    public PhysicalToReferenceEntityMap(JSONObject jsonObject) {
        if(jsonObject.containsKey("dbId")){
            this.dbId = FactoryUtils.getLongValue(jsonObject, "dbId");
        }

        if(jsonObject.containsKey("_displayName")){
            this._displayName = FactoryUtils.getStringValue(jsonObject, "_displayName");
        }

        if(jsonObject.containsKey("displayName")){
            this.displayName = FactoryUtils.getStringValue(jsonObject, "displayName");
        }

        this.schemaClass = FactoryUtils.getSchemaClass(jsonObject);

        if(jsonObject.containsKey("stableIdentifier")){
            this.stableIdentifier = (StableIdentifier) FactoryUtils.getDatabaseObject(jsonObject, "stableIdentifier");
        }
        if(jsonObject.containsKey("peDbId")){
            this.peDbId = FactoryUtils.getLongValue(jsonObject, "peDbId");
        }

        /*Changes for disease flag
        for (JSONObject object : FactoryUtils.getObjectList(jsonObject, "disease")) {
            this.disease = true;
        }*/

        this.schemaClass = FactoryUtils.getSchemaClass(jsonObject);

        checkDatabaseObject(schemaClass);
    }

    Long getDbId() {
        return dbId;
    }

    public String get_displayName() {
        return _displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getIdentifier(){
        return String.valueOf(getDbId());
    }

    public SchemaClass getSchemaClass() {
        return schemaClass;
    }

    public StableIdentifier getStableIdentifier() {
        return stableIdentifier;
    }

    public Long getPeDbId() {
        return peDbId;
    }

    /*Changes for disease flag
    public boolean isDisease() {
        return disease;
    }*/

    private void checkDatabaseObject(SchemaClass schemaClass){
        if(this.peDbId.equals(0L) || this.displayName == null){
            String msg = "WRONG DATABASE OBJECT [" + this.toString() + "].";
            if(GWT.isScript()) Console.error(msg);
            throw new RuntimeException("WRONG DATABASE OBJECT [" + this.toString() + "].");
        }

        if(!this.schemaClass.equals(schemaClass)){
            String msg = "WRONG SCHEMA CLASS. Expecting [" + schemaClass.schemaClass + "], found [" + this.schemaClass.schemaClass + "].";
            if(GWT.isScript()) Console.error(msg);
            throw new RuntimeException(msg);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhysicalToReferenceEntityMap that = (PhysicalToReferenceEntityMap) o;

        if (peDbId != null ? !peDbId.equals(that.peDbId) : that.peDbId != null) return false;
        //noinspection RedundantIfStatement
        if (schemaClass != that.schemaClass) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = peDbId != null ? peDbId.hashCode() : 0;
        result = 31 * result + (schemaClass != null ? schemaClass.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PhysicalEntity{" +
                "peDbId=" + peDbId +
                ", displayName='" + displayName + '\'' +
                ", schemaClass=" + schemaClass.schemaClass +
                '}';
    }
}