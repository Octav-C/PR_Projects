package application;



import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import application.ViewController.OrdersTree;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.util.Callback;
import javafx.util.StringConverter;


public class ViewController {
	
	//@FXML private TreeTableView<OrdersTree> treeTableView;
	//@FXML private TreeTableColumn<OrdersTree,String> name;
	//@FXML private TreeTableColumn<OrdersTree,String> total;
	@FXML private DatePicker fromDatePicker;
	@FXML private DatePicker toDatePicker;
	@FXML private AnchorPane anchorPane;
	public String startDate = "2017-11-01";
	public String endDate = "2017-11-10";
	public String categoriesURL = "https://evil-legacy-service.herokuapp.com/api/v101/categories/";
	public String ordersURL = "https://evil-legacy-service.herokuapp.com/api/v101/orders/?start=" + startDate + "&end=" + endDate;
	public ParseCategories parseCategories;
	public ParseOrders parseOrders;
	InputStream body;
	InputStream body1;
	List<String> categories ;
	List<String> orders ;
	Tree<String> root;
	TreeTableView<OrdersTree> treeTableView;
	TreeItem<OrdersTree> falseRoot = new TreeItem<>(new OrdersTree("root","root"));
	
	@FXML private void initialize() throws InterruptedException, ExecutionException, IOException {
		TreeTableColumn<OrdersTree, String> nameColumn = 
	            new TreeTableColumn<>("Name");
		nameColumn.setPrefWidth(350);
		nameColumn.setCellValueFactory(
	            (TreeTableColumn.CellDataFeatures<OrdersTree, String> param) -> 
	            new ReadOnlyStringWrapper(param.getValue().getValue().getNameProperty())
	        );

	        TreeTableColumn<OrdersTree, String> totalColumn = 
	            new TreeTableColumn<>("Total");
	    totalColumn.setPrefWidth(190);
	    totalColumn.setCellValueFactory(
	            (TreeTableColumn.CellDataFeatures<OrdersTree, String> param) -> 
	            new ReadOnlyStringWrapper(param.getValue().getValue().getTotalProperty())
	        );
		treeTableView = new TreeTableView<>(falseRoot);
		treeTableView.setShowRoot(false);
        treeTableView.setLayoutX(250);
        treeTableView.setLayoutY(200);
        treeTableView.getColumns().setAll(nameColumn, totalColumn);
        anchorPane.getChildren().add(treeTableView);
		setDefaultsDatePicker();
		
		
		 
		}
	public void setDefaultsDatePicker() {
		
		fromDatePicker.setPromptText("Select Value");
		toDatePicker.setPromptText("Select Value");
		fromDatePicker.setConverter(new StringConverter<LocalDate>() {
		     DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		     @Override
		     public String toString(LocalDate date) {
		         if (date != null) {
		             return dateFormatter.format(date);
		         } else {
		             return "";
		         }
		     }
		     @Override
		     public LocalDate fromString(String string) {
		         if (string != null && !string.isEmpty()) {
		             return LocalDate.parse(string, dateFormatter);
		         } else {
		             return null;
		         }
		     }
		 });
		toDatePicker.setConverter(new StringConverter<LocalDate>() {
		     DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		     @Override
		     public String toString(LocalDate date) {
		         if (date != null) {
		             return dateFormatter.format(date);
		         } else {
		             return "";
		         }
		     }
		     @Override
		     public LocalDate fromString(String string) {
		         if (string != null && !string.isEmpty()) {
		             return LocalDate.parse(string, dateFormatter);
		         } else {
		             return null;
		         }
		     }
		 });
		
	}
	
	
	public void createTreeTableUI() {
		falseRoot = new TreeItem<>(new OrdersTree("root","root"));
		//TreeItem<OrdersTree> falseRoot = new TreeItem<>(new OrdersTree("Test", "Test2"));
		falseRoot.setExpanded(true);
		final AtomicInteger counter = new AtomicInteger(1);
		final AtomicInteger counter2 = new AtomicInteger(1);
		this.root.getChildren().forEach(each ->  {
			
			TreeItem<OrdersTree> child = new TreeItem<>(new OrdersTree(each.getData().toString(),each.getTotal().toString()));
			falseRoot.getChildren().add(child);
			if(each.getChildren().isEmpty()==false) {
				((Tree<String>)each).getChildren().forEach(each2 -> {
					TreeItem<OrdersTree> child2 = new TreeItem<>(new OrdersTree(((Tree<String>) each2).getData().toString(),each2.getTotal().toString()));
					falseRoot.getChildren().get(counter.get()-1).getChildren().add(child2);
					if(each2.getChildren().isEmpty()==false) {
						
						each2.getChildren().forEach(each3 -> {
							TreeItem<OrdersTree> child3 = new TreeItem<>(new OrdersTree(((Tree<String>) each3).getData().toString(),((Tree<String>) each3).getTotal().toString()));
							falseRoot.getChildren().get(counter.get()-1).getChildren().get(counter2.get()-1).getChildren().add(child3);
							
						});
						
						
						
					}
					counter2.getAndAdd(1);
					
					
				});
			}
			counter2.set(1);
			counter.getAndAdd(1);
		});
		TreeTableColumn<OrdersTree, String> nameColumn = 
	            new TreeTableColumn<>("Name");
		nameColumn.setPrefWidth(350);
		nameColumn.setCellValueFactory(
	            (TreeTableColumn.CellDataFeatures<OrdersTree, String> param) -> 
	            new ReadOnlyStringWrapper(param.getValue().getValue().getNameProperty())
	        );

	        TreeTableColumn<OrdersTree, String> totalColumn = 
	            new TreeTableColumn<>("Total");
	    totalColumn.setPrefWidth(190);
	    totalColumn.setCellValueFactory(
	            (TreeTableColumn.CellDataFeatures<OrdersTree, String> param) -> 
	            new ReadOnlyStringWrapper(param.getValue().getValue().getTotalProperty())
	        );
		treeTableView = new TreeTableView<>(falseRoot);
		treeTableView.setShowRoot(false);
        treeTableView.setLayoutX(250);
        treeTableView.setLayoutY(200);
        treeTableView.getColumns().setAll(nameColumn, totalColumn);
        anchorPane.getChildren().add(treeTableView);
		

	        

	        
		
		
	}
	
	public void executeRequest() {
		Task<Void> task = new Task<Void>() {
	        @Override
	        protected Void call() throws Exception {
	            createTree();
	            Platform.runLater(() -> {               
	            	
		           
	            }); 
	            return null;
	        }            
	    };
	    task.setOnSucceeded(event -> {
	    	createTreeTableUI();
	    	
	    });
	    Thread thread = new Thread(task);
	    thread.setDaemon(true);
	    thread.start();
	}
	
	public void createTree() throws InterruptedException, ExecutionException, IOException {
		if(fromDatePicker.getValue()!=null && toDatePicker.getValue()!=null) {
			startDate = fromDatePicker.getValue().toString();
			endDate = toDatePicker.getValue().toString();
			ordersURL = "https://evil-legacy-service.herokuapp.com/api/v101/orders/?start=" + startDate + "&end=" + endDate;
			parseCategories= new ParseCategories();
			parseOrders= new ParseOrders();
			categories = parseCategories.getCategories();
			orders = parseOrders.getOrders();
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Select From and To Dates");
			alert.showAndWait();

			return;
		}
		ordersURL = "https://evil-legacy-service.herokuapp.com/api/v101/orders/?start=" + startDate + "&end=" + endDate;
		
		ExecutorService executor = Executors.newFixedThreadPool(2); 
		
		Future<Response> response = executor.submit(new Request(new URL(categoriesURL)));
		body = response.get().getBody();
		Future<Response> response1 = executor.submit(new Request(new URL(ordersURL)));
		body1 = response1.get().getBody();
		for (String str : parseCategories.getCategories(body)) {
			System.out.println(str);
		}
		for (String str : parseOrders.getOrders(body1)) {
			System.out.println(str);
		}
		manageData();
		createTreeTableUI();
		executor.shutdown();
	}
	
	public void manageData() throws IOException {
		
		
		System.out.println("--------------------------------------");
		root = new Tree<>("root");
		//--------------------------------------------------------------
		for(int i=1; i<categories.size();i++) {
			if(parseCategories.getCategory_id(i) == -1) {
				Tree<String> child = new Tree<>(parseCategories.getName(i));
				child.setidNode(parseCategories.getId(i));
				root.addChild(child);
				
				categories.remove(i);
				i--;
			}
		}
		int i=0;
		Tree<String> tempRoot = root;
		root = treeParseData(tempRoot);
		tempRoot = root.getChildren().get(2);
		tempRoot = treeParseData(tempRoot);
		root.getChildren().set(2, tempRoot);
		tempRoot = root.getChildren().get(3);
		tempRoot = treeParseData(tempRoot);
		root.getChildren().set(3, tempRoot);
		
		calculateSum(root);
		root.print(0);
	}
	
	public Tree<String> treeParseData(Tree<String> root) {
		for(int i=1; i<categories.size();i++) {
			for(int j=0;j<root.getChildren().size();j++) {
				if(parseCategories.getCategory_id(i) == root.getChildren().get(j).getidNode() ) {
					Tree<String> child = new Tree<>(parseCategories.getName(i));
					child.setidNode(parseCategories.getId(i));
					for(int k=1;k<orders.size();k++) {
						if(child.getidNode() == parseOrders.getCategoryID(k))
							child.setTotal(parseOrders.getTotal(k));
					}
					root.getChildren().get(j).addChild(child);
					categories.remove(i);
					i--;
				}
			}
		}
		return root;
	}
	
	public void calculateSum(Tree<String> node) {
		if(node.getParent()!=null && node.getParent().getData() != "root") {
			if(node.getChildren().isEmpty()==true) {
				node.getParent().setTotal(node.getParent().getTotal().add(node.getTotal()));
			} else {
				
				
				}
				
		}
		node.getChildren().forEach(each ->  calculateSum((Tree) each));
		for(Tree<String> temp : root.getChildren()) {
			temp.setTotal(BigDecimal.ZERO);
			for(Tree<String> tempChild : temp.getChildren()) {
				tempChild.getParent().setTotal(tempChild.getParent().getTotal().add(tempChild.getTotal()));
				
			}
			
		}
    }
	
	class OrdersTree{
		SimpleStringProperty nameProperty;
		SimpleStringProperty totalProperty;
		
		public OrdersTree(String name, String total) {
			this.nameProperty = new SimpleStringProperty(name);
			this.totalProperty = new SimpleStringProperty(total);
		}

		public String getNameProperty() {
			return nameProperty.get();
		}

		public void setNameProperty(SimpleStringProperty nameProperty) {
			this.nameProperty = nameProperty;
		}

		public String getTotalProperty() {
			return totalProperty.get();
		}

		public void setTotalProperty(SimpleStringProperty totalProperty) {
			this.totalProperty = totalProperty;
		}
	}

}

