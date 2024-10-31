package j.haianh.thymeleaf.controller;

import java.util.List;
import java.util.Locale.Category;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import j.haianh.thymeleaf.model.CategoryModel;
import j.haianh.thymeleaf.service.ICategoryService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("admin/categories")
public class CategoryController {

	@Autowired
	
	ICategoryService cateServ;

	@RequestMapping("")
	public String list(ModelMap model) {
		List<Category> list = cateServ.findAll();
		model.addAttribute("categories", list);
		return "admin/categories/list";
	}
	
	@GetMapping("add")
	public String add(ModelMap model) {
		CategoryModel cateMod = new CategoryModel();
		cateMod.setIsEdit(false););

		model.addAttribute("category", cateMod);
		return "admin/categories/addOrEdit";
	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("cate") CategoryModel cateModel,
			BindingResult result) {

		if (result.hasErrors()) {
			return new ModelAndView("admin/categories/addOrEdit");
		}
		Category entity = new Category();

		BeanUtils.copyProperties(cateModel, entity);

		cateServ.save(entity);

		String message = "";
		if (cateModel.isEdit() == true) {
			message = "Chinh sua thanh cong";
		} else {
			message = "Da luu thanh cong";
		}

		model.addAttribute("message", message);
		return new ModelAndView("forward:/admin/categories", model);
	}
	
	@GetMapping("edit/{categoryId}")
	public ModelAndView edit(ModelMap model, @PathVariable("categoryId") Long categoryId) {
		Optional<Category> optCate = cateServ.findById(categoryId);
		CategoryModel cateModel = new CategoryModel();
		if (optCate.isPresent()) {
			Category entity = optCate.get();
			
			BeanUtils.copyProperties(entity, cateModel);
			cateModel.setIsEdit(true);
			model.addAttribute("category", cateModel);
			return new ModelAndView("admin/category/addOrEdit", model);
		}
		model.addAttribute("message", "category is not existed");
		return new ModelAndView("forward:/admin/categories", model);
	}
	
	@GetMapping("delete/{categoryId}")
	public ModelAndView delete(ModelMap model, @PathVariable("categoryId") Long categoryId) {
		cateServ.deleteById(categoryId);
		model.addAttribute("message","Deleted!");
		return new ModelAndView("forward:/admin/categories", model);
	}
	
	
}
