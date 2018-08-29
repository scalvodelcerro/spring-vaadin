package com.example.springvaadin.view;

import org.springframework.util.StringUtils;

import com.example.springvaadin.entity.Customer;
import com.example.springvaadin.repository.CustomerRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {
	private static final long serialVersionUID = 3857915262427106097L;

	private final CustomerRepository customerRepo;
	private final CustomerEditor customerEditor;
	
	private Grid<Customer> grid;
	private TextField filter;
	private Button addNewBtn;

	public MainView(CustomerRepository customerRepo, CustomerEditor customerEditor) {
		this.customerRepo = customerRepo;
		this.customerEditor = customerEditor;
		this.grid = new Grid<>(Customer.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("New customer", VaadinIcon.PLUS.create());

		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		add(actions, grid, this.customerEditor);

		grid.setHeight("300px");
		grid.setColumns("id", "firstName", "lastName");
		grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

		filter.setPlaceholder("Filter by last name");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.EAGER);
		filter.addValueChangeListener(e -> listCustomers(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
		grid.asSingleSelect().addValueChangeListener(e -> {
			this.customerEditor.editCustomer(e.getValue());
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> this.customerEditor.editCustomer(new Customer("", "")));

		// Listen changes made by the editor, refresh data from backend
		this.customerEditor.setChangeHandler(() -> {
			this.customerEditor.setVisible(false);
			listCustomers(filter.getValue());
		});

		// Initialize listing
		listCustomers(null);
	}

	private void listCustomers() {
		grid.setItems(customerRepo.findAll());
	}
	
	void listCustomers(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(customerRepo.findAll());
		}
		else {
			grid.setItems(customerRepo.findByLastNameStartsWithIgnoreCase(filterText));
		}
	}

}
