package j.haianh.thymeleaf.repository;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import j.haianh.thymeleaf.entity.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>{
	List<CategoryEntity> findByNamecontaining(String name);
	Page<CategoryEntity> findByNameContaining(String name,Pageable pageable);
}
