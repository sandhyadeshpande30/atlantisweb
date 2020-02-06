package tth.loyalty.program.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author M1006550
 * Entity Model for Loyalty Customer
 *
 */
@Entity
@Table(name="loyalty_customer")
public class LoyaltyCustomer implements Serializable{
	
	/**
	 * Default version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *Primary Key
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * UserID for customer
	 */
	@Column(name="user_id")
	private String userID;
	
	/**
	 * firstName
	 */
	@Column(name="first_name")
	private String firstName;
	
	/**
	 * lastName
	 */
	@Column(name="last_name")
	private String lastName;
	
	/**
	 * current balance
	 */
	private Long balance;
	
	/**
	 * Custom message for customer
	 */
	@Column(name="custom_message")
	private String customMessage;
	
	/**
	 * contains all recent transactions
	 */
	@OneToMany(fetch= FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "loyaltyCustomer")
	private Set<LoyaltyTransaction> transactions;
	
	
	
	/**
	 * Getters and setters 
	 *
	 */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Long getBalance() {
		return balance;
	}
	public void setBalance(Long balance) {
		this.balance = balance;
	}
	
	public String getCustomMessage() {
		return customMessage;
	}
	public void setCustomMessage(String customMessage) {
		this.customMessage = customMessage;
	}
	
	public Set<LoyaltyTransaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(Set<LoyaltyTransaction> transactions) {
		this.transactions = transactions;
	}
	@Override
	public String toString() {
		return "LoyaltyCustomer [id=" + id + ", userID=" + userID + ", firstName=" + firstName + ", lastName="
				+ lastName + ", balance=" + balance + ", customMessage=" + customMessage + ", transactions="
				+ transactions + "]";
	}
	

}
