package j.haianh.thymeleaf.repository;

import java.util.List;

import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import j.haianh.thymeleaf.entity.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>{
	List<CategoryEntity> findByNamecontaining(String name);
	Page<CategoryEntity> findByNameContaining()
}