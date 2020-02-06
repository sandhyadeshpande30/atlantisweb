package tth.loyalty.program.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="loyalty_transaction")
public class LoyaltyTransaction implements Serializable {
	
	/**
	 * default version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *Primary Key
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * Transaction Type 
	 * PURCHASE, REDEEM or ACQUIRE
	 */
	@NotNull
	@Column(name="transaction_type")
	private TransactionType type; 
	
	/**
	 * Date of Transaction
	 */
	@Column(name="transaction_date")
	private Calendar transactionDate;
	
	/**
	 * Amount of Transaction
	 */
	@Column(name="transaction_amount")
	private BigDecimal transactionAmount;
	
	/**
	 *  Points involved in transaction
	 */
	@NotNull
	@Column(name="loyalty_points")
	private Long loyaltyPoints;
	
	/**
	 * transactionRemarks
	 */
	@Column(name="transaction_remarks")
	private String transactionRemarks; 
	
	/**
	 * status - FAILED, SUCCESS
	 */
	private TransactionStatus status;
	
	/**
	 * payment from
	 */ 
	private String source;
	
	/**errorReson
	 */
	@Column(name="failure_reason")
	private String failureReason;
	
	
	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public String getTransactionRemarks() {
		return transactionRemarks;
	}

	public void setTransactionRemarks(String transactionRemarks) {
		this.transactionRemarks = transactionRemarks;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * Foreign Key reference to Customer
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id") 
	private LoyaltyCustomer loyaltyCustomer;

	/**
	 * Getter and setters
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public Calendar getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Calendar transactionDate) {
		this.transactionDate = transactionDate;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public Long getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(Long loaltyPoints) {
		this.loyaltyPoints = loaltyPoints;
	}
  

	public LoyaltyCustomer getLoyaltyCustomer() {
		return loyaltyCustomer;
	}

	public void setLoyaltyCustomer(LoyaltyCustomer loyaltyCustomer) {
		this.loyaltyCustomer = loyaltyCustomer;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

	
}
