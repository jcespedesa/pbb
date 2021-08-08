package com.trc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="products")
public class ProductEntity 
{
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long productid;
	
	 @Column(name="product")
	 private String product;
	     
	 @Column(name="storeid")
	 private String storeId;
	    
	 @Column(name="filename")
	 private String fileName;
	 
	 @Column(name="price")
	 private String price;
	 
	 @Column(name="status")
	 private String status;
	 
	 @Column(name="condition")
	 private String condition;
	 
	 @Column(name="delivery")
	 private String delivery;
	 
	 @Column(name="categorynum")
	 private String categoryNum;
	 
	 @Column(name="category")
	 private String category;
	 
	 
	 @Override
	 public String toString() 
	 {
	       return "ProductEntity [productid="+ productid +",product="+ product +",storeId="+ storeId +",fileName="+ fileName +",status="+ status +",price="+ price +",condition="+ condition +",delivery="+ delivery +",categoryNum="+ categoryNum +",category="+ category +"]";
	 }

	 //Setters and getters
	 
	public Long getProductid() 
	{
		return productid;
	}

	public void setProductid(Long productid) 
	{
		this.productid=productid;
	}

	public String getProduct() 
	{
		return product;
	}

	public void setProduct(String product) 
	{
		this.product=product;
	}

	public String getStoreId() 
	{
		return storeId;
	}

	public void setStoreId(String storeId) 
	{
		this.storeId=storeId;
	}

	public String getFileName() 
	{
		return fileName;
	}

	public void setFileName(String fileName) 
	{
		this.fileName=fileName;
	}

	public String getStatus() 
	{
		return status;
	}

	public void setStatus(String status) 
	{
		this.status=status;
	}

	public String getPrice() 
	{
		return price;
	}

	public void setPrice(String price) 
	{
		this.price=price;
	}

	public String getCondition() 
	{
		return condition;
	}

	public void setCondition(String condition) 
	{
		this.condition=condition;
	}

	public String getDelivery() 
	{
		return delivery;
	}

	public void setDelivery(String delivery) 
	{
		this.delivery=delivery;
	}

	public String getCategoryNum() 
	{
		return categoryNum;
	}

	public void setCategoryNum(String categoryNum) 
	{
		this.categoryNum=categoryNum;
	}

	public String getCategory() 
	{
		return category;
	}

	public void setCategory(String category) 
	{
		this.category=category;
	}

		
}
