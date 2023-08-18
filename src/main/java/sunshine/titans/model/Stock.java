package sunshine.titans.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity @Table(name="portfolio")//table name
public class Stock implements Serializable {


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    
    @Column(name="ticker")
    private String ticker;
    
    @Column(name="purchasePrice")
    private Double purchasePrice;
    
    @Column(name="quantity")
    private Integer quantity;
    
    public Stock() {}
    
    public Stock(String ticker, double purchasePrice, int quantity) {
    	this.ticker = ticker;
    	this.purchasePrice = purchasePrice;
    	this.quantity = quantity;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
    
}
