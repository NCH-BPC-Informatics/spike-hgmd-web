package org.nch.research.hgmd.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import org.nch.research.hgmd.db.HGMD;
import org.nch.research.hgmd.db.HgmdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.vaadin.viritin.button.ConfirmButton;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.components.DisclosurePanel;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.label.RichText;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

@Title("HGMD Search")
@Theme("valo")
@SpringUI
public class MainUI extends UI {

    private static final long serialVersionUID = 1L;

    HgmdRepository repo;

    private MTable<HGMD> list = new MTable<>(HGMD.class)
            .withProperties("entrezId", "accession", "classification", "omimId", "alternateSymbols")
            .withColumnHeaders("Entrez ID", "Accession ID", "Classification", "OMIM ID", "Alternate Symbols")
            .setSortableProperties("entrezId", "accession", "classification", "omimId", "alternateSymbols")
            .withFullWidth();

    private TextField filterById = new MTextField()
            .withInputPrompt("Filter by ID/content");
    private Button addNew = new MButton(FontAwesome.PLUS, this::add);
    private Button edit = new MButton(FontAwesome.PENCIL_SQUARE_O, this::edit);
    private Button delete = new ConfirmButton(FontAwesome.TRASH_O,
            "Are you sure you want to delete the entry?", this::remove);

    public MainUI(HgmdRepository r) {
        this.repo = r;
    }

    @Override
    protected void init(VaadinRequest request) {
        DisclosurePanel aboutBox = new DisclosurePanel("HGMD Search Utility", new RichText().withMarkDown("Hi!")); // .withMarkDownResource("/welcome.md"));
        setContent(
                new MVerticalLayout(
                        aboutBox,
                        new MHorizontalLayout(filterById, addNew, edit, delete),
                        list
                ).expand(list)
        );
        listEntities();

        list.addMValueChangeListener(e -> adjustActionButtonState());
        filterById.addTextChangeListener(e -> {
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
        String likeFilter = "%" + idFilter + "%";
        if(idFilter == null || idFilter.trim().isEmpty()) {
            list.setRows(repo.findAll());
        } else {
            list.setRows(repo.findByIds(likeFilter));
        }

        // Lazy binding for better optimized connection from the Vaadin Table to
        // Spring Repository. This approach uses less memory and database
        // resources. Use this approach if you expect you'll have lots of data
        // in your table. There are simpler APIs if you don't need sorting.
        // list.lazyLoadFrom(
        //         // entity fetching strategy
        //         (firstRow, asc, sortProperty) -> repo.findByNameLikeIgnoreCase(
        //                 likeFilter,
        //                 new PageRequest(
        //                         firstRow / PAGESIZE,
        //                         PAGESIZE,
        //                         asc ? Sort.Direction.ASC : Sort.Direction.DESC,
        //                         // fall back to id as "natural order"
        //                         sortProperty == null ? "id" : sortProperty
        //                 )
        //         ),
        //         // count fetching strategy
        //         () -> (int) repo.countByNameLike(likeFilter),
        //         PAGESIZE
        // );
        adjustActionButtonState();

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

    protected void edit(final HGMD record) {
//        personForm.setEntity(phoneBookEntry);
//        personForm.openInModalPopup();
    }

//    @EventBusListenerMethod(scope = EventScope.UI)
//    public void onPersonModified(PersonModifiedEvent event) {
//        listEntities();
//        personForm.closePopup();
//    }

}
