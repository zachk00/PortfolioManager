package sunshine.titans.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity @Table(name="watchlist")
public class WatchlistStock {
	
	WatchlistStock(){}
	
	public WatchlistStock(String ticker) {
		this.ticker = ticker;
	}
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
	
	@Column(name="ticker")
	private String ticker;

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

	@Override
	public String toString() {
		return "WatchlistStock [id=" + id + ", ticker=" + ticker + "]";
	}	
	
}
