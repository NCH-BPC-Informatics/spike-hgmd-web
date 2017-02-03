package org.nch.research.hgmd.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;
import org.nch.research.hgmd.db.HGMDGene;
import org.nch.research.hgmd.db.HGMDGeneRepository;
import org.nch.research.hgmd.db.HGMDVariant;
import org.nch.research.hgmd.db.HGMDVariantRepository;
import org.vaadin.viritin.button.ConfirmButton;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.components.DisclosurePanel;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.label.RichText;
import org.vaadin.viritin.layouts.MVerticalLayout;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Title("HGMD Search")
@Theme("valo")
@SpringUI
public class MainUI extends UI {

    private static final long serialVersionUID = 1L;

    HGMDGeneRepository repo;
    HGMDVariantRepository variantRepository;
    private Panel variantListPanel = new Panel("HGMD Variant List");
    Panel geneListPanel = new Panel("HGMD Gene Search");
    private MTable<HGMDGene> list = new MTable<>(HGMDGene.class)
            .withProperties("geneSymbol", "Gene_Name", "entrezId", "omimId", "alternateSymbols", "Number_of_Reported_Mutations", "Number_of_variants_avail")
            .withColumnHeaders("Gene Symbol", "Gene Name", "Entrez ID", "OMIM ID", "Alternate Symbols", "# mutations", "# variants in dataset")
            .withStyleName(ValoTheme.TABLE_SMALL)
            .withStyleName(ValoTheme.TABLE_COMPACT)
            .withFullWidth();

    private MTable<HGMDVariant> variantList = new MTable<>(HGMDVariant.class)
            .withProperties("geneSymbol", "dnaChange", "dbSnp", "proteinChange", "weight")
            .withColumnHeaders("Gene Symbol", "DNA Change", "dbSNP", "Protein Change", "Weight")
            .withStyleName(ValoTheme.TABLE_SMALL)
            .withStyleName(ValoTheme.TABLE_COMPACT)
            .withFullWidth();

    private TextField filterById = new MTextField()
            .withInputPrompt("Filter by ID/content");
    private Button addNew = new MButton(FontAwesome.PLUS, this::add);
    private Button edit = new MButton(FontAwesome.PENCIL_SQUARE_O, this::edit);
    private Button delete = new ConfirmButton(FontAwesome.TRASH_O,
            "Are you sure you want to delete the entry?", this::remove);
    private VerticalLayout panelLayout;

    public MainUI(HGMDGeneRepository r, HGMDVariantRepository vr) {
        this.repo = r;
        this.variantRepository = vr;
    }

    @Override
    protected void init(VaadinRequest request) {
        /*
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add(".v-panel-caption-caption-green .v-caption { " +
                "    color: #FFFFFF; " +
                "    background-color: #4CAF50; " +
                "}");
        styles.add(".v-panel-caption-caption-blue .v-caption { " +
                "    color: #FFFFFF; " +
                "    background-color: darkblue; " +
                "}");
        variantListPanel.addStyleName("caption-green");
        */
        MVerticalLayout variantListLayout = new MVerticalLayout(variantList);
        variantListPanel.setContent(variantListLayout);

        list.addRowClickListener(this::rowSelected);
        MVerticalLayout geneSearchLayout = new MVerticalLayout(
                // aboutBox,
                // new MHorizontalLayout(filterById, addNew, edit, delete),
                filterById,
                list
        );

        geneListPanel.addStyleName("caption-blue");
        geneListPanel.setContent(geneSearchLayout);

        panelLayout = new VerticalLayout(geneListPanel, variantListPanel);
        setContent(panelLayout);
        // setContent(geneSearchLayout.expand(list));

        listEntities();



        list.addMValueChangeListener(e -> adjustActionButtonState());
        filterById.addTextChangeListener(e -> {
            variantList.setRows(new ArrayList<HGMDVariant>());
            listEntities(e.getText());
        });
    }

    protected void adjustActionButtonState() {
        boolean hasSelection = list.getValue() != null;
        edit.setEnabled(hasSelection);
        delete.setEnabled(hasSelection);
    }

    static final int PAGESIZE = 45;

    private void listEntities() {
        listEntities(filterById.getValue());
    }

    private void listEntities(String idFilter) {
        // A dead simple in memory listing would be:
        // list.setRows(repo.findAll());

        // But we want to support filtering, first add the % marks for SQL name query
        String likeFilter = "%" + idFilter.toUpperCase() + "%";
        if(idFilter != null && idFilter.length() > 1) {
            /*
//         Lazy binding for better optimized connection from the Vaadin Table to
//         Spring Repository. This approach uses less memory and database
//         resources. Use this approach if you expect you'll have lots of data
//         in your table. There are simpler APIs if you don't need sorting.
            list.lazyLoadFrom(
                    // entity fetching strategy
                    (firstRow, asc, sortProperty) -> repo.findByIds(
                            idFilter,
                            new PageRequest(
                                    firstRow / PAGESIZE,
                                    PAGESIZE,
                                    asc ? Sort.Direction.ASC : Sort.Direction.DESC,
                                    // fall back to id as "natural order"
                                    sortProperty == null ? "Gene_Symbol" : sortProperty
                            )
                    ),
                    // count fetching strategy
                    () -> (int) repo.countByIds(likeFilter),
                    PAGESIZE
            ); */
            list.setRows(repo.findByIdsOrderByGeneSymbol(likeFilter));
        } else {
            list.setRows(repo.findAllByOrderByGeneSymbol());
        }
        adjustActionButtonState();

    }

    public void rowSelected(MTable.RowClickEvent clickEvent) {
        HGMDGene row = (HGMDGene) clickEvent.getRow();
        if( row != null ) {
            String geneSymbol = row.getGeneSymbol();
            List<HGMDVariant> rows = variantRepository.findById(geneSymbol);
            variantListPanel.setCaption(MessageFormat.format("HGMD Variant List <i>({0} variant(s))</i>", rows.size()));
            variantList.setRows(rows);
        }
    }

    public void add(ClickEvent clickEvent) {
        // edit(new Person());
    }

    public void edit(ClickEvent e) {
        // edit(list.getValue());
    }

    public void remove(ClickEvent e) {
//        repo.delete(list.getValue());
//        list.setValue(null);
//        listEntities();
    }

    protected void edit(final HGMDGene record) {
//        personForm.setEntity(phoneBookEntry);
//        personForm.openInModalPopup();
    }

//    @EventBusListenerMethod(scope = EventScope.UI)
//    public void onPersonModified(PersonModifiedEvent event) {
//        listEntities();
//        personForm.closePopup();
//    }

}
