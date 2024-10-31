package j.haianh.thymeleaf.entity;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="Category")
public class CategoryEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	@Column(name="categoryId")
	private Long categoryid;
	@Column(name="category_name", length=200,columnDefinition = "nvarchar(200) not null")
	@NotEmpty(message="Khong dc rong")
	private String name;
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private Set<ProductEntity> products;
}

