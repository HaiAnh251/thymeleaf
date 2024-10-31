package j.haianh.thymeleaf.controller.admin;


import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import j.haianh.thymeleaf.entity.CategoryEntity;
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
		List<CategoryEntity> list = cateServ.findAll();
		model.addAttribute("categories", list);
		return "admin/categories/list";
	}
	
	@GetMapping("add")
	public String add(ModelMap model) {
		CategoryModel cateMod = new CategoryModel();
		cateMod.setIsEdit(false);

		model.addAttribute("category", cateMod);
		return "admin/categories/AddOrEdit";
	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("cate") CategoryModel cateModel,
			BindingResult result) {

		if (result.hasErrors()) {
			return new ModelAndView("admin/categories/AddOrEdit");
		}
		CategoryEntity entity = new CategoryEntity();

		BeanUtils.copyProperties(cateModel, entity);

		cateServ.save(entity);

		String message = "";
		if (cateModel.getIsEdit() == true) {
			message = "Chinh sua thanh cong";
		} else {
			message = "Da luu thanh cong";
		}

		model.addAttribute("message", message);
		return new ModelAndView("forward:/admin/categories", model);
	}
	
	@GetMapping("edit/{categoryid}")
	public ModelAndView edit(ModelMap model, @PathVariable("categoryid") Long categoryId) {
		Optional<CategoryEntity> optCate = cateServ.findById(categoryId);
		CategoryModel cateModel = new CategoryModel();
		if (optCate.isPresent()) {
			CategoryEntity entity = optCate.get();
			
			BeanUtils.copyProperties(entity, cateModel);
			cateModel.setIsEdit(true);
			model.addAttribute("category", cateModel);
			return new ModelAndView("admin/categories/AddOrEdit", model);
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
	@GetMapping("search")
	
		public String search(ModelMap model, @RequestParam(name="name", required=false) String name) {
			List<CategoryEntity> list=null;
			if(StringUtils.hasText(name))
			{
				list=cateServ.findAll();
			}
			model.addAttribute("cactegories", list);
			return "admin/categories/search";
			
			
		}
	
	@RequestMapping("searchpaginated")
	public String search1(ModelMap model,
			@RequestParam(name="name", required = false) String name,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size){
		int count=(int) cateServ.count();
		int currentPage=page.orElse(1);
		int pagesize=size.orElse(3);
		Pageable pageable=PageRequest.of(currentPage-1, pagesize,Sort.by("name"));
		Page<CategoryEntity> resultPage=null;
		if(StringUtils.hasText(name))
		{
			resultPage=cateServ.findByNameContaining(name, pageable);
			model.addAttribute("name", name);
		}
		else
		{
			resultPage = cateServ.findAll(pageable);
		}
		int totalPages=resultPage.getTotalPages();
		if(totalPages>0)
		{
			int start=Math.max(1, currentPage-2);
			int end=Math.min(currentPage+2, totalPages);
			if(totalPages> count)
			{
				if(end==totalPages) start=end-count;
				else if(start==1) end=start+count;
			}
			List<Integer> pageNumbers=IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		model.addAttribute("categoryPage", resultPage);
		
	
	return "admin/categories/searchpaginated";
	}
}	
	
	
