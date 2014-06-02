package org.reactome.web.elv.client.details.tabs.analysis.view.widgets.found.columns;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.Header;
import org.reactome.web.elv.client.common.analysis.model.PathwayIdentifier;
import org.reactome.web.elv.client.common.analysis.model.PathwaySummary;
import org.reactome.web.elv.client.details.tabs.analysis.view.widgets.common.cells.CustomHeader;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public abstract class AbstractColumn<T> extends Column<PathwayIdentifier, T> {

    protected final String COLUMN_NAME_TITLE;
    protected final String COLUMN_GROUP;
    protected final Style.TextAlign HEADER_ALIGN;

    protected Integer width = 90;

    public AbstractColumn(Cell<T> cell, String group, String title) {
        this(cell, Style.TextAlign.CENTER, group, title);
    }

    public AbstractColumn(Cell<T> cell, Style.TextAlign headerAlign, String group, String title) {
        super(cell);

        setDataStoreName(title);
        COLUMN_NAME_TITLE = title;
        COLUMN_GROUP = group;
        HEADER_ALIGN = headerAlign;

        this.setHorizontalAlignment(ALIGN_RIGHT);
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public final Header buildHeader(){
        return new CustomHeader(new TextCell(), HEADER_ALIGN, COLUMN_GROUP, COLUMN_NAME_TITLE);
    }

}
