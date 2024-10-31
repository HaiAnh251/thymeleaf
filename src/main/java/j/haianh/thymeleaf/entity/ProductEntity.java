package j.haianh.thymeleaf.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="Products")
public class ProductEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	@Column(name="productId")
	private Long productid;
	@Column(name="product_name", length=200,columnDefinition = "nvarchar(200) not null")
	@NotEmpty(message="Khong dc rong")
	private String name;
	@JoinColumn(name = "CategoryID")
	private CategoryEntity category;

}
